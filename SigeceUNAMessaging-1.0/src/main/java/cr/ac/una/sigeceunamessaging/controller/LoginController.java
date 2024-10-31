package cr.ac.una.sigeceunamessaging.controller;

import cr.ac.una.sigeceunamessaging.model.UserDto;
import cr.ac.una.sigeceunamessaging.service.UserService;
import cr.ac.una.sigeceunamessaging.util.AppContext;
import cr.ac.una.sigeceunamessaging.util.FlowController;
import cr.ac.una.sigeceunamessaging.util.Formato;
import cr.ac.una.sigeceunamessaging.util.Message;
import cr.ac.una.sigeceunamessaging.util.Response;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author altam
 */
public class LoginController extends Controller implements Initializable {

    @FXML
    private ImageView imgLogo;
    @FXML
    private ImageView imgUser;
    @FXML
    private TextField txtUser;
    @FXML
    private ImageView imgPassword;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnForgotPassword;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnLogIn;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assignTextFileFormatting();
    }

    @Override
    public void initialize() {
        txtUser.clear();
        txtPassword.clear();
    }

    @FXML
    private void onActionForgorPassword(ActionEvent event) {
        AppContext.getInstance().set("previousLoginView", getStage().getScene().getRoot());
        FlowController.getInstance().goViewInStage("ForgotPasswordView", getStage());
    }

    @FXML
    private void onActionBtnCancel(ActionEvent event) {
        AnchorPane previousView = (AnchorPane) AppContext.getInstance().get("previousView");
        getStage().getScene().setRoot(previousView);
    }

    @FXML
    private void onActionBtnLogin(ActionEvent event) {
        validateUser();
    }

    @FXML
    private void validateUser() {
        try {
            if (txtUser.getText() == null || txtUser.getText().isBlank()) {
                new Message().showModal(Alert.AlertType.ERROR, "User Validation", getStage(),
                        "You must enter a username to log into the system.");
            } else if (txtPassword.getText() == null || txtPassword.getText().isBlank()) {
                new Message().showModal(Alert.AlertType.ERROR, "User Validation", getStage(),
                        "You must enter a password to log into the system.");
            } else {
                UserService userService = new UserService();
                Response response = userService.validateUserRs(txtUser.getText(), txtPassword.getText());
                Response responseTemp=userService.validateUserWithTemppasswordRs(txtUser.getText(), txtPassword.getText());
                if (response.getState()) {
                    UserDto userDto=(UserDto)response.getResult("User");
                    if(userDto.isNormal()){
                        this.txtPassword.clear();
                        this.txtUser.clear();
                        AppContext.getInstance().set("User", response.getResult("User"));
                        FlowController.getInstance().goMain();
                        getStage().close();
                    }
                    else{
                        new Message().showModal(Alert.AlertType.WARNING, "Access Denied", getStage(),
                            "Only users with normal role are allowed to log in.");
                    }
                } 
                else if(responseTemp.getState()&&!response.getState()){
                    this.txtPassword.clear();
                    this.txtUser.clear();
                    AppContext.getInstance().set("User", responseTemp.getResult("User"));
                    AppContext.getInstance().set("previousLoginView", getStage().getScene().getRoot());
                    FlowController.getInstance().goViewInStage("ChangePasswordView", getStage());
                }else {
                    new Message().show(Alert.AlertType.ERROR, "User Validation", response.getMessage());
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, "Login Error", ex);
            new Message().show(Alert.AlertType.ERROR, "Log In", "Error logging into the system.");
        }
    }

    private void assignTextFileFormatting() {
//        txtUser.setTextFormatter(Formato.getInstance().letrasFormat(5));
//        txtPassword.setTextFormatter(Formato.getInstance().letrasFormat(15));
    }
    
}
