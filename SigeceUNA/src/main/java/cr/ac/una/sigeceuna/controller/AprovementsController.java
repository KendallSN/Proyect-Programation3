
package cr.ac.una.sigeceuna.controller;

import cr.ac.una.sigeceuna.model.ManagementDto;
import cr.ac.una.sigeceuna.model.ManagementaprobationDto;
import cr.ac.una.sigeceuna.model.UserDto;
import cr.ac.una.sigeceuna.service.ManagementAprobationService;
import cr.ac.una.sigeceuna.service.ManagementService;
import cr.ac.una.sigeceuna.util.AppContext;
import cr.ac.una.sigeceuna.util.FlowController;
import cr.ac.una.sigeceuna.util.Message;
import cr.ac.una.sigeceuna.util.Response;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;


public class AprovementsController extends Controller implements Initializable{
    @FXML
    private TableColumn<ManagementaprobationDto, String> tblC_ID;

    @FXML
    private TableColumn<ManagementaprobationDto,String> tblC_ManagementID;

    @FXML
    private TableColumn<ManagementaprobationDto, String> tblC_State;

    @FXML
    private TableView<ManagementaprobationDto> tblV_Aprovements;

    @FXML
    private TableColumn<ManagementaprobationDto, Void> tblC_Vote;
    
    @FXML
    private TableColumn<ManagementaprobationDto,String> tblC_Description;
    
    //services
    ManagementAprobationService managementaprobationService=new ManagementAprobationService();
    ManagementService managementService=new ManagementService();
    
    private ObservableList<ManagementaprobationDto>observableManagementaprobationsDto=FXCollections.observableArrayList();

    private List<ManagementaprobationDto> managementaprobationsList=new ArrayList<>();
    private UserDto loggedUser;
    private ResourceBundle bundle;
    @FXML
    private ImageView imgNew;
    
