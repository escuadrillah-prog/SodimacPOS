package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.JOptionPane;
import modelo.ItemVenta;
import modelo.Producto;
import modelo.ProductoDAO;
import util.SintetizadorVoz;
import vista.VentanaPrincipal;

public class ControladorPOS implements ActionListener {
    private VentanaPrincipal vista;
    private ProductoDAO dao;
    private List<ItemVenta> carrito;
    private int descuentoAplicado = 0;
    private int montoPagado = 0;
    private String socioActivo = null;
    private String metodoSeleccionado = "";

    public ControladorPOS(VentanaPrincipal vista, ProductoDAO dao) {
        this.vista = vista;
        this.dao = dao;
        this.carrito = new ArrayList<ItemVenta>();

        // 1. Bienvenida
        vista.vistaBienvenida.btnIniciarEscaneo.addActionListener(this);
        vista.vistaBienvenida.btnOtrosServicios.addActionListener(this);
        vista.vistaBienvenida.btnIdioma.addActionListener(this);
        vista.vistaBienvenida.btnAyuda.addActionListener(this);
        vista.vistaBienvenida.btnVolumen.addActionListener(this);

        // 2. Escaneo
        vista.vistaEscaneo.txtScanner.addActionListener(this);
        vista.vistaEscaneo.btnBuscarArtic.addActionListener(this);
        vista.vistaEscaneo.btnCantidad.addActionListener(this);
        vista.vistaEscaneo.btnCupon.addActionListener(this);
        vista.vistaEscaneo.btnTarjetaSocio.addActionListener(this);
        vista.vistaEscaneo.btnAnular.addActionListener(this);
        vista.vistaEscaneo.btnSubir.addActionListener(this);
        vista.vistaEscaneo.btnBajar.addActionListener(this);
        vista.vistaEscaneo.btnPagar.addActionListener(this);
        vista.vistaEscaneo.btnAyuda.addActionListener(this);
        vista.vistaEscaneo.btnVolumen.addActionListener(this);

        // 3. Embolsar
        vista.vistaEmbolsar.btnConfirmarBolsa.addActionListener(this);

        // 4. RUT Socio (teclado numerico)
        for (int i = 0; i <= 9; i++) {
            vista.vistaRutSocio.botonesNum[i].addActionListener(this);
        }
        vista.vistaRutSocio.btnK.addActionListener(this);
        vista.vistaRutSocio.btnBorrar.addActionListener(this);
        vista.vistaRutSocio.btnIngresar.addActionListener(this);
        vista.vistaRutSocio.btnRegresar.addActionListener(this);

        // 5. Seleccion forma de pago (6 iconos)
        vista.vistaSeleccionPago.btnFpay.addActionListener(this);
        vista.vistaSeleccionPago.btnCmr.addActionListener(this);
        vista.vistaSeleccionPago.btnDebito.addActionListener(this);
        vista.vistaSeleccionPago.btnCredito.addActionListener(this);
        vista.vistaSeleccionPago.btnNotaCredito.addActionListener(this);
        vista.vistaSeleccionPago.btnOtroPago.addActionListener(this);
        vista.vistaSeleccionPago.btnRegresar.addActionListener(this);
        vista.vistaSeleccionPago.btnPedirAyuda.addActionListener(this);

        // 6. Pinpad
        vista.vistaPinpad.btnSimularTarjeta.addActionListener(this);
        vista.vistaPinpad.btnCancelarPinpad.addActionListener(this);

        // 7. Pago Efectivo
        vista.vistaPagoEfectivo.btnIngresarEfectivo.addActionListener(this);
        vista.vistaPagoEfectivo.btnCancelarPago.addActionListener(this);
        vista.vistaPagoEfectivo.btnAyuda.addActionListener(this);

        // 8. Retiro de articulos
        vista.vistaRetiroArticulos.btnFinalizarCliente.addActionListener(this);
        vista.vistaRetiroArticulos.btnAyuda.addActionListener(this);

        SintetizadorVoz.hablar("Bienvenido a Homecenter Sodimac. Escanee sus artículos o seleccione una opción.");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        // Control Volumen / Voz
        if (src == vista.vistaBienvenida.btnVolumen || src == vista.vistaEscaneo.btnVolumen) {
            SintetizadorVoz.alternarSilencio();
            String estado = SintetizadorVoz.isSilenciado() ? "MUTE" : "VOL";
            vista.vistaBienvenida.btnVolumen.setText("<html><center>" + estado + "</center></html>");
            vista.vistaEscaneo.btnVolumen.setText("<html><center>" + estado + "</center></html>");
            if (!SintetizadorVoz.isSilenciado()) {
                SintetizadorVoz.hablar("Audio activado.");
            }
            return;
        }

        // --- BIENVENIDA ---
        if (src == vista.vistaBienvenida.btnIniciarEscaneo) {
            vista.mostrarPantalla("ESCANEO");
            SintetizadorVoz.hablar("Por favor escanee sus artículos.");
        } else if (src == vista.vistaBienvenida.btnOtrosServicios) {
            SintetizadorVoz.hablar("Otros servicios disponibles.");
            JOptionPane.showMessageDialog(vista,
                    "Servicios disponibles:\n- Consulta de precios\n- Estado de cuenta CMR / Sodimac",
                    "Otros Servicios", JOptionPane.INFORMATION_MESSAGE);
        } else if (src == vista.vistaBienvenida.btnIdioma) {
            vista.vistaBienvenida.alternarIdioma();
        }

        // --- ESCANEO ---
        else if (src == vista.vistaEscaneo.txtScanner) {
            String codigo = vista.vistaEscaneo.txtScanner.getText();
            vista.vistaEscaneo.txtScanner.setText("");
            agregarProductoPorCodigo(codigo);
        } else if (src == vista.vistaEscaneo.btnBuscarArtic) {
            mostrarCatalogoBusqueda();
        } else if (src == vista.vistaEscaneo.btnCantidad) {
            int fila = vista.vistaEscaneo.tablaProductos.getSelectedRow();
            if (fila < 0) {
                JOptionPane.showMessageDialog(vista, "Seleccione un artículo de la lista.");
                return;
            }
            String input = JOptionPane.showInputDialog(vista, "Nueva cantidad:", carrito.get(fila).getCantidad());
            if (input != null) {
                try {
                    int cant = Integer.parseInt(input);
                    if (cant > 0) {
                        carrito.get(fila).setCantidad(cant);
                        refrescarTabla();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(vista, "Ingrese un número válido.");
                }
            }
        } else if (src == vista.vistaEscaneo.btnCupon) {
            String cupon = JOptionPane.showInputDialog(vista, "Ingrese código de cupón (Ej: SODIMAC1000):");
            if (cupon != null && !cupon.isEmpty()) {
                int desc = dao.validarCupon(cupon);
                if (desc > 0) {
                    descuentoAplicado = desc;
                    refrescarTabla();
                    SintetizadorVoz.hablar("Cupón de descuento aplicado.");
                    JOptionPane.showMessageDialog(vista, "Cupón aplicado: -$" + desc);
                } else {
                    JOptionPane.showMessageDialog(vista, "Cupón inválido.");
                }
            }
        } else if (src == vista.vistaEscaneo.btnTarjetaSocio) {
            vista.vistaRutSocio.txtRut.setText("");
            vista.mostrarPantalla("RUT_SOCIO");
            SintetizadorVoz.hablar("Ingrese su RUT y acumule CMR puntos.");
        } else if (src == vista.vistaEscaneo.btnAnular) {
            int fila = vista.vistaEscaneo.tablaProductos.getSelectedRow();
            if (fila < 0) {
                JOptionPane.showMessageDialog(vista, "Seleccione el artículo a anular.");
                return;
            }
            carrito.remove(fila);
            refrescarTabla();
            SintetizadorVoz.hablar("Artículo anulado.");
        } else if (src == vista.vistaEscaneo.btnSubir) {
            int fila = vista.vistaEscaneo.tablaProductos.getSelectedRow();
            if (fila > 0) vista.vistaEscaneo.tablaProductos.setRowSelectionInterval(fila - 1, fila - 1);
        } else if (src == vista.vistaEscaneo.btnBajar) {
            int fila = vista.vistaEscaneo.tablaProductos.getSelectedRow();
            if (fila >= 0 && fila < carrito.size() - 1) {
                vista.vistaEscaneo.tablaProductos.setRowSelectionInterval(fila + 1, fila + 1);
            }
        } else if (src == vista.vistaEscaneo.btnPagar) {
            int total = calcularTotalFinal();
            if (total <= 0) {
                JOptionPane.showMessageDialog(vista, "Escanee al menos un artículo antes de pagar.");
                return;
            }
            montoPagado = 0;
            vista.vistaSeleccionPago.actualizarMontos(total, montoPagado);
            vista.mostrarPantalla("SELECCION_PAGO");
            SintetizadorVoz.hablar("Elija forma de pago a utilizar.");
        }

        // --- EMBOLSAR ---
        else if (src == vista.vistaEmbolsar.btnConfirmarBolsa) {
            vista.mostrarPantalla("ESCANEO");
        }

        // --- TECLADO RUT SOCIO ---
        else if (src == vista.vistaRutSocio.btnK) {
            vista.vistaRutSocio.txtRut.setText(vista.vistaRutSocio.txtRut.getText() + "K");
        } else if (src == vista.vistaRutSocio.btnBorrar) {
            String t = vista.vistaRutSocio.txtRut.getText();
            if (!t.isEmpty()) {
                vista.vistaRutSocio.txtRut.setText(t.substring(0, t.length() - 1));
            }
        } else if (src == vista.vistaRutSocio.btnIngresar) {
            String rut = vista.vistaRutSocio.txtRut.getText().trim();
            if (rut.length() >= 7) {
                socioActivo = rut;
                SintetizadorVoz.hablar("RUT registrado correctamente.");
                JOptionPane.showMessageDialog(vista, "Socio CMR registrado: " + socioActivo);
                vista.mostrarPantalla("ESCANEO");
            } else {
                JOptionPane.showMessageDialog(vista, "Ingrese un RUT válido.");
            }
        } else if (src == vista.vistaRutSocio.btnRegresar) {
            vista.mostrarPantalla("ESCANEO");
        }

        // --- SELECCION FORMA DE PAGO (6 ICONOS) ---
        else if (src == vista.vistaSeleccionPago.btnFpay) {
            abrirPinpad("QR");
        } else if (src == vista.vistaSeleccionPago.btnCmr) {
            abrirPinpad("CMR Falabella");
        } else if (src == vista.vistaSeleccionPago.btnDebito) {
            abrirPinpad("DÉBITO Redcompra");
        } else if (src == vista.vistaSeleccionPago.btnCredito) {
            abrirPinpad("CRÉDITO");
        } else if (src == vista.vistaSeleccionPago.btnNotaCredito) {
            abrirPinpad("Nota de Crédito");
        } else if (src == vista.vistaSeleccionPago.btnOtroPago) {
            int total = calcularTotalFinal();
            vista.vistaPagoEfectivo.actualizarMontos(total - montoPagado, montoPagado);
            vista.mostrarPantalla("EFECTIVO");
            SintetizadorVoz.hablar("Por favor introduzca el efectivo.");
        } else if (src == vista.vistaSeleccionPago.btnRegresar) {
            vista.mostrarPantalla("ESCANEO");
        }

        // --- PINPAD ---
        else if (src == vista.vistaPinpad.btnSimularTarjeta) {
            completarCompraYRetirar("Pago aprobado con " + metodoSeleccionado);
        } else if (src == vista.vistaPinpad.btnCancelarPinpad) {
            vista.mostrarPantalla("SELECCION_PAGO");
        }

        // --- EFECTIVO ---
        else if (src == vista.vistaPagoEfectivo.btnIngresarEfectivo) {
            montoPagado += 5000;
            int total = calcularTotalFinal();
            int saldo = total - montoPagado;
            vista.vistaPagoEfectivo.actualizarMontos(saldo, montoPagado);
            if (saldo <= 0) {
                int vuelto = Math.abs(saldo);
                completarCompraYRetirar("Pago en Efectivo aprobado (Vuelto: $" + vuelto + ")");
            }
        } else if (src == vista.vistaPagoEfectivo.btnCancelarPago) {
            vista.mostrarPantalla("SELECCION_PAGO");
        }

        // --- RETIRO FINAL ---
        else if (src == vista.vistaRetiroArticulos.btnFinalizarCliente) {
            reiniciarSistema();
        }

        // --- PEDIR AYUDA ---
        else if (src == vista.vistaBienvenida.btnAyuda
                || src == vista.vistaEscaneo.btnAyuda
                || src == vista.vistaSeleccionPago.btnPedirAyuda
                || src == vista.vistaPagoEfectivo.btnAyuda
                || src == vista.vistaRetiroArticulos.btnAyuda) {
            SintetizadorVoz.hablar("Un asistente viene en camino a ayudarle.");
            JOptionPane.showMessageDialog(vista,
                    "Un asistente de tienda se dirige a su caja.",
                    "Pedir Ayuda", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Verificar botones numericos 0-9 del RUT
            for (int i = 0; i <= 9; i++) {
                if (src == vista.vistaRutSocio.botonesNum[i]) {
                    vista.vistaRutSocio.txtRut.setText(vista.vistaRutSocio.txtRut.getText() + i);
                    return;
                }
            }
        }
    }

    private void abrirPinpad(String metodo) {
        this.metodoSeleccionado = metodo;
        int total = calcularTotalFinal();
        vista.vistaPinpad.actualizarMontos(total - montoPagado, montoPagado, metodo);
        vista.mostrarPantalla("PINPAD");
        if ("QR".equals(metodo)) {
            SintetizadorVoz.hablar("Por favor escanee el código QR con su teléfono.");
        } else {
            SintetizadorVoz.hablar("Por favor deslice o inserte su tarjeta en el pinpad.");
        }
    }

    private void agregarProductoPorCodigo(String codigo) {
        Producto p = dao.buscarPorCodigo(codigo);
        if (p == null) {
            SintetizadorVoz.hablar("Código no encontrado.");
            JOptionPane.showMessageDialog(vista, "Producto no encontrado: " + codigo);
            return;
        }
        boolean encontrado = false;
        for (ItemVenta item : carrito) {
            if (item.getProducto().getCodigo().equals(p.getCodigo())) {
                item.setCantidad(item.getCantidad() + 1);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            carrito.add(new ItemVenta(p, 1));
        }
        refrescarTabla();

        // Mostrar pantalla "Embolsar articulo" como en el flujo real
        vista.vistaEmbolsar.setDatosProducto(p.getNombre(), p.getPrecio());
        vista.mostrarPantalla("EMBOLSAR");
        SintetizadorVoz.hablar("Coloque el artículo en el área de embolsado.");
    }

    private void mostrarCatalogoBusqueda() {
        List<Producto> lista = dao.listarTodos();
        String[] opciones = new String[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            Producto p = lista.get(i);
            opciones[i] = p.getCodigo() + " - " + p.getNombre() + " ($" + p.getPrecio() + ")";
        }
        String sel = (String) JOptionPane.showInputDialog(
                vista, "Seleccione un producto:", "Buscar Artículo",
                JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
        if (sel != null) {
            agregarProductoPorCodigo(sel.split(" - ")[0]);
        }
    }

    private int calcularTotalFinal() {
        int suma = 0;
        for (ItemVenta item : carrito) {
            suma += item.getSubtotal();
        }
        return Math.max(0, suma - descuentoAplicado);
    }

    private void refrescarTabla() {
        vista.vistaEscaneo.modeloTabla.setRowCount(0);
        NumberFormat nf = NumberFormat.getInstance(new Locale("es", "CL"));
        for (ItemVenta item : carrito) {
            vista.vistaEscaneo.modeloTabla.addRow(new Object[]{
                item.getProducto().getNombre(),
                "x" + item.getCantidad(),
                nf.format(item.getSubtotal())
            });
        }
        if (!carrito.isEmpty()) {
            int ult = carrito.size() - 1;
            vista.vistaEscaneo.tablaProductos.setRowSelectionInterval(ult, ult);
        }
        vista.vistaEscaneo.actualizarTotal(calcularTotalFinal());
    }

    private void completarCompraYRetirar(String detallePago) {
        String socio = (socioActivo != null) ? "<br>Socio CMR: " + socioActivo : "";
        vista.vistaRetiroArticulos.lblDetalleBoleta.setText(
                "<html>" + detallePago + socio + "<br>Por favor retire su boleta y sus artículos.</html>");
        vista.mostrarPantalla("RETIRO");
        SintetizadorVoz.hablar("Pago aprobado. Por favor tome sus artículos y retire su boleta.");
    }

    private void reiniciarSistema() {
        carrito.clear();
        descuentoAplicado = 0;
        montoPagado = 0;
        socioActivo = null;
        refrescarTabla();
        vista.mostrarPantalla("BIENVENIDA");
        SintetizadorVoz.hablar("Bienvenido a Homecenter Sodimac.");
    }
}
