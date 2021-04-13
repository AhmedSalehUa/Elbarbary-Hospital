/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.store;

import assets.animation.ZoomInLeft;
import assets.animation.ZoomInRight;
import assets.classes.AlertDialogs;
import static assets.classes.statics.*;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import db.User; 
import elbarbary.hospital.ElBarbaryHospital;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences; 
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author ahmed
 */
public class StoreScreenController implements Initializable {

    @FXML
    private BorderPane borderpane;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private JFXHamburger hamburg;
    Preferences prefs;
    @FXML
    private Button stores;
    @FXML
    private Button storeCpmpany;
    @FXML
    private Button storeTransaction;
    @FXML
    private Button storeShorts;
    @FXML
    private Button storeInvoice;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prefs = Preferences.userNodeForPackage(ElBarbaryHospital.class);

        new ZoomInRight(stores).play();
        new ZoomInLeft(storeShorts).play();
        new ZoomInRight(storeTransaction).play();
        new ZoomInLeft(storeCpmpany).play();
        new ZoomInRight(storeInvoice).play();
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
            stores.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                try {

                    stores.setStyle(" -fx-background-color: -mainColor-dark; ");
                    storeCpmpany.setStyle(" -fx-background-color: -mainColor-light; ");
                    storeInvoice.setStyle(" -fx-background-color: -mainColor-light; ");
                    storeTransaction.setStyle(" -fx-background-color: -mainColor-light; ");
                    storeShorts.setStyle(" -fx-background-color: -mainColor-light; ");
                    Parent node = FXMLLoader.load(getClass().getResource(NoPermission));
                    if (User.canAccess("StoreScreenStores")) {
                        node = FXMLLoader.load(getClass().getResource("StoreScreenStores.fxml"));
                    }
                    borderpane.setCenter(node);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    AlertDialogs.showErrors(ex);
                }
            });
            storeCpmpany.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                try {
                    storeCpmpany.setStyle(" -fx-background-color: -mainColor-dark; ");
                    stores.setStyle(" -fx-background-color: -mainColor-light; ");
                    storeInvoice.setStyle(" -fx-background-color: -mainColor-light; ");
                    storeTransaction.setStyle(" -fx-background-color: -mainColor-light; ");
                    storeShorts.setStyle(" -fx-background-color: -mainColor-light; ");
                    Parent node = FXMLLoader.load(getClass().getResource(NoPermission));
                    if (User.canAccess("StoreScreenCompany")) {
                        node = FXMLLoader.load(getClass().getResource("StoreScreenCompany.fxml"));
                    }
                    borderpane.setCenter(node);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    AlertDialogs.showErrors(ex);
                }
            });
            storeShorts.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                try {
                    storeShorts.setStyle(" -fx-background-color: -mainColor-dark; ");
                    storeCpmpany.setStyle(" -fx-background-color: -mainColor-light; ");
                    stores.setStyle(" -fx-background-color: -mainColor-light; ");
                    storeInvoice.setStyle(" -fx-background-color: -mainColor-light; ");
                    storeTransaction.setStyle(" -fx-background-color: -mainColor-light; ");
                    Parent node = FXMLLoader.load(getClass().getResource(NoPermission));
                    if (User.canAccess("StoreScreenShorts")) {
                        node = FXMLLoader.load(getClass().getResource("StoreScreenShorts.fxml"));
                    }
                    borderpane.setCenter(node);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    AlertDialogs.showErrors(ex);
                }
            });
            storeInvoice.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                try {
                    storeInvoice.setStyle(" -fx-background-color: -mainColor-dark; ");
                    storeShorts.setStyle(" -fx-background-color: -mainColor-light; ");
                    storeCpmpany.setStyle(" -fx-background-color: -mainColor-light; ");
                    stores.setStyle(" -fx-background-color: -mainColor-light; ");
                    storeTransaction.setStyle(" -fx-background-color: -mainColor-light; ");
                    Parent node = FXMLLoader.load(getClass().getResource(NoPermission));
                    if (User.canAccess("StoreScreenInvoices")) {
                        node = FXMLLoader.load(getClass().getResource("StoreScreenInvoices.fxml"));
                    }
                    borderpane.setCenter(node);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    AlertDialogs.showErrors(ex);
                }
            });
            storeTransaction.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                try {
                    storeTransaction.setStyle(" -fx-background-color: -mainColor-dark; ");
                    storeInvoice.setStyle(" -fx-background-color: -mainColor-light; ");
                    storeShorts.setStyle(" -fx-background-color: -mainColor-light; ");
                    storeCpmpany.setStyle(" -fx-background-color: -mainColor-light; ");
                    stores.setStyle(" -fx-background-color: -mainColor-light; ");
                    Parent node = FXMLLoader.load(getClass().getResource(NoPermission));
                    if (User.canAccess("StoreScreenTransactions")) {
                        node = FXMLLoader.load(getClass().getResource("StoreScreenTransactions.fxml"));
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
