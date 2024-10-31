
package cr.ac.una.sigeceuna.controller;

import cr.ac.una.sigeceuna.model.FileDto;
import cr.ac.una.sigeceuna.model.ManagementDto;
import cr.ac.una.sigeceuna.model.ManagementaprobationDto;
import cr.ac.una.sigeceuna.model.TracingDto;
import cr.ac.una.sigeceuna.model.UserDto;
import cr.ac.una.sigeceuna.service.FileService;
import cr.ac.una.sigeceuna.service.TracingService;
import cr.ac.una.sigeceuna.util.AppContext;
import cr.ac.una.sigeceuna.util.FlowController;
import cr.ac.una.sigeceuna.util.Message;
import cr.ac.una.sigeceuna.util.Response;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
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


public class TracingsController extends Controller implements Initializable{
    @FXML
    private TableColumn<TracingDto, LocalDateTime> tblC_CreationDate;

    @FXML
    private TableColumn<TracingDto, String> tblC_ID;

    @FXML
    private TableColumn<TracingDto, String> tblC_Type;
    
    @FXML
    private TableColumn<TracingDto, String> tblC_SolutionType;

    @FXML
    private TableView<TracingDto> tblV_Tracings;

    @FXML
    private TextArea txtA_Details;

    @FXML
    private TextField txt_TypeFilter;
    
    @FXML
    private ListView<FileDto> listV_File;

    @FXML
    private ChoiceBox<String> chb_SolutionType;

    private boolean fileLoad=false;
    
    private TracingDto tracingDto;
    private UserDto loggedUser;
    private TracingDto tracingSelected;
    
    //Services
    private TracingService tracingService=new TracingService();
    private FileService fileService = new FileService();
    
    private List<TracingDto>tracingsList=new ArrayList<>();
    
    //observableList for the table view
    private ObservableList<TracingDto>observableTracingsDto=FXCollections.observableArrayList();
    private ObservableList<FileDto> observableListFiles = FXCollections.observableArrayList();
    //dtos
    ManagementDto managementDtoSelected;
    