    @Override
    public void initialize() {
        this.bundle=FlowController.getIdioma();
        this.managementaprobationsList=new ArrayList<>();
        this.observableManagementaprobationsDto.clear();
        this.loggedUser=(UserDto) AppContext.getInstance().get("User");    
        
        this.managementaprobationsList=((List<ManagementaprobationDto>) this.managementaprobationService.getManagementaprobations().getResult("Managementaprobations"))
                .stream().filter(m->m.getUsrToaproveId().getUsrId().equals(this.loggedUser.getUsrId())).toList();      
        this.observableManagementaprobationsDto.addAll(managementaprobationsList);
        FXCollections.sort(observableManagementaprobationsDto, Comparator.comparing(ManagementaprobationDto::getMgtaId));

        //Initialize TableView
        tblC_ID.setCellValueFactory(new PropertyValueFactory<>("mgtaId"));
        tblC_ManagementID.setCellValueFactory(new PropertyValueFactory<>("mgtId"));
        tblC_Description.setCellValueFactory(new PropertyValueFactory<>("mgtaComment"));
        
        tblC_State.setCellValueFactory(new PropertyValueFactory<>("mgtaState"));
        tblC_State.setCellFactory(column -> new TableCell<ManagementaprobationDto, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    switch (item.toLowerCase()) {
                        case "pending":
                            setText(bundle.getString("pending"));
                            break;
                        case "approved":
                            setText(bundle.getString("approved"));
                            break;
                        case "rejected":
                            setText(bundle.getString("rejected"));
                            break;
                        default:
                            setText(bundle.getString("unknown"));
                            break;
                    }
                }
            }
        });
        
        tblV_Aprovements.setItems(this.observableManagementaprobationsDto);
        
        //buttons column
        Callback<TableColumn<ManagementaprobationDto, Void>, TableCell<ManagementaprobationDto, Void>> cellFactory = new Callback<TableColumn<ManagementaprobationDto, Void>, TableCell<ManagementaprobationDto, Void>>() {
            @Override
            public TableCell<ManagementaprobationDto, Void> call(final TableColumn<ManagementaprobationDto, Void> param) {
                final TableCell<ManagementaprobationDto, Void> cell = new TableCell<ManagementaprobationDto, Void>() {

                    private final Button aproveButton = new Button(bundle.getString("button.accept"));
                    private final Button rejectButton = new Button(bundle.getString("button.reject"));
                    private final HBox buttonsBox = new HBox(aproveButton, rejectButton);

                    {
                        //Css buttons
                        aproveButton.getStyleClass().add("button-Save");
                        rejectButton.getStyleClass().add("button-Delete");
                        
                        //Initialize buttons
                        aproveButton.setOnAction(event -> {
                            ManagementaprobationDto managementaprobation = getTableView().getItems().get(getIndex());
                            //Inicialize window comment
                            TextInputDialog dialog = new TextInputDialog();
                            dialog.setTitle(bundle.getString("addComment"));
                            dialog.setContentText(bundle.getString("optionalComment"));

                            //show window comment
                            Optional<String> result = dialog.showAndWait();

                            //Save comment
                            if (result.isPresent()) {
                                //Actualize comment
                                managementaprobation.setMgtaComment(result.get());
                                 //Actualize state Approved
                                managementaprobation.setMgtaState("Approved");

                                //actualize changes y refresh table
                                actualizeManagementaprovation(managementaprobation);
                                changeMgtState(managementaprobation.getMgtId());
                                tblV_Aprovements.refresh();
                            }
                            
                           
                        });

                        rejectButton.setOnAction(event -> {
                            ManagementaprobationDto managementaprobation = getTableView().getItems().get(getIndex());
                           // Actualize state Rejected
                                managementaprobation.setMgtaState("Rejected");
                                
                                //actualize changes y refresh table
                                actualizeManagementaprovation(managementaprobation);
                                changeMgtState(managementaprobation.getMgtId());
                                tblV_Aprovements.refresh();
                        });

                        buttonsBox.setSpacing(10);
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            ManagementaprobationDto managementaprobation = getTableView().getItems().get(getIndex());
                            //Show button only if it is Pending
                            if ("Pending".equals(managementaprobation.getMgtaState()) || "On hold".equals(managementaprobation.getMgtaState()) ) {
                                setGraphic(buttonsBox);
                            } else {
                                setGraphic(null);
                            }
                        }
                    }
                };
                return cell;
            }
        };

        tblC_Vote.setCellFactory(cellFactory);
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
     @FXML
    void onActionBtnNewAprovement(ActionEvent event) {
        if(AppContext.getInstance().get("Role").equals("Admin")){
            FlowController.getInstance().goView("CreateAprovementsView");
        }
        else{
            new Message().showModal(Alert.AlertType.WARNING, bundle.getString("accessDenied"), getStage(),
            bundle.getString("onlyAdminAccess"));
        }
    }

    public void actualizeManagementaprovation(ManagementaprobationDto managementaprobationDto){
        try {          
            Response response = this.managementaprobationService.saveManagementaprobations(managementaprobationDto);
            if (!response.getState()) {
                new Message().showModal(Alert.AlertType.ERROR, bundle.getString("actualizeAprobation"), getStage(), response.getMessage());
            } else {               
                new Message().showModal(Alert.AlertType.CONFIRMATION, bundle.getString("actualizeAprobation"), getStage(), bundle.getString("actualizeSuccessAprobation"));
            }
            } catch (Exception ex) {
                Logger.getLogger(AprovementsController.class.getName()).log(Level.SEVERE, bundle.getString("actualizeErrorAprobation"), ex);
                new Message().showModal(Alert.AlertType.ERROR, bundle.getString("actualizeAprobation"), getStage(), bundle.getString("actualizeErrorAprobation"));
            }   
    }
    public void changeMgtState(Long managementID){       
        ManagementDto managementDto=(ManagementDto) this.managementService.getManagement(managementID).getResult("Management");
        List<ManagementaprobationDto>aprobationsbyManagement=(List<ManagementaprobationDto>)this.managementaprobationService.getManagementaprobationsByManagement(managementID)
                .getResult("Managementaprobations");
        boolean allApprove=aprobationsbyManagement.stream().allMatch(mgta->mgta.getMgtaState().equals("Approved"));
        boolean someRejected=aprobationsbyManagement.stream().anyMatch(mgta->mgta.getMgtaState().equals("Rejected"));
        if(managementDto.getMgtState().equals("In approval") && allApprove){
            managementDto.setMgtState("In progress");
            this.managementService.saveManagement(managementDto);
        }
        if(managementDto.getMgtState().equals("In approval") && someRejected){
            managementDto.setMgtState("Rejected");
            this.managementService.saveManagement(managementDto);
        }
        
    }
}
