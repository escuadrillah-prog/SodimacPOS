package main;

import controlador.ControladorPOS;
import javax.swing.SwingUtilities;
import modelo.ProductoDAO;
import vista.VentanaPrincipal;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                VentanaPrincipal vista = new VentanaPrincipal();
                ProductoDAO dao = new ProductoDAO();
                new ControladorPOS(vista, dao);
                vista.setVisible(true);
            }
        });
    }
}
