/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.settings;

import assets.classes.AlertDialogs;
import static assets.classes.statics.*;
import db.User;
import elbarbary.hospital.ElBarbaryHospital;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 * FXML Controller class
 *
 * @author ahmed
 */
public class SettingsController implements Initializable {

    @FXML
    private ComboBox<String> colorSelection;

    Preferences prefs;
    @FXML
    private ComboBox<String> database;
    @FXML
    private TextField sdkLocation;
    @FXML
    private TextField dbLocation;
    @FXML
    private TextField ext;
    @FXML
    private TextField command;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prefs = Preferences.userNodeForPackage(ElBarbaryHospital.class);

        setdata();

    }

    private void setdata() {
        sdkLocation.setText(prefs.get(PYTHON_PATH, PYTHON_PATH_DEFAULT));
        dbLocation.setText(prefs.get(DATABASE_LOCATION, DATABASE_LOCATION_DEFAULT));
        ext.setText(prefs.get(EXTEND, EXTEND_DEFAULT));
        command.setText(prefs.get(COMMAND, COMMAND_DEFAULT));
        colorSelection.getItems().add("lightMode");
        colorSelection.getItems().add("darkMode");
        colorSelection.getSelectionModel().select(prefs.get(THEME, DEFAULT_THEME));

        database.getItems().add("mysql");
        database.getItems().add("sqlite");
        database.getSelectionModel().select(prefs.get(DATABASE_TYPE, DATABASE_TYPE_DEFAULT));
    }

    @FXML
    private void apply(ActionEvent event) {
        prefs.put(THEME, colorSelection.getSelectionModel().getSelectedItem());
        prefs.put(DATABASE_TYPE, database.getSelectionModel().getSelectedItem());

        prefs.put(EXTEND, ext.getText());
        prefs.put(COMMAND, command.getText());
        prefs.put(PYTHON_PATH, sdkLocation.getText());
        prefs.put(DATABASE_LOCATION, dbLocation.getText()); 
        home(event);
    }

    private void home(ActionEvent e) {

        try {

            Parent mainMember = FXMLLoader.load(getClass().getResource("/Navigator/Home.fxml"));

            mainMember.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(THEME, DEFAULT_THEME) + ".css").toExternalForm());
            Scene sc = new Scene(mainMember);
            Stage st = (Stage) ((Node) e.getSource()).getScene().getWindow();
            st.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icons/logo.png")));
            st.setTitle("ElBarbary Hospital");
            st.setY(0);
            st.setX(0);
            st.setScene(sc);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @FXML
    private void selectLocation(ActionEvent event) {
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        DirectoryChooser chooser = new DirectoryChooser();
        File showDialog = chooser.showDialog(st);
        if (showDialog != null) {
            sdkLocation.setText(showDialog.getAbsolutePath());
        }
    }

    @FXML
    private void selectDBLocation(ActionEvent event) {
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FileChooser chooser = new FileChooser();
        File showDialog = chooser.showOpenDialog(st);
        if (showDialog != null) {
            dbLocation.setText(showDialog.getAbsolutePath());
        }
    }

}
