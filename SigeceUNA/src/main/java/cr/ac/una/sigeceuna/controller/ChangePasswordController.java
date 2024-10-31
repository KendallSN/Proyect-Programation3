package cr.ac.una.sigeceuna.controller;

import cr.ac.una.sigeceuna.model.UserDto;
import cr.ac.una.sigeceuna.service.UserService;
import cr.ac.una.sigeceuna.util.AppContext;
import cr.ac.una.sigeceuna.util.Message;
import cr.ac.una.sigeceuna.util.Response;
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
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author altam
 */
public class ChangePasswordController extends Controller implements Initializable {

    @FXML
    private PasswordField txtPassword;
    @FXML
    private PasswordField txtConfirmPassword;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnConfirm;

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
    private void onActionBtnCancel(ActionEvent event) {
        AnchorPane previousView = (AnchorPane) AppContext.getInstance().get("previousLoginView");
        getStage().getScene().setRoot(previousView);
    }

    @FXML
    private void onActionBtnConfirm(ActionEvent event) {
        if(this.txtPassword.getText().isEmpty()||this.txtConfirmPassword.getText().isEmpty()){
            new Message().showModal(Alert.AlertType.ERROR, "Cambiar contraseña", getStage(), "No pueden haber espacios vacíos.");
        }
        else if(!this.txtPassword.getText().equals(this.txtConfirmPassword.getText())){
            new Message().showModal(Alert.AlertType.ERROR, "Cambiar contraseña", getStage(), "La contraseña debe ser la misma.");
        }
        else{
            try {   
                UserDto userDto=(UserDto)AppContext.getInstance().get("User");
                UserService userService=new UserService();
                userDto.setUsrPassword(this.txtPassword.getText());
                userDto.setUsrTemppassword("");
                Response response = userService.saveUser(userDto);
                if (!response.getState()) {
                    new Message().showModal(Alert.AlertType.ERROR, "Cambiar contraseña", getStage(), response.getMessage());
                } else {
                    this.txtPassword.clear();
                    this.txtConfirmPassword.clear();
                    new Message().showModal(Alert.AlertType.INFORMATION, "Cambiar contraseña", getStage(), "Contraseña cambiada exitosamente.");  
                    AnchorPane previousView = (AnchorPane) AppContext.getInstance().get("previousLoginView");
                    getStage().getScene().setRoot(previousView);
                }
            } catch (Exception ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "Ocurrió un error mientrar se cambiaba la contraseña.", ex);
                new Message().showModal(Alert.AlertType.ERROR, "Cambiar contraseña", getStage(), "Ocurrió un error mientrar se cambiaba la contraseña.");
            }
        }
    }
    
}
