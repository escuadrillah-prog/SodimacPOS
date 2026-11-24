package vista;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * Pantalla "Use PINPAD - Por favor deslice su tarjeta en el pinpad"
 */
public class VistaPinpad extends PanelFondo {
    public JLabel lblMensajePinpad;
    public JLabel lblMontoPagar;
    public JLabel lblMontoPagado;
    public BotonPOS btnSimularTarjeta;
    public BotonPOS btnCancelarPinpad;

    public VistaPinpad() {
        super(true); // sodimac_chico.png

        JLabel lblTitulo = new JLabel("Use PINPAD");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(20, 35, 55));
        lblTitulo.setBounds(115, 18, 400, 38);
        add(lblTitulo);

        lblMensajePinpad = new JLabel("<html>Por favor deslice o inserte su tarjeta<br>en el pinpad</html>");
        lblMensajePinpad.setFont(new Font("Arial", Font.PLAIN, 24));
        lblMensajePinpad.setForeground(new Color(30, 42, 58));
        lblMensajePinpad.setBounds(215, 145, 560, 80);
        add(lblMensajePinpad);

        lblMontoPagar = new JLabel("Monto a pagar: $ 0", SwingConstants.CENTER);
        lblMontoPagar.setFont(new Font("Arial", Font.BOLD, 26));
        lblMontoPagar.setForeground(new Color(25, 35, 45));
        lblMontoPagar.setBounds(250, 525, 520, 35);
        add(lblMontoPagar);

        lblMontoPagado = new JLabel("Monto pagado: $ 0", SwingConstants.CENTER);
        lblMontoPagado.setFont(new Font("Arial", Font.PLAIN, 18));
        lblMontoPagado.setForeground(new Color(55, 65, 75));
        lblMontoPagado.setBounds(250, 562, 520, 28);
        add(lblMontoPagado);

        btnSimularTarjeta = new BotonPOS("Simular Aprobación Pinpad", false);
        btnSimularTarjeta.setBounds(260, 658, 300, 55);
        add(btnSimularTarjeta);

        btnCancelarPinpad = new BotonPOS("Cancelar", true);
        btnCancelarPinpad.setBounds(585, 658, 180, 55);
        add(btnCancelarPinpad);
    }

    public void actualizarMontos(int pendiente, int pagado, String metodo) {
        NumberFormat nf = NumberFormat.getInstance(new Locale("es", "CL"));
        lblMontoPagar.setText("Monto a pagar: $ " + nf.format(Math.max(0, pendiente)));
        lblMontoPagado.setText("Monto pagado: $ " + nf.format(pagado));
        if ("QR".equals(metodo)) {
            lblMensajePinpad.setText("<html>Escanee el código QR con su aplicación Fpay<br>para autorizar el pago</html>");
        } else {
            lblMensajePinpad.setText("<html>Por favor deslice o inserte su tarjeta (" + metodo + ")<br>en el pinpad</html>");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Marco exterior
        g2.setColor(new Color(255, 255, 255, 110));
        g2.fillRoundRect(100, 95, 820, 415, 24, 24);
        g2.setColor(new Color(145, 170, 195));
        g2.setStroke(new BasicStroke(2f));
        g2.drawRoundRect(100, 95, 820, 415, 24, 24);

        // Cuadro blanco central del mensaje
        g2.setColor(new Color(248, 251, 255));
        g2.fillRect(195, 130, 620, 315);
        g2.setColor(new Color(180, 195, 210));
        g2.drawRect(195, 130, 620, 315);

        // Indicadores de progreso abajo (cuadro lleno + cuadro vacio)
        g2.setColor(new Color(70, 85, 105));
        g2.fillRect(485, 410, 18, 18);
        g2.drawRect(512, 410, 18, 18);
    }
}
