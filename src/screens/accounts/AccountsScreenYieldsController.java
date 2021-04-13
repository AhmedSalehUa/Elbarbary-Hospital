/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.accounts;

import assets.classes.AlertDialogs;
import static assets.classes.statics.*;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import screens.accounts.assets.AccountsYields;
import screens.accounts.assets.YieldsCategory;
import screens.admission.assets.Admission;
import screens.reception.ReceptionScreenController;
import screens.reception.assets.ReceptionYields;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class AccountsScreenYieldsController implements Initializable {

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
    @FXML
    private JFXTextField searchOther;
    @FXML
    private TableView<AccountsYields> yieldOtherTable;
    @FXML
    private TableColumn<AccountsYields, String> yieldOtherTabUser;
    @FXML
    private TableColumn<AccountsYields, String> yieldOtherTabDate;
    @FXML
    private TableColumn<AccountsYields, String> yieldOtherTabAdmission;
    @FXML
    private TableColumn<AccountsYields, String> yieldOtherTabAmount;
    @FXML
    private TableColumn<AccountsYields, String> yieldOtherTabId;
    @FXML
    private Label yieldOtherId;
    @FXML
    private TextField yieldOtherCredit;
    @FXML
    private FontAwesomeIconView yieldCatAdd;
    @FXML
    private ComboBox<YieldsCategory> yieldOtherCat;
    @FXML
    private JFXDatePicker yieldOtherDate;
    @FXML
    private ProgressIndicator OtherProgress;
    @FXML
    private Button formOtherNew;
    @FXML
    private Button formOtherDelete;
    @FXML
    private Button formOtherEdite;
    @FXML
    private Button formOtherAdd;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        progress.setVisible(true);
        OtherProgress.setVisible(true);
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
                OtherProgress.setVisible(false);
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
        yieldOtherTable.setOnMouseClicked((e) -> {
            if (yieldOtherTable.getSelectionModel().getSelectedIndex() == -1) {
            } else {
                formOtherNew.setDisable(false);

                formOtherDelete.setDisable(false);

                formOtherEdite.setDisable(false);

                formOtherAdd.setDisable(true);

                AccountsYields selected = yieldOtherTable.getSelectionModel().getSelectedItem();
                yieldOtherId.setText(Integer.toString(selected.getId()));
                yieldOtherCredit.setText(selected.getAmount());
                yieldOtherDate.setValue(LocalDate.parse(selected.getDate()));
                ObservableList<YieldsCategory> items1 = yieldOtherCat.getItems();
                for (YieldsCategory a : items1) {
                    if (a.getName().equals(selected.getCat())) {
                        yieldOtherCat.getSelectionModel().select(a);
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

        yieldOtherTabUser.setCellValueFactory(new PropertyValueFactory<>("user"));
        yieldOtherTabDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        yieldOtherTabAdmission.setCellValueFactory(new PropertyValueFactory<>("cat"));
        yieldOtherTabAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        yieldOtherTabId.setCellValueFactory(new PropertyValueFactory<>("id"));

    }

    private void clear() {
        try {
            getAutoNum();
            formNew.setDisable(true);

            formDelete.setDisable(true);

            formEdite.setDisable(true);

            formAdd.setDisable(false);

            formOtherNew.setDisable(true);

            formOtherDelete.setDisable(true);

            formOtherEdite.setDisable(true);

            formOtherAdd.setDisable(false);

            yieldOtherCredit.setText("");
            yieldOtherDate.setValue(null);;
            yieldOtherCat.getSelectionModel().clearSelection();
            yieldCredit.setText("");
            yieldDate.setValue(null);
            yieldAdmission.getSelectionModel().clearSelection();

        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    private void getAutoNum() throws Exception {
        yieldId.setText(ReceptionYields.getAutoNum());
        yieldOtherId.setText(AccountsYields.getAutoNum());
    }

    private void getData() {
        try {
            yieldTable.setItems(ReceptionYields.getDataAccounts());
            items = yieldTable.getItems();
            yieldOtherTable.setItems(AccountsYields.getData());
            itemsCat = yieldOtherTable.getItems();
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }
    ObservableList<ReceptionYields> items;
    ObservableList<AccountsYields> itemsCat;

    private void fillCombo() {
        try {

            yieldOtherCat.setItems(YieldsCategory.getData());
            yieldOtherCat.setConverter(new StringConverter<YieldsCategory>() {
                @Override
                public String toString(YieldsCategory patient) {
                    return patient.getName();
                }

                @Override
                public YieldsCategory fromString(String string) {
                    return null;
                }
            });
            yieldOtherCat.setCellFactory(cell -> new ListCell<YieldsCategory>() {

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
                protected void updateItem(YieldsCategory person, boolean empty) {
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
                                        r.setAcc_id(Integer.parseInt(prefs.get(MAIN_ACC_ID, "2")));
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
                                    r.setAcc_id(Integer.parseInt(prefs.get(MAIN_ACC_ID, "2")));
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
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void searchOther(KeyEvent event) {
        FilteredList<AccountsYields> filteredData = new FilteredList<>(itemsCat, p -> true);

        filteredData.setPredicate(pa -> {

            if (searchOther.getText() == null || searchOther.getText().isEmpty()) {
                return true;
            }

            String lowerCaseFilter = searchOther.getText().toLowerCase();

            return (pa.getDate().contains(lowerCaseFilter)
                    || pa.getAmount().contains(lowerCaseFilter)
                    || pa.getUser().contains(lowerCaseFilter));

        });

        SortedList<AccountsYields> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(yieldOtherTable.comparatorProperty());
        yieldOtherTable.setItems(sortedData);
    }

    @FXML
    private void yieldCatAdd(MouseEvent event) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Add Cat Name");
        dialog.setHeaderText("اضافة تصنيف جديد");

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
                                            YieldsCategory.Add(results);
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
    private void formOtherNew(ActionEvent event) {
        clear();
    }

    @FXML
    private void formOtherDelete(ActionEvent event) {
        OtherProgress.setVisible(true);
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
                                    alert.setTitle("Deleting  Yields");
                                    alert.setHeaderText("سيتم حذف الايراد ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        AccountsYields r = new AccountsYields();
                                        r.setId(Integer.parseInt(yieldOtherId.getText()));

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
                OtherProgress.setVisible(false);
                clear();
                getData();
                fillCombo();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void formOtherEdite(ActionEvent event) {
        OtherProgress.setVisible(true);
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
                                    alert.setTitle("Editing  Yields");
                                    alert.setHeaderText("سيتم تعديل الايراد ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        AccountsYields r = new AccountsYields();
                                        r.setId(Integer.parseInt(yieldOtherId.getText()));
                                        r.setAcc_id(Integer.parseInt(prefs.get(MAIN_ACC_ID, "2")));
                                        r.setUser_id(Integer.parseInt(prefs.get(USER_ID, "0")));
                                        r.setAmount(yieldOtherCredit.getText());
                                        r.setDate(yieldOtherDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                                        r.setCat_id(yieldOtherCat.getSelectionModel().getSelectedItem().getId());
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
                OtherProgress.setVisible(false);
                clear();
                getData();
                fillCombo();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void formOtherAdd(ActionEvent event) {
        OtherProgress.setVisible(true);
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
                                    AccountsYields r = new AccountsYields();
                                    r.setId(Integer.parseInt(yieldOtherId.getText()));
                                    r.setAcc_id(Integer.parseInt(prefs.get(MAIN_ACC_ID, "2")));
                                    r.setUser_id(Integer.parseInt(prefs.get(USER_ID, "0")));
                                    r.setAmount(yieldOtherCredit.getText());
                                    r.setDate(yieldOtherDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                                    r.setCat_id(yieldOtherCat.getSelectionModel().getSelectedItem().getId());
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
                OtherProgress.setVisible(false);
                clear();
                getData();
                fillCombo();
                super.succeeded();
            }
        };
        service.start();
    }

}
