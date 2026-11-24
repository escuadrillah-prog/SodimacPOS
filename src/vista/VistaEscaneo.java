package vista;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class VistaEscaneo extends PanelFondo {
    public JTextField txtScanner;
    public JTable tablaProductos;
    public DefaultTableModel modeloTabla;
    public JLabel lblTotalMonto;

    public BotonPOS btnCupon;
    public BotonPOS btnCantidad;
    public BotonPOS btnBuscarArtic;
    public BotonPOS btnTarjetaSocio;
    public BotonPOS btnPagar;

    public BotonPOS btnSubir;
    public BotonPOS btnBajar;
    public BotonPOS btnVolumen;
    public BotonPOS btnIdioma;
    public BotonPOS btnAnular;
    public BotonPOS btnAyuda;

    private Image imgKioscoEscaneo;

    public VistaEscaneo() {
        super(false); // Logo grande
        imgKioscoEscaneo = cargarImagen("/img/kiosco_escaneo.png");

        JLabel lblEncabezado = new JLabel("Escanear y embolsar", SwingConstants.CENTER);
        lblEncabezado.setFont(new Font("Arial", Font.BOLD, 28));
        lblEncabezado.setForeground(new Color(20, 35, 55));
        lblEncabezado.setBounds(48, 8, 495, 36);
        add(lblEncabezado);

        JLabel lblSim = new JLabel("Código Barras (1001-1006 + Enter):");
        lblSim.setFont(new Font("Arial", Font.BOLD, 12));
        lblSim.setForeground(new Color(25, 40, 60));
        lblSim.setBounds(52, 222, 235, 24);
        add(lblSim);

        txtScanner = new JTextField();
        txtScanner.setFont(new Font("Monospaced", Font.BOLD, 14));
        txtScanner.setBounds(290, 220, 250, 26);
        add(txtScanner);

        String[] columnas = {"Producto", "Cant", "Precio"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaProductos = new JTable(modeloTabla);
        tablaProductos.setRowHeight(34);
        tablaProductos.setFont(new Font("Arial", Font.BOLD, 15));
        tablaProductos.setSelectionBackground(new Color(105, 122, 142));
        tablaProductos.setSelectionForeground(Color.WHITE);
        tablaProductos.setShowGrid(false);
        tablaProductos.setOpaque(false);
        ((DefaultTableCellRenderer) tablaProductos.getDefaultRenderer(Object.class)).setOpaque(false);
        tablaProductos.getTableHeader().setVisible(false);

        tablaProductos.getColumnModel().getColumn(0).setPreferredWidth(330);
        tablaProductos.getColumnModel().getColumn(1).setPreferredWidth(55);
        tablaProductos.getColumnModel().getColumn(2).setPreferredWidth(105);

        JScrollPane scroll = new JScrollPane(tablaProductos);
        scroll.setBounds(48, 252, 495, 318);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(165, 180, 195)));
        add(scroll);

        btnSubir = new BotonPOS("▲", true);
        btnSubir.setFont(new Font("Arial", Font.BOLD, 14));
        btnSubir.setBounds(56, 584, 48, 48);
        add(btnSubir);

        btnBajar = new BotonPOS("▼", true);
        btnBajar.setFont(new Font("Arial", Font.BOLD, 14));
        btnBajar.setBounds(112, 584, 48, 48);
        add(btnBajar);

        JLabel lblTotalText = new JLabel("Total");
        lblTotalText.setFont(new Font("Monospaced", Font.BOLD, 28));
        lblTotalText.setForeground(new Color(25, 35, 45));
        lblTotalText.setBounds(215, 588, 110, 40);
        add(lblTotalText);

        lblTotalMonto = new JLabel("$ 0", SwingConstants.RIGHT);
        lblTotalMonto.setFont(new Font("Monospaced", Font.BOLD, 30));
        lblTotalMonto.setForeground(new Color(15, 25, 35));
        lblTotalMonto.setBounds(330, 588, 205, 40);
        add(lblTotalMonto);

        btnCupon = new BotonPOS("Cupón", false);
        btnCupon.setBounds(585, 200, 180, 108);
        add(btnCupon);

        btnCantidad = new BotonPOS("Cantidad", false);
        btnCantidad.setBounds(785, 200, 180, 108);
        add(btnCantidad);

        btnBuscarArtic = new BotonPOS("Buscar\nartíc", false);
        btnBuscarArtic.setBounds(585, 325, 180, 108);
        add(btnBuscarArtic);

        btnTarjetaSocio = new BotonPOS("Tarjeta de\nSocio /\nVenta\nPersonal", false);
        btnTarjetaSocio.setFont(new Font("Arial", Font.BOLD, 15));
        btnTarjetaSocio.setBounds(785, 325, 180, 108);
        add(btnTarjetaSocio);

        btnPagar = new BotonPOS("Pagar", false);
        btnPagar.setFont(new Font("Arial", Font.BOLD, 28));
        btnPagar.setBounds(585, 455, 380, 115);
        add(btnPagar);

        btnVolumen = new BotonPOS("VOL", true);
        btnVolumen.setFont(new Font("Arial", Font.BOLD, 15));
        btnVolumen.setBounds(285, 668, 85, 48);
        add(btnVolumen);

        btnIdioma = new BotonPOS("English", false);
        btnIdioma.setBounds(395, 668, 165, 48);
        add(btnIdioma);

        btnAnular = new BotonPOS("Anular artículos", true);
        btnAnular.setFont(new Font("Arial", Font.BOLD, 15));
        btnAnular.setBounds(585, 668, 180, 48);
        add(btnAnular);

        btnAyuda = new BotonPOS("Pedir Ayuda", true);
        btnAyuda.setFont(new Font("Arial", Font.BOLD, 15));
        btnAyuda.setBounds(785, 668, 180, 48);
        add(btnAyuda);
    }

    public void actualizarTotal(int total) {
        NumberFormat nf = NumberFormat.getInstance(new Locale("es", "CL"));
        lblTotalMonto.setText("$ " + nf.format(total));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (imgKioscoEscaneo != null) {
            g2.drawImage(imgKioscoEscaneo, 48, 48, 495, 166, this);
        }

        g2.setColor(new Color(255, 255, 255, 165));
        g2.fillRect(48, 252, 495, 318);

        g2.fillRoundRect(48, 578, 495, 62, 12, 12);
        g2.setColor(new Color(165, 180, 195));
        g2.setStroke(new BasicStroke(1.8f));
        g2.drawRoundRect(48, 578, 495, 62, 12, 12);
    }
}
