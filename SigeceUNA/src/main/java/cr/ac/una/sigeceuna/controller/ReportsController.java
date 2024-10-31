package cr.ac.una.sigeceuna.controller;

import cr.ac.una.sigeceuna.model.AreaDto;
import cr.ac.una.sigeceuna.model.ManagementDto;
import cr.ac.una.sigeceuna.model.ManagementaprobationDto;
import cr.ac.una.sigeceuna.model.UserDto;
import cr.ac.una.sigeceuna.service.AreaService;
import cr.ac.una.sigeceuna.service.ManagementAprobationService;
import cr.ac.una.sigeceuna.service.ManagementService;
import cr.ac.una.sigeceuna.service.UserService;
import cr.ac.una.sigeceuna.util.AppContext;
import cr.ac.una.sigeceuna.util.FlowController;
import cr.ac.una.sigeceuna.util.Message;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

public class ReportsController extends Controller implements Initializable {

    @FXML
    private CheckBox chkAttended;
    @FXML
    private CheckBox chkRequested;
    @FXML
    private DatePicker dprFromDate;
    @FXML
    private DatePicker dprSpecificDate;
    @FXML
    private DatePicker dprUntilDate;
    @FXML
    private ListView<UserDto> listViewUserReport1;
    @FXML
    private RadioButton rdbRangeDates;
    @FXML
    private RadioButton rdbSpecificDate;
    @FXML
    private TextField txtFilterUser;
    @FXML
    private VBox vBoxRangeDates;
    @FXML
    private VBox vBoxSpecificDate;

    private ObservableList<UserDto> observableListUsers = FXCollections.observableArrayList();
    private FilteredList<UserDto> filteredUsers;
    private List<UserDto> userDtos = new ArrayList<>();
    private UserService userService = new UserService();
    private ManagementService managementService = new ManagementService();

    Predicate<ManagementDto> isManagementIn(List<ManagementDto> list) {
        return m -> list.stream().anyMatch(ma -> ma.getMgtId().equals(m.getMgtId()));
    }
    Predicate<ManagementDto> isAproved = m -> m.getMgtState().equals("Resolved");

    Predicate<ManagementDto> requestedInRange(LocalDate from, LocalDate until) {
        return m -> m.getMgtCreationdate().toLocalDate().isBefore(until.plusDays(Long.valueOf(1)))
                && m.getMgtCreationdate().toLocalDate().isAfter(from);
    }
    //Second Report vars

    private FilteredList<AreaDto> filteredAreas;
    private ObservableList<AreaDto> observableListAreas;
    private List<AreaDto> areaDtos = new ArrayList<>();
    private AreaService areaService = new AreaService();
    @FXML
    private RadioButton rdbAllManagements;
    @FXML
    private RadioButton rdbManagementsPerArea;
    @FXML
    private ListView<AreaDto> listViewAreas;
    @FXML
    private TextField txtFilterAreas;
    @FXML
    private VBox vBoxAreas;
    @FXML
    private Button btnGenerateReport2;

    //End Second Report vars
    //Third Report vars
    private FilteredList<UserDto> filteredUsers1;
    private ObservableList<UserDto> observableListUsers1;
    private List<UserDto> userDtos1 = new ArrayList<>();
    @FXML
    private ListView<UserDto> listViewUserReport3;
    @FXML
    private TextField txtFilterUser2;
    @FXML
    private CheckBox chkAprobationsApproveds;
    @FXML
    private CheckBox chkAprobationsPendings;
    @FXML
    private CheckBox chkAprobationsRejecteds;
    @FXML
    private Button btnGenerateReport3;
    private ManagementAprobationService managementAprobationService = new ManagementAprobationService();
    private ResourceBundle bundle;
    //End Third Report vars
    @Override
    public void initialize() {
        this.bundle=FlowController.getIdioma();
        //Reset selections
        this.changeManagementsReportPerformance(true);
        this.changeTypeOfTime(true);
        
        //First report initialize
        this.userDtos = (List<UserDto>) userService.getUsers().getResult("Users");
        observableListUsers = FXCollections.observableArrayList(userDtos);
        observableListUsers.sort(Comparator.comparingLong(UserDto::getUsrId));
        filteredUsers = new FilteredList<>(this.observableListUsers, u -> true);
        listViewUserReport1.setItems(filteredUsers);
        this.txtFilterUser.clear();

        //Second report initialize
        this.areaDtos = (List<AreaDto>) areaService.getAreas().getResult("Areas");
        observableListAreas = FXCollections.observableArrayList(areaDtos);
        observableListAreas.sort(Comparator.comparingLong(AreaDto::getAreId));
        filteredAreas = new FilteredList<>(this.observableListAreas, a -> true);
        listViewAreas.setItems(filteredAreas);
        this.txtFilterAreas.clear();
        
        //Third report initialize
        this.userDtos1 = (List<UserDto>) userService.getUsers().getResult("Users");
        observableListUsers1 = FXCollections.observableArrayList(userDtos1);
        observableListUsers1.sort(Comparator.comparingLong(UserDto::getUsrId));
        filteredUsers1 = new FilteredList<>(this.observableListUsers1, u -> true);
        listViewUserReport3.setItems(filteredUsers1);
        this.txtFilterUser2.clear();
    }

