/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.mainDataScreen;

import assets.animation.ZoomInLeft;
import assets.animation.ZoomInRight;
import assets.classes.AlertDialogs;
import assets.classes.statics;
import static assets.classes.statics.*;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * FXML Controller class
 *
 * @author ahmed
 */
public class MainDataScreenController implements Initializable {

    @FXML
    private BorderPane borderpane;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private JFXHamburger hamburg;
    Preferences prefs;
    @FXML
    private Button patient;
    @FXML
    private Button doctors;
    @FXML
    private Button medicine;
    @FXML
    private Button clincs;
    @FXML
    private Button contracts;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prefs = Preferences.userNodeForPackage(ElBarbaryHospital.class);

        new ZoomInRight(doctors).play();
        new ZoomInLeft(medicine).play();
        new ZoomInRight(clincs).play();
        new ZoomInLeft(contracts).play();

        try {
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/Navigator/SideNavigator.fxml"));

            anchorPane.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(THEME, DEFAULT_THEME) + ".css").toExternalForm());

            drawer.setSidePane(anchorPane);

            double drawerx = drawer.getLayoutX();
            double drawery = drawer.getLayoutY();
            drawer.setLayoutX(drawerx + 250);
            drawer.setLayoutY(drawery);
            drawer.setVisible(false);
            drawer.setMaxWidth(0);

            drawer.setOnDrawerOpening(event
                    -> {
                drawer.setLayoutX(drawerx);
                drawer.setLayoutY(drawery);
                drawer.setMaxWidth(250);
            });

            drawer.setOnDrawerClosed(event
                    -> {
                drawer.setLayoutX(drawerx + 250);
                drawer.setLayoutY(drawery);
                drawer.setVisible(false);
                drawer.setMaxWidth(0);
            });
            for (Node node : anchorPane.getChildren()) {

                node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {

                    changeView(node.getId(), e);
                });

            }
            HamburgerBackArrowBasicTransition nav = new HamburgerBackArrowBasicTransition(hamburg);
            nav.setRate(nav.getRate() * -1);
            nav.play();
            hamburg.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                nav.setRate(nav.getRate() * -1);
                nav.play();
                drawer.setVisible(true);
                if (drawer.isOpened()) {
                    drawer.close();
                } else {
                    drawer.open();
                }
            });
            patient.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                try {
                    System.err.println(prefs.get(THEME, DEFAULT_THEME));
                    patient.setStyle(" -fx-background-color: -mainColor-dark; ");
                    doctors.setStyle(" -fx-background-color: -mainColor-light; ");
                    medicine.setStyle(" -fx-background-color: -mainColor-light; ");
                    clincs.setStyle(" -fx-background-color: -mainColor-light; ");
                    Parent node = FXMLLoader.load(getClass().getResource(NoPermission));
                    if (User.canAccess("MainDataScreenPatient")) {
                        node = FXMLLoader.load(getClass().getResource("MainDataScreenPatient.fxml"));
                    }
                    borderpane.setCenter(node);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    AlertDialogs.showErrors(ex);
                }
            });
            doctors.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                try {
                    doctors.setStyle(" -fx-background-color: -mainColor-dark; ");
                    patient.setStyle(" -fx-background-color: -mainColor-light; ");
                    medicine.setStyle(" -fx-background-color: -mainColor-light; ");
                    clincs.setStyle(" -fx-background-color: -mainColor-light; ");
                    contracts.setStyle(" -fx-background-color: -mainColor-light; ");
                    Parent node = FXMLLoader.load(getClass().getResource(NoPermission));
                    if (User.canAccess("MainDataScreenDoctors")) {
                        node = FXMLLoader.load(getClass().getResource("MainDataScreenDoctors.fxml"));
                    }
                    borderpane.setCenter(node);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    AlertDialogs.showErrors(ex);
                }
            });
            medicine.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                try {
                    medicine.setStyle(" -fx-background-color: -mainColor-dark; ");
                    patient.setStyle(" -fx-background-color: -mainColor-light; ");
                    doctors.setStyle(" -fx-background-color: -mainColor-light; ");
                    clincs.setStyle(" -fx-background-color: -mainColor-light; ");
                    contracts.setStyle(" -fx-background-color: -mainColor-light; ");
                    Parent node = FXMLLoader.load(getClass().getResource(NoPermission));
                    if (User.canAccess("MainDataScreenMedicine")) {
                        node = FXMLLoader.load(getClass().getResource("MainDataScreenMedicine.fxml"));
                    }
                    borderpane.setCenter(node);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    AlertDialogs.showErrors(ex);
                }
            });
            clincs.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                try {
                    clincs.setStyle(" -fx-background-color: -mainColor-dark; ");
                    patient.setStyle(" -fx-background-color: -mainColor-light; ");
                    doctors.setStyle(" -fx-background-color: -mainColor-light; ");
                    medicine.setStyle(" -fx-background-color: -mainColor-light; ");
                    contracts.setStyle(" -fx-background-color: -mainColor-light; ");
                    Parent node = FXMLLoader.load(getClass().getResource(NoPermission));
                    if (User.canAccess("MainDataScreenClincs")) {
                        node = FXMLLoader.load(getClass().getResource("MainDataScreenClincs.fxml"));
                    }
                    borderpane.setCenter(node);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    AlertDialogs.showErrors(ex);
                }
            });
            contracts.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                try {
                    contracts.setStyle(" -fx-background-color: -mainColor-dark; ");
                    patient.setStyle(" -fx-background-color: -mainColor-light; ");
                    doctors.setStyle(" -fx-background-color: -mainColor-light; ");
                    medicine.setStyle(" -fx-background-color: -mainColor-light; ");
                    clincs.setStyle(" -fx-background-color: -mainColor-light; ");
                    Parent node = FXMLLoader.load(getClass().getResource(NoPermission));
                    if (User.canAccess("MainDataScreenContract")) {
                        node = FXMLLoader.load(getClass().getResource("MainDataScreenContract.fxml"));
                    }
                    borderpane.setCenter(node);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    AlertDialogs.showErrors(ex);
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
            AlertDialogs.showErrors(ex);

        }
    }

    private void changeView(String id, MouseEvent e) {

    }

}
