package util;

import java.io.IOException;

/**
 * Permite que el archivo JAR hable fisicamente en Windows (POSReady 7 / 7 / 10 / 11)
 * usando el motor nativo SAPI.SpVoice de Windows sin librerias externas pesadas.
 */
public class SintetizadorVoz {
    private static boolean silenciado = false;

    public static void alternarSilencio() {
        silenciado = !silenciado;
    }

    public static boolean isSilenciado() {
        return silenciado;
    }

    public static void hablar(final String texto) {
        if (silenciado || texto == null || texto.trim().isEmpty()) {
            return;
        }
        // Ejecutar en un hilo separado para no congelar la interfaz tactil JFrame
        Thread hiloVoz = new Thread(new Runnable() {
            @Override
            public void run() {
                String limpio = texto.replace("\"", " ").replace("'", " ");
                String os = System.getProperty("os.name").toLowerCase();
                try {
                    if (os.contains("win")) {
                        // Comando nativo Windows (funciona en POSReady 7 con PowerShell / mshta VBScript)
                        String vbs = "mshta vbscript:Execute(\"CreateObject(\\\"SAPI.SpVoice\\\").Speak(\\\"" + limpio + "\\\"):window.close\")";
                        Runtime.getRuntime().exec(vbs);
                    } else if (os.contains("mac")) {
                        Runtime.getRuntime().exec(new String[]{"say", limpio});
                    } else {
                        // Linux (espeak / spd-say)
                        Runtime.getRuntime().exec(new String[]{"espeak", "-v", "es", limpio});
                    }
                } catch (IOException ex) {
                    System.out.println("[VOZ POS]: " + texto);
                }
            }
        });
        hiloVoz.setDaemon(true);
        hiloVoz.start();
    }
}
