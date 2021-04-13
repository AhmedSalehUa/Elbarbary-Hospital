/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.hr;

import assets.animation.ZoomInLeft;
import assets.animation.ZoomInRight;
import assets.classes.AlertDialogs;
import static assets.classes.statics.* ;
import elbarbary.hospital.ElBarbaryHospital;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class HrScreenMainReportsController implements Initializable {
 Preferences prefs;
    @FXML
    private Button individualReport;
    @FXML
    private Button allSalaryxReport;
    @FXML
    private Button clacAttend;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) { prefs = Preferences.userNodeForPackage(ElBarbaryHospital.class);
       new ZoomInRight(individualReport).play();
      
        new ZoomInRight(allSalaryxReport).play();
        new ZoomInLeft(clacAttend).play();
       
    }    

    @FXML
    private void individualReport(ActionEvent event) {
    }

    @FXML
    private void allSalaryxReport(ActionEvent event) {
    }

    @FXML
    private void clacAttend(ActionEvent event) {
         try {
            Parent mainMember = FXMLLoader.load(getClass().getResource(HrScreenCalcAttend));
            mainMember.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(THEME, DEFAULT_THEME) + ".css").toExternalForm());
            Scene sc = new Scene(mainMember);
            Stage st = new Stage();
            st.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icons/logo.png")));
            st.setTitle("Elbarbary Hospital (حساب الحضور والانصراف)");
            st.setScene(sc);
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            st.setX((screenBounds.getWidth() - 1360) / 2);
            st.setY((screenBounds.getHeight() - 760) / 2);
            st.show();
        } catch (IOException ex) {
            AlertDialogs.showErrors(ex);
        }
    }
    
}
