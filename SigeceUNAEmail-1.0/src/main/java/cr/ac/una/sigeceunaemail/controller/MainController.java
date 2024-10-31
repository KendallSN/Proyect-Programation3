package cr.ac.una.sigeceunaemail.controller;

import cr.ac.una.sigeceunaemail.util.AppContext;
import cr.ac.una.sigeceunaemail.util.FlowController;
import cr.ac.una.sigeceunaemail.util.Message;
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
    private Button btnManagement;
    @FXML
    private Button btnEmail;
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
    private void onActionBtnManagement(ActionEvent event) {
        if(AppContext.getInstance().get("role")=="Admin"){
            FlowController.getInstance().goView("ManagementView");
        }
        else{
            new Message().showModal(Alert.AlertType.WARNING, "Access Denied", getStage(),
            "Only admin users are allowed to management.");
        }
    }

    @FXML
    private void onActionBtnEmail(ActionEvent event) {
        FlowController.getInstance().goView("EmailView");
    }
    @FXML
    void onActionBtnManageEmails(ActionEvent event) {
        if(AppContext.getInstance().get("role")=="Admin"){
            FlowController.getInstance().goView("ManageMailsView");
        }else{
            new Message().showModal(Alert.AlertType.WARNING, "Access Denied", getStage(),
            "Only admin users are allowed to management.");
        }
    }
    @FXML
    private void onActionBtnExit(ActionEvent event) {
        boolean userConfirmed = new Message().showConfirmation("Exit", getStage(), "Are you sure you want to log-out");
       if(userConfirmed){
        FlowController.getInstance().goViewInWindow("AppStartView");
        FlowController.getInstance().salir();
       }
    }

}
