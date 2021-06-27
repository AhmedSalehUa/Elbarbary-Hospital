/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.accounts;

import assets.classes.AlertDialogs;
import assets.classes.AutoCompleteBox;
import assets.classes.ComboBoxAutoComplete;
import static assets.classes.statics.DEFAULT_THEME;
import static assets.classes.statics.RECEPTION_ACC_ID;
import static assets.classes.statics.THEME;
import static assets.classes.statics.USER_ID;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import elbarbary.hospital.ElBarbaryHospital;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.StringConverter;
import org.controlsfx.control.textfield.TextFields;
import screens.admission.assets.Admission;
import screens.admission.assets.AdmissionSpacfication;
import screens.admission.assets.AdmissionStatue;
import screens.admission.assets.AdmissionType;
import screens.mainDataScreen.assets.Contract;
import screens.mainDataScreen.assets.Patients;
import screens.reception.ReceptionScreenController;
import screens.reception.assets.ReceptionYields;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class AccountsScreenContractController implements Initializable {

    @FXML
    private ComboBox<Contract> contracts;
    @FXML
    private JFXDatePicker from;
    @FXML
    private JFXDatePicker to;
    @FXML
    private Button show;
    @FXML
    private Label allAdmission;
    @FXML
    private Label allCost;
    @FXML
    private Button payAll;
    @FXML
    private TableView<Admission> admissionTable;
    @FXML
    private TableColumn<Admission, String> admissionTabCost;
    @FXML
    private TableColumn<Admission, String> admissionTabSpacification;
    @FXML
    private TableColumn<Admission, String> admissionTabStatue;
    @FXML
    private TableColumn<Admission, String> admissionTabType;
    @FXML
    private TableColumn<Admission, String> admissionTabDateOfExite;
    @FXML
    private TableColumn<Admission, String> admissionTabDateOfEntrance;
    @FXML
    private TableColumn<Admission, String> admissionTabContract;
    @FXML
    private TableColumn<Admission, String> admissionTabPatient;
    @FXML
    private TableColumn<Admission, String> admissionTabId;
    @FXML
    private Label someAdmission;
    @FXML
    private Label someCost;
    @FXML
    private Button paySome;
    @FXML
    private ProgressIndicator progress;
    Preferences prefs;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
         from.setConverter(new StringConverter<LocalDate>() {
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
          to.setConverter(new StringConverter<LocalDate>() {
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

                                    clear();
                                    intialColumn();
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
        admissionTable.setOnMouseClicked((event) -> {
            ObservableList<Admission> selectedItems = admissionTable.getSelectionModel().getSelectedItems();
            someAdmission.setText(Integer.toString(selectedItems.size()));
            double total = 0;
            for (Admission a : selectedItems) {
                total += Double.parseDouble(a.getTotalCost());
            }
            someCost.setText(Double.toString(total));
        });
    }

    private void intialColumn() {
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

    private void clear() {
        admissionTable.setItems(null);
    }

    private void fillCombo() throws Exception {
        contracts.setItems(Contract.getData());
        contracts.setConverter(new StringConverter<Contract>() {
            @Override
            public String toString(Contract patient) {
                return patient.getName();
            }

            @Override
            public Contract fromString(String string) {
                return null;
            }
        });
        contracts.setCellFactory(cell -> new ListCell<Contract>() {

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
            protected void updateItem(Contract person, boolean empty) {
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

    @FXML
    private void show(ActionEvent event) {
        if (contracts.getSelectionModel().getSelectedIndex() == -1) {
            AlertDialogs.showError("اختار التعاقد اولا");
        } else {
            try {
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                if (from.getValue() == null && to.getValue() == null) {
                    admissionTable.setItems(Admission.getDataOfCotract(contracts.getSelectionModel().getSelectedItem().getId(), null, null));
                } else if (from.getValue() == null || to.getValue() == null) {
                    AlertDialogs.showError("اختار الفترة كاملة او بدون فترة");
                } else {
                    admissionTable.setItems(Admission.getDataOfCotract(contracts.getSelectionModel().getSelectedItem().getId(), from.getValue().format(format), to.getValue().format(format)));
                }
                ObservableList<Admission> items = admissionTable.getItems();
                if (items.size() == 0) {
                } else {
                    allAdmission.setText(Integer.toString(items.size()));
                    double total = 0;
                    for (Admission a : items) {
                        total += Double.parseDouble(a.getTotalCost());
                    }
                    allCost.setText(Double.toString(total));
                }
                admissionTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            } catch (Exception ex) {

                AlertDialogs.showErrors(ex);
            }
        }
    }

    @FXML
    private void payAll(ActionEvent event) {
        ObservableList<Admission> items = admissionTable.getItems();
        if (items.size() == 0) {
            AlertDialogs.showError("الجدول فارغ");
        } else {
            for (Admission a : items) {
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
                                            ReceptionYields r = new ReceptionYields();
                                            r.setId(Integer.parseInt(ReceptionYields.getAutoNum()));
                                            r.setAcc_id(Integer.parseInt(prefs.get(RECEPTION_ACC_ID, "1")));
                                            r.setUser_id(Integer.parseInt(prefs.get(USER_ID, "0")));
                                            r.setAmount(a.getTotalCost());
                                            r.setDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                                            r.setAdmission_id(a.getId());
                                            r.Add();
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

            }
            clear();
        }
    }

    @FXML
    private void paySome(ActionEvent event) {
        ObservableList<Admission> items = admissionTable.getSelectionModel().getSelectedItems();
        if (items.size() == 0) {
            AlertDialogs.showError("لم يتم تحديد اى تذكرة");
        } else {
            for (Admission a : items) {
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
                                            ReceptionYields r = new ReceptionYields();
                                            r.setId(Integer.parseInt(ReceptionYields.getAutoNum()));
                                            r.setAcc_id(Integer.parseInt(prefs.get(RECEPTION_ACC_ID, "1")));
                                            r.setUser_id(Integer.parseInt(prefs.get(USER_ID, "0")));
                                            r.setAmount(a.getTotalCost());
                                            r.setDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                                            r.setAdmission_id(a.getId());
                                            r.Add();
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

            }
            clear();
        }
    }

}
