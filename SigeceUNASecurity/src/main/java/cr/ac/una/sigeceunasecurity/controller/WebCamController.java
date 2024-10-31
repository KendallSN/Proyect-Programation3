package cr.ac.una.sigeceunasecurity.controller;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import cr.ac.una.sigeceunasecurity.util.AppContext;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WebCamController extends Controller implements Initializable {

    private Webcam webcam = Webcam.getDefault();
    @FXML
    private ImageView imgPhoto;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnPhoto;

    private ScheduledExecutorService executor;
    private boolean isPreviewing = false;

    @Override
    public void initialize() {
        if (webcam != null) {
            webcam.setViewSize(WebcamResolution.VGA.getSize());
            webcam.open();
            isPreviewing = true;
            startPreview();
        } else {
            System.out.println("No webcam detected");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void onActionBtnTakePhoto(ActionEvent event) {
        if (webcam != null && webcam.isOpen()) {
            BufferedImage bufferedImage = webcam.getImage();
            Image image = convertToFXImage(bufferedImage);
            // Guarda la imagen en AppContext
            AppContext.getInstance().set("photoUser", image);
            stopPreview();
            getStage().close();
            webcam.close();
        }
    }

    @FXML
    private void onActionBtnCancel(ActionEvent event) {
        stopPreview();
        getStage().close();
        webcam.close();
    }

    private void startPreview() {
        if (executor == null || executor.isShutdown()) {
            executor = Executors.newSingleThreadScheduledExecutor();
            executor.scheduleAtFixedRate(() -> {
                if (webcam.isOpen()) {
                    BufferedImage bufferedImage = webcam.getImage();
                    Image image = convertToFXImage(bufferedImage);
                    Platform.runLater(() -> imgPhoto.setImage(image));
                }
            }, 0, 33, TimeUnit.MILLISECONDS);
        }
    }

    private void stopPreview() {
        if (executor != null && !executor.isShutdown()) {
            executor.shutdownNow();
            try {
                if (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
                    System.err.println("Preview executor did not terminate in the specified time.");
                }
            } catch (InterruptedException ex) {
                System.err.println("Interrupted while waiting for executor termination.");
                Thread.currentThread().interrupt();
            }
        }
    }

    private Image convertToFXImage(BufferedImage bufferedImage) {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        WritableImage writableImage = new WritableImage(width, height);

        javafx.scene.image.PixelWriter pixelWriter = writableImage.getPixelWriter();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = bufferedImage.getRGB(x, y);
                javafx.scene.paint.Color color = javafx.scene.paint.Color.rgb(
                        (rgb >> 16) & 0xFF,
                        (rgb >> 8) & 0xFF,
                        rgb & 0xFF
                );
                pixelWriter.setColor(x, y, color);
            }
        }

        return writableImage;
    }
}