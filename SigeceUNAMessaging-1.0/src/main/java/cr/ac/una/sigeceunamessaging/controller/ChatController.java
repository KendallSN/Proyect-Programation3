package cr.ac.una.sigeceunamessaging.controller;

import cr.ac.una.sigeceunamessaging.model.AudioController;
import cr.ac.una.sigeceunamessaging.model.ChatDto;
import cr.ac.una.sigeceunamessaging.model.FileController;
import cr.ac.una.sigeceunamessaging.model.FileDto;
import cr.ac.una.sigeceunamessaging.model.MessageDto;
import cr.ac.una.sigeceunamessaging.model.UserDto;
import cr.ac.una.sigeceunamessaging.service.ChatService;
import cr.ac.una.sigeceunamessaging.service.FileService;
import cr.ac.una.sigeceunamessaging.service.MessageService;
import cr.ac.una.sigeceunamessaging.service.UserService;
import cr.ac.una.sigeceunamessaging.util.AppContext;
import cr.ac.una.sigeceunamessaging.util.Message;
import cr.ac.una.sigeceunamessaging.util.Response;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;


public class ChatController extends Controller implements Initializable {
    private UserService userService=new UserService();
    private ChatService chatService=new ChatService();
    private FileService fileService = new FileService();
    private MessageService messageService=new MessageService();
    private UserDto loggedUser;
    private UserDto selectedUser;
    private ChatDto selectedChat;
    private MessageDto message;
    private List<UserDto> fullList;
    private ObservableList<UserDto>list=FXCollections.observableArrayList();
    private List<MessageDto>messagesList;
    private List<FileDto>filesToDelete;
    private List<FileDto>listFiles =new ArrayList<>();
    boolean fileLoad=false;
    private FilteredList<UserDto> filteredUsers;
    private MediaPlayer mediaPlayer;
    private AudioController audioController;
    private FileController fileController;
   
    @FXML
    ListView<UserDto> listViewChats;
    
    @FXML
    private Button btn_Send;

    @FXML
    private Label label_Addressee;
    
    @FXML
    private TextArea txtField_Message;
    
    @FXML
    private VBox VBox_ChatMessages;
    
    @FXML
    private Button btn_deleteChat;
    
     @FXML
    private TextArea txtArea_Search;
     
