package cr.ac.una.sigeceunasecurity.controller;

import cr.ac.una.sigeceunasecurity.model.UserDto;
import cr.ac.una.sigeceunasecurity.service.UserService;
import cr.ac.una.sigeceunasecurity.util.AppContext;
import cr.ac.una.sigeceunasecurity.util.Formato;
import cr.ac.una.sigeceunasecurity.util.Message;
import cr.ac.una.sigeceunasecurity.util.Response;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class SearchUserController extends Controller implements Initializable {

    @FXML
    private Label labelSearchUser;
    @FXML
    private TableView<UserDto> tblTable;
    @FXML
    private TableColumn<UserDto, String> columId;
    @FXML
    private TableColumn<UserDto, String> colmName;
    @FXML
    private TableColumn<UserDto, String> columSurname;
    @FXML
    private TableColumn<UserDto, String> columSecondSurname;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtSurname;
    @FXML
    private TextField txtSecondSurname;

    private UserDto userDtoTable;
    private List<UserDto> userDtoList = new ArrayList<>();
    private ObservableList<UserDto> userDtoObservable = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assignFormat();
    }

    @Override
    public void initialize() {
    }

    private void handleUserSelection(UserDto selectedUser) {
        if (selectedUser != null) {
            AppContext.getInstance().set("selectedUser", selectedUser);
            new Message().showModal(Alert.AlertType.INFORMATION, "Filter User", getStage(), "User " + selectedUser.getUsrName() + " has been selected.");
        } else {
            new Message().showModal(Alert.AlertType.WARNING, "Load User", getStage(), "No user has been selected.");
        }
    }

    /*Seleccionar user de la lista con doble click*/
    @FXML
    private void onMousePressedLoadList(MouseEvent event) {
        if (event.getClickCount() == 2) {
            userDtoTable = tblTable.getSelectionModel().getSelectedItem();
            handleUserSelection(userDtoTable);
        }
    }

    @FXML
    private void onActionBtnFilter(ActionEvent event) {
        loadUser();
        displayUserTable();
    }

    @FXML
    private void onActionBtnAccept(ActionEvent event) {
        handleUserSelection(userDtoTable);
    }

    private void loadUser() {
        try {
            UserService userService = new UserService();
            Response response = userService.getUsers();
            if (response.getState()) {
                List<UserDto> allUsers = (List<UserDto>) response.getResult("Users");
                this.userDtoList = filtrarUsuarios(allUsers);
                userDtoObservable = FXCollections.observableArrayList(userDtoList);
                tblTable.setItems(userDtoObservable);

                new Message().showModal(Alert.AlertType.INFORMATION, "Load Users", getStage(), "User table loaded successfully.");
            }

        } catch (Exception ex) {
            Logger.getLogger(SearchUserController.class.getName()).log(Level.SEVERE, "Error loading the user list.", ex);
            new Message().showModal(Alert.AlertType.ERROR, "Load User", getStage(), "An error occurred while loading users.");
        }
    }

    private List<UserDto> filtrarUsuarios(List<UserDto> allUsers) {
        String idFilter = txtId.getText().trim();
        String nameFilter = txtName.getText().trim();
        String surnameFilter = txtSurname.getText().trim();
        String secondSurnameFilter = txtSecondSurname.getText().trim();

        return allUsers.stream()
                .filter(user -> (idFilter.isEmpty() || user.getUsrId().equals(idFilter))
                && (nameFilter.isEmpty() || user.getUsrName().equalsIgnoreCase(nameFilter))
                && (surnameFilter.isEmpty() || user.getUsrLastname().equalsIgnoreCase(surnameFilter))
                && (secondSurnameFilter.isEmpty() || user.getUsrSurname().equalsIgnoreCase(secondSurnameFilter)))
                .collect(Collectors.toList());
    }

    private void assignFormat() {
        txtId.setTextFormatter(Formato.getInstance().integerFormat());
        txtName.setTextFormatter(Formato.getInstance().letrasFormat(15));
        txtSurname.setTextFormatter(Formato.getInstance().letrasFormat(15));
        txtSecondSurname.setTextFormatter(Formato.getInstance().letrasFormat(15));
    }

    private void displayUserTable() {
        userDtoObservable = FXCollections.observableArrayList(userDtoList);
        tblTable.setItems(userDtoObservable);

        columId.setCellValueFactory(new PropertyValueFactory<UserDto, String>("Id"));
        colmName.setCellValueFactory(new PropertyValueFactory<UserDto, String>("Name"));
        columSurname.setCellValueFactory(new PropertyValueFactory<UserDto, String>("Surname"));
        columSecondSurname.setCellValueFactory(new PropertyValueFactory<UserDto, String>("secondSurname"));
    }
}