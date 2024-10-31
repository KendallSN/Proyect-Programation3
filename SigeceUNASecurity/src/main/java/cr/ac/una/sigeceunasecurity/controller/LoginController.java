package cr.ac.una.sigeceunasecurity.controller;

import cr.ac.una.sigeceunasecurity.model.UserDto;
import cr.ac.una.sigeceunasecurity.service.UserService;
import cr.ac.una.sigeceunasecurity.util.AppContext;
import cr.ac.una.sigeceunasecurity.util.FlowController;
import cr.ac.una.sigeceunasecurity.util.Formato;
import cr.ac.una.sigeceunasecurity.util.Message;
import cr.ac.una.sigeceunasecurity.util.Response;
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

public class LoginController extends Controller implements Initializable {

    @FXML
    private ImageView imgLogo;
    @FXML
    private ImageView imgUser;
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
    @FXML
    private TextField txtIdentification;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assignTextFileFormatting();
    }

    @Override
    public void initialize() {
        txtIdentification.clear();
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

    private void validateUser() {
        try {
            if (txtIdentification.getText() == null || txtIdentification.getText().isBlank()) {
                new Message().showModal(Alert.AlertType.ERROR, "User Validation", getStage(),
                        "You must enter a username to log into the system.");
            } else if (txtPassword.getText() == null || txtPassword.getText().isBlank()) {
                new Message().showModal(Alert.AlertType.ERROR, "User Validation", getStage(),
                        "You must enter a password to log into the system.");
            } else {
                UserService userService = new UserService();
                Response response = userService.validateUser(txtIdentification.getText(), txtPassword.getText());
                Response responseTemp = userService.validateUserWithTempPassword(txtIdentification.getText(), txtPassword.getText());
                if (response.getState()) {
                    UserDto user = new UserDto((cr.ac.una.sigeceunasecurityws.controller.UserDto) response.getResult("User"));
                    if (user.isAdmin()) {
                        AppContext.getInstance().set("User", user);
                        FlowController.getInstance().goMain();
                        getStage().close();
                    } else {
                        new Message().showModal(Alert.AlertType.WARNING, "Access Denied", getStage(),
                                "Only administrators are allowed to log in.");
                    }
                } else if (responseTemp.getState() && !response.getState()) {
                    UserDto user = new UserDto((cr.ac.una.sigeceunasecurityws.controller.UserDto) responseTemp.getResult("User"));
                    this.txtPassword.clear();
                    this.txtIdentification.clear();
                    AppContext.getInstance().set("User", user);
                    AppContext.getInstance().set("previousLoginView", getStage().getScene().getRoot());
                    FlowController.getInstance().goViewInStage("ChangePasswordView", getStage());
                } else {
                    new Message().show(Alert.AlertType.ERROR, "User Validation", response.getMessage());
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, "Login Error", ex);
            new Message().show(Alert.AlertType.ERROR, "Log In", "Error logging into the system.");
        }
    }

    private void assignTextFileFormatting() {
        txtIdentification.setTextFormatter(Formato.getInstance().maxLengthFormat(9));
        txtPassword.setTextFormatter(Formato.getInstance().maxLengthFormat(15));
    }
}