package cr.ac.una.sigeceunamessaging.controller;

import cr.ac.una.sigeceunamessaging.model.UserDto;
import cr.ac.una.sigeceunamessaging.util.AppContext;
import cr.ac.una.sigeceunamessaging.util.FlowController;
import cr.ac.una.sigeceunamessaging.util.Message;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author altam
 */
public class MainController extends Controller implements Initializable {

    @FXML
    private ImageView imgLogo;
    @FXML
    private Button btnChat;
    @FXML
    private Button btnExit;
    @FXML
    private BorderPane root;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @Override
    public void initialize() {
        // TODO
    }

    @FXML
    private void onActionBtnChat(ActionEvent event) {
        FlowController.getInstance().goView("ChatView");
    }

    @FXML
    private void onActionBtnExit(ActionEvent event) {
       boolean userConfirmed = new Message().showConfirmation("Exit", getStage(), "Are you sure you want to log-out");
       if(userConfirmed){
        FlowController.getInstance().goViewInWindow("AppStartView");
        AppContext.getInstance().delete("User");
        FlowController.getInstance().salir();
       }
        
    }

}
