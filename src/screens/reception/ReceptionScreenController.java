/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.reception;

import assets.classes.AlertDialogs;
import static assets.classes.statics.*;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import db.User;
import elbarbary.hospital.ElBarbaryHospital;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.prefs.Preferences;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import screens.admission.AdmissionScreenController;

/**
 * FXML Controller class
 *
 * @author ahmed
 */
public class ReceptionScreenController implements Initializable {

    Preferences prefs;

    @FXML
    private JFXDrawer drawer;
    @FXML
    private JFXHamburger hamburg;

    @FXML
    private Tab admissionPane;
    @FXML
    private Tab dailyExpenses;
    @FXML
    private Tab orderShorts;
    @FXML
    private Tab collectYield;
    @FXML
    private Tab exportYield;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        //Background work                       
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    prefs = Preferences.userNodeForPackage(ElBarbaryHospital.class);
                                    configPanels();
                                    configDrawer();

                                } catch (Exception ex) {
                                    AlertDialogs.showErrors(ex);
                                } finally {
                                    latch.countDown();
                                }
                            }

                        });
                        latch.await();

                        return null;
                    }
                };

            }

            @Override
            protected void succeeded() {

                super.succeeded();
            }
        };
        service.start();

    }

    public void configPanels() throws Exception {
        FXMLLoader l4 = new FXMLLoader(getClass().getResource(NoPermission));
        if (User.canAccess("AdmissionScreen")) {
            l4 = new FXMLLoader(getClass().getResource(AdmissionScreen));
            admissionPane.setContent(l4.load());
            AdmissionScreenController admCont = l4.getController();
            admCont.setrecptionController(ReceptionScreenController.this);
        } else {
            admissionPane.setContent(l4.load());
        }

        FXMLLoader l1 = new FXMLLoader(getClass().getResource(NoPermission));
        if (User.canAccess("ReceptionScreenDailyExpenses")) {
            l1 = new FXMLLoader(getClass().getResource(ReceptionScreenDailyExpenses));
        }
        dailyExpenses.setContent(l1.load());

        FXMLLoader l2 = new FXMLLoader(getClass().getResource(NoPermission));
        if (User.canAccess("ReceptionScreenExportToAccounts")) {
            l2 = new FXMLLoader(getClass().getResource(ReceptionScreenExportToAccounts));
        }
        exportYield.setContent(l2.load());

        FXMLLoader l5 = new FXMLLoader(getClass().getResource(NoPermission));
        if (User.canAccess("ReceptionScreenShortsOrder")) {
            l5 = new FXMLLoader(getClass().getResource(ReceptionScreenShortsOrder));
        }
        orderShorts.setContent(l5.load());

        FXMLLoader c = new FXMLLoader(getClass().getResource(NoPermission));
        if (User.canAccess("ReceptionScreenGetYields")) {
            c = new FXMLLoader(getClass().getResource(ReceptionScreenGetYields));
            collectYield.setContent(c.load());
            ReceptionScreenGetYieldsController cont = c.getController();
            cont.setrecptionController(ReceptionScreenController.this);
        } else {
            collectYield.setContent(c.load());
        }

    }

    private void configDrawer() {
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

        } catch (Exception ex) {
            ex.printStackTrace();
            AlertDialogs.showErrors(ex);
        }
    }

}
