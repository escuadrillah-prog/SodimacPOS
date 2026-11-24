package main;

import controlador.ControladorPOS;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import modelo.ProductoDAO;
import vista.VentanaPrincipal;

public class Main {
    public static void main(String[] args) {
        System.setProperty("javax.accessibility.assistive_technologies", "com.sun.java.accessibility.AccessBridge");

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                VentanaPrincipal vista = new VentanaPrincipal();

                if (!vista.isDisplayable()) {
                    vista.setUndecorated(true);
                }

                // Resolución fija exacta 1024x768 centrada en pantalla
                Dimension resolucionFija = new Dimension(1024, 768);
                vista.setSize(resolucionFija);
                vista.setPreferredSize(resolucionFija);
                vista.setMinimumSize(resolucionFija);
                vista.setMaximumSize(resolucionFija);
                vista.setResizable(false);
                vista.setLocationRelativeTo(null);

                // Atajo de administrador para cerrar el POS: Ctrl + Shift + Q
                vista.getRootPane().registerKeyboardAction(
                    new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.exit(0);
                        }
                    },
                    KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK),
                    JComponent.WHEN_IN_FOCUSED_WINDOW
                );

                ProductoDAO dao = new ProductoDAO();
                new ControladorPOS(vista, dao);
                vista.setVisible(true);
            }
        });
    }
}
