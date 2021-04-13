/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Navigator;

import assets.classes.AlertDialogs;
import assets.classes.statics;
import static assets.classes.statics.*;
import db.User;
import elbarbary.hospital.ElBarbaryHospital;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ahmed
 */
public class SideNavigatorController implements Initializable {

    @FXML
    private Label mainLabel;
    @FXML
    private ImageView imgview;
    @FXML
    private Button accountButton;

    Preferences prefs;
    @FXML
    private Button mainDataButton;
    @FXML
    private Button drugButton;
    @FXML
    private Button clincsButton;
    @FXML
    private Button storeButton;
    @FXML
    private Button admissionButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prefs = Preferences.userNodeForPackage(ElBarbaryHospital.class);
        mainLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            try {

                Parent login = FXMLLoader.load(getClass().getResource("/Navigator/Home.fxml"));
                login.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(statics.THEME, statics.DEFAULT_THEME) + ".css").toExternalForm());
                Scene sc = new Scene(login);
                Stage st = (Stage) mainLabel.getScene().getWindow();
                st.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icons/logo.png")));
                st.setTitle("ElBarbary Hospital");
                st.centerOnScreen();
                st.setScene(sc);
                st.show();
            } catch (IOException ex) {
                AlertDialogs.showErrors(ex);
            }
        });
        mainDataButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            if (User.canAccess("MainData")) {
                try {

                    Parent login = FXMLLoader.load(getClass().getResource("/screens/mainDataScreen/MainDataScreen.fxml"));
                    login.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(statics.THEME, statics.DEFAULT_THEME) + ".css").toExternalForm());
                    Scene sc = new Scene(login);
                    Stage st = (Stage) mainLabel.getScene().getWindow();
                    st.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icons/logo.png")));
                    st.setTitle("Elbarbary Hospital (البيانات الرئيسية)");
                    st.centerOnScreen();
                    st.setScene(sc);
                    st.show();
                } catch (IOException ex) {
                    AlertDialogs.showErrors(ex);
                }
            } else {
                AlertDialogs.permissionDenied();
            }

        });
        admissionButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            if (User.canAccess("Reception")) {
                try {

                    Parent login = FXMLLoader.load(getClass().getResource(ReceptionScreen));
                    login.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(statics.THEME, statics.DEFAULT_THEME) + ".css").toExternalForm());
                    Scene sc = new Scene(login);
                    Stage st = (Stage) mainLabel.getScene().getWindow();
                    st.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icons/logo.png")));
                    st.setTitle("Elbarbary Hospital (الاستقبال)");
                    st.centerOnScreen();
                    st.setScene(sc);
                    st.show();
                } catch (IOException ex) {
                    AlertDialogs.showErrors(ex);
                }
            } else {
                AlertDialogs.permissionDenied();
            }

        });
        storeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            if (User.canAccess("Store")) {
                try {

                    Parent login = FXMLLoader.load(getClass().getResource("/screens/store/StoreScreen.fxml"));
                    login.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(statics.THEME, statics.DEFAULT_THEME) + ".css").toExternalForm());
                    Scene sc = new Scene(login);
                    Stage st = (Stage) mainLabel.getScene().getWindow();
                    st.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icons/logo.png")));
                    st.setTitle("Elbarbary Hospital (المخازن)");
                    st.centerOnScreen();
                    st.setScene(sc);
                    st.show();
                } catch (IOException ex) {
                    AlertDialogs.showErrors(ex);
                }
            } else {
                AlertDialogs.permissionDenied();
            }

        });
        accountButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            if (User.canAccess("Accounts")) {
                try {

                    Parent login = FXMLLoader.load(getClass().getResource("/screens/accounts/AccountsScreen.fxml"));
                    login.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(statics.THEME, statics.DEFAULT_THEME) + ".css").toExternalForm());
                    Scene sc = new Scene(login);
                    Stage st = (Stage) mainLabel.getScene().getWindow();
                    st.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icons/logo.png")));
                    st.setTitle("Elbarbary Hospital (الحسابات)");
                    st.centerOnScreen();
                    st.setScene(sc);
                    st.show();
                } catch (IOException ex) {
                    AlertDialogs.showErrors(ex);
                }
            } else {
                AlertDialogs.permissionDenied();
            }

        });
        clincsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            if (User.canAccess("Hr")) {
                try {

                    Parent login = FXMLLoader.load(getClass().getResource("/screens/hr/HrScreen.fxml"));
                    login.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(statics.THEME, statics.DEFAULT_THEME) + ".css").toExternalForm());
                    Scene sc = new Scene(login);
                    Stage st = (Stage) mainLabel.getScene().getWindow();
                    st.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icons/logo.png")));
                    st.setTitle("Elbarbary Hospital (شؤون الموظفين)");
                    st.centerOnScreen();
                    st.setScene(sc);
                    st.show();
                } catch (IOException ex) {
                    AlertDialogs.showErrors(ex);
                }
            } else {
                AlertDialogs.permissionDenied();
            }
        });
        drugButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            if (User.canAccess("Drugs")) {
                try {

                    Parent login = FXMLLoader.load(getClass().getResource("/screens/drugs/DrugsScreen.fxml"));
                    login.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(statics.THEME, statics.DEFAULT_THEME) + ".css").toExternalForm());
                    Scene sc = new Scene(login);
                    Stage st = (Stage) mainLabel.getScene().getWindow();
                    st.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icons/logo.png")));
                    st.setTitle("Elbarbary Hospital (النفسية والادمان)");
                    st.centerOnScreen();
                    st.setScene(sc);
                    st.show();
                } catch (IOException ex) {
                    AlertDialogs.showErrors(ex);
                }
            } else {
                AlertDialogs.permissionDenied();
            }
        });
        
    }

}
