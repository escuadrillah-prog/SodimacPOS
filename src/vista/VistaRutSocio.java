package vista;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * Pantalla "Ingrese su RUT - Y acumule CMR Puntos" con teclado numerico tactil
 */
public class VistaRutSocio extends PanelFondo {
    public JTextField txtRut;
    public BotonPOS[] botonesNum = new BotonPOS[10];
    public BotonPOS btnK;
    public BotonPOS btnBorrar;
    public BotonPOS btnIngresar;
    public BotonPOS btnRegresar;

    public VistaRutSocio() {
        super(true); // sodimac_chico.png

        JLabel lblTitulo = new JLabel("Ingrese su RUT");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(20, 35, 55));
        lblTitulo.setBounds(150, 16, 400, 36);
        add(lblTitulo);

        JLabel lblSub = new JLabel("<html>Y acumule CMR Puntos. Además, podrá guardar<br>sus boletas y verlas en sodimac.com</html>");
        lblSub.setFont(new Font("Arial", Font.PLAIN, 18));
        lblSub.setForeground(new Color(25, 40, 58));
        lblSub.setBounds(150, 54, 540, 48);
        add(lblSub);

        txtRut = new JTextField();
        txtRut.setEditable(false);
        txtRut.setBackground(Color.WHITE);
        txtRut.setFont(new Font("Monospaced", Font.BOLD, 26));
        txtRut.setHorizontalAlignment(SwingConstants.LEFT);
        txtRut.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(130, 150, 170), 2),
                BorderFactory.createEmptyBorder(2, 12, 2, 12)));
        txtRut.setBounds(290, 128, 440, 46);
        add(txtRut);

        // Teclado numerico 1..9
        int startX = 290;
        int startY = 195;
        int w = 85;
        int h = 76;
        int gap = 16;

        for (int i = 1; i <= 9; i++) {
            int row = (i - 1) / 3;
            int col = (i - 1) % 3;
            botonesNum[i] = new BotonPOS(String.valueOf(i), false);
            botonesNum[i].setFont(new Font("Arial", Font.BOLD, 22));
            botonesNum[i].setBounds(startX + col * (w + gap), startY + row * (h + gap), w, h);
            add(botonesNum[i]);
        }

        // Fila inferior: 0 y K
        botonesNum[0] = new BotonPOS("0", false);
        botonesNum[0].setFont(new Font("Arial", Font.BOLD, 22));
        botonesNum[0].setBounds(startX + (w + gap), startY + 3 * (h + gap), w, h);
        add(botonesNum[0]);

        btnK = new BotonPOS("K", false);
        btnK.setFont(new Font("Arial", Font.BOLD, 22));
        btnK.setBounds(startX + 2 * (w + gap), startY + 3 * (h + gap), w, h);
        add(btnK);

        // Botones laterales Borrar e Ingresar
        btnBorrar = new BotonPOS("Borrar", true);
        btnBorrar.setFont(new Font("Arial", Font.BOLD, 18));
        btnBorrar.setBounds(605, 325, 145, 76);
        add(btnBorrar);

        btnIngresar = new BotonPOS("Ingresar", true);
        btnIngresar.setFont(new Font("Arial", Font.BOLD, 24));
        btnIngresar.setBounds(595, 420, 165, 125);
        add(btnIngresar);

        btnRegresar = new BotonPOS("Regresar", false);
        btnRegresar.setFont(new Font("Arial", Font.BOLD, 20));
        btnRegresar.setBounds(605, 658, 220, 56);
        add(btnRegresar);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(255, 255, 255, 125));
        g2.fillRoundRect(120, 110, 780, 475, 26, 26);
        g2.setColor(new Color(145, 170, 195));
        g2.setStroke(new BasicStroke(2f));
        g2.drawRoundRect(120, 110, 780, 475, 26, 26);
    }
}
