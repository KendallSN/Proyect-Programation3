package cr.ac.una.sigeceunasecurity.controller;

import cr.ac.una.sigeceunasecurity.model.UserDto;
import cr.ac.una.sigeceunasecurity.service.UserService;
import cr.ac.una.sigeceunasecurity.util.AppContext;
import cr.ac.una.sigeceunasecurity.util.BindingUtils;
import cr.ac.una.sigeceunasecurity.util.FlowController;
import cr.ac.una.sigeceunasecurity.util.Formato;
import cr.ac.una.sigeceunasecurity.util.Message;
import cr.ac.una.sigeceunasecurity.util.Response;
import io.github.palexdev.mfxcore.utils.fx.SwingFXUtils;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javax.imageio.ImageIO;

public class EditUserInformationController extends Controller {

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
    private RadioButton spanishCheckBox;
    @FXML
    private ToggleGroup tggLanguage;
    @FXML
    private RadioButton englishCheckBox;
    @FXML
    private ImageView imgUserPhoto;
    @FXML
    private TextField txtIdentification;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private TextField txtEmail;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnSave;
    UserDto userDto;
    List<Node> required = new ArrayList<>();
    private boolean areValid = true;
    private String invalidFields = "";
    @FXML
    private Button btnChangePhoto;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnNew;

    @Override
    public void initialize() {
        assignTextFileFormatting();
        indicateRequired();
    }

