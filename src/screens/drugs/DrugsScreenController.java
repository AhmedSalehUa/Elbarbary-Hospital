package screens.drugs;

import assets.animation.ZoomInLeft;
import assets.animation.ZoomInRight;
import assets.classes.AlertDialogs;
import assets.classes.statics;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.events.JFXDrawerEvent;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class DrugsScreenController
        implements Initializable {

    @FXML
    private BorderPane borderpane;
    @FXML
    private Button dailyExpenses;
    @FXML
    private Button patient;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private JFXHamburger hamburg;
    Preferences prefs;

    public void initialize(URL url, ResourceBundle rb) {
        this.prefs = Preferences.userNodeForPackage(ElBarbaryHospital.class);

        (new ZoomInLeft((Node) this.dailyExpenses)).play();
        (new ZoomInRight((Node) this.patient)).play();

        Service<Void> service = new Service<Void>() {
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    protected Void call() throws Exception {
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(new Runnable() {
                            public void run() {
                                try {
                                    DrugsScreenController.this.configDrawer();
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

            protected void succeeded() {
                super.succeeded();
            }
        };
        service.start();

        try {
            this.patient.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                try {
                    this.patient.setStyle(" -fx-background-color: -mainColor-dark; ");

                    this.dailyExpenses.setStyle(" -fx-background-color: -mainColor-light; ");
                    Parent node = (Parent) FXMLLoader.load(getClass().getResource(statics.NoPermission));
                    if (User.canAccess("DrugsScreenPatient")) {
                        node = (Parent) FXMLLoader.load(getClass().getResource("DrugsScreenPatient.fxml"));
                    }
                    this.borderpane.setCenter((Node) node);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    AlertDialogs.showErrors(ex);
                }
            });
            this.dailyExpenses.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                try {
                    this.dailyExpenses.setStyle(" -fx-background-color: -mainColor-dark; ");

                    this.patient.setStyle(" -fx-background-color: -mainColor-light; ");
                    Parent node = (Parent) FXMLLoader.load(getClass().getResource(statics.NoPermission));
                    if (User.canAccess("DrugsScreenPatient")) {
                        node = (Parent) FXMLLoader.load(getClass().getResource("DrugsScreenPatienrAccounts.fxml"));
                    }
                    this.borderpane.setCenter((Node) node);
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

    private void configDrawer() throws Exception {
        AnchorPane anchorPane = (AnchorPane) FXMLLoader.load(getClass().getResource("/Navigator/SideNavigator.fxml"));

        anchorPane.getStylesheets().add(getClass().getResource("/assets/styles/" + this.prefs.get(statics.THEME, statics.DEFAULT_THEME) + ".css").toExternalForm());

        this.drawer.setSidePane(new Node[]{(Node) anchorPane});

        double drawerx = this.drawer.getLayoutX();
        double drawery = this.drawer.getLayoutY();
        this.drawer.setLayoutX(drawerx + 250.0D);
        this.drawer.setLayoutY(drawery);
        this.drawer.setVisible(false);
        this.drawer.setMaxWidth(0.0D);

        this.drawer.setOnDrawerOpening(event -> {
            this.drawer.setLayoutX(drawerx);

            this.drawer.setLayoutY(drawery);

            this.drawer.setMaxWidth(250.0D);
        });
        this.drawer.setOnDrawerClosed(event -> {
            this.drawer.setLayoutX(drawerx + 250.0D);

            this.drawer.setLayoutY(drawery);

            this.drawer.setVisible(false);
            this.drawer.setMaxWidth(0.0D);
        });
        HamburgerBackArrowBasicTransition nav = new HamburgerBackArrowBasicTransition(this.hamburg);
        nav.setRate(nav.getRate() * -1.0D);
        nav.play();
        this.hamburg.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            nav.setRate(nav.getRate() * -1.0D);
            nav.play();
            this.drawer.setVisible(true);
            if (this.drawer.isOpened()) {
                this.drawer.close();
            } else {
                this.drawer.open();
            }
        });
    }
}
