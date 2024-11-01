
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ManagementController extends Controller implements Initializable{
    @FXML
    private TableColumn<UserDto, String> tblC_ID;

    @FXML
    private TableColumn<UserDto, String> tblC_LastName;

    @FXML
    private TableColumn<UserDto, String> tblC_Name;

    @FXML
    private TableColumn<UserDto, String> tblC_SurName;

    @FXML
    private TableView<UserDto> tblV_UserAprovements;
    
    @FXML
    private TextField txt_ActivityorSubActivity;

    @FXML
    private TextArea txtA_Description;

    @FXML
    private TextField txt_AssignedUserName;

    @FXML
    private TextField txt_CreationDate;

    @FXML
    private TextField txt_ID;

    @FXML
    private TextField txt_MaxDateToSolve;

    @FXML
    private TextField txt_RequestedName;

    @FXML
    private TextField txt_State;

    @FXML
    private TextField txt_Subject;
    
    @FXML
    private Button btn_Resolved;
    
    @FXML
    private Label lbl_Resolved;
    
    @FXML
    private Label lbl_expired;
    
    @FXML
    private Button btn_OnHold;
    
    @FXML
    private TextField txt_SolvedDate;
    
    @FXML
    private Button btn_QuitOnHold;
    
    @FXML
    private ListView<FileDto> listV_Files;
    
    private ManagementDto managementSelected;
    private List<ManagementaprobationDto>managementaprovationsList;
    private List<UserDto>usersList;
    
    
    //services
    private ManagementService managementService=new ManagementService();
    private ActivityService activityService=new ActivityService();
    private SubactivityService subactivityService=new SubactivityService();
    private UserService userService=new UserService();
    private ManagementAprobationService managementAprobationService=new ManagementAprobationService();
    private FileService fileService = new FileService();
    
    //observableList for the table view
    private ObservableList<UserDto>observableUsertoAproveDto=FXCollections.observableArrayList();
    private ObservableList<FileDto> observableListFiles = FXCollections.observableArrayList();
    
    private UserDto loggedUser;
    private ResourceBundle bundle;
    @Override
    public void initialize() {
        this.bundle=FlowController.getIdioma();
        this.lbl_expired.setVisible(false);
        this.lbl_Resolved.setVisible(false);
        this.txt_SolvedDate.setVisible(false);
        this.btn_Resolved.setVisible(false);
        this.btn_OnHold.setVisible(false);
        this.btn_QuitOnHold.setVisible(false);
        this.loggedUser=(UserDto) AppContext.getInstance().get("User");
        this.observableUsertoAproveDto.clear();   
        this.observableListFiles.clear();
        listV_Files.setItems(observableListFiles);
        
        clearSpaces();
        this.usersList=new ArrayList<>();
        this.managementaprovationsList=new ArrayList<>();
        this.usersList = (List<UserDto>) this.userService.getUsers().getResult("Users");
        fillSpaces();
        this.tblV_UserAprovements.setItems(observableUsertoAproveDto);
        showResolvedButton();
        
    }

    Predicate<UserDto>toAproveTheManagement(List<ManagementaprobationDto> list){
        return x->list.stream().anyMatch(mgtA->x.getUsrId().equals(mgtA.getUsrToaproveId().getUsrId()));
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TableView Areas inicialization
        tblC_ID.setCellValueFactory(new PropertyValueFactory<>("usrId"));
        tblC_Name.setCellValueFactory(new PropertyValueFactory<>("usrName"));
        tblC_SurName.setCellValueFactory(new PropertyValueFactory<>("usrSurname"));
        tblC_LastName.setCellValueFactory(new PropertyValueFactory<>("usrLastname"));
        
        this.listV_Files.setCellFactory(param -> new ListCell<FileDto>() {          
            private final VBox vbox = new VBox();
            private final Label fileInfoLabel = new Label();
            private final Label downloadLabel = new Label(bundle.getString("clickToDownload"));

            {
                downloadLabel.setStyle("-fx-font-size: 10; -fx-text-fill: grey;");
                fileInfoLabel.setStyle("-fx-font-size: 15; -fx-text-fill: black;");
                vbox.getChildren().addAll(fileInfoLabel,downloadLabel);
            }

            @Override
            protected void updateItem(FileDto item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    fileInfoLabel.setText(item.getFleName() + "." + item.getFleType());
                    setGraphic(vbox);
                }
            }
        });
    }
    
    @FXML
    void handleFileClicked(MouseEvent event) {
        FileDto fileDto = this.listV_Files.getSelectionModel().getSelectedItem();
        if(fileDto != null){
            downloadFile(fileDto);
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
        Stage stage = (Stage) this.listV_Files.getScene().getWindow();
        
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
    
    @FXML
    void onActionBtnCreateManagement(ActionEvent event) {
        if(AppContext.getInstance().get("Role").equals("Admin") || AppContext.getInstance().get("Role").equals("Applicant")){
            FlowController.getInstance().goView("CreateManagementView");
        }
        else{
            new Message().showModal(Alert.AlertType.WARNING, bundle.getString("accessDenied"), getStage(),
            bundle.getString("onlyAdminOrApplicantAccess"));
        }
    }
    
    @FXML
    void onActionBtnTracings(ActionEvent event) {
        if(this.managementSelected!=null){
            if(AppContext.getInstance().get("Role").equals("Admin") || AppContext.getInstance().get("Role").equals("Applicant")){
                FlowController.getInstance().goView("TracingsView");
            }
            else{
                new Message().showModal(Alert.AlertType.WARNING, bundle.getString("accessDenied"), getStage(),
                bundle.getString("onlyAdminOrApplicantAccess"));
            }
        }
        else{
            new Message().showModal(Alert.AlertType.WARNING, bundle.getString("noManagementLoad"), getStage(),bundle.getString("loadAManagement"));
        }
    }
    
    private void checkIfManagementIsExpired() {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime maxDateToSolve = this.managementSelected.getMgtMaxdatetosolve();

        if (maxDateToSolve.isBefore(today)) {
            this.lbl_expired.setVisible(true);
        } else {
            this.lbl_expired.setVisible(false);
        }
    }
    
    private void fillSpaces(){
        this.managementaprovationsList=new ArrayList<>();
        this.observableUsertoAproveDto.clear();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        this.managementSelected=(ManagementDto)AppContext.getInstance().get("ManagementSelected");
        String state="";
        if(this.managementSelected!=null){
            if(AppContext.getInstance().get("Role").equals("Admin") && ((this.managementSelected.getMgtState().equals("In progress"))
                    || this.managementSelected.getMgtState().equals("In approval"))){
                this.btn_OnHold.setVisible(true);
            }
            if(AppContext.getInstance().get("Role").equals("Admin") && this.managementSelected.getMgtState().equals("On hold")){
                this.btn_QuitOnHold.setVisible(true);
            }
            if(this.managementSelected.getMgtState().equals("Resolved")){
                filltxt_SolvedDate();
            }
            checkIfManagementIsExpired();
            this.txt_Subject.setText(this.managementSelected.getMgtSubject());
            this.txtA_Description.setText(this.managementSelected.getMgtDescription());
            this.txt_ID.setText(this.managementSelected.getMgtId().toString());
            this.txt_AssignedUserName.setText(this.managementSelected.getUsrAssignedId().getUsrName()+" "+
                    this.managementSelected.getUsrAssignedId().getUsrSurname()+" "+
                    this.managementSelected.getUsrAssignedId().getUsrLastname());
            this.txt_RequestedName.setText(this.managementSelected.getUsrRequestingId().getUsrName()+" "+
                    this.managementSelected.getUsrRequestingId().getUsrSurname()+" "+
                    this.managementSelected.getUsrRequestingId().getUsrLastname());          
            this.txt_MaxDateToSolve.setText(this.managementSelected.getMgtMaxdatetosolve().format(formatter));
            this.txt_CreationDate.setText(this.managementSelected.getMgtCreationdate().format(formatter));           
            this.txt_State.setText(defineState());
            if(this.managementSelected.getActId()!=null){
                ActivityDto activity=(ActivityDto)this.activityService.getActivity(this.managementSelected.getActId()).getResult("Activity");
                this.txt_ActivityorSubActivity.setText(activity.getActName());
            }
            else{SubactivityDto subactivity=(SubactivityDto)this.subactivityService.getSubactivity(this.managementSelected.getSactId()).getResult("Subactivity");
                this.txt_ActivityorSubActivity.setText(subactivity.getSactName());
            }
            this.managementaprovationsList = (List<ManagementaprobationDto>) this.managementAprobationService.getManagementaprobationsByManagement(this.managementSelected.getMgtId()).getResult("Managementaprobations"); 
            if(this.managementaprovationsList!=null && !this.managementaprovationsList.isEmpty()){
            this.observableUsertoAproveDto.addAll(usersList.stream().filter(toAproveTheManagement(managementaprovationsList)).toList());
            }
            if(!this.managementSelected.getFileCollection().isEmpty() && this.managementSelected.getFileCollection()!=null){
                this.observableListFiles.addAll(this.managementSelected.getFileCollection());
            }
        }
        
    }
    
    private String defineState(){
        String state;
        switch (this.managementSelected.getMgtState()) {
            case "In progress":
                state = bundle.getString("inProgress");
                break;
            case "In approval":
                state = bundle.getString("inApproval");
                break;
            case "Rejected":
                state = bundle.getString("rejected");
                break;
            case "On hold":
                state = bundle.getString("onHold");
                break;
            case "Resolved":
                state = bundle.getString("resolved");
                break;
            default:
                state = bundle.getString("unknown");
                break;
        }
        return state;
    }
    
    private void clearSpaces(){
        this.txt_Subject.clear();
        this.txtA_Description.clear();
        this.txt_ID.clear();
        this.txt_AssignedUserName.clear();
        this.txt_RequestedName.clear();
        this.txt_MaxDateToSolve.clear();
        this.txt_CreationDate.clear();
        this.txt_State.clear();
        this.txt_ActivityorSubActivity.clear();
    }
    
    @FXML
    void onActionBtnResolved(ActionEvent event) {
            try {    
                this.managementSelected.setMgtState("Resolved");
                this.managementSelected.setMgtSolvedate(LocalDateTime.now());
                Response response = this.managementService.saveManagement(managementSelected);
                if (!response.getState()) {
                    new Message().showModal(Alert.AlertType.ERROR, bundle.getString("actualizeManagement"), getStage(), response.getMessage());
                } else {
                    this.managementSelected=(ManagementDto) response.getResult("Management");
                    this.txt_State.clear();
                    this.txt_State.setText(defineState());                  
                    this.btn_Resolved.setVisible(false);
                    
                    filltxt_SolvedDate();
                }
                } catch (Exception ex) {
                    Logger.getLogger(ManagementController.class.getName()).log(Level.SEVERE, bundle.getString("actualizeErrorManagement"), ex);
                    new Message().showModal(Alert.AlertType.ERROR, bundle.getString("actualizeManagement"), getStage(), bundle.getString("actualizeErrorManagement"));
                }       
    }
    
    private void showResolvedButton(){
        if(this.managementSelected!=null){
            if(this.managementSelected.getMgtState().equals("In progress") && 
                this.managementSelected.getUsrAssignedId().getUsrId().equals(this.loggedUser.getUsrId())){
                if(this.managementSelected.getMgtSolvedate()==null && !this.managementSelected.getMgtMaxdatetosolve().isBefore(LocalDateTime.now())){
                        this.btn_Resolved.setVisible(true);
                        this.lbl_expired.setVisible(false);
                }
            }
        }
        else{this.btn_Resolved.setVisible(false);}
    }
    
    private void filltxt_SolvedDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        this.lbl_Resolved.setVisible(true);
        this.txt_SolvedDate.setVisible(true);
        this.txt_SolvedDate.setText(managementSelected.getMgtSolvedate().format(formatter));
    }
    
    @FXML
    void onActionBtnOnHold(ActionEvent event) {
        this.managementSelected.setMgtState("On hold");
        Response response=this.managementService.saveManagement(managementSelected);       
        this.managementSelected=(ManagementDto) response.getResult("Management");
        this.txt_State.setText(defineState());
        this.btn_OnHold.setVisible(false);
        this.btn_Resolved.setVisible(false);
        this.btn_QuitOnHold.setVisible(true);
    }
    
    @FXML
    void onActionBtnQuitOnHold(ActionEvent event) {
        List<ManagementaprobationDto>aprobationsbyManagement=(List<ManagementaprobationDto>)this.managementAprobationService.getManagementaprobationsByManagement(this.managementSelected.getMgtId())
                .getResult("Managementaprobations");
        boolean allApprove=aprobationsbyManagement.stream().allMatch(mgta->mgta.getMgtaState().equals("Approved"));
        if(allApprove){
            this.managementSelected.setMgtState("In progress");
        }
        else{
            this.managementSelected.setMgtState("In approval");
        }
        Response response=this.managementService.saveManagement(managementSelected);
        this.managementSelected=(ManagementDto) response.getResult("Management");
        this.txt_State.setText(defineState());
        this.btn_OnHold.setVisible(true);
        this.btn_QuitOnHold.setVisible(false);
        showResolvedButton();
        
    }
}