
package cr.ac.una.sigeceuna.controller;

import cr.ac.una.sigeceuna.model.ManagementDto;
import cr.ac.una.sigeceuna.model.ManagementaprobationDto;
import cr.ac.una.sigeceuna.model.UserDto;
import cr.ac.una.sigeceuna.service.ManagementAprobationService;
import cr.ac.una.sigeceuna.service.ManagementService;
import cr.ac.una.sigeceuna.service.UserService;
import cr.ac.una.sigeceuna.util.FlowController;
import cr.ac.una.sigeceuna.util.Message;
import cr.ac.una.sigeceuna.util.Response;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


public class CreateAprovementsController extends Controller implements Initializable{
     @FXML
    private TableColumn<ManagementDto, String> tblC_IDManagement;

    @FXML
    private TableColumn<UserDto, String> tblC_IDUser;

    @FXML
    private TableColumn<ManagementDto, String> tblC_StateManagement;

    @FXML
    private TableColumn<ManagementDto, String> tblC_SubjectManagement;

    @FXML
    private TableColumn<UserDto, String> tblC_UserIdentification;

    @FXML
    private TableColumn<UserDto, String> tblC_UserLastName;

    @FXML
    private TableColumn<UserDto, String> tblC_UserName;

    @FXML
    private TableColumn<UserDto, String> tblC_UserSurName;

    @FXML
    private TableView<ManagementDto> tblV_Managements;

    @FXML
    private TableView<UserDto> tblV_Users;
    
    @FXML
    private TableColumn<ManagementaprobationDto, String> tblC_DescriptionAprobation;
    
    @FXML
    private TableColumn<ManagementaprobationDto, String> tblC_idAprobation;

    @FXML
    private TableColumn<ManagementaprobationDto, String> tblC_idManagementAprobation;

    @FXML
    private TableColumn<ManagementaprobationDto, String> tblC_stateAprobation;

    @FXML
    private TableView<ManagementaprobationDto> tblV_Aprobations;
    
    @FXML
    private TextField txt_FilterAprobations;
    
    @FXML
    private TextField txt_filterManagements;

    @FXML
    private TextField txt_filterUsers;
    
    //Services
    private ManagementService managementService=new ManagementService();
    private UserService userService=new UserService();
    private ManagementAprobationService managementaprobationService=new ManagementAprobationService();
    
    //Lists from the dataBase
    private List<UserDto>usersList=new ArrayList<>();
    private List<ManagementDto>managementsList=new ArrayList<>();
    private List<ManagementaprobationDto>mgtmanagementaprobationsList=new ArrayList<>();
    
    //Observable lists
    private ObservableList<UserDto>observableUsersDto=FXCollections.observableArrayList();
    private ObservableList<ManagementDto>observableManagementDto=FXCollections.observableArrayList();
    private ObservableList<ManagementaprobationDto>observableManagementaprobationDto=FXCollections.observableArrayList();
    
    //Dtos selected
    ManagementDto managementDtoSelected;
    ManagementaprobationDto managementaprobationDtoSelected;
    UserDto userDtoSelected;
    
    ManagementaprobationDto managementaprobationDto;
    private ResourceBundle bundle;
    @FXML
    private Button btn_filterManagements;
    @FXML
    private Button btn_filterUsers;
    @FXML
    private Button btn_FilterAprobations;
    @FXML
    private Button btn_DeleteAprobation;

