package cr.ac.una.sigeceuna.controller;

import cr.ac.una.sigeceuna.model.UserDto;
import cr.ac.una.sigeceuna.util.AppContext;
import cr.ac.una.sigeceuna.util.FlowController;
import cr.ac.una.sigeceuna.util.Message;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class MainController extends Controller implements Initializable {

    @FXML
    private ImageView imgLogo;
    @FXML
    private Button btnExit;
    @FXML
    private BorderPane root;   
    @FXML
    private Label lbl_UserName;  
    @FXML
    private Label lbl_role;
    private ResourceBundle bundle;
    private UserDto loggedUser;
    private String currentRole;
    @FXML
    private ImageView imgLogin;
    @FXML
    private ImageView imgRole;
    @FXML
    private Button btnHome;
    @FXML
    private Button btnAreas;
    @FXML
    private Button btnCalendar;
    @FXML
    private Button btnManagement;
    @FXML
    private Button btnAprovements;
    @FXML
    private Button btnSearchManagement;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.bundle=FlowController.getIdioma();
        this.currentRole=(String) AppContext.getInstance().get("Role");
        this.loggedUser=(UserDto) AppContext.getInstance().get("User");
        this.lbl_UserName.setText(this.loggedUser.getUsrName()+ " " + this.loggedUser.getUsrSurname() + " " + this.loggedUser.getUsrLastname());
        this.lbl_role.setText(this.bundle.getString("role") + " " + this.currentRole);
    }

    @Override
    public void initialize() {
        // TODO
    }

    @FXML
    private void onActionBtnAreas(ActionEvent event) {
        if(AppContext.getInstance().get("Role")=="Admin"){
            FlowController.getInstance().goView("AreasView");
        }
        else{
            new Message().showModal(Alert.AlertType.WARNING, bundle.getString("accessDenied"), getStage(),
            bundle.getString("onlyAdminAccess"));
        }
    }

    @FXML
    private void onActionBtnHome(ActionEvent event) {
        FlowController.getInstance().goView("HomeView");
    }

    @FXML
    void onActionBtnCalendar(ActionEvent event) {
        FlowController.getInstance().goView("CalendarView");
    }

    @FXML
    void onActionBtnManagement(ActionEvent event) {
        AppContext.getInstance().delete("ManagementSelected");
        FlowController.getInstance().goView("ManagementView");
    }

    @FXML
    void onActionBtnSearchManagement(ActionEvent event) {
        FlowController.getInstance().goView("SearchManagementView");
    }

    @FXML
    void onActionBtnAprovements(ActionEvent event) {
        FlowController.getInstance().goView("AprovementsView");
    }

    @FXML
    void onActionBtnReports(ActionEvent event) {
        FlowController.getInstance().goView("ReportsView");
    }

    @FXML
    private void onActionBtnExit(ActionEvent event) {
        boolean userConfirmed = new Message().showConfirmation(bundle.getString("exit"), getStage(), bundle.getString("sureToLogOut"));
        if (userConfirmed) {
            FlowController.getInstance().goViewInWindow("AppStartView");
            AppContext.getInstance().delete("User");
            AppContext.getInstance().delete("ManagementSelected");
            FlowController.getInstance().salir();
        }
    }
}
