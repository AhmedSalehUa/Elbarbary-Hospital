/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.reception;

import assets.classes.AlertDialogs;
import static assets.classes.statics.RECEPTION_ACC_ID;
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
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import screens.accounts.assets.Category;
import screens.accounts.assets.Expenses;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class ReceptionScreenDailyExpensesController implements Initializable {

    Preferences prefs;
    @FXML
    private JFXTextField search;
    @FXML
    private TableView<Expenses> accTable;
    @FXML
    private TableColumn<Expenses, String> accTabNotes;
    @FXML
    private TableColumn<Expenses, String> accTabDate;
    @FXML
    private TableColumn<Expenses, String> accTabCat;
    @FXML
    private TableColumn<Expenses, String> accTabAmount;
    @FXML
    private TableColumn<Expenses, String> accTabId;
    @FXML
    private Label accId;
    @FXML
    private TextField accNotes;
    @FXML
    private TextField accCredit;
    @FXML
    private ComboBox<Category> accCategory;
    @FXML
    private JFXDatePicker accDate;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
        accTable.setOnMouseClicked((e) -> {
            if (accTable.getSelectionModel().getSelectedIndex() == -1) {
            } else {
                formNew.setDisable(false);

                formDelete.setDisable(false);

                formEdite.setDisable(false);

                formAdd.setDisable(true);

                Expenses selected = accTable.getSelectionModel().getSelectedItem();
                accId.setText(Integer.toString(selected.getId()));
                accCredit.setText(selected.getAmount());
                accDate.setValue(LocalDate.parse(selected.getDate()));
                accNotes.setText(selected.getNotes());
 accDate.setValue(LocalDate.parse(selected.getDate()));
                ObservableList<Category> items1 = accCategory.getItems();
                for (Category a : items1) {
                    if (a.getName().equals(selected.getCat())) {
                        accCategory.getSelectionModel().select(a);
                    }
                }
            }
        });
    }

    private void intialColumn() {
        accTabNotes.setCellValueFactory(new PropertyValueFactory<>("notes"));

        accTabDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        accTabCat.setCellValueFactory(new PropertyValueFactory<>("cat"));

        accTabAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        accTabId.setCellValueFactory(new PropertyValueFactory<>("id"));

    }

    private void clear() {
        try {
            getAutoNum();
            formNew.setDisable(true);

            formDelete.setDisable(true);

            formEdite.setDisable(true);

            formAdd.setDisable(false);

            accCredit.setText("");
            accDate.setValue(null);
            accNotes.setText("");

            accCategory.getSelectionModel().clearSelection();
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    private void getAutoNum() throws Exception {
        accId.setText(Expenses.getAutoNum());
    }

    private void getData() {
        try {
            accTable.setItems(Expenses.getReceptionData());
            items = accTable.getItems();
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }
    ObservableList<Expenses> items;

    private void fillCombo() throws Exception {
        accCategory.setItems(Category.getRecptionData());
        accCategory.setConverter(new StringConverter<Category>() {
            @Override
            public String toString(Category patient) {
                return patient.getName();
            }

            @Override
            public Category fromString(String string) {
                return null;
            }
        });
        accCategory.setCellFactory(cell -> new ListCell<Category>() {

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
            protected void updateItem(Category person, boolean empty) {
                super.updateItem(person, empty);

                if (!empty && person != null) {

                    // Update our Labels
                    lblid.setText("م: " + Integer.toString(person.getId()));
                    lblName.setText("التصنيف: " + person.getName());

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
    private void search(KeyEvent event) {
        FilteredList<Expenses> filteredData = new FilteredList<>(items, p -> true);

        filteredData.setPredicate(pa -> {

            if (search.getText() == null || search.getText().isEmpty()) {
                return true;
            }

            String lowerCaseFilter = search.getText().toLowerCase();

            return (pa.getAmount().contains(lowerCaseFilter)
                    || pa.getDate().contains(lowerCaseFilter));

        });

        SortedList<Expenses> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(accTable.comparatorProperty()
        );
        accTable.setItems(sortedData);
    }

    @FXML
    private void addCategory(MouseEvent event) { 
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
                                            Category.AddFromRecption(results);
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
                                    alert.setTitle("Deleting Expenses ");
                                    alert.setHeaderText("سيتم حذف المصروف ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {

                                        Expenses ex = new Expenses();
                                        ex.setId(Integer.parseInt(accId.getText()));
                                        ex.Delete();
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
                                    alert.setTitle("Editing Expenses ");
                                    alert.setHeaderText("سيتم تعديل المصروف ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        Expenses ex = new Expenses();
                                        ex.setId(Integer.parseInt(accId.getText()));
                                        ex.setAmount(accCredit.getText());
                                        ex.setCat_id(accCategory.getSelectionModel().getSelectedItem().getId());
                                        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                        ex.setDate(accDate.getValue().format(format));
                                        ex.setNotes(accNotes.getText());
                                        ex.setAcc_id(prefs.getInt(RECEPTION_ACC_ID, 2));
                                        ex.Edite();
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
                                    Expenses ex = new Expenses();
                                    ex.setId(Integer.parseInt(accId.getText()));
                                    ex.setAmount(accCredit.getText());
                                    ex.setCat_id(accCategory.getSelectionModel().getSelectedItem().getId());
                                    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                    ex.setDate(accDate.getValue().format(format));
                                    ex.setNotes(accNotes.getText());
                                    ex.setAcc_id(prefs.getInt(RECEPTION_ACC_ID, 2));
                                    ex.Add();
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
                super.succeeded();
            }
        };
        service.start();
    }
}
