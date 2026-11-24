package util;

public class LocucionesPOS {

    public static void avisoSoloTarjetas() {
        SintetizadorVoz.hablar("Esta máquina solo acepta tarjetas de crédito y débito en este momento. ¿Desea continuar?");
    }

    public static void precioProducto(int precio) {
        SintetizadorVoz.hablar(precio + " pesos");
    }

    public static void solicitarRut() {
        SintetizadorVoz.hablar("Ingrese su RUT");
    }

    public static void seleccionarPago() {
        SintetizadorVoz.hablar("Seleccione el tipo de pago");
    }

    public static void instruccionPinpadDeslizar() {
        SintetizadorVoz.hablar("Por favor deslice su tarjeta en el pinpad");
    }

    public static void instruccionPinpadClave() {
        SintetizadorVoz.hablar("Por favor, confirme monto a pagar y luego ingrese su clave en el pinpad");
    }

    public static void finalizarCompra() {
        SintetizadorVoz.hablar("Por favor tome sus artículos. Gracias por comprar en Sodimac.");
    }
}
