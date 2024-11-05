package cr.ac.una.sigeceuna.controller;

import cr.ac.una.sigeceuna.model.UserDto;
import cr.ac.una.sigeceuna.service.UserService;
import cr.ac.una.sigeceuna.util.AppContext;
import cr.ac.una.sigeceuna.util.Message;
import cr.ac.una.sigeceuna.util.Response;
import java.net.URL;
import java.security.SecureRandom;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class ForgotPasswordController extends Controller implements Initializable {

    @FXML
    private Label labelEnterEmail;
    @FXML
    private TextField txtEmail;

    private UserService userService=new UserService();
    private static String CharactersAllow = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_*";
    @FXML
    private Button btnCancelTem;
    @FXML
    private Button btnConfirmTem;
    @FXML
    private ImageView imgLogo;
    @FXML
    private ImageView imgCancelTem;
    @FXML
    private ImageView imgConfirm;
    
    private static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    @Override
    public void initialize() {
        // TODO
    }
    
    public static boolean emailValid(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
    
    @FXML
    private void onActionBtnCancel(ActionEvent event) {
        AnchorPane previousView = (AnchorPane) AppContext.getInstance().get("previousLoginView");
        getStage().getScene().setRoot(previousView);
    }

    @FXML
    private void onActionBtnConfirm(ActionEvent event) {
        if(this.txtEmail.getText().isEmpty()||this.txtEmail.getText().isBlank()){
            new Message().showModal(Alert.AlertType.ERROR, "Enviar contraseña temporal", getStage(), "No pueden haber espacios vacíos.");
        }
        else if(!emailValid(this.txtEmail.getText())){
            new Message().showModal(Alert.AlertType.ERROR, "Enviar contraseña temporal", getStage(), "El formato del correo es inválido.");
        }
        else{
            try {
                String email=txtEmail.getText();
                Response response=userService.isUserWithEmail(email);
                if (!response.getState()) {
                    new Message().showModal(Alert.AlertType.ERROR, "Enviar contraseña temporal", getStage(), response.getMessage());
                } else {
                    changeTempPassword(response,email);
                    new Message().showModal(Alert.AlertType.INFORMATION, "Enviar contraseña temporal", getStage(), "Contraseña enviada.");  
                    AnchorPane previousView = (AnchorPane) AppContext.getInstance().get("previousLoginView");
                    getStage().getScene().setRoot(previousView);
                }
            } catch (Exception ex) {
                Logger.getLogger(ForgotPasswordController.class.getName()).log(Level.SEVERE, "Ocurrió un error mientrar se enviaba la contraseña temporal.", ex);
                new Message().showModal(Alert.AlertType.ERROR, "Enviar contraseña temporal", getStage(), "Ocurrió un error mientrar se enviaba la contraseña temporal.");
            }
        }
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

    private void changeTempPassword(Response response, String email) {
        try {   
            UserDto userDto=(UserDto)response.getResult("User");
            userDto.setUsrTemppassword(generateRandomPassword());
            Response response2 = userService.saveUser(userDto);
            if (!response2.getState()) {
                new Message().showModal(Alert.AlertType.ERROR, "Cambiar contraseña", getStage(), response2.getMessage());
            } else {
                UserDto user2=(UserDto) response2.getResult("User");
                if(user2.getRoleCollection().isEmpty() || userDto.getRoleCollection()==null){
                    System.out.println("role collection null");
                }
                userService.sendEmailTempPassword(email,userDto.getUsrTemppassword());
                this.txtEmail.clear();
            }
        } catch (Exception ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "Ocurrió un error mientrar se cambiaba la contraseña temporal.", ex);
            new Message().showModal(Alert.AlertType.ERROR, "Cambiar contraseña temporal", getStage(), "Ocurrió un error mientrar se cambiaba la contraseña temporal.");
        }
    }
    
}
