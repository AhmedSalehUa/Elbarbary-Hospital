package screens.drugs;

import assets.classes.AlertDialogs;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import javafx.util.StringConverter;
import screens.drugs.assets.DrugsMoneyIn;
import screens.drugs.assets.DrugsPatients;
import screens.drugs.assets.DrugsPatientsEscort;

public class DrugsScreenMoneyInController
        implements Initializable {

    @FXML
    private Label id;
    @FXML
    private ComboBox<DrugsPatients> patient;
    @FXML
    private ComboBox<DrugsPatientsEscort> escort;
    @FXML
    private TextField amount;
    @FXML
    private JFXDatePicker date;
    @FXML
    private Button New;
    @FXML
    private Button delete;
    @FXML
    private Button edite;
    @FXML
    private Button add;
    @FXML
    private JFXTextField search1;
    @FXML
    private TableView<DrugsMoneyIn> table;
    @FXML
    private TableColumn<DrugsMoneyIn, String> tabDate;
    @FXML
    private TableColumn<DrugsMoneyIn, String> tabAmount;
    @FXML
    private TableColumn<DrugsMoneyIn, String> tabEscort;
    @FXML
    private TableColumn<DrugsMoneyIn, String> tabId;
    @FXML
    private ProgressIndicator progress;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
         date.setConverter(new StringConverter<LocalDate>() {
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
        this.progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    protected Void call() throws Exception {
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(new Runnable() {
                            public void run() {
                                try {
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

            protected void succeeded() {
                progress.setVisible(false);

                super.succeeded();
            }
        };
        service.start();
        table.setOnMouseClicked(e -> {
            if (table.getSelectionModel().getSelectedIndex() != -1) {
                New.setDisable(false);
                delete.setDisable(false);
                edite.setDisable(false);
                add.setDisable(true);
                DrugsMoneyIn selected = table.getSelectionModel().getSelectedItem();
                id.setText(Integer.toString(selected.getId()));
                amount.setText(selected.getAmount());
                date.setValue(LocalDate.parse(selected.getDate()));
                ObservableList<DrugsPatientsEscort> items1 = escort.getItems();
                for (DrugsPatientsEscort a : items1) {
                    if (a.getName().equals(selected.getEscort_name())) {
                        escort.getSelectionModel().select(a);
                    }
                }
            }
        });
    }

    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private void intialColumn() {
        tabDate.setCellValueFactory(new PropertyValueFactory("date"));

        tabAmount.setCellValueFactory(new PropertyValueFactory("amount"));

        tabEscort.setCellValueFactory(new PropertyValueFactory("escort_name"));

        tabId.setCellValueFactory(new PropertyValueFactory("id"));
    }
    ObservableList<DrugsMoneyIn> items;

    private void clear() {
        getAutoNum();
        New.setDisable(true);

        delete.setDisable(true);

        edite.setDisable(true);

        add.setDisable(false);
        amount.setText("");
        date.setValue(null);

        escort.getSelectionModel().clearSelection();
    }

    private void getAutoNum() {
        try {
            id.setText(DrugsMoneyIn.getAutoNum());
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    private void fillCombo() {
        try {
            patient.setItems(DrugsPatients.getData());
            patient.setConverter(new StringConverter<DrugsPatients>() {
                @Override
                public String toString(DrugsPatients patient) {
                    return patient.getName();
                }

                @Override
                public DrugsPatients fromString(String string) {
                    return null;
                }
            });
            patient.setCellFactory(cell -> new ListCell<DrugsPatients>() {

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
                protected void updateItem(DrugsPatients person, boolean empty) {
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
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    private void fillEscortCombo(int patientId) {
         try {
            escort.setItems(DrugsPatientsEscort.getData(Integer.toString(patientId)));
            escort.setConverter(new StringConverter<DrugsPatientsEscort>() {
                @Override
                public String toString(DrugsPatientsEscort patient) {
                    return patient.getName();
                }

                @Override
                public DrugsPatientsEscort fromString(String string) {
                    return null;
                }
            });
            escort.setCellFactory(cell -> new ListCell<DrugsPatientsEscort>() {

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
                protected void updateItem(DrugsPatientsEscort person, boolean empty) {
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
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    @FXML
    private void New(ActionEvent event) {
        clear();
    }

    @FXML
    private void delete(ActionEvent event) {
        if (this.escort.getSelectionModel().getSelectedIndex() == -1) {
            AlertDialogs.showError("اختار المرافق مودع المبلغ اولا");
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
                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setTitle("Deleting  لايداع");
                                        alert.setHeaderText("سيتم حذف لايداع ");
                                        alert.setContentText("هل انت متاكد؟");

                                        Optional<ButtonType> result = alert.showAndWait();
                                        if (result.get() == ButtonType.OK) {
                                            DrugsMoneyIn mo = new DrugsMoneyIn();
                                            mo.setId(Integer.parseInt(id.getText()));
                                            mo.setPatient_id(patient.getSelectionModel().getSelectedItem().getId());
                                            mo.setAmount(amount.getText());
                                            mo.Delete();
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

                protected void succeeded() {
                    progress.setVisible(false);
                    clear();
                    getData(patient.getSelectionModel().getSelectedItem().getId());
                    updateParent();
                    super.succeeded();
                }
            };
            service.start();
        }
    }

    @FXML
    private void edite(ActionEvent event) {
        if (escort.getSelectionModel().getSelectedIndex() == -1) {
            AlertDialogs.showError("اختار المرافق مودع المبلغ اولا");
        } else {
            progress.setVisible(true);
            Service<Void> service = new Service<Void>() {
                protected Task<Void> createTask() {
                    return new Task<Void>() {
                        protected Void call() throws Exception {
                            final CountDownLatch latch = new CountDownLatch(1);
                            Platform.runLater(new Runnable() {
                                public void run() {
                                    try {
                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setTitle("Editing  لايداع");
                                        alert.setHeaderText("سيتم تعديل لايداع ");
                                        alert.setContentText("هل انت متاكد؟");

                                        Optional<ButtonType> result = alert.showAndWait();
                                        if (result.get() == ButtonType.OK) {
                                            DrugsMoneyIn mo = new DrugsMoneyIn();
                                            mo.setId(Integer.parseInt(id.getText()));
                                            mo.setPatient_id(patient.getSelectionModel().getSelectedItem().getId());
                                            mo.setAmount(amount.getText());
                                            mo.setDate(date.getValue().format(DrugsScreenMoneyInController.this.format));
                                            mo.setEscort_id(escort.getSelectionModel().getSelectedItem().getId());
                                            mo.Edite();
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

                protected void succeeded() {
                    progress.setVisible(false);
                    clear();
                    getData(patient.getSelectionModel().getSelectedItem().getId());
                    updateParent();
                    super.succeeded();
                }
            };
            service.start();
        }
    }

    @FXML
    private void add(ActionEvent event) {
        if (escort.getSelectionModel().getSelectedIndex() == -1) {
            AlertDialogs.showError("اختار المرافق مودع المبلغ اولا");
        } else {
            progress.setVisible(true);
            Service<Void> service = new Service<Void>() {
                protected Task<Void> createTask() {
                    return new Task<Void>() {
                        protected Void call() throws Exception {
                            final CountDownLatch latch = new CountDownLatch(1);
                            Platform.runLater(new Runnable() {
                                public void run() {
                                    try {
                                        DrugsMoneyIn mo = new DrugsMoneyIn();
                                        mo.setId(Integer.parseInt(id.getText()));
                                        mo.setPatient_id(patient.getSelectionModel().getSelectedItem().getId());
                                        mo.setAmount(amount.getText());
                                        mo.setDate(date.getValue().format(format));
                                        mo.setEscort_id(escort.getSelectionModel().getSelectedItem().getId());
                                        mo.Add();
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
                    clear();
                    getData(((DrugsPatients) DrugsScreenMoneyInController.this.patient.getSelectionModel().getSelectedItem()).getId());
                    updateParent();
                    super.succeeded();
                }
            };
            service.start();
        }
    }

    @FXML
    private void search(KeyEvent event) {
        FilteredList<DrugsMoneyIn> filteredData = new FilteredList(this.items, p -> true);

        filteredData.setPredicate(pa -> {
            if (this.search1.getText() == null || this.search1.getText().isEmpty()) {
                return true;
            }

            String lowerCaseFilter = this.search1.getText().toLowerCase();

            return (pa.getEscort_name().contains(lowerCaseFilter) || pa.getDate().contains(lowerCaseFilter) || pa.getAmount().contains(lowerCaseFilter));
        });

        SortedList<DrugsMoneyIn> sortedData = new SortedList((ObservableList) filteredData);
        sortedData.comparatorProperty().bind((ObservableValue) this.table.comparatorProperty());
        this.table.setItems((ObservableList) sortedData);
    }

    private void getData(int patien) {
        try {
            table.setItems(DrugsMoneyIn.getData(patien));
            items = table.getItems();
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    @FXML
    private void show(ActionEvent event) {
        if (patient.getSelectionModel().getSelectedIndex() == -1) {
            AlertDialogs.showError("اختار حساب المريض اولا");
        } else {

            int id1 = patient.getSelectionModel().getSelectedItem().getId();
            fillEscortCombo(id1);
            getData(id1);
        }
    }

    private void updateParent() {
        parent.updateParent();
    }
    DrugsScreenPatienrAccountsController parent;

    public void setParents(DrugsScreenPatienrAccountsController parent) {
        this.parent = parent;
    }
}
