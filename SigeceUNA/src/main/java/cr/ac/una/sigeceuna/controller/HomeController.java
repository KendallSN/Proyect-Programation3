package cr.ac.una.sigeceuna.controller;

import cr.ac.una.sigeceuna.model.AreaDto;
import cr.ac.una.sigeceuna.model.ManagementDto;
import cr.ac.una.sigeceuna.model.UserDto;
import cr.ac.una.sigeceuna.service.AreaService;
import cr.ac.una.sigeceuna.service.ManagementService;
import cr.ac.una.sigeceuna.util.AppContext;
import cr.ac.una.sigeceuna.util.FlowController;
import cr.ac.una.sigeceuna.util.Response;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class HomeController extends Controller implements Initializable {

    @FXML
    private Label txtManagementRequested;
    @FXML
    private Label txtManagementToApprove;
    @FXML
    private Label txtManagementToAttend;
    @FXML
    private ChoiceBox<String> choiceBoxAreas;
    @FXML
    private TableView<ManagementDto> tblRequestedProcedures;
    @FXML
    private TableColumn<ManagementDto, String> clmIssueRequested;
    @FXML
    private TableColumn<ManagementDto, String> clmDateRequested;
    @FXML
    private TableColumn<ManagementDto, String> clmAreaRequested;
    @FXML
    private TableView<ManagementDto> tblProceduresToApprove;
    @FXML
    private TableColumn<ManagementDto, String> clmIssueApprove;
    @FXML
    private TableColumn<ManagementDto, String> clmDateApprove;
    @FXML
    private TableView<ManagementDto> tblPendingProcedures;
    @FXML
    private TableColumn<ManagementDto, String> clmIssuePending;
    @FXML
    private TableColumn<ManagementDto, String> clmDatePending;
    @FXML
    private TableColumn<ManagementDto, String> clmMaxDatePendingAttention;
    @FXML
    private TableView<ManagementDto> tblCompleteProcedures;
    @FXML
    private TableColumn<ManagementDto, String> clmIssueRequestedCompleted;
    @FXML
    private TableColumn<ManagementDto, String> clmDateRequestedCompleted;
    @FXML
    private TableColumn<ManagementDto, String> clmAreaRequestedCompleted;

    //Dtos 
    private UserDto userDto;
    private ManagementDto managementDtoSelected;

    //Lists to take information from the database
    List<ManagementDto> allManagements;
    List<AreaDto> areas;
    List<String> areaNames;

    //Services
    private ManagementService managementService = new ManagementService();
    private AreaService areService = new AreaService();

    //Observables
    private ObservableList<ManagementDto> observableRequestedProcedures = FXCollections.observableArrayList();
    private ObservableList<ManagementDto> observablePendingAttentionProcedures = FXCollections.observableArrayList();
    private ObservableList<ManagementDto> observableCompletedProcedures = FXCollections.observableArrayList();
    private ObservableList<ManagementDto> observableManagements = FXCollections.observableArrayList();
    private ObservableList<String> observableAreas = FXCollections.observableArrayList();
    
    FilteredList<ManagementDto> filteredRequestedManagements;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @Override
    public void initialize() {
        this.userDto = (UserDto) AppContext.getInstance().get("User");
        this.managementDtoSelected=new ManagementDto();
        cleanObservableList();
        filterManagementsRequested();
        filterManagementsProceduresToaprove();
        filterManagementsAttend();
        loadAreasChoiceBox();
        loadTables();
        tableListeners();
    }
    
    private void tableListeners(){
        //Table selections
         this.tblRequestedProcedures.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue != null) {
                    this.managementDtoSelected = newValue;
                    AppContext.getInstance().set("ManagementSelected", newValue);
                    FlowController.getInstance().goView("ManagementView");
                }
            }
        );
         
        //Table selections
         this.tblProceduresToApprove.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue != null) {
                    this.managementDtoSelected = newValue;
                    AppContext.getInstance().set("ManagementSelected", newValue);
                    FlowController.getInstance().goView("ManagementView");
                }
            }
        );
         
        //Table selections
         this.tblPendingProcedures.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue != null) {
                    this.managementDtoSelected = newValue;
                    AppContext.getInstance().set("ManagementSelected", newValue);
                    FlowController.getInstance().goView("ManagementView");
                }
            }
        );
        
         //Table selections
         this.tblCompleteProcedures.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue != null) {
                    this.managementDtoSelected = newValue;
                    AppContext.getInstance().set("ManagementSelected", newValue);
                    FlowController.getInstance().goView("ManagementView");
                }
            }
        );
    }

    private void cleanObservableList() {
        this.observableRequestedProcedures.clear();
        this.observablePendingAttentionProcedures.clear();
        this.observableCompletedProcedures.clear();
        this.observableManagements.clear();
    }

    private void loadTables() {
        loadRequestedProcedures();
        loadPendingAttentionProcedures();
        loadProceduresToApprove();
        loadCompletedProcedures();
    }

    private void loadAreasChoiceBox() {
        Response response = areService.getAreas();

        areas = (List<AreaDto>) response.getResult("Areas");

        areaNames = areas.stream()
                .map(AreaDto::getAreName)
                .collect(Collectors.toList());

        observableAreas = FXCollections.observableArrayList(areaNames);
        observableAreas.add("");
        choiceBoxAreas.setItems(observableAreas);
    }

    //Tables
    private void loadRequestedProcedures() {
        Response response = managementService.getManagements();
        allManagements = (List<ManagementDto>) response.getResult("Managements");
        //Filtra
        List<ManagementDto> requestedProcedures = allManagements.stream()
                .filter(mgt -> mgt.getUsrRequestingId().getUsrId().equals(userDto.getUsrId()) &&
                        mgt.getMgtState().equals("In progress"))
                .collect(Collectors.toList());

        clmIssueRequested.setCellValueFactory(new PropertyValueFactory<>("mgtSubject"));
        clmDateRequested.setCellValueFactory(new PropertyValueFactory<>("mgtCreationdate"));
        clmAreaRequested.setCellValueFactory(new PropertyValueFactory<>("areName"));

        observableRequestedProcedures = FXCollections.observableArrayList(requestedProcedures);
        this.filteredRequestedManagements = new FilteredList<>(this.observableRequestedProcedures, p -> true);
        tblRequestedProcedures.setItems(filteredRequestedManagements);
        
        this.choiceBoxAreas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.filteredRequestedManagements.setPredicate(managementDto -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            return managementDto.getAreName().equals(newValue);
            });
        });
   }

    private void loadPendingAttentionProcedures() {

        Response response = managementService.getManagements();
        allManagements = (List<ManagementDto>) response.getResult("Managements");
        //Filtra
        List<ManagementDto> pendingAttentionProcedures = allManagements.stream()
                .filter(mgt -> mgt.getUsrAssignedId().getUsrId().equals(userDto.getUsrId()))
                .filter(mgt -> "In progress".equals(mgt.getMgtState()))
                .collect(Collectors.toList());

        clmIssuePending.setCellValueFactory(new PropertyValueFactory<>("mgtSubject"));
        clmDatePending.setCellValueFactory(new PropertyValueFactory<>("mgtCreationdate"));
        clmMaxDatePendingAttention.setCellValueFactory(new PropertyValueFactory<>("mgtMaxdatetosolve"));

        observablePendingAttentionProcedures = FXCollections.observableArrayList(pendingAttentionProcedures);
        FXCollections.sort(observablePendingAttentionProcedures, (o1, o2) -> o1.getMgtMaxdatetosolve().compareTo(o2.getMgtMaxdatetosolve()));
        tblPendingProcedures.setItems(observablePendingAttentionProcedures);
        
        tblPendingProcedures.setRowFactory(new Callback<TableView<ManagementDto>, TableRow<ManagementDto>>() {
            @Override
            public TableRow<ManagementDto> call(TableView<ManagementDto> tableView) {
                return new TableRow<>() {
                    @Override
                    protected void updateItem(ManagementDto item, boolean empty) {
                        super.updateItem(item, empty);
                        getStyleClass().removeAll("gray", "red", "yellow");
                        if (item == null || item.getMgtMaxdatetosolve() == null) {
                            setStyle("");
                        } else {
                            LocalDateTime now = LocalDateTime.now();
                            LocalDateTime maxDateTime = item.getMgtMaxdatetosolve();

                            // Calcular los días y minutos restantes
                            long daysRemaining = ChronoUnit.DAYS.between(now, maxDateTime);
                            long minutesRemaining = ChronoUnit.MINUTES.between(now, maxDateTime);
                            // Si la fecha ya se venció
                            if (minutesRemaining < 0) {
                                getStyleClass().add("gray");
                            } 
                            // 1 día restante o menos de 24 horas restantes
                            else if (daysRemaining == 0 && minutesRemaining > 0 && minutesRemaining <= 1440) {
                                getStyleClass().add("red");
                            } 
                            // Entre 2 y 7 días restantes
                            else if (daysRemaining >= 1 && daysRemaining <= 7) {
                                getStyleClass().add("yellow");
                            } 
                            // Más de una semana restante
                            else {
                                getStyleClass().removeAll("gray", "red", "yellow");
                            }
                        }
                    }
                };
            }
        });
    }

    private void loadProceduresToApprove() {
        Response response = managementService.getManagementsToAprovedBy(userDto.getUsrId());
        allManagements = (List<ManagementDto>) response.getResult("Managements");

        clmIssueApprove.setCellValueFactory(new PropertyValueFactory<>("mgtSubject"));
        clmDateApprove.setCellValueFactory(new PropertyValueFactory<>("mgtMaxdatetosolve"));

        observableManagements = FXCollections.observableArrayList(allManagements);
        FXCollections.sort(observableManagements, (o1, o2) -> o1.getMgtMaxdatetosolve().compareTo(o2.getMgtMaxdatetosolve()));
        tblProceduresToApprove.setItems(observableManagements);
    }

    private void loadCompletedProcedures() {
        Response response = managementService.getManagements();

        allManagements = (List<ManagementDto>) response.getResult("Managements");

        List<ManagementDto> completedProcedures = allManagements.stream()
                .filter(mgt -> mgt.getUsrRequestingId().getUsrId().equals(userDto.getUsrId()))
                .filter(mgt -> "Rejected".equals(mgt.getMgtState()) || "Resolved".equals(mgt.getMgtState()))
                .filter(mgt -> {
                    LocalDateTime solveDateTime = mgt.getMgtSolvedate();
                    return solveDateTime != null && solveDateTime.toLocalDate().isAfter(LocalDate.now().minusWeeks(1));
                })
                .collect(Collectors.toList());
        clmIssueRequestedCompleted.setCellValueFactory(new PropertyValueFactory<>("mgtSubject"));
        clmDateRequestedCompleted.setCellValueFactory(new PropertyValueFactory<>("mgtCreationdate"));
        
        clmAreaRequestedCompleted.setCellValueFactory(new PropertyValueFactory<>("areName"));
        observableCompletedProcedures = FXCollections.observableArrayList(completedProcedures);
        FXCollections.sort(observableCompletedProcedures, (o1, o2) -> o1.getMgtSolvedate().compareTo(o2.getMgtSolvedate()));
        tblCompleteProcedures.setItems(observableCompletedProcedures);
    }

    //Labels
    private void filterManagementsRequested() {
        allManagements = (List<ManagementDto>) managementService.getManagements().getResult("Managements");

        long countInProgress = allManagements.stream()
                .filter(mgt -> mgt.getUsrRequestingId().getUsrId().equals(userDto.getUsrId()))
                .filter(mgt -> "In progress".equals(mgt.getMgtState()))
                .count();
        txtManagementRequested.setText(String.valueOf(countInProgress));
    }

    private void filterManagementsProceduresToaprove() {
        allManagements = (List<ManagementDto>) managementService.getManagementsToAprovedBy(userDto.getUsrId()).getResult("Managements");

        long countToApprove = allManagements.size();
        txtManagementToApprove.setText(String.valueOf(countToApprove));
    }

    private void filterManagementsAttend() {
        allManagements = (List<ManagementDto>) managementService.getManagements().getResult("Managements");

        long countInProgress = allManagements.stream()
                .filter(mgt -> mgt.getUsrAssignedId().getUsrId().equals(userDto.getUsrId()))
                .filter(mgt -> "In progress".equals(mgt.getMgtState()))
                .count();

        txtManagementToAttend.setText(String.valueOf(countInProgress));
    }

    @FXML
    private void Pending(MouseEvent event) {
    }

}
