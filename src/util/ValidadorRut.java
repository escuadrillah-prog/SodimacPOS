package util;

public class ValidadorRut {

    /**
     * Valida un RUT chileno real mediante algoritmo Modulo 11.
     */
    public static boolean esRutValido(String rutRaw) {
        if (rutRaw == null) return false;
        String rut = rutRaw.replace(".", "").replace("-", "").trim().toUpperCase();
        if (rut.length() < 8 || rut.length() > 9) return false;

        String cuerpo = rut.substring(0, rut.length() - 1);
        char dvIngresado = rut.charAt(rut.length() - 1);

        int numero;
        try {
            numero = Integer.parseInt(cuerpo);
        } catch (NumberFormatException e) {
            return false;
        }

        int m = 0;
        int s = 1;
        for (; numero != 0; numero /= 10) {
            s = (s + numero % 10 * (9 - m++ % 6)) % 11;
        }
        char dvEsperado = (char) (s != 0 ? s + 47 : 75);
        return dvIngresado == dvEsperado;
    }

    /**
     * Formatea un RUT limpio a formato estandar XX.XXX.XXX-Y
     */
    public static String formatearRut(String rutRaw) {
        if (rutRaw == null || rutRaw.trim().isEmpty()) return "";
        String rut = rutRaw.replace(".", "").replace("-", "").trim().toUpperCase();
        if (rut.length() < 2) return rut;
        String cuerpo = rut.substring(0, rut.length() - 1);
        String dv = rut.substring(rut.length() - 1);
        StringBuilder sb = new StringBuilder();
        int contador = 0;
        for (int i = cuerpo.length() - 1; i >= 0; i--) {
            sb.insert(0, cuerpo.charAt(i));
            contador++;
            if (contador == 3 && i != 0) {
                sb.insert(0, ".");
                contador = 0;
            }
        }
        return sb.toString() + "-" + dv;
    }
}
