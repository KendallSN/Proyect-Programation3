
package cr.ac.una.sigeceunamessaging.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioController {
   private MediaPlayer mediaPlayer;
   
   public void stopAudio() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();//Detect audio
            mediaPlayer.dispose();
            mediaPlayer = null; // Reststar mediaPlayer
        }
    }
   
   private void playAudio(Byte[] fileContent) {
        // Convert Byte[] by byte[]
        byte[] byteArray = new byte[fileContent.length];
        for (int i = 0; i < fileContent.length; i++) {
            byteArray[i] = fileContent[i];
        }

        // Temp file to save the audio
        File tempFile = null;
        try {
            tempFile = File.createTempFile("tempAudio", ".mp3");
            tempFile.deleteOnExit(); // Delete temp file

            // Write the content from byteArray in the tempFile
            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                fos.write(byteArray);
            }

            if (mediaPlayer != null) {
                mediaPlayer.stop(); // Stop the actual audio if its playing
                mediaPlayer.dispose();
            }

            // Create a Media object and asign the mediaPlayer
            Media media = new Media(tempFile.toURI().toString());
            mediaPlayer = new MediaPlayer(media);

            mediaPlayer.setOnEndOfMedia(() -> {
                mediaPlayer.stop();
                mediaPlayer.dispose();
                mediaPlayer = null; // Restart mediaPlayer
            });

            mediaPlayer.play(); // Start reproduction
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   
    public void handlePlayButton(Byte[] fileContent) {
        if (mediaPlayer == null) {
            playAudio(fileContent);
        } else if (mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED) {
            mediaPlayer.play();
        } else {
            System.out.println("El audio ya se está reproduciendo.");
        }
    }
}
