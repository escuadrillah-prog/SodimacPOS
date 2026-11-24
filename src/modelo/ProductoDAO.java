package modelo;

import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    private List<Producto> catalogo;

    public ProductoDAO() {
        catalogo = new ArrayList<Producto>();
        catalogo.add(new Producto("1001", "CAJA DE HERRAM 12\" T/PANAL", 5320));
        catalogo.add(new Producto("1002", "TERMO 1,0L ACERO INOX...", 7990));
        catalogo.add(new Producto("1003", "SET DESTORNILLADORES 6 PZ", 6490));
        catalogo.add(new Producto("1004", "PINTURA LATEX BLANCO 1 GL", 14990));
        catalogo.add(new Producto("1005", "CINTA METRICA 5M REFORZADA", 3290));
        catalogo.add(new Producto("1006", "TALADRO PERCUTOR 650W", 34990));
    }

    public Producto buscarPorCodigo(String codigo) {
        if (codigo == null) return null;
        for (Producto p : catalogo) {
            if (p.getCodigo().equalsIgnoreCase(codigo.trim())) {
                return p;
            }
        }
        return null;
    }

    public List<Producto> listarTodos() {
        return catalogo;
    }

    public int validarCupon(String codigoCupon) {
        if (codigoCupon == null) return 0;
        String c = codigoCupon.trim().toUpperCase();
        if ("SODIMAC1000".equals(c) || "SOLMA1000".equals(c)) return 1000;
        if ("DESC2000".equals(c)) return 2000;
        return 0;
    }
}
