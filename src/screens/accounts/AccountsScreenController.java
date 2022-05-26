/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.accounts;

import assets.classes.AlertDialogs;
import static assets.classes.statics.*;
import static assets.classes.statics.DEFAULT_THEME;
import static assets.classes.statics.THEME;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import db.User;
import elbarbary.hospital.ElBarbaryHospital;
import java.io.IOException;
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
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class AccountsScreenController implements Initializable {

    Preferences prefs;

    @FXML
    private JFXDrawer drawer;
    @FXML
    private JFXHamburger hamburg;

    @FXML
    private BorderPane borderpane;
    @FXML
    private Button accountReports;
    @FXML
    private Button accountMedicineCompany;
    @FXML
    private Button accountContracts;
    @FXML
    private Button accountExpenses;
    @FXML
    private Button accountYields;
    @FXML
    private Button accounts;
    @FXML
    private Button accountTransactions;

    /**
     * Initializes the controller class.
     */
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
        accounts.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {

            try {
                accounts.setStyle(" -fx-background-color: -mainColor-dark; ");
                accountContracts.setStyle(" -fx-background-color: -mainColor-light; ");
                accountExpenses.setStyle(" -fx-background-color: -mainColor-light; ");
                accountTransactions.setStyle(" -fx-background-color: -mainColor-light; ");

                accountMedicineCompany.setStyle(" -fx-background-color: -mainColor-light; ");
                accountReports.setStyle(" -fx-background-color: -mainColor-light; ");
                accountYields.setStyle(" -fx-background-color: -mainColor-light; ");
                Parent node = FXMLLoader.load(getClass().getResource(NoPermission));
                if (User.canAccess("AllAccounts")) {
                    node = FXMLLoader.load(getClass().getResource("AccountsScreenAccounts.fxml"));
                }
                borderpane.setCenter(node);
            } catch (IOException ex) {

                AlertDialogs.showErrors(ex);
            }

        });
        accountExpenses.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {

            try {
                accountExpenses.setStyle(" -fx-background-color: -mainColor-dark; ");
                accountContracts.setStyle(" -fx-background-color: -mainColor-light; ");
                accounts.setStyle(" -fx-background-color: -mainColor-light; ");
                accountMedicineCompany.setStyle(" -fx-background-color: -mainColor-light; ");
                accountTransactions.setStyle(" -fx-background-color: -mainColor-light; ");

                accountReports.setStyle(" -fx-background-color: -mainColor-light; ");
                accountYields.setStyle(" -fx-background-color: -mainColor-light; ");
                Parent node = FXMLLoader.load(getClass().getResource(NoPermission));
                if (User.canAccess("accountExpenses")) {
                    node = FXMLLoader.load(getClass().getResource("AccountsScreenExpenses.fxml"));
                }
                borderpane.setCenter(node);
            } catch (IOException ex) {

                AlertDialogs.showErrors(ex);
            }

        });
        accountYields.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {

            try {
                accountYields.setStyle(" -fx-background-color: -mainColor-dark; ");
                accountContracts.setStyle(" -fx-background-color: -mainColor-light; ");
                accounts.setStyle(" -fx-background-color: -mainColor-light; ");
                accountTransactions.setStyle(" -fx-background-color: -mainColor-light; ");

                accountMedicineCompany.setStyle(" -fx-background-color: -mainColor-light; ");
                accountReports.setStyle(" -fx-background-color: -mainColor-light; ");
                accountExpenses.setStyle(" -fx-background-color: -mainColor-light; ");
                Parent node = FXMLLoader.load(getClass().getResource(NoPermission));
                if (User.canAccess("accountYields")) {
                    node = FXMLLoader.load(getClass().getResource(AccountsScreenYields));
                }
                borderpane.setCenter(node);
            } catch (IOException ex) {

                AlertDialogs.showErrors(ex);
            }

        });
        accountContracts.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {

            try {
                accountContracts.setStyle(" -fx-background-color: -mainColor-dark; ");
                accountYields.setStyle(" -fx-background-color: -mainColor-light; ");
                accountTransactions.setStyle(" -fx-background-color: -mainColor-light; ");

                accounts.setStyle(" -fx-background-color: -mainColor-light; ");
                accountMedicineCompany.setStyle(" -fx-background-color: -mainColor-light; ");
                accountReports.setStyle(" -fx-background-color: -mainColor-light; ");
                accountExpenses.setStyle(" -fx-background-color: -mainColor-light; ");
                Parent node = FXMLLoader.load(getClass().getResource(NoPermission));
                if (User.canAccess("accountContracts")) {
                    node = FXMLLoader.load(getClass().getResource(AccountsScreenContract));
                }
                borderpane.setCenter(node);
            } catch (IOException ex) {

                AlertDialogs.showErrors(ex);
            }

        });
        accountMedicineCompany.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {

            try {
                accountMedicineCompany.setStyle(" -fx-background-color: -mainColor-dark; ");
                accountContracts.setStyle(" -fx-background-color: -mainColor-light; ");
                accounts.setStyle(" -fx-background-color: -mainColor-light; ");
                accountYields.setStyle(" -fx-background-color: -mainColor-light; ");
                accountReports.setStyle(" -fx-background-color: -mainColor-light; ");
                accountTransactions.setStyle(" -fx-background-color: -mainColor-light; ");

                accountExpenses.setStyle(" -fx-background-color: -mainColor-light; ");
                Parent node = FXMLLoader.load(getClass().getResource(NoPermission));
                if (User.canAccess("accountMedicineCompany")) {
                    node = FXMLLoader.load(getClass().getResource(AccountsScreenCompany));
                }
                borderpane.setCenter(node);
            } catch (IOException ex) {

                AlertDialogs.showErrors(ex);
            }

        });
        accountReports.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {

            try {
                accountReports.setStyle(" -fx-background-color: -mainColor-dark; ");
                accountContracts.setStyle(" -fx-background-color: -mainColor-light; ");
                accounts.setStyle(" -fx-background-color: -mainColor-light; ");
                accountYields.setStyle(" -fx-background-color: -mainColor-light; ");
                accountTransactions.setStyle(" -fx-background-color: -mainColor-light; ");

                accountMedicineCompany.setStyle(" -fx-background-color: -mainColor-light; ");
                accountExpenses.setStyle(" -fx-background-color: -mainColor-light; ");
                Parent node = FXMLLoader.load(getClass().getResource(NoPermission));
                if (User.canAccess("AccountsScreenReports")) {
                    node = FXMLLoader.load(getClass().getResource(AccountsScreenReport));
                }
                borderpane.setCenter(node);
            } catch (IOException ex) {

                AlertDialogs.showErrors(ex);
            }

        });
        accountTransactions.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {

            try {
                accountTransactions.setStyle(" -fx-background-color: -mainColor-dark; ");
                accountContracts.setStyle(" -fx-background-color: -mainColor-light; ");
                accountReports.setStyle(" -fx-background-color: -mainColor-light; ");
                accounts.setStyle(" -fx-background-color: -mainColor-light; ");
                accountYields.setStyle(" -fx-background-color: -mainColor-light; ");
                accountMedicineCompany.setStyle(" -fx-background-color: -mainColor-light; ");
                accountExpenses.setStyle(" -fx-background-color: -mainColor-light; ");
                Parent node = FXMLLoader.load(getClass().getResource(NoPermission));
                if (User.canAccess("accountTransactions")) {
                    node = FXMLLoader.load(getClass().getResource("/screens/drugs/DrugsScreenMoneyTransactions.fxml"));
                }
                borderpane.setCenter(node);
            } catch (IOException ex) {

                AlertDialogs.showErrors(ex);
            }

        });
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
            AlertDialogs.showErrors(ex);
        }
    }
}