    List<Node> requiredSpaces = new ArrayList<>();
    private ResourceBundle bundle;
    @Override
    public void initialize() {
        this.bundle=FlowController.getIdioma();
        indicateRequiredSpaces();
        this.tracingSelected=new TracingDto();
        this.loggedUser=(UserDto) AppContext.getInstance().get("User");
        clearSpaces();
        this.managementDtoSelected=(ManagementDto) AppContext.getInstance().get("ManagementSelected");
        this.tracingDto=new TracingDto();
        this.observableListFiles.clear();
        this.listV_File.setItems(observableListFiles);
        updateTracingsList();
        
        this.chb_SolutionType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setSolutionType(newValue);
            }
        });
        
        //Table selections
         this.tblV_Tracings.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue != null) {
                    this.tracingSelected = newValue;
                    loadTracing();
                }
            }
        );
        
        tblC_SolutionType.setCellValueFactory(new PropertyValueFactory<>("tcgSolutiontype"));
        tblC_SolutionType.setCellFactory(column -> new TableCell<TracingDto, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    switch (item.toLowerCase()) {
                        case "denied":
                            setText(bundle.getString("denied"));
                            break;
                        case "solved":
                            setText(bundle.getString("resolved"));
                            break;
                        default:
                            setText(item);
                            break;
                    }
                }
            }
        });
        
        tblC_Type.setCellValueFactory(new PropertyValueFactory<>("tcgType"));
        tblC_Type.setCellFactory(column -> new TableCell<TracingDto, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    switch (item.toLowerCase()) {
                        case "text":
                            setText(bundle.getString("text"));
                            break;
                        case "file":
                            setText(bundle.getString("file"));
                            break;
                        default:
                            setText(bundle.getString("unknown"));
                            break;
                    }
                }
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.bundle=FlowController.getIdioma();
        //choicebox solutionType inicialization
        ObservableList<String> optionsSolutionType = FXCollections.observableArrayList(
            bundle.getString("denied"), 
            bundle.getString("resolved")
        );
        this.chb_SolutionType.setItems(optionsSolutionType);
        chb_SolutionType.setAccessibleText("Tipo de solucion");
        
        //TableView Areas inicialization
        tblC_ID.setCellValueFactory(new PropertyValueFactory<>("tcgId"));
        tblC_CreationDate.setCellValueFactory(new PropertyValueFactory<>("tcgCreationdate"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        tblC_CreationDate.setCellFactory(column -> new TableCell<TracingDto, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.format(formatter));
                }
            }
        });
        this.tracingDto=new TracingDto();
        newTracing();
        
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
    
    private void clearSpaces(){
        this.tracingDto = new TracingDto();
        this.txtA_Details.deselect();
        this.txt_TypeFilter.deselect();
        this.fileLoad=false;
        this.observableListFiles.clear();
        this.txtA_Details.clear();
        this.txt_TypeFilter.clear();
        this.chb_SolutionType.getSelectionModel().clearSelection();
    }
    
     @FXML
    void onActionBtnReturn(ActionEvent event) {
        FlowController.getInstance().goView("ManagementView");
    }
    
    @FXML
    void onActionBtnDelete(ActionEvent event) {
        if(this.tracingSelected.getTcgId()==null){
           new Message().showModal(Alert.AlertType.ERROR, bundle.getString("noTracingSelected"), getStage(),bundle.getString("selectATracing"));
           return;
        }
        boolean userConfirmed = new Message().showConfirmation(bundle.getString("deleteTracing"), getStage(), bundle.getString("sureToDeleteTracing"));
        if(userConfirmed){
            try {
                Response response = this.tracingService.deleteTracing(this.tracingSelected.getTcgId());
                if (!response.getState()) {
                    new Message().showModal(Alert.AlertType.ERROR, bundle.getString("deleteTracing"), getStage(), response.getMessage());
                } else {
                    this.tblV_Tracings.getSelectionModel().clearSelection();
                    this.tracingSelected=new TracingDto();
                    clearSpaces();
                    updateTracingsList();
                    new Message().showModal(Alert.AlertType.INFORMATION, bundle.getString("deleteTracing"), getStage(), bundle.getString("deleteSuccessTracing"));
                }         
            } catch (Exception ex) {
                Logger.getLogger(TracingsController.class.getName()).log(Level.SEVERE, bundle.getString("deleteErrorTracing"), ex);
                new Message().showModal(Alert.AlertType.ERROR, bundle.getString("deleteTracing"), getStage(), bundle.getString("deleteErrorTracing"));
            }
        }
    }

    @FXML
    void onActionBtnFilter(ActionEvent event) {
        String txtFilter=this.txt_TypeFilter.getText().toLowerCase();
        this.observableTracingsDto.clear();
        if(!this.txt_TypeFilter.getText().isEmpty() && !this.txt_TypeFilter.getText().isBlank()){           
            this.observableTracingsDto.addAll(this.tracingsList.stream().filter(tcg -> {
                String solutionType = tcg.getTcgSolutiontype().toLowerCase();
                return (txtFilter.contains("denegado") && solutionType.equals("denied")) || 
                       (txtFilter.contains("resuelto") && solutionType.equals("solved")) ||
                       (txtFilter.contains("denied")) || (txtFilter.contains("resolved"));
            }).toList());
        }
        else{
            this.observableTracingsDto.addAll(tracingsList);
        }
        this.tblV_Tracings.setItems(observableTracingsDto);
    }
    
    @FXML
    void handleFileClicked(MouseEvent event) {
        FileDto fileDto = this.listV_File.getSelectionModel().getSelectedItem();
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
        Stage stage = (Stage) this.listV_File.getScene().getWindow();
        
        // Mostrar la ventana para elegir dónde guardar el archivo
        File fileToSave = fileChooser.showSaveDialog(stage);

        if (fileToSave != null) {
            try {
                writeBinaryDataToFile(fileToSave, fileDto.getFleContent());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeBinaryDataToFile(File file, Byte[] binaryData) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            // Convertir Byte[] a byte[] para escribirlo correctamente en el archivo
            byte[] byteArray = new byte[binaryData.length];
            for (int i = 0; i < binaryData.length; i++) {
                byteArray[i] = binaryData[i];
            }

            // Escribir los datos binarios directamente en el archivo
            fileOutputStream.write(byteArray);
        }
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
        byte[] fileBytes = Files.readAllBytes(file.toPath());
        Byte[] binaryData = new Byte[fileBytes.length];

        for (int i = 0; i < fileBytes.length; i++) {
            binaryData[i] = fileBytes[i];
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
    void onActionBtnClean(ActionEvent event) {
        clearSpaces();
        this.tracingSelected = new TracingDto();
        this.tblV_Tracings.getSelectionModel().clearSelection();
    }
    
    @FXML
    void onActionBtnSave(ActionEvent event) {
        if(inProgressorApproval()){
            String invalid = validateRequiredSpaces();
            if (!invalid.isEmpty()) {
                new Message().showModal(Alert.AlertType.ERROR, bundle.getString("saveTracing"), getStage(), invalid);
            }
            else{           
                try {    
                    this.tracingDto.setMgtId(this.managementDtoSelected.getMgtId());
                    if(this.tracingDto.getTcgId()==null){
                        this.tracingDto.setTcgCreationdate(LocalDateTime.now());
                    }
                    this.tracingDto.setUsrId(this.loggedUser);
                    if(this.fileLoad){
                        this.tracingDto.setTcgType("file");
                        createFilesDto();                   
                    }
                    Response response = this.tracingService.saveTracing(tracingDto);
                    if (!response.getState()) {
                        new Message().showModal(Alert.AlertType.ERROR, bundle.getString("saveTracing"), getStage(), response.getMessage());
                    } else {
                        new Message().showModal(Alert.AlertType.CONFIRMATION, bundle.getString("saveTracing"), getStage(), bundle.getString("saveSuccessTracing"));
                        clearSpaces();
                        updateTracingsList();
                        unbindTracing();
                        bindTracing(false);
                    }
                    } catch (Exception ex) {
                        Logger.getLogger(TracingsController.class.getName()).log(Level.SEVERE, bundle.getString("saveErrorTracing"), ex);
                        new Message().showModal(Alert.AlertType.ERROR, bundle.getString("saveTracing"), getStage(), bundle.getString("saveErrorTracing"));
                    }
                   newTracing();
            }
        }
        else{new Message().showModal(Alert.AlertType.WARNING, bundle.getString("managemetStateNotValid"), getStage(),bundle.getString("managementState"));
}
    }
    
    private boolean inProgressorApproval(){
        if(this.managementDtoSelected.getMgtState().equals("In approval") || this.managementDtoSelected.getMgtState().equals("In progress")){
            return true;
        }
        else{
            return false;
        }
    }

    private void newTracing() {
        unbindTracing();
        this.tracingDto = new TracingDto();
        bindTracing(true);
    }

    private void unbindTracing() {
        this.txtA_Details.textProperty().unbindBidirectional(this.tracingDto.tcgSolutiondetail);
    }

    private void bindTracing(boolean b) {
        this.txtA_Details.textProperty().bindBidirectional(this.tracingDto.tcgSolutiondetail);
    }
    
    private void updateTracingsList() {
        this.tracingsList=new ArrayList<>();
        this.observableTracingsDto.clear();
        System.out.println(loggedUser.getUsrId());
        this.tracingsList=((List<TracingDto>) this.tracingService.getTracings().getResult("Tracings"))
                .stream().filter(tcg->tcg.getMgtId().equals(this.managementDtoSelected.getMgtId())).toList();       
        this.observableTracingsDto.addAll(tracingsList);
        this.tblV_Tracings.setItems(observableTracingsDto);
    }

    private void setSolutionType(String solutionType) {
        switch(solutionType){
        case "Denegado":
        case "Denied":
            this.tracingDto.setTcgSolutiontype("denied");break;
        case "Resuelta":
        case "Resolved":
            this.tracingDto.setTcgSolutiontype("solved");break;
        default:break;
        }
    }
    
    private void indicateRequiredSpaces() {
        this.requiredSpaces.clear();
        this.requiredSpaces.addAll(Arrays.asList(this.chb_SolutionType));
    }
    
    public String validateRequiredSpaces() {
        boolean valid = true;
        StringBuilder invalid = new StringBuilder();

        for (Node node : requiredSpaces) {
            if (node instanceof ChoiceBox) {
                ChoiceBox<?> choiceBox = (ChoiceBox<?>) node;
                if (choiceBox.getSelectionModel().getSelectedIndex() < 0) {
                    valid = false;
                    invalid.append(choiceBox.getAccessibleText()).append(", ");
                }
            }
        }
        if (valid) {
            return "";
        } else {
            return bundle.getString("emptySpaces")+" [" + invalid.substring(0, invalid.length() - 2) + "].";
        }
    }

    private void createFilesDto() {
        try {
            for (FileDto fileDto : this.observableListFiles) {
                if(fileDto.getFleId()==null){
                    Response response = this.fileService.saveFile(fileDto);
                    FileDto savedFile = (FileDto) response.getResult("File");

                    if (!response.getState()) {
                        new Message().showModal(Alert.AlertType.ERROR, bundle.getString("saveFile"), getStage(), response.getMessage());
                        this.fileLoad=false;
                    } else {
                        this.tracingDto.getFileCollection().add(savedFile);
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(TracingsController.class.getName()).log(Level.SEVERE, bundle.getString("saveErrorFile"), ex);
            new Message().showModal(Alert.AlertType.ERROR, bundle.getString("saveFile"), getStage(), bundle.getString("saveErrorFile"));
        }
    }

    private void loadTracing() {
        this.observableListFiles.clear();
        this.tracingDto=this.tracingSelected;
        this.txtA_Details.setText(this.tracingDto.getTcgSolutiondetail());
        if(this.tracingDto.getTcgSolutiontype().equals("denied")){
            this.chb_SolutionType.setValue(bundle.getString("denied"));
        }
        else{this.chb_SolutionType.setValue(bundle.getString("resolved"));}
        if(!this.tracingDto.getFileCollection().isEmpty() && this.tracingDto.getFileCollection()!=null){
            this.observableListFiles.addAll(this.tracingDto.getFileCollection());
        }
        bindTracing(false);
    }
}
