package cr.ac.una.sigeceunaemail.controller;

import cr.ac.una.sigeceunaemail.model.ConstrainDto;
import cr.ac.una.sigeceunaemail.model.NotificationProcessDto;
import cr.ac.una.sigeceunaemail.model.VariableDto;
import cr.ac.una.sigeceunaemail.service.ConstrainService;
import cr.ac.una.sigeceunaemail.service.NotificationProcessService;
import cr.ac.una.sigeceunaemail.service.VariableService;
import cr.ac.una.sigeceunaemail.util.Message;
import cr.ac.una.sigeceunaemail.util.Response;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author altam
 */
public class ManagementController extends Controller implements Initializable {

    @FXML
    private TableColumn<ConstrainDto, String> Cons_idColumn;

    @FXML
    private TableColumn<ConstrainDto, String> Cons_idVarColumn;

    @FXML
    private TableColumn<ConstrainDto, String> Cons_resultColumn;

    @FXML
    private TableColumn<ConstrainDto, String> Cons_symbolColumn;

    @FXML
    private TableColumn<NotificationProcessDto, String> Ntp_descriptionColumn;

    @FXML
    private TableColumn<NotificationProcessDto, String> Ntp_idColumn;

    @FXML
    private TableColumn<VariableDto, String> Var_idColumn;

    @FXML
    private TableColumn<VariableDto, String> Var_idNTPColumn;

    @FXML
    private TableColumn<VariableDto, String> Var_typeColumn;

    @FXML
    private TableColumn<VariableDto, String> Var_varColumn;

    @FXML
    private Button btn_saveCons;

    @FXML
    private Button btn_saveNTP;

    @FXML
    private Button btn_saveVar;
    
    @FXML
    private Button btnDeleteCons;

    @FXML
    private Button btnDeleteNTP;

    @FXML
    private Button btnDeleteVar;

    @FXML
    private ChoiceBox<String> chk_type;

    @FXML
    private TableView<ConstrainDto> tableView_Cons;

    @FXML
    private TableView<NotificationProcessDto> tableView_NTP;

    @FXML
    private TableView<VariableDto> tableView_Var;

    @FXML
    private TextArea txtArea_description;

    @FXML
    private TextArea txtArea_html;

    @FXML
    private TextField txt_Var;

    @FXML
    private TextField txt_default;

    @FXML
    private TextField txt_result;

    @FXML
    private TextField txt_symbol;
    
    private NotificationProcessDto notificationProcessDto=new NotificationProcessDto();
    private VariableDto variableDto=new VariableDto();
    private ConstrainDto constrainDto=new ConstrainDto();
    
    //Service
    private NotificationProcessService notificationProcessService=new NotificationProcessService();
    private VariableService variableService=new VariableService();
    private ConstrainService constrainService=new ConstrainService();
    
    
    //Dtos Selected
    private NotificationProcessDto notificationProcessDtoSelected;
    private VariableDto variableDtoSelected;
    private ConstrainDto constrainDtoSelected;
    
    //Lists to take information from the database
    private List<NotificationProcessDto>notificationProcessesList;
    private List<VariableDto>variablesList;
    private List<ConstrainDto>constrainsList;
    
    //ObservableLists for the tables
    private ObservableList<NotificationProcessDto>observableNotificationProcessesDto=FXCollections.observableArrayList();
    private ObservableList<VariableDto>observableVariablesDto=FXCollections.observableArrayList();
    private ObservableList<ConstrainDto>observableConstrainsDto=FXCollections.observableArrayList();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.chk_type.getItems().addAll("S","C","M");
        this.chk_type.setValue("S");
        this.txtArea_description.clear();
        this.txtArea_html.clear();
        this.txt_Var.clear();
        this.txt_default.clear();
        this.txt_result.clear();
        this.txt_symbol.clear();
        