    @FXML
    private ImageView ImgView_PhotoChat;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        audioController = new AudioController();
        fileController = new FileController();
        fullList=(List<UserDto>)userService.getUsers().getResult("Users");
        list=FXCollections.observableArrayList(fullList);
        filteredUsers = new FilteredList<>(list, p -> true);
        listViewChats.setItems(filteredUsers);
        listViewChats.setCellFactory(param -> new ListCell<UserDto>() {
            private final ImageView imageView = new ImageView();
            private final Label nameLabel = new Label();
            private final HBox hbox = new HBox(5, imageView, nameLabel);

            {
                // Configurar el ImageView
                imageView.setFitWidth(40);
                imageView.setFitHeight(40);
                
                nameLabel.setStyle("-fx-font-size: 14px;");
                // Configurar el HBox
                hbox.setSpacing(5);
                hbox.setPadding(new Insets(5));
                hbox.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
                setGraphic(hbox);
            }

            @Override
            protected void updateItem(UserDto item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    nameLabel.setText(null);
                    imageView.setImage(null);
                } else {
                    // Configurar el contenido del ListCell
                    nameLabel.setText(item.getUsrName()+" "+item.getUsrSurname()+ "   Estado="+getState(item.getUsrState()));

                    // Configurar la imagen
                    imageView.setImage(base64ToImage(item.getUsrPhoto()));

                    setGraphic(hbox);
                }
            }
        });

        // Filter Settings
        this.txtArea_Search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredUsers.setPredicate(user -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return user.getUsrName().toLowerCase().contains(lowerCaseFilter)
                        || user.getUsrSurname().toLowerCase().contains(lowerCaseFilter);
            });
        });
        
        this.message=new MessageDto();
        newMessage();
    }

    @Override
    public void initialize() {
        this.fileLoad=false;
        this.filesToDelete=new ArrayList<>();
        this.listFiles = new ArrayList<>();
        this.loggedUser=(UserDto)AppContext.getInstance().get("User");
        this.listViewChats.getSelectionModel().clearSelection();
        this.label_Addressee.setText("");
        this.ImgView_PhotoChat.setImage(null);
        this.selectedChat=new ChatDto();
        this.selectedUser=new UserDto();
        this.VBox_ChatMessages.getChildren().clear();
    }
    
    public Image base64ToImage(String base64String) {/*Este metodo no se usa aquí pero es el de pasar de base64 a Image para ponerlo en el imageView*/
        try {
            byte[] imageBytes = Base64.getDecoder().decode(base64String);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
            return new Image(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private String getState(String estado) {
        switch (estado) {
            case "A":
                return "Activo";
            case "I":
                return "Inactivo";
            default:
                return "Inactivo";
        }
    }
    @FXML
    public void handleUserClick(MouseEvent event) {
        this.selectedUser=new UserDto();
        this.selectedChat=new ChatDto();
        selectedUser = listViewChats.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            this.label_Addressee.setText(selectedUser.getUsrName()+" "+selectedUser.getUsrLastname());
            this.ImgView_PhotoChat.setImage(base64ToImage(selectedUser.getUsrPhoto()));
            loadChat();
        }
    }
    
    @FXML
    void onActionBtnSend(ActionEvent event) {
        if(this.selectedChat.getChtId()==null){
            new Message().showModal(Alert.AlertType.ERROR, "No chat selected", getStage(), "You have to select a chat to send a message");
            this.txtField_Message.clear();
        }
        else{
            try {   
                    this.message.setChtId(this.selectedChat.getChtId());
                    this.message.setUsrIdSender(this.loggedUser.getUsrId());
                    if(this.fileLoad){
                        createFilesDto();                   
                    }
                    Response response = messageService.saveMessage(this.message);
                    if (!response.getState()) {
                        new Message().showModal(Alert.AlertType.ERROR, "Save message", getStage(), response.getMessage());
                    } else {
                        this.txtField_Message.clear();
                        this.listFiles.clear();
                        loadMessages();
                        displayMessages();
                        unbindMessage();
                        message = (MessageDto) response.getResult("Message");
                        bindMessage(false);
                    }

            } catch (Exception ex) {
                Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, "An error ocurred while saving the message.", ex);
                new Message().showModal(Alert.AlertType.ERROR, "Save message", getStage(), "An error ocurred while saving the message.");
            }
           newMessage();
        }
    }
    
    @FXML
    void onActionBtnDeleteChat(ActionEvent event) {
        if(this.selectedChat.getChtId()==null){
          new Message().showModal(Alert.AlertType.INFORMATION, "Delete chat", getStage(), "You have to select a chat to delete.");  
        }
        else if(this.messagesList.isEmpty()){
          new Message().showModal(Alert.AlertType.INFORMATION, "Delete chat", getStage(), "This chat has not messages.");
        }
        else{
            boolean userConfirmed = new Message().showConfirmation("Delete chat", getStage(), "Are you sure you want to delete this chat?.");
            if(userConfirmed){
               try {
                    Response response = chatService.deleteChat(this.selectedChat.getChtId());
                    if (!response.getState()) {
                        new Message().showModal(Alert.AlertType.ERROR, "Delete chat", getStage(), response.getMessage());
                    } else {
                       this.selectedUser=new UserDto();
                       this.selectedChat=new ChatDto();
                       this.label_Addressee.setText("");
                       this.ImgView_PhotoChat.setImage(null);
                       this.listViewChats.getSelectionModel().clearSelection();
                       this.VBox_ChatMessages.getChildren().clear();
                        new Message().showModal(Alert.AlertType.INFORMATION, "Delete chat", getStage(), "Chat deleted successfully.");
                    }         
            } catch (Exception ex) {
                Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, "An error ocurred while deleting the chat.", ex);
                new Message().showModal(Alert.AlertType.ERROR, "Delete chat", getStage(), "An error ocurred while deleting the chat.");
            }  
            }
        }
    }
    
    private void loadChat() {
        createChat();
        loadMessages();
        displayMessages();
    }
    
    public void newMessage(){
        unbindMessage();
        message = new MessageDto();
        bindMessage(true);
    }
    
    public void commonChat(){
        this.selectedChat=new ChatDto();
        Response response=chatService.getChatByUsers(this.loggedUser.getUsrId(),this.selectedUser.getUsrId());
        if(response.getState()){
            this.selectedChat=(ChatDto)response.getResult("Chat");
        }
    }
    
    public void createChat(){
        commonChat();
        if(this.selectedChat.getChtId()==null){
           this.selectedChat.setUsrIdUser1(this.loggedUser.getUsrId());
           this.selectedChat.setUsrIdUser2(this.selectedUser.getUsrId());
           this.selectedChat=(ChatDto)chatService.saveChat(this.selectedChat).getResult("Chat"); 
        }    
    }
    
    private void displayMessages() {
        this.VBox_ChatMessages.getChildren().clear();
        for (MessageDto msg : this.messagesList) {
            HBox messageContainer = new HBox();
            messageContainer.setSpacing(5);

            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            Label lblMessage = new Label(msg.getMsgMessage());
            Label lblTime = new Label(msg.getMsgDateAndHour().format(format));
            lblTime.setStyle("-fx-font-size: 10px; -fx-text-fill: blue;");

            VBox messageBox = new VBox(lblMessage, lblTime);
            messageBox.setSpacing(5);

            // Estilos según el remitente
            if (Objects.equals(msg.getUsrIdSender(), this.loggedUser.getUsrId()) ||
                    (msg.getUsrIdSender().equals(this.loggedUser.getUsrId()) &&
                    msg.getUsrIdSender().equals(this.selectedUser.getUsrId()))) {
                lblMessage.setStyle("-fx-background-color: lightgreen; -fx-text-fill: black;");
                messageContainer.setAlignment(Pos.CENTER_RIGHT);              
            } else if (Objects.equals(msg.getUsrIdSender(), this.selectedUser.getUsrId())) {
                lblMessage.setStyle("-fx-background-color: lightblue; -fx-text-fill: black;");
                messageContainer.setAlignment(Pos.CENTER_LEFT);
            }

            // Add files if they exist
        if (msg.getFileCollection() != null && !msg.getFileCollection().isEmpty()) {
            for (FileDto file : msg.getFileCollection()) {
                // container for the file and its buttons
                HBox fileContainer = new HBox();
                fileContainer.setSpacing(10);

                Label fileLabel = new Label("📎 " + file.getFleName() + "." + file.getFleType());
                fileLabel.setStyle("-fx-text-fill: blue; -fx-underline: true; -fx-font-size: 18px;");
                fileLabel.setPadding(new Insets(2, 5, 2, 5));

                // Download file
                if ("mp3".equalsIgnoreCase(file.getFleType())) {
                    Button btnPlay = new Button();
                    Button btnStop = new Button();
                    
                    btnPlay.setId("btnPlay");
                    btnStop.setId("btnStop");

                    // Configurar los eventos para los botones
                    btnPlay.setOnAction(event -> audioController.handlePlayButton(file.getFleContent()));
                    btnStop.setOnAction(event -> audioController.stopAudio());

                    // Añadir el label y los botones al contenedor del archivo
                    fileContainer.getChildren().addAll(fileLabel, btnPlay, btnStop);
                } else {
                    // Evento de clic para descargar el archivo
                    fileLabel.setOnMouseClicked(event -> downloadFile(file));
                    fileContainer.getChildren().add(fileLabel);
                }

                    messageBox.getChildren().add(fileContainer);
                }
            }

            messageContainer.getChildren().add(messageBox);

            // Evento de clic para eliminar mensaje
            messageContainer.setOnMouseClicked(event -> {
                deleteMessageById(msg.getMsgId());
            });

            this.VBox_ChatMessages.getChildren().add(messageContainer);
        }
    }
    
    private void downloadFile(FileDto fileDto) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save file");

        // Establecer el nombre del archivo por defecto
        fileChooser.setInitialFileName(fileDto.getFleName() + "." + fileDto.getFleType());

        // Filtro para el formato del archivo a guardar
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Formato original", "*." + fileDto.getFleType())
        );

        Stage stage = (Stage) this.VBox_ChatMessages.getScene().getWindow();
        File fileToSave = fileChooser.showSaveDialog(stage);

        if (fileToSave != null) {
            try {
                fileController.writeBinaryDataToFile(fileToSave, fileDto.getFleContent());
                System.out.println("Archivo guardado exitosamente en: " + fileToSave.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void loadMessages() {
        Response response=messageService.getMessagesByChat(this.selectedChat.getChtId());
        if(response.getState()){
            List<MessageDto> fullMessagesList=(List<MessageDto>)response.getResult("Messages");
            this.messagesList=fullMessagesList.stream().sorted(Comparator.comparingLong(MessageDto::getMsgId))
            .collect(Collectors.toList());                  
        }
    }
    
    private void bindMessage(Boolean nuevo) {       
        this.txtField_Message.textProperty().bindBidirectional(this.message.msgMessage);     
    }
    
    private void unbindMessage() {
        this.txtField_Message.textProperty().unbindBidirectional(this.message.msgMessage);
    }
    
    private void deleteMessageById(Long msgId) {
        boolean userConfirmed = new Message().showConfirmation("Delete message", getStage(), "Are you sure you want to delete this message?.");
        if(userConfirmed){
            try {
                    Response response = messageService.deleteMessage(msgId);
                    if (!response.getState()) {
                        new Message().showModal(Alert.AlertType.ERROR, "Delete message", getStage(), response.getMessage());
                    } else {
                        loadMessages();
                        displayMessages();
                        new Message().showModal(Alert.AlertType.INFORMATION, "Delete message", getStage(), "Message deleted successfully.");
                    }         
            } catch (Exception ex) {
                Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, "An error ocurred while deleting the message.", ex);
                new Message().showModal(Alert.AlertType.ERROR, "Delete message", getStage(), "An error ocurred while deleting the message.");
            }
        }
    }
    
    @FXML
    void onActionBtnLoadFile(ActionEvent event) {
        if(this.selectedChat.getChtId()==null){
            new Message().showModal(Alert.AlertType.ERROR, "No chat selected", getStage(), "You have to select a chat to send a message");
            this.txtField_Message.clear();
        }
        else{
            String fileFormat;
            String fileName;
            Byte[] binaryData;
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Seleccionar archivo");

            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Todos los archivos", "*.*")
            );

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            File selectedFile = fileChooser.showOpenDialog(stage);

            if (selectedFile != null) {
                try {
                    binaryData = fileController.convertFileToBinary(selectedFile);
                    // File Format
                    fileName = fileController.getFileName(selectedFile);
                    fileFormat = fileController.getFileExtension(selectedFile);
                    listFiles.add(new FileDto(null,fileFormat,fileName,binaryData));
                    this.fileLoad=true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void createFilesDto() {
        try {
            for (FileDto fileDto : this.listFiles) {
                if(fileDto.getFleId()==null){
                    Response response = this.fileService.saveFile(fileDto);
                    FileDto savedFile = (FileDto) response.getResult("File");

                    if (!response.getState()) {
                        new Message().showModal(Alert.AlertType.ERROR, "Enviar archivo", getStage(), response.getMessage());
                        this.fileLoad=false;
                    } else {
                        this.message.getFileCollection().add(savedFile);                     
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, "Ocurrió un error mientras se enviaba el archivo.", ex);
            new Message().showModal(Alert.AlertType.ERROR, "Enviar archivo", getStage(), "Ocurrió un error mientras se enviaba el archivo.");
        }
    }
    
}
