/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.reception;

import assets.classes.AlertDialogs;
import assets.classes.statics;
import static assets.classes.statics.*;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
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
import javafx.fxml.Initializable;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import screens.admission.assets.Admission;
import screens.reception.assets.ReceptionYields;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class ReceptionScreenGetYieldsController implements Initializable {

    @FXML
    private JFXTextField search;
    @FXML
    private TableView<ReceptionYields> yieldTable;
    @FXML
    private TableColumn<ReceptionYields, String> yieldTabUser;
    @FXML
    private TableColumn<ReceptionYields, String> yieldTabDate;
    @FXML
    private TableColumn<ReceptionYields, String> yieldTabAdmission;
    @FXML
    private TableColumn<ReceptionYields, String> yieldTabAmount;
    @FXML
    private TableColumn<ReceptionYields, String> yieldTabId;
    @FXML
    private Label yieldId;
    @FXML
    private TextField yieldCredit;
    @FXML
    private ComboBox<Admission> yieldAdmission;
    @FXML
    private JFXDatePicker yieldDate;
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
    Preferences prefs;

    @Override
    public void initialize(URL url, ResourceBundle rb) { yieldDate.setConverter(new StringConverter<LocalDate>() {
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
                                    getData();
                                    fillCombo();
                                    yieldCredit.setEditable(false);
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
        yieldTable.setOnMouseClicked((e) -> {
            if (yieldTable.getSelectionModel().getSelectedIndex() == -1) {
            } else {
                formNew.setDisable(false);

                formDelete.setDisable(false);

                formEdite.setDisable(false);

                formAdd.setDisable(true);

                ReceptionYields selected = yieldTable.getSelectionModel().getSelectedItem();
                yieldId.setText(Integer.toString(selected.getId()));
                yieldCredit.setText(selected.getAmount());
                yieldDate.setValue(LocalDate.parse(selected.getDate()));
                ObservableList<Admission> items1 = yieldAdmission.getItems();
                for (Admission a : items1) {
                    if (a.getId() == selected.getAdmission_id()) {
                        yieldAdmission.getSelectionModel().select(a);
                    }
                }
            }
        });
    }

    private void intialColumn() {
        yieldTabUser.setCellValueFactory(new PropertyValueFactory<>("user"));
        yieldTabDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        yieldTabAdmission.setCellValueFactory(new PropertyValueFactory<>("admission_id"));
        yieldTabAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        yieldTabId.setCellValueFactory(new PropertyValueFactory<>("id"));

    }

    private void updateParent() {
        try {
            ReceptionScreenController.configPanels();
        } catch (Exception ex) {
             AlertDialogs.showErrors(ex);
        }
    }

    private void clear() {
        try {
            getAutoNum();
            formNew.setDisable(true);

            formDelete.setDisable(true);

            formEdite.setDisable(true);

            formAdd.setDisable(false);

            yieldCredit.setText("");
            yieldDate.setValue(null);
            yieldAdmission.getSelectionModel().clearSelection();

        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    private void getAutoNum() throws Exception {
        yieldId.setText(ReceptionYields.getAutoNum());
    }

    private void getData() {
        try {
            yieldTable.setItems(ReceptionYields.getData());
            items = yieldTable.getItems();
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }
    ObservableList<ReceptionYields> items;

    private void fillCombo() {
        try {
            yieldAdmission.setItems(Admission.getDataNotPaied());
            yieldAdmission.setConverter(new StringConverter<Admission>() {
                @Override
                public String toString(Admission patient) {
                    yieldCredit.setText(patient.getTotalCost());
                    return patient.getPatient_name();
                }

                @Override
                public Admission fromString(String string) {
                    return null;
                }
            });
            yieldAdmission.setCellFactory(cell -> new ListCell<Admission>() {

                // Create our layout here to be reused for each ListCell
                GridPane gridPane = new GridPane();
                Label lblid = new Label();
                Label lblName = new Label();
                Label lblAmount = new Label();

                // Static block to configure our layout
                {
                    // Ensure all our column widths are constant
                    gridPane.getColumnConstraints().addAll(
                            new ColumnConstraints(100, 100, 100), new ColumnConstraints(100, 100, 100),
                            new ColumnConstraints(100, 100, 100)
                    );

                    gridPane.add(lblid, 0, 1);
                    gridPane.add(lblName, 1, 1);
                    gridPane.add(lblAmount, 2, 1);

                }

                // We override the updateItem() method in order to provide our own layout for this Cell's graphicProperty
                @Override
                protected void updateItem(Admission person, boolean empty) {
                    super.updateItem(person, empty);

                    if (!empty && person != null) {

                        // Update our Labels
                        lblid.setText("م: " + Integer.toString(person.getId()));
                        lblName.setText("الاسم: " + person.getPatient_name());
                        lblAmount.setText("المبلغ: " + person.getTotalCost());
                        // Set this ListCell's graphicProperty to display our GridPane
                        setGraphic(gridPane);
                    } else {
                        // Nothing to display here
                        setGraphic(null);
                    }
                }
            });
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }

    }

    @FXML
    private void search(KeyEvent event) {
        FilteredList<ReceptionYields> filteredData = new FilteredList<>(items, p -> true);

        filteredData.setPredicate(pa -> {

            if (search.getText() == null || search.getText().isEmpty()) {
                return true;
            }

            String lowerCaseFilter = search.getText().toLowerCase();

            return (pa.getDate().contains(lowerCaseFilter)
                    || pa.getAmount().contains(lowerCaseFilter)
                    || pa.getUser().contains(lowerCaseFilter));

        });

        SortedList<ReceptionYields> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(yieldTable.comparatorProperty());
        yieldTable.setItems(sortedData);

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
                                    alert.setTitle("Deleting  Yield");
                                    alert.setHeaderText("سيتم حذف الايراد ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        ReceptionYields r = new ReceptionYields();
                                        r.setId(Integer.parseInt(yieldId.getText()));
                                        r.Delete();
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
                fillCombo();
                updateParent();
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
                                    alert.setTitle("Editing  Yield");
                                    alert.setHeaderText("سيتم تعديل الايراد ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        ReceptionYields r = new ReceptionYields();
                                        r.setId(Integer.parseInt(yieldId.getText()));
                                        r.setAcc_id(Integer.parseInt(prefs.get(RECEPTION_ACC_ID, "2")));
                                        r.setUser_id(Integer.parseInt(prefs.get(USER_ID, "0")));
                                        r.setAmount(yieldCredit.getText());
                                        r.setDate(yieldDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                                        r.setAdmission_id(yieldAdmission.getSelectionModel().getSelectedItem().getId());
                                        r.Edite();
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
                fillCombo();
                updateParent();
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
                                    ReceptionYields r = new ReceptionYields();
                                    r.setId(Integer.parseInt(yieldId.getText()));
                                    r.setAcc_id(Integer.parseInt(prefs.get(RECEPTION_ACC_ID, "2")));
                                    r.setUser_id(Integer.parseInt(prefs.get(USER_ID, "0")));
                                    r.setAmount(yieldCredit.getText());
                                    r.setDate(yieldDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                                    r.setAdmission_id(yieldAdmission.getSelectionModel().getSelectedItem().getId());
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
                clear();
                getData();
                fillCombo();
                updateParent();
                super.succeeded();
            }
        };
        service.start();
    }
    ReceptionScreenController ReceptionScreenController;

    void setrecptionController(ReceptionScreenController receptionScreenController) {
        this.ReceptionScreenController = receptionScreenController;
    }

}