    @Override
    public void initialize() {
        this.bundle=FlowController.getIdioma();
        this.managementaprobationDto=new ManagementaprobationDto();
        this.managementDtoSelected=new ManagementDto();
        this.managementaprobationDtoSelected=new ManagementaprobationDto();
        this.userDtoSelected=new UserDto();
        
        this.tblV_Managements.getSelectionModel().clearSelection();
        this.tblV_Users.getSelectionModel().clearSelection();
        
        this.observableManagementDto.clear();
        this.observableUsersDto.clear();
        this.observableManagementaprobationDto.clear();
        
        updateManagementsList();
        FXCollections.sort(observableManagementDto,Comparator.comparing(ManagementDto::getMgtId));
        this.tblV_Managements.setItems(observableManagementDto);
        
        updateAprobationsList();        
        FXCollections.sort(observableManagementaprobationDto,Comparator.comparing(ManagementaprobationDto::getMgtaId));
        this.tblV_Aprobations.setItems(observableManagementaprobationDto);
        
        //Table selections
         this.tblV_Managements.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue != null) {
                    this.managementDtoSelected = newValue;
                    this.userDtoSelected = null;
                    updateUsersList();
                }
            }
        );
         
        //Table selections
         this.tblV_Users.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue != null) {
                    this.userDtoSelected = newValue;
                }
            }
        );
         
        //Table selections
         this.tblV_Aprobations.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue != null) {
                    this.managementaprobationDtoSelected= newValue;
                }
            }
        );
        
        tblC_stateAprobation.setCellValueFactory(new PropertyValueFactory<>("mgtaState"));
        tblC_stateAprobation.setCellFactory(column -> new TableCell<ManagementaprobationDto, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    switch (item.toLowerCase()) {
                        case "pending":
                            setText(bundle.getString("pending"));
                            break;
                        case "approved":
                            setText(bundle.getString("approved"));
                            break;
                        case "rejected":
                            setText(bundle.getString("rejected"));
                            break;
                        default:
                            setText(bundle.getString("unknown"));
                            break;
                    }
                }
            }
        });
        
        tblC_StateManagement.setCellValueFactory(new PropertyValueFactory<>("mgtState"));
        tblC_StateManagement.setCellFactory(column -> new TableCell<ManagementDto, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    switch (item.toLowerCase()) {
                        case "in approval":
                            setText(bundle.getString("inApproval"));
                            break;
                        case "in progress":
                            setText(bundle.getString("inProgress"));
                            break;
                        case "on hold":
                            setText(bundle.getString("onHold"));
                            break;
                        case "rejected":
                            setText(bundle.getString("rejected"));
                            break;
                        case "resolved":
                            setText(bundle.getString("solved"));
                            break;
                        default:
                            setText(item);
                            break;
                    }
                }
            }
        });

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TableView Users inicialization
        tblC_IDUser.setCellValueFactory(new PropertyValueFactory<>("usrId"));
        tblC_UserIdentification.setCellValueFactory(new PropertyValueFactory<>("usrIdentification"));
        tblC_UserLastName.setCellValueFactory(new PropertyValueFactory<>("usrLastname"));
        tblC_UserSurName.setCellValueFactory(new PropertyValueFactory<>("usrSurname"));
        tblC_UserName.setCellValueFactory(new PropertyValueFactory<>("usrName"));
        
        //TableView Management inicialization
        tblC_IDManagement.setCellValueFactory(new PropertyValueFactory<>("mgtId"));
        tblC_SubjectManagement.setCellValueFactory(new PropertyValueFactory<>("mgtSubject"));
        
        //TableView Managementaprobations inicialization
        tblC_idAprobation.setCellValueFactory(new PropertyValueFactory<>("mgtaId"));
        tblC_idManagementAprobation.setCellValueFactory(new PropertyValueFactory<>("mgtId"));
        tblC_DescriptionAprobation.setCellValueFactory(new PropertyValueFactory<>("mgtaComment"));
    }
    
    @FXML
    void onActiontnReturn(ActionEvent event) {
        FlowController.getInstance().goView("AprovementsView");
    }
    
    @FXML
    void onActionBtnCreate(ActionEvent event) {
        if(this.userDtoSelected.getUsrId()==null){
            new Message().showModal(Alert.AlertType.ERROR, bundle.getString("noUserSelected"), getStage(),bundle.getString("selectAnUser"));
        }
        else{
            try {
                this.managementaprobationDto.setMgtId(this.managementDtoSelected.getMgtId());
                this.managementaprobationDto.setUsrToaproveId(this.userDtoSelected);
                Response response = this.managementaprobationService.saveManagementaprobations(this.managementaprobationDto);
                if (!response.getState()) {
                    new Message().showModal(Alert.AlertType.ERROR, bundle.getString("saveAprobation"), getStage(), response.getMessage());
                } else {
                    if(this.managementDtoSelected.getMgtState().equals("In progress")){
                        this.managementDtoSelected.setMgtState("In approval");
                        this.managementService.saveManagement(managementDtoSelected);
                    }
                    this.tblV_Managements.getSelectionModel().clearSelection();
                    this.tblV_Users.getSelectionModel().clearSelection();
                    this.observableUsersDto.clear();
                    this.managementDtoSelected=new ManagementDto();
                    this.userDtoSelected=new UserDto();
                    updateManagementsList();
                    updateAprobationsList();
                }
                } catch (Exception ex) {
                    Logger.getLogger(CreateAprovementsController.class.getName()).log(Level.SEVERE, bundle.getString("saveErrorAprobation"), ex);
                    new Message().showModal(Alert.AlertType.ERROR, bundle.getString("saveAprobation"), getStage(), bundle.getString("saveErrorAprobation"));
                }
        }

    }

    private void updateUsersList() {
        if(this.managementDtoSelected.getMgtId()!=null){
            this.usersList=new ArrayList<>();
        this.observableUsersDto.clear();
        this.mgtmanagementaprobationsList=new ArrayList<>();
        this.mgtmanagementaprobationsList=((List<ManagementaprobationDto>) this.managementaprobationService.getManagementaprobationsByManagement(this.managementDtoSelected.getMgtId())
                .getResult("Managementaprobations"));
        this.usersList=((List<UserDto>) this.userService.getUsers().getResult("Users")).stream()
                .filter(user->!(this.mgtmanagementaprobationsList.stream()
                        .anyMatch(mgta->user.getUsrId().equals(mgta.getUsrToaproveId().getUsrId())))&& user.getUsrState()
                .equals("A")).toList();
        
        this.observableUsersDto.addAll(usersList);
        FXCollections.sort(observableUsersDto,Comparator.comparing(UserDto::getUsrId));
        this.tblV_Users.setItems(observableUsersDto);
        }
    }
    
    private void updateAprobationsList(){
        this.observableManagementaprobationDto.clear();
        this.mgtmanagementaprobationsList=new ArrayList<>();
        this.mgtmanagementaprobationsList=((List<ManagementaprobationDto>) this.managementaprobationService.getManagementaprobations()
                .getResult("Managementaprobations"));
        this.observableManagementaprobationDto.addAll(mgtmanagementaprobationsList);
    }
    
    @FXML
    void onActionBtnDeleteAprobation(ActionEvent event) {
        if(this.managementaprobationDtoSelected.getMgtaId()==null){
           new Message().showModal(Alert.AlertType.ERROR, bundle.getString("noAprobationSelected"), getStage(),bundle.getString("selectAnAprobation"));
           return;
        }
        boolean userConfirmed = new Message().showConfirmation(bundle.getString("deleteAprobation"), getStage(), bundle.getString("sureToDeleteAprobation"));
        if(userConfirmed){
            try {
                Response response = this.managementaprobationService.deleteManagementaprobation(this.managementaprobationDtoSelected.getMgtaId());
                if (!response.getState()) {
                    new Message().showModal(Alert.AlertType.ERROR, bundle.getString("deleteAprobation"), getStage(), response.getMessage());
                } else {
                    changeMgtState(this.managementaprobationDtoSelected.getMgtId());
                    this.tblV_Aprobations.getSelectionModel().clearSelection();
                    this.managementaprobationDtoSelected=new ManagementaprobationDto();
                    updateManagementsList();
                    updateAprobationsList();
                    updateUsersList();
                    new Message().showModal(Alert.AlertType.INFORMATION, bundle.getString("deleteAprobation"), getStage(), bundle.getString("deleteSuccessAprobation"));
                }         
            } catch (Exception ex) {
                Logger.getLogger(TracingsController.class.getName()).log(Level.SEVERE, bundle.getString("deleteErrorAprobation"), ex);
                new Message().showModal(Alert.AlertType.ERROR, bundle.getString("deleteAprobation"), getStage(), bundle.getString("deleteErrorAprobation"));
            }
        }
    }

    @FXML
    void onActionBtnFilterAprobations(ActionEvent event) {
        String textSearchString = this.txt_FilterAprobations.getText().toLowerCase();    
    // Si el campo de texto está vacío, muestra la lista completa
        if (textSearchString.isEmpty()) {
            this.observableManagementaprobationDto = FXCollections.observableArrayList(this.mgtmanagementaprobationsList);
            this.tblV_Aprobations.setItems(observableManagementaprobationDto);
            return;
        }
        else {
            try {
                Long searchId = Long.valueOf(textSearchString);
                // Filter the orignal List
                List<ManagementaprobationDto> filteredList = this.mgtmanagementaprobationsList.stream()
                    .filter(a -> a.getMgtId().equals(searchId))
                    .collect(Collectors.toList());

                // Actualice the observable list with the filtered list
                this.observableManagementaprobationDto = FXCollections.observableArrayList(filteredList);
                this.tblV_Aprobations.setItems(observableManagementaprobationDto);
            } catch (NumberFormatException e) {
               new Message().showModal(Alert.AlertType.ERROR, bundle.getString("noNumericFormat"), getStage(),bundle.getString("noNumericFormat"));
            }
        }
    }

    @FXML
    void onActionBtnFilterManagements(ActionEvent event) {
        String textSearchString = this.txt_filterManagements.getText().toLowerCase();
        if(textSearchString!=""){
            // Filter the orignal List
            List<ManagementDto> filteredList = this.managementsList.stream()
                .filter(a -> a.getMgtSubject().toLowerCase().contains(textSearchString))
                .collect(Collectors.toList());

            // Actualice the observable list with the filtered list
            this.observableManagementDto = FXCollections.observableArrayList(filteredList);
            FXCollections.sort(observableManagementDto,Comparator.comparing(ManagementDto::getMgtId));
            this.tblV_Managements.setItems(observableManagementDto);
        }
    }

    @FXML
    void onActionBtnFilterUsers(ActionEvent event) {
        if(this.managementDtoSelected==null){
            new Message().showModal(Alert.AlertType.ERROR, bundle.getString("noUsers"), getStage(),bundle.getString("loadAManagement"));
        }
        else{
            String textSearchString = this.txt_filterUsers.getText().toLowerCase();
            if(textSearchString!=""){
                // Filter the orignal List
                List<UserDto> filteredList = this.usersList.stream()
                    .filter(a -> (a.getUsrName() +" " + a.getUsrSurname()+ " " +a.getUsrLastname()).toLowerCase()
                            .contains(textSearchString))
                    .collect(Collectors.toList());

                // Actualice the observable list with the filtered list
                this.observableUsersDto = FXCollections.observableArrayList(filteredList);
                FXCollections.sort(observableUsersDto,Comparator.comparing(UserDto::getUsrId));
                this.tblV_Users.setItems(observableUsersDto);
            }
        }
    }

    private void updateManagementsList() {
        this.observableManagementDto.clear();
        //managements that are not Rejected o Resolved
        this.managementsList=((List<ManagementDto>) this.managementService.getManagements().getResult("Managements"))
                .stream().filter(mgt->mgt.getMgtState().equals("In progress") || mgt.getMgtState().equals("In approval"))
                .toList();      
        this.observableManagementDto.addAll(managementsList);
    }
    
    public void changeMgtState(Long managementID){       
        ManagementDto managementDto=(ManagementDto) this.managementService.getManagement(managementID).getResult("Management");
        List<ManagementaprobationDto>aprobationsbyManagement=(List<ManagementaprobationDto>)this.managementaprobationService.getManagementaprobationsByManagement(managementID)
                .getResult("Managementaprobations");
        boolean allApprove=aprobationsbyManagement.stream().allMatch(mgta->mgta.getMgtaState().equals("Approved"));
        boolean someRejected=aprobationsbyManagement.stream().anyMatch(mgta->mgta.getMgtaState().equals("Rejected"));
        if(managementDto.getMgtState().equals("In approval") && allApprove){
            managementDto.setMgtState("In progress");
            this.managementService.saveManagement(managementDto);
        }
        if(managementDto.getMgtState().equals("In approval") && someRejected){
            managementDto.setMgtState("Rejected");
            this.managementService.saveManagement(managementDto);
        }
    }
}
