package cr.ac.una.sigeceunasecurity.controller;

import cr.ac.una.sigeceunasecurityws.controller.RoleDto;
import cr.ac.una.sigeceunasecurityws.controller.SystemsDto;
import cr.ac.una.sigeceunasecurity.model.UserDto;
import cr.ac.una.sigeceunasecurity.service.RoleService;
import cr.ac.una.sigeceunasecurity.service.SystemsService;
import cr.ac.una.sigeceunasecurity.service.UserService;
import cr.ac.una.sigeceunasecurity.util.AppContext;
import cr.ac.una.sigeceunasecurity.util.BindingUtils;
import cr.ac.una.sigeceunasecurity.util.FlowController;
import cr.ac.una.sigeceunasecurity.util.Formato;
import cr.ac.una.sigeceunasecurity.util.Message;
import cr.ac.una.sigeceunasecurity.util.Response;
import io.github.palexdev.mfxcore.utils.fx.SwingFXUtils;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;

public class RegisterUserController extends Controller implements Initializable {

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtSecondLastName;
    @FXML
    private TextField txtPhoneNumber;
    @FXML
    private TextField txtAnotherPhoneNumber;
    @FXML
    private ImageView imgUserPhoto;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private TextField txtEmail;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnTakePhoto;
    @FXML
    private TextField txtIdentification;
    @FXML
    private RadioButton spanishCheckBox;
    @FXML
    private RadioButton englishCheckBox;
    @FXML
    private ToggleGroup tggLanguage;
    @FXML
    private Button btnSignInUser;

