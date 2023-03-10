package screens.drugs;

import assets.classes.AlertDialogs;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXTextField;
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
import javafx.collections.FXCollections;
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
    @FXML
    private TableColumn<DrugsPatients,String> patientTabDateIn;
    @FXML
    private TableColumn<DrugsPatients,String> patientTabDateOut;

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
                                    clear();
                                    setTableColumns();
                                    getDataToTable();
                                    fillCombo();

                                    double drawerx = drawer.getLayoutX();
                                    double drawery = drawer.getLayoutY();
                                    drawer.setLayoutX(drawerx + 600.0D);
                                    drawer.setLayoutY(drawery);
                                    drawer.setVisible(false);
                                    drawer.setMaxWidth(0.0D);

                                    drawer.setOnDrawerOpening(e -> {
                                        drawer.setLayoutX(drawerx);

                                        drawer.setLayoutY(drawery);

                                        drawer.setMaxWidth(600.0D);
                                    });
                                    drawer.setOnDrawerClosed(e -> {
                                        drawer.setLayoutX(drawerx + 600.0D);

                                        drawer.setLayoutY(drawery);

                                        drawer.setVisible(false);
                                        drawer.setMaxWidth(0.0D);
                                    });
                                    ham.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
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

            protected void succeeded() {
                progress.setVisible(false);
                super.succeeded();
            }
        };
        service.start();

        this.patientTable.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (this.patientTable.getSelectionModel().getSelectedIndex() == -1) {
                this.escortTable.setItems(null);
            } else {
                patientAdd.setDisable(true);

                patientEdite.setDisable(false);

                patientDelete.setDisable(false);

                patientNew.setDisable(false);
                DrugsPatients pa = patientTable.getSelectionModel().getSelectedItem();
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
                ObservableList<Doctors> items1 = this.patientDoctor.getItems();
                for (Doctors a : items1) {
                    if (a.getName().equals(pa.getDoctor_name())) {
                        patientDoctor.getSelectionModel().select(a);
                    }
                }
                try {
                    escortProgress.setVisible(true);
                    escortTable.setItems(DrugsPatientsEscort.getData(Integer.toString(pa.getId())));
                    Escortitems = this.escortTable.getItems();
                    clearEscort();
                    escortProgress.setVisible(false);
                } catch (Exception ex) {
                    AlertDialogs.showErrors(ex);
                }
            }
        });
        escortTable.setOnMouseClicked(e -> {
            if (escortTable.getSelectionModel().getSelectedIndex() != -1) {
                DrugsPatientsEscort ps = escortTable.getSelectionModel().getSelectedItem();
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

    private void fillCombo() throws Exception {
        patientGender.getItems().add("ذكر");
        patientGender.getItems().add("انثي");
        Service<Void> service = new Service<Void>() {
            ObservableList<TransferOrganization> transferData;
            ObservableList<TransferOrganization> transferDataSearch;
            ObservableList<Doctors> docData;
            ObservableList<Doctors> docDataSearch;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {

                            transferData = TransferOrganization.getData();
                            docData = Doctors.getData();

                        } catch (Exception ex) {
                            AlertDialogs.showErrors(ex);
                        }
                        return null;
                    }
                };

            }

            @Override
            protected void succeeded() {

                patientDoctor.setItems(docData);
                patientDoctor.setEditable(true);
                patientDoctor.setOnKeyReleased((event) -> {

                    if (patientDoctor.getEditor().getText().length() == 0) {
                        patientDoctor.setItems(docData);
                    } else {
                        docDataSearch = FXCollections.observableArrayList();

                        for (Doctors a : docData) {
                            if (a.getName().contains(patientDoctor.getEditor().getText())) {
                                docDataSearch.add(a);
                            }
                        }
                        patientDoctor.setItems(docDataSearch);
                        patientDoctor.show();
                    }
                });
                patientDoctor.setConverter(new StringConverter<Doctors>() {
                    public String toString(Doctors contract) {
                        return contract.getName();
                    }

                    public Doctors fromString(String string) {
                        return null;
                    }
                });
                patientDoctor.setCellFactory(cell -> new ListCell<Doctors>() {

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

                            lblid.setText("م: " + Integer.toString(person.getId()));
                            lblName.setText("الاسم: " + person.getName());
                            lblQuali.setText("التخصص: " + person.getQualification_name());

                            setGraphic((Node) this.gridPane);
                        } else {

                            setGraphic(null);
                        }
                    }
                });
                patientTransName.setItems(transferData);
                patientTransName.setEditable(true);
                patientTransName.setOnKeyReleased((event) -> {

                    if (patientTransName.getEditor().getText().length() == 0) {
                        patientTransName.setItems(transferData);
                    } else {
                        transferDataSearch = FXCollections.observableArrayList();

                        for (TransferOrganization a : transferData) {
                            if (a.getName().contains(patientTransName.getEditor().getText())) {
                                transferDataSearch.add(a);
                            }
                        }
                        patientTransName.setItems(transferDataSearch);
                        patientTransName.show();
                    }
                });
                patientTransName.setConverter(new StringConverter<TransferOrganization>() {
                    public String toString(TransferOrganization contract) {
                        return contract.getName();
                    }

                    public TransferOrganization fromString(String string) {
                        return null;
                    }
                });
                patientTransName.setCellFactory(cell -> new ListCell<TransferOrganization>() {

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

                            lblid.setText("م: " + Integer.toString(person.getId()));
                            lblName.setText("الاسم: " + person.getName());

                            setGraphic((Node) this.gridPane);
                        } else {

                            setGraphic(null);
                        }
                    }
                });
                super.succeeded();
            }
        };
        service.start();

    }

    @FXML
    private void patientNew(ActionEvent event) {
        clear();
    }

    @FXML
    private void patientEdite(ActionEvent event) {
        if (patientName.getText().isEmpty() || this.patientName.getText() == null) {
            AlertDialogs.showError("اسم المريض لا يجب ان يكون فارغا!!");
        } else {
            progress.setVisible(true);
            Service<Void> service = new Service<Void>() {
                boolean isDone = false;
                DrugsPatients patient = new DrugsPatients();

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
                                                if (patientPhoto.getText().isEmpty() || patientPhoto.getText() == null) {
                                                    patient.setId(Integer.parseInt(patientId.getText()));
                                                    patient.setName(patientName.getText());
                                                    patient.setAddress(patientAddress.getText());
                                                    patient.setAge(patientAge.getText());
                                                    patient.setNational_id(patientNational.getText());
                                                    patient.setDoctor_id(patientDoctor.getItems().get(patientDoctor.getSelectionModel().getSelectedIndex()).getId());
                                                    patient.setDiagnosis(patientGiagnose.getText());
                                                    patient.setGovernment(patientGovernment.getText());
                                                    DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                                    patient.setDateOfBirth(patientDateOfBirth.getValue().format(f));
                                                    patient.setTranportOrgId(patientTransName.getItems().get(patientTransName.getSelectionModel().getSelectedIndex()).getId());
                                                    patient.setTele1(patientTele1.getText());
                                                    patient.setTele2(patientTele2.getText());
                                                    patient.setGender(patientGender.getSelectionModel().getSelectedItem());
                                                    patient.Edite();
                                                    isDone = true;
                                                } else {
                                                    patient.setId(Integer.parseInt(patientId.getText()));
                                                    patient.setName(patientName.getText());
                                                    patient.setAddress(patientAddress.getText());
                                                    patient.setAge(patientAge.getText());
                                                    patient.setNational_id(patientNational.getText());
                                                    patient.setDoctor_id(patientDoctor.getItems().get(patientDoctor.getSelectionModel().getSelectedIndex()).getId());
                                                    patient.setDiagnosis(patientGiagnose.getText());
                                                    patient.setTele1(patientTele1.getText());
                                                    patient.setTele2(patientTele2.getText());
                                                    patient.setGovernment(patientGovernment.getText());
                                                    DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                                    patient.setDateOfBirth(patientDateOfBirth.getValue().format(f));
                                                    patient.setTranportOrgId(patientTransName.getItems().get(patientTransName.getSelectionModel().getSelectedIndex()).getId());
                                                    InputStream in = new FileInputStream(new File(patientPhoto.getText()));
                                                    patient.setPhoto(in);

                                                    String[] st = patientPhoto.getText().split(Pattern.quote("."));
                                                    patient.setPhotoExt(st[st.length - 1]);
                                                    patient.setGender(patientGender.getSelectionModel().getSelectedItem());
                                                    patient.EditeWithPhoto();
                                                    isDone = true;
                                                }
                                            }
                                        } catch (Exception ex) {
                                            AlertDialogs.showErrors(ex);
                                            isDone = false;
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
                    if (isDone) {
                        clear();
                    } else {
                        clear(patient);
                    }

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
        if (this.patientName.getText().isEmpty() || this.patientName.getText() == null) {
            AlertDialogs.showError("اسم المريض لا يجب ان يكون فارغا!!");
        } else {
            this.progress.setVisible(true);
            Service<Void> service = new Service<Void>() {
                boolean isDone = false;
                DrugsPatients patient = new DrugsPatients();

                protected Task<Void> createTask() {
                    return new Task<Void>() {
                        protected Void call() throws Exception {
                            final CountDownLatch latch = new CountDownLatch(1);
                            Platform.runLater(new Runnable() {
                                public void run() {
                                    try {
                                        try {
                                            if (patientPhoto.getText().isEmpty() || patientPhoto.getText() == null) {
                                                patient.setId(Integer.parseInt(patientId.getText()));
                                                patient.setName(patientName.getText());
                                                patient.setAddress(patientAddress.getText());
                                                patient.setAge(patientAge.getText());
                                                patient.setNational_id(patientNational.getText());
                                                patient.setDoctor_id(patientDoctor.getItems().get(patientDoctor.getSelectionModel().getSelectedIndex()).getId());
                                                patient.setDiagnosis(patientGiagnose.getText());
                                                patient.setGovernment(patientGovernment.getText());
                                                DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                                patient.setDateOfBirth(patientDateOfBirth.getValue().format(f));
                                                patient.setTranportOrgId(patientTransName.getItems().get(patientTransName.getSelectionModel().getSelectedIndex()).getId());
                                                patient.setTele1(patientTele1.getText());
                                                patient.setTele2(patientTele2.getText());
                                                patient.setGender(patientGender.getSelectionModel().getSelectedItem());
                                                patient.Add();
                                                isDone = true;
                                            } else {
                                                patient.setId(Integer.parseInt(patientId.getText()));
                                                patient.setName(patientName.getText());
                                                patient.setAddress(patientAddress.getText());
                                                patient.setAge(patientAge.getText());
                                                patient.setNational_id(patientNational.getText());
                                                patient.setDoctor_id(patientDoctor.getItems().get(patientDoctor.getSelectionModel().getSelectedIndex()).getId());
                                                patient.setDiagnosis(patientGiagnose.getText());
                                                patient.setTele1(patientTele1.getText());
                                                patient.setTele2(patientTele2.getText());
                                                patient.setGovernment(patientGovernment.getText());
                                                DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                                patient.setDateOfBirth(patientDateOfBirth.getValue().format(f));
                                                patient.setTranportOrgId(patientTransName.getItems().get(patientTransName.getSelectionModel().getSelectedIndex()).getId());
                                                InputStream in = new FileInputStream(new File(patientPhoto.getText()));
                                                patient.setPhoto(in);

                                                String[] st = patientPhoto.getText().split(Pattern.quote("."));
                                                patient.setPhotoExt(st[st.length - 1]);
                                                patient.setGender(patientGender.getSelectionModel().getSelectedItem());
                                                patient.AddWithPhoto();
                                                isDone = true;
                                            }
                                            DrugsAccounts da = new DrugsAccounts();
                                            da.setId(Integer.parseInt(DrugsAccounts.getAutoNum()));
                                            DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                            da.setDateOfEntrance(LocalDate.now().format(f));
                                            da.setPaitent_id(Integer.parseInt(patientId.getText()));
                                            da.setTotal_paied("0");
                                            da.setTotal_spended("0");
                                            da.setRemaining("0");
                                            da.Add();
                                            isDone = true;
                                        } catch (Exception ex) {
                                            AlertDialogs.showErrors(ex);
                                            isDone = false;
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
                    if (isDone) {
                        clear();
                    } else {
                        clear(patient);
                    }
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
        this.progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            boolean isDone = false;
            DrugsPatients patient = new DrugsPatients();

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
                                            patient.setId(Integer.parseInt(patientId.getText()));
                                            patient.Delete();
                                            DrugsAccounts da = new DrugsAccounts();
                                            da.setPaitent_id(Integer.parseInt(patientId.getText()));
                                            da.Delete();
                                            isDone = true;
                                        }

                                    } catch (Exception ex) {
                                        AlertDialogs.showErrors(ex);
                                        isDone = false;
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
                if (isDone) {
                    clear();
                } else {
                    clear(patient);
                }
                getDataToTable();
                progress.setVisible(false);
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
        patientDoctor.getEditor().setText("");
        this.patientDateOfBirth.setValue(null);
        this.patientGovernment.setText("");
        this.patientGender.getSelectionModel().clearSelection();
        this.patientTransName.getSelectionModel().clearSelection();
        patientTransName.getEditor().setText("");
        this.patientTele1.setText("");
        this.patientTele2.setText("");
        this.patientAdd.setDisable(false);
        this.patientEdite.setDisable(true);
        this.patientDelete.setDisable(true);
        this.patientNew.setDisable(true);
    }

    private void clear(DrugsPatients pa) {
        setAutoNumber();
        this.patientName.setText(pa.getName());
        this.patientAddress.setText(pa.getAddress());
        this.patientAge.setText(pa.getAge());
        this.patientNational.setText(pa.getNational_id());
        this.patientGiagnose.setText(pa.getDiagnosis());

        ObservableList<TransferOrganization> items = this.patientTransName.getItems();
        for (TransferOrganization a : items) {
            if (a.getName().equals(pa.getTranportOrg())) {
                this.patientTransName.getSelectionModel().select(a);
            }
        }
        ObservableList<Doctors> items1 = this.patientDoctor.getItems();
        for (Doctors a : items1) {
            if (a.getId() == pa.getDoctor_id()) {
                this.patientDoctor.getSelectionModel().select(a);
            }
        }
        try {

            this.patientDateOfBirth.setValue(LocalDate.parse(pa.getDateOfBirth()));
        } catch (Exception e) {
        }
        this.patientGovernment.setText(pa.getGovernment());
        this.patientGender.getSelectionModel().select(pa.getGender());
        this.patientTele1.setText(pa.getTele1());
        this.patientTele2.setText(pa.getTele2());
        this.patientAdd.setDisable(false);
        this.patientEdite.setDisable(true);
        this.patientDelete.setDisable(true);
        this.patientNew.setDisable(true);
    }

    private void setTableColumns() {
        this.patientTabId.setCellValueFactory(new PropertyValueFactory("id"));
        this.patientTabName.setCellValueFactory(new PropertyValueFactory("name"));
        this.patientTabDiagnoses.setCellValueFactory(new PropertyValueFactory("diagnosis"));
        this.patientTabDoctor.setCellValueFactory(new PropertyValueFactory("doctor_name"));
        this.patientTabTele1.setCellValueFactory(new PropertyValueFactory("tele1"));
        this.patientTabTele2.setCellValueFactory(new PropertyValueFactory("tele2"));
        this.patientTabDateIn.setCellValueFactory(new PropertyValueFactory("dateOfEntrance"));
        this.patientTabDateOut.setCellValueFactory(new PropertyValueFactory("dateOfExit"));
        
        this.escortTabNational.setCellValueFactory(new PropertyValueFactory("id"));
        this.escortTabName.setCellValueFactory(new PropertyValueFactory("name"));
        this.escortTabId.setCellValueFactory(new PropertyValueFactory("id"));
        this.escortTabTele2.setCellValueFactory(new PropertyValueFactory("tele2"));
        this.escortTabTele1.setCellValueFactory(new PropertyValueFactory("tele1"));
        this.escortTabAddress.setCellValueFactory(new PropertyValueFactory("Address"));
        this.escortTabRelation.setCellValueFactory(new PropertyValueFactory("relation"));
    }

    private void getDataToTable() {
        this.progress.setVisible(true);
        Service<Void> service = new Service<Void>() {ObservableList<DrugsPatients> overrideData;
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    protected Void call() throws Exception {
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(new Runnable() {
                            public void run() {
                                try {
                                    try {
                                        overrideData = DrugsPatients.getOverrideData();
                                       
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
                 patientTable.setItems(overrideData);
                items = overrideData; 
                progress.setVisible(false);
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
        sortedData.comparatorProperty().bind(patientTable.comparatorProperty());
        patientTable.setItems((ObservableList) sortedData);
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
                                            DrugsPatients tab = patientTable.getSelectionModel().getSelectedItem();
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
                                                    patient.setPatientId(patientTable.getSelectionModel().getSelectedItem().getId());
                                                    patient.setName(escortName.getText());
                                                    patient.setRelation(escortRelation.getText());
                                                    patient.setAddress(escortAddress.getText());
                                                    patient.setNationaId(escortNational.getText());
                                                    patient.setTele1(escortTele1.getText());
                                                    patient.setTele2(escortTele2.getText());
                                                    patient.EditeWithoutPhoto();
                                                } else {
                                                    DrugsPatientsEscort patient = new DrugsPatientsEscort();
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
                                            if (escortPhoto.getText().isEmpty() || escortPhoto.getText() == null) {
                                                DrugsPatientsEscort patient = new DrugsPatientsEscort();
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
                                                DrugsPatientsEscort patient = new DrugsPatientsEscort();
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
            if (this.patientTable.getSelectionModel().getSelectedIndex() == -1) {
                this.escortTable.setItems(null);
            } else {
                this.escortTable.setItems(DrugsPatientsEscort.getData(Integer.toString(patientTable.getSelectionModel().getSelectedItem().getId())));
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
                                            DrugsPatientsEscort tab = escortTable.getSelectionModel().getSelectedItem();
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
                progress.setVisible(false);
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

    @FXML
    private void setBirthDateFromNational(KeyEvent event) {
        if (patientNational.getText().length() == 14) {
            String national = patientNational.getText();
            String year;
            if (national.substring(0, 1).equals("2")) {
                year = "19" + national.substring(1, 3);
            } else {
                year = "20" + national.substring(1, 3);
            }
            String month = national.substring(3, 5);
            String day = national.substring(5, 7);
            patientDateOfBirth.setValue(LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day)));
        }
    }

    @FXML
    private void setAgeFromBirth(ActionEvent event) {
        LocalDate lo = LocalDate.now();
        patientAge.setText(Integer.toString(lo.getYear() - patientDateOfBirth.getValue().getYear()));
    }

    @FXML
    private void setBithdateFromAge(KeyEvent event) {
        LocalDate lo = LocalDate.now();
        patientDateOfBirth.setValue(LocalDate.of(lo.getYear() - Integer.parseInt(patientAge.getText()), lo.getMonth(), lo.getDayOfMonth()));
    }

    @FXML
    private void setBirthDateFromNational(ActionEvent event) {
        if (patientNational.getText().length() == 14) {
            String national = patientNational.getText();
            String year;
            if (national.substring(0, 1).equals("2")) {
                year = "19" + national.substring(1, 3);
            } else {
                year = "20" + national.substring(1, 3);
            }
            String month = national.substring(3, 5);
            String day = national.substring(5, 7);
            patientDateOfBirth.setValue(LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day)));
        }
    }

    @FXML
    private void setBithdateFromAge(ActionEvent event) {
        LocalDate lo = LocalDate.now();
        patientDateOfBirth.setValue(LocalDate.of(lo.getYear() - Integer.parseInt(patientAge.getText()), lo.getMonth(), lo.getDayOfMonth()));
    }
}
