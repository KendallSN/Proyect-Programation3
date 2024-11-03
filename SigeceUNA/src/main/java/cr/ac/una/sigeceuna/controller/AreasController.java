package cr.ac.una.sigeceuna.controller;

import cr.ac.una.sigeceuna.model.ActivityDto;
import cr.ac.una.sigeceuna.model.AreaDto;
import cr.ac.una.sigeceuna.model.ManagementDto;
import cr.ac.una.sigeceuna.model.SubactivityDto;
import cr.ac.una.sigeceuna.service.ActivityService;
import cr.ac.una.sigeceuna.service.AreaService;
import cr.ac.una.sigeceuna.service.ManagementService;
import cr.ac.una.sigeceuna.service.SubactivityService;
import cr.ac.una.sigeceuna.util.FlowController;
import cr.ac.una.sigeceuna.util.Message;
import cr.ac.una.sigeceuna.util.Response;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public class AreasController extends Controller implements Initializable {
     @FXML
    private Button btn_DeleteActivitie;

    @FXML
    private Button btn_DeleteArea;

    @FXML
    private Button btn_DeleteSubActivitie;

    @FXML
    private Button btn_SaveActivitie;

    @FXML
    private Button btn_SaveArea;

    @FXML
    private Button btn_SaveSubActivitie;

    @FXML
    private Button btn_SearchActivitie;

    @FXML
    private Button btn_SearchArea;

    @FXML
    private Button btn_SearchSubActivitie;

    @FXML
    private CheckBox chk_AreaState;
    
    @FXML
    private TextField txt_ActivitieName;

    @FXML
    private TextField txt_AreaName;
    
    @FXML
    private TextField txt_SubActivitieName;

    @FXML
    private TextField txt_SearchActivitie;

    @FXML
    private TextField txt_SearchArea;

    @FXML
    private TextField txt_SearchSubActivitie;
    
    @FXML
    private TableColumn<ActivityDto, String> tblC_IDActivitie;

    @FXML
    private TableColumn<AreaDto, String> tblC_IDArea;

    @FXML
    private TableColumn<SubactivityDto, String> tblC_IDSubActivitie;

    @FXML
    private TableColumn<ActivityDto, String> tblC_NameActivitie;

    @FXML
    private TableColumn<AreaDto, String> tblC_NameArea;

    @FXML
    private TableColumn<SubactivityDto, String> tblC_NameSubActivitie;

    @FXML
    private TableView<ActivityDto> tblV_Activities;

    @FXML
    private TableView<AreaDto> tblV_Areas;

    @FXML
    private TableView<SubactivityDto> tblV_SubActivities;
    
    private AreaDto areaDto=new AreaDto();
    private ActivityDto activityDto=new ActivityDto();
    private SubactivityDto subactivityDto=new SubactivityDto();
    
    //Dtos Selected
    private AreaDto areaDtoSelected;
    private ActivityDto activityDtoSelected;
    private SubactivityDto subactivityDtoSelected;
    
    //Services
    private AreaService areaService=new AreaService();
    private ActivityService activityService=new ActivityService();
    private SubactivityService subactivityService=new SubactivityService();
    private ManagementService managementService=new ManagementService();
    
    //Lists to take information from the database
    private List<AreaDto>areasList;
    private List<ActivityDto>activitiesList;
    private List<SubactivityDto>subactivitiesList;
    
    //Observable lists
    private ObservableList<AreaDto>observableAreasDto=FXCollections.observableArrayList();
    private ObservableList<ActivityDto>observableActivitiesDto=FXCollections.observableArrayList();
    private ObservableList<SubactivityDto>observableSubactivitiesDto=FXCollections.observableArrayList();
    
    List<Node> requiredSpacesArea = new ArrayList<>();
    List<Node> requiredSpacesActivity = new ArrayList<>();
    List<Node> requiredSpacesSubactivity = new ArrayList<>();
    
    private ResourceBundle bundle;
    @Override
    public void initialize() {
        this.bundle=FlowController.getIdioma();
        indicateRequiredSpaces();
        this.tblV_Areas.getSelectionModel().clearSelection();
        this.tblV_Activities.getSelectionModel().clearSelection();
        this.tblV_SubActivities.getSelectionModel().clearSelection();
        
        this.areaDtoSelected=new AreaDto();
        this.activityDtoSelected=new ActivityDto();
        this.subactivityDtoSelected=new SubactivityDto();
        
        this.observableAreasDto.clear();
        this.observableActivitiesDto.clear();
        this.observableSubactivitiesDto.clear();
        
        this.areasList=(List<AreaDto>)this.areaService.getAreas().getResult("Areas");
        for(AreaDto are:areasList){
            this.observableAreasDto.add(are);
        }
        this.tblV_Areas.setItems(observableAreasDto);
        
        //Table selections
         this.tblV_Areas.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue != null) {
                    this.areaDtoSelected = newValue;
                    loadArea(this.areaDtoSelected.getAreId());
                    this.activityDtoSelected = new ActivityDto();
                    this.subactivityDtoSelected = new SubactivityDto();
                    updateActivitiesList();
                    this.observableSubactivitiesDto.clear();
                    this.tblV_SubActivities.setItems(observableSubactivitiesDto);
                }
            }
        );
        this.tblV_Activities.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue != null) {
                    this.activityDtoSelected = newValue;
                    loadActivity(this.activityDtoSelected.getActId());
                    this.subactivityDtoSelected = new SubactivityDto();
                    updateSubactivitiesList();
                }
            }
        );
         this.tblV_SubActivities.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue != null) {                 
                    this.subactivityDtoSelected=newValue;
                    loadSubctivity(this.subactivityDtoSelected.getSactId());
                }
            }
        );

         setupDragAndDropActivity();
         setupDragAndDropSubactivity();
         
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TableView Areas inicialization
        tblC_IDArea.setCellValueFactory(new PropertyValueFactory<>("areId"));
        tblC_NameArea.setCellValueFactory(new PropertyValueFactory<>("areName"));
        //TableView Activities inicialization
        tblC_IDActivitie.setCellValueFactory(new PropertyValueFactory<>("actId"));
        tblC_NameActivitie.setCellValueFactory(new PropertyValueFactory<>("actName"));  
        //TableView Subactivities inicialization
        tblC_IDSubActivitie.setCellValueFactory(new PropertyValueFactory<>("sactId"));
        tblC_NameSubActivitie.setCellValueFactory(new PropertyValueFactory<>("sactName")); 
        
        this.chk_AreaState.setSelected(false);
        this.txt_AreaName.clear();
        this.txt_ActivitieName.clear();
        this.txt_SubActivitieName.clear();
        
        //Dtos inicialization
        this.areaDto=new AreaDto();
        this.activityDto=new ActivityDto();
        this.subactivityDto=new SubactivityDto();
        
        newArea();
        newActivity();
        newSubactivity();
    }
    
    private void indicateRequiredSpaces() {
        this.requiredSpacesArea.clear();
        this.requiredSpacesArea.addAll(Arrays.asList(this.txt_AreaName));
        
        this.requiredSpacesActivity.clear();
        this.requiredSpacesActivity.addAll(Arrays.asList(this.txt_ActivitieName));
        
        this.requiredSpacesSubactivity.clear();
        this.requiredSpacesSubactivity.addAll(Arrays.asList(this.txt_SubActivitieName));
    }
    
    public String validateRequiredArea() {
        boolean valid = true;
        StringBuilder invalid = new StringBuilder();

        for (Node node : requiredSpacesArea) {
            if (node instanceof TextField) {
                TextField textField = (TextField) node;
                if (textField.getText() == null || textField.getText().isBlank()) {
                    valid = false;
                    invalid.append(textField.getPromptText()).append(", ");
                }
            }
        }
        if (valid) {
            return "";
        } else {
            return bundle.getString("emptySpaces")+ "[" + invalid.substring(0, invalid.length() - 2) + "].";
        }
    }
    
    public String validateRequiredActivity() {
        boolean valid = true;
        StringBuilder invalid = new StringBuilder();

        for (Node node : requiredSpacesActivity) {
            if (node instanceof TextField) {
                TextField textField = (TextField) node;
                if (textField.getText() == null || textField.getText().isBlank()) {
                    valid = false;
                    invalid.append(textField.getPromptText()).append(", ");
                }
            }
        }
        if (valid) {
            return "";
        } else {
            return bundle.getString("emptySpaces")+ "[" + invalid.substring(0, invalid.length() - 2) + "].";
        }
    }
    
    public String validateRequiredSubactivity() {
        boolean valid = true;
        StringBuilder invalid = new StringBuilder();

        for (Node node : requiredSpacesSubactivity) {
            if (node instanceof TextField) {
                TextField textField = (TextField) node;
                if (textField.getText() == null || textField.getText().isBlank()) {
                    valid = false;
                    invalid.append(textField.getPromptText()).append(", ");
                }
            }
        }
        if (valid) {
            return "";
        } else {
            return bundle.getString("emptySpaces")+ "[" + invalid.substring(0, invalid.length() - 2) + "].";
        }
    }
    
    private void setupDragAndDropActivity() {
    tblV_Activities.setRowFactory(tv -> {
        TableRow<ActivityDto> row = new TableRow<>();

        row.setOnDragDetected(event -> {
            if (!row.isEmpty()) {
                Dragboard db = row.startDragAndDrop(TransferMode.MOVE);

                ActivityDto activity = row.getItem();

                ClipboardContent content = new ClipboardContent();
                content.putString(activity.getActId().toString()); 
                db.setContent(content);

                WritableImage snapshot = row.snapshot(null, null);
                db.setDragView(snapshot);

                event.consume();
            }
        });

        row.setOnDragOver(event -> {
            if (event.getGestureSource() != row && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        row.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                Long draggedActId = Long.valueOf(db.getString());

                ActivityDto draggedActivity = observableActivitiesDto.stream()
                    .filter(act -> act.getActId().equals(draggedActId))
                    .findFirst().orElse(null);

                if (draggedActivity != null) {
                    int draggedIdx = observableActivitiesDto.indexOf(draggedActivity);
                    int targetIdx = row.getIndex();

                    if (draggedIdx != -1 && targetIdx != -1 && draggedIdx < observableActivitiesDto.size() && targetIdx <= observableActivitiesDto.size()) {
                        if (draggedIdx != targetIdx) {
                            observableActivitiesDto.remove(draggedIdx);
                            if (targetIdx > draggedIdx) {
                                targetIdx--;
                            }
                            observableActivitiesDto.add(targetIdx, draggedActivity);
                        
                            for (int i = 0; i < observableActivitiesDto.size(); i++) {
                                ActivityDto activity = observableActivitiesDto.get(i);
                                activity.setActIndexpertype(Long.valueOf(i + 1));
                                this.activityService.saveActivity(activity);
                            }
                        }

                        success = true;
                    }
                }
            }
            event.setDropCompleted(success);
            event.consume();
        });

        return row;
    });
}
    
    private void setupDragAndDropSubactivity() {
        tblV_SubActivities.setRowFactory(tv -> {
            TableRow<SubactivityDto> row = new TableRow<>();

            row.setOnDragDetected(event -> {
                if (!row.isEmpty()) {
                    Dragboard db = row.startDragAndDrop(TransferMode.MOVE);

                    SubactivityDto subactivity = row.getItem();

                    ClipboardContent content = new ClipboardContent();
                    content.putString(subactivity.getSactId().toString()); 
                    db.setContent(content);

                    WritableImage snapshot = row.snapshot(null, null);
                    db.setDragView(snapshot);

                    event.consume();
                }
            });

            row.setOnDragOver(event -> {
                if (event.getGestureSource() != row && event.getDragboard().hasString()) {
                    event.acceptTransferModes(TransferMode.MOVE);
                }
                event.consume();
            });

            row.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    Long draggedSactId = Long.valueOf(db.getString());

                    SubactivityDto draggedSubactivity = observableSubactivitiesDto.stream()
                        .filter(act -> act.getSactId().equals(draggedSactId))
                        .findFirst().orElse(null);

                    if (draggedSubactivity != null) {
                        int draggedIdx = observableSubactivitiesDto.indexOf(draggedSubactivity);
                        int targetIdx = row.getIndex();
                        if (draggedIdx != -1 && targetIdx != -1 && draggedIdx < observableSubactivitiesDto.size() && targetIdx <= observableSubactivitiesDto.size()) {
                            if (draggedIdx != targetIdx) {
                                observableSubactivitiesDto.remove(draggedIdx);
                                if (targetIdx > draggedIdx) {
                                    targetIdx--;
                                }
                                observableSubactivitiesDto.add(targetIdx, draggedSubactivity);

                                for (int i = 0; i < observableSubactivitiesDto.size(); i++) {
                                    SubactivityDto subactivity = observableSubactivitiesDto.get(i);
                                    subactivity.setSactIndexpertype(Long.valueOf(i + 1));
                                    this.subactivityService.saveSubactivity(subactivity);
                                }

                            }

                            success = true;
                        }
                    }
                }
                event.setDropCompleted(success);
                event.consume();
            });

            return row;
        });
    }
    
    @FXML
    void onActionBtnDeleteActivitie(ActionEvent event) {
        if(this.activityDtoSelected.getActId()==null){
           new Message().showModal(Alert.AlertType.ERROR, bundle.getString("noActivitySelected"), getStage(),bundle.getString("selectAnActivity"));
           return;
        }
        if(!activityDeleteable()){
            new Message().showModal(Alert.AlertType.ERROR, bundle.getString("deleteActivity"), getStage(),bundle.getString("secundaryRegistersActivity"));
           return;
        }
        else{
            boolean userConfirmed = new Message().showConfirmation(bundle.getString("deleteActivity"), getStage(), bundle.getString("sureToDeleteActivity"));
            if(userConfirmed){
                try {
                        Response response = this.activityService.deleteActivity(this.activityDtoSelected.getActId());
                        if (!response.getState()) {
                            new Message().showModal(Alert.AlertType.ERROR, bundle.getString("deleteActivity"), getStage(), response.getMessage());
                        } else {
                            this.tblV_Activities.getSelectionModel().clearSelection();
                            this.activityDtoSelected=new ActivityDto();
                            this.subactivityDtoSelected=new SubactivityDto();                      
                            this.observableSubactivitiesDto=FXCollections.observableArrayList();
                            this.tblV_SubActivities.setItems(observableSubactivitiesDto);
                            updateActivitiesList();
                            new Message().showModal(Alert.AlertType.INFORMATION, bundle.getString("deleteActivity"), getStage(), bundle.getString("deleteSuccessActivity"));
                        }         
                } catch (Exception ex) {
                    Logger.getLogger(AreasController.class.getName()).log(Level.SEVERE, bundle.getString("deleteErrorActivity"), ex);
                    new Message().showModal(Alert.AlertType.ERROR, bundle.getString("deleteActivity"), getStage(), bundle.getString("deleteErrorActivity"));
                }
            } 
        }
    }

    @FXML
    void onActionBtnDeleteArea(ActionEvent event) {
        if(this.areaDtoSelected.getAreId()==null){
           new Message().showModal(Alert.AlertType.ERROR, bundle.getString("noAreaSelected"), getStage(),bundle.getString("selectAnArea"));
           return;
        }
        if(!areaDeleteable()){
            new Message().showModal(Alert.AlertType.ERROR, bundle.getString("deleteArea"), getStage(),bundle.getString("secundaryRegistersArea"));
           return;
        }
        boolean userConfirmed = new Message().showConfirmation(bundle.getString("deleteArea"), getStage(), bundle.getString("sureToDeleteArea"));
        if(userConfirmed){
            try {
                    Response response = this.areaService.deleteArea(this.areaDtoSelected.getAreId());
                    if (!response.getState()) {
                        new Message().showModal(Alert.AlertType.ERROR, bundle.getString("deleteArea"), getStage(), response.getMessage());
                    } else {
                        this.tblV_Areas.getSelectionModel().clearSelection();
                        this.activityDtoSelected=new ActivityDto();
                        this.areaDtoSelected=new AreaDto();
                        this.subactivityDtoSelected=new SubactivityDto();
                        this.observableActivitiesDto=FXCollections.observableArrayList();
                        this.observableSubactivitiesDto=FXCollections.observableArrayList();
                        this.tblV_SubActivities.setItems(observableSubactivitiesDto);
                        this.tblV_Activities.setItems(observableActivitiesDto);
                        updateAreasList();
                        new Message().showModal(Alert.AlertType.INFORMATION, bundle.getString("deleteArea"), getStage(), bundle.getString("deleteSuccessArea"));
                    }         
            } catch (Exception ex) {
                Logger.getLogger(AreasController.class.getName()).log(Level.SEVERE, bundle.getString("deleteErrorArea"), ex);
                new Message().showModal(Alert.AlertType.ERROR, bundle.getString("deleteArea"), getStage(), bundle.getString("deleteErrorArea"));
            }
        }
    }

    @FXML
    void onActionBtnDeleteSubActivitie(ActionEvent event) {
        if(this.subactivityDtoSelected.getSactId()==null){
           new Message().showModal(Alert.AlertType.ERROR, bundle.getString("noSubactivitySelected"), getStage(),bundle.getString("selectASubactivity"));
           return;
        }
        if(!subactivyDeleteable()){
            new Message().showModal(Alert.AlertType.ERROR, bundle.getString("deleteSubactivity"), getStage(),bundle.getString("secundaryRegistersSubactivity"));
           return;
        }
        boolean userConfirmed = new Message().showConfirmation(bundle.getString("deleteSubactivity"), getStage(), bundle.getString("sureToDeleteSubactivity"));
        if(userConfirmed){
            try {
                    Response response = this.subactivityService.deleteSubactivity(this.subactivityDtoSelected.getSactId());
                    if (!response.getState()) {
                        new Message().showModal(Alert.AlertType.ERROR, bundle.getString("deleteSubactivity"), getStage(), response.getMessage());
                    } else {
                        this.tblV_SubActivities.getSelectionModel().clearSelection();
                        this.subactivityDtoSelected=new SubactivityDto();
                        updateSubactivitiesList();
                        new Message().showModal(Alert.AlertType.INFORMATION, bundle.getString("deleteSubactivity"), getStage(), bundle.getString("deleteSuccessSubactivity"));
                    }         
            } catch (Exception ex) {
                Logger.getLogger(AreasController.class.getName()).log(Level.SEVERE, bundle.getString("deleteErrorSubactivity"), ex);
                new Message().showModal(Alert.AlertType.ERROR, bundle.getString("deleteSubactivity"), getStage(), bundle.getString("deleteErrorSubactivity"));
            }
        }
    }

    @FXML
    void onActionBtnSaveActivitie(ActionEvent event) {
        if(this.areaDtoSelected.getAreId()==null){
            new Message().showModal(Alert.AlertType.ERROR, bundle.getString("noAreaSelected"), getStage(),bundle.getString("selectAnArea"));
        }
        else{
            String invalid = validateRequiredActivity();
            if (!invalid.isEmpty()) {
                new Message().showModal(Alert.AlertType.ERROR, bundle.getString("saveActivity"), getStage(), invalid);
            }
            else{
                try {    
                    this.activityDto.setAreId(this.areaDtoSelected.getAreId());
                    assignIndex(1);
                    Response response = this.activityService.saveActivity(activityDto);
                    if (!response.getState()) {
                        new Message().showModal(Alert.AlertType.ERROR, bundle.getString("saveActivity"), getStage(), response.getMessage());
                    } else {
                        this.txt_ActivitieName.clear();
                        updateAreasList();
                        updateActivitiesList();
                        unbindActivity();
                        bindActivity(false);
                    }
                    } catch (Exception ex) {
                        Logger.getLogger(ManagementController.class.getName()).log(Level.SEVERE, bundle.getString("saveErrorActivity"), ex);
                        new Message().showModal(Alert.AlertType.ERROR, bundle.getString("saveActivity"), getStage(), bundle.getString("saveErrorActivity"));
                    }
                   newActivity();
            }
        }
    }

    @FXML
    void onActionBtnSaveArea(ActionEvent event) {
        String invalid = validateRequiredArea();
            if (!invalid.isEmpty()) {
                new Message().showModal(Alert.AlertType.ERROR, bundle.getString("saveArea"), getStage(), invalid);
            }
            else{
                try {
                    Response response = this.areaService.saveArea(areaDto);
                    if (!response.getState()) {
                        new Message().showModal(Alert.AlertType.ERROR, bundle.getString("saveArea"), getStage(), response.getMessage());
                    } else {
                        this.txt_AreaName.clear();
                        this.chk_AreaState.setSelected(false);
                        updateAreasList();
                        unbindArea();
                        bindArea(false);
                    }
                    } catch (Exception ex) {
                        Logger.getLogger(ManagementController.class.getName()).log(Level.SEVERE, bundle.getString("saveErrorArea"), ex);
                        new Message().showModal(Alert.AlertType.ERROR, bundle.getString("saveArea"), getStage(), bundle.getString("saveErrorArea"));
                    }
                   newArea();
            }
    }
    
    private void assignIndex(int option){
        switch(option){
            case 1:
                if(this.activitiesList.isEmpty()){
                    this.activityDto.setActIndexpertype(1L);
                }
                else{
                    sortList(1);
                    ActivityDto dto=this.activitiesList.get(this.activitiesList.size()-1);
                    this.activityDto.setActIndexpertype(dto.getActIndexpertype()+1);}
                break;
            case 2:
                if(this.subactivitiesList.isEmpty()){
                    this.subactivityDto.setSactIndexpertype(1L);
                }
                else{
                    sortList(2);
                    SubactivityDto dto=this.subactivitiesList.get(this.subactivitiesList.size()-1);
                    this.subactivityDto.setSactIndexpertype(dto.getSactIndexpertype()+1);}
                break;
                
            default:break;
        }
    }
    
    private void sortList(int option){
        switch(option){
            case 1:
                this.activitiesList = this.activitiesList.stream()
                .sorted(Comparator.comparing(ActivityDto::getActIndexpertype))
                .collect(Collectors.toList());
                break;
            case 2:
                this.subactivitiesList = this.subactivitiesList.stream()
                .sorted(Comparator.comparing(SubactivityDto::getSactIndexpertype))
                .collect(Collectors.toList());
                break;
            default:break;
        }
    }
    @FXML
    void onActionBtnSaveSubActivitie(ActionEvent event) {
        if(this.activityDtoSelected.getActId()==null){
            new Message().showModal(Alert.AlertType.ERROR, bundle.getString("noActivitySelected"), getStage(),bundle.getString("selectAnActivity"));
        }
        else{
            String invalid = validateRequiredSubactivity();
            if (!invalid.isEmpty()) {
                new Message().showModal(Alert.AlertType.ERROR, bundle.getString("saveSubactivity"), getStage(), invalid);
            }
            else{
                try {    
                    this.subactivityDto.setActId(this.activityDtoSelected.getActId());
                    assignIndex(2);
                    Response response = this.subactivityService.saveSubactivity(subactivityDto);
                    if (!response.getState()) {
                        new Message().showModal(Alert.AlertType.ERROR, bundle.getString("saveSubactivity"), getStage(), response.getMessage());
                    } else {
                        this.txt_SubActivitieName.clear();
                        updateSubactivitiesList();
                        unbindSubactivity();
                        bindSubactivity(false);
                    }
                    } catch (Exception ex) {
                        Logger.getLogger(ManagementController.class.getName()).log(Level.SEVERE, bundle.getString("saveErrorSubactivity"), ex);
                        new Message().showModal(Alert.AlertType.ERROR, bundle.getString("saveSubactivity"), getStage(), bundle.getString("saveErrorSubactivity"));
                    }
                   newActivity();
            }
        }
    }

    @FXML
    void onActionBtnSearchActivitie(ActionEvent event) {
        String textSearchActivity = this.txt_SearchActivitie.getText().toLowerCase();
        if(textSearchActivity!=""){
            // Filter the orignal List
            List<ActivityDto> filteredList = this.activitiesList.stream()
                .filter(a -> a.getActName().toLowerCase().contains(textSearchActivity))
                .collect(Collectors.toList());

            // Actualice the observable list with the filtered list
            this.observableActivitiesDto = FXCollections.observableArrayList(filteredList);

            this.tblV_Activities.setItems(observableActivitiesDto);
        }
    }

    @FXML
    void onActionBtnSearchArea(ActionEvent event) {
        String textSearchArea = this.txt_SearchArea.getText().toLowerCase();
        if(textSearchArea!=""){
            // Filter the orignal List
            List<AreaDto> filteredList = this.areasList.stream()
                .filter(a -> a.getAreName().toLowerCase().contains(textSearchArea))
                .collect(Collectors.toList());

            // Actualice the observable list with the filtered list
            this.observableAreasDto = FXCollections.observableArrayList(filteredList);

            this.tblV_Areas.setItems(observableAreasDto);
        }
    }

    @FXML
    void onActionBtnSearchSubActivitie(ActionEvent event) {
        String textSearchSubactivity = this.txt_SearchSubActivitie.getText().toLowerCase();
        if(textSearchSubactivity!=""){
            // Filter the orignal List
            List<SubactivityDto> filteredList = this.subactivitiesList.stream()
                .filter(a -> a.getSactName().toLowerCase().contains(textSearchSubactivity))
                .collect(Collectors.toList());

            // Actualice the observable list with the filtered list
            this.observableSubactivitiesDto = FXCollections.observableArrayList(filteredList);

            this.tblV_SubActivities.setItems(observableSubactivitiesDto);
        }
    }

    private void newArea() {
        unbindArea();
        this.areaDto = new AreaDto();
        bindArea(true);
    }

    private void unbindArea() {
        this.txt_AreaName.textProperty().unbindBidirectional(this.areaDto.areName);
        this.chk_AreaState.selectedProperty().unbindBidirectional(this.areaDto.areState);
    }

    private void bindArea(boolean b) {
        this.txt_AreaName.textProperty().bindBidirectional(this.areaDto.areName);
        this.chk_AreaState.selectedProperty().bindBidirectional(this.areaDto.areState);
    }

    private void updateAreasList() {
        this.areasList=new ArrayList<>();
        this.areasList=(List<AreaDto>)this.areaService.getAreas().getResult("Areas");
        this.observableAreasDto.clear();
        for(AreaDto are:areasList){
            this.observableAreasDto.add(are);
        }
        this.tblV_Areas.setItems(observableAreasDto);
    }

    private void newActivity() {
        unbindActivity();
        this.activityDto = new ActivityDto();
        bindActivity(true);    
    }

    private void updateActivitiesList() {
        this.activitiesList=new ArrayList<>();     
        this.activitiesList=(List<ActivityDto>)this.activityService.getActivitiesByArea(this.areaDtoSelected.getAreId()).getResult("Activities");
        this.observableActivitiesDto.clear();
        sortList(1);
        for(ActivityDto act:activitiesList){
            this.observableActivitiesDto.add(act);
        }
        this.tblV_Activities.setItems(observableActivitiesDto);
    }

    private void unbindActivity() {
        this.txt_ActivitieName.textProperty().unbindBidirectional(this.activityDto.actName);
    }

    private void bindActivity(boolean b) {
        this.txt_ActivitieName.textProperty().bindBidirectional(this.activityDto.actName);
    }

    private void newSubactivity() {
        unbindSubactivity();
        this.subactivityDto = new SubactivityDto();
        bindSubactivity(true);
    }

    private void unbindSubactivity() {
        this.txt_SubActivitieName.textProperty().unbindBidirectional(this.subactivityDto.sactName);
    }

    private void bindSubactivity(boolean b) {
        this.txt_SubActivitieName.textProperty().bindBidirectional(this.subactivityDto.sactName);
    }

    private void updateSubactivitiesList() {
        this.subactivitiesList=new ArrayList<>();
        this.subactivitiesList=(List<SubactivityDto>)this.subactivityService.getSubactivitiesByActivity(this.activityDtoSelected.getActId()).getResult("Subactivities");
        this.observableSubactivitiesDto.clear();
        sortList(2);
        for(SubactivityDto sact:subactivitiesList){
            this.observableSubactivitiesDto.add(sact);
        }
        this.tblV_SubActivities.setItems(observableSubactivitiesDto);
    }
    
    private void loadArea(Long id) {
        try {
            Response response = areaService.getArea(id);

            if (response.getState()) {
                this.areaDto= (AreaDto) response.getResult("Area");
                if (areaDto != null) {
                    //unbindUser();
                    bindArea(false);
                    validateRequiredArea();
                } else {
                    new Message().showModal(Alert.AlertType.ERROR, bundle.getString("loadArea"), getStage(), "ID " + id + bundle.getString("notFound"));
                }
            } else {
                new Message().showModal(Alert.AlertType.ERROR, bundle.getString("loadArea"), getStage(), response.getMessage());
            }
        } catch (Exception ex) {
            Logger.getLogger(AreasController.class.getName()).log(Level.SEVERE, bundle.getString("loadErrorArea"), ex);
            new Message().showModal(Alert.AlertType.ERROR, bundle.getString("loadArea"), getStage(), bundle.getString("loadErrorArea"));
        }
    }
    
    private void loadActivity(Long id) {
        try {
            Response response = activityService.getActivity(id);

            if (response.getState()) {
                this.activityDto= (ActivityDto) response.getResult("Activity");
                if (activityDto != null) {
                    //unbindUser();
                    bindActivity(false);
                    validateRequiredActivity();
                } else {
                    new Message().showModal(Alert.AlertType.ERROR, bundle.getString("loadActivity"), getStage(), "ID " + id + bundle.getString("notFound"));
                }
            } else {
                new Message().showModal(Alert.AlertType.ERROR, bundle.getString("loadActivity"), getStage(), response.getMessage());
            }
        } catch (Exception ex) {
            Logger.getLogger(AreasController.class.getName()).log(Level.SEVERE, bundle.getString("loadErrorActivity"), ex);
            new Message().showModal(Alert.AlertType.ERROR, bundle.getString("loadActivity"), getStage(), bundle.getString("loadErrorActivity"));
        }
    }
    
    private void loadSubctivity(Long id) {
        try {
            Response response = subactivityService.getSubactivity(id);

            if (response.getState()) {
                this.subactivityDto= (SubactivityDto) response.getResult("Subactivity");
                if (subactivityDto != null) {
                    //unbindUser();
                    bindSubactivity(false);
                    validateRequiredSubactivity();
                } else {
                    new Message().showModal(Alert.AlertType.ERROR, bundle.getString("loadSubactivity"), getStage(), "ID " + id + bundle.getString("notFound"));
                }
            } else {
                new Message().showModal(Alert.AlertType.ERROR, bundle.getString("loadSubactivity"), getStage(), response.getMessage());
            }
        } catch (Exception ex) {
            Logger.getLogger(AreasController.class.getName()).log(Level.SEVERE, bundle.getString("loadErrorSubactivity"), ex);
            new Message().showModal(Alert.AlertType.ERROR, bundle.getString("loadSubactivity"), getStage(), bundle.getString("loadErrorSubactivity"));
        }
    }
    
    @FXML
    void onActionBtnNewAre(ActionEvent event) {
        if (new Message().showConfirmation(bundle.getString("cleanArea"), getStage(), bundle.getString("sureToCleanSpaces"))) {
            newArea();
            this.tblV_Areas.getSelectionModel().clearSelection();
        }
    }

    @FXML
    void onActionBtnNewSub(ActionEvent event) {
        if (new Message().showConfirmation(bundle.getString("cleanSubactivity"), getStage(), bundle.getString("sureToCleanSpaces"))) {
            newSubactivity();
            this.tblV_SubActivities.getSelectionModel().clearSelection();
        }
    }

    @FXML
    void onActionBtnNewAct(ActionEvent event) {
        if (new Message().showConfirmation(bundle.getString("cleanActivity"), getStage(), bundle.getString("sureToCleanSpaces"))) {
            newActivity();
            this.tblV_Activities.getSelectionModel().clearSelection();
        }
    }
    
    private boolean activityDeleteable(){
        List <ManagementDto>managementsList = (List<ManagementDto>) this.managementService.getManagements().getResult("Managements");
        List<SubactivityDto> subactivitiesList = (List<SubactivityDto>) this.subactivityService.getSubactivitiesByActivity(this.activityDtoSelected.getActId()).getResult("Subactivities");
        boolean deleteableSubactivities = !(managementsList.stream().filter(m->m.getSactId()!=null).anyMatch(m->subactivitiesList.stream().anyMatch(s->s.getSactId().equals(m.getSactId()))));
        return !(managementsList.stream().filter(m->m.getActId()!=null).anyMatch(m->m.getActId().equals(this.activityDtoSelected.getActId()))) && deleteableSubactivities;
    }
    
    private boolean subactivyDeleteable(){
        List <ManagementDto>managementsList = (List<ManagementDto>) this.managementService.getManagements().getResult("Managements");
        return !(managementsList.stream().filter(m->m.getSactId()!=null).anyMatch(m->m.getSactId().equals(this.subactivityDtoSelected.getSactId())));
    }

    private boolean areaDeleteable() {
        List <ManagementDto>managementsList = (List<ManagementDto>) this.managementService.getManagements().getResult("Managements");
        List<ActivityDto> activitiesList = (List<ActivityDto>) this.activityService.getActivitiesByArea(this.areaDtoSelected.getAreId()).getResult("Activities");
        List<SubactivityDto> subactivitiesList = (List<SubactivityDto>) this.subactivityService.getSubactivities().getResult("Subactivities");
        List<SubactivityDto> subactivities = subactivitiesList.stream().filter(m->(activitiesList.stream().anyMatch(a->m.getActId().equals(a.getActId())))).toList();
        boolean deleteableSubactivities = !(managementsList.stream().filter(m->m.getSactId()!=null).anyMatch(m->subactivities.stream().anyMatch(s->s.getSactId().equals(m.getSactId()))));
        boolean deleteableActivities = !(managementsList.stream().filter(m->m.getActId()!=null).anyMatch(m->activitiesList.stream().anyMatch(s->s.getActId().equals(m.getActId()))));
        return deleteableActivities && deleteableSubactivities;
    }
}
