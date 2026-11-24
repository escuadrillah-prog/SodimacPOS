package util;

import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import modelo.ItemVenta;

public class PersistenciaWindows {

    public static File getCarpetaBase() {
        String userHome = System.getProperty("user.home");
        return new File(userHome, "POS_Sodimac_Data");
    }

    public static File getArchivoCatalogo() {
        return new File(getCarpetaBase(), "catalogo_productos.csv");
    }

    public static File getArchivoVentas() {
        return new File(getCarpetaBase(), "ventas_realizadas.csv");
    }

    public static File getCarpetaBoletas() {
        return new File(getCarpetaBase(), "boletas");
    }

    public static void inicializarDirectorios() {
        File base = getCarpetaBase();
        if (!base.exists()) {
            base.mkdirs();
        }
        File boletas = getCarpetaBoletas();
        if (!boletas.exists()) {
            boletas.mkdirs();
        }
        File ventas = getArchivoVentas();
        if (!ventas.exists()) {
            try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(
                    new FileOutputStream(ventas), StandardCharsets.UTF_8))) {
                pw.println("FOLIO;FECHA_HORA;RUT_SOCIO;METODO_PAGO;SUBTOTAL;DESCUENTO;TOTAL_PAGAR;MONTO_PAGADO;VUELTO");
            } catch (Exception ignored) {
            }
        }
    }

    public static int obtenerSiguienteFolio() {
        File ventas = getArchivoVentas();
        int lineas = 0;
        if (ventas.exists()) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(ventas), StandardCharsets.UTF_8))) {
                while (br.readLine() != null) {
                    lineas++;
                }
            } catch (Exception ignored) {
            }
        }
        return Math.max(1, lineas);
    }

    /**
     * Registra la venta real en CSV y genera el archivo fisico de Boleta Electronica en Windows.
     */
    public static File registrarVentaYGenerarBoleta(
            List<ItemVenta> items,
            String rutSocio,
            String metodoPago,
            int subtotal,
            int descuento,
            int totalFinal,
            int montoPagado,
            int vuelto) {

        inicializarDirectorios();
        int folio = obtenerSiguienteFolio();
        String folioStr = String.format("%06d", folio);
        String fechaHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
        String socioTexto = (rutSocio != null && !rutSocio.isEmpty()) ? rutSocio : "SIN RUT";

        // 1. Guardar linea en ventas_realizadas.csv
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(
                new FileOutputStream(getArchivoVentas(), true), StandardCharsets.UTF_8))) {
            pw.println(folioStr + ";" + fechaHora + ";" + socioTexto + ";" + metodoPago + ";"
                    + subtotal + ";" + descuento + ";" + totalFinal + ";" + montoPagado + ";" + vuelto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2. Generar archivo fisico de boleta
        NumberFormat nf = NumberFormat.getInstance(new Locale("es", "CL"));
        File archivoBoleta = new File(getCarpetaBoletas(), "BOLETA_" + folioStr + ".txt");
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(
                new FileOutputStream(archivoBoleta), StandardCharsets.UTF_8))) {
            pw.println("==========================================");
            pw.println("         HOMECENTER SODIMAC S.A.          ");
            pw.println("       AUTOSERVICIO POS - WINDOWS         ");
            pw.println("==========================================");
            pw.println("BOLETA ELECTRONICA N°: " + folioStr);
            pw.println("FECHA / HORA         : " + fechaHora);
            pw.println("CLIENTE / SOCIO CMR  : " + socioTexto);
            pw.println("FORMA DE PAGO        : " + metodoPago);
            pw.println("------------------------------------------");
            pw.println("CANT  DESCRIPCION                 TOTAL");
            pw.println("------------------------------------------");
            for (ItemVenta it : items) {
                String nom = it.getProducto().getNombre();
                if (nom.length() > 24) nom = nom.substring(0, 24);
                pw.printf("%-5s %-24s $ %9s%n",
                        it.getCantidad() + "x",
                        nom,
                        nf.format(it.getSubtotal()));
            }
            pw.println("------------------------------------------");
            pw.printf("SUBTOTAL                     : $ %9s%n", nf.format(subtotal));
            if (descuento > 0) {
                pw.printf("DESCUENTO CUPON              : -$%9s%n", nf.format(descuento));
            }
            pw.printf("TOTAL A PAGAR                : $ %9s%n", nf.format(totalFinal));
            pw.printf("MONTO PAGADO                 : $ %9s%n", nf.format(montoPagado));
            pw.printf("VUELTO                       : $ %9s%n", nf.format(vuelto));
            pw.println("==========================================");
            pw.println("   ¡GRACIAS POR PREFERIR SODIMAC!         ");
            pw.println("==========================================");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return archivoBoleta;
    }

    public static void abrirBoletaEnWindows(File archivoBoleta) {
        if (archivoBoleta != null && archivoBoleta.exists()) {
            try {
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(archivoBoleta);
                }
            } catch (Exception ignored) {
            }
        }
    }

    public static void imprimirBoletaWindows(final File archivoBoleta) {
        if (archivoBoleta == null || !archivoBoleta.exists()) return;
        try {
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setJobName("Boleta Sodimac - " + archivoBoleta.getName());
            job.setPrintable(new Printable() {
                @Override
                public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                    if (pageIndex > 0) return NO_SUCH_PAGE;
                    Graphics2D g2d = (Graphics2D) graphics;
                    g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
                    g2d.setFont(new Font("Monospaced", Font.PLAIN, 9));
                    int y = 20;
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(
                            new FileInputStream(archivoBoleta), StandardCharsets.UTF_8))) {
                        String linea;
                        while ((linea = br.readLine()) != null) {
                            g2d.drawString(linea, 15, y);
                            y += 12;
                        }
                    } catch (Exception ignored) {
                    }
                    return PAGE_EXISTS;
                }
            });
            if (job.printDialog()) {
                job.print();
            }
        } catch (Exception ignored) {
        }
    }
}
