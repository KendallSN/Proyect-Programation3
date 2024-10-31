package cr.ac.una.sigeceunasecurity.controller;

import cr.ac.una.sigeceunasecurity.service.RoleService;
import cr.ac.una.sigeceunasecurity.service.UserService;
import cr.ac.una.sigeceunasecurity.service.SystemsService;
import cr.ac.una.sigeceunasecurity.util.Message;
import cr.ac.una.sigeceunasecurity.util.Response;
import cr.ac.una.sigeceunasecurityws.controller.RoleDto;
import cr.ac.una.sigeceunasecurityws.controller.SystemsDto;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableCell;

public class ManageRolesController extends Controller implements Initializable {

    @FXML
    private TextField txtSystemID_Search;
    @FXML
    private TextField txtSystemName_Search;
    @FXML
    private TextField txtUserID_Search;
    @FXML
    private TextField txtUserName_Search;
    @FXML
    private TextField txtSystemDescription;
    @FXML
    private TextField txtSystemName;
    @FXML
    private TextField txtRoleName;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btn_DeleteRole;
    @FXML
    private Button btn_DeleteSystem;

    @FXML
    private TableView<cr.ac.una.sigeceunasecurity.model.UserDto> tblUsers;
    @FXML
    private TableColumn<cr.ac.una.sigeceunasecurity.model.UserDto, SimpleStringProperty> colId;
    @FXML
    private TableColumn<cr.ac.una.sigeceunasecurity.model.UserDto, SimpleStringProperty> colName;
    @FXML
    private TableColumn<cr.ac.una.sigeceunasecurity.model.UserDto, String> colUsrState;
    @FXML
    private TableView<cr.ac.una.sigeceunasecurity.model.SystemsDto> tblSystems;
    @FXML
    private TableColumn<cr.ac.una.sigeceunasecurity.model.SystemsDto, SimpleStringProperty> colSystemId;
    @FXML
    private TableColumn<cr.ac.una.sigeceunasecurity.model.SystemsDto, SimpleStringProperty> colSystemName;
    @FXML
    private TableColumn<cr.ac.una.sigeceunasecurity.model.SystemsDto, SimpleStringProperty> colSystemRole;
    @FXML
    private TableView<cr.ac.una.sigeceunasecurity.model.RoleDto> tblRoles;
    @FXML
    private TableColumn<cr.ac.una.sigeceunasecurity.model.RoleDto, SimpleStringProperty> colRoleId;
    @FXML
    private TableColumn<cr.ac.una.sigeceunasecurity.model.RoleDto, SimpleStringProperty> colRoleName;

    UserService userService = new UserService();
    SystemsService systemsService = new SystemsService();
    RoleService roleService = new RoleService();

    java.util.List<cr.ac.una.sigeceunasecurityws.controller.RoleDto> roles;
    java.util.List<cr.ac.una.sigeceunasecurity.model.RoleDto> roleDtos;
    cr.ac.una.sigeceunasecurity.model.SystemsDto selectedSystem = new cr.ac.una.sigeceunasecurity.model.SystemsDto();
    cr.ac.una.sigeceunasecurity.model.UserDto selectedUser = new cr.ac.una.sigeceunasecurity.model.UserDto();
    cr.ac.una.sigeceunasecurity.model.RoleDto selectedRole=new cr.ac.una.sigeceunasecurity.model.RoleDto();

    // ObservableLists for the tables
    ObservableList<cr.ac.una.sigeceunasecurity.model.UserDto> observableUserDtos = FXCollections.observableArrayList(new ArrayList<>());
    ObservableList<cr.ac.una.sigeceunasecurity.model.SystemsDto> observableSystemDtos = FXCollections.observableArrayList(new ArrayList<>());
    ObservableList<cr.ac.una.sigeceunasecurity.model.RoleDto> observableRoleDtos = FXCollections.observableArrayList(new ArrayList<>());

