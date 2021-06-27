package screens.drugs;

import assets.classes.AlertDialogs;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.events.JFXDrawerEvent;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import javafx.util.StringConverter;
import screens.drugs.assets.DrugsAccounts;
import screens.drugs.assets.DrugsPatients;
import screens.drugs.assets.DrugsPatientsEscort;
import screens.mainDataScreen.assets.Doctors;
import screens.mainDataScreen.assets.TransferOrganization;

public class DrugsScreenPatientController
        implements Initializable {

    @FXML
    private TableView<DrugsPatients> patientTable;
    @FXML
    private TableColumn<DrugsPatients, String> patientTabTele2;
    @FXML
    private TableColumn<DrugsPatients, String> patientTabTele1;
    @FXML
    private TableColumn<DrugsPatients, String> patientTabName;
    @FXML
    private TableColumn<DrugsPatients, String> patientTabId;
    @FXML
    private TableColumn<DrugsPatients, String> patientTabDiagnoses;
    @FXML
    private TableColumn<DrugsPatients, String> patientTabDoctor;
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
    private TableView<DrugsPatientsEscort> escortTable;
    @FXML
    private TableColumn<DrugsPatientsEscort, String> escortTabNational;
    @FXML
    private TableColumn<DrugsPatientsEscort, String> escortTabName;
    @FXML
    private TableColumn<DrugsPatientsEscort, String> escortTabId;
    @FXML
    private TableColumn<DrugsPatientsEscort, String> escortTabTele2;
    @FXML
    private TableColumn<DrugsPatientsEscort, String> escortTabTele1;
    @FXML
    private TableColumn<DrugsPatientsEscort, String> escortTabAddress;
    @FXML
    private TableColumn<DrugsPatientsEscort, String> escortTabRelation;
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
    ObservableList<DrugsPatientsEscort> Escortitems;
    ObservableList<DrugsPatients> items;

    public void initialize(URL url, ResourceBundle rb) {
         patientDateOfBirth.setConverter(new StringConverter<LocalDate>() {
            private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override
            public String toString(LocalDate localDate) {
                if (localDate == null) {
                    return "";
                }
                return dateTimeFormatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString) {
                if (dateString == null || dateString.trim().isEmpty()) {
                    return null;
                }
                return LocalDate.parse(dateString, dateTimeFormatter);
            }
        });
        Service<Void> service = new Service<Void>() {
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    protected Void call() throws Exception {
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(new Runnable() {
                            public void run() {
                                try {
                                    DrugsScreenPatientController.this.clear();
                                    DrugsScreenPatientController.this.setTableColumns();
                                    DrugsScreenPatientController.this.getDataToTable();
                                    DrugsScreenPatientController.this.fillCombo();

                                    double drawerx = DrugsScreenPatientController.this.drawer.getLayoutX();
                                    double drawery = DrugsScreenPatientController.this.drawer.getLayoutY();
                                    DrugsScreenPatientController.this.drawer.setLayoutX(drawerx + 600.0D);
                                    DrugsScreenPatientController.this.drawer.setLayoutY(drawery);
                                    DrugsScreenPatientController.this.drawer.setVisible(false);
                                    DrugsScreenPatientController.this.drawer.setMaxWidth(0.0D);

                                    DrugsScreenPatientController.this.drawer.setOnDrawerOpening(e -> {
                                        DrugsScreenPatientController.this.drawer.setLayoutX(drawerx);

                                        DrugsScreenPatientController.this.drawer.setLayoutY(drawery);

                                        DrugsScreenPatientController.this.drawer.setMaxWidth(600.0D);
                                    });
                                    DrugsScreenPatientController.this.drawer.setOnDrawerClosed(e -> {
                                        DrugsScreenPatientController.this.drawer.setLayoutX(drawerx + 600.0D);

                                        DrugsScreenPatientController.this.drawer.setLayoutY(drawery);

                                        DrugsScreenPatientController.this.drawer.setVisible(false);
                                        DrugsScreenPatientController.this.drawer.setMaxWidth(0.0D);
                                    });
                                    DrugsScreenPatientController.this.ham.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                                        DrugsScreenPatientController.this.drawer.setVisible(true);

                                        if (DrugsScreenPatientController.this.drawer.isOpened()) {
                                            DrugsScreenPatientController.this.drawer.close();
                                        } else {
                                            DrugsScreenPatientController.this.drawer.open();
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

            protected void succeeded() {
                DrugsScreenPatientController.this.progress.setVisible(false);
                super.succeeded();
            }
        };
        service.start();

        this.patientTable.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (this.patientTable.getSelectionModel().getSelectedIndex() == -1) {
                this.escortTable.setItems(null);
            } else {
                this.patientAdd.setDisable(true);

                this.patientEdite.setDisable(false);

                this.patientDelete.setDisable(false);

                this.patientNew.setDisable(false);
                DrugsPatients pa = (DrugsPatients) this.patientTable.getSelectionModel().getSelectedItem();
                this.patientId.setText(Integer.toString(pa.getId()));
                this.patientName.setText(pa.getName());
                this.patientAddress.setText(pa.getAddress());
                this.patientAge.setText(pa.getAge());
                this.patientTele1.setText(pa.getTele1());
                this.patientTele2.setText(pa.getTele2());
                this.patientNational.setText(pa.getNational_id());
                this.patientGiagnose.setText(pa.getDiagnosis());
                this.patientDateOfBirth.setValue(LocalDate.parse(pa.getDateOfBirth()));
                this.patientGovernment.setText(pa.getGovernment());
                this.patientGender.getSelectionModel().select(pa.getGender());
                ObservableList<TransferOrganization> items = this.patientTransName.getItems();
                for (TransferOrganization a : items) {
                    if (a.getName().equals(pa.getTranportOrg())) {
                        this.patientTransName.getSelectionModel().select(a);
                    }
                }
                ObservableList<Doctors> items1 = this.patientDoctor.getItems();
                for (Doctors a : items1) {
                    if (a.getName().equals(pa.getDoctor_name())) {
                        this.patientDoctor.getSelectionModel().select(a);
                    }
                }
                try {
                    this.escortProgress.setVisible(true);
                    this.escortTable.setItems(DrugsPatientsEscort.getData(Integer.toString(pa.getId())));
                    this.Escortitems = this.escortTable.getItems();
                    clearEscort();
                    this.escortProgress.setVisible(false);
                } catch (Exception ex) {
                    AlertDialogs.showErrors(ex);
                }
            }
        });
        this.escortTable.setOnMouseClicked(e -> {
            if (this.escortTable.getSelectionModel().getSelectedIndex() != -1) {
                DrugsPatientsEscort ps = (DrugsPatientsEscort) this.escortTable.getSelectionModel().getSelectedItem();
                this.escortAdd.setDisable(true);
                this.escortEdite.setDisable(false);
                this.escortDelete.setDisable(false);
                this.escortNew.setDisable(false);
                this.escortId.setText(Integer.toString(ps.getId()));
                this.escortName.setText(ps.getName());
                this.escortRelation.setText(ps.getRelation());
                this.escortAddress.setText(ps.getAddress());
                this.escortNational.setText(ps.getNationaId());
                this.escortPhoto.setText("");
                this.escortTele1.setText(ps.getTele1());
                this.escortTele2.setText(ps.getTele2());
            }
        });
    }

    private void fillCombo() throws Exception {
        this.patientGender.getItems().add("ذكر");
        this.patientGender.getItems().add("انثي");
        this.patientTransName.setItems(TransferOrganization.getData());
        this.patientTransName.setConverter(new StringConverter<TransferOrganization>() {
            public String toString(TransferOrganization contract) {
                return contract.getName();
            }

            public TransferOrganization fromString(String string) {
                return null;
            }
        });
        this.patientTransName.setCellFactory(cell -> new ListCell<TransferOrganization>() {

            GridPane gridPane = new GridPane();
            Label lblid = new Label();
            Label lblName = new Label();

            // Static block to configure our layout
            {
                // Ensure all our column widths are constant
                gridPane.getColumnConstraints().addAll(
                        new ColumnConstraints(100, 100, 100),
                        new ColumnConstraints(100, 100, 100)
                );

                gridPane.add(lblid, 0, 1);
                gridPane.add(lblName, 1, 1);

            }

            protected void updateItem(TransferOrganization person, boolean empty) {
                super.updateItem(person, empty);

                if (!empty && person != null) {

                    this.lblid.setText("م: " + Integer.toString(person.getId()));
                    this.lblName.setText("الاسم: " + person.getName());

                    setGraphic((Node) this.gridPane);
                } else {

                    setGraphic(null);
                }
            }
        });
        this.patientDoctor.setItems(Doctors.getData());
        this.patientDoctor.setConverter(new StringConverter<Doctors>() {
            public String toString(Doctors contract) {
                return contract.getName();
            }

            public Doctors fromString(String string) {
                return null;
            }
        });
        this.patientDoctor.setCellFactory(cell -> new ListCell<Doctors>() {

            GridPane gridPane = new GridPane();
            Label lblid = new Label();
            Label lblName = new Label();
            Label lblQuali = new Label();

            // Static block to configure our layout
            {
                // Ensure all our column widths are constant
                gridPane.getColumnConstraints().addAll(
                        new ColumnConstraints(100, 100, 100), new ColumnConstraints(100, 100, 100),
                        new ColumnConstraints(100, 100, 100)
                );

                gridPane.add(lblid, 0, 1);
                gridPane.add(lblName, 1, 1);
                gridPane.add(lblQuali, 2, 1);

            }

            protected void updateItem(Doctors person, boolean empty) {
                super.updateItem(person, empty);

                if (!empty && person != null) {

                    this.lblid.setText("م: " + Integer.toString(person.getId()));
                    this.lblName.setText("الاسم: " + person.getName());
                    this.lblQuali.setText("التخصص: " + person.getQualification_name());

                    setGraphic((Node) this.gridPane);
                } else {

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
        if (this.patientName.getText().isEmpty() || this.patientName.getText() == null) {
            AlertDialogs.showError("اسم المريض لا يجب ان يكون فارغا!!");
        } else {
            this.progress.setVisible(true);
            Service<Void> service = new Service<Void>() {
                protected Task<Void> createTask() {
                    return new Task<Void>() {
                        protected Void call() throws Exception {
                            final CountDownLatch latch = new CountDownLatch(1);
                            Platform.runLater(new Runnable() {
                                public void run() {
                                    try {
                                        try {
                                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                            alert.setTitle("Editing Patient");
                                            alert.setHeaderText("سيتم تعديل بيانات المريض");
                                            alert.setContentText("هل انت متاكد؟");

                                            Optional<ButtonType> result = alert.showAndWait();
                                            if (result.get() == ButtonType.OK) {
                                                if (DrugsScreenPatientController.this.patientPhoto.getText().isEmpty() || DrugsScreenPatientController.this.patientPhoto.getText() == null) {
                                                    DrugsPatients patient = new DrugsPatients();
                                                    patient.setId(Integer.parseInt(DrugsScreenPatientController.this.patientId.getText()));
                                                    patient.setName(DrugsScreenPatientController.this.patientName.getText());
                                                    patient.setAddress(DrugsScreenPatientController.this.patientAddress.getText());
                                                    patient.setAge(DrugsScreenPatientController.this.patientAge.getText());
                                                    patient.setNational_id(DrugsScreenPatientController.this.patientNational.getText());
                                                    patient.setDoctor_id(((Doctors) DrugsScreenPatientController.this.patientDoctor.getSelectionModel().getSelectedItem()).getId());
                                                    patient.setDiagnosis(DrugsScreenPatientController.this.patientGiagnose.getText());
                                                    patient.setGovernment(DrugsScreenPatientController.this.patientGovernment.getText());
                                                    DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                                    patient.setDateOfBirth(((LocalDate) DrugsScreenPatientController.this.patientDateOfBirth.getValue()).format(f));
                                                    patient.setTranportOrgId(((TransferOrganization) DrugsScreenPatientController.this.patientTransName.getSelectionModel().getSelectedItem()).getId());
                                                    patient.setTele1(DrugsScreenPatientController.this.patientTele1.getText());
                                                    patient.setTele2(DrugsScreenPatientController.this.patientTele2.getText());
                                                    patient.setGender((String) DrugsScreenPatientController.this.patientGender.getSelectionModel().getSelectedItem());
                                                    patient.Edite();
                                                } else {
                                                    DrugsPatients patient = new DrugsPatients();
                                                    patient.setId(Integer.parseInt(DrugsScreenPatientController.this.patientId.getText()));
                                                    patient.setName(DrugsScreenPatientController.this.patientName.getText());
                                                    patient.setAddress(DrugsScreenPatientController.this.patientAddress.getText());
                                                    patient.setAge(DrugsScreenPatientController.this.patientAge.getText());
                                                    patient.setNational_id(DrugsScreenPatientController.this.patientNational.getText());
                                                    patient.setDoctor_id(((Doctors) DrugsScreenPatientController.this.patientDoctor.getSelectionModel().getSelectedItem()).getId());
                                                    patient.setDiagnosis(DrugsScreenPatientController.this.patientGiagnose.getText());
                                                    patient.setTele1(DrugsScreenPatientController.this.patientTele1.getText());
                                                    patient.setTele2(DrugsScreenPatientController.this.patientTele2.getText());
                                                    patient.setGovernment(DrugsScreenPatientController.this.patientGovernment.getText());
                                                    DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                                    patient.setDateOfBirth(((LocalDate) DrugsScreenPatientController.this.patientDateOfBirth.getValue()).format(f));
                                                    patient.setTranportOrgId(((TransferOrganization) DrugsScreenPatientController.this.patientTransName.getSelectionModel().getSelectedItem()).getId());
                                                    InputStream in = new FileInputStream(new File(DrugsScreenPatientController.this.patientPhoto.getText()));
                                                    patient.setPhoto(in);

                                                    String[] st = DrugsScreenPatientController.this.patientPhoto.getText().split(Pattern.quote("."));
                                                    patient.setPhotoExt(st[st.length - 1]);
                                                    patient.setGender((String) DrugsScreenPatientController.this.patientGender.getSelectionModel().getSelectedItem());
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

                protected void succeeded() {
                    DrugsScreenPatientController.this.clear();
                    DrugsScreenPatientController.this.getDataToTable();
                    DrugsScreenPatientController.this.progress.setVisible(false);
                    super.succeeded();
                }
            };
            service.start();
        }
    }

    @FXML
    private void patientAdd(ActionEvent event) {
        if (this.patientName.getText().isEmpty() || this.patientName.getText() == null) {
            AlertDialogs.showError("اسم المريض لا يجب ان يكون فارغا!!");
        } else {
            this.progress.setVisible(true);
            Service<Void> service = new Service<Void>() {
                protected Task<Void> createTask() {
                    return new Task<Void>() {
                        protected Void call() throws Exception {
                            final CountDownLatch latch = new CountDownLatch(1);
                            Platform.runLater(new Runnable() {
                                public void run() {
                                    try {
                                        try {
                                            if (DrugsScreenPatientController.this.patientPhoto.getText().isEmpty() || DrugsScreenPatientController.this.patientPhoto.getText() == null) {
                                                DrugsPatients patient = new DrugsPatients();
                                                patient.setId(Integer.parseInt(DrugsScreenPatientController.this.patientId.getText()));
                                                patient.setName(DrugsScreenPatientController.this.patientName.getText());
                                                patient.setAddress(DrugsScreenPatientController.this.patientAddress.getText());
                                                patient.setAge(DrugsScreenPatientController.this.patientAge.getText());
                                                patient.setNational_id(DrugsScreenPatientController.this.patientNational.getText());
                                                patient.setDoctor_id(((Doctors) DrugsScreenPatientController.this.patientDoctor.getSelectionModel().getSelectedItem()).getId());
                                                patient.setDiagnosis(DrugsScreenPatientController.this.patientGiagnose.getText());
                                                patient.setGovernment(DrugsScreenPatientController.this.patientGovernment.getText());
                                                DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                                patient.setDateOfBirth(((LocalDate) DrugsScreenPatientController.this.patientDateOfBirth.getValue()).format(f));
                                                patient.setTranportOrgId(((TransferOrganization) DrugsScreenPatientController.this.patientTransName.getSelectionModel().getSelectedItem()).getId());
                                                patient.setTele1(DrugsScreenPatientController.this.patientTele1.getText());
                                                patient.setTele2(DrugsScreenPatientController.this.patientTele2.getText());
                                                patient.setGender((String) DrugsScreenPatientController.this.patientGender.getSelectionModel().getSelectedItem());
                                                patient.Add();
                                            } else {
                                                DrugsPatients patient = new DrugsPatients();
                                                patient.setId(Integer.parseInt(DrugsScreenPatientController.this.patientId.getText()));
                                                patient.setName(DrugsScreenPatientController.this.patientName.getText());
                                                patient.setAddress(DrugsScreenPatientController.this.patientAddress.getText());
                                                patient.setAge(DrugsScreenPatientController.this.patientAge.getText());
                                                patient.setNational_id(DrugsScreenPatientController.this.patientNational.getText());
                                                patient.setDoctor_id(((Doctors) DrugsScreenPatientController.this.patientDoctor.getSelectionModel().getSelectedItem()).getId());
                                                patient.setDiagnosis(DrugsScreenPatientController.this.patientGiagnose.getText());
                                                patient.setTele1(DrugsScreenPatientController.this.patientTele1.getText());
                                                patient.setTele2(DrugsScreenPatientController.this.patientTele2.getText());
                                                patient.setGovernment(DrugsScreenPatientController.this.patientGovernment.getText());
                                                DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                                patient.setDateOfBirth(((LocalDate) DrugsScreenPatientController.this.patientDateOfBirth.getValue()).format(f));
                                                patient.setTranportOrgId(((TransferOrganization) DrugsScreenPatientController.this.patientTransName.getSelectionModel().getSelectedItem()).getId());
                                                InputStream in = new FileInputStream(new File(DrugsScreenPatientController.this.patientPhoto.getText()));
                                                patient.setPhoto(in);

                                                String[] st = DrugsScreenPatientController.this.patientPhoto.getText().split(Pattern.quote("."));
                                                patient.setPhotoExt(st[st.length - 1]);
                                                patient.setGender((String) DrugsScreenPatientController.this.patientGender.getSelectionModel().getSelectedItem());
                                                patient.AddWithPhoto();
                                            }
                                            DrugsAccounts da = new DrugsAccounts();
                                            da.setId(Integer.parseInt(DrugsAccounts.getAutoNum()));
                                            da.setPaitent_id(Integer.parseInt(DrugsScreenPatientController.this.patientId.getText()));
                                            da.setTotal_paied("0");
                                            da.setTotal_spended("0");
                                            da.setRemaining("0");
                                            da.Add();
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

                protected void succeeded() {
                    DrugsScreenPatientController.this.clear();
                    DrugsScreenPatientController.this.getDataToTable();
                    DrugsScreenPatientController.this.progress.setVisible(false);
                    super.succeeded();
                }
            };
            service.start();
        }
    }

    @FXML
    private void patientDelete(ActionEvent event) {
        this.progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    protected Void call() throws Exception {
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(new Runnable() {
                            public void run() {
                                try {
                                    try {
                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setTitle("Deleting Patient");
                                        alert.setHeaderText("سيتم حذف المريض");
                                        alert.setContentText("هل انت متاكد؟");

                                        Optional<ButtonType> result = alert.showAndWait();
                                        if (result.get() == ButtonType.OK) {
                                            DrugsPatients patient = new DrugsPatients();
                                            patient.setId(Integer.parseInt(DrugsScreenPatientController.this.patientId.getText()));
                                            patient.Delete();
                                            DrugsAccounts da = new DrugsAccounts();
                                            da.setPaitent_id(Integer.parseInt(DrugsScreenPatientController.this.patientId.getText()));
                                            da.Delete();
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

            protected void succeeded() {
                DrugsScreenPatientController.this.clear();
                DrugsScreenPatientController.this.getDataToTable();
                DrugsScreenPatientController.this.progress.setVisible(false);
                super.succeeded();
            }
        };
        service.start();
    }

    private void setAutoNumber() {
        try {
            this.patientId.setText(DrugsPatients.getAutoNum());
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    private void clear() {
        setAutoNumber();
        this.patientName.setText("");
        this.patientAddress.setText("");
        this.patientAge.setText("");
        this.patientNational.setText("");
        this.patientGiagnose.setText("");
        this.patientPhoto.setText("");
        this.patientDoctor.getSelectionModel().clearSelection();
        this.patientDateOfBirth.setValue(null);
        this.patientGovernment.setText("");
        this.patientGender.getSelectionModel().clearSelection();
        this.patientTransName.getSelectionModel().clearSelection();
        this.patientTele1.setText("");
        this.patientTele2.setText("");
        this.patientAdd.setDisable(false);
        this.patientEdite.setDisable(true);
        this.patientDelete.setDisable(true);
        this.patientNew.setDisable(true);
    }

    private void setTableColumns() {
        this.patientTabId.setCellValueFactory((Callback) new PropertyValueFactory("id"));
        this.patientTabName.setCellValueFactory((Callback) new PropertyValueFactory("name"));
        this.patientTabDiagnoses.setCellValueFactory((Callback) new PropertyValueFactory("diagnosis"));
        this.patientTabDoctor.setCellValueFactory((Callback) new PropertyValueFactory("doctor_name"));
        this.patientTabTele1.setCellValueFactory((Callback) new PropertyValueFactory("tele1"));
        this.patientTabTele2.setCellValueFactory((Callback) new PropertyValueFactory("tele2"));

        this.escortTabNational.setCellValueFactory((Callback) new PropertyValueFactory("id"));
        this.escortTabName.setCellValueFactory((Callback) new PropertyValueFactory("name"));
        this.escortTabId.setCellValueFactory((Callback) new PropertyValueFactory("id"));
        this.escortTabTele2.setCellValueFactory((Callback) new PropertyValueFactory("tele2"));
        this.escortTabTele1.setCellValueFactory((Callback) new PropertyValueFactory("tele1"));
        this.escortTabAddress.setCellValueFactory((Callback) new PropertyValueFactory("Address"));
        this.escortTabRelation.setCellValueFactory((Callback) new PropertyValueFactory("relation"));
    }

    private void getDataToTable() {
        this.progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    protected Void call() throws Exception {
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(new Runnable() {
                            public void run() {
                                try {
                                    try {
                                        DrugsScreenPatientController.this.patientTable.setItems(DrugsPatients.getData());
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

            protected void succeeded() {
                DrugsScreenPatientController.this.items = DrugsScreenPatientController.this.patientTable.getItems();
                DrugsScreenPatientController.this.progress.setVisible(false);
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void search(KeyEvent event) {
        FilteredList<DrugsPatients> filteredData = new FilteredList(this.items, p -> true);

        filteredData.setPredicate(pa -> {
            if (this.search.getText() == null || this.search.getText().isEmpty()) {
                return true;
            }

            String lowerCaseFilter = this.search.getText().toLowerCase();

            return (pa.getName().contains(lowerCaseFilter) || pa.getAddress().contains(lowerCaseFilter) || pa.getAge().contains(lowerCaseFilter) || pa.getNational_id().contains(lowerCaseFilter) || pa.getTele1().contains(lowerCaseFilter) || pa.getTele2().contains(lowerCaseFilter));
        });

        SortedList<DrugsPatients> sortedData = new SortedList((ObservableList) filteredData);
        sortedData.comparatorProperty().bind((ObservableValue) this.patientTable.comparatorProperty());
        this.patientTable.setItems((ObservableList) sortedData);
    }

    @FXML
    private void pickPhoto(MouseEvent event) {
        FileChooser fil_chooser = new FileChooser();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File file = fil_chooser.showOpenDialog((Window) st);

        if (file != null) {
            this.patientPhoto.setText(file.getAbsolutePath());
        }
    }

    @FXML
    private void showPicture(MouseEvent event) {
        this.progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    protected Void call() throws Exception {
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(new Runnable() {
                            public void run() {
                                try {
                                    try {
                                        try {
                                            DrugsPatients tab = (DrugsPatients) DrugsScreenPatientController.this.patientTable.getSelectionModel().getSelectedItem();
                                            DrugsPatients cont = new DrugsPatients();
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

            protected void succeeded() {
                DrugsScreenPatientController.this.progress.setVisible(false);
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void escortPhotoPick(MouseEvent event) {
        FileChooser fil_chooser = new FileChooser();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File file = fil_chooser.showOpenDialog((Window) st);

        if (file != null) {
            this.escortPhoto.setText(file.getAbsolutePath());
        }
    }

    @FXML
    private void escortNew(ActionEvent event) {
        clearEscort();
    }

    @FXML
    private void escortEdite(ActionEvent event) {
        if (this.escortName.getText().isEmpty() || this.escortName.getText() == null) {
            AlertDialogs.showError("اسم المرافق لا يجب ان يكون فارغا!!");
        } else {
            this.escortProgress.setVisible(true);
            Service<Void> service = new Service<Void>() {
                protected Task<Void> createTask() {
                    return new Task<Void>() {
                        protected Void call() throws Exception {
                            final CountDownLatch latch = new CountDownLatch(1);
                            Platform.runLater(new Runnable() {
                                public void run() {
                                    try {
                                        try {
                                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                            alert.setTitle("Editing Patient");
                                            alert.setHeaderText("سيتم تعديل بيانات المرافق");
                                            alert.setContentText("هل انت متاكد؟");

                                            Optional<ButtonType> result = alert.showAndWait();
                                            if (result.get() == ButtonType.OK) {
                                                if (DrugsScreenPatientController.this.escortPhoto.getText().isEmpty() || DrugsScreenPatientController.this.escortPhoto.getText() == null) {
                                                    DrugsPatientsEscort patient = new DrugsPatientsEscort();
                                                    patient.setId(Integer.parseInt(DrugsScreenPatientController.this.escortId.getText()));
                                                    patient.setPatientId(((DrugsPatients) DrugsScreenPatientController.this.patientTable.getSelectionModel().getSelectedItem()).getId());
                                                    patient.setName(DrugsScreenPatientController.this.escortName.getText());
                                                    patient.setRelation(DrugsScreenPatientController.this.escortRelation.getText());
                                                    patient.setAddress(DrugsScreenPatientController.this.escortAddress.getText());
                                                    patient.setNationaId(DrugsScreenPatientController.this.escortNational.getText());
                                                    patient.setTele1(DrugsScreenPatientController.this.escortTele1.getText());
                                                    patient.setTele2(DrugsScreenPatientController.this.escortTele2.getText());
                                                    patient.EditeWithoutPhoto();
                                                } else {
                                                    DrugsPatientsEscort patient = new DrugsPatientsEscort();
                                                    patient.setId(Integer.parseInt(DrugsScreenPatientController.this.escortId.getText()));
                                                    patient.setPatientId(((DrugsPatients) DrugsScreenPatientController.this.patientTable.getSelectionModel().getSelectedItem()).getId());
                                                    patient.setName(DrugsScreenPatientController.this.escortName.getText());
                                                    patient.setRelation(DrugsScreenPatientController.this.escortRelation.getText());
                                                    patient.setAddress(DrugsScreenPatientController.this.escortAddress.getText());
                                                    patient.setNationaId(DrugsScreenPatientController.this.escortNational.getText());
                                                    patient.setTele1(DrugsScreenPatientController.this.escortTele1.getText());
                                                    patient.setTele2(DrugsScreenPatientController.this.escortTele2.getText());

                                                    InputStream in = new FileInputStream(new File(DrugsScreenPatientController.this.escortPhoto.getText()));
                                                    patient.setPhoto(in);

                                                    String[] st = DrugsScreenPatientController.this.escortPhoto.getText().split(Pattern.quote("."));
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

                protected void succeeded() {
                    DrugsScreenPatientController.this.clearEscort();
                    DrugsScreenPatientController.this.clear();
                    DrugsScreenPatientController.this.getDataToTable();
                    DrugsScreenPatientController.this.escortProgress.setVisible(false);
                    super.succeeded();
                }
            };
            service.start();
        }
    }

    @FXML
    private void escortDelete(ActionEvent event) {
        if (this.escortName.getText().isEmpty() || this.escortName.getText() == null) {
            AlertDialogs.showError("اسم المرافق لا يجب ان يكون فارغا!!");
        } else {
            this.escortProgress.setVisible(true);
            Service<Void> service = new Service<Void>() {
                protected Task<Void> createTask() {
                    return new Task<Void>() {
                        protected Void call() throws Exception {
                            final CountDownLatch latch = new CountDownLatch(1);
                            Platform.runLater(new Runnable() {
                                public void run() {
                                    try {
                                        try {
                                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                            alert.setTitle("Deleting Patient");
                                            alert.setHeaderText("سيتم حذف بيانات المرافق");
                                            alert.setContentText("هل انت متاكد؟");

                                            Optional<ButtonType> result = alert.showAndWait();
                                            if (result.get() == ButtonType.OK) {
                                                DrugsPatientsEscort patient = new DrugsPatientsEscort();
                                                patient.setId(Integer.parseInt(DrugsScreenPatientController.this.escortId.getText()));

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

                protected void succeeded() {
                    DrugsScreenPatientController.this.clearEscort();
                    DrugsScreenPatientController.this.clear();
                    DrugsScreenPatientController.this.getDataToTable();
                    DrugsScreenPatientController.this.escortProgress.setVisible(false);
                    super.succeeded();
                }
            };
            service.start();
        }
    }

    @FXML
    private void escortAdd(ActionEvent event) {
        if (this.escortName.getText().isEmpty() || this.escortName.getText() == null) {
            AlertDialogs.showError("اسم المرافق لا يجب ان يكون فارغا!!");
        } else {
            this.escortProgress.setVisible(true);
            Service<Void> service = new Service<Void>() {
                protected Task<Void> createTask() {
                    return new Task<Void>() {
                        protected Void call() throws Exception {
                            final CountDownLatch latch = new CountDownLatch(1);
                            Platform.runLater(new Runnable() {
                                public void run() {
                                    try {
                                        try {
                                            if (DrugsScreenPatientController.this.escortPhoto.getText().isEmpty() || DrugsScreenPatientController.this.escortPhoto.getText() == null) {
                                                DrugsPatientsEscort patient = new DrugsPatientsEscort();
                                                patient.setId(Integer.parseInt(DrugsScreenPatientController.this.escortId.getText()));
                                                patient.setPatientId(((DrugsPatients) DrugsScreenPatientController.this.patientTable.getSelectionModel().getSelectedItem()).getId());
                                                patient.setName(DrugsScreenPatientController.this.escortName.getText());
                                                patient.setRelation(DrugsScreenPatientController.this.escortRelation.getText());
                                                patient.setAddress(DrugsScreenPatientController.this.escortAddress.getText());
                                                patient.setNationaId(DrugsScreenPatientController.this.escortNational.getText());
                                                patient.setTele1(DrugsScreenPatientController.this.escortTele1.getText());
                                                patient.setTele2(DrugsScreenPatientController.this.escortTele2.getText());
                                                patient.AddWithoutPhoto();
                                            } else {
                                                DrugsPatientsEscort patient = new DrugsPatientsEscort();
                                                patient.setId(Integer.parseInt(DrugsScreenPatientController.this.escortId.getText()));
                                                patient.setPatientId(((DrugsPatients) DrugsScreenPatientController.this.patientTable.getSelectionModel().getSelectedItem()).getId());
                                                patient.setName(DrugsScreenPatientController.this.escortName.getText());
                                                patient.setRelation(DrugsScreenPatientController.this.escortRelation.getText());
                                                patient.setAddress(DrugsScreenPatientController.this.escortAddress.getText());
                                                patient.setNationaId(DrugsScreenPatientController.this.escortNational.getText());
                                                patient.setTele1(DrugsScreenPatientController.this.escortTele1.getText());
                                                patient.setTele2(DrugsScreenPatientController.this.escortTele2.getText());

                                                InputStream in = new FileInputStream(new File(DrugsScreenPatientController.this.escortPhoto.getText()));
                                                patient.setPhoto(in);

                                                String[] st = DrugsScreenPatientController.this.escortPhoto.getText().split(Pattern.quote("."));
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

                protected void succeeded() {
                    DrugsScreenPatientController.this.clearEscort();
                    DrugsScreenPatientController.this.clear();
                    DrugsScreenPatientController.this.getDataToTable();
                    DrugsScreenPatientController.this.escortProgress.setVisible(false);
                    super.succeeded();
                }
            };
            service.start();
        }
    }

    private void clearEscort() {
        try {
            if (this.patientTable.getSelectionModel().getSelectedIndex() == -1) {
                this.escortTable.setItems(null);
            } else {
                this.escortTable.setItems(DrugsPatientsEscort.getData(Integer.toString(((DrugsPatients) this.patientTable.getSelectionModel().getSelectedItem()).getId())));
            }

            setEscortAutoNum();
            this.escortProgress.setVisible(false);
            this.escortAdd.setDisable(false);
            this.escortEdite.setDisable(true);
            this.escortDelete.setDisable(true);
            this.escortNew.setDisable(true);

            this.escortName.setText("");
            this.escortRelation.setText("");
            this.escortAddress.setText("");
            this.escortNational.setText("");
            this.escortPhoto.setText("");
            this.escortTele1.setText("");
            this.escortTele2.setText("");
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    private void setEscortAutoNum() throws Exception {
        this.escortId.setText(DrugsPatientsEscort.getAutoNum());
    }

    @FXML
    private void showEscortPicture(MouseEvent event) {
        this.progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    protected Void call() throws Exception {
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(new Runnable() {
                            public void run() {
                                try {
                                    try {
                                        try {
                                            DrugsPatientsEscort tab = (DrugsPatientsEscort) DrugsScreenPatientController.this.escortTable.getSelectionModel().getSelectedItem();
                                            DrugsPatientsEscort cont = new DrugsPatientsEscort();
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

            protected void succeeded() {
                DrugsScreenPatientController.this.progress.setVisible(false);
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void searchEscort(KeyEvent event) {
        FilteredList<DrugsPatientsEscort> filteredData = new FilteredList(this.Escortitems, p -> true);

        filteredData.setPredicate(pa -> {
            if (this.searchEscort.getText() == null || this.searchEscort.getText().isEmpty()) {
                return true;
            }

            String lowerCaseFilter = this.searchEscort.getText().toLowerCase();

            return (pa.getName().contains(lowerCaseFilter) || pa.getAddress().contains(lowerCaseFilter) || pa.getTele1().contains(lowerCaseFilter) || pa.getTele2().contains(lowerCaseFilter));
        });

        SortedList<DrugsPatientsEscort> sortedData = new SortedList((ObservableList) filteredData);
        sortedData.comparatorProperty().bind((ObservableValue) this.escortTable.comparatorProperty());
        this.escortTable.setItems((ObservableList) sortedData);
    }

    @FXML
    private void addTranspering(MouseEvent event) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Add Org Name");
        dialog.setHeaderText("اضافة اسم المؤسسة");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (((String) result.get()).isEmpty() || result.get() == null) {
                AlertDialogs.showError("خطا!! يرجي ادخال اسم المؤسسة");
            } else {
                String results = result.get();
                try {
                    Service service = new Service() {
                        protected Task createTask() {
                            return new Task() {
                                protected Object call() throws Exception {
                                    CountDownLatch latch = new CountDownLatch(1);
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

                                protected void succeeded() {
                                    try {
                                        DrugsScreenPatientController.this.fillCombo();
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
}