    UserDto userDto;
    cr.ac.una.sigeceunasecurityws.controller.UserDto userDATOwS = new cr.ac.una.sigeceunasecurityws.controller.UserDto();
    List<Node> required = new ArrayList<>();
    private boolean areValid = true;
    private String invalidFields = "";
    SystemsDto systemDto = new SystemsDto();
    cr.ac.una.sigeceunasecurity.model.SystemsDto selectedSystem = new cr.ac.una.sigeceunasecurity.model.SystemsDto();
    cr.ac.una.sigeceunasecurity.model.RoleDto selectedRole = new cr.ac.una.sigeceunasecurity.model.RoleDto();
    RoleDto roleDto = new RoleDto();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assignTextFileFormatting();
        this.userDto = new UserDto();
        indicateRequired();
        createNewUser();
    }

    @Override
    public void initialize() {

    }

    @FXML
    private void onActionBtnCancel(ActionEvent event) {
        AnchorPane previousView = (AnchorPane) AppContext.getInstance().get("previousView");
        getStage().getScene().setRoot(previousView);
    }

    private void indicateRequired() {
        required.clear();
        required.addAll(Arrays.asList(
                txtName, txtLastName,
                txtSecondLastName, txtIdentification,
                txtPhoneNumber, txtPassword, txtEmail));
    }

    private void bindUser(Boolean nuevo) {
        if (!nuevo) {
            txtId.textProperty().bind(userDto.usrId);
        }
        txtName.textProperty().bindBidirectional(userDto.usrName);
        txtLastName.textProperty().bindBidirectional(userDto.usrSurname);
        txtSecondLastName.textProperty().bindBidirectional(userDto.usrLastname);
        txtPhoneNumber.textProperty().bindBidirectional(userDto.usrCelphonenumber);
        txtAnotherPhoneNumber.textProperty().bindBidirectional(userDto.usrTelephone);
        txtEmail.textProperty().bindBidirectional(userDto.usrEmail);
        txtPassword.textProperty().bindBidirectional(userDto.usrPassword);
        txtIdentification.textProperty().bindBidirectional(userDto.usrIdentification);
        BindingUtils.bindToggleGroupToProperty(tggLanguage, userDto.usrLanguage);
    }

    private void unbindUser() {
        txtId.textProperty().unbind();
        txtName.textProperty().unbindBidirectional(userDto.usrName);
        txtLastName.textProperty().unbindBidirectional(userDto.usrSurname);
        txtSecondLastName.textProperty().unbindBidirectional(userDto.usrLastname);
        txtPhoneNumber.textProperty().unbindBidirectional(userDto.usrCelphonenumber);
        txtAnotherPhoneNumber.textProperty().unbindBidirectional(userDto.usrTelephone);
        txtEmail.textProperty().unbindBidirectional(userDto.usrEmail);
        txtPassword.textProperty().unbindBidirectional(userDto.usrPassword);
        txtIdentification.textProperty().unbindBidirectional(userDto.usrIdentification);
        BindingUtils.unbindToggleGroupToProperty(tggLanguage, userDto.usrLanguage);
        imgUserPhoto.imageProperty().unbind();
    }

    private void createNewUser() {
        unbindUser();
        userDto = new UserDto();
        bindUser(true);
//        userDto.setUsrState("I");
//        validateRequiredFields();
        txtId.setDisable(false);
        txtId.clear();
        txtId.requestFocus();
    }

    public String validateRequiredFields() {
        areValid = true;
        invalidFields = "";

        for (Node field : required) {
            if (field instanceof TextField && (((TextField) field).getText() == null || ((TextField) field).getText().isBlank())) {
                if (areValid) {
                    invalidFields += ((TextField) field).getPromptText();
                } else {
                    invalidFields += ", " + ((TextField) field).getPromptText();
                }
                areValid = false;
            } else if (field instanceof PasswordField && (((PasswordField) field).getText() == null || ((PasswordField) field).getText().isBlank())) {
                if (areValid) {
                    invalidFields += ((PasswordField) field).getPromptText();
                } else {
                    invalidFields += ", " + ((PasswordField) field).getPromptText();
                }
                areValid = false;
            } else if (field instanceof RadioButton) {
                if (tggLanguage.getSelectedToggle() == null) {
                    if (areValid) {
                        invalidFields += "Language Selection";
                    } else {
                        invalidFields += ", Language Selection";
                    }
                    areValid = false;
                }
            }
        }

        if (areValid) {
            return "";
        } else {
            return "Required or invalid fields: [" + invalidFields + "].";
        }
    }

    private void validateRequiredPhoto() {
        if (imgUserPhoto.getImage() == null) {
            if (areValid) {
                invalidFields += "User Photo";
            } else {
                invalidFields += ", User Photo";
            }
            areValid = false;
        }
    }

    @FXML
    private void onActionBtnSignIn(ActionEvent event) {
        try {
            if (createUser()) {
                sendActivationEmail(userDto.getUsrEmail());
            }
        } catch (Exception ex) {
            Logger.getLogger(RegisterUserController.class.getName()).log(Level.SEVERE, "Error saving user.", ex);
            new Message().showModal(Alert.AlertType.ERROR, "Save User", getStage(), "An error occurred while saving the user.");
        }
    }

    private List<UserDto> getUserList() {
        try {
            UserService userService = new UserService();
            Response response = userService.getUsers();
            if (response.getState()) {
                return (List<UserDto>) response.getResult("Users");
            } else {
                new Message().showModal(Alert.AlertType.ERROR, "Get Users", getStage(), response.getMessage());
                return new ArrayList<>();
            }
        } catch (Exception ex) {
            Logger.getLogger(RegisterUserController.class.getName()).log(Level.SEVERE, "Error fetching user list", ex);
            new Message().showModal(Alert.AlertType.ERROR, "Get Users", getStage(), "An error occurred while fetching the user list.");
            return new ArrayList<>();
        }
    }

    public void validateFirstUser() {
        List<UserDto> userList = getUserList();

        if (userList == null || userList.isEmpty() || userList.size()==1) {
            createSecuritySystem();
            createRole();
            assignRole();
        }else{/*createRole();*/}       
    }

    private void createSecuritySystem() {
        try {
            systemDto.setSystName("Security");
            systemDto.setSystDescription("First user security system");
            systemDto.setSystVersion(1L);

            SystemsService systemsService = new SystemsService();
            selectedSystem = new cr.ac.una.sigeceunasecurity.model.SystemsDto((SystemsDto) systemsService.saveSystem(systemDto).getResult("System"));

        } catch (Exception ex) {
            new Message().showModal(Alert.AlertType.ERROR, "Error creating system", getStage(), "An error occurred while creating the system: " + ex.getMessage());
            Logger.getLogger(EditUserInformationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void createRole() {
        if (this.systemDto != null && this.userDto != null) {
            roleDto.setRolName("Admin");
            roleDto.setRolVersion(1L);
            roleDto.setSystId(selectedSystem.getSystId());

            RoleService roleService = new RoleService();
            selectedRole = new cr.ac.una.sigeceunasecurity.model.RoleDto((RoleDto) roleService.saveRole(roleDto).getResult("Role"));
        } else {
            new Message().showModal(Alert.AlertType.ERROR, "User or system not selected", getStage(), "Select a user and system to save a role");
        }
    }

    private void assignRole() {
        Collection<cr.ac.una.sigeceunasecurity.model.RoleDto> userRoles = userDto.getRoleCollection();

        if (selectedRole != null && userDto != null) {
            userRoles.add(selectedRole);
            userDto.setRoleCollection(userRoles);

            UserService userService = new UserService();
            userDto = new UserDto((cr.ac.una.sigeceunasecurityws.controller.UserDto) userService.saveUser(userDto.getUser()).getResult("User"));
        } else {
            System.out.println("There aren't roles");
        }
    }

    private boolean createUser() {
        invalidFields = validateRequiredFields();
        validateRequiredPhoto();
        if (!invalidFields.isEmpty()) {
            new Message().showModal(Alert.AlertType.ERROR, "Save User", getStage(), invalidFields);
            return false;
        } else {
            try {
                UserService userService = new UserService();
                System.out.println(userDto.getUsrIdentification()+" identificacioon");
                Response response = userService.saveUser(userDto.getUser());

                if (!response.getState()) {
                    new Message().showModal(Alert.AlertType.ERROR, "Save User", getStage(), response.getMessage());
                    return false;
                } else {
                    unbindUser();    
                    userDto = new UserDto((cr.ac.una.sigeceunasecurityws.controller.UserDto) response.getResult("User"));
                    validateFirstUser();
                    bindUser(false);
                    txtId.setDisable(true);
                    new Message().showModal(Alert.AlertType.INFORMATION, "Save User", getStage(), "User saved successfully.");

                    return true;
                }
            } catch (Exception ex) {
                new Message().showModal(Alert.AlertType.ERROR, "Save User", getStage(), "An error occurred while saving the user: " + ex.getMessage());
                Logger.getLogger(EditUserInformationController.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
    }

    private void sendActivationEmail(String email) {
        UserService userService = new UserService();
        Response response = userService.sendActivationLink(email);
        if (!response.getState()) {
            new Message().showModal(Alert.AlertType.ERROR, "Activation Email", getStage(), "Failed to send activation email: " + response.getMessage());
        } else {
            new Message().showModal(Alert.AlertType.INFORMATION, "Activation Email", getStage(), "Activation email sent successfully.");
        }
    }

    /*User photo*/
    @FXML
    private void onActionBtnTakePhoto(ActionEvent event) {

        FlowController.getInstance().goViewInWindowModal("WebCamView", getStage(), false);
        Platform.runLater(() -> {
            loadUserPhoto();
        });
    }

    private void loadUserPhoto() {
        Platform.runLater(() -> {
            Image image = (Image) AppContext.getInstance().get("photoUser");
            if (image != null) {
                imgUserPhoto.setImage(image);
                // Convertir la imagen a Base64
                String base64ToString = imageToBase64(image);
                if (base64ToString != null) {
                    this.userDto.setUsrPhoto(base64ToString);
                } else {
                    System.out.println("Error converting image to Base64");
                }
            } else {
                System.out.println("Error taking photo");
            }
        });
    }

    private String imageToBase64(Image image) {
        try {
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", outputStream);
            byte[] imageBytes = outputStream.toByteArray();
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            return null;
        }
    }

    private void assignTextFileFormatting() {
        englishCheckBox.setUserData("EN");
        spanishCheckBox.setUserData("ES");
        imgUserPhoto.setUserData("Prueba.png");
        txtId.setTextFormatter(Formato.getInstance().integerFormat());
        txtName.setTextFormatter(Formato.getInstance().letrasFormat(15));
        txtLastName.setTextFormatter(Formato.getInstance().letrasFormat(15));
        txtSecondLastName.setTextFormatter(Formato.getInstance().letrasFormat(15));
        txtPhoneNumber.setTextFormatter(Formato.getInstance().integerFormat());
        txtAnotherPhoneNumber.setTextFormatter(Formato.getInstance().integerFormat());
        txtEmail.setTextFormatter(Formato.getInstance().maxLengthFormat(40));
        txtIdentification.setTextFormatter(Formato.getInstance().maxLengthFormat(10));
        txtPassword.setTextFormatter(Formato.getInstance().maxLengthFormat(15));
    }

    private void clearFields() {
        txtId.clear();
        txtName.clear();
        txtLastName.clear();
        txtSecondLastName.clear();
        txtPhoneNumber.clear();
        txtAnotherPhoneNumber.clear();
        txtIdentification.clear();
        txtPassword.clear();
        txtEmail.clear();
        imgUserPhoto.setImage(null);
    }
    
    @FXML
    void onActionBtnNew(ActionEvent event) {
        if (new Message().showConfirmation("Clear User", getStage(), "Are you sure you want to clear the record?")) {
            createNewUser();
            imgUserPhoto.setImage(null);
        }
    }
}
