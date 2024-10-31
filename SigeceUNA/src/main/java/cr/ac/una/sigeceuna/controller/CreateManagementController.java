
package cr.ac.una.sigeceuna.controller;

import cr.ac.una.sigeceuna.model.ActivityDto;
import cr.ac.una.sigeceuna.model.FileDto;
import cr.ac.una.sigeceuna.model.ManagementDto;
import cr.ac.una.sigeceuna.model.ManagementaprobationDto;
import cr.ac.una.sigeceuna.model.SubactivityDto;
import cr.ac.una.sigeceuna.model.UserDto;
import cr.ac.una.sigeceuna.service.ActivityService;
import cr.ac.una.sigeceuna.service.FileService;
import cr.ac.una.sigeceuna.service.ManagementAprobationService;
import cr.ac.una.sigeceuna.service.ManagementService;
import cr.ac.una.sigeceuna.service.SubactivityService;
import cr.ac.una.sigeceuna.service.UserService;
import cr.ac.una.sigeceuna.util.AppContext;
import cr.ac.una.sigeceuna.util.FlowController;
import cr.ac.una.sigeceuna.util.Message;
import cr.ac.una.sigeceuna.util.Response;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class CreateManagementController extends Controller implements Initializable{
    @FXML
    private Button btn_DeleteUserAprove;

    @FXML
    private Button btn_FilterAssignedUser;

    @FXML
    private Button btn_FilterUserAprove;

    @FXML
    private Button btn_LoadFile;

    @FXML
    private Button btn_SaveManagement;

    @FXML
    private ChoiceBox<String> chb_AssignedUser;

    @FXML
    private ChoiceBox<String> chb_UserAprove;

    @FXML
    private DatePicker datePicker_ResolveDate;

    @FXML
    private TableColumn<ActivityDto, String> tblC_ActivitieName;

    @FXML
    private TableColumn<UserDto, String> tblC_FirstLastNameUserAprove;

    @FXML
    private TableColumn<ActivityDto, String> tblC_IDActivitie;

    @FXML
    private TableColumn<SubactivityDto, String> tblC_IDSubActivitie;

    @FXML
    private TableColumn<UserDto, String> tblC_IDUserAprove;

    @FXML
    private TableColumn<UserDto, String> tblC_NameUserAprove;

    @FXML
    private TableColumn<UserDto, String> tblC_SecondLastNameUserAprove;

    @FXML
    private TableColumn<SubactivityDto, String> tblC_SubActivitieName;

    @FXML
    private TableView<ActivityDto> tblV_Activities;

    @FXML
    private TableView<SubactivityDto> tblV_SubActivities;

    @FXML
    private TableView<UserDto> tblV_UserAprove;

    @FXML
    private TextField txt_FilterAssignedUser;

    @FXML
    private TextField txt_FilterUserAprove;

    @FXML
    private TextField txt_SelectedActivitieOrSubActivitie;
    
    @FXML
    private TextField txt_Id;

    @FXML
    private TextField txt_Subject;
    
    @FXML
    private TextField txt_Description;
    
    @FXML
    private Spinner<Integer> spin_Hour;

    @FXML
    private Spinner<Integer> spin_Minutes;
    
    @FXML
    private ListView<FileDto> listV_File;
    
    private ManagementDto managementDto=new ManagementDto();
      
    //Dto Selected
    private UserDto userDtoSelected;
    private ActivityDto activityDtoSelected;
    private SubactivityDto subactivityDtoSelected;
    
    //Services
    private ManagementService managementService=new ManagementService();
    private ManagementAprobationService managementaprobationService=new ManagementAprobationService();
    private UserService userService=new UserService();
    private ActivityService activityService=new ActivityService();
    private SubactivityService subactivityService=new SubactivityService();
    private FileService fileService = new FileService();
    
    //Lists to take information from the database
    private List<UserDto>chb_usersToAproveList=new ArrayList<>();
    private List<UserDto>FilteredUsersAprove=new ArrayList<>();
    private List<UserDto>usersList=new ArrayList<>();
    private List<ActivityDto>activitiesList=new ArrayList<>();
    private List<SubactivityDto>subactivitiesList=new ArrayList<>();
    List<ManagementaprobationDto>aprobations=new ArrayList<>();
    
    //Observable lists
    private ObservableList<UserDto>observableUsersDto=FXCollections.observableArrayList();
    private ObservableList<ActivityDto>observableActivitiesDto=FXCollections.observableArrayList();
    private ObservableList<SubactivityDto>observableSubactivitiesDto=FXCollections.observableArrayList();
    private ObservableList<String> usersChb_AssignedUser = FXCollections.observableArrayList();
    private ObservableList<String> usersChb_AproveUser = FXCollections.observableArrayList();
    private ObservableList<FileDto> observableListFiles = FXCollections.observableArrayList();
    
    private UserDto loggedUser;
    private String filterAprovements;
    private boolean fileLoad=false;
    List<Node> requiredSpaces = new ArrayList<>();
    private ResourceBundle bundle;
    private boolean managementLoad=false;
    @Override
    public void initialize() {
        this.managementLoad=false;
        this.bundle=FlowController.getIdioma();
        indicateRequiredSpaces();
        this.loggedUser=(UserDto) AppContext.getInstance().get("User");
        //Initialize spiners with time
        this.spin_Hour.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 12));
        this.spin_Minutes.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
        
        clearSpaces();
        
        this.filterAprovements="";
        this.tblV_UserAprove.getSelectionModel().clearSelection();
        this.tblV_Activities.getSelectionModel().clearSelection();
        this.tblV_SubActivities.getSelectionModel().clearSelection();
        
        //Initialize dtos selected
        this.activityDtoSelected=new ActivityDto();
        this.subactivityDtoSelected=new SubactivityDto();
        this.userDtoSelected=new UserDto();
        
        //clear observableLists
        this.observableUsersDto.clear();
        this.observableActivitiesDto.clear();
        this.observableSubactivitiesDto.clear();
        this.usersChb_AproveUser.clear();
        this.usersChb_AssignedUser.clear();
        this.observableListFiles.clear();
        
        this.listV_File.setItems(observableListFiles);
        this.FilteredUsersAprove.clear();
        
        this.tblV_UserAprove.setItems(observableUsersDto);
        
        //Fill lists 
        this.usersList=((List<UserDto>) this.userService.getUsers().getResult("Users")).stream()
                .filter(user->user.getUsrState().equals("A")).toList();
        this.chb_usersToAproveList=new ArrayList<>();
        this.chb_usersToAproveList.addAll(usersList);
        this.FilteredUsersAprove.addAll(usersList);
        
        //Initialize choiceBoxes
        if(this.usersList!=null && !this.usersList.isEmpty()){
            inicialiceUserAssigned();
            inicialiceUserAprove();
        }
        this.activitiesList=(List<ActivityDto>) this.activityService.getActivities().getResult("Activities");
        this.observableActivitiesDto.addAll(this.activitiesList);
        this.tblV_Activities.setItems(observableActivitiesDto);
        
        this.subactivitiesList=(List<SubactivityDto>) this.subactivityService.getSubactivities().getResult("Subactivities");
        this.observableSubactivitiesDto.addAll(subactivitiesList);
        this.tblV_SubActivities.setItems(observableSubactivitiesDto);
        
        //Table selections
         this.tblV_UserAprove.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue != null) {
                    this.userDtoSelected = newValue;
                }
            }
        );
         
        this.tblV_Activities.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue != null) {
                    this.txt_SelectedActivitieOrSubActivitie.clear();
                    this.txt_SelectedActivitieOrSubActivitie.setText(bundle.getString("activity")+" "+newValue.getActName());
                    this.managementDto.setActId(newValue.getActId());
                }
            }
        );
        
        this.tblV_SubActivities.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue != null) {
                    this.txt_SelectedActivitieOrSubActivitie.clear();
                    this.txt_SelectedActivitieOrSubActivitie.setText(bundle.getString("subactivity")+" "+newValue.getSactName());
                    this.managementDto.setSactId(newValue.getSactId());
                }
            }
        );
    }
    
    private void inicialiceUserAssigned(){
        //String usersList
        for (UserDto user : this.usersList) {
            usersChb_AssignedUser.add(user.getUsrId() + " - " + user.getUsrName() + " " + user.getUsrSurname() + " " + user.getUsrLastname());
        }

        this.chb_AssignedUser.setItems(usersChb_AssignedUser);

        //listener
        this.chb_AssignedUser.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String[] partes = newValue.split(" - ");
                Long idSelected = Long.valueOf(partes[0]);

                // Search the selected userById
                for (UserDto user : this.usersList) {
                    if (user.getUsrId().equals(idSelected)) {
                        this.managementDto.setUsrAssignedId(user);
                        break;
                    }
                }
            }
        });  
    }
    
    private void inicialiceUserAprove(){
        //String usersList
        for (UserDto user : this.FilteredUsersAprove) {
            usersChb_AproveUser.add(user.getUsrId() + " - " + user.getUsrName() + " " + user.getUsrSurname() + " " + user.getUsrLastname());
        }

        this.chb_UserAprove.setItems(this.usersChb_AproveUser);

        //listener
        this.chb_UserAprove.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
            String[] partes = newValue.split(" - ");
            Long idSelected = Long.valueOf(partes[0]);

            // Search the selected userById
                for (UserDto user : this.usersList) {
                    if (user.getUsrId().equals(idSelected)) {
                        boolean existsInObservable = this.observableUsersDto.stream()
                        .anyMatch(existingUser -> existingUser.getUsrId().equals(user.getUsrId()));
                        if (!existsInObservable) {
                            this.observableUsersDto.add(user);
                            initializeFilteredUserAproved();
                        }
                        else{
                            new Message().showModal(Alert.AlertType.ERROR, bundle.getString("existingUser"), getStage(),bundle.getString("userAlreadyInTheTable"));
                        }
//                        this.chb_usersToAproveList.remove(user);
                        this.chb_UserAprove.getSelectionModel().clearSelection();
//                        this.tblV_UserAprove.refresh();
                        break;
                    }
                }
            }
        });  
    }
   
    private void initializeFilteredUserAproved(){
        this.FilteredUsersAprove.clear();
        this.usersChb_AproveUser.clear();
        this.chb_usersToAproveList=new ArrayList<>();
        this.chb_usersToAproveList.addAll(usersList);
        if(this.filterAprovements!=""){
            this.chb_usersToAproveList.stream().filter(s->(s.getUsrName() + " " + s.getUsrSurname() + " " + s.getUsrLastname()).toLowerCase()
                    .contains(this.filterAprovements.toLowerCase())).forEach(s->this.FilteredUsersAprove.add(s));
        }
        else{
            this.chb_usersToAproveList.stream().forEach(s->this.FilteredUsersAprove.add(s));
        }
        this.FilteredUsersAprove.stream().forEach(f->this.usersChb_AproveUser.add(f.getUsrId()+ " - " + f.getUsrName() + " " + f.getUsrSurname() + " " + f.getUsrLastname()));
    }
    
    private void clearSpaces(){
        this.txt_Id.clear();
        this.spin_Hour.getValueFactory().setValue(12);
        this.spin_Minutes.getValueFactory().setValue(0);
        this.txt_Description.clear();
        this.txt_FilterAssignedUser.clear();
        this.txt_FilterUserAprove.clear();
        this.managementLoad=false;
        this.fileLoad=false;
        this.observableListFiles.clear();
        this.txt_Subject.clear();
        this.datePicker_ResolveDate.setValue(null);
        this.chb_AssignedUser.getSelectionModel().clearSelection();
        this.txt_SelectedActivitieOrSubActivitie.clear();
        this.filterAprovements="";
        if(!this.observableUsersDto.isEmpty()){
            this.observableUsersDto.clear();
            initializeFilteredUserAproved();
        }     
        this.tblV_Activities.getSelectionModel().clearSelection();
        this.tblV_SubActivities.getSelectionModel().clearSelection();
        this.tblV_UserAprove.getSelectionModel().clearSelection();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TableView UsersAprove inicialization
        tblC_IDUserAprove.setCellValueFactory(new PropertyValueFactory<>("usrId"));
        tblC_NameUserAprove.setCellValueFactory(new PropertyValueFactory<>("usrName"));
        tblC_FirstLastNameUserAprove.setCellValueFactory(new PropertyValueFactory<>("usrSurname"));
        tblC_SecondLastNameUserAprove.setCellValueFactory(new PropertyValueFactory<>("usrLastname"));
        
        //TableView Activities
        tblC_IDActivitie.setCellValueFactory(new PropertyValueFactory<>("actId"));
        tblC_ActivitieName.setCellValueFactory(new PropertyValueFactory<>("actName"));
        
        //TableView Subactivities
        tblC_IDSubActivitie.setCellValueFactory(new PropertyValueFactory<>("sactId"));
        tblC_SubActivitieName.setCellValueFactory(new PropertyValueFactory<>("sactName"));
        
        chb_AssignedUser.setAccessibleText("Usuario Asignado");
        
        this.managementDto=new ManagementDto();
        
        newManagement();
        
        this.listV_File.setCellFactory(param -> new ListCell<FileDto>() {
            private Button deleteButton = new Button("x");
            private final HBox hbox = new HBox();
            private final VBox vbox = new VBox();
            private final Label fileInfoLabel = new Label();
            private final Label downloadLabel = new Label(bundle.getString("clickToDownload"));

            {
                deleteButton.setMaxWidth(15);
                deleteButton.setOnAction(e -> {
                    FileDto item = getItem();
                    if (item != null) {
                        for (int i = 0; i < observableListFiles.size(); i++) {
                            if (observableListFiles.get(i).getFleId() == item.getFleId()) {
                                observableListFiles.remove(i);
                                if(observableListFiles.isEmpty()){fileLoad=false;}
                            }
                        }
                    }
                });
                deleteButton.getStyleClass().add("button-Delete");
                downloadLabel.setStyle("-fx-font-size: 10; -fx-text-fill: grey;");
                fileInfoLabel.setStyle("-fx-font-size: 15; -fx-text-fill: black;");
                vbox.getChildren().addAll(fileInfoLabel,downloadLabel);
                hbox.getChildren().addAll(deleteButton,vbox);
            }

            @Override
            protected void updateItem(FileDto item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    fileInfoLabel.setText(item.getFleName() + "." + item.getFleType());
                    setGraphic(hbox);
                }
            }
        });
    }
    
    @FXML
    void onActionBtnDeleteUserAprove(ActionEvent event) {
        if(this.userDtoSelected.getUsrId()==null){
           new Message().showModal(Alert.AlertType.ERROR, bundle.getString("notFound"), getStage(),bundle.getString("selectAnUser"));
           return;
        }
        boolean userConfirmed = new Message().showConfirmation(bundle.getString("deleteUser"), getStage(), bundle.getString("sureToDeleteUser"));
        if(userConfirmed){
            this.observableUsersDto.remove(this.userDtoSelected);
//            this.chb_usersToAproveList.add(userDtoSelected);
            initializeFilteredUserAproved();         
            this.userDtoSelected=new UserDto();
            this.tblV_UserAprove.getSelectionModel().clearSelection();       
        }
    }

    @FXML
    void onActionBtnFilterUserAprove(ActionEvent event) {
        this.usersChb_AproveUser.clear();
        if(!this.txt_FilterUserAprove.getText().isEmpty() && !this.txt_FilterUserAprove.getText().isBlank()){
            this.filterAprovements=this.txt_FilterUserAprove.getText().toLowerCase();
//            initializeFilteredUserAproved();
            this.usersList.stream()
                .filter(f -> (f.getUsrName() + " " + f.getUsrSurname()+" " + f.getUsrLastname()).toLowerCase().contains(this.filterAprovements))
                .forEach(f->this.usersChb_AproveUser.add(f.getUsrId()+ " - " + f.getUsrName() + " " + f.getUsrSurname() + " " + f.getUsrLastname()));
        }
        else{
            this.filterAprovements="";
            List<String> allUsers = this.usersList.stream()
                .map(a -> a.getUsrId() + " - " + a.getUsrName() + " " + a.getUsrSurname() + " " + a.getUsrLastname())
                .collect(Collectors.toList());

            this.usersChb_AproveUser.setAll(allUsers);
        }
        
    }

    @FXML
    void onActionBtnFilterUserAsiggned(ActionEvent event) {
        this.usersChb_AssignedUser.clear();
        if (!this.txt_FilterAssignedUser.getText().isEmpty() && !this.txt_FilterAssignedUser.getText().isBlank()) {
            String filterText = this.txt_FilterAssignedUser.getText().toLowerCase();

            this.usersList.stream()
                .filter(f -> (f.getUsrName() + " " + f.getUsrSurname()+" " + f.getUsrLastname()).toLowerCase().contains(filterText))
                .forEach(f->this.usersChb_AssignedUser.add(f.getUsrId()+ " - " + f.getUsrName() + " " + f.getUsrSurname() + " " + f.getUsrLastname()));
        }else {         
            List<String> allUsers = this.usersList.stream()
                .map(a -> a.getUsrId() + " - " + a.getUsrName() + " " + a.getUsrSurname() + " " + a.getUsrLastname())
                .collect(Collectors.toList());

            this.usersChb_AssignedUser.setAll(allUsers);
        }
    }

    public LocalDateTime getMaxDateToSolve() {
        LocalDate date = this.datePicker_ResolveDate.getValue();
        int hour = this.spin_Hour.getValue();
        int minute = this.spin_Minutes.getValue();
        return LocalDateTime.of(date, LocalTime.of(hour, minute));
    }
    
    @FXML
    void onActionBtnLoadFile(ActionEvent event) {
        String fileFormat;
        String fileName;
        Byte[] binaryData;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(bundle.getString("selectFile"));

        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Todos los archivos", "*.*")
        );

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            try {
                binaryData = convertFileToBinary(selectedFile);
                // Obtener el formato del archivo
                fileName = getFileName(selectedFile);
                fileFormat = getFileExtension(selectedFile);
                observableListFiles.add(new FileDto(null,fileFormat,fileName,binaryData));
                this.fileLoad=true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private Byte[] convertFileToBinary(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        List<Character> binaryCharList = new ArrayList<>();

        int byteData;
        while ((byteData = fileInputStream.read()) != -1) {
            String binaryString = String.format("%8s", Integer.toBinaryString(byteData)).replace(' ', '0');
            for (char bit : binaryString.toCharArray()) {
                binaryCharList.add(bit);
            }
        }
        fileInputStream.close();

        Byte[] binaryData = new Byte[binaryCharList.size()];
        for (int i = 0; i < binaryCharList.size(); i++) {
            binaryData[i] =(byte) binaryCharList.get(i).charValue();
        }
        return binaryData;
    }
    
    private String getFileName(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(0, dotIndex);
    }
    
    private String getFileExtension(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }
    @FXML
    void handleFileClicked(MouseEvent event) {
        FileDto fileDto = this.listV_File.getSelectionModel().getSelectedItem();
        if(fileDto != null){
            downloadFile(fileDto);
        }
    }

    @FXML
    void onActionBtnReturn(ActionEvent event) {
        FlowController.getInstance().goView("ManagementView");
    }

    @FXML
    void onActionBtnSaveManagement(ActionEvent event) {
        if(this.observableUsersDto.isEmpty()){
            new Message().showModal(Alert.AlertType.ERROR, bundle.getString("saveManagement"), getStage(), bundle.getString("selectUsersToApprove"));
        }
        else{
            String invalid = validateRequiredSpaces();
            if (!invalid.isEmpty()) {
                new Message().showModal(Alert.AlertType.ERROR, bundle.getString("saveManagement"), getStage(), invalid);
            }
            else{
                try {  
                    if(this.fileLoad){
                        createFilesDto();
                    }
                    if(this.managementDto.getMgtId()==null){
                        this.managementDto.setMgtCreationdate(LocalDateTime.now());
                    }
                    this.managementDto.setMgtMaxdatetosolve(getMaxDateToSolve());
                    this.managementDto.setUsrRequestingId(this.loggedUser);
                    Response response = this.managementService.saveManagement(managementDto);
                    if (!response.getState()) {
                        new Message().showModal(Alert.AlertType.ERROR, bundle.getString("saveManagement"), getStage(), response.getMessage());
                    } else {
                        if(this.managementDto.getMgtId()==null){
                            createManagementAprobations(((ManagementDto)response.getResult("Management")).getMgtId());
                        }
                        new Message().showModal(Alert.AlertType.CONFIRMATION, bundle.getString("saveManagement"), getStage(), bundle.getString("saveSuccessManagement"));
                        clearSpaces();
                        unbindManagement();
                        bindManagement(false);
                    }
                    } catch (Exception ex) {
                        Logger.getLogger(CreateManagementController.class.getName()).log(Level.SEVERE, bundle.getString("saveErrorManagement"), ex);
                        new Message().showModal(Alert.AlertType.ERROR, bundle.getString("saveManagement"), getStage(), bundle.getString("saveErrorManagement"));
                    }
                   newManagement();
            }
        }
    }

    private void newManagement() {
        unbindManagement();
        this.managementDto = new ManagementDto();
        bindManagement(true);
    }

    private void unbindManagement() {
        this.txt_Subject.textProperty().unbindBidirectional(this.managementDto.mgtSubject);
        this.txt_Description.textProperty().unbindBidirectional(this.managementDto.mgtDescription);
    }

    private void bindManagement(boolean b) {
        this.txt_Subject.textProperty().bindBidirectional(this.managementDto.mgtSubject);
        this.txt_Description.textProperty().bindBidirectional(this.managementDto.mgtDescription);
    }
    
    private void createManagementAprobations(Long idMgt){
        try {
            this.observableUsersDto.stream().forEach(user -> {
                ManagementaprobationDto managementaprobationDto = new ManagementaprobationDto();
                managementaprobationDto.setMgtId(idMgt);
                managementaprobationDto.setUsrToaproveId(user);
                Response response = this.managementaprobationService.saveManagementaprobations(managementaprobationDto);
                if (!response.getState()) {
                    new Message().showModal(Alert.AlertType.ERROR, bundle.getString("saveAprobation"), getStage(), response.getMessage());
                }
            });
        } catch (Exception ex) {
            Logger.getLogger(CreateManagementController.class.getName()).log(Level.SEVERE, bundle.getString("saveErrorAprobation"), ex);
            new Message().showModal(Alert.AlertType.ERROR, bundle.getString("saveAprobation"), getStage(), bundle.getString("saveErrorAprobation"));
        }
    }

    private void indicateRequiredSpaces() {
        this.requiredSpaces.clear();
        this.requiredSpaces.addAll(Arrays.asList(this.txt_Description,this.txt_SelectedActivitieOrSubActivitie,this.txt_Subject
        ,this.chb_AssignedUser,this.datePicker_ResolveDate));
    }
    
    public String validateRequiredSpaces() {
        boolean valid = true;
        StringBuilder invalid = new StringBuilder();

        for (Node node : requiredSpaces) {
            if (node instanceof TextField) {
                TextField textField = (TextField) node;
                if (textField.getText() == null || textField.getText().isBlank()) {
                    valid = false;
                    invalid.append(textField.getPromptText()).append(", ");
                }
            } else if (node instanceof ChoiceBox) {
                ChoiceBox<?> choiceBox = (ChoiceBox<?>) node;
                if (choiceBox.getSelectionModel().getSelectedIndex() < 0) {
                    valid = false;
                    invalid.append(choiceBox.getAccessibleText()).append(", ");
                }
            }else if (node instanceof DatePicker) {
                DatePicker datePicker = (DatePicker) node;
                if (datePicker.getValue() == null) {
                    valid = false;
                    invalid.append(datePicker.getPromptText()).append(", ");
                }
            }
        }
        if (valid) {
            return "";
        } else {
            return bundle.getString("emptySpaces")+"[" + invalid.substring(0, invalid.length() - 2) + "].";
        }
    }

    private void downloadFile(FileDto fileDto) {
        // Crear un objeto FileChooser para guardar el archivo
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(bundle.getString("saveFile"));

        // Establecer el nombre del archivo por defecto
        fileChooser.setInitialFileName(fileDto.getFleName() + "." + fileDto.getFleType());

        // Filtro para el formato del archivo a guardar
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Formato original", "*." +  fileDto.getFleType())
        );

        // Obtener el stage actual
        Stage stage = (Stage) this.listV_File.getScene().getWindow();
        
        // Mostrar la ventana para elegir dónde guardar el archivo
        File fileToSave = fileChooser.showSaveDialog(stage);

        if (fileToSave != null) {
            try {
                // Convertir el array de chars binario a bytes y escribir en el archivo
                writeBinaryDataToFile(fileToSave, fileDto.getFleContent());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeBinaryDataToFile(File file, Byte[] binaryData) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        char[] charArray = new char[binaryData.length];
        for (int i = 0; i < binaryData.length; i++) {
            charArray[i] = (char) (binaryData[i] & 0xFF);
        }
        for (int i = 0; i < charArray.length; i += 8) {
            StringBuilder byteString = new StringBuilder();
            for (int j = 0; j < 8 && i + j < charArray.length; j++) {
                byteString.append(charArray[i + j]);
            }
            int byteValue = Integer.parseInt(byteString.toString(), 2);
            fileOutputStream.write(byteValue);
        }
        fileOutputStream.close();
    }

    private void createFilesDto() {
        try {
            for (FileDto fileDto : this.observableListFiles) {
                if (fileDto.getFleId() == null) {
                    Response response = this.fileService.saveFile(fileDto);
                    FileDto savedFile = (FileDto) response.getResult("File");

                    if (!response.getState()) {
                        new Message().showModal(Alert.AlertType.ERROR, bundle.getString("saveFile"), getStage(), response.getMessage());
                        this.fileLoad = false;
                    } else {
                        this.managementDto.getFileCollection().add(savedFile);
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(TracingsController.class.getName()).log(Level.SEVERE, bundle.getString("saveErrorFile"),ex);
            new Message().showModal(Alert.AlertType.ERROR, bundle.getString("saveFile"), getStage(), bundle.getString("saveErrorFile"));
        }
    }
    
    @FXML
    void onKeyPressedId(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER && !this.txt_Id.getText().isBlank()) {
            loadManagement(Long.valueOf(this.txt_Id.getText()));
            this.managementLoad=true;
        }
    }

    private void loadManagement(Long id) {
        this.observableListFiles.clear();
        try {
            Response response = this.managementService.getManagement(id);
            if (response.getState()) {
                this.managementDto=(ManagementDto) response.getResult("Management");
                if (managementDto != null) {
                    bindManagement(false);
                    loadOtherSpaces();
                    validateRequiredSpaces();
                } else {
                    new Message().showModal(Alert.AlertType.ERROR, bundle.getString("loadManagement"), getStage(), "ID " + id + bundle.getString("notFound"));
                }
            } else {
                new Message().showModal(Alert.AlertType.ERROR, bundle.getString("loadManagement"), getStage(), response.getMessage());
            }
        } catch (Exception ex) {
            Logger.getLogger(CreateManagementController.class.getName()).log(Level.SEVERE, bundle.getString("loadErrorManagement"), ex);
            new Message().showModal(Alert.AlertType.ERROR, bundle.getString("loadManagement"), getStage(), bundle.getString("loadErrorManagement"));
        }
    }

    private void loadOtherSpaces() {
        if(this.managementDto.getActId()!=null){
            this.subactivityDtoSelected=new SubactivityDto();
            this.activityDtoSelected=(ActivityDto) activityService.getActivity(this.managementDto.getActId()).getResult("Activity");
            this.txt_SelectedActivitieOrSubActivitie.setText(bundle.getString("activity")+" " + this.activityDtoSelected.getActName());
        }
        if(this.managementDto.getSactId()!=null){
            this.activityDtoSelected=new ActivityDto();
            this.subactivityDtoSelected=(SubactivityDto) subactivityService.getSubactivity(this.managementDto.getSactId()).getResult("Subactivity");
            this.txt_SelectedActivitieOrSubActivitie.setText(bundle.getString("subactivity")+" " + this.subactivityDtoSelected.getSactName());
        }
        for (String userString : usersChb_AssignedUser) {
            if (userString.startsWith(this.managementDto.getUsrAssignedId().getUsrId() + " -")) {
                this.chb_AssignedUser.setValue(userString);
                break;
            }
        }
        LocalDateTime dateTime = this.managementDto.getMgtMaxdatetosolve();
        LocalDate date = dateTime.toLocalDate();
        LocalTime time = dateTime.toLocalTime();
        
        this.datePicker_ResolveDate.setValue(date);
        this.spin_Hour.getValueFactory().setValue(time.getHour());
        this.spin_Minutes.getValueFactory().setValue(time.getMinute());
        
        if(!this.managementDto.getFileCollection().isEmpty() && this.managementDto.getFileCollection()!=null){
            this.observableListFiles.addAll(this.managementDto.getFileCollection());
        }
        
        this.observableUsersDto.clear();
        this.aprobations = (List<ManagementaprobationDto>) managementaprobationService.getManagementaprobationsByManagement(this.managementDto.getMgtId()).getResult("Managementaprobations");
        List<UserDto>usersLoad = ((List<UserDto>) userService.getUsers().getResult("Users")).stream()
                .filter(isUserToAprove(aprobations)).toList();
        this.observableUsersDto.addAll(usersLoad);     
    }
    
    Predicate<UserDto>isUserToAprove(List<ManagementaprobationDto> mgta){
       return user->mgta.stream().anyMatch(mg->mg.getUsrToaproveId().getUsrId().equals(user.getUsrId()));
    }
    
    @FXML
    void onActionBtnClean(ActionEvent event) {
        if (new Message().showConfirmation(bundle.getString("cleanManagement"), getStage(), bundle.getString("sureToCleanSpaces"))) {
            clearSpaces();
            this.managementDto = new ManagementDto();
            this.activityDtoSelected = new ActivityDto();
            this.subactivityDtoSelected = new SubactivityDto();
            this.userDtoSelected = new UserDto();
        }
    }
    
    @FXML
    void onActionBtnDeleteManagement(ActionEvent event) {
        if(!this.managementLoad){
            new Message().showModal(Alert.AlertType.WARNING, bundle.getString("noManagementLoad"), getStage(), bundle.getString("loadAManagement"));
        }
        else{
            boolean userConfirmed = new Message().showConfirmation(bundle.getString("deleteManagement"), getStage(), bundle.getString("sureToDeleteManagement"));
            if(userConfirmed){
                try {
                        Response response = this.managementService.deleteManagement(this.managementDto.getMgtId());
                        if (!response.getState()) {
                            new Message().showModal(Alert.AlertType.ERROR, bundle.getString("deleteManagement"), getStage(), response.getMessage());
                        } else {
                            clearSpaces();
                            unbindManagement();
                            bindManagement(false);
                            new Message().showModal(Alert.AlertType.INFORMATION, bundle.getString("deleteManagement"), getStage(), bundle.getString("deleteSuccessManagement"));
                        }         
                } catch (Exception ex) {
                    Logger.getLogger(AreasController.class.getName()).log(Level.SEVERE, bundle.getString("deleteErrorManagement"), ex);
                    new Message().showModal(Alert.AlertType.ERROR, bundle.getString("deleteManagement"), getStage(), bundle.getString("deleteErrorManagement"));
                }
            }
        }
    }
}