    private FilteredList<cr.ac.una.sigeceunasecurity.model.UserDto> filteredUserDtos;
    private FilteredList<cr.ac.una.sigeceunasecurity.model.SystemsDto> filteredSystemDtos;
    private FilteredList<cr.ac.una.sigeceunasecurity.model.RoleDto> filteredRoleDtos;
    @FXML
    private Label unusedLabel;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colId.setCellValueFactory(new PropertyValueFactory<>("usrId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("usrName"));
        colUsrState.setCellValueFactory(new PropertyValueFactory<>("usrState"));
        colUsrState.setCellFactory(column -> new TableCell<cr.ac.una.sigeceunasecurity.model.UserDto, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.equals("A") ? "Activo" : "Inactivo");
                }
            }
        });
        
        colSystemId.setCellValueFactory(new PropertyValueFactory<>("systId"));
        colSystemName.setCellValueFactory(new PropertyValueFactory<>("systName"));
        colSystemRole.setCellValueFactory(new PropertyValueFactory<>("asignedRole"));
        colRoleId.setCellValueFactory(new PropertyValueFactory<>("rolId"));
        colRoleName.setCellValueFactory(new PropertyValueFactory<>("rolName"));

        
        // Inicialización de listas filtradas
        filteredUserDtos = new FilteredList<>(observableUserDtos, p -> true);
        filteredSystemDtos = new FilteredList<>(observableSystemDtos, p -> true);
        filteredRoleDtos = new FilteredList<>(observableRoleDtos, p -> true);

        tblUsers.setItems(filteredUserDtos);
        tblSystems.setItems(filteredSystemDtos);
        tblRoles.setItems(filteredRoleDtos);

        // Configuración de listeners para los campos de búsqueda
        txtUserID_Search.textProperty().addListener((observable, oldValue, newValue) -> updateUserFilter());
        txtUserName_Search.textProperty().addListener((observable, oldValue, newValue) -> updateUserFilter());
        txtSystemID_Search.textProperty().addListener((observable, oldValue, newValue) -> updateSystemFilter());
        txtSystemName_Search.textProperty().addListener((observable, oldValue, newValue) -> updateSystemFilter());

        // Configuración de selección en tablas
        tblUsers.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        selectedUser = newValue;
                        selectedSystem = null;
                        selectedRole=null;
                        updateSystemsLists();
                        updateSystemsTable();
                    }
                }
        );
        tblSystems.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        selectedSystem = newValue;
                        selectedRole=null;
                        updateRolesTable();
                    }
                }
        );
        tblRoles.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        Collection<cr.ac.una.sigeceunasecurity.model.RoleDto> newList = new ArrayList<>();
                        selectedRole=newValue;
                        if (selectedUserHasRole(newValue)) {
                            new Message().showModal(Alert.AlertType.INFORMATION, "Role asignation", getStage(), "This user already has this role");
                        }

                        newList.add(newValue);
                        for (cr.ac.una.sigeceunasecurity.model.RoleDto roleDto : selectedUser.getRoleCollection()) {
                            if (roleDto.getSystId() != newValue.getSystId()) {
                                newList.add(roleDto);
                            }
                        }

                        this.selectedUser.setRoleCollection(newList);
                        userService.saveUser(selectedUser.getUser());
                        updateSystemsLists();
                        updateSystemsTable();
                    }
                }
        );
    }

    private boolean selectedUserHasRole(cr.ac.una.sigeceunasecurity.model.RoleDto newValue) {
        for (cr.ac.una.sigeceunasecurity.model.RoleDto roleDto : this.selectedUser.getRoleCollection()) {
            if (roleDto.getRolId() == newValue.getRolId()) {
                return true;
            }
        }
        return false;
    }

    private void updateUserFilter() {
        filteredUserDtos.setPredicate(user -> {
            String idFilter = txtUserID_Search.getText().toLowerCase();
            String nameFilter = txtUserName_Search.getText().toLowerCase();
            boolean idMatches = user.getUsrId() != null && user.getUsrId().toString().toLowerCase().contains(idFilter);
            boolean nameMatches = user.getUsrName() != null && user.getUsrName().toLowerCase().contains(nameFilter);
            return idFilter.isEmpty() ? nameMatches : (idMatches && nameMatches);
        });
    }

    private void updateSystemFilter() {
        filteredSystemDtos.setPredicate(system -> {
            String idFilter = txtSystemID_Search.getText().toLowerCase();
            String nameFilter = txtSystemName_Search.getText().toLowerCase();
            boolean idMatches = system.getSystId() != null && system.getSystId().toString().toLowerCase().contains(idFilter);
            boolean nameMatches = system.getSystName() != null && system.getSystName().toLowerCase().contains(nameFilter);
            return idFilter.isEmpty() ? nameMatches : (idMatches && nameMatches);
        });
    }

    private void updateSystemsTable() {
        for (cr.ac.una.sigeceunasecurity.model.SystemsDto systemDto : this.observableSystemDtos) {
            systemDto.setAsignedRole(getRolNamebySystem(systemDto.getSystId()));
        }
        tblSystems.setItems(filteredSystemDtos); // Use filteredSystemDtos
    }

    private void updateSystemsLists() {
        List<cr.ac.una.sigeceunasecurityws.controller.SystemsDto> systems = (List<cr.ac.una.sigeceunasecurityws.controller.SystemsDto>) systemsService.getSystems().getResult("Systems");
        this.observableSystemDtos.clear();
        for (cr.ac.una.sigeceunasecurityws.controller.SystemsDto system : systems) {
            cr.ac.una.sigeceunasecurity.model.SystemsDto systemsDto = new cr.ac.una.sigeceunasecurity.model.SystemsDto(system);
            this.observableSystemDtos.add(systemsDto);
        }
    }

    private String getRolNamebySystem(Long SystemID) {
        for (cr.ac.una.sigeceunasecurity.model.RoleDto roleDto : this.selectedUser.getRoleCollection()) {
            if (SystemID.equals(roleDto.getSystId())) {
                return roleDto.getRolName();
            }
        }
        return "Sin rol";
    }

    private void updateRolesTable() {
        roleDtos = new ArrayList<>();
        roles = (List<RoleDto>) roleService.getRolesOfSystem(this.selectedSystem.getSystem()).getResult("RolesOfSystem");
        for (cr.ac.una.sigeceunasecurityws.controller.RoleDto role : roles) {
            roleDtos.add(new cr.ac.una.sigeceunasecurity.model.RoleDto(role));
        }
        observableRoleDtos = FXCollections.observableArrayList(roleDtos);
        tblRoles.setItems(observableRoleDtos); // Use filteredRoleDtos
    }
    private void updateUsersTable() {
        List<cr.ac.una.sigeceunasecurityws.controller.UserDto> users = (List<cr.ac.una.sigeceunasecurityws.controller.UserDto>) userService.getUsers().getResult("Users");
        this.selectedSystem = null;
        this.selectedUser = null;
        this.selectedRole=null;
        this.observableUserDtos.clear();
        this.observableSystemDtos.clear();
        this.observableRoleDtos.clear();
        for (cr.ac.una.sigeceunasecurityws.controller.UserDto user : users) {
            this.observableUserDtos.add(new cr.ac.una.sigeceunasecurity.model.UserDto(user));
        }
        tblUsers.setItems(filteredUserDtos);
    }
    @Override
    public void initialize() {
        this.txtSystemID_Search.clear();
        this.txtSystemName_Search.clear();
        this.txtUserID_Search.clear();
        this.txtUserName_Search.clear();

        updateUsersTable();
        tblUsers.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        selectedUser = newValue;
                        selectedSystem = null;
                        selectedRole=null;
                        updateSystemsLists();
                        updateSystemsTable();
                    }
                }
        );
        tblSystems.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        selectedSystem = newValue;
                        selectedRole=null;
                        updateRolesTable();
                    }
                }
        );
        tblRoles.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        selectedRole=newValue;
                        Collection<cr.ac.una.sigeceunasecurity.model.RoleDto> newList = new ArrayList<>();

