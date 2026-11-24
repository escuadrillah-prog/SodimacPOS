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
 * Pantalla "Introducir el efectivo" (OTRO PAGO)
 */
public class VistaPagoEfectivo extends PanelFondo {
    public BotonPOS btnIngresarEfectivo;
    public BotonPOS btnCancelarPago;
    public BotonPOS btnAyuda;
    public JLabel lblMontoPagar;
    public JLabel lblMontoPagado;

    private Image imgKioscoPago;

    public VistaPagoEfectivo() {
        super(true); // sodimac_chico.png
        imgKioscoPago = cargarImagen("/img/kiosco_pago.png");

        JLabel lblTitulo = new JLabel("Introducir el efectivo");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(20, 35, 55));
        lblTitulo.setBounds(65, 22, 500, 36);
        add(lblTitulo);

        JLabel lblSub = new JLabel("Introducir efectivo en la ranura iluminada");
        lblSub.setFont(new Font("Arial", Font.PLAIN, 17));
        lblSub.setForeground(Color.WHITE);
        lblSub.setBounds(67, 60, 480, 26);
        add(lblSub);

        btnIngresarEfectivo = new BotonPOS("Ingresar Billete\n($ 5.000)", false);
        btnIngresarEfectivo.setFont(new Font("Arial", Font.BOLD, 19));
        btnIngresarEfectivo.setBounds(395, 435, 230, 78);
        add(btnIngresarEfectivo);

        lblMontoPagar = new JLabel("Monto a pagar: $ 0", SwingConstants.CENTER);
        lblMontoPagar.setFont(new Font("Arial", Font.BOLD, 26));
        lblMontoPagar.setForeground(new Color(25, 35, 45));
        lblMontoPagar.setBounds(250, 530, 520, 35);
        add(lblMontoPagar);

        lblMontoPagado = new JLabel("Monto pagado: $ 0", SwingConstants.CENTER);
        lblMontoPagado.setFont(new Font("Arial", Font.PLAIN, 18));
        lblMontoPagado.setForeground(new Color(55, 65, 75));
        lblMontoPagado.setBounds(250, 568, 520, 28);
        add(lblMontoPagado);

        btnCancelarPago = new BotonPOS("Cancelar pago", false);
        btnCancelarPago.setBounds(540, 665, 195, 50);
        add(btnCancelarPago);

        btnAyuda = new BotonPOS("Ayuda", true);
        btnAyuda.setBounds(760, 665, 185, 50);
        add(btnAyuda);
    }

    public void actualizarMontos(int saldoPendiente, int pagado) {
        NumberFormat nf = NumberFormat.getInstance(new Locale("es", "CL"));
        lblMontoPagar.setText("Monto a pagar: $ " + nf.format(Math.max(0, saldoPendiente)));
        lblMontoPagado.setText("Monto pagado: $ " + nf.format(pagado));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(255, 255, 255, 145));
        g2.fillRoundRect(160, 130, 700, 490, 26, 26);
        g2.setColor(new Color(145, 170, 195));
        g2.setStroke(new BasicStroke(2f));
        g2.drawRoundRect(160, 130, 700, 490, 26, 26);

        if (imgKioscoPago != null) {
            g2.drawImage(imgKioscoPago, 310, 155, 400, 260, this);
        }
    }
}
