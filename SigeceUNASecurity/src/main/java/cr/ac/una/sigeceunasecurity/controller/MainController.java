package cr.ac.una.sigeceunasecurity.controller;

import cr.ac.una.sigeceunasecurity.util.FlowController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class MainController extends Controller implements Initializable {

    @FXML
    private ImageView imgLogo;
    @FXML
    private Button btnManageRoles;
    @FXML
    private Button btnExit;
    @FXML
    private BorderPane root;
    @FXML
    private Button btnEditUser;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @Override
    public void initialize() {
    }

    @FXML
    private void onActionManageRoles(ActionEvent event) {
        FlowController.getInstance().goView("ManageRolesView");
    }

    @FXML
    private void onActionBtnExit(ActionEvent event) {
        FlowController.getInstance().goViewInWindow("AppStartView");
        FlowController.getInstance().salir();
    }

    @FXML
    private void OnActionBtnEditUser(ActionEvent event) {
         FlowController.getInstance().goView("EditUserInformationView");
    }

}