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
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * Pantalla "Embolsar articulo - Coloque el articulo en el area de embolsado"
 */
public class VistaEmbolsar extends PanelFondo {
    public JLabel lblProductoNombre;
    public JLabel lblProductoPrecio;
    public BotonPOS btnConfirmarBolsa;
    private Image imgEmbolsar;

    public VistaEmbolsar() {
        super(true); // sodimac_chico.png
        imgEmbolsar = cargarImagen("/img/kiosco_embolsar.png");

        JLabel lblTitulo = new JLabel("Embolsar artículo");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(20, 35, 55));
        lblTitulo.setBounds(130, 18, 450, 36);
        add(lblTitulo);

        JLabel lblSub = new JLabel("Coloque el artículo en el área de embolsado.");
        lblSub.setFont(new Font("Arial", Font.PLAIN, 19));
        lblSub.setForeground(new Color(20, 35, 55));
        lblSub.setBounds(130, 56, 550, 28);
        add(lblSub);

        lblProductoNombre = new JLabel("PRODUCTO ESCANEADO", SwingConstants.LEFT);
        lblProductoNombre.setFont(new Font("Arial", Font.BOLD, 22));
        lblProductoNombre.setForeground(new Color(35, 45, 60));
        lblProductoNombre.setBounds(215, 535, 430, 35);
        add(lblProductoNombre);

        lblProductoPrecio = new JLabel("0", SwingConstants.RIGHT);
        lblProductoPrecio.setFont(new Font("Arial", Font.BOLD, 24));
        lblProductoPrecio.setForeground(new Color(35, 45, 60));
        lblProductoPrecio.setBounds(650, 535, 140, 35);
        add(lblProductoPrecio);

        btnConfirmarBolsa = new BotonPOS("Artículo Embolsado (Continuar)", false);
        btnConfirmarBolsa.setBounds(330, 658, 360, 55);
        add(btnConfirmarBolsa);
    }

    public void setDatosProducto(String nombre, int precio) {
        NumberFormat nf = NumberFormat.getInstance(new Locale("es", "CL"));
        lblProductoNombre.setText(nombre);
        lblProductoPrecio.setText(nf.format(precio));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(255, 255, 255, 130));
        g2.fillRoundRect(110, 115, 800, 470, 26, 26);
        g2.setColor(new Color(145, 170, 195));
        g2.setStroke(new BasicStroke(2f));
        g2.drawRoundRect(110, 115, 800, 470, 26, 26);

        if (imgEmbolsar != null) {
            g2.drawImage(imgEmbolsar, 225, 155, 570, 355, this);
        }
    }
}
