package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JLabel;

public class VistaBienvenida extends PanelFondo {
    public BotonPOS btnIniciarEscaneo;
    public BotonPOS btnOtrosServicios;
    public BotonPOS btnIdioma;
    public BotonPOS btnAyuda;
    public BotonPOS btnVolumen;

    public JLabel lblTitulo;
    public JLabel lblSubtitulo;
    private Image imgKiosco;
    private boolean enIngles = false;

    public VistaBienvenida() {
        super(false); // Logo grande
        imgKiosco = cargarImagen("/img/kiosco_bienvenida.png");

        lblTitulo = new JLabel("¡Bienvenido!");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 34));
        lblTitulo.setForeground(new Color(14, 72, 138));
        lblTitulo.setBounds(585, 200, 380, 45);
        add(lblTitulo);

        lblSubtitulo = new JLabel("<html>Escanee sus artículos o<br>seleccione un idioma</html>");
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 22));
        lblSubtitulo.setForeground(new Color(35, 45, 55));
        lblSubtitulo.setBounds(585, 255, 380, 65);
        add(lblSubtitulo);

        btnIniciarEscaneo = new BotonPOS("Escanear Artículos", false);
        btnIniciarEscaneo.setFont(new Font("Arial", Font.BOLD, 22));
        btnIniciarEscaneo.setBounds(580, 345, 385, 85);
        add(btnIniciarEscaneo);

        btnOtrosServicios = new BotonPOS("Otros Servicios", false);
        btnOtrosServicios.setFont(new Font("Arial", Font.BOLD, 22));
        btnOtrosServicios.setBounds(580, 448, 385, 85);
        add(btnOtrosServicios);

        btnIdioma = new BotonPOS("English", false);
        btnIdioma.setFont(new Font("Arial", Font.BOLD, 22));
        btnIdioma.setBounds(580, 550, 385, 85);
        add(btnIdioma);

        btnVolumen = new BotonPOS("VOL", true);
        btnVolumen.setFont(new Font("Arial", Font.BOLD, 15));
        btnVolumen.setBounds(310, 672, 85, 48);
        add(btnVolumen);

        JLabel lblControl = new JLabel("<html>Las compras se controlan para<br>asegurar un mejor servicio.</html>");
        lblControl.setFont(new Font("Arial", Font.PLAIN, 12));
        lblControl.setForeground(new Color(55, 65, 75));
        lblControl.setBounds(405, 672, 230, 48);
        add(lblControl);

        btnAyuda = new BotonPOS("Ayuda", true);
        btnAyuda.setBounds(775, 672, 190, 50);
        add(btnAyuda);
    }

    public void alternarIdioma() {
        enIngles = !enIngles;
        if (enIngles) {
            lblTitulo.setText("Welcome!");
            lblSubtitulo.setText("<html>Scan your items or<br>select a language</html>");
            btnIniciarEscaneo.setText("<html><center>Scan Items</center></html>");
            btnOtrosServicios.setText("<html><center>Other Services</center></html>");
            btnIdioma.setText("<html><center>Español</center></html>");
            btnAyuda.setText("<html><center>Help</center></html>");
        } else {
            lblTitulo.setText("¡Bienvenido!");
            lblSubtitulo.setText("<html>Escanee sus artículos o<br>seleccione un idioma</html>");
            btnIniciarEscaneo.setText("<html><center>Escanear Artículos</center></html>");
            btnOtrosServicios.setText("<html><center>Otros Servicios</center></html>");
            btnIdioma.setText("<html><center>English</center></html>");
            btnAyuda.setText("<html><center>Ayuda</center></html>");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imgKiosco != null) {
            g.drawImage(imgKiosco, 45, 45, 500, 595, this);
        }
    }
}
