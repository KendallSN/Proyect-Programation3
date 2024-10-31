package cr.ac.una.sigeceunaemail.controller;

import cr.ac.una.sigeceunaemail.model.EmailmanagerDto;
import cr.ac.una.sigeceunaemail.model.EmailsDto;
import cr.ac.una.sigeceunaemail.service.EmailManagerService;
import cr.ac.una.sigeceunaemail.service.EmailsService;
import cr.ac.una.sigeceunaemail.util.Message;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class ManageMailsController extends Controller implements Initializable {
    private ObservableList<EmailsDto> ObservableListEmails;
    private EmailsService emailsService = new EmailsService();
    private EmailManagerService emailManagerService = new EmailManagerService();
    private FilteredList<EmailsDto> filteredEmails;
    private EmailmanagerDto emailmanagerDto;

    @FXML
    private ListView<EmailsDto> listViewEmails;
    @FXML
    private TextField txtEmail_Search;
    @FXML
    private ChoiceBox<String> choiceBoxState;
    @FXML
    private Label lblEmail;
    @FXML
    private Label lblLimitPerHour;
    @FXML
    private Label lblPassword;
    @FXML
    private Label lblPort;
    @FXML
    private Label lblTime;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtLimitPerHour;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtPort;
    @FXML
    private TextField txtTimer;
    @FXML
    void onActionUpdateParameters(ActionEvent event) {
        if(!this.txtEmail.getText().isEmpty() && !this.txtEmail.getText().isBlank()){
            this.emailmanagerDto.setEmmEmail(this.txtEmail.getText());
            this.txtEmail.clear();
        }
        if(!this.txtPassword.getText().isEmpty() && !this.txtPassword.getText().isBlank()){
            this.emailmanagerDto.setEmmPassword(this.txtPassword.getText());
            this.txtPassword.clear();
        }
        if(!this.txtLimitPerHour.getText().isEmpty() && !this.txtLimitPerHour.getText().isBlank()){
            this.emailmanagerDto.setEmmLimitperhour(Long.valueOf(this.txtLimitPerHour.getText()));
            this.txtLimitPerHour.clear();
        }
        if(!this.txtTimer.getText().isEmpty() && !this.txtTimer.getText().isBlank()){
            this.emailmanagerDto.setEmmTimeinminutes(Long.valueOf(this.txtTimer.getText()));
            this.txtTimer.clear();
        }
        if(!this.txtPort.getText().isEmpty() && !this.txtPort.getText().isBlank()){
            this.emailmanagerDto.setEmmPort(this.txtPort.getText());
            this.txtPort.clear();
        }
        this.emailmanagerDto = (EmailmanagerDto) this.emailManagerService.saveEmailManager(emailmanagerDto).getResult("EmailManager");
        this.updateParametersLabels();
    }
    // EveryTime the fxml is charged
    @Override
    public void initialize() {
        
        updateParametersLabels();
        ObservableList<String> options = FXCollections.observableArrayList();
        options.add("Sent");
        options.add("Pending");
        options.add("All");
        choiceBoxState.setItems(options);
        updateObservableListEmails();
    }
    private void updateParametersLabels(){
        this.lblEmail.setText(this.emailmanagerDto.getEmmEmail());
        this.lblPassword.setText(this.emailmanagerDto.getEmmPassword());
        this.lblLimitPerHour.setText(this.emailmanagerDto.getEmmLimitperhour().toString());
        this.lblTime.setText(this.emailmanagerDto.getEmmTimeinminutes().toString());
        this.lblPort.setText(this.emailmanagerDto.getEmmPort());
    }
    // First time the fxml is charged
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        emailmanagerDto = (EmailmanagerDto) emailManagerService.getEmailManager(Long.valueOf(1)).getResult("EmailManager");
        if(emailmanagerDto==null){
            emailmanagerDto=new EmailmanagerDto();
            emailmanagerDto.setEmmEmail("evacomunamail@gmail.com");
            emailmanagerDto.setEmmPassword("rgjp qncf ydwk euqp");
            emailmanagerDto.setEmmPort("587");
            emailmanagerDto.setEmmLimitperhour(Long.valueOf(60));
            emailmanagerDto.setEmmTimeinminutes(Long.valueOf(1));
            emailmanagerDto.setEmmVersion(Long.valueOf(1));
            emailManagerService.saveEmailManager(emailmanagerDto);
        }
        updateObservableListEmails();
        txtEmail_Search.textProperty().addListener((observable, oldValue, newValue) -> {
            applyFilters();
        });

        choiceBoxState.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            applyFilters();
        });

        listViewEmails.setCellFactory(param -> new ListCell<EmailsDto>() {
            @Override
            protected void updateItem(EmailsDto item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    if ("N".equals(item.getEmlSent())) {
                        setText(item.getEmlId() + "              " + item.getEmlEmailaddress() + "           Pending");
                    } else {
                        setText(item.getEmlId() + "              " + item.getEmlEmailaddress() + "           Sent");
                    }
                }
            }
        });
    }
    
    @FXML
    void OnMouserClickedEmail(MouseEvent event) {
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

    private void updateObservableListEmails() {
        cr.ac.una.sigeceunaemail.util.Response res = emailsService.getEmails();
        List<EmailsDto> ListEmails = (List<EmailsDto>) res.getResult("Emails");
        this.ObservableListEmails = FXCollections.observableArrayList(ListEmails);
        this.filteredEmails = new FilteredList<>(this.ObservableListEmails, e -> true);
        this.listViewEmails.setItems(filteredEmails);
        FXCollections.sort(ObservableListEmails,Comparator.comparing(EmailsDto::getEmlId));
        choiceBoxState.getSelectionModel().select(2);
    }

    private void applyFilters() {
        String searchText = txtEmail_Search.getText().toLowerCase();
        String selectedState = choiceBoxState.getSelectionModel().getSelectedItem();

        filteredEmails.setPredicate(email -> {
            boolean matchesSearchText = (searchText == null || searchText.isEmpty()) ||
                    email.getEmlEmailaddress().toLowerCase().contains(searchText);
            boolean matchesState = false;
            if ("All".equals(selectedState)) {
                matchesState = true;
            } else if ("Sent".equals(selectedState)) {
                matchesState = "Y".equals(email.getEmlSent());
            } else if ("Pending".equals(selectedState)) {
                matchesState = "N".equals(email.getEmlSent());
            }
            return matchesSearchText && matchesState;
        });
    }
    
    @FXML
    void onActionUpdate(ActionEvent event) {
        updateObservableListEmails();
    }
}
