package cr.ac.una.sigeceunaemail.controller;

import cr.ac.una.sigeceunaemail.util.AppContext;
import cr.ac.una.sigeceunaemail.util.FlowController;
import cr.ac.una.sigeceunaemail.util.Message;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author altam
 */
public class AppStartController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private ImageView imgLogo;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnExit;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    @Override
    public void initialize() {
       
    }

    @FXML
    private void onActionBtnLogin(ActionEvent event) {
        AppContext.getInstance().set("previousView", getStage().getScene().getRoot());
        FlowController.getInstance().goViewInStage("LoginView", getStage());
    }
    @FXML
    private void onActionBtnExit(ActionEvent event) {
         boolean userConfirmed = new Message().showConfirmation("Exit", getStage(), "Are you sure you want to exit");
        if(userConfirmed){
            if (getStage() != null) {
            getStage().close();
            }
        }
    }
}
