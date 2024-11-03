
package cr.ac.una.sigeceuna.controller;

import cr.ac.una.sigeceuna.model.ActivityDto;
import cr.ac.una.sigeceuna.model.Filter;
import cr.ac.una.sigeceuna.model.ManagementDto;
import cr.ac.una.sigeceuna.model.SubactivityDto;
import cr.ac.una.sigeceuna.service.ActivityService;
import cr.ac.una.sigeceuna.service.ManagementService;
import cr.ac.una.sigeceuna.service.SubactivityService;
import cr.ac.una.sigeceuna.util.AppContext;
import cr.ac.una.sigeceuna.util.FlowController;
import cr.ac.una.sigeceuna.util.Message;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SearchManagementController extends Controller implements Initializable{
    @FXML
    private ChoiceBox<String> chb_Attribute;

    @FXML
    private ChoiceBox<String> chb_Operator;

    @FXML
    private TableColumn<ManagementDto, String> tblC_ID;

    @FXML
    private TableColumn<ManagementDto, String> tblC_State;

    @FXML
    private TableColumn<ManagementDto, String> tblC_Subject;

    @FXML
    private TableView<ManagementDto> tblV_Managements;
    
    @FXML
    private TableColumn<Filter, String> tblC_YOperator;
    
    @FXML
    private TableColumn<Filter, String> tblC_YAttribute;

    @FXML
    private TableColumn<Filter, String> tblC_YText;

    @FXML
    private TableView<Filter> tblV_YFilters;
    
    @FXML
    private TableColumn<Filter, String> tblC_OOperator;
    
    @FXML
    private TableColumn<Filter, String> tblC_OAttribute;

    @FXML
    private TableColumn<Filter, String> tblC_OText;
    
    @FXML
    private DatePicker dateP_date;

    @FXML
    private TableView<Filter> tblV_OFilters;


    @FXML
    private TextField txt_Filter;
    
    //Item selected from the tableView
    private Filter filterSelectedY;
    private Filter filterSelectedO;
    
    //Objects
    private Filter filter;
    private ActivityDto activityDto;
    private SubactivityDto subactivityDto;
    
    //Services
    private ManagementService managementService=new ManagementService();
    private ActivityService activityService= new ActivityService();
    private SubactivityService subactiviyService= new SubactivityService();
    
    //Lists
    private List<Filter> OfilterList;
    private List<Filter> YfilterList;
    private List<ManagementDto>managementsDtoList;
    
    //Observable lists
    private ObservableList<ManagementDto>observableManagementsDto=FXCollections.observableArrayList();
    private ObservableList<Filter>observableYFilters=FXCollections.observableArrayList();
    private ObservableList<Filter>observableOFilters=FXCollections.observableArrayList();
    
    private SimpleStringProperty combinedValue = new SimpleStringProperty();
    List<Node> requiredSpaces = new ArrayList<>();
    private ResourceBundle bundle;
    
    @Override
    public void initialize() {
        this.bundle=FlowController.getIdioma();
        indicateRequiredSpaces();
        this.activityDto=new ActivityDto();
        this.subactivityDto=new SubactivityDto();
        clearSpaces();
        this.OfilterList=new ArrayList<>();
        this.YfilterList=new ArrayList<>();
        this.tblV_YFilters.getSelectionModel().clearSelection();
        this.tblV_Managements.getSelectionModel().clearSelection();
        
        this.filterSelectedY=new Filter();
        this.filterSelectedO=new Filter();
        this.combinedValue=new SimpleStringProperty();
        
        this.observableYFilters.clear();
        this.observableOFilters.clear();
        this.observableManagementsDto.clear();        
        
        tblC_State.setCellValueFactory(new PropertyValueFactory<>("mgtState"));
        tblC_State.setCellFactory(column -> new TableCell<ManagementDto, String>() {
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
        fillManagementDtoList();
        this.observableManagementsDto.addAll(managementsDtoList);
        FXCollections.sort(observableManagementsDto,Comparator.comparing(ManagementDto::getMgtId));
        this.tblV_Managements.setItems(observableManagementsDto);
        
        //Table selections
         this.tblV_Managements.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue != null) {
                    AppContext.getInstance().set("ManagementSelected", newValue);
                    FlowController.getInstance().goView("ManagementView");
                }
            }
        );
         
        this.tblV_YFilters.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue != null) {
                    this.filterSelectedY = newValue;
                    this.filterSelectedO=new Filter();
                }
            }
        );
        this.tblV_OFilters.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue != null) {
                    this.filterSelectedO = newValue;
                    this.filterSelectedY=new Filter();
                }
            }
        );
        
        // textField Listener
        this.txt_Filter.textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.isEmpty()) {
                combinedValue.set(newText);
                this.dateP_date.setValue(null);
            }
        });

        // datePicker Listener
        this.dateP_date.valueProperty().addListener((obs, oldDate, newDate) -> {
            if (newDate != null) {
                combinedValue.set(newDate.toString());
                this.txt_Filter.clear();
            }
        });       
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.bundle=FlowController.getIdioma();
        //TableView inicialization
        tblC_ID.setCellValueFactory(new PropertyValueFactory<>("mgtId"));
        tblC_Subject.setCellValueFactory(new PropertyValueFactory<>("mgtSubject"));
        
        
        tblC_YAttribute.setCellValueFactory(new PropertyValueFactory<>("attribute"));
        tblC_YOperator.setCellValueFactory(new PropertyValueFactory<>("operator"));
        tblC_YText.setCellValueFactory(new PropertyValueFactory<>("text"));
        
        tblC_OAttribute.setCellValueFactory(new PropertyValueFactory<>("attribute"));
        tblC_OOperator.setCellValueFactory(new PropertyValueFactory<>("operator"));
        tblC_OText.setCellValueFactory(new PropertyValueFactory<>("text"));
        
        //choicebox operator inicialization
        ObservableList<String> optionsOperator = FXCollections.observableArrayList(
            "=", bundle.getString("contains"), "<", ">");
        this.chb_Operator.setItems(optionsOperator);
        chb_Operator.setAccessibleText("Operador");
        
        //choicebox attribute inicialization
        ObservableList<String> optionsAttribute = FXCollections.observableArrayList(
            bundle.getString("id"),
            bundle.getString("subject"),
            bundle.getString("state"),
            bundle.getString("description"),
            bundle.getString("maxSolveDate"),
            bundle.getString("creationDate"),
            bundle.getString("resolutionDate"),
            bundle.getString("requestingUser"),
            bundle.getString("assignedUser"),
            bundle.getString("activityName"),
            bundle.getString("subactivityName"),
            bundle.getString("areaName")
        );
        this.chb_Attribute.setItems(optionsAttribute);
        chb_Attribute.setAccessibleText("Attribute");
        
        this.filter=new Filter();
        newFilter();
    }
    
    private void fillManagementDtoList(){
        this.managementsDtoList=new ArrayList<>();
        this.managementsDtoList=(List<ManagementDto>) this.managementService.getManagements().getResult("Managements");       
    }
    
    private void indicateRequiredSpaces() {
        this.requiredSpaces.clear();
        this.requiredSpaces.addAll(Arrays.asList(this.chb_Operator, this.chb_Attribute));     
    }
    
    public String validateRequired() {
        Boolean valid = true;
        String invalid = "";
        for (Node node : requiredSpaces) {
            if (node instanceof ChoiceBox && ((ChoiceBox<?>) node).getSelectionModel().getSelectedIndex() < 0) {
                if (valid) {
                    invalid += ((ChoiceBox<?>) node).getAccessibleText();
                } else {
                    invalid += ", " + ((ChoiceBox<?>) node).getAccessibleText();
                }
                valid = false;
            }
        }
        if ((this.txt_Filter.getText() == null || this.txt_Filter.getText().isBlank()) && this.dateP_date.getValue() == null) {
            if (valid) {
                invalid += bundle.getString("invalidDateorText");
            } else {
                invalid += ", "+bundle.getString("invalidDateorText");
            }
            valid = false;
        }
        if (valid) {
            return "";
        } else {
            return bundle.getString("emptySpaces")+"[" + invalid + "].";
        }
    }
    
    @FXML
    void onActionBtnAddOFilter(ActionEvent event) {
        String invalid = validateRequired();
        if (!invalid.isEmpty()) {
                new Message().showModal(Alert.AlertType.ERROR, bundle.getString("addFilter"), getStage(), invalid);
            }
        else{
            this.observableOFilters.add(filter);
            this.tblV_OFilters.setItems(observableOFilters);
            unbindFilter();
            bindFilter(true);
            newFilter();
            clearSpaces();
        }
    }

    @FXML
    void onActionBtnAddYFilter(ActionEvent event) {
        String invalid = validateRequired();
        if (!invalid.isEmpty()) {
                new Message().showModal(Alert.AlertType.ERROR, bundle.getString("addFilter"), getStage(), invalid);
            }
        else{
            this.observableYFilters.add(filter);
            this.tblV_YFilters.setItems(observableYFilters);
            unbindFilter();
            bindFilter(true);       
            newFilter();
            clearSpaces();
        }
    }

    @FXML
    void onActionBtnFilter(ActionEvent event) {
        fillManagementDtoList();
        if(this.managementsDtoList.isEmpty()){
            new Message().showModal(Alert.AlertType.ERROR, bundle.getString("noManagements"), getStage(),bundle.getString("noManagementsForFilters"));
        }
        else{
            this.observableManagementsDto=applyFilters();
            this.tblV_Managements.setItems(observableManagementsDto);
        }
    }
    
    @FXML
    void onActionBtnDeleteFilter(ActionEvent event) {
        if(!this.filterSelectedO.getText().isEmpty()){
            this.observableOFilters.remove(this.filterSelectedO);
            this.observableManagementsDto=applyFilters();
            this.tblV_Managements.setItems(observableManagementsDto);
            this.filterSelectedO=new Filter();
        }
        else if(!this.filterSelectedY.getText().isEmpty()){
            this.observableYFilters.remove(this.filterSelectedY);
            this.observableManagementsDto=applyFilters();
            this.tblV_Managements.setItems(observableManagementsDto);
            this.filterSelectedY=new Filter();
        }
        else{
            new Message().showModal(Alert.AlertType.ERROR, bundle.getString("noFilterSelected"), getStage(),bundle.getString("selectAFilter"));
        }
    }
    
    @FXML
    void onActionBtnCleanFilters(ActionEvent event) {
        this.observableOFilters.clear();
        this.observableYFilters.clear();
        fillManagementDtoList();
        this.observableManagementsDto=applyFilters();
        this.tblV_Managements.setItems(observableManagementsDto);
    }

    @FXML
    void onActionBtnExportToExcel(ActionEvent event) {
        List<String> headers = Arrays.asList(
            bundle.getString("id"),
            bundle.getString("subject"),
            bundle.getString("description"),
            bundle.getString("creationDate"),
            bundle.getString("maxSolveDate"),
            bundle.getString("resolutionDate"),
            bundle.getString("state"),
            bundle.getString("activityName"),
            bundle.getString("subactivityName"),
            bundle.getString("areaName"),
            bundle.getString("requestingUser"),
            bundle.getString("assignedUser")
        );
        
        List<ManagementDto>managements=new ArrayList<>();
        managements.addAll(this.observableManagementsDto);
        if(managements.isEmpty()){
            new Message().showModal(Alert.AlertType.ERROR, bundle.getString("noManagements"), getStage(),bundle.getString("noManagementsForExcel"));
            return;
        }
        // Excel creation
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(bundle.getString("label.managements"));
            
        // Set headers
        int column = 0;
        int row = 0;
        Row headerRow = sheet.createRow(row);
        for (String header : headers) {          
            headerRow.createCell(column).setCellValue(header);
            column++;
        }
        for(ManagementDto mgt : managements){
            row++;
            column = 0;
            Row mgtheaderRow = sheet.createRow(row);
            for(String header : headers){               
                mgtheaderRow.createCell(column).setCellValue(columnData(column, mgt));                
                column++;
            }    
        }
        

        // Save excel window config
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(bundle.getString("saveFile"));
        fileChooser.setInitialFileName(bundle.getString("label.managements") + " " + ".xlsx");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Excel Files", "*.xlsx"));
        File file = fileChooser.showSaveDialog(((Window) this.tblV_Managements.getScene().getWindow()));

        if (file != null) {
            try (FileOutputStream fileOut = new FileOutputStream(file)) {
                workbook.write(fileOut);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private String columnData(int option, ManagementDto mgt){
        String result="";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        switch(option){
            case 0:
                return result=mgt.getMgtId().toString();
            case 1:
                return result=mgt.getMgtSubject();
            case 2:
                return result=mgt.getMgtDescription();
            case 3:               
                return result=mgt.getMgtCreationdate().format(formatter);
            case 4:
                return result=mgt.getMgtMaxdatetosolve().format(formatter);
            case 5:
                if(mgt.getMgtSolvedate()==null){return result;}
                return result=mgt.getMgtSolvedate().format(formatter);
            case 6:
                return result=mgt.getMgtState();
            case 7:
                if(mgt.getActId()==null){return result;}
                this.activityDto=(ActivityDto) this.activityService.getActivity(mgt.getActId()).getResult("Activity");
                if(this.activityDto.getActId()==null){return result;}
                return result=this.activityDto.getActName();
            case 8:
                if(mgt.getSactId()==null){return result;}
                this.subactivityDto=(SubactivityDto) this.subactiviyService.getSubactivity(mgt.getSactId()).getResult("Subactivity");
                if(this.subactivityDto.getSactId()==null){return result;}
                return result=this.subactivityDto.getSactName();
            case 9:
                return result=mgt.getAreName();
            case 10:
                return result=mgt.getUsrRequestingId().getUsrName() + " " + mgt.getUsrRequestingId().getUsrSurname()
                        + " " + mgt.getUsrRequestingId().getUsrLastname();
            case 11:
                return result=mgt.getUsrAssignedId().getUsrName() + " " + mgt.getUsrAssignedId().getUsrSurname()
                        + " " + mgt.getUsrAssignedId().getUsrLastname();
            default:
                return result;
        }
    
    }
    
    private void clearSpaces(){
        this.dateP_date.setValue(null);
        this.txt_Filter.clear();
        this.chb_Attribute.getSelectionModel().clearSelection();
        this.chb_Operator.getSelectionModel().clearSelection();
    }

    private void newFilter() {
        unbindFilter();
        this.filter= new Filter();
        bindFilter(true);
    }

    private void unbindFilter() {
        this.filter.text.unbindBidirectional(this.combinedValue);
        this.chb_Operator.valueProperty().unbindBidirectional(this.filter.operator);
        this.chb_Attribute.valueProperty().unbindBidirectional(this.filter.attribute);
    }

    private void bindFilter(boolean b) {
        this.filter.text.bindBidirectional(this.combinedValue);
        this.chb_Operator.valueProperty().bindBidirectional(this.filter.operator);
        this.chb_Attribute.valueProperty().bindBidirectional(this.filter.attribute);
    }
    
    public ObservableList<ManagementDto> applyFilters() {
        this.OfilterList.clear();
        this.YfilterList.clear();
        this.YfilterList.addAll(this.observableYFilters);
        this.OfilterList.addAll(this.observableOFilters);
        List<ManagementDto> resultY = new ArrayList<>();
        List<ManagementDto> resultO = new ArrayList<>();
        List<ManagementDto> result = new ArrayList<>(this.managementsDtoList);
        if(!this.YfilterList.isEmpty()){
            resultY = this.managementsDtoList.stream()
            .filter(mgt -> meetsFiltersY(mgt))
            .collect(Collectors.toList());
        }
        
        if (!this.OfilterList.isEmpty()) {
            resultO = this.managementsDtoList.stream()
                .filter(mgt -> meetsFiltersO(mgt))
                .collect(Collectors.toList());
        }
        
        result = result.stream().filter(isItemInList(resultY).or(isItemInList(resultO))).toList();
        if(this.OfilterList.isEmpty() && this.YfilterList.isEmpty()){
            result=this.managementsDtoList;
        }
        return FXCollections.observableArrayList(result);
    }
    
    Predicate<ManagementDto> isItemInList(List<ManagementDto> managements){
        return mgt->managements.stream().anyMatch(mg->mgt.getMgtId().equals(mg.getMgtId()));
    }
    
    private boolean meetsFiltersY(ManagementDto mgmt) {
        for (Filter filter : this.YfilterList) {
            if (!meetFilter(mgmt, filter)) {
                return false;
            }
        }
        return true;
    }

    private boolean meetsFiltersO(ManagementDto mgmt) {
        for (Filter filter : this.OfilterList) {
            if (meetFilter(mgmt, filter)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean meetFilter(ManagementDto mgt, Filter filter) {
        String attribute = filter.getAttribute();
        String operator = filter.getOperator();
        String filterValue = filter.getText();

        switch (attribute) {
            case "Id":
                return compareNumericValues(mgt.getMgtId(), filterValue, operator);
            case "Asunto":
            case "Subject":
                return compareValues(mgt.getMgtSubject(), filterValue, operator);
            case "Estado":
            case "State":
                return compareValues(mgt.getMgtState(), filterValue, operator);
            case "Descripción":
            case "Description":
                return compareValues(mgt.getMgtDescription(), filterValue, operator);
            case "Fecha máxima para resolver":
            case "Max Date to Resolve":
                return compareValuesDate(mgt.getMgtMaxdatetosolve(), filterValue, operator);
            case "Fecha de creación":
            case "Creation Date":
                return compareValuesDate(mgt.getMgtCreationdate(),filterValue, operator);
            case "Fecha de resolución":
            case "Resolution Date":
                if(mgt.getMgtSolvedate()==null){return false;}
                return compareValuesDate(mgt.getMgtSolvedate(),filterValue, operator);
            case "Usuario solicitante":
            case "Requesting User":
                return compareValues(mgt.getUsrRequestingId().getUsrName(),filterValue, operator);
            case "Usuario asignado":
            case "Assigned User":
                return compareValues(mgt.getUsrAssignedId().getUsrName(),filterValue, operator);
            case "Nombre de la actividad":
            case "Activity Name":
                this.activityDto=(ActivityDto) this.activityService.getActivity(mgt.getActId()).getResult("Activity");
                if(this.activityDto.getActId()==null){return false;}
                return compareValues(this.activityDto.getActName(),filterValue, operator);
            case "Nombre de la subactividad":
            case "Subactivity Name":
                this.subactivityDto=(SubactivityDto) this.subactiviyService.getSubactivity(mgt.getSactId()).getResult("Subactivity");
                if(this.subactivityDto.getSactId()==null){return false;}
                return compareValues(this.subactivityDto.getSactName(),filterValue, operator);
            case "Nombre del área":
            case "Area Name":
                return compareValues(mgt.getAreName(),filterValue, operator);
            default:
                return false;
        }
    }

    private boolean compareValues(String attribute, String filterValue, String operator) {
        switch (operator) {
        case "=":
            return attribute.toLowerCase().equals(filterValue.toLowerCase());
        case "<":
            return attribute.toLowerCase().compareTo(filterValue.toLowerCase()) < 0;
        case ">":
            return attribute.toLowerCase().compareTo(filterValue.toLowerCase()) > 0;
        case "contiene":
        case "contains":
            return attribute.toLowerCase().contains(filterValue.toLowerCase());
        default:
            return false;
        }
    }
    
    private boolean compareNumericValues(Long attribute, String filterValue, String operator) {
        try {
        Long filterValueLong = Long.parseLong(filterValue);
        
        switch (operator) {
            case "=":
                return attribute.equals(filterValueLong);
            case "<":
                return attribute < filterValueLong;
            case ">":
                return attribute > filterValueLong;
            default:
                return false;
        }
        } catch (NumberFormatException e) {
            new Message().showModal(Alert.AlertType.ERROR, bundle.getString("noNumericFormat"), getStage(),bundle.getString("errorEnterNumberForComparison"));
            return false;
            }
    }

    private boolean compareValuesDate(LocalDateTime dateTime, String filterValue, String operator) {
        try {
            LocalDate dateFilter = LocalDate.parse(filterValue);
            LocalDate date = dateTime.toLocalDate();

            switch (operator) {
                case "=":
                    return date.isEqual(dateFilter);
                case "<":
                    return date.isBefore(dateFilter);
                case ">":
                    return date.isAfter(dateFilter);
                default:
                    return false;
            }
        } catch (DateTimeParseException e) {
            new Message().showModal(Alert.AlertType.ERROR, bundle.getString("noDateFormat"), getStage(),bundle.getString("errorEnterDateForComparison"));
            return false;
        }
    }
}
