package cr.ac.una.sigeceuna.controller;

import cr.ac.una.sigeceuna.model.AreaDto;
import cr.ac.una.sigeceuna.model.ManagementDto;
import cr.ac.una.sigeceuna.model.UserDto;
import cr.ac.una.sigeceuna.service.AreaService;
import cr.ac.una.sigeceuna.service.ManagementService;
import cr.ac.una.sigeceuna.util.AppContext;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class HomeController extends Controller implements Initializable {

    @FXML
    private Label txtManagementRequested;
    @FXML
    private Label txtManagementToApprove;
    @FXML
    private Label txtManagementToAttend;
    @FXML
    private ChoiceBox<AreaDto> choiseBoxAreas;
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
    private UserDto userDto = (UserDto) AppContext.getInstance().get("User");

    //Lists to take information from the database
    List<ManagementDto> allManagements;

    //Services
    private ManagementService managementService = new ManagementService();
    private AreaService areService = new AreaService();
    
    //Observables
    private ObservableList<ManagementDto> observableRequestedProcedures = FXCollections.observableArrayList();
    private ObservableList<ManagementDto> observablePendingAttentionProcedures = FXCollections.observableArrayList();
    private ObservableList<ManagementDto> observableCompletedProcedures = FXCollections.observableArrayList();
    private ObservableList<ManagementDto> observableManagements = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @Override
    public void initialize() {

        cleanObservableList();
        loadTables();
        filterManagementsRequested();
        filterManagementsProceduresToaprove();
        filterManagementsAttend();
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

    private void loadAreasChoiseBox() {  
        choiseBoxAreas.setItems((ObservableList<AreaDto>) areService.getAreas());
    }

    //Tables
    private void loadRequestedProcedures() {
        allManagements = (List<ManagementDto>) managementService.getManagements().getResult("Managements");

        List<ManagementDto> requestedProcedures = allManagements.stream()
                .filter(mgt -> mgt.getUsrRequestingId().getUsrId().equals(userDto.getUsrId()))
                .collect(Collectors.toList());

        clmIssueRequested.setCellValueFactory(new PropertyValueFactory<>("mgtSubject"));
        clmDateRequested.setCellValueFactory(new PropertyValueFactory<>("mgtCreationdate"));
        clmAreaRequested.setCellValueFactory(new PropertyValueFactory<>("areName"));

        observableRequestedProcedures = FXCollections.observableArrayList(requestedProcedures);
        tblRequestedProcedures.setItems(observableRequestedProcedures);
    }

    //5
    private void loadPendingAttentionProcedures() {
        allManagements = (List<ManagementDto>) managementService.getManagements().getResult("Managements");

        List<ManagementDto> pendingAttentionProcedures = allManagements.stream()
                .filter(mgt -> mgt.getUsrAssignedId().getUsrId().equals(userDto.getUsrId()))
                .filter(mgt -> "In progress".equals(mgt.getMgtState()))
                .collect(Collectors.toList());

        clmIssuePending.setCellValueFactory(new PropertyValueFactory<>("mgtSubject"));
        clmDatePending.setCellValueFactory(new PropertyValueFactory<>("mgtCreationdate"));
        clmMaxDatePendingAttention.setCellValueFactory(new PropertyValueFactory<>("mgtMaxdatetosolve"));

        observablePendingAttentionProcedures = FXCollections.observableArrayList(pendingAttentionProcedures);
        tblPendingProcedures.setItems(observablePendingAttentionProcedures);
    }

    //6 Falta
    private void loadProceduresToApprove() {
        allManagements = (List<ManagementDto>) managementService.getManagementsToAprovedBy(userDto.getUsrId()).getResult("Managements");

        clmIssueApprove.setCellValueFactory(new PropertyValueFactory<>("mgtSubject"));
        clmDateApprove.setCellValueFactory(new PropertyValueFactory<>("mgtCreationdate"));

        observableManagements = FXCollections.observableArrayList(allManagements);

        tblProceduresToApprove.setItems(observableManagements);
    }

    //7
    private void loadCompletedProcedures() {
        allManagements = (List<ManagementDto>) managementService.getManagements().getResult("Managements");

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