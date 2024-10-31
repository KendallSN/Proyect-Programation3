package cr.ac.una.sigeceuna.controller;

import cr.ac.una.sigeceuna.model.UserDto;
import cr.ac.una.sigeceuna.service.UserService;
import cr.ac.una.sigeceuna.util.AppContext;
import cr.ac.una.sigeceuna.util.FlowController;
import cr.ac.una.sigeceuna.util.Formato;
import cr.ac.una.sigeceuna.util.Message;
import cr.ac.una.sigeceuna.util.Response;
import java.net.URL;
import java.util.Locale;
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
                new Message().showModal(Alert.AlertType.ERROR, "Validacion de Usuario", getStage(),
                        "Debe ingresar su nombre de usuario para ingresar al sistema.");
            } else if (txtPassword.getText() == null || txtPassword.getText().isBlank()) {
                new Message().showModal(Alert.AlertType.ERROR, "Validacion de Usuario", getStage(),
                        "Debe ingresar su contraseña para ingresar al sistema.");
            } else {
                UserService userService = new UserService();
                Response response = userService.validateUserRs(txtUser.getText(), txtPassword.getText());
                Response responseTemp=userService.validateUserWithTemppasswordRs(txtUser.getText(), txtPassword.getText());
                if (response.getState()) {
                    UserDto userDto=(UserDto)response.getResult("User");
                    AppContext.getInstance().set("Role", userDto.Role());
                    if(true){//colocar AppContext.getInstance().get("role")!="Sin asignar"
                        this.txtPassword.clear();
                        this.txtUser.clear();
                       Locale locale=Locale.forLanguageTag(userDto.getUsrLanguage());
                    ResourceBundle exampleBundle = ResourceBundle.getBundle("cr.ac.una.sigeceuna.languajes.Messages",locale);                  
                    FlowController.setIdioma(exampleBundle);
                AppContext.getInstance().set("User", response.getResult("User"));
                        FlowController.getInstance().goMain();
                        getStage().close();
                    }
                    else{
                        new Message().showModal(Alert.AlertType.WARNING, "Acceso denagado", getStage(),
                            "Solo usuario con Admin, Manage o Applicant role tienen permitido ingresar al sistema.");
                    }
                } 
                else if(responseTemp.getState()&&!response.getState()){
                    this.txtPassword.clear();
                    this.txtUser.clear();
                    AppContext.getInstance().set("User", responseTemp.getResult("User"));
                    AppContext.getInstance().set("previousLoginView", getStage().getScene().getRoot());
                    FlowController.getInstance().goViewInStage("ChangePasswordView", getStage());
                }else {
                    new Message().show(Alert.AlertType.ERROR, "Validacion de Usuario", response.getMessage());
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, "Error de ingreso", ex);
            new Message().show(Alert.AlertType.ERROR, "Ingresar", "Error ingresando al sistema.");
        }
    }

    private void assignTextFileFormatting() {
//        txtUser.setTextFormatter(Formato.getInstance().letrasFormat(5));
//        txtPassword.setTextFormatter(Formato.getInstance().letrasFormat(15));
    }
    
}
