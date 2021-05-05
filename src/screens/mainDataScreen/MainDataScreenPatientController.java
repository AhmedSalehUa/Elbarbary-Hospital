/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.mainDataScreen;

import assets.classes.AlertDialogs;
import static assets.classes.statics.DEFAULT_THEME;
import static assets.classes.statics.THEME;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javax.imageio.ImageIO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import screens.mainDataScreen.assets.Contract;
import screens.mainDataScreen.assets.Doctors;
import screens.mainDataScreen.assets.Patients;
import screens.mainDataScreen.assets.PatientsEscort;
import screens.mainDataScreen.assets.TransferOrganization;

/**
 * FXML Controller class
 *
 * @author ahmed
 */
public class MainDataScreenPatientController implements Initializable {

    @FXML
    private TableView<Patients> patientTable;
    @FXML
    private TableColumn<Patients, String> patientTabTele2;
    @FXML
    private TableColumn<Patients, String> patientTabTele1;
    @FXML
    private TableColumn<Patients, String> patientTabName;
    @FXML
    private TableColumn<Patients, String> patientTabId;
    @FXML
    private TableColumn<Patients, String> patientTabDiagnoses;
    @FXML
    private TableColumn<Patients, String> patientTabDoctor;
    @FXML
    private Label patientId;
    @FXML
    private TextField patientName;
    @FXML
    private TextField patientAddress;
    @FXML
    private TextField patientAge;
    @FXML
    private TextField patientTele1;
    @FXML
    private TextField patientTele2;
    @FXML
    private Button patientNew;
    @FXML
    private Button patientEdite;
    @FXML
    private Button patientAdd;
    @FXML
    private Button patientDelete;
    @FXML
    private JFXTextField search;
    @FXML
    private ProgressIndicator progress;
    @FXML
    private TextField patientNational;
    @FXML
    private ImageView patientPhotoPicker;
    @FXML
    private TextField patientPhoto;
    @FXML
    private TextField patientGiagnose;
    @FXML
    private ComboBox<Doctors> patientDoctor;
    @FXML
    private ImageView showPicture;
    @FXML
    private JFXTextField searchEscort;
    @FXML
    private ImageView showEscortPicture;
    @FXML
    private TableView<PatientsEscort> escortTable;
    @FXML
    private TableColumn<PatientsEscort, String> escortTabNational;
    @FXML
    private TableColumn<PatientsEscort, String> escortTabName;
    @FXML
    private TableColumn<PatientsEscort, String> escortTabId;
    @FXML
    private TableColumn<PatientsEscort, String> escortTabTele2;
    @FXML
    private TableColumn<PatientsEscort, String> escortTabTele1;
    @FXML
    private TableColumn<PatientsEscort, String> escortTabAddress;
    @FXML
    private TableColumn<PatientsEscort, String> escortTabRelation;
    @FXML
    private Label escortId;
    @FXML
    private TextField escortName;
    @FXML
    private TextField escortNational;
    @FXML
    private ImageView escortPhotoPicker;
    @FXML
    private TextField escortPhoto;
    @FXML
    private ProgressIndicator escortProgress;
    @FXML
    private Button escortNew;
    @FXML
    private Button escortDelete;
    @FXML
    private Button escortEdite;
    @FXML
    private Button escortAdd;
    @FXML
    private JFXDatePicker patientDateOfBirth;
    @FXML
    private TextField patientGovernment;
    @FXML
    private ComboBox<TransferOrganization> patientTransName;
    @FXML
    private TextField escortTele1;
    @FXML
    private TextField escortRelation;
    @FXML
    private TextField escortAddress;
    @FXML
    private TextField escortTele2;
    @FXML
    private MaterialIconView addTranspering;
    @FXML
    private ComboBox<String> patientGender;
    @FXML
    private FontAwesomeIconView ham;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private ImageView print;

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
                                    clear();
                                    setTableColumns();
                                    getDataToTable();
                                    fillCombo();

                                    double drawerx = drawer.getLayoutX();
                                    double drawery = drawer.getLayoutY();
                                    drawer.setLayoutX(drawerx + 600);
                                    drawer.setLayoutY(drawery);
                                    drawer.setVisible(false);
                                    drawer.setMaxWidth(0);

