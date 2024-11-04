/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.sigeceuna.controller;

import java.net.URL;
import java.util.ResourceBundle;
iimport javafx.event.ActionEvent;
import javafx.fxml.FXML;
mport javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
/**
 * FXML Controller class
 *
 * @author altam
 */
public class WebCamController implements Initializable {


    @FXML
    private ImageView imgPhoto;
    @FXML
    private Button btnPhoto;
    @FXML
    private ImageView imgTake;
    @FXML
    private Button btnCancel;
    @FXML
    private ImageView imgCancelTem;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void onActionBtnTakePhoto(ActionEvent event) {
    }

    @FXML
    private void onActionBtnCancel(ActionEvent event) {
    }

}
