package vista;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * Boton cuadrado con bordes redondeados, icono de pago arriba y etiqueta abajo
 * (Fpay, CMR, DEBITO, CREDITO, Nota de Credito, OTRO PAGO)
 */
public class BotonMetodoPago extends JButton {
    private Image icono;
    private String etiqueta;

    public BotonMetodoPago(String rutaIcono, String etiqueta) {
        this.etiqueta = etiqueta;
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        URL url = getClass().getResource(rutaIcono);
        if (url != null) {
            this.icono = new ImageIcon(url).getImage();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        int r = 22;

        // Sombra
        g2.setColor(new Color(25, 45, 70, 65));
        g2.fill(new RoundRectangle2D.Double(4, 6, w - 7, h - 7, r, r));

        // Fondo tarjeta blanca brillante
        Color cTop = getModel().isPressed() ? new Color(220, 232, 245) : Color.WHITE;
        Color cBot = getModel().isPressed() ? new Color(195, 215, 235) : new Color(235, 243, 250);
        g2.setPaint(new GradientPaint(0, 0, cTop, 0, h, cBot));
        g2.fill(new RoundRectangle2D.Double(2, 2, w - 6, h - 7, r, r));

        // Borde gris azulado
        g2.setColor(new Color(125, 145, 168));
        g2.setStroke(new BasicStroke(2.2f));
        g2.draw(new RoundRectangle2D.Double(2, 2, w - 6, h - 7, r, r));

        // Linea separadora sutil encima del texto
        g2.setColor(new Color(205, 218, 232));
        g2.setStroke(new BasicStroke(1.2f));
        g2.drawLine(10, h - 36, w - 12, h - 36);

        // Dibujar icono centrado arriba
        if (icono != null) {
            int iw = 136;
            int ih = 74;
            g2.drawImage(icono, (w - iw) / 2, 10, iw, ih, this);
        }

        // Dibujar texto inferior
        g2.setColor(new Color(45, 58, 75));
        g2.setFont(new Font("Arial", Font.BOLD, 14));
        FontMetrics fm = g2.getFontMetrics();
        int tx = (w - fm.stringWidth(etiqueta)) / 2;
        g2.drawString(etiqueta, tx, h - 16);

        g2.dispose();
    }
}
