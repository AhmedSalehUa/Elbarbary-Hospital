/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.admission;

import assets.classes.AlertDialogs;
import assets.classes.ComboBoxAutoComplete;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;
import javax.imageio.ImageIO;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import screens.admission.assets.Admission;
import screens.admission.assets.AdmissionSpacfication;
import screens.admission.assets.AdmissionStatue;
import screens.admission.assets.AdmissionType;
import screens.mainDataScreen.assets.Contract;
import screens.mainDataScreen.assets.Patients;
import screens.reception.ReceptionScreenController;

/**
 * FXML Controller class
 *
 * @author ahmed
 */
public class AdmissionScreenController implements Initializable {

    Preferences prefs;

    @FXML
    private JFXTextField search;
    @FXML
    private TableView<Admission> admissionTable;
    @FXML
    private TableColumn<Admission, String> admissionTabCost;
    @FXML
    private TableColumn<Admission, String> admissionTabStatue;
    @FXML
    private TableColumn<Admission, String> admissionTabType;

    @FXML
    private TableColumn<Admission, String> admissionTabContract;
    @FXML
    private TableColumn<Admission, String> admissionTabPatient;
    @FXML
    private TableColumn<Admission, String> admissionTabId;
    @FXML
    private ProgressIndicator process;
    @FXML
    private Button admissionNew;
    @FXML
    private Button admissionDelete;
    @FXML
    private Button admissionEdite;
    @FXML
    private Button admissionAdd;
    @FXML
    private HBox admissionSection;
    @FXML
    private Label admissionId;
    @FXML
    private ComboBox<Patients> admissionPatient;
    @FXML
    private ComboBox<Contract> admissionContract;

