package cr.ac.una.sigeceunaemail.controller;

import cr.ac.una.sigeceunaemail.model.ConstrainDto;
import cr.ac.una.sigeceunaemail.model.EmailsDto;
import cr.ac.una.sigeceunaemail.model.FileDto;
import cr.ac.una.sigeceunaemail.model.NotificationProcessDto;
import cr.ac.una.sigeceunaemail.model.VariableDto;
import cr.ac.una.sigeceunaemail.service.ConstrainService;
import cr.ac.una.sigeceunaemail.service.EmailsService;
import cr.ac.una.sigeceunaemail.service.FileService;
import cr.ac.una.sigeceunaemail.service.NotificationProcessService;
import cr.ac.una.sigeceunaemail.service.VariableService;
import cr.ac.una.sigeceunaemail.util.Message;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

// Imports for apache poi
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class MailController extends Controller implements Initializable {

    // FXML Variables
    @FXML
    private TextField txtNPname_Search;
    @FXML
    private TextField txtEmail_Search;
    @FXML
    private ListView<NotificationProcessDto> listViewNotificationProcess;
    @FXML
    private ListView<EmailsDto> listViewEmails;
    @FXML
    private ListView<FileDto> listViewFiles;
    
    private NotificationProcessService notificationProcessService = new NotificationProcessService();
    private ObservableList<NotificationProcessDto> ObservableListNotificationProcess;
    private ObservableList<EmailsDto> observableListEmails = FXCollections.observableArrayList();;
    private FilteredList<NotificationProcessDto> filteredNotificationProcess;
    private FilteredList<EmailsDto> filteredEmails;
    private VariableService variableService = new VariableService();
    private EmailsService emailsService = new EmailsService();
    private ConstrainService constrainService = new ConstrainService();
    private ObservableList<FileDto> observableListFiles = FXCollections.observableArrayList();
    
    private FileService fileService = new FileService();
    //Cada que se carga el fxml
    @Override
    public void initialize() {
        this.observableListFiles.clear();
        List<NotificationProcessDto> ListNoticationProcess=(List<NotificationProcessDto>) notificationProcessService.getNotificationProcesses().getResult("NotificationProcesses");
        ObservableListNotificationProcess=FXCollections.observableArrayList(ListNoticationProcess);
        this.filteredNotificationProcess=new FilteredList<>(this.ObservableListNotificationProcess, p -> true);
        this.listViewNotificationProcess.setItems(filteredNotificationProcess);
        observableListEmails.clear();
    }
    //Solo la primera vez que se carga el fxml
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<NotificationProcessDto> listNotificationProcess = (List<NotificationProcessDto>) notificationProcessService.getNotificationProcesses().getResult("NotificationProcesses");
        ObservableListNotificationProcess = FXCollections.observableArrayList(listNotificationProcess);
        filteredNotificationProcess = new FilteredList<>(ObservableListNotificationProcess, p -> true);
        listViewNotificationProcess.setItems(filteredNotificationProcess);
        listViewFiles.setItems(observableListFiles);
        // Initialize ListView for Notification Process
        listViewNotificationProcess.setCellFactory(param -> new ListCell<NotificationProcessDto>() {
            @Override
            protected void updateItem(NotificationProcessDto item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNtpId() + " " + item.getNtpDescription());
                }
            }
        });
        listViewFiles.setCellFactory(param -> new ListCell<FileDto>() {
            private Button deleteButton = new Button("x");
            private final HBox hbox = new HBox();
            private final VBox vbox = new VBox(); // Añadimos un VBox para tener el texto y botón separados.
            private final Label fileInfoLabel = new Label();
            private final Label downloadLabel = new Label("click to download");

            {
                deleteButton.setMaxWidth(15);
                deleteButton.setOnAction(e -> {
                    FileDto item = getItem();
                    if (item != null) {
                        for (int i = 0; i < observableListFiles.size(); i++) {
                            if (observableListFiles.get(i).getFleId() == item.getFleId()) {
                                observableListFiles.remove(i);
                            }
                        }
                    }
                });

                downloadLabel.setStyle("-fx-font-size: 10; -fx-text-fill: grey;"); // Estilo del texto
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

        // Filter for Notification Processes
        txtNPname_Search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredNotificationProcess.setPredicate(notificationProcess -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return notificationProcess.getNtpDescription().toLowerCase().contains(lowerCaseFilter);
            });
        });

        // Initialize ListView for Emails with delete button
        listViewEmails.setCellFactory(param -> new ListCell<EmailsDto>() {
            private final Button deleteButton = new Button("Delete");
            private final HBox hbox = new HBox();

            {
                hbox.getChildren().add(deleteButton);
                deleteButton.setOnAction(e -> {
                    EmailsDto item = getItem();
                    if (item != null) {
                        for (int i = 0; i < observableListEmails.size(); i++) {
                            if(observableListEmails.get(i).getEmlEmailaddress()==item.getEmlEmailaddress()&&observableListEmails.get(i).getEmlHtml()==item.getEmlHtml()){
                                observableListEmails.remove(i);
                            }
                        }
                    }
                });
            }

            @Override
            protected void updateItem(EmailsDto item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item.getEmlEmailaddress());
                    setGraphic(hbox);
                }
            }
        });

        // Filter for Emails
        txtEmail_Search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredEmails.setPredicate(email -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return email.getEmlEmailaddress().toLowerCase().contains(lowerCaseFilter);
            });
        });
    }

    @FXML
    void generateExcel(ActionEvent event) {
        if (listViewNotificationProcess.getSelectionModel().getSelectedItem() != null) {
            Long notificationProcessID = listViewNotificationProcess.getSelectionModel().getSelectedItem().getNtpId();
            List<VariableDto> variables = (List<VariableDto>) variableService.getVariablesByNTP(notificationProcessID).getResult("Variables");

            // Excel creation
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Notification Processes");

            // Set headers
            int row = 0;
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(row).setCellValue("Mail");
            for (VariableDto variableDto : variables) {
                row++;
                headerRow.createCell(row).setCellValue(variableDto.getVarVariable());
            }

            // Save excel window config
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Excel File");
            fileChooser.getExtensionFilters().add(new ExtensionFilter("Excel Files", "*.xlsx"));
            File file = fileChooser.showSaveDialog(((Window) listViewNotificationProcess.getScene().getWindow()));

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
        } else {
            new Message().show(Alert.AlertType.INFORMATION, "Generate excel", "You must select a notification process to generate an excel");
        }
    }

    @FXML
    void loadExcel(ActionEvent event) {
        if (listViewNotificationProcess.getSelectionModel().getSelectedItem() != null) {
            observableListEmails.clear();
            Long notificationProcessID = listViewNotificationProcess.getSelectionModel().getSelectedItem().getNtpId();
            List<VariableDto> variables = (List<VariableDto>) variableService.getVariablesByNTP(notificationProcessID).getResult("Variables");
            // Load excel window config
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Excel File");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));

            File file = fileChooser.showOpenDialog(((Window) listViewNotificationProcess.getScene().getWindow()));

            if (file != null) {
                try (Workbook workbook = WorkbookFactory.create(file)) {
                    Sheet sheet = workbook.getSheetAt(0);
                    ObservableList<NotificationProcessDto> loadedData = FXCollections.observableArrayList();

                    for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                        Row row = sheet.getRow(rowNum);
                        EmailsDto email = new EmailsDto();
                        email.setNtpId(listViewNotificationProcess.getSelectionModel().getSelectedItem().getNtpId());
                        email.setEmlHtml(listViewNotificationProcess.getSelectionModel().getSelectedItem().getNtpHtml());
                        email.setEmlSent("N");
                        email.setEmlVersion(Long.valueOf(1));
                        if (row != null) {
                            int column = 0;
                            Cell mailCell = row.getCell(column);
                            email.setEmlEmailaddress(mailCell.getStringCellValue());
                            column++;
                            Cell variable;
                            while (sheet.getRow(0).getCell(column) != null) {
                                variable = row.getCell(column);
                                String columnVar = sheet.getRow(0).getCell(column).getStringCellValue();
                                if(variable==null || (isVariableContrain( variables, columnVar) && !isValueToContrainWithValue(variables,columnVar,variable.getStringCellValue()))){
                                    email.setEmlHtml(email.getEmlHtml().replace(columnVar, getDefaultValue(variables, columnVar)));
                                }else{
                                    if(isValueToContrainWithValue(variables,columnVar,variable.getStringCellValue())){
                                    String cellValue = variable.getStringCellValue();
                                    email.setEmlHtml(email.getEmlHtml().replace( columnVar, getValueToContrainWithValue(variables, columnVar, variable.getStringCellValue())));
                                    }else{
                                    String cellValue = variable.getStringCellValue();
                                    email.setEmlHtml(email.getEmlHtml().replace( columnVar, cellValue));
                                    }
                                }
                                column++;
                            }
                        }
                        observableListEmails.add(email);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                new Message().show(Alert.AlertType.INFORMATION, "Load Excel", "No file was selected.");
            }
            filteredEmails = new FilteredList<>(observableListEmails, p -> true);
            listViewEmails.setItems(filteredEmails);
        } else {
            new Message().show(Alert.AlertType.INFORMATION, "Load excel", "You must select a notification process to load an excel");
        }
    }
    private boolean isVariableContrain(List<VariableDto> variables, String variable){
        for(VariableDto var : variables){
            if(var.getVarVariable().equals(variable)){
                if("C".equals(var.getVarType())){
                    return true;
                }
            }
        }
        return false;
    }
    private boolean isValueToContrainWithValue(List<VariableDto> variables, String variable, String stringCellValue){
        for(VariableDto var : variables){
            if(var.getVarVariable().equals(variable)){
                List<ConstrainDto> constrains = (List<ConstrainDto>) this.constrainService.getConstrainsByVariable(var.getVarId()).getResult("Constrains");
                for(ConstrainDto constrain : constrains){
                    if(constrain.getCnstSymbol().equals(stringCellValue)){
                        return true;
                    }
                }
            }
        }return false;
    }
    private String getValueToContrainWithValue(List<VariableDto> variables, String variable, String stringCellValue){
        for(VariableDto var : variables){
            if(var.getVarVariable().equals(variable)){
                List<ConstrainDto> constrains = (List<ConstrainDto>) this.constrainService.getConstrainsByVariable(var.getVarId()).getResult("Constrains");
                for(ConstrainDto constrain : constrains){
                    if(constrain.getCnstSymbol().equals(stringCellValue)){
                        return constrain.getCnstResult();
                    }
                }
            }
        }return "Failure with value";
    }
    
    private String getDefaultValue(List<VariableDto> variables,String variable){
        for(VariableDto var : variables){
            if(var.getVarVariable().equals(variable)){
            return var.getVarDefault();
            }
        }
        return "no default value";
    }
    @FXML
    void handleNotificationProcessClick(MouseEvent event) {
        EmailsDto selectedEmail = listViewEmails.getSelectionModel().getSelectedItem();

        if (selectedEmail != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Email HTML Content");
            alert.setHeaderText("HTML Content of Selected Email");

            WebView webView = new WebView();
            WebEngine webEngine = webView.getEngine();
            webEngine.loadContent(selectedEmail.getEmlHtml());

            webView.setPrefSize(800, 600);

            VBox vbox = new VBox(webView);
            alert.getDialogPane().setContent(vbox);

            alert.showAndWait();
            this.listViewEmails.getSelectionModel().clearSelection();
        } else {
            new Message().show(Alert.AlertType.INFORMATION, "Select Email", "You must select an email to view its content");
        }
    }
    @FXML
    void handleFileClicked(MouseEvent event) {
        FileDto fileDto = listViewFiles.getSelectionModel().getSelectedItem();
        if(fileDto != null){
            downloadFile(fileDto);
        }
    }
    @FXML
    void onActionAproveToSend(ActionEvent event) {
        if(!this.observableListEmails.isEmpty()&&this.observableListEmails!=null){
            if(this.observableListFiles != null && !this.observableListFiles.isEmpty()){
                for(int i = 0 ; i < this.observableListFiles.size() ; i++){
                    FileDto fileDto = this.observableListFiles.get(i);
                    fileDto.setFleVersion(Long.valueOf(1));
                    fileDto = (FileDto) fileService.saveFile(fileDto).getResult("File");
                    this.observableListFiles.set(i, fileDto);
                }
            }
            for(EmailsDto email: this.observableListEmails){
                if(this.observableListFiles != null && !this.observableListFiles.isEmpty()){
                    this.observableListFiles.stream().forEach(f->email.getFileCollection().add(f));
                }
                emailsService.saveEmail(email);
            }
        }
        this.observableListFiles.clear();
        this.observableListEmails.clear();
    }
    
    @FXML
    void onActionAddFile(ActionEvent event) {
        String fileFormat;
        String fileName;
        Byte[] binaryData;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar archivo");

        // Filtro para permitir solo algunos tipos de archivos (opcional)
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Todos los archivos", "*.*")
        );

        // Obtener la ventana actual (el stage)
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

        // Mostrar la ventana para seleccionar el archivo
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            try {
                // Leer el archivo y convertirlo a un vector de char en binario
                binaryData = convertFileToBinary(selectedFile);
                // Obtener el formato del archivo
                fileName = getFileName(selectedFile);
                fileFormat = getFileExtension(selectedFile);
                observableListFiles.add(new FileDto(null,fileFormat,fileName,binaryData));
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void downloadFile(FileDto fileDto) {
        // Crear un objeto FileChooser para guardar el archivo
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save file");

        // Establecer el nombre del archivo por defecto
        fileChooser.setInitialFileName(fileDto.getFleName() + "." + fileDto.getFleType());

        // Filtro para el formato del archivo a guardar
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Formato original", "*." +  fileDto.getFleType())
        );

        // Obtener el stage actual
        Stage stage = (Stage) listViewFiles.getScene().getWindow();
        
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

    private Byte[] convertFileToBinary(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        List<Character> binaryCharList = new ArrayList<>();

        int byteData;
        // Leer cada byte del archivo
        while ((byteData = fileInputStream.read()) != -1) {
            // Convertir cada byte a su representación binaria y agregar a la lista
            String binaryString = String.format("%8s", Integer.toBinaryString(byteData)).replace(' ', '0');
            for (char bit : binaryString.toCharArray()) {
                binaryCharList.add(bit);
            }
        }
        fileInputStream.close();

        // Convertir la lista de chars a un array de chars
        Byte[] binaryData = new Byte[binaryCharList.size()];
        for (int i = 0; i < binaryCharList.size(); i++) {
            binaryData[i] =(byte) binaryCharList.get(i).charValue();
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
    void onActionClearFiles(ActionEvent event) {
        this.observableListFiles.clear();
    }
}
