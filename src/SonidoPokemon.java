import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * =================================================================================
 * CLASE DE UTILIDAD DE AUDIO (AUDIO HELPER)
 * =================================================================================
 * Esta clase gestiona la reproducción de sonidos (gritos de Pokémon) desde URLs remotas.
 * Centraliza el manejo de MediaPlayer y evita problemas de concurrencia o memoria.
 */
public class SonidoPokemon {

    // Variable estática para mantener una referencia al reproductor actual
    // y evitar que el Garbage Collector lo elimine mientras reproduce.
    private static MediaPlayer mediaPlayer;

    /**
     * Reproduce el grito (cry) de un Pokémon específico.
     * Descarga el audio desde Pokemon Showdown y lo reproduce.
     *
     * @param p El Pokémon cuyo sonido se quiere reproducir.
     */
    public static void reproducirGrito(Pokémon p) {
        // Ejecutar en el hilo de la interfaz gráfica (JavaFX Application Thread)
        // para asegurar la sincronización correcta con la UI.
        javafx.application.Platform.runLater(() -> {
            try {
                // 1. Normalizar el nombre para construir la URL correcta
                // (minúsculas, sin espacios, correcciones específicas como 'bulbasur' -> 'bulbasaur')
                String nombreNormalizado = p.getNombre().toLowerCase().replace(" ", "");
                if (nombreNormalizado.equals("bulbasur")) nombreNormalizado = "bulbasaur";
                
                // URL del archivo de audio (MP3)
                String soundUrl = "https://play.pokemonshowdown.com/audio/cries/" + nombreNormalizado + ".mp3";
                
                // 2. Limpiar el reproductor anterior si existe
                // Esto evita que se solapen sonidos y libera recursos.
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.dispose();
                }
                
                // 3. Crear y configurar el nuevo reproductor
                Media sound = new Media(soundUrl);
                mediaPlayer = new MediaPlayer(sound);
                
                // Manejo de errores de reproducción (ej. archivo no encontrado, error de códec)
                mediaPlayer.setOnError(() -> System.out.println("Error MediaPlayer: " + mediaPlayer.getError()));
                
                // 4. Iniciar reproducción
                mediaPlayer.play();
                
            } catch (Throwable e) {
                // Capturar cualquier error (incluso de enlace nativo) para no romper la aplicación
                System.out.println("No se pudo reproducir sonido: " + e.getMessage());
            }
        });
    }
}