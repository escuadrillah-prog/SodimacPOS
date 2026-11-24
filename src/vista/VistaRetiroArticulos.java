package vista;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JLabel;

/**
 * Pantalla final "Por favor tome sus articulos"
 */
public class VistaRetiroArticulos extends PanelFondo {
    public BotonPOS btnFinalizarCliente;
    public BotonPOS btnAyuda;
    public JLabel lblDetalleBoleta;

    public VistaRetiroArticulos() {
        super(true); // sodimac_chico.png

        JLabel lblTitulo = new JLabel("Por favor tome sus artículos");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(20, 35, 55));
        lblTitulo.setBounds(130, 18, 520, 38);
        add(lblTitulo);

        JLabel lblCentral = new JLabel("Por favor tome sus artículos");
        lblCentral.setFont(new Font("Arial", Font.PLAIN, 26));
        lblCentral.setForeground(new Color(35, 45, 60));
        lblCentral.setBounds(225, 185, 520, 40);
        add(lblCentral);

        lblDetalleBoleta = new JLabel("<html>Retire su boleta y sus productos del área de embolsado.<br>¡Gracias por preferir Homecenter Sodimac!</html>");
        lblDetalleBoleta.setFont(new Font("Arial", Font.PLAIN, 17));
        lblDetalleBoleta.setForeground(new Color(70, 85, 105));
        lblDetalleBoleta.setBounds(225, 250, 560, 70);
        add(lblDetalleBoleta);

        btnFinalizarCliente = new BotonPOS("Finalizar / Nuevo Cliente", false);
        btnFinalizarCliente.setBounds(350, 658, 320, 55);
        add(btnFinalizarCliente);

        btnAyuda = new BotonPOS("Ayuda", true);
        btnAyuda.setBounds(775, 658, 190, 55);
        add(btnAyuda);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(255, 255, 255, 110));
        g2.fillRoundRect(115, 95, 795, 485, 24, 24);
        g2.setColor(new Color(145, 170, 195));
        g2.setStroke(new BasicStroke(2f));
        g2.drawRoundRect(115, 95, 795, 485, 24, 24);

        g2.setColor(new Color(248, 251, 255));
        g2.fillRect(205, 145, 610, 320);
        g2.setColor(new Color(180, 195, 210));
        g2.drawRect(205, 145, 610, 320);
    }
}