                                    drawer.setOnDrawerOpening(e
                                            -> {
                                        drawer.setLayoutX(drawerx);
                                        drawer.setLayoutY(drawery);
                                        drawer.setMaxWidth(600);
                                    });

                                    drawer.setOnDrawerClosed(e
                                            -> {
                                        drawer.setLayoutX(drawerx + 600);
                                        drawer.setLayoutY(drawery);
                                        drawer.setVisible(false);
                                        drawer.setMaxWidth(0);
                                    });

                                    ham.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {

                                        drawer.setVisible(true);
                                        if (drawer.isOpened()) {
                                            drawer.close();
                                        } else {
                                            drawer.open();
                                        }
                                    });
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

        patientTable.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            if (patientTable.getSelectionModel().getSelectedIndex() == -1) {
                escortTable.setItems(null);
            } else {
                patientAdd.setDisable(true);
                patientEdite.setDisable(false);
                patientDelete.setDisable(false);
                patientNew.setDisable(false);

                Patients pa = patientTable.getSelectionModel().getSelectedItem();
                patientId.setText(Integer.toString(pa.getId()));
                patientName.setText(pa.getName());
                patientAddress.setText(pa.getAddress());
                patientAge.setText(pa.getAge());
                patientTele1.setText(pa.getTele1());
                patientTele2.setText(pa.getTele2());
                patientNational.setText(pa.getNational_id());
                patientGiagnose.setText(pa.getDiagnosis());
                patientDateOfBirth.setValue(LocalDate.parse(pa.getDateOfBirth()));
                patientGovernment.setText(pa.getGovernment());
                patientGender.getSelectionModel().select(pa.getGender());
                ObservableList<TransferOrganization> items = patientTransName.getItems();
                for (TransferOrganization a : items) {
                    if (a.getName().equals(pa.getTranportOrg())) {

                        patientTransName.getSelectionModel().select(a);
                    }
                }
                ObservableList<Doctors> items1 = patientDoctor.getItems();
                for (Doctors a : items1) {
                    if (a.getName().equals(pa.getDoctor_name())) {

                        patientDoctor.getSelectionModel().select(a);
                    }
                }
                try {
                    escortProgress.setVisible(true);
                    escortTable.setItems(PatientsEscort.getData(Integer.toString(pa.getId())));
                    Escortitems = escortTable.getItems();
                    clearEscort();
                    escortProgress.setVisible(false);
                } catch (Exception ex) {
                    AlertDialogs.showErrors(ex);
                }
            }
        });
        escortTable.setOnMouseClicked((e) -> {
            if (escortTable.getSelectionModel().getSelectedIndex() == -1) {
            } else {
                PatientsEscort ps = escortTable.getSelectionModel().getSelectedItem();
                escortAdd.setDisable(true);
                escortEdite.setDisable(false);
                escortDelete.setDisable(false);
                escortNew.setDisable(false);

                escortId.setText(Integer.toString(ps.getId()));
                escortName.setText(ps.getName());
                escortRelation.setText(ps.getRelation());
                escortAddress.setText(ps.getAddress());
                escortNational.setText(ps.getNationaId());
                escortPhoto.setText("");
                escortTele1.setText(ps.getTele1());
                escortTele2.setText(ps.getTele2());
            }
        });
    }
    ObservableList<PatientsEscort> Escortitems;

    private void fillCombo() throws Exception {
        patientGender.getItems().add("ذكر");
        patientGender.getItems().add("انثي");
        patientTransName.setItems(TransferOrganization.getData());
        patientTransName.setConverter(new StringConverter<TransferOrganization>() {
            @Override
            public String toString(TransferOrganization contract) {
                return contract.getName();
            }

            @Override
            public TransferOrganization fromString(String string) {
                return null;
            }
        });
        patientTransName.setCellFactory(cell -> new ListCell<TransferOrganization>() {

            GridPane gridPane = new GridPane();
            Label lblid = new Label();
            Label lblName = new Label();

            {

                gridPane.getColumnConstraints().addAll(
                        new ColumnConstraints(100, 100, 100),
                        new ColumnConstraints(100, 100, 100)
                );

                gridPane.add(lblid, 0, 1);
                gridPane.add(lblName, 1, 1);

            }

            @Override
            protected void updateItem(TransferOrganization person, boolean empty) {
                super.updateItem(person, empty);

                if (!empty && person != null) {

                    // Update our Labels
                    lblid.setText("م: " + Integer.toString(person.getId()));
                    lblName.setText("الاسم: " + person.getName());

                    // Set this ListCell's graphicProperty to display our GridPane
                    setGraphic(gridPane);
                } else {
                    // Nothing to display here
                    setGraphic(null);
                }
            }
        });
        patientDoctor.setItems(Doctors.getData());
        patientDoctor.setConverter(new StringConverter<Doctors>() {
            @Override
            public String toString(Doctors contract) {
                return contract.getName();
            }

            @Override
            public Doctors fromString(String string) {
                return null;
            }
        });
        patientDoctor.setCellFactory(cell -> new ListCell<Doctors>() {

            GridPane gridPane = new GridPane();
            Label lblid = new Label();
            Label lblName = new Label();
            Label lblQuali = new Label();

            {

                gridPane.getColumnConstraints().addAll(
                        new ColumnConstraints(100, 100, 100), new ColumnConstraints(100, 100, 100),
                        new ColumnConstraints(100, 100, 100)
                );

                gridPane.add(lblid, 0, 1);
                gridPane.add(lblName, 1, 1);
                gridPane.add(lblQuali, 2, 1);

            }

            @Override
            protected void updateItem(Doctors person, boolean empty) {
                super.updateItem(person, empty);

                if (!empty && person != null) {

                    // Update our Labels
                    lblid.setText("م: " + Integer.toString(person.getId()));
                    lblName.setText("الاسم: " + person.getName());
                    lblQuali.setText("التخصص: " + person.getQualification_name());
                    // Set this ListCell's graphicProperty to display our GridPane
                    setGraphic(gridPane);
                } else {
                    // Nothing to display here
                    setGraphic(null);
                }
            }
        });
    }

    @FXML
    private void patientNew(ActionEvent event) {
        clear();
    }

    @FXML
    private void patientEdite(ActionEvent event) {
        if (patientName.getText().isEmpty() || patientName.getText() == null) {
            AlertDialogs.showError("اسم المريض لا يجب ان يكون فارغا!!");
        } else {
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
                                        try {
                                            Alert alert = new Alert(AlertType.CONFIRMATION);
                                            alert.setTitle("Editing Patient");
                                            alert.setHeaderText("سيتم تعديل بيانات المريض");
                                            alert.setContentText("هل انت متاكد؟");

                                            Optional<ButtonType> result = alert.showAndWait();
                                            if (result.get() == ButtonType.OK) {
                                                if (patientPhoto.getText().isEmpty() || patientPhoto.getText() == null) {
                                                    Patients patient = new Patients();
                                                    patient.setId(Integer.parseInt(patientId.getText()));
                                                    patient.setName(patientName.getText());
                                                    patient.setAddress(patientAddress.getText());
                                                    patient.setAge(patientAge.getText());
                                                    patient.setNational_id(patientNational.getText());
                                                    patient.setDoctor_id(patientDoctor.getSelectionModel().getSelectedItem().getId());
                                                    patient.setDiagnosis(patientGiagnose.getText());
                                                    patient.setGovernment(patientGovernment.getText());
                                                    DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                                    patient.setDateOfBirth(patientDateOfBirth.getValue().format(f));
                                                    patient.setTranportOrgId(patientTransName.getSelectionModel().getSelectedItem().getId());
                                                    patient.setTele1(patientTele1.getText());
                                                    patient.setTele2(patientTele2.getText());
                                                    patient.setGender(patientGender.getSelectionModel().getSelectedItem());
                                                    patient.Edite();
                                                } else {
                                                    Patients patient = new Patients();
                                                    patient.setId(Integer.parseInt(patientId.getText()));
                                                    patient.setName(patientName.getText());
                                                    patient.setAddress(patientAddress.getText());
                                                    patient.setAge(patientAge.getText());
                                                    patient.setNational_id(patientNational.getText());
                                                    patient.setDoctor_id(patientDoctor.getSelectionModel().getSelectedItem().getId());
                                                    patient.setDiagnosis(patientGiagnose.getText());
                                                    patient.setTele1(patientTele1.getText());
                                                    patient.setTele2(patientTele2.getText());
                                                    patient.setGovernment(patientGovernment.getText());
                                                    DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                                    patient.setDateOfBirth(patientDateOfBirth.getValue().format(f));
                                                    patient.setTranportOrgId(patientTransName.getSelectionModel().getSelectedItem().getId());
                                                    InputStream in = new FileInputStream(new File(patientPhoto.getText()));
                                                    patient.setPhoto(in);

                                                    String[] st = patientPhoto.getText().split(Pattern.quote("."));
                                                    patient.setPhotoExt(st[st.length - 1]);
                                                    patient.setGender(patientGender.getSelectionModel().getSelectedItem());
                                                    patient.EditeWithPhoto();
                                                }
                                            }
                                        } catch (Exception ex) {
                                            AlertDialogs.showErrors(ex);
                                        }
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
                    clear();
                    getDataToTable();
                    progress.setVisible(false);
                    super.succeeded();
                }
            };
            service.start();
        }

    }

    @FXML
    private void patientAdd(ActionEvent event) {
        if (patientName.getText().isEmpty() || patientName.getText() == null) {
            AlertDialogs.showError("اسم المريض لا يجب ان يكون فارغا!!");
        } else {
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
                                        try {
                                            if (patientPhoto.getText().isEmpty() || patientPhoto.getText() == null) {
                                                Patients patient = new Patients();
                                                patient.setId(Integer.parseInt(patientId.getText()));
                                                patient.setName(patientName.getText());
                                                patient.setAddress(patientAddress.getText());
                                                patient.setAge(patientAge.getText());
                                                patient.setNational_id(patientNational.getText());
                                                patient.setDoctor_id(patientDoctor.getSelectionModel().getSelectedItem().getId());
                                                patient.setDiagnosis(patientGiagnose.getText());
                                                patient.setGovernment(patientGovernment.getText());
                                                DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                                patient.setDateOfBirth(patientDateOfBirth.getValue().format(f));
                                                patient.setTranportOrgId(patientTransName.getSelectionModel().getSelectedItem().getId());
                                                patient.setTele1(patientTele1.getText());
                                                patient.setTele2(patientTele2.getText());
                                                patient.setGender(patientGender.getSelectionModel().getSelectedItem());
                                                patient.Add();
                                            } else {
                                                Patients patient = new Patients();
                                                patient.setId(Integer.parseInt(patientId.getText()));
                                                patient.setName(patientName.getText());
                                                patient.setAddress(patientAddress.getText());
                                                patient.setAge(patientAge.getText());
                                                patient.setNational_id(patientNational.getText());
                                                patient.setDoctor_id(patientDoctor.getSelectionModel().getSelectedItem().getId());
                                                patient.setDiagnosis(patientGiagnose.getText());
                                                patient.setTele1(patientTele1.getText());
                                                patient.setTele2(patientTele2.getText());
                                                patient.setGovernment(patientGovernment.getText());
                                                DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                                patient.setDateOfBirth(patientDateOfBirth.getValue().format(f));
                                                patient.setTranportOrgId(patientTransName.getSelectionModel().getSelectedItem().getId());
                                                InputStream in = new FileInputStream(new File(patientPhoto.getText()));
                                                patient.setPhoto(in);

                                                String[] st = patientPhoto.getText().split(Pattern.quote("."));
                                                patient.setPhotoExt(st[st.length - 1]);
                                                patient.setGender(patientGender.getSelectionModel().getSelectedItem());
                                                patient.AddWithPhoto();
                                            }
                                        } catch (Exception ex) {
                                            AlertDialogs.showErrors(ex);
                                        }
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
                    clear();
                    getDataToTable();
                    progress.setVisible(false);
                    super.succeeded();
                }
            };
            service.start();
        }
    }

    @FXML
    private void patientDelete(ActionEvent event) {
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
                                    try {
                                        Alert alert = new Alert(AlertType.CONFIRMATION);
                                        alert.setTitle("Deleting Patient");
                                        alert.setHeaderText("سيتم حذف المريض");
                                        alert.setContentText("هل انت متاكد؟");

                                        Optional<ButtonType> result = alert.showAndWait();
                                        if (result.get() == ButtonType.OK) {
                                            Patients patient = new Patients();
                                            patient.setId(Integer.parseInt(patientId.getText()));
                                            patient.Delete();
                                        }

                                    } catch (Exception ex) {
                                        AlertDialogs.showErrors(ex);
                                    }
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
                clear();
                getDataToTable();
                progress.setVisible(false);
                super.succeeded();
            }
        };
        service.start();

    }

    private void setAutoNumber() {
        try {
            patientId.setText(Patients.getAutoNum());
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    private void clear() {
        setAutoNumber();
        patientName.setText("");
        patientAddress.setText("");
        patientAge.setText("");
        patientNational.setText("");
        patientGiagnose.setText("");
        patientPhoto.setText("");
        patientDoctor.getSelectionModel().clearSelection();
        patientDateOfBirth.setValue(null);
        patientGovernment.setText("");
        patientGender.getSelectionModel().clearSelection();
        patientTransName.getSelectionModel().clearSelection();
        patientTele1.setText("");
        patientTele2.setText("");
        patientAdd.setDisable(false);
        patientEdite.setDisable(true);
        patientDelete.setDisable(true);
        patientNew.setDisable(true);
    }

    private void setTableColumns() {
        patientTabId.setCellValueFactory(new PropertyValueFactory<>("id"));
        patientTabName.setCellValueFactory(new PropertyValueFactory<>("name"));
        patientTabDiagnoses.setCellValueFactory(new PropertyValueFactory<>("diagnosis"));
        patientTabDoctor.setCellValueFactory(new PropertyValueFactory<>("doctor_name"));
        patientTabTele1.setCellValueFactory(new PropertyValueFactory<>("tele1"));
        patientTabTele2.setCellValueFactory(new PropertyValueFactory<>("tele2"));

        escortTabNational.setCellValueFactory(new PropertyValueFactory<>("id"));
        escortTabName.setCellValueFactory(new PropertyValueFactory<>("name"));
        escortTabId.setCellValueFactory(new PropertyValueFactory<>("id"));
        escortTabTele2.setCellValueFactory(new PropertyValueFactory<>("tele2"));
        escortTabTele1.setCellValueFactory(new PropertyValueFactory<>("tele1"));
        escortTabAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        escortTabRelation.setCellValueFactory(new PropertyValueFactory<>("relation"));

    }

    private void getDataToTable() {
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
                                    try {

                                        patientTable.setItems(Patients.getData());

                                    } catch (Exception ex) {
                                        AlertDialogs.showErrors(ex);
                                    }
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

                items = patientTable.getItems();
                progress.setVisible(false);
                super.succeeded();
            }
        };
        service.start();
    }
    ObservableList<Patients> items;

    @FXML
    private void search(KeyEvent event) {

        FilteredList<Patients> filteredData = new FilteredList<>(items, p -> true);

        filteredData.setPredicate(pa -> {

            if (search.getText() == null || search.getText().isEmpty()) {
                return true;
            }

            String lowerCaseFilter = search.getText().toLowerCase();

            if (pa.getName().contains(lowerCaseFilter)
                    || pa.getAddress().contains(lowerCaseFilter)
                    || pa.getAge().contains(lowerCaseFilter)
                    || pa.getNational_id().contains(lowerCaseFilter)
                    || pa.getTele1().contains(lowerCaseFilter)
                    || pa.getTele2().contains(lowerCaseFilter)) {
                return true;
            } else {
                return false;
            }

        });

        SortedList< Patients> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(patientTable.comparatorProperty());
        patientTable.setItems(sortedData);
    }

    @FXML
    private void pickPhoto(MouseEvent event) {
        FileChooser fil_chooser = new FileChooser();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File file = fil_chooser.showOpenDialog(st);

        if (file != null) {
            patientPhoto.setText(file.getAbsolutePath());
        }
    }

    @FXML
    private void showPicture(MouseEvent event) {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    try {

                                        try {
                                            Patients tab = patientTable.getSelectionModel().getSelectedItem();
                                            Patients cont = new Patients();
                                            cont.setId(tab.getId());
                                            cont.setName(tab.getName());
                                            cont.getNationalIdPhoto();
                                        } catch (Exception ex) {
                                            AlertDialogs.showErrors(ex);
                                        }

                                    } catch (Exception ex) {
                                        AlertDialogs.showErrors(ex);
                                    }
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
    }

    @FXML
    private void escortPhotoPick(MouseEvent event) {
        FileChooser fil_chooser = new FileChooser();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File file = fil_chooser.showOpenDialog(st);

        if (file != null) {
            escortPhoto.setText(file.getAbsolutePath());
        }
    }

    @FXML
    private void escortNew(ActionEvent event) {
        clearEscort();
    }

    @FXML
    private void escortEdite(ActionEvent event) {
        if (escortName.getText().isEmpty() || escortName.getText() == null) {
            AlertDialogs.showError("اسم المرافق لا يجب ان يكون فارغا!!");
        } else {
            escortProgress.setVisible(true);
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
                                        try {
                                            Alert alert = new Alert(AlertType.CONFIRMATION);
                                            alert.setTitle("Editing Patient");
                                            alert.setHeaderText("سيتم تعديل بيانات المرافق");
                                            alert.setContentText("هل انت متاكد؟");

                                            Optional<ButtonType> result = alert.showAndWait();
                                            if (result.get() == ButtonType.OK) {
                                                if (escortPhoto.getText().isEmpty() || escortPhoto.getText() == null) {
                                                    PatientsEscort patient = new PatientsEscort();
                                                    patient.setId(Integer.parseInt(escortId.getText()));
                                                    patient.setPatientId(patientTable.getSelectionModel().getSelectedItem().getId());
                                                    patient.setName(escortName.getText());
                                                    patient.setRelation(escortRelation.getText());
                                                    patient.setAddress(escortAddress.getText());
                                                    patient.setNationaId(escortNational.getText());
                                                    patient.setTele1(escortTele1.getText());
                                                    patient.setTele2(escortTele2.getText());
                                                    patient.EditeWithoutPhoto();
                                                } else {
                                                    PatientsEscort patient = new PatientsEscort();
                                                    patient.setId(Integer.parseInt(escortId.getText()));
                                                    patient.setPatientId(patientTable.getSelectionModel().getSelectedItem().getId());
                                                    patient.setName(escortName.getText());
                                                    patient.setRelation(escortRelation.getText());
                                                    patient.setAddress(escortAddress.getText());
                                                    patient.setNationaId(escortNational.getText());
                                                    patient.setTele1(escortTele1.getText());
                                                    patient.setTele2(escortTele2.getText());

                                                    InputStream in = new FileInputStream(new File(escortPhoto.getText()));
                                                    patient.setPhoto(in);

                                                    String[] st = escortPhoto.getText().split(Pattern.quote("."));
                                                    patient.setPhotoExt(st[st.length - 1]);

                                                    patient.Edite();
                                                }
                                            }
                                        } catch (Exception ex) {
                                            AlertDialogs.showErrors(ex);
                                        }
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
                    clearEscort();
                    clear();
                    getDataToTable();
                    escortProgress.setVisible(false);
                    super.succeeded();
                }
            };
            service.start();
        }
    }

    @FXML
    private void escortDelete(ActionEvent event) {
        if (escortName.getText().isEmpty() || escortName.getText() == null) {
            AlertDialogs.showError("اسم المرافق لا يجب ان يكون فارغا!!");
        } else {
            escortProgress.setVisible(true);
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
                                        try {
                                            Alert alert = new Alert(AlertType.CONFIRMATION);
                                            alert.setTitle("Deleting Patient");
                                            alert.setHeaderText("سيتم حذف بيانات المرافق");
                                            alert.setContentText("هل انت متاكد؟");

                                            Optional<ButtonType> result = alert.showAndWait();
                                            if (result.get() == ButtonType.OK) {

                                                PatientsEscort patient = new PatientsEscort();
                                                patient.setId(Integer.parseInt(escortId.getText()));

                                                patient.Delete();

                                            }
                                        } catch (Exception ex) {
                                            AlertDialogs.showErrors(ex);
                                        }
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
                    clearEscort();
                    clear();
                    getDataToTable();
                    escortProgress.setVisible(false);
                    super.succeeded();
                }
            };
            service.start();
        }
    }

    @FXML
    private void escortAdd(ActionEvent event) {
        if (escortName.getText().isEmpty() || escortName.getText() == null) {
            AlertDialogs.showError("اسم المرافق لا يجب ان يكون فارغا!!");
        } else {
            escortProgress.setVisible(true);
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
                                        try {

                                            if (escortPhoto.getText().isEmpty() || escortPhoto.getText() == null) {
                                                PatientsEscort patient = new PatientsEscort();
                                                patient.setId(Integer.parseInt(escortId.getText()));
                                                patient.setPatientId(patientTable.getSelectionModel().getSelectedItem().getId());
                                                patient.setName(escortName.getText());
                                                patient.setRelation(escortRelation.getText());
                                                patient.setAddress(escortAddress.getText());
                                                patient.setNationaId(escortNational.getText());
                                                patient.setTele1(escortTele1.getText());
                                                patient.setTele2(escortTele2.getText());
                                                patient.AddWithoutPhoto();
                                            } else {
                                                PatientsEscort patient = new PatientsEscort();
                                                patient.setId(Integer.parseInt(escortId.getText()));
                                                patient.setPatientId(patientTable.getSelectionModel().getSelectedItem().getId());
                                                patient.setName(escortName.getText());
                                                patient.setRelation(escortRelation.getText());
                                                patient.setAddress(escortAddress.getText());
                                                patient.setNationaId(escortNational.getText());
                                                patient.setTele1(escortTele1.getText());
                                                patient.setTele2(escortTele2.getText());

                                                InputStream in = new FileInputStream(new File(escortPhoto.getText()));
                                                patient.setPhoto(in);

                                                String[] st = escortPhoto.getText().split(Pattern.quote("."));
                                                patient.setPhotoExt(st[st.length - 1]);

                                                patient.Add();
                                            }

                                        } catch (Exception ex) {
                                            AlertDialogs.showErrors(ex);
                                        }
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
                    clearEscort();
                    clear();
                    getDataToTable();
                    escortProgress.setVisible(false);
                    super.succeeded();
                }
            };
            service.start();
        }
    }

    private void clearEscort() {
        try {
            if (patientTable.getSelectionModel().getSelectedIndex() == -1) {
                escortTable.setItems(null);
            } else {
                escortTable.setItems(PatientsEscort.getData(Integer.toString(patientTable.getSelectionModel().getSelectedItem().getId())));
            }

            setEscortAutoNum();
            escortProgress.setVisible(false);
            escortAdd.setDisable(false);
            escortEdite.setDisable(true);
            escortDelete.setDisable(true);
            escortNew.setDisable(true);

            escortName.setText("");
            escortRelation.setText("");
            escortAddress.setText("");
            escortNational.setText("");
            escortPhoto.setText("");
            escortTele1.setText("");
            escortTele2.setText("");
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    private void setEscortAutoNum() throws Exception {
        escortId.setText(PatientsEscort.getAutoNum());
    }

    @FXML
    private void showEscortPicture(MouseEvent event) {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    try {

                                        try {
                                            PatientsEscort tab = escortTable.getSelectionModel().getSelectedItem();
                                            PatientsEscort cont = new PatientsEscort();
                                            cont.setId(tab.getId());
                                            cont.setName(tab.getName());
                                            cont.getNationalIdPhoto();
                                        } catch (Exception ex) {
                                            AlertDialogs.showErrors(ex);
                                        }

                                    } catch (Exception ex) {
                                        AlertDialogs.showErrors(ex);
                                    }
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
    }

    @FXML
    private void searchEscort(KeyEvent event) {

        FilteredList<PatientsEscort> filteredData = new FilteredList<>(Escortitems, p -> true);

        filteredData.setPredicate(pa -> {

            if (searchEscort.getText() == null || searchEscort.getText().isEmpty()) {
                return true;
            }

            String lowerCaseFilter = searchEscort.getText().toLowerCase();

            if (pa.getName().contains(lowerCaseFilter)
                    || pa.getAddress().contains(lowerCaseFilter)
                    || pa.getTele1().contains(lowerCaseFilter)
                    || pa.getTele2().contains(lowerCaseFilter)) {
                return true;
            } else {
                return false;
            }

        });

        SortedList< PatientsEscort> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(escortTable.comparatorProperty());
        escortTable.setItems(sortedData);
    }

    @FXML
    private void addTranspering(MouseEvent event) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Add Org Name");
        dialog.setHeaderText("اضافة اسم المؤسسة");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (result.get().isEmpty() || result.get() == null) {
                AlertDialogs.showError("خطا!! يرجي ادخال اسم المؤسسة");
            } else {
                final String results = result.get();
                try {
                    Service service = new Service() {
                        @Override
                        protected Task createTask() {
                            return new Task() {
                                @Override
                                protected Object call() throws Exception {
                                    final CountDownLatch latch = new CountDownLatch(1);
                                    Platform.runLater(() -> {
                                        try {
                                            TransferOrganization.Add(results);
                                        } catch (Exception ex) {
                                            AlertDialogs.showErrors(ex);
                                        } finally {
                                            latch.countDown();
                                        }
                                    });
                                    latch.await();

                                    return null;
                                }

                                @Override
                                protected void succeeded() {
                                    try {
                                        fillCombo();
                                    } catch (Exception ex) {
                                        AlertDialogs.showErrors(ex);
                                    }
                                }
                            };
                        }
                    };
                    service.start();

                } catch (Exception ex) {
                    AlertDialogs.showErrors(ex);
                }
            }
        }
    }

    @FXML
    private void print(MouseEvent event) {
        if (patientTable.getSelectionModel().getSelectedIndex() == -1) {
            AlertDialogs.showError("اختار المريض اولا");
        } else {

            Service<Void> service = new Service<Void>() {
                @Override
                protected Task<Void> createTask() {
                    return new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {

                          

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
  try {
                                Patients pa = patientTable.getSelectionModel().getSelectedItem();
                                HashMap hash = new HashMap();
                                BufferedImage image = ImageIO.read(getClass().getResource("/assets/icons/logo.png"));
                                hash.put("logo", image);
                                hash.put("patient_id", Integer.toString(pa.getId()));
                                hash.put("name", pa.getName());
                                hash.put("government", pa.getGovernment());
                                hash.put("adress", pa.getAddress());
                                hash.put("nationalId", pa.getNational_id());
                                hash.put("trans", pa.getTranportOrg());
                                hash.put("disasea", pa.getDiagnosis());
                                hash.put("doctor", pa.getDoctor_name());
                                hash.put("date_of_birth", pa.getDateOfBirth());
                                hash.put("gender", pa.getGender());
                                hash.put("age", pa.getAge());
                                hash.put("tele1", pa.getTele1());
                                hash.put("tele2", pa.getTele2());
                               
                                InputStream admissionReport = getClass().getResourceAsStream("/screens/mainDataScreen/reports/AdmissionDetails.jasper");
                                hash.put("admissionReport", admissionReport);
                              
                                InputStream clincRep = getClass().getResourceAsStream("/screens/mainDataScreen/reports/AdmissionDetailsClincs.jasper");
                                hash.put("clincRep", clincRep);
                               
                                InputStream contractRep = getClass().getResourceAsStream("/screens/mainDataScreen/reports/AdmissionDetailsContract.jasper");
                                hash.put("contractRep", contractRep);
                               
                                InputStream medicineRep = getClass().getResourceAsStream("/screens/mainDataScreen/reports/AdmissionDetailsMedicines.jasper");
                                hash.put("medicineRep", medicineRep);
                              
                                InputStream serviceRep = getClass().getResourceAsStream("/screens/mainDataScreen/reports/AdmissionDetailsServices.jasper");
                                hash.put("serviceRep", serviceRep);
                               
                                InputStream surmedRep = getClass().getResourceAsStream("/screens/mainDataScreen/reports/AdmissionDetailsSurMed.jasper");
                                hash.put("surmedRep", surmedRep);
                               
                                InputStream surgiresRep = getClass().getResourceAsStream("/screens/mainDataScreen/reports/AdmissionDetailsSurgires.jasper");
                                hash.put("surgiresRep", surgiresRep);
                               
                                InputStream a = getClass().getResourceAsStream("/screens/mainDataScreen/reports/PatientFile.jrxml");
                                JasperDesign design = JRXmlLoader.load(a);
                                JasperReport jasperreport = JasperCompileManager.compileReport(design);
                                JasperPrint jasperprint = JasperFillManager.fillReport(jasperreport, hash, db.get.getReportCon());
                                JasperViewer.viewReport(jasperprint, false);

                            } catch (Exception ex) {
                                AlertDialogs.showErrors(ex);
                            } finally {

                            }
        }
    }

}