//                    if (selectedUserHasRole(newValue)) {
//                        new Message().showModal(Alert.AlertType.INFORMATION, "Role asignation", getStage(), "This user already has this role");
//                    }
                        newList.add(newValue);
                        for (cr.ac.una.sigeceunasecurity.model.RoleDto roleDto : selectedUser.getRoleCollection()) {
                            if (roleDto.getSystId() != newValue.getSystId()) {
                                newList.add(roleDto);
                            }
                        }

                        this.selectedUser.setRoleCollection(newList);
                        userService.saveUser(selectedUser.getUser());
                        updateSystemsLists();
                        updateSystemsTable();
                    }
                }
        );
    }
    @FXML
    void deactivateUser(ActionEvent event) {
         if(this.selectedUser!=null){
            this.selectedUser.setUsrState("I");
            userService.saveUser(selectedUser.getUser());
            new Message().showModal(Alert.AlertType.INFORMATION, "User diactivate", getStage(), "User "+this.selectedUser.getUsrName()+" diactivated");
            updateUsersTable();
        }else{
            new Message().showModal(Alert.AlertType.ERROR, "User diactivate", getStage(), "Select a user to diactivate");
        }
    }

    @FXML
    private void onActionBtnCancel(ActionEvent event) {
        // Implement cancel logic if needed
    }

    private void onActionBtnFilterPerson(ActionEvent event) {
        updateUserFilter();
    }

    @FXML
    void saveSystem(ActionEvent event) {
        if (txtSystemName.getText() != null && !txtSystemName.getText().trim().isEmpty()) {
            SystemsDto systemDto = new SystemsDto();
            systemDto.setSystName(txtSystemName.getText());
            txtSystemName.clear();
            systemDto.setSystDescription(txtSystemDescription.getText());
            txtSystemDescription.clear();
            systemDto.setSystVersion(1L);
            systemsService.saveSystem(systemDto);
            updateSystemsLists();
            updateSystemsTable();
        }
    }

    @FXML
    void saveRole(ActionEvent event) {
        if (txtRoleName.getText() != null && !txtRoleName.getText().trim().isEmpty() && this.selectedSystem != null && this.selectedUser != null) {
            RoleDto roleDto = new RoleDto();
            roleDto.setRolName(txtRoleName.getText());
            txtRoleName.clear();
            roleDto.setSystId(this.selectedSystem.getSystem().getSystId());
            roleDto.setRolVersion(1L);
            
            roleDto.setSystId(selectedSystem.getSystId());
            roleService.saveRole(roleDto);
            updateRolesTable();
        } else {
            new Message().showModal(Alert.AlertType.ERROR, "User or system not selected", getStage(), "Select a user and system to save a role");
            txtRoleName.clear();
        }
    }
    
    @FXML
    void onActionBtnDeleteRole(ActionEvent event) {
         boolean userConfirmed = new Message().showConfirmation("Delete role", getStage(), "Are you sure you want to delete this role?.");
        if(userConfirmed){
            try {
                    Response response = roleService.deleteRole(this.selectedRole.getRolId());
                    this.selectedUser.getRoleCollection().remove(this.selectedRole);
                    if (!response.getState()) {
                        new Message().showModal(Alert.AlertType.ERROR, "Delete role", getStage(), response.getMessage());
                    } else {    
                        updateRolesTable();
                        updateSystemsLists();                     
                        updateSystemsTable();
                        
                        new Message().showModal(Alert.AlertType.INFORMATION, "Delete role", getStage(), "Role deleted successfully.");
                    }         
            } catch (Exception ex) {
                Logger.getLogger(ManageRolesController.class.getName()).log(Level.SEVERE, "An error ocurred while deleting the role.", ex);
                new Message().showModal(Alert.AlertType.ERROR, "Delete role", getStage(), "An error ocurred while deleting the role.");
            }
        }
    }

    @FXML
    void onActionBtnDeleteSystem(ActionEvent event) {
        boolean userConfirmed = new Message().showConfirmation("Delete system", getStage(), "Are you sure you want to delete this system?.");
        if(userConfirmed){
            try {
                    Response response = systemsService.deleteSystems(this.selectedSystem.getSystId());
                    if (!response.getState()) {
                        new Message().showModal(Alert.AlertType.ERROR, "Delete system", getStage(), response.getMessage());
                    } else {
                        updateRolesTable();
                        updateSystemsLists();
                        updateSystemsTable();
                        new Message().showModal(Alert.AlertType.INFORMATION, "Delete system", getStage(), "System deleted successfully.");
                    }         
            } catch (Exception ex) {
                Logger.getLogger(ManageRolesController.class.getName()).log(Level.SEVERE, "An error ocurred while deleting the system.", ex);
                new Message().showModal(Alert.AlertType.ERROR, "Delete system", getStage(), "An error ocurred while deleting the system.");
            }
        }
    }

}