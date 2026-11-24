package vista;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JButton;

public class BotonPOS extends JButton {
    private Color colorSuperior;
    private Color colorInferior;
    private Color colorBordeExterior = new Color(120, 138, 156);
    private Color colorBordeInterior = new Color(235, 248, 255, 190);
    private int radio;

    public BotonPOS(String texto, boolean esGrisSecundario) {
        super("<html><center>" + texto.replace("\n", "<br>") + "</center></html>");
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setForeground(new Color(18, 32, 48));
        setFont(new Font("Arial", Font.BOLD, 18));

        if (esGrisSecundario) {
            this.colorSuperior = new Color(242, 246, 250);
            this.colorInferior = new Color(172, 186, 200);
            this.radio = 44;
        } else {
            this.colorSuperior = new Color(165, 228, 252);
            this.colorInferior = new Color(48, 148, 208);
            this.radio = 56;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        int r = Math.min(radio, h - 4);

        Color cSup = getModel().isPressed() ? colorSuperior.darker() : colorSuperior;
        Color cInf = getModel().isPressed() ? colorInferior.darker() : colorInferior;

        g2.setColor(new Color(30, 50, 70, 55));
        g2.fill(new RoundRectangle2D.Double(3, 5, w - 6, h - 6, r, r));

        GradientPaint gpPrincipal = new GradientPaint(0, 2, cSup, 0, h - 4, cInf);
        g2.setPaint(gpPrincipal);
        g2.fill(new RoundRectangle2D.Double(2, 2, w - 5, h - 6, r, r));

        GradientPaint gpBrillo = new GradientPaint(
                0, 4, new Color(255, 255, 255, 165),
                0, h * 0.52f, new Color(255, 255, 255, 15));
        g2.setPaint(gpBrillo);
        g2.fill(new RoundRectangle2D.Double(6, 4, w - 13, h * 0.48, r * 0.75, r * 0.75));

        g2.setColor(colorBordeInterior);
        g2.setStroke(new BasicStroke(1.5f));
        g2.draw(new RoundRectangle2D.Double(4, 4, w - 9, h - 10, r - 2, r - 2));

        g2.setColor(colorBordeExterior);
        g2.setStroke(new BasicStroke(2.2f));
        g2.draw(new RoundRectangle2D.Double(2, 2, w - 5, h - 6, r, r));

        g2.dispose();
        super.paintComponent(g);
    }
}
