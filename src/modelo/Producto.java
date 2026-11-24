package modelo;

public class Producto {
    private String codigo;
    private String nombre;
    private int precio;

    public Producto(String codigo, String nombre, int precio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public int getPrecio() { return precio; }
}
