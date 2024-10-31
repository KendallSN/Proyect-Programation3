package cr.ac.una.sigeceunasecurity.controller;

import cr.ac.una.sigeceunasecurity.model.UserDto;
import cr.ac.una.sigeceunasecurity.service.UserService;
import cr.ac.una.sigeceunasecurity.util.AppContext;
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
            new Message().showModal(Alert.AlertType.ERROR, "Change Password", getStage(), "There can be no empty spaces.");
        }
        else if(!this.txtPassword.getText().equals(this.txtConfirmPassword.getText())){
            new Message().showModal(Alert.AlertType.ERROR, "Change Password", getStage(), "The password has to be the same.");
        }
        else{
            try {   
                UserDto userDto=(UserDto)AppContext.getInstance().get("User");
                UserService userService=new UserService();
                userDto.setUsrPassword(this.txtPassword.getText());
                userDto.setUsrTemppassword("");
                Response response = userService.saveUser(userDto.getUser());
                if (!response.getState()) {
                    new Message().showModal(Alert.AlertType.ERROR, "change password", getStage(), response.getMessage());
                } else {
                    this.txtPassword.clear();
                    this.txtConfirmPassword.clear();
                    new Message().showModal(Alert.AlertType.INFORMATION, "Change Password", getStage(), "Password changed successfuly.");  
                    AnchorPane previousView = (AnchorPane) AppContext.getInstance().get("previousLoginView");
                    getStage().getScene().setRoot(previousView);
                }
            } catch (Exception ex) {
                Logger.getLogger(ChangePasswordController.class.getName()).log(Level.SEVERE, "An error ocurred while changing the password.", ex);
                new Message().showModal(Alert.AlertType.ERROR, "Change Password", getStage(), "An error ocurred while changing the password.");
            }
        }
    }
    
}
