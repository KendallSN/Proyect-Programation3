package cr.ac.una.sigeceuna.controller;

import cr.ac.una.sigeceuna.util.AppContext;
import cr.ac.una.sigeceuna.util.FlowController;
import cr.ac.una.sigeceuna.util.Message;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class AppStartController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private ImageView imgLogo;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnExit;
    @FXML
    private ImageView imgLogin;
    @FXML
    private ImageView imgExit;

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