    @FXML
    private ComboBox<AdmissionType> admissionType;
    @FXML
    private ComboBox<AdmissionStatue> admissionStatue;
    @FXML
    private HBox servicesSection;
    @FXML
    private Tab admissionServicesTab;
    @FXML
    private Tab admissionSurgeriesTab;
    @FXML
    private Tab admissionExaminationsTab;
    @FXML
    private TabPane admissionTabbed;
    @FXML
    private MaterialIconView admissionTypeAdd;
    @FXML
    private MaterialIconView admissionStatueAdd;
    @FXML
    private MaterialIconView admissionSpacficationAdd;
    @FXML
    private ComboBox<AdmissionSpacfication> admissionSpacfication;
    @FXML
    private TableColumn<Admission, String> admissionTabSpacification;
    @FXML
    private Tab admissionContractTab;
    @FXML
    private CheckBox withExciteDate;
    @FXML
    private CheckBox withSpacification;
    @FXML
    private CheckBox withEnterDate;
    @FXML
    private CheckBox withContract;
    @FXML
    private CheckBox withPatient;
    @FXML
    private JFXDatePicker admissionDateOfEnrance;
    @FXML
    private JFXDatePicker admissionDateOfExcite;
    @FXML
    private TableColumn<Admission, String> admissionTabDateOfEntrance;
    @FXML
    private TableColumn<Admission, String> admissionTabDateOfExite;
    @FXML
    private Tab admissionMedicineTab;
    @FXML
    private Tab admissionSurgMedTab;
    @FXML
    private ImageView print;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        admissionDateOfEnrance.setConverter(new StringConverter<LocalDate>() {
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
        admissionDateOfExcite.setConverter(new StringConverter<LocalDate>() {
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
        process.setVisible(true);
        admissionTabbed.setVisible(false);
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
                                    process.setVisible(true);

                                    admissionClear();
                                    setAdmissionTableColumns();

                                    getAdmissionDataToTable();
                                    fillCombos();

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
                process.setVisible(false);

                super.succeeded();
            }
        };
        service.start();
        admissionTable.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {

            if (admissionTable.getSelectionModel().getSelectedIndex() == -1) {
                admissionTabbed.setVisible(false);
            } else {
                process.setVisible(true);
                Service<Void> service2 = new Service<Void>() {
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
                                            configPanels();
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
                        process.setVisible(false);

                        super.succeeded();
                    }
                };
                service2.start();
                admissionAdd.setDisable(true);
                admissionEdite.setDisable(false);
                admissionDelete.setDisable(false);
                admissionNew.setDisable(false);

                Admission pa = admissionTable.getSelectionModel().getSelectedItem();
                admissionId.setText(Integer.toString(pa.getId()));

                ObservableList<Patients> items1 = admissionPatient.getItems();
                for (Patients a : items1) {
                    if (a.getName().equals(pa.getPatient_name())) {

                        admissionPatient.getSelectionModel().select(a);
                    }
                }

                ObservableList<Contract> items2 = admissionContract.getItems();
                for (Contract a : items2) {
                    if (a.getName().equals(pa.getContract_name())) {

                        admissionContract.getSelectionModel().select(a);
                    }
                }
                ObservableList<AdmissionType> items3 = admissionType.getItems();
                for (AdmissionType a : items3) {
                    if (a.getName().equals(pa.getType())) {

                        admissionType.getSelectionModel().select(a);
                    }
                }
                ObservableList<AdmissionStatue> items4 = admissionStatue.getItems();
                for (AdmissionStatue a : items4) {
                    if (a.getName().equals(pa.getStatue())) {

                        admissionStatue.getSelectionModel().select(a);
                    }
                }
                ObservableList<AdmissionSpacfication> items5 = admissionSpacfication.getItems();
                for (AdmissionSpacfication a : items5) {
                    if (a.getName().equals(pa.getSpacification())) {

                        admissionSpacfication.getSelectionModel().select(a);
                    }
                }

                admissionDateOfEnrance.setValue(LocalDate.parse(pa.getDateOfEntrance()));
                if (pa.getDateOfExite().equals("0000-00-00")) {
                } else {
                    admissionDateOfExcite.setValue(LocalDate.parse(pa.getDateOfExite()));
                }

            }
        });
    }

    private void admissionClear() {
        setAutoNumber();
        admissionAdd.setDisable(false);
        admissionEdite.setDisable(true);
        admissionDelete.setDisable(true);
        admissionNew.setDisable(true);
        admissionTabbed.setVisible(false);

        admissionPatient.getSelectionModel().select(-1);

        admissionContract.getSelectionModel().select(-1);

        admissionType.getSelectionModel().select(-1);
        admissionStatue.getSelectionModel().select(-1);
        admissionDateOfEnrance.setValue(null);
        admissionDateOfExcite.setValue(null);
        admissionExaminationsTab.setContent(null);
        admissionServicesTab.setContent(null);
        admissionSurgeriesTab.setContent(null);

    }

    private void setAutoNumber() {
        try {
            admissionId.setText(Admission.getAutoNum());
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    private void configPanels() throws Exception {
        Admission ad = admissionTable.getSelectionModel().getSelectedItem();
        admissionTabbed.setVisible(true);

        FXMLLoader l1 = new FXMLLoader(getClass().getResource("/screens/admission/AdmissionScreenExamination.fxml"));
        admissionExaminationsTab.setContent(l1.load());
        AdmissionScreenExaminationController controlle = l1.getController();
        controlle.initData(ad);
        controlle.setParentController(this);

        FXMLLoader l2 = new FXMLLoader(getClass().getResource("/screens/admission/AdmissionScreenServices.fxml"));
        admissionServicesTab.setContent(l2.load());
        AdmissionScreenServicesController controlle2 = l2.getController();
        controlle2.initData(ad);
        controlle2.setParentController(this);

        FXMLLoader l3 = new FXMLLoader(getClass().getResource("/screens/admission/AdmissionScreenSurgies.fxml"));
        admissionSurgeriesTab.setContent(l3.load());
        AdmissionScreenSurgiesController controlle3 = l3.getController();
        controlle3.initData(ad);
        controlle3.setParentController(this);

        FXMLLoader cz = new FXMLLoader(getClass().getResource("/screens/admission/AdmissionScreenContract.fxml"));
        admissionContractTab.setContent(cz.load());
        AdmissionScreenContractController controlle5 = cz.getController();
        controlle5.initData(ad);
        controlle5.setParentController(this);

        FXMLLoader l4 = new FXMLLoader(getClass().getResource("/screens/admission/AdmissionScreenMedicine.fxml"));
        admissionMedicineTab.setContent(l4.load());
        AdmissionScreenMedicineController controlle4 = l4.getController();
        controlle4.initData(ad);
        controlle4.setParentController(this);

        FXMLLoader l5 = new FXMLLoader(getClass().getResource("/screens/admission/AdmissionScreenSurMed.fxml"));
        admissionSurgMedTab.setContent(l5.load());
        AdmissionScreenSurMedController controlle6 = l5.getController();
        controlle6.initData(ad);
        controlle6.setParentController(this);
    }

    private void fillStatueCombos() throws Exception {
        admissionStatue.setItems(AdmissionStatue.getData());
        admissionStatue.setConverter(new StringConverter<AdmissionStatue>() {
            @Override
            public String toString(AdmissionStatue patient) {
                return patient.getName();
            }

            @Override
            public AdmissionStatue fromString(String string) {
                return null;
            }
        });
        admissionStatue.setCellFactory(cell -> new ListCell<AdmissionStatue>() {

            // Create our layout here to be reused for each ListCell
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

            // We override the updateItem() method in order to provide our own layout for this Cell's graphicProperty
            @Override
            protected void updateItem(AdmissionStatue person, boolean empty) {
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
    }

    private void fillTypeCombos() throws Exception {

        admissionType.setItems(AdmissionType.getData());
        admissionType.setConverter(new StringConverter<AdmissionType>() {
            @Override
            public String toString(AdmissionType patient) {
                return patient.getName();
            }

            @Override
            public AdmissionType fromString(String string) {
                return null;
            }
        });
        admissionType.setCellFactory(cell -> new ListCell<AdmissionType>() {

            // Create our layout here to be reused for each ListCell
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

            // We override the updateItem() method in order to provide our own layout for this Cell's graphicProperty
            @Override
            protected void updateItem(AdmissionType person, boolean empty) {
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
    }

    private void fillSpacificationCombos() throws Exception {

        admissionSpacfication.setItems(AdmissionSpacfication.getData());
        admissionSpacfication.setConverter(new StringConverter<AdmissionSpacfication>() {
            @Override
            public String toString(AdmissionSpacfication patient) {
                return patient.getName();
            }

            @Override
            public AdmissionSpacfication fromString(String string) {
                return null;
            }
        });
        admissionSpacfication.setCellFactory(cell -> new ListCell<AdmissionSpacfication>() {

            // Create our layout here to be reused for each ListCell
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

            // We override the updateItem() method in order to provide our own layout for this Cell's graphicProperty
            @Override
            protected void updateItem(AdmissionSpacfication person, boolean empty) {
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
    }

    private void fillCombos() throws Exception {
        fillStatueCombos();
        fillTypeCombos();
        fillSpacificationCombos();
        admissionPatient.setItems(Patients.getData());
        ComboBox<Patients> cmb = admissionPatient;
        cmb.setTooltip(new Tooltip());

        new ComboBoxAutoComplete(cmb);
        admissionContract.setItems(Contract.getData());
        admissionContract.setConverter(new StringConverter<Contract>() {
            @Override
            public String toString(Contract contract) {
                return contract.getName();
            }

            @Override
            public Contract fromString(String string) {
                return null;
            }
        });
        admissionContract.setCellFactory(cell -> new ListCell<Contract>() {

            GridPane gridPane = new GridPane();
            Label lblid = new Label();
            Label lblName = new Label();
            Label lblAge = new Label();

            {

                gridPane.getColumnConstraints().addAll(
                        new ColumnConstraints(100, 100, 100),
                        new ColumnConstraints(100, 100, 100),
                        new ColumnConstraints(100, 100, 100)
                );

                gridPane.add(lblid, 0, 1);
                gridPane.add(lblName, 1, 1);
                gridPane.add(lblAge, 2, 1);

            }

            @Override
            protected void updateItem(Contract person, boolean empty) {
                super.updateItem(person, empty);

                if (!empty && person != null) {

                    // Update our Labels
                    lblid.setText("م: " + Integer.toString(person.getId()));
                    lblName.setText("الاسم: " + person.getName());
                    lblAge.setText("نسبة الخصم: " + person.getDiscount() + "%");

                    // Set this ListCell's graphicProperty to display our GridPane
                    setGraphic(gridPane);
                } else {
                    // Nothing to display here
                    setGraphic(null);
                }
            }
        });
    }

    private void setAdmissionTableColumns() {
        admissionTabSpacification.setCellValueFactory(new PropertyValueFactory<>("spacification"));
        admissionTabDateOfEntrance.setCellValueFactory(new PropertyValueFactory<>("dateOfEntrance"));
        admissionTabDateOfExite.setCellValueFactory(new PropertyValueFactory<>("dateOfExite"));
        admissionTabCost.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        admissionTabStatue.setCellValueFactory(new PropertyValueFactory<>("statue"));
        admissionTabType.setCellValueFactory(new PropertyValueFactory<>("type"));
        admissionTabContract.setCellValueFactory(new PropertyValueFactory<>("contract_name"));
        admissionTabPatient.setCellValueFactory(new PropertyValueFactory<>("patient_name"));
        admissionTabId.setCellValueFactory(new PropertyValueFactory<>("id"));
    }

    public void getAdmissionDataToTable() {
        process.setVisible(true);
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        //Background work                       
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(() -> {
                            try {
                                try {
                                    admissionTable.setItems(Admission.getData());
                                } catch (Exception ex) {
                                    AlertDialogs.showErrors(ex);
                                }
                            } finally {
                                latch.countDown();
                            }
                        });
                        latch.await();

                        return null;
                    }
                };
            }

            @Override
            protected void succeeded() {

                items = admissionTable.getItems();
                process.setVisible(false);
                super.succeeded();
            }
        };
        service.start();
    }
    ObservableList<Admission> items;

    @FXML
    private void search(KeyEvent event) {
        FilteredList<Admission> filteredData = new FilteredList<>(items, p -> true);

        filteredData.setPredicate(pa -> {

            if (search.getText() == null || search.getText().isEmpty()) {
                return true;
            }

            String lowerCaseFilter = search.getText().toLowerCase();

            return (pa.getPatient_name().contains(lowerCaseFilter) && withPatient.isSelected())
                    || (pa.getContract_name().contains(lowerCaseFilter) && withContract.isSelected())
                    || (pa.getDateOfEntrance().contains(lowerCaseFilter) && withEnterDate.isSelected())
                    || (pa.getDateOfExite().contains(lowerCaseFilter) && withExciteDate.isSelected())
                    || (pa.getSpacification().contains(lowerCaseFilter) && withSpacification.isSelected());

        });

        SortedList< Admission> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(admissionTable.comparatorProperty());
        admissionTable.setItems(sortedData);
    }

    @FXML
    private void admissionNew(ActionEvent event) {
        admissionClear();
    }

    @FXML
    private void admissionDelete(ActionEvent event) {
        process.setVisible(true);
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
                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setTitle("Deleting Admission");
                                        alert.setHeaderText("سيتم حذف التذكرة");
                                        alert.setContentText("هل انت متاكد؟");

                                        Optional<ButtonType> result = alert.showAndWait();
                                        if (result.get() == ButtonType.OK) {
                                            Admission ad = new Admission();
                                            ad.setId(Integer.parseInt(admissionId.getText()));
                                            ad.Delete();
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
                admissionClear();
                getAdmissionDataToTable();
                process.setVisible(false);
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void admissionEdite(ActionEvent event) {
        if (admissionPatient.getSelectionModel().getSelectedIndex() == -1
                || admissionContract.getSelectionModel().getSelectedIndex() == -1
                || admissionStatue.getSelectionModel().getSelectedIndex() == -1 || admissionSpacfication.getSelectionModel().getSelectedIndex() == -1
                || admissionType.getSelectionModel().getSelectedIndex() == -1) {
            AlertDialogs.showError("يوجد بيانات فارغة !!");
        } else {
            process.setVisible(true);
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
                                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                            alert.setTitle("Editting Admission");
                                            alert.setHeaderText("سيتم تعديل بيانات التذكرة");
                                            alert.setContentText("هل انت متاكد؟");

                                            Optional<ButtonType> result = alert.showAndWait();
                                            if (result.get() == ButtonType.OK) {
                                                Admission ad = new Admission();
                                                ad.setId(Integer.parseInt(admissionId.getText()));
                                                ad.setPatient_id(admissionPatient.getSelectionModel().getSelectedItem().getId());
                                                ad.setContract_id(admissionContract.getSelectionModel().getSelectedItem().getId());
                                                ad.setStatue_id(admissionStatue.getSelectionModel().getSelectedItem().getId());
                                                ad.setType_id(admissionType.getSelectionModel().getSelectedItem().getId());
                                                ad.setSpacification_id(admissionSpacfication.getSelectionModel().getSelectedItem().getId());
                                                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                                ad.setDateOfEntrance(admissionDateOfEnrance.getValue().format(format));
                                                ad.setDateOfExite(admissionDateOfExcite.getValue().format(format));
                                                ad.Edite();
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
                    admissionClear();
                    getAdmissionDataToTable();
                    process.setVisible(false);
                    super.succeeded();
                }
            };
            service.start();
        }
    }

    @FXML
    private void admissionAdd(ActionEvent event) {
        if (admissionPatient.getSelectionModel().getSelectedIndex() == -1
                || admissionContract.getSelectionModel().getSelectedIndex() == -1 || admissionSpacfication.getSelectionModel().getSelectedIndex() == -1
                || admissionStatue.getSelectionModel().getSelectedIndex() == -1
                || admissionType.getSelectionModel().getSelectedIndex() == -1) {
            AlertDialogs.showError("يوجد بيانات فارغة !!");
        } else {
            process.setVisible(true);
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
                                            Admission ad = new Admission();
                                            ad.setId(Integer.parseInt(admissionId.getText()));
                                            ad.setPatient_id(admissionPatient.getSelectionModel().getSelectedItem().getId());
                                            ad.setContract_id(admissionContract.getSelectionModel().getSelectedItem().getId());
                                            ad.setStatue_id(admissionStatue.getSelectionModel().getSelectedItem().getId());
                                            ad.setType_id(admissionType.getSelectionModel().getSelectedItem().getId());
                                            ad.setSpacification_id(admissionSpacfication.getSelectionModel().getSelectedItem().getId());
                                            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                            ad.setDateOfEntrance(admissionDateOfEnrance.getValue().format(format));
                                            ad.setDateOfExite(admissionDateOfExcite.getValue().format(format));
                                            ad.Add();
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
                    admissionClear();
                    getAdmissionDataToTable();
                    process.setVisible(false);
                    super.succeeded();
                }
            };
            service.start();
        }
    }

    void setSlectedItem(Admission admission) {

        ObservableList<Admission> items1 = admissionTable.getItems();
        for (Admission a : items1) {

            if (a.getId() == admission.getId()) {

                admissionTable.getSelectionModel().clearSelection();
                admissionTable.getSelectionModel().select(a);

            }
        }
    }

    @FXML
    private void admissionTypeAdd(MouseEvent event) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Add Type Name");
        dialog.setHeaderText("اضافة نوع التذكرة");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (result.get().isEmpty() || result.get() == null) {
                AlertDialogs.showError("خطا!! يرجي ادخال اسم نوع");
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
                                            AdmissionType.Add(results);
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
                                        fillTypeCombos();
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
    private void admissionStatueAdd(MouseEvent event) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Add Statue Name");
        dialog.setHeaderText("اضافة حالة التذكرة");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (result.get().isEmpty() || result.get() == null) {
                AlertDialogs.showError("خطا!! يرجي ادخال اسم حالة");
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
                                            AdmissionStatue.Add(results);
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
                                        fillStatueCombos();
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
    private void admissionSpacficationAdd(MouseEvent event) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Add Spacification Name");
        dialog.setHeaderText("اضافة تخصص التذكرة");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (result.get().isEmpty() || result.get() == null) {
                AlertDialogs.showError("خطا!! يرجي ادخال اسم تخصص");
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
                                            AdmissionSpacfication.Add(results);
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
                                        fillSpacificationCombos();
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

    public void updateParent() {
        try {
            receptionScreenController.configPanels();
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }
    ReceptionScreenController receptionScreenController;

    public void setrecptionController(ReceptionScreenController receptionScreenController) {
        this.receptionScreenController = receptionScreenController;
    }

    @FXML
    private void print(MouseEvent event) {
        if (admissionTable.getSelectionModel().getSelectedIndex() == -1) {
            AlertDialogs.showError("اختار التذكرة اولا");
        } else {

            Service<Void> service = new Service<Void>() {
                @Override
                protected Task<Void> createTask() {
                    return new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {

                            try {
                                Admission pa = admissionTable.getSelectionModel().getSelectedItem();
                                HashMap hash = new HashMap();
                                BufferedImage image = ImageIO.read(getClass().getResource("/assets/icons/logo.png"));
                                hash.put("logo", image);
                                hash.put("admissionId", pa.getId());
                                hash.put("reptype", true);
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

                                InputStream a = getClass().getResourceAsStream("/screens/mainDataScreen/reports/AdmissionDetails.jrxml");
                                JasperDesign design = JRXmlLoader.load(a);
                                JasperReport jasperreport = JasperCompileManager.compileReport(design);
                                JasperPrint jasperprint = JasperFillManager.fillReport(jasperreport, hash, db.get.getReportCon());
                                JasperViewer.viewReport(jasperprint, false);

                            } catch (Exception ex) {
                                AlertDialogs.showErrors(ex);
                            } finally {

                            }

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
    }

}