    @FXML
    private void onActionBtnDelete(ActionEvent event) {
        
        try {
            if (userDto.getUsrId() == null) {
                new Message().showModal(Alert.AlertType.ERROR, "Delete User", getStage(), "You must load the user you want to delete.");
            } else {
                UserService userService = new UserService();
                Response response = userService.deleteUserById(userDto.getUsrId());
                if (!response.getState()) {
                    new Message().showModal(Alert.AlertType.ERROR, "Delete User", getStage(), response.getMessage());
                } else {
                    new Message().showModal(Alert.AlertType.INFORMATION, "Delete User", getStage(), "User successfully deleted.");
                    newUser();
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(EditUserInformationController.class.getName()).log(Level.SEVERE, "Error deleting user.", ex);
            new Message().showModal(Alert.AlertType.ERROR, "Delete User", getStage(), "An error occurred while deleting the user.");
        }
    }

    @FXML
    private void OnActionBtnNew(ActionEvent event
    ) {
        if (new Message().showConfirmation("Clear User", getStage(), "Are you sure you want to clear the record?")) {
            newUser();
        }
    }

    @FXML
    private void onActionBtnChangePhoto(ActionEvent event
    ) {
        FlowController.getInstance().goViewInWindowModal("WebCamView", getStage(), false);
        Platform.runLater(() -> {
            loadUserPhoto();
        });
    }

    @FXML
    private void OnActionBtnSearch(ActionEvent event) {
        FlowController.getInstance().goViewInWindowModal("SearchUserView", getStage(), false);
        userDto = (UserDto) AppContext.getInstance().get("selectedUser");
        loaduser(userDto.getUsrId());
    }

    @FXML
    private void onActionBtnSave(ActionEvent event
    ) {
        if (validateRequiredFields().isEmpty()) {
            try {
                UserService service = new UserService();
                Response response = service.saveUser(userDto.getUser());
                if (response.getState()) {
                    new Message().showModal(Alert.AlertType.INFORMATION, "Save User", getStage(), "User saved successfully.");
                } else {
                    new Message().showModal(Alert.AlertType.ERROR, "Save User", getStage(), response.getMessage());
                }
            } catch (Exception ex) {
                Logger.getLogger(EditUserInformationController.class.getName()).log(Level.SEVERE, "Error saving user.", ex);
                new Message().showModal(Alert.AlertType.ERROR, "Save User", getStage(), "An error occurred while saving the user.");
            }
        } else {
            new Message().showModal(Alert.AlertType.WARNING, "Validation", getStage(), validateRequiredFields());
        }
    }

    @FXML
    private void onKeyPressTxtId(KeyEvent event
    ) {
        if (event.getCode() == KeyCode.ENTER && !txtId.getText().isBlank()) {
            loaduser(Long.valueOf(txtId.getText()));
        }
    }

    private void newUser() {
        unbindUser();
        userDto = new UserDto();
        bindUser(true);
        txtId.clear();
        txtId.requestFocus();
    }

    private void loaduser(Long id) {
        try {
            UserService service = new UserService();
            Response response = service.getUserById(id);

            if (response.getState()) {
                cr.ac.una.sigeceunasecurityws.controller.UserDto userDto=  (cr.ac.una.sigeceunasecurityws.controller.UserDto) response.getResult("User");
                UserDto user= new UserDto(userDto);
                if (user != null) {
                    //unbindUser();
                    this.userDto = user;
                    bindUser(false);
                    validateRequiredFields();
                } else {
                    new Message().showModal(Alert.AlertType.ERROR, "Load User", getStage(), "User with ID " + id + " not found.");
                }
            } else {
                new Message().showModal(Alert.AlertType.ERROR, "Load User", getStage(), response.getMessage());
            }
        } catch (Exception ex) {
            Logger.getLogger(EditUserInformationController.class.getName()).log(Level.SEVERE, "Error loading user.", ex);
            new Message().showModal(Alert.AlertType.ERROR, "Load User", getStage(), "An error occurred while loading the user.");
        }
    }

    private void loadUserPhoto() {
        Platform.runLater(() -> {
            Image image = (Image) AppContext.getInstance().get("photoUser");
            if (image != null) {
                imgUserPhoto.setImage(image);
                /* Convertir la imagen a Base64 y asignarla al objeto UserDto*/
                String base64String = imageToBase64(image);
                if (base64String != null) {
                    this.userDto.setUsrPhoto(base64String);
                } else {
                    System.out.println("Error converting image to Base64");
                }
            } else {
                System.out.println("Error taking photo");
            }
        });
    }

    private void unbindUser() {
        txtId.textProperty().unbind();
        txtName.textProperty().unbindBidirectional(userDto.usrName);
        txtLastName.textProperty().unbindBidirectional(userDto.usrLastname);
        txtSecondLastName.textProperty().unbindBidirectional(userDto.usrSurname);
        txtPhoneNumber.textProperty().unbindBidirectional(userDto.usrCelphonenumber);
        txtAnotherPhoneNumber.textProperty().unbindBidirectional(userDto.usrTelephone);
        txtEmail.textProperty().unbindBidirectional(userDto.usrEmail);
        txtPassword.textProperty().unbindBidirectional(userDto.usrPassword);
        txtIdentification.textProperty().unbindBidirectional(userDto.usrIdentification);

        BindingUtils.unbindToggleGroupToProperty(tggLanguage, userDto.usrLanguage);
        imgUserPhoto.imageProperty().unbind();
    }

    private void bindUser(Boolean nuevo) {
        if (!nuevo) {
            txtId.textProperty().bind(userDto.usrId);
        }
        txtName.textProperty().bindBidirectional(userDto.usrName);
        txtLastName.textProperty().bindBidirectional(userDto.usrLastname);
        txtSecondLastName.textProperty().bindBidirectional(userDto.usrSurname);
        txtPhoneNumber.textProperty().bindBidirectional(userDto.usrCelphonenumber);
        txtAnotherPhoneNumber.textProperty().bindBidirectional(userDto.usrTelephone);
        txtEmail.textProperty().bindBidirectional(userDto.usrEmail);
        txtPassword.textProperty().bindBidirectional(userDto.usrPassword);
        txtIdentification.textProperty().bindBidirectional(userDto.usrIdentification);
        BindingUtils.bindToggleGroupToProperty(tggLanguage, userDto.usrLanguage);

        if (userDto.getUsrPhoto() != null && !userDto.getUsrPhoto().isEmpty()) {
            Image userImage = base64ToImage(userDto.getUsrPhoto());
            if (userImage != null) {
                imgUserPhoto.setImage(userImage);
            } else {
                imgUserPhoto.setImage(null);
            }
        } else {
            imgUserPhoto.setImage(null);
        }
    }

    /*Cargar la imagen del usuario*/
    private Image base64ToImage(String base64String) {
        try {
            byte[] imageBytes = Base64.getDecoder().decode(base64String);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
            return new Image(inputStream);
        } catch (Exception e) {
            new Message().showModal(Alert.AlertType.ERROR, "Load User", getStage(), "An error occurred while loading the user.");
            return null;
        }
    }

    /*Actualizar la foto del usuario*/
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

    private void indicateRequired() {
        required.clear();
        required.addAll(Arrays.asList(
                txtName, txtLastName,
                txtSecondLastName, txtIdentification,
                txtPhoneNumber, txtPassword, txtEmail));
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

    private void assignTextFileFormatting() {
        englishCheckBox.setUserData("EN");
        spanishCheckBox.setUserData("ES");
        imgUserPhoto.setUserData("Prueba.png");
        txtId.setTextFormatter(Formato.getInstance().integerFormat());
        txtName.setTextFormatter(Formato.getInstance().letrasFormat(15));
        txtLastName.setTextFormatter(Formato.getInstance().letrasFormat(15));
        txtSecondLastName.setTextFormatter(Formato.getInstance().letrasFormat(15));
        txtPhoneNumber.setTextFormatter(Formato.getInstance().maxLengthFormat(10));
        txtAnotherPhoneNumber.setTextFormatter(Formato.getInstance().maxLengthFormat(10));
        txtEmail.setTextFormatter(Formato.getInstance().maxLengthFormat(40));
        txtIdentification.setTextFormatter(Formato.getInstance().maxLengthFormat(10));
        txtPassword.setTextFormatter(Formato.getInstance().maxLengthFormat(15));
    }
}