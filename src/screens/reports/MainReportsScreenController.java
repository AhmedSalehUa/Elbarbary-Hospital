/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.reports;

import assets.classes.AlertDialogs;
import static assets.classes.statics.DEFAULT_THEME;
import static assets.classes.statics.THEME;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import elbarbary.hospital.ElBarbaryHospital;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.prefs.Preferences;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import javax.imageio.ImageIO;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import screens.reports.assets.Baby;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class MainReportsScreenController implements Initializable {

    @FXML
    private JFXDrawer drawer;
    @FXML
    private JFXHamburger hamburg;
    Preferences prefs;
    @FXML
    private JFXTextField searchBaby;
    @FXML
    private TableView<Baby> babyTable;
    @FXML
    private TableColumn<Baby, String> babyTabDoctor;
    @FXML
    private TableColumn<Baby, String> babyTabDateBith;
    @FXML
    private TableColumn<Baby, String> babyTabCountry;
    @FXML
    private TableColumn<Baby, String> babyTabMother;
    @FXML
    private TableColumn<Baby, String> babyTabFather;
    @FXML
    private TableColumn<Baby, String> babyTabGender;
    @FXML
    private TableColumn<Baby, String> babyTabBaby;
    @FXML
    private TableColumn<Baby, String> babyTabId;
    @FXML
    private Label babyId;
    @FXML
    private TextField babyName;
    @FXML
    private JFXDatePicker babyDateOfBirth;
    @FXML
    private TextField babyGender;
    @FXML
    private TextField babyFather;
    @FXML
    private TextField babyMother;
    @FXML
    private TextField babyCountury;
    @FXML
    private TextField babyDoctor;
    @FXML
    private ProgressIndicator progress;
    @FXML
    private Button formNew;
    @FXML
    private Button formDelete;
    @FXML
    private Button formEdite;
    @FXML
    private Button formAdd;
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        babyDateOfBirth.setConverter(new StringConverter<LocalDate>() {

            @Override
            public String toString(LocalDate localDate) {
                if (localDate == null) {
                    return "";
                }
                return format.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString) {
                if (dateString == null || dateString.trim().isEmpty()) {
                    return null;
                }
                return LocalDate.parse(dateString, format);
            }
        });
        progress.setVisible(true);
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
                                    clear();
                                    intialColumn();
                                    getData();
                                    fillCombo();
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
                progress.setVisible(false);
                super.succeeded();
            }
        };
        service.start();

        babyDateOfBirth.setValue(LocalDate.now());
        babyTable.setOnMouseClicked((e) -> {
            if (babyTable.getSelectionModel().getSelectedIndex() == -1) {
            } else {
                formNew.setDisable(false);

                formDelete.setDisable(false);

                formEdite.setDisable(false);

                formAdd.setDisable(true);

                Baby selected = babyTable.getSelectionModel().getSelectedItem();
                babyId.setText(Integer.toString(selected.getId()));
                babyName.setText(selected.getName());
                babyFather.setText(selected.getFather());
                babyMother.setText(selected.getMother());
                babyGender.setText(selected.getGender());
                babyCountury.setText(selected.getCountry());
                babyDoctor.setText(selected.getDoctor());
                babyDateOfBirth.setValue(LocalDate.parse(selected.getDateOfBirth()));
            }
        });
    }

    private void intialColumn() {
        babyTabDoctor.setCellValueFactory(new PropertyValueFactory<>("doctor"));

        babyTabDateBith.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));

        babyTabCountry.setCellValueFactory(new PropertyValueFactory<>("Country"));

        babyTabMother.setCellValueFactory(new PropertyValueFactory<>("mother"));

        babyTabFather.setCellValueFactory(new PropertyValueFactory<>("father"));

        babyTabGender.setCellValueFactory(new PropertyValueFactory<>("gender"));

        babyTabBaby.setCellValueFactory(new PropertyValueFactory<>("name"));

        babyTabId.setCellValueFactory(new PropertyValueFactory<>("id"));

    }

    private void clear() {
        getAutoNum();
        formNew.setDisable(true);

        formDelete.setDisable(true);

        formEdite.setDisable(true);

        formAdd.setDisable(false);

        babyName.setText("");
        babyFather.setText("");
        babyMother.setText("");
        babyGender.setText("");
        babyCountury.setText("");
        babyDoctor.setText("");
        babyDateOfBirth.setValue(LocalDate.now());
    }

    private void getAutoNum() {
        try {
            babyId.setText(Baby.getAutoNum());
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }
    ObservableList<Baby> items;

    private void getData() {
        try {
            babyTable.setItems(Baby.getData());
            items = babyTable.getItems();
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    private void fillCombo() {

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

    @FXML
    private void searchBaby(KeyEvent event) {
        FilteredList<Baby> filteredData = new FilteredList<>(items, p -> true);

        filteredData.setPredicate(pa -> {

            if (searchBaby.getText() == null || searchBaby.getText().isEmpty()) {
                return true;
            }

            String lowerCaseFilter = searchBaby.getText().toLowerCase();

            return (pa.getName().contains(lowerCaseFilter)
                    || pa.getDateOfBirth().contains(lowerCaseFilter)
                    || pa.getDoctor().contains(lowerCaseFilter)
                    || pa.getFather().contains(lowerCaseFilter)
                    || pa.getMother().contains(lowerCaseFilter));

        });

        SortedList<Baby> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(babyTable.comparatorProperty()
        );
        babyTable.setItems(sortedData);
    }

    @FXML
    private void formNew(ActionEvent event) {
        clear();
    }

    @FXML
    private void formDelete(ActionEvent event) {
        progress.setVisible(true);
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
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Deleting  Baby");
                                    alert.setHeaderText("سيتم حذف المولود ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        Baby ba = new Baby();
                                        ba.setId(Integer.parseInt(babyId.getText()));
                                        ba.Delete();
                                    }
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
                progress.setVisible(false);
                clear();
                getData();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void formEdite(ActionEvent event) {
        progress.setVisible(true);
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
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Editing  Baby");
                                    alert.setHeaderText("سيتم تعديل المولود ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        Baby ba = new Baby();
                                        ba.setId(Integer.parseInt(babyId.getText()));
                                        ba.setName(babyName.getText());
                                        ba.setGender(babyGender.getText());
                                        ba.setFather(babyFather.getText());
                                        ba.setMother(babyMother.getText());
                                        ba.setCountry(babyCountury.getText());
                                        ba.setDateOfBirth(babyDateOfBirth.getValue().format(format));
                                        ba.setDoctor(babyDoctor.getText());
                                        ba.Edite();
                                    }
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
                progress.setVisible(false);
                clear();
                getData();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void formAdd(ActionEvent event) {
        progress.setVisible(true);
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
                                    Baby ba = new Baby();
                                    ba.setId(Integer.parseInt(babyId.getText()));
                                    ba.setName(babyName.getText());
                                    ba.setGender(babyGender.getText());
                                    ba.setFather(babyFather.getText());
                                    ba.setMother(babyMother.getText());
                                    ba.setCountry(babyCountury.getText());
                                    ba.setDateOfBirth(babyDateOfBirth.getValue().format(format));
                                    ba.setDoctor(babyDoctor.getText());
                                    ba.Add();
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
                progress.setVisible(false);
                clear();
                getData();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void print(MouseEvent event) {
        if (babyTable.getSelectionModel().getSelectedIndex() == -1) {
            AlertDialogs.showError("اختار الطفل اولا");

        } else {
            progress.setVisible(true);
            Service<Void> service = new Service<Void>() {
                @Override
                protected Task<Void> createTask() {
                    return new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {

                            try {
                                HashMap hash = new HashMap();
                                BufferedImage image = ImageIO.read(getClass().getResource("/assets/icons/logo.png"));
                                hash.put("logo", image); 
                                 hash.put("baby_id", Integer.toString(babyTable.getSelectionModel().getSelectedItem().getId())); 
                                InputStream a = getClass().getResourceAsStream("/screens/reports/reports/babyRep.jrxml");

                                JasperDesign design = JRXmlLoader.load(a);
                                JasperReport jasperreport = JasperCompileManager.compileReport(design);
                                JasperPrint jasperprint = JasperFillManager.fillReport(jasperreport, hash, db.get.getReportCon());
                                JasperViewer.viewReport(jasperprint, false);
                            } catch (Exception ex) {
                                AlertDialogs.showErrors(ex);
                            }

                            return null;
                        }
                    };
                }

                @Override
                protected void succeeded() {
                    progress.setVisible(false);
                    super.succeeded();
                }
            };
            service.start();
        }
    }
}
