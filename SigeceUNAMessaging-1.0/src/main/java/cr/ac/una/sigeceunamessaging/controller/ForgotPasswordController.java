package cr.ac.una.sigeceunamessaging.controller;

import cr.ac.una.sigeceunamessaging.model.UserDto;
import cr.ac.una.sigeceunamessaging.service.UserService;
import cr.ac.una.sigeceunamessaging.util.AppContext;
import cr.ac.una.sigeceunamessaging.util.Response;
import java.net.URL;
import java.security.SecureRandom;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author altam
 */
public class ForgotPasswordController extends Controller implements Initializable {

    @FXML
    private Label labelEnterEmail;
    @FXML
    private TextField txtEmail;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnConfirm;

    private UserService userService=new UserService();
    private static String CharactersAllow = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_*";
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
        String email=txtEmail.getText();
        Response response=userService.isUserWithEmail(email);
        if(response.getState()){
        UserDto userDto=(UserDto)response.getResult("User");
        userDto.setUsrTemppassword(generateRandomPassword()); 
        userService.saveUser(userDto);
        userService.sendEmailTempPassword(email,userDto.getUsrTemppassword());
        }
        AnchorPane previousView = (AnchorPane) AppContext.getInstance().get("previousLoginView");
        getStage().getScene().setRoot(previousView);
    }
    
    private String generateRandomPassword(){
    StringBuilder GeneratedPassword = new StringBuilder();
        SecureRandom rand = new SecureRandom();
        for (int i = 0; i < 5; i++) {
            int ind = rand.nextInt(CharactersAllow.length());
            char character = CharactersAllow.charAt(ind);
            GeneratedPassword.append(character);
        }

        return GeneratedPassword.toString();
    }
    
}
