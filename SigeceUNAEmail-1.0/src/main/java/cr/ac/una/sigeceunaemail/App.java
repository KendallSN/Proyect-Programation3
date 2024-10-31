package cr.ac.una.sigeceunaemail;

import cr.ac.una.sigeceunaemail.util.FlowController;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.MalformedURLException;

import static javafx.application.Application.launch;

/**
 * JavaFX App
 */

public class App extends Application {

   @Override
    public void start(Stage stage) throws IOException {
        FlowController.getInstance().InitializeFlow(stage, null);
        FlowController.getInstance().goViewInWindow("AppStartView");
    }

    public static void main(String[] args) throws MalformedURLException {
        launch();
    }
}