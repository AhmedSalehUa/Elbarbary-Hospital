/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elbarbary.hospital;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class SplashScreenController implements Initializable {

    @FXML
    private ProgressBar prog;
    @FXML
    private Label num;

 
    
    
    public static Label label;
    
    
    
    public static ProgressBar statProgressBar;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
       
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       label = num ;
       statProgressBar = prog;
    }    
}
