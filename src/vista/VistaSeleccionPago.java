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
 * Pantalla "Elija forma de pago a utilizar" con los 6 botones con iconos en /img/pago/
 */
public class VistaSeleccionPago extends PanelFondo {
    public BotonMetodoPago btnFpay;
    public BotonMetodoPago btnCmr;
    public BotonMetodoPago btnDebito;
    public BotonMetodoPago btnCredito;
    public BotonMetodoPago btnNotaCredito;
    public BotonMetodoPago btnOtroPago;

    public JLabel lblMontoPagar;
    public JLabel lblMontoPagado;

    public BotonPOS btnRegresar;
    public BotonPOS btnPedirAyuda;

    public VistaSeleccionPago() {
        super(true); // sodimac_chico.png

        JLabel lblTitulo = new JLabel("Elija forma de pago a utilizar");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(20, 35, 55));
        lblTitulo.setBounds(135, 18, 520, 38);
        add(lblTitulo);

        // Fila 1 de metodos de pago (4 botones)
        int yFila1 = 155;
        int wBtn = 165;
        int hBtn = 132;
        btnFpay = new BotonMetodoPago("/img/pago/fpay.png", "Pagar con QR");
        btnFpay.setBounds(145, yFila1, wBtn, hBtn);
        add(btnFpay);

        btnCmr = new BotonMetodoPago("/img/pago/cmr.png", "CMR");
        btnCmr.setBounds(335, yFila1, wBtn, hBtn);
        add(btnCmr);

        btnDebito = new BotonMetodoPago("/img/pago/debito.png", "DÉBITO");
        btnDebito.setBounds(525, yFila1, wBtn, hBtn);
        add(btnDebito);

        btnCredito = new BotonMetodoPago("/img/pago/credito.png", "CRÉDITO");
        btnCredito.setBounds(715, yFila1, wBtn, hBtn);
        add(btnCredito);

        // Fila 2 de metodos de pago (2 botones centrados)
        int yFila2 = 315;
        btnNotaCredito = new BotonMetodoPago("/img/pago/nota_credito.png", "Nota de Credito");
        btnNotaCredito.setBounds(335, yFila2, wBtn, hBtn);
        add(btnNotaCredito);

        btnOtroPago = new BotonMetodoPago("/img/pago/otro_pago.png", "OTRO PAGO");
        btnOtroPago.setBounds(525, yFila2, wBtn, hBtn);
        add(btnOtroPago);

        // Montos abajo
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

        // Botones inferiores
        btnRegresar = new BotonPOS("Regresar", false);
        btnRegresar.setFont(new Font("Arial", Font.BOLD, 20));
        btnRegresar.setBounds(575, 658, 200, 56);
        add(btnRegresar);

        btnPedirAyuda = new BotonPOS("Pedir Ayuda", false);
        btnPedirAyuda.setFont(new Font("Arial", Font.BOLD, 18));
        btnPedirAyuda.setBounds(795, 658, 190, 56);
        add(btnPedirAyuda);
    }

    public void actualizarMontos(int pendiente, int pagado) {
        NumberFormat nf = NumberFormat.getInstance(new Locale("es", "CL"));
        lblMontoPagar.setText("Monto a pagar: $ " + nf.format(Math.max(0, pendiente)));
        lblMontoPagado.setText("Monto pagado: $ " + nf.format(pagado));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(255, 255, 255, 115));
        g2.fillRoundRect(110, 115, 805, 385, 26, 26);
        g2.setColor(new Color(145, 170, 195));
        g2.setStroke(new BasicStroke(2f));
        g2.drawRoundRect(110, 115, 805, 385, 26, 26);
    }
}