        //NotificationProcess Table
        this.Ntp_idColumn.setCellValueFactory(new PropertyValueFactory<>("ntpId"));
        this.Ntp_descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("ntpDescription"));
        
        //Variable Table
        this.Var_idColumn.setCellValueFactory(new PropertyValueFactory<>("varId"));
        this.Var_idNTPColumn.setCellValueFactory(new PropertyValueFactory<>("ntpId"));
        this.Var_typeColumn.setCellValueFactory(new PropertyValueFactory<>("varType"));
        this.Var_varColumn.setCellValueFactory(new PropertyValueFactory<>("varVariable"));
        
        //Constrain Table
        this.Cons_idColumn.setCellValueFactory(new PropertyValueFactory<>("cnstId"));
        this.Cons_idVarColumn.setCellValueFactory(new PropertyValueFactory<>("varId"));
        this.Cons_resultColumn.setCellValueFactory(new PropertyValueFactory<>("cnstResult"));
        this.Cons_symbolColumn.setCellValueFactory(new PropertyValueFactory<>("cnstSymbol"));
        
        this.notificationProcessDto=new NotificationProcessDto();
        this.variableDto=new VariableDto();
        this.constrainDto=new ConstrainDto();
        this.notificationProcessDtoSelected=new NotificationProcessDto();
        this.variableDtoSelected=new VariableDto();
        this.constrainDtoSelected=new ConstrainDto();
        newNotificationProcess();
        newVariable();
        newConstrain();
    }    

    @Override
    public void initialize() {       
        this.chk_type.setValue("S");
        this.tableView_NTP.getSelectionModel().clearSelection();
        this.tableView_Var.getSelectionModel().clearSelection();
        this.tableView_Cons.getSelectionModel().clearSelection();
        
        this.notificationProcessDtoSelected=new NotificationProcessDto();
        this.variableDtoSelected=new VariableDto();
        this.constrainDtoSelected=new ConstrainDto();
        
        this.observableNotificationProcessesDto.clear();
        this.observableVariablesDto.clear();
        this.observableConstrainsDto.clear();
        
        this.notificationProcessesList=(List<NotificationProcessDto>)this.notificationProcessService.getNotificationProcesses().getResult("NotificationProcesses");
        for(NotificationProcessDto ntp:notificationProcessesList){
            this.observableNotificationProcessesDto.add(ntp);
        }
        this.tableView_NTP.setItems(observableNotificationProcessesDto);
        
        //Table selections
         this.tableView_NTP.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue != null) {
                    this.notificationProcessDtoSelected = newValue;
                    this.variableDtoSelected = null;
                    this.constrainDtoSelected=null;
                    updateVariableLists();
                    this.observableConstrainsDto.clear();
                    this.tableView_Cons.setItems(observableConstrainsDto);
                }
            }
        );
        this.tableView_Var.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue != null) {
                    this.variableDtoSelected = newValue;
                    this.constrainDtoSelected=null;
                    updateConstrainsList();
                }
            }
        );
        this.tableView_Cons.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue != null) {                 
                    this.constrainDtoSelected=newValue;
                }
            }
        );
        this.txt_symbol.textProperty().addListener((observable, oldValue, newValue) -> {
    if (newValue.length() > 5) {
        new Message().showModal(Alert.AlertType.ERROR, "number of characters", getStage(),"Only 5 characteres allow");
        this.txt_symbol.setText(oldValue);
    }
});
        
}

    @FXML
    void onActionBtnSaveCons(ActionEvent event) {
        if(this.variableDtoSelected.getVarId()==null){
            new Message().showModal(Alert.AlertType.ERROR, "No variable selected", getStage(),"You have to select a variable");
        }
        else if(!"C".equals(this.variableDtoSelected.getVarType())){
            new Message().showModal(Alert.AlertType.ERROR, "No conditional variable selected", getStage(),"You have to select a conditional variable");
        }
        else{
            try {    
                this.constrainDto.setVarId(this.variableDtoSelected.getVarId());
                Response response = this.constrainService.saveConstrain(constrainDto);
                if (!response.getState()) {
                    new Message().showModal(Alert.AlertType.ERROR, "Save constrain", getStage(), response.getMessage());
                } else {
                    this.txt_result.clear();
                    this.txt_symbol.clear();
                    updateConstrainsList();
                    unbindConstrain();
                    bindConstrain(false);
                }
                } catch (Exception ex) {
                    Logger.getLogger(ManagementController.class.getName()).log(Level.SEVERE, "An error ocurred while saving the constrain.", ex);
                    new Message().showModal(Alert.AlertType.ERROR, "Save constrain", getStage(), "An error ocurred while saving the constrain.");
                }
               newConstrain();
        }

    }

    @FXML
    void onActionBtnSaveNTP(ActionEvent event) {
         try {             
            Response response = this.notificationProcessService.saveNotificationProcess(notificationProcessDto);
            if (!response.getState()) {
                new Message().showModal(Alert.AlertType.ERROR, "Save notificationProcess", getStage(), response.getMessage());
            } else {
                this.txtArea_description.clear();
                this.txtArea_html.clear();
                updateNotificationProcessList();
                unbindNotificationProcess();
                bindNotificationProcess(false);
            }
            } catch (Exception ex) {
                Logger.getLogger(ManagementController.class.getName()).log(Level.SEVERE, "An error ocurred while saving the notificationProcess.", ex);
                new Message().showModal(Alert.AlertType.ERROR, "Save notificationProcess", getStage(), "An error ocurred while saving the NotificationProcess.");
            }
           newNotificationProcess();
    }

    @FXML
    void onActionBtnSaveVar(ActionEvent event) {
        if(this.notificationProcessDtoSelected.getNtpId()==null){
            new Message().showModal(Alert.AlertType.ERROR, "No notificationProcess selected", getStage(),"You have to select a notification Process");
        }
        else{
            try {    
                this.variableDto.setNtpId(this.notificationProcessDtoSelected.getNtpId());
                Response response = this.variableService.saveVariable(variableDto);
                if (!response.getState()) {
                    new Message().showModal(Alert.AlertType.ERROR, "Save variable", getStage(), response.getMessage());
                } else {
                    this.txt_default.clear();
                    this.txt_Var.clear();
                    this.chk_type.setValue("S");
                    updateVariableLists();
                    unbindVariable();
                    bindVariable(false);
                }
                } catch (Exception ex) {
                    Logger.getLogger(ManagementController.class.getName()).log(Level.SEVERE, "An error ocurred while saving the variable.", ex);
                    new Message().showModal(Alert.AlertType.ERROR, "Save variable", getStage(), "An error ocurred while saving the variable.");
                }
               newVariable();
        }
    }
    
    @FXML
    void onActionBtnDeleteCons(ActionEvent event) {
        if(this.constrainDtoSelected.getCnstId()==null){
           new Message().showModal(Alert.AlertType.ERROR, "No constrain selected", getStage(),"You have to select a constrain to delete");
           return;
        }
        boolean userConfirmed = new Message().showConfirmation("Delete constrain", getStage(), "Are you sure you want to delete this constrain?.");
        if(userConfirmed){
            try {
                    Response response = this.constrainService.deleteConstrain(this.constrainDtoSelected.getCnstId());
                    if (!response.getState()) {
                        new Message().showModal(Alert.AlertType.ERROR, "Delete constrain", getStage(), response.getMessage());
                    } else {
                        this.tableView_Cons.getSelectionModel().clearSelection();
                        this.constrainDtoSelected=new ConstrainDto();
                        updateConstrainsList();
                        new Message().showModal(Alert.AlertType.INFORMATION, "Delete constrain", getStage(), "Constrain deleted successfully.");
                    }         
            } catch (Exception ex) {
                Logger.getLogger(ManagementController.class.getName()).log(Level.SEVERE, "An error ocurred while deleting the constrain.", ex);
                new Message().showModal(Alert.AlertType.ERROR, "Delete constrain", getStage(), "An error ocurred while deleting the constrain.");
            }
        } 
    }

    @FXML
    void onActionBtnDeleteNTP(ActionEvent event) {
        if(this.notificationProcessDtoSelected.getNtpId()==null){
           new Message().showModal(Alert.AlertType.ERROR, "No notification Process selected", getStage(),"You have to select a notification Process to delete");
           return;
        }
        boolean userConfirmed = new Message().showConfirmation("Delete notification Process", getStage(), "Are you sure you want to delete this notification Process?.");
        if(userConfirmed){
            try {
                    Response response = this.notificationProcessService.deleteNotificationProcess(this.notificationProcessDtoSelected.getNtpId());
                    if (!response.getState()) {
                        new Message().showModal(Alert.AlertType.ERROR, "Delete notification Process", getStage(), response.getMessage());
                    } else {
                        this.tableView_NTP.getSelectionModel().clearSelection();
                        this.variableDtoSelected=new VariableDto();
                        this.notificationProcessDtoSelected=new NotificationProcessDto();
                        this.constrainDtoSelected=new ConstrainDto();
                        this.observableVariablesDto=FXCollections.observableArrayList();
                        this.observableConstrainsDto=FXCollections.observableArrayList();
                        this.tableView_Cons.setItems(observableConstrainsDto);
                        this.tableView_Var.setItems(observableVariablesDto);
                        updateNotificationProcessList();
                        new Message().showModal(Alert.AlertType.INFORMATION, "Delete notification Process", getStage(), "Notification Process deleted successfully.");
                    }         
            } catch (Exception ex) {
                Logger.getLogger(ManagementController.class.getName()).log(Level.SEVERE, "An error ocurred while deleting the notification Process.", ex);
                new Message().showModal(Alert.AlertType.ERROR, "Delete notification Process", getStage(), "An error ocurred while deleting the notification Process.");
            }
        } 
    }

    @FXML
    void onActionBtnDeleteVar(ActionEvent event) {
        if(this.variableDtoSelected.getVarId()==null){
           new Message().showModal(Alert.AlertType.ERROR, "No variable selected", getStage(),"You have to select a variable to delete");
           return;
        }
        boolean userConfirmed = new Message().showConfirmation("Delete variable", getStage(), "Are you sure you want to delete this variable?.");
        if(userConfirmed){
            try {
                    Response response = this.variableService.deleteVariable(this.variableDtoSelected.getVarId());
                    if (!response.getState()) {
                        new Message().showModal(Alert.AlertType.ERROR, "Delete variable", getStage(), response.getMessage());
                    } else {
                        this.tableView_Var.getSelectionModel().clearSelection();
                        this.variableDtoSelected=new VariableDto();
                        this.constrainDtoSelected=new ConstrainDto();                      
                        this.observableConstrainsDto=FXCollections.observableArrayList();
                        this.tableView_Cons.setItems(observableConstrainsDto);
                        updateVariableLists();
                        new Message().showModal(Alert.AlertType.INFORMATION, "Delete variable", getStage(), "Variable deleted successfully.");
                    }         
            } catch (Exception ex) {
                Logger.getLogger(ManagementController.class.getName()).log(Level.SEVERE, "An error ocurred while deleting the variable.", ex);
                new Message().showModal(Alert.AlertType.ERROR, "Delete variable", getStage(), "An error ocurred while deleting the variable.");
            }
        } 
    }
    
    private void updateNotificationProcessList(){
        this.notificationProcessesList=new ArrayList<>();
        this.notificationProcessesList=(List<NotificationProcessDto>)this.notificationProcessService.getNotificationProcesses().getResult("NotificationProcesses");
        this.observableNotificationProcessesDto.clear();
        for(NotificationProcessDto ntp:notificationProcessesList){
            this.observableNotificationProcessesDto.add(ntp);
        }
        this.tableView_NTP.setItems(observableNotificationProcessesDto);
    }

    private void updateVariableLists() {
        this.variablesList=new ArrayList<>();
        this.variablesList=(List<VariableDto>)this.variableService.getVariablesByNTP(this.notificationProcessDtoSelected.getNtpId()).getResult("Variables");
        this.observableVariablesDto.clear();
        for(VariableDto var:variablesList){
            this.observableVariablesDto.add(var);
        }
        this.tableView_Var.setItems(observableVariablesDto);
    }

    private void updateConstrainsList() {
        this.constrainsList=new ArrayList<>();
        this.constrainsList=(List<ConstrainDto>)this.constrainService.getConstrainsByVariable(this.variableDtoSelected.getVarId()).getResult("Constrains");
        this.observableConstrainsDto.clear();
        for(ConstrainDto cnst:constrainsList){
            this.observableConstrainsDto.add(cnst);
        }
        this.tableView_Cons.setItems(observableConstrainsDto);
    }

    private void newNotificationProcess() {
        unbindNotificationProcess();
        this.notificationProcessDto = new NotificationProcessDto();
        bindNotificationProcess(true);
    }

    private void unbindNotificationProcess() {
        this.txtArea_description.textProperty().unbindBidirectional(this.notificationProcessDto.ntpDescription);
        this.txtArea_html.textProperty().unbindBidirectional(this.notificationProcessDto.ntpHtml);
    }

    private void bindNotificationProcess(boolean b) {
        this.txtArea_description.textProperty().bindBidirectional(this.notificationProcessDto.ntpDescription);
        this.txtArea_html.textProperty().bindBidirectional(this.notificationProcessDto.ntpHtml);     
    }

    private void newVariable() {
        unbindVariable();
        this.variableDto = new VariableDto();
        bindVariable(true);
    }

    private void unbindVariable() {
        this.txt_Var.textProperty().unbindBidirectional(this.variableDto.varVariable);
        this.txt_default.textProperty().unbindBidirectional(this.variableDto.varDefault);
        this.chk_type.valueProperty().unbindBidirectional(this.variableDto.varType);
    }

    private void bindVariable(boolean b) {
        this.txt_Var.textProperty().bindBidirectional(this.variableDto.varVariable);
        this.txt_default.textProperty().bindBidirectional(this.variableDto.varDefault);
        this.chk_type.valueProperty().bindBidirectional(this.variableDto.varType);
    }

    private void newConstrain() {
        unbindConstrain();
        this.constrainDto = new ConstrainDto();
        bindConstrain(true);
    }

    private void unbindConstrain() {
        this.txt_result.textProperty().unbindBidirectional(this.constrainDto.cnstResult);
        this.txt_symbol.textProperty().unbindBidirectional(this.constrainDto.cnstSymbol);
    }

    private void bindConstrain(boolean b) {
        this.txt_result.textProperty().bindBidirectional(this.constrainDto.cnstResult);
        this.txt_symbol.textProperty().bindBidirectional(this.constrainDto.cnstSymbol);
    }

}
