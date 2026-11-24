package vista;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PanelFondo extends JPanel {
    private Image imagenFondo;
    private Image imagenLogoGrande;
    private Image imagenLogoChico;
    private boolean usarLogoChico;

    public PanelFondo() {
        this(false);
    }

    public PanelFondo(boolean usarLogoChico) {
        setLayout(null);
        this.usarLogoChico = usarLogoChico;
        this.imagenFondo = cargarImagen("/img/background.png");
        this.imagenLogoGrande = cargarImagen("/img/logo_sodimac.png");
        this.imagenLogoChico = cargarImagen("/img/sodimac_chico.png");
    }

    protected Image cargarImagen(String ruta) {
        URL url = getClass().getResource(ruta);
        if (url != null) {
            return new ImageIcon(url).getImage();
        }
        System.err.println("No se encontro la imagen: " + ruta);
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagenFondo != null) {
            g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
        }
        if (usarLogoChico) {
            if (imagenLogoChico != null) {
                g.drawImage(imagenLogoChico, 745, 22, 210, 62, this);
            }
        } else {
            if (imagenLogoGrande != null) {
                g.drawImage(imagenLogoGrande, 675, 10, 240, 145, this);
            }
        }
    }
}
