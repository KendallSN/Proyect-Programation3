
package cr.ac.una.sigeceuna.controller;

import cr.ac.una.sigeceuna.model.gestionPrueba;
import cr.ac.una.sigeceuna.controller.Controller;
import cr.ac.una.sigeceuna.model.ManagementDto;
import cr.ac.una.sigeceuna.model.UserDto;
import cr.ac.una.sigeceuna.service.ManagementService;
import cr.ac.una.sigeceuna.util.AppContext;
import cr.ac.una.sigeceuna.util.FlowController;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class CalendarController extends Controller implements Initializable{
    @FXML
    private DatePicker datePicker_ManagementCalendar;
    
    @FXML
    private TitledPane titledPane_Information;
    
    @FXML
    private TextArea txtArea_Information;
    
    @FXML
    private TableColumn<ManagementDto, String> tblC_ID;

    @FXML
    private TableColumn<ManagementDto, LocalDate> tblC_MaxDateToSolve;

    @FXML
    private TableColumn<ManagementDto, String> tblC_Subject;

    @FXML
    private TableView<ManagementDto> tblV_Managements;
    
    private UserDto loggedUser;

    private List<ManagementDto> managementsList;
    //Dto Selected
    private ManagementDto managementDtoSelected;
    //Observable list for the tableView
    private ObservableList<ManagementDto>observableManagementDto=FXCollections.observableArrayList();
    private ManagementService managementService=new ManagementService();
    private ResourceBundle bundle;
    @Override
    public void initialize() {
        this.bundle=FlowController.getIdioma();
        this.datePicker_ManagementCalendar.setValue(null);
        this.tblV_Managements.getSelectionModel().clearSelection();
        this.managementDtoSelected=new ManagementDto();
        this.observableManagementDto.clear();
            this.loggedUser=(UserDto)AppContext.getInstance().get("User");
    // Inicialize managementsList
            this.managementsList=new ArrayList<>();
            this.managementsList=(List<ManagementDto>)this.managementService.getManagements().getResult("Managements");
            this.managementsList=this.managementsList.stream().filter(x->x.getUsrAssignedId().getUsrId()
                    .equals(this.loggedUser.getUsrId()) && (x.getMgtState().equals("In progress"))).collect(Collectors.toList());
        datePicker_ManagementCalendar.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                if (!empty) {
                    List<ManagementDto> dayManagements = managementsList.stream()
                            .filter(m -> m.getMgtMaxdatetosolve().toLocalDate().equals(date))
                            .collect(Collectors.toList());
                    if (!dayManagements.isEmpty()) {
                        StringBuilder managementsSubject = new StringBuilder();
                        for (ManagementDto management : dayManagements) {
                            managementsSubject.append(management.getMgtSubject()).append("\n");
                        }

                        Tooltip tooltip = new Tooltip(managementsSubject.toString());
                        tooltip.setWrapText(true);
                        setTooltip(tooltip);
                        setStyle("-fx-background-color: #FFD700;");
                        setText(getText() + " *");
                    }
                }
            }
        });
        showInstructions();
        datePicker_ManagementCalendar.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                this.observableManagementDto.clear();
                List<ManagementDto> filteredList = managementsList.stream()
                        .filter(m -> m.getMgtMaxdatetosolve().toLocalDate().equals(newValue))
                        .collect(Collectors.toList());        
                this.observableManagementDto.addAll(filteredList);
                this.tblV_Managements.setItems(observableManagementDto);
            }
        });
        //Table selections
         this.tblV_Managements.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue != null) {
                    this.managementDtoSelected = newValue;
                    AppContext.getInstance().set("ManagementSelected", newValue);
                    FlowController.getInstance().goView("ManagementView");
                }
            }
        );
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TableView Managements inicialization
        tblC_ID.setCellValueFactory(new PropertyValueFactory<>("mgtId"));
        tblC_Subject.setCellValueFactory(new PropertyValueFactory<>("mgtSubject"));
        tblC_MaxDateToSolve.setCellValueFactory(cellData -> {
            LocalDateTime dateTime = cellData.getValue().getMgtMaxdatetosolve();
            LocalDate localDate = dateTime != null ? dateTime.toLocalDate() : null;
            return new SimpleObjectProperty<>(localDate);
        });

        tblC_MaxDateToSolve.setCellFactory(column -> new TableCell<ManagementDto, LocalDate>() {
            private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.format(dateFormatter));
                }
            }
        });
    }  
    
    private void showInstructions() {
        txtArea_Information.setText(bundle.getString("calendar.instructions"));
        titledPane_Information.setContent(txtArea_Information);
    }
}
