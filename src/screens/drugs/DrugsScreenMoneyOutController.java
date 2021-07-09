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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import screens.drugs.assets.DrugsAccounts;
import screens.drugs.assets.DrugsMoneyOut;
import screens.drugs.assets.DrugsPatients;
import screens.drugs.assets.DrugsPatientsEscort;

public class DrugsScreenMoneyOutController implements Initializable {

    @FXML
    private ComboBox<DrugsPatients> patient;
    @FXML
    private Label id;
    @FXML
    private ComboBox<DrugsPatientsEscort> escort;
    @FXML
    private JFXDatePicker date;
    @FXML
    private Label totalRemaining;
    @FXML
    private ProgressIndicator progress;
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
    private TableView<DrugsMoneyOut> table;
    @FXML
    private TableColumn<DrugsMoneyOut, String> tabDate;
    @FXML
    private TableColumn<DrugsMoneyOut, String> tabAmount;
    @FXML
    private TableColumn<DrugsMoneyOut, String> tabEscort;
    @FXML
    private TableColumn<DrugsMoneyOut, String> tabId;
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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
        table.setOnMouseClicked((e) -> {
            if (table.getSelectionModel().getSelectedIndex() == -1) {
            } else {
                New.setDisable(false);

                delete.setDisable(false);

                edite.setDisable(false);

                add.setDisable(true);

                DrugsMoneyOut selected = table.getSelectionModel().getSelectedItem();
                id.setText(Integer.toString(selected.getId()));
                totalRemaining.setText(selected.getAmount());
                date.setValue(LocalDate.parse(selected.getDate()));
                ObservableList<DrugsPatientsEscort> items1 = escort.getItems();
                for (DrugsPatientsEscort a : items1) {
                    if (a.getId()==Integer.parseInt(selected.getEscort_name())) {
                        escort.getSelectionModel().select(a);
                    }
                }
            }
        });
    }

    private void intialColumn() {
        tabDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        tabAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        tabEscort.setCellValueFactory(new PropertyValueFactory<>("escort_name"));

        tabId.setCellValueFactory(new PropertyValueFactory<>("id"));

    }

    private void clear() {
        getAutoNum();
        New.setDisable(true);

        delete.setDisable(true);

        edite.setDisable(true);

        add.setDisable(false);
        totalRemaining.setText("");
        date.setValue(null);
        escort.getSelectionModel().clearSelection();
    }

    private void getAutoNum() {
        try {
            id.setText(DrugsMoneyOut.getAutoNum());
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    private void getData(int patient) {
        try {
            table.setItems(DrugsMoneyOut.getData(patient));
            items = table.getItems();
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);

        }
    }
    ObservableList<DrugsMoneyOut> items;

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

    private void fillEscortCombo(int patient) {
        try {
            escort.setItems(DrugsPatientsEscort.getData(Integer.toString(patient)));
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
    private void show(ActionEvent event) {
        try {
            int id1 = patient.getSelectionModel().getSelectedItem().getId();
            getData(id1);
            fillEscortCombo(id1);
            ObservableList<DrugsAccounts> data = DrugsAccounts.getData(id1);
            DrugsAccounts acc = data.get(0);
            totalRemaining.setText(acc.getRemaining());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void New(ActionEvent event) {
        clear();
    }

    @FXML
    private void delete(ActionEvent event) {
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
                                    alert.setTitle("Deleting  Money");
                                    alert.setHeaderText("سيتم حذف المبلغ ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        DrugsMoneyOut d = new DrugsMoneyOut();
                                        d.setId(Integer.parseInt(id.getText()));
                                        d.setDate(date.getValue().format(format));
                                        d.setAmount(totalRemaining.getText());

                                        if (escort.getSelectionModel().getSelectedIndex() == -1) {

                                        } else {
                                            d.setEscort_id(escort.getSelectionModel().getSelectedItem().getId());
                                        }
                                        d.setPatient_id(patient.getSelectionModel().getSelectedItem().getId());
                                        d.Delete();
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
                getData(patient.getSelectionModel().getSelectedItem().getId());
                clear();
                updateParent();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void edite(ActionEvent event) {
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
                                    alert.setTitle("Deleting  Money");
                                    alert.setHeaderText("سيتم حذف المبلغ ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        DrugsMoneyOut d = new DrugsMoneyOut();
                                        d.setId(Integer.parseInt(id.getText()));
                                        d.setDate(date.getValue().format(format));
                                        d.setAmount(totalRemaining.getText());
                                        if (escort.getSelectionModel().getSelectedIndex() == -1) {

                                        } else {
                                            d.setEscort_id(escort.getSelectionModel().getSelectedItem().getId());
                                        }
                                        d.setPatient_id(patient.getSelectionModel().getSelectedItem().getId());
                                        d.Edite();
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
                getData(patient.getSelectionModel().getSelectedItem().getId());
                clear();
                updateParent();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void add(ActionEvent event) {
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
                                    DrugsMoneyOut d = new DrugsMoneyOut();
                                    d.setId(Integer.parseInt(id.getText()));
                                    d.setDate(date.getValue().format(format));
                                    d.setAmount(totalRemaining.getText());
                                    if (escort.getSelectionModel().getSelectedIndex() == -1) {

                                    } else {
                                        d.setEscort_id(escort.getSelectionModel().getSelectedItem().getId());
                                    }
                                    d.setPatient_id(patient.getSelectionModel().getSelectedItem().getId());
                                    d.Add();
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
                getData(patient.getSelectionModel().getSelectedItem().getId());
                clear();
                updateParent();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void search(KeyEvent event) {
        FilteredList<DrugsMoneyOut> filteredData = new FilteredList<>(items, p -> true);

        filteredData.setPredicate(pa -> {

            if (search1.getText() == null || search1.getText().isEmpty()) {
                return true;
            }

            String lowerCaseFilter = search1.getText().toLowerCase();

            return (pa.getEscort_name().contains(lowerCaseFilter)
                    || pa.getDate().contains(lowerCaseFilter));

        });

        SortedList<DrugsMoneyOut> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
    }

    private void updateParent() {
        parent.updateParent();
    }
    DrugsScreenPatienrAccountsController parent;

    public void setParents(DrugsScreenPatienrAccountsController parent) {
        this.parent = parent;
    }
}