    private void changeTypeOfTime(boolean SpecificDate) {
        this.rdbSpecificDate.setSelected(SpecificDate);
        this.vBoxSpecificDate.setVisible(SpecificDate);

        this.rdbRangeDates.setSelected(!SpecificDate);
        this.vBoxRangeDates.setVisible(!SpecificDate);

        this.dprFromDate.setValue(null);
        this.dprSpecificDate.setValue(null);
        this.dprUntilDate.setValue(null);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //First Report
        listViewUserReport1.setCellFactory(param -> new ListCell<UserDto>() {
            @Override
            protected void updateItem(UserDto item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getUsrId() + "     " + item.getUsrName() + "     " + item.getUsrLastname() + "     " + item.getUsrSurname());
                }
            }
        });
        txtFilterUser.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredUsers.setPredicate(user -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return user.getUsrName().toLowerCase().contains(lowerCaseFilter) || user.getUsrLastname().toLowerCase().contains(lowerCaseFilter)
                        || user.getUsrSurname().toLowerCase().contains(newValue);
            });
        });
        //Second Report
        listViewAreas.setCellFactory(param -> new ListCell<AreaDto>() {
            @Override
            protected void updateItem(AreaDto item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getAreId() + "     " + item.getAreName() + "     " + item.getAreState());
                }
            }
        });
        txtFilterAreas.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredAreas.setPredicate(area -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return area.getAreName().toLowerCase().contains(newValue);
            });
        });
        //Third Report
        listViewUserReport3.setCellFactory(param -> new ListCell<UserDto>() {
            @Override
            protected void updateItem(UserDto item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getUsrId() + "     " + item.getUsrName() + "     " + item.getUsrLastname() + "     " + item.getUsrSurname());
                }
            }
        });
        txtFilterUser2.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredUsers1.setPredicate(user -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return user.getUsrName().toLowerCase().contains(lowerCaseFilter) || user.getUsrLastname().toLowerCase().contains(lowerCaseFilter)
                        || user.getUsrSurname().toLowerCase().contains(newValue);
            });
        });
    }

    @FXML
    void rangeDateSelected(ActionEvent event) {
        changeTypeOfTime(false);
    }

    @FXML
    void specificDateSelected(ActionEvent event) {
        changeTypeOfTime(true);
    }

    @FXML
    void onActionGenerateReport1(ActionEvent event) {
        if (!this.chkAttended.isSelected() && !this.chkRequested.isSelected()) {
            new Message().showModal(Alert.AlertType.ERROR, bundle.getString("includeManagements"), getStage(), bundle.getString("includeATypeOfManagement"));
        } else if (rdbSpecificDate.isSelected() && dprSpecificDate.getValue() == null) {
            new Message().showModal(Alert.AlertType.ERROR, bundle.getString("date"), getStage(), bundle.getString("selectADate"));
        } else if (rdbRangeDates.isSelected() && (dprFromDate.getValue() == null || dprUntilDate.getValue() == null)) {
            new Message().showModal(Alert.AlertType.ERROR, bundle.getString("dates"), getStage(), bundle.getString("selectDates"));
        } else if (rdbRangeDates.isSelected() && (dprFromDate.getValue().isAfter(dprUntilDate.getValue()) || dprFromDate.getValue().isEqual(dprUntilDate.getValue()))) {
            new Message().showModal(Alert.AlertType.ERROR, bundle.getString("dates"), getStage(), bundle.getString("checkDates"));
        } else if (listViewUserReport1.getSelectionModel().isEmpty()) {
            new Message().showModal(Alert.AlertType.ERROR, bundle.getString("user"), getStage(), bundle.getString("selectAnUser"));
        } else {
            generateAndSaveReport1(this.listViewUserReport1.getScene().getWindow());
        }
    }

    public void generateAndSaveReport1(Window window) {
        try {
            List<ManagementDto> attendeds = new ArrayList<>();
            List<ManagementDto> requesteds = new ArrayList<>();
            List<ManagementDto> managementDtos = (List<ManagementDto>) managementService.getManagements().getResult("Managements");
            UserDto userDto = (UserDto) AppContext.getInstance().get("User");
            String reportInformation = "";
            String reportInformationDates = "";
            if (this.chkAttended.isSelected()) {
                reportInformation += "Gestiones Atendidas ";
                attendeds = managementDtos.stream().filter(isAproved.and(m -> m.getUsrAssignedId().getUsrId().equals(userDto.getUsrId()))).toList();
            }
            if (this.chkRequested.isSelected()) {
                if (this.chkAttended.isSelected()) {
                    reportInformation += "y Solicitadas ";
                } else {
                    reportInformation += "Gestiones Solicitadas ";
                }
                requesteds = managementDtos.stream().filter(m -> m.getUsrRequestingId().getUsrId().equals(userDto.getUsrId())).toList();
            }
            reportInformation += "por el usuario ";
            if (this.rdbSpecificDate.isSelected()) {
                reportInformation += "en la fecha";
                reportInformationDates = this.dprSpecificDate.getValue().toString();
                attendeds = attendeds.stream().filter(m -> m.getMgtSolvedate().toLocalDate().isEqual(dprSpecificDate.getValue())).toList();
                requesteds = requesteds.stream().filter(m -> m.getMgtSolvedate().toLocalDate().isEqual(dprSpecificDate.getValue())).toList();
            } else {
                attendeds = attendeds.stream().filter(requestedInRange(dprFromDate.getValue(), dprUntilDate.getValue())).toList();
                requesteds = requesteds.stream().filter(requestedInRange(dprFromDate.getValue(), dprUntilDate.getValue())).toList();
                reportInformation += "entre las fechas";
                reportInformationDates = this.dprFromDate.getValue().toString() + " y " + this.dprUntilDate.getValue().toString();
            }
            managementDtos = managementDtos.stream().filter(isManagementIn(attendeds).or(isManagementIn(requesteds))).toList();
            managementDtos.stream().filter(isManagementIn(requesteds)).forEach(m -> m.setMgtType("Solicitada"));
            managementDtos.stream().filter(isManagementIn(attendeds)).forEach(m -> m.setMgtType("Atendida"));
            InputStream appLogo = this.getClass().getResourceAsStream("/cr/ac/una/sigeceuna/resources/SigeceUNA.png");

            File file = new File(getClass().getResource("/cr/ac/una/sigeceuna/reports/ManagementsByUser.jasper").getFile());
            if (file.exists()) {
                InputStream is = new BufferedInputStream(new FileInputStream(file.getAbsoluteFile()));
                JasperReport jr = (JasperReport) JRLoader.loadObject(is);

                JRBeanArrayDataSource dsManagements = new JRBeanArrayDataSource(managementDtos.toArray());

                Map<String, Object> parameters = new HashMap<>();
                parameters.put("dsManagements", dsManagements);
                parameters.put("userFullName", userDto.getUsrName() + " " + userDto.getUsrLastname() + " " + userDto.getUsrSurname());
                parameters.put("usrIdentification", userDto.getUsrIdentification());
                parameters.put("reportInformation", reportInformation);
                parameters.put("reportInformationDates", reportInformationDates);
                parameters.put("logoImage", appLogo);

                // Generar el reporte
                JasperPrint jp = JasperFillManager.fillReport(jr, parameters, new JREmptyDataSource());

                // Desplegar ventana para elegir ubicación de guardado usando FileChooser
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle(bundle.getString("saveReport"));
                fileChooser.setInitialFileName(bundle.getString("reportOf") + " " + ".pdf");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf"));
                File fileToSave = fileChooser.showSaveDialog(window);

                if (fileToSave != null) {
                    if (!fileToSave.getAbsolutePath().endsWith(".pdf")) {
                        fileToSave = new File(fileToSave.getAbsolutePath() + ".pdf");
                    }

                    try (FileOutputStream outputStream = new FileOutputStream(fileToSave)) {
                        JasperExportManager.exportReportToPdfStream(jp, outputStream);
                    }
                    new Message().showModal(Alert.AlertType.CONFIRMATION, bundle.getString("reportSaved"), getStage(), bundle.getString("reportSavedSuccess"));
                }
            } else {
                JOptionPane.showMessageDialog(null, bundle.getString("filesNotFound"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, bundle.getString("saveErrorReport") + " " + e.getMessage());
            e.printStackTrace();
        }
    }

    //Methods Second Report
    private void changeManagementsReportPerformance(boolean allManagements) {
        this.rdbAllManagements.setSelected(allManagements);

        this.txtFilterAreas.clear();
        this.rdbManagementsPerArea.setSelected(!allManagements);
        this.vBoxAreas.setVisible(!allManagements);
    }

    @FXML
    void onActionrdbAllManagements(ActionEvent event) {
        this.changeManagementsReportPerformance(true);
    }

    @FXML
    void onActionrdbManagementsPerArea(ActionEvent event) {
        this.changeManagementsReportPerformance(false);
    }

    @FXML
    void onActionGenerateReport2(ActionEvent event) {
        if (rdbManagementsPerArea.isSelected() && listViewAreas.getSelectionModel().isEmpty()) {
            new Message().showModal(Alert.AlertType.ERROR, bundle.getString("area"), getStage(), bundle.getString("selectAnArea"));
        } else {
            generateAndSaveReport2(btnGenerateReport2.getScene().getWindow());
        }
    }

    private void generateAndSaveReport2(Window window) {
        List<ManagementDto> managementDtos = new ArrayList<>();
        String performanceDescription;
        Long pendingManagements;
        Long attendedManagements;
        Long completedsOnTime;
        Long noCompletedsOnTime;
        Long totalManagements;
        if (rdbManagementsPerArea.isSelected()) {
            performanceDescription = " Gestiones pertenecientes al area " + listViewAreas.getSelectionModel().getSelectedItem().getAreName();
            //managementDtos =
                List<ManagementDto> list = ((List<ManagementDto>) this.managementService.getManagements()
                    .getResult("Managements")).stream().filter(m -> m.getAreId()
                    .equals(listViewAreas.getSelectionModel().getSelectedItem().getAreId())).toList();
                managementDtos.addAll(list);
        } else {
            performanceDescription = " Gestiones pertenecientes a todas las areas";
            managementDtos = (List<ManagementDto>) this.managementService.getManagements().getResult("Managements");
        }
        managementDtos.sort(Comparator.comparingLong(ManagementDto::getMgtId));
        
        pendingManagements = managementDtos.stream().filter(m -> (m.getMgtState().equals("In progress")) || (m.getMgtState().equals("In approval"))).count();
        attendedManagements = managementDtos.stream().filter(m -> (m.getMgtState().equals("Resolved"))).count();
        completedsOnTime = managementDtos.stream().filter(m -> (m.getMgtState().equals("Resolved")) && (!m.getMgtSolvedate().isAfter(m.getMgtMaxdatetosolve()))).count();
        noCompletedsOnTime = managementDtos.stream().filter(m -> ((m.getMgtState().equals("In progress")) || (m.getMgtState().equals("In approval"))) && (m.getMgtMaxdatetosolve().isBefore(LocalDateTime.now()))).count();
        totalManagements = managementDtos.stream().count();

        InputStream appLogo = this.getClass().getResourceAsStream("/cr/ac/una/sigeceuna/resources/SigeceUNA.png");
        File file = new File(getClass().getResource("/cr/ac/una/sigeceuna/reports/ManagementsPerformance.jasper").getFile());
        try {
            if (file.exists()) {
                InputStream is = new BufferedInputStream(new FileInputStream(file.getAbsoluteFile()));
                JasperReport jr = (JasperReport) JRLoader.loadObject(is);

                JRBeanArrayDataSource dsManagements = new JRBeanArrayDataSource(managementDtos.toArray());

                Map<String, Object> parameters = new HashMap<>();
                parameters.put("dsManagements", dsManagements);
                parameters.put("pendingManagements", pendingManagements);
                parameters.put("attendedManagements", attendedManagements);
                parameters.put("completedsOnTime", completedsOnTime);
                parameters.put("noCompletedsOnTime", noCompletedsOnTime);
                parameters.put("totalManagements", totalManagements);

                parameters.put("performanceDescription", performanceDescription);

                parameters.put("logoImage", appLogo);

                // Generar el reporte
                JasperPrint jp = JasperFillManager.fillReport(jr, parameters, new JREmptyDataSource());

                // Desplegar ventana para elegir ubicación de guardado usando FileChooser
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle(bundle.getString("saveReport"));
                fileChooser.setInitialFileName(bundle.getString("performanceReport") + ".pdf");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf"));
                File fileToSave = fileChooser.showSaveDialog(window);

                if (fileToSave != null) {
                    if (!fileToSave.getAbsolutePath().endsWith(".pdf")) {
                        fileToSave = new File(fileToSave.getAbsolutePath() + ".pdf");
                    }

                    try (FileOutputStream outputStream = new FileOutputStream(fileToSave)) {
                        JasperExportManager.exportReportToPdfStream(jp, outputStream);
                    }
                    new Message().showModal(Alert.AlertType.CONFIRMATION, bundle.getString("reportSaved"), getStage(), bundle.getString("reportSavedSuccess"));
                }
            } else {
                new Message().showModal(Alert.AlertType.ERROR, bundle.getString("generateReport"), getStage(), bundle.getString("filesNotFound"));
            }
        } catch (Exception e) {
            new Message().showModal(Alert.AlertType.ERROR, bundle.getString("generateReport"), getStage(), bundle.getString("saveErrorReport") + " " + e.getMessage());
            e.printStackTrace();
        }
    }

    //Method Third Report
    @FXML
    void onActionGenerateReport3(ActionEvent event) {
        if(this.listViewUserReport3.getSelectionModel().getSelectedItem()==null){
            new Message().showModal(Alert.AlertType.ERROR, bundle.getString("user"), getStage(), bundle.getString("selectAnUser"));
        }else if (!this.chkAprobationsApproveds.isSelected() && !this.chkAprobationsPendings.isSelected() && !this.chkAprobationsRejecteds.isSelected()) {
            new Message().showModal(Alert.AlertType.ERROR, bundle.getString("aprobationsType"), getStage(), bundle.getString("atLeastOneAprobation"));
        }else {
            generateAndSaveReport3(btnGenerateReport3.getScene().getWindow());
        }
    }

    private void generateAndSaveReport3(Window window) {
        List<ManagementaprobationDto> managementaprobationDtos = (List<ManagementaprobationDto>) managementAprobationService.getManagementaprobations().getResult("Managementaprobations");
        String aprobationsPerUserDescription = "Aprobaciones ";
        UserDto userDto = this.listViewUserReport3.getSelectionModel().getSelectedItem();
        managementaprobationDtos = managementaprobationDtos.stream().filter(ma->(ma.getUsrToaproveId().getUsrId().equals(userDto.getUsrId()))).toList();
        Long approvedAprobations = managementaprobationDtos.stream().filter(ma->(ma.getMgtaState().equals("Approved"))).count();
        Long rejectedAprobations = managementaprobationDtos.stream().filter(ma->(ma.getMgtaState().equals("Approved"))).count();
        Long pendingAprobations = managementaprobationDtos.stream().filter(ma->(ma.getMgtaState().equals("Pending"))).count();
        Long totalAprobations = managementaprobationDtos.stream().count();
        if(!chkAprobationsApproveds.isSelected()){
            managementaprobationDtos = managementaprobationDtos.stream().filter(ma->(!ma.getMgtaState().equals("Approved"))).toList();
        }else{
            aprobationsPerUserDescription +=" Aprobadas,";
        }
        if(!chkAprobationsRejecteds.isSelected()){
            managementaprobationDtos = managementaprobationDtos.stream().filter(ma->(!ma.getMgtaState().equals("Rejected"))).toList();
        }else{
            aprobationsPerUserDescription +=" Rechazadas,";
        }
        if(!chkAprobationsPendings.isSelected()){
            managementaprobationDtos = managementaprobationDtos.stream().filter(ma->(!ma.getMgtaState().equals("Pending"))).toList();
        }else{
            aprobationsPerUserDescription +=" Pendientes.";
        }
        int index = aprobationsPerUserDescription.lastIndexOf(",");
        if(index == aprobationsPerUserDescription.length()-1 && index != -1){
            aprobationsPerUserDescription = aprobationsPerUserDescription.subSequence(0, index)+".";
        }
        index = aprobationsPerUserDescription.lastIndexOf(",");
        if( index != -1){
            aprobationsPerUserDescription = aprobationsPerUserDescription.subSequence(0, index)+" y"+aprobationsPerUserDescription.subSequence(index+1, aprobationsPerUserDescription.length());
        }
        
        InputStream appLogo = this.getClass().getResourceAsStream("/cr/ac/una/sigeceuna/resources/SigeceUNA.png");
        File file = new File(getClass().getResource("/cr/ac/una/sigeceuna/reports/AprobationsByUser.jasper").getFile());
        try {
            if (file.exists()) {
                InputStream is = new BufferedInputStream(new FileInputStream(file.getAbsoluteFile()));
                JasperReport jr = (JasperReport) JRLoader.loadObject(is);

                JRBeanArrayDataSource dsAprobations = new JRBeanArrayDataSource(managementaprobationDtos.toArray());

                Map<String, Object> parameters = new HashMap<>();
                parameters.put("dsAprobations", dsAprobations);
                parameters.put("userCompleteName", userDto.getUsrName()+" "+userDto.getUsrLastname()+" "+userDto.getUsrSurname());
                parameters.put("aprobationsPerUserDescription", aprobationsPerUserDescription);
                parameters.put("pendingAprobations", " Pendientes :   " + pendingAprobations);
                parameters.put("approvedAprobations", " Aprobadas :   " + approvedAprobations);
                parameters.put("rejectedAprobations", "Rechazadas :   " + rejectedAprobations);
                parameters.put("totalAprobations", "Totales :   " + totalAprobations);

                parameters.put("logoImage", appLogo);

                // Generar el reporte
                JasperPrint jp = JasperFillManager.fillReport(jr, parameters, new JREmptyDataSource());

                // Desplegar ventana para elegir ubicación de guardado usando FileChooser
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle(bundle.getString("saveReport"));
                fileChooser.setInitialFileName(bundle.getString("aprobationsOf") +userDto.getUsrName()+ ".pdf");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf"));
                File fileToSave = fileChooser.showSaveDialog(window);

                if (fileToSave != null) {
                    if (!fileToSave.getAbsolutePath().endsWith(".pdf")) {
                        fileToSave = new File(fileToSave.getAbsolutePath() + ".pdf");
                    }

                    try (FileOutputStream outputStream = new FileOutputStream(fileToSave)) {
                        JasperExportManager.exportReportToPdfStream(jp, outputStream);
                    }
                    new Message().showModal(Alert.AlertType.INFORMATION, bundle.getString("reportSaved"), getStage(), bundle.getString("reportSavedSuccess"));
                }
            } else {
                new Message().showModal(Alert.AlertType.ERROR, bundle.getString("generateReport"), getStage(), bundle.getString("filesNotFound"));
            }
        } catch (Exception e) {
            new Message().showModal(Alert.AlertType.ERROR, bundle.getString("generateReport"), getStage(), bundle.getString("saveErrorReport") + " " + e.getMessage());
            e.printStackTrace();
        }
    }
}
