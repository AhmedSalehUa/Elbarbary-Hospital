/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.drugs;

import assets.classes.AlertDialogs;
import static assets.classes.statics.USER_ID;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import elbarbary.hospital.ElBarbaryHospital;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import screens.accounts.assets.Accounts;
import screens.drugs.assets.DrugsMoneyTransactions;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class DrugsScreenMoneyTransactionsController implements Initializable {

    DrugsScreenPatienrAccountsController accountsController;
    @FXML
    private JFXTextField search;
    @FXML
    private TableView<DrugsMoneyTransactions> tab;
    @FXML
    private TableColumn<DrugsMoneyTransactions, String> tabUser;
    @FXML
    private TableColumn<DrugsMoneyTransactions, String> tabTo;
    @FXML
    private TableColumn<DrugsMoneyTransactions, String> TabFrom;
    @FXML
    private TableColumn<DrugsMoneyTransactions, String> tabDate;
    @FXML
    private TableColumn<DrugsMoneyTransactions, String> tabAmount;
    @FXML
    private TableColumn<DrugsMoneyTransactions, String> tabId;
    @FXML
    private Label id;
    @FXML
    private TextField amount;
    @FXML
    private ComboBox<Accounts> from;
    @FXML
    private ComboBox<Accounts> to;
    @FXML
    private JFXDatePicker date;
    @FXML
    private ProgressIndicator progress;
    @FXML
    private Button New;
    @FXML
    private Button Delete;
    @FXML
    private Button Edite;
    @FXML
    private Button Add;

    Preferences prefs;

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
                                    prefs = Preferences.userNodeForPackage(ElBarbaryHospital.class);

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
        tab.setOnMouseClicked((e) -> {
            if (tab.getSelectionModel().getSelectedIndex() == -1) {
            } else {
                New.setDisable(false);

                Delete.setDisable(false);

                Edite.setDisable(false);

                Add.setDisable(true);

                DrugsMoneyTransactions selected = tab.getSelectionModel().getSelectedItem();
                id.setText(Integer.toString(selected.getId()));
                amount.setText(selected.getAmount());
                date.setValue(LocalDate.parse(selected.getDate()));
                ObservableList<Accounts> items1 = from.getItems();
                for (Accounts a : items1) {
                    if (a.getName().equals(selected.getFrom_acc())) {
                        from.getSelectionModel().select(a);
                    }
                }
                ObservableList<Accounts> items2 = to.getItems();
                for (Accounts a : items2) {
                    if (a.getName().equals(selected.getTo_acc())) {
                        to.getSelectionModel().select(a);
                    }
                }
            }
        });
    }

    private void intialColumn() {
        tabUser.setCellValueFactory(new PropertyValueFactory<>("user"));

        tabTo.setCellValueFactory(new PropertyValueFactory<>("to_acc"));

        TabFrom.setCellValueFactory(new PropertyValueFactory<>("from_acc"));

        tabDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        tabAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        tabId.setCellValueFactory(new PropertyValueFactory<>("id"));
    }

    private void clear() {
        getAutoNum();
        New.setDisable(true);

        Delete.setDisable(true);

        Edite.setDisable(true);

        Add.setDisable(false);
    }

    private void getAutoNum() {
        try {
            id.setText(DrugsMoneyTransactions.getAutoNum());
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }
    ObservableList<DrugsMoneyTransactions> items;

    private void getData() {
        try {
            tab.setItems(DrugsMoneyTransactions.getData());
            items = tab.getItems();

        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    private void fillCombo() {
        try {
            from.setItems(Accounts.getData());
            from.setConverter(new StringConverter<Accounts>() {
                @Override
                public String toString(Accounts patient) {
                    return patient.getName();
                }

                @Override
                public Accounts fromString(String string) {
                    return null;
                }
            });
            from.setCellFactory(cell -> new ListCell<Accounts>() {
                GridPane gridPane = new GridPane();
                Label lblid = new Label();
                Label lblName = new Label();

                {
                    gridPane.getColumnConstraints().addAll(
                            new ColumnConstraints(50, 50, 50),
                            new ColumnConstraints(150, 150, 150)
                    );

                    gridPane.add(lblid, 0, 1);
                    gridPane.add(lblName, 1, 1);

                }

                @Override
                protected void updateItem(Accounts person, boolean empty) {
                    super.updateItem(person, empty);

                    if (!empty && person != null) {
                        lblid.setText("م: " + Integer.toString(person.getId()));
                        lblName.setText("الاسم: " + person.getName());
                        setGraphic(gridPane);
                    } else {
                        setGraphic(null);
                    }
                }
            });
            to.setItems(Accounts.getData());
            to.setConverter(new StringConverter<Accounts>() {
                @Override
                public String toString(Accounts patient) {
                    return patient.getName();
                }

                @Override
                public Accounts fromString(String string) {
                    return null;
                }
            });
            to.setCellFactory(cell -> new ListCell<Accounts>() {
                GridPane gridPane = new GridPane();
                Label lblid = new Label();
                Label lblName = new Label();

                {
                    gridPane.getColumnConstraints().addAll(
                            new ColumnConstraints(50, 50, 50),
                            new ColumnConstraints(150, 150, 150)
                    );

                    gridPane.add(lblid, 0, 1);
                    gridPane.add(lblName, 1, 1);

                }

                @Override
                protected void updateItem(Accounts person, boolean empty) {
                    super.updateItem(person, empty);

                    if (!empty && person != null) {
                        lblid.setText("م: " + Integer.toString(person.getId()));
                        lblName.setText("الاسم: " + person.getName());
                        setGraphic(gridPane);
                    } else {
                        setGraphic(null);
                    }
                }
            });
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    void setParents(DrugsScreenPatienrAccountsController accountsController) {
        this.accountsController = accountsController;
    }

    private void updateParent() {
        accountsController.updateParent();
    }

    @FXML
    private void search(KeyEvent event) {

        FilteredList<DrugsMoneyTransactions> filteredData = new FilteredList<>(items, p -> true);

        filteredData.setPredicate(pa -> {

            if (search.getText() == null || search.getText().isEmpty()) {
                return true;
            }

            String lowerCaseFilter = search.getText().toLowerCase();

            return (pa.getAmount().contains(lowerCaseFilter)
                    || pa.getDate().contains(lowerCaseFilter)
                    || pa.getFrom_acc().contains(lowerCaseFilter)
                    || pa.getTo_acc().contains(lowerCaseFilter)
                    || pa.getUser().contains(lowerCaseFilter));

        });

        SortedList<DrugsMoneyTransactions> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tab.comparatorProperty());
        tab.setItems(sortedData);

    }

    @FXML
    private void New(ActionEvent event) {
        clear();
    }

    @FXML
    private void Delete(ActionEvent event) {
        if (from.getSelectionModel().getSelectedIndex() == -1 || to.getSelectionModel().getSelectedIndex() == -1) {
            AlertDialogs.showError("يجب اختيار الحيابين اولا");
        } else if (from.getSelectionModel().getSelectedItem().getId() == to.getSelectionModel().getSelectedItem().getId()) {
            AlertDialogs.showError("يجب ان يكون الحسابين مختلفين");
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
                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setTitle("Deleting  Transaction");
                                        alert.setHeaderText("سيتم حذف التحويل ");
                                        alert.setContentText("هل انت متاكد؟");

                                        Optional<ButtonType> result = alert.showAndWait();
                                        if (result.get() == ButtonType.OK) {
                                            DrugsMoneyTransactions tr = new DrugsMoneyTransactions();
                                            tr.setId(Integer.parseInt(id.getText()));
                                            tr.setFrom_acc_id(from.getSelectionModel().getSelectedItem().getId());
                                            tr.setTo_acc_id(to.getSelectionModel().getSelectedItem().getId());
                                            tr.setUser_id(Integer.parseInt(prefs.get(USER_ID, "0")));
                                            tr.setAmount(amount.getText());
                                            tr.setDate(date.getValue().format(format));
                                            tr.Delete();
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
                    updateParent();
                    super.succeeded();
                }
            };
            service.start();
        }
    }

    @FXML
    private void Edite(ActionEvent event) {
        if (from.getSelectionModel().getSelectedIndex() == -1 || to.getSelectionModel().getSelectedIndex() == -1) {
            AlertDialogs.showError("يجب اختيار الحيابين اولا");
        } else if (from.getSelectionModel().getSelectedItem().getId() == to.getSelectionModel().getSelectedItem().getId()) {
            AlertDialogs.showError("يجب ان يكون الحسابين مختلفين");
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
                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setTitle("Editting  Transaction");
                                        alert.setHeaderText("سيتم تعديل التحويل ");
                                        alert.setContentText("هل انت متاكد؟");

                                        Optional<ButtonType> result = alert.showAndWait();
                                        if (result.get() == ButtonType.OK) {
                                            DrugsMoneyTransactions tr = new DrugsMoneyTransactions();
                                            tr.setId(Integer.parseInt(id.getText()));
                                            tr.setFrom_acc_id(from.getSelectionModel().getSelectedItem().getId());
                                            tr.setTo_acc_id(to.getSelectionModel().getSelectedItem().getId());
                                            tr.setUser_id(Integer.parseInt(prefs.get(USER_ID, "0")));
                                            tr.setAmount(amount.getText());
                                            tr.setDate(date.getValue().format(format));
                                            tr.Edite();
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
                    updateParent();
                    super.succeeded();
                }
            };
            service.start();
        }
    }

    @FXML
    private void Add(ActionEvent event) {
        if (from.getSelectionModel().getSelectedIndex() == -1 || to.getSelectionModel().getSelectedIndex() == -1) {
            AlertDialogs.showError("يجب اختيار الحيابين اولا");
        } else if (from.getSelectionModel().getSelectedItem().getId() == to.getSelectionModel().getSelectedItem().getId()) {
            AlertDialogs.showError("يجب ان يكون الحسابين مختلفين");
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
                                        DrugsMoneyTransactions tr = new DrugsMoneyTransactions();
                                        tr.setId(Integer.parseInt(id.getText()));
                                        tr.setFrom_acc_id(from.getSelectionModel().getSelectedItem().getId());
                                        tr.setTo_acc_id(to.getSelectionModel().getSelectedItem().getId());
                                        tr.setUser_id(Integer.parseInt(prefs.get(USER_ID, "0")));
                                        tr.setAmount(amount.getText());
                                        tr.setDate(date.getValue().format(format));
                                        tr.Add();
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
                    updateParent();
                    super.succeeded();
                }
            };
            service.start();
        }
    }

}
