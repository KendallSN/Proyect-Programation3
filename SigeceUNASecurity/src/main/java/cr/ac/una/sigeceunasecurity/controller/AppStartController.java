package cr.ac.una.sigeceunasecurity.controller;

import cr.ac.una.sigeceunasecurity.util.AppContext;
import cr.ac.una.sigeceunasecurity.util.FlowController;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Base64;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
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
    private Button btnSignIn;
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
    private void onActionBtnSignIn(ActionEvent event) {
        AppContext.getInstance().set("previousView", getStage().getScene().getRoot());
        FlowController.getInstance().goViewInStage("RegisterUserView", getStage());
        
        
    }

    @FXML
    private void onActionBtnExit(ActionEvent event) {
        if (getStage() != null) {
            getStage().close();
        }
    }
}
