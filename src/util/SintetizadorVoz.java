package util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class SintetizadorVoz {

    private static boolean silenciado = false;
    private static Process procesoActual = null;

    public static void setSilenciado(boolean estado) {
        silenciado = estado;
        if (silenciado) {
            detener();
        }
    }

    public static boolean isSilenciado() {
        return silenciado;
    }

    public static boolean alternarSilencio() {
        setSilenciado(!silenciado);
        return silenciado;
    }

    public static void detener() {
        if (procesoActual != null && procesoActual.isAlive()) {
            procesoActual.destroyForcibly();
        }
    }

    public static void hablar(final String texto) {
        if (silenciado || texto == null || texto.trim().isEmpty()) {
            return;
        }

        detener();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String textoLimpio = texto.replace("\"", "").replace("'", "");
                    String script =
                        "Add-Type -AssemblyName System.Speech; " +
                        "$voz = New-Object System.Speech.Synthesis.SpeechSynthesizer; " +
                        "$vozEs = $voz.GetInstalledVoices() | Where-Object { $_.VoiceInfo.Culture.Name -like 'es-*' -and $_.VoiceInfo.Gender -eq 'Female' } | Select-Object -First 1; " +
                        "if (-not $vozEs) { $vozEs = $voz.GetInstalledVoices() | Where-Object { $_.VoiceInfo.Culture.Name -like 'es-*' } | Select-Object -First 1 }; " +
                        "if ($vozEs) { $voz.SelectVoice($vozEs.VoiceInfo.Name) }; " +
                        "$voz.Rate = -1; " +
                        "$voz.Volume = 100; " +
                        "$voz.Speak('" + textoLimpio + "');";

                    byte[] bytes = script.getBytes(StandardCharsets.UTF_16LE);
                    String encodedCommand = Base64.getEncoder().encodeToString(bytes);

                    ProcessBuilder pb = new ProcessBuilder(
                        "powershell.exe",
                        "-NoProfile",
                        "-NonInteractive",
                        "-WindowStyle", "Hidden",
                        "-EncodedCommand", encodedCommand
                    );
                    procesoActual = pb.start();
                    procesoActual.waitFor();
                } catch (Exception e) {
                    System.err.println("Error en SintetizadorVoz: " + e.getMessage());
                }
            }
        }).start();
    }
}
