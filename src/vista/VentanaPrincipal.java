package vista;

import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class VentanaPrincipal extends JFrame {
    private CardLayout cardLayout;
    private JPanel contenedorPrincipal;

    public VistaBienvenida vistaBienvenida;
    public VistaEscaneo vistaEscaneo;
    public VistaEmbolsar vistaEmbolsar;
    public VistaRutSocio vistaRutSocio;
    public VistaSeleccionPago vistaSeleccionPago;
    public VistaPinpad vistaPinpad;
    public VistaPagoEfectivo vistaPagoEfectivo;
    public VistaRetiroArticulos vistaRetiroArticulos;

    public VentanaPrincipal() {
        setTitle("Autoservicio POS - HOMECENTER SODIMAC");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        cardLayout = new CardLayout();
        contenedorPrincipal = new JPanel(cardLayout);

        vistaBienvenida = new VistaBienvenida();
        vistaEscaneo = new VistaEscaneo();
        vistaEmbolsar = new VistaEmbolsar();
        vistaRutSocio = new VistaRutSocio();
        vistaSeleccionPago = new VistaSeleccionPago();
        vistaPinpad = new VistaPinpad();
        vistaPagoEfectivo = new VistaPagoEfectivo();
        vistaRetiroArticulos = new VistaRetiroArticulos();

        contenedorPrincipal.add(vistaBienvenida, "BIENVENIDA");
        contenedorPrincipal.add(vistaEscaneo, "ESCANEO");
        contenedorPrincipal.add(vistaEmbolsar, "EMBOLSAR");
        contenedorPrincipal.add(vistaRutSocio, "RUT_SOCIO");
        contenedorPrincipal.add(vistaSeleccionPago, "SELECCION_PAGO");
        contenedorPrincipal.add(vistaPinpad, "PINPAD");
        contenedorPrincipal.add(vistaPagoEfectivo, "EFECTIVO");
        contenedorPrincipal.add(vistaRetiroArticulos, "RETIRO");

        setContentPane(contenedorPrincipal);
    }

    public void mostrarPantalla(String nombrePantalla) {
        cardLayout.show(contenedorPrincipal, nombrePantalla);
        if ("ESCANEO".equals(nombrePantalla)) {
            vistaEscaneo.txtScanner.requestFocusInWindow();
        }
    }
}
