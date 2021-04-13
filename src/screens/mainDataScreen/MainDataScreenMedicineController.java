/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.mainDataScreen;

import assets.classes.AlertDialogs;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import javafx.util.StringConverter;
import screens.mainDataScreen.assets.Doctors;
import screens.mainDataScreen.assets.Medicine;
import screens.mainDataScreen.assets.MedicineUnit;

public class MainDataScreenMedicineController implements Initializable {

    @FXML
    private JFXTextField search;
    @FXML
    private ProgressIndicator progress;
    @FXML
    private TableView<Medicine> medicineTable;
    @FXML
    private TableColumn<Medicine, String> medicineTabWarnNum;
    @FXML
    private TableColumn<Medicine, String> medicineTabUnit;
    @FXML
    private TableColumn<Medicine, String> medicineTabBarcode;
    @FXML
    private TableColumn<Medicine, String> medicineTabName;
    @FXML
    private TableColumn<Medicine, String> medicineTabId;
    @FXML
    private Label medicineID;
    @FXML
    private TextField medicineName;
    @FXML
    private TextField medicineWarnNum;
    @FXML
    private ComboBox<MedicineUnit> medicineUnit;
    @FXML
    private Button medicineNew;
    @FXML
    private Button medicineEdite;
    @FXML
    private Button medicineAdd;
    @FXML
    private Button medicineDelete;
    @FXML
    private TextField medicineBarcode;
    @FXML
    private MaterialDesignIconView addUnit;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

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
                                    setCombo();
                                    clear();
                                    setTableColumns();
                                    getDataToTable();
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
        medicineTable.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            medicineAdd.setDisable(true);
            medicineEdite.setDisable(false);
            medicineDelete.setDisable(false);
            medicineNew.setDisable(false);

            Medicine me = medicineTable.getSelectionModel().getSelectedItem();
            medicineID.setText(Integer.toString(me.getId()));
            medicineName.setText(me.getName());
            medicineBarcode.setText(me.getBarcode());

            ObservableList<MedicineUnit> items1 = medicineUnit.getItems();
            for (MedicineUnit a : items1) {
                if (a.getUnit().equals(me.getUnit())) {

                    medicineUnit.getSelectionModel().select(a);
                }
            }

            medicineWarnNum.setText(me.getWarnNum());

        });
    }

    @FXML
    private void medicineNew(ActionEvent event) {
        clear();
    }

    @FXML
    private void medicineEdite(ActionEvent event) {
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
                                    try {
                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setTitle("Deleting Medicine");
                                        alert.setHeaderText("سيتم تعديل بيانات الدواء");
                                        alert.setContentText("هل انت متاكد؟");

                                        Optional<ButtonType> result = alert.showAndWait();
                                        if (result.get() == ButtonType.OK) {
                                            Medicine medi = new Medicine();
                                            medi.setId(Integer.parseInt(medicineID.getText()));
                                            medi.setName(medicineName.getText());
                                            medi.setBarcode(medicineBarcode.getText());
                                            medi.setUnitId(medicineUnit.getSelectionModel().getSelectedItem().getId());
                                            medi.setWarnNum(medicineWarnNum.getText());

                                            medi.Edite();
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
                clear();
                getDataToTable();
                progress.setVisible(false);
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void medicineAdd(ActionEvent event) {
        if (medicineName.getText().isEmpty() || medicineName.getText() == null
                || medicineUnit.getSelectionModel().getSelectedItem() == null) {
            AlertDialogs.showError("اسم الدواء والمية والوحدة لا يجب ان يكون فارغا!!");
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
                                        try {
                                            Medicine medi = new Medicine();
                                            medi.setId(Integer.parseInt(medicineID.getText()));
                                            medi.setName(medicineName.getText());
                                            medi.setBarcode(medicineBarcode.getText());
                                            medi.setUnitId(medicineUnit.getSelectionModel().getSelectedItem().getId());
                                            medi.setWarnNum(medicineWarnNum.getText());

                                            medi.Add();

                                        } catch (Exception ex) {
                                            System.out.println(ex.getMessage());
                                            ex.printStackTrace();
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
                    clear();
                    getDataToTable();
                    progress.setVisible(false);
                    super.succeeded();
                }
            };
            service.start();
        }
    }

    @FXML
    private void medicineDelete(ActionEvent event) {
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
                                    try {
                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setTitle("Deleting Medicine");
                                        alert.setHeaderText("سيتم حذف الدواء");
                                        alert.setContentText("هل انت متاكد؟");

                                        Optional<ButtonType> result = alert.showAndWait();
                                        if (result.get() == ButtonType.OK) {
                                            Medicine medi = new Medicine();
                                            medi.setId(Integer.parseInt(medicineID.getText()));
                                            medi.Delete();
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
                clear();
                getDataToTable();
                progress.setVisible(false);
                super.succeeded();
            }
        };
        service.start();

    }

    @FXML
    private void search(KeyEvent event) {
        FilteredList<Medicine> filteredData = new FilteredList<>(items, p -> true);

        filteredData.setPredicate(pa -> {

            if (search.getText() == null || search.getText().isEmpty()) {
                return true;
            }

            String lowerCaseFilter = search.getText().toLowerCase();

            if (pa.getName().toLowerCase().contains(lowerCaseFilter)
                    || pa.getBarcode().toLowerCase().contains(lowerCaseFilter)
                    || pa.getUnit().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else {
                return false;
            }

        });

        SortedList< Medicine> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(medicineTable.comparatorProperty());
        medicineTable.setItems(sortedData);
    }

    private void setAutoNumber() {
        try {
            medicineID.setText(Medicine.getAutoNum());
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    private void clear() {
        setAutoNumber();
        medicineName.setText("");
        medicineBarcode.setText("");
        medicineWarnNum.setText("");

        medicineUnit.getSelectionModel().clearSelection();
        medicineAdd.setDisable(false);
        medicineEdite.setDisable(true);
        medicineDelete.setDisable(true);
        medicineNew.setDisable(true);
    }

    private void setTableColumns() {
        medicineTabId.setCellValueFactory(new PropertyValueFactory<>("id"));
        medicineTabName.setCellValueFactory(new PropertyValueFactory<>("name"));
        medicineTabBarcode.setCellValueFactory(new PropertyValueFactory<>("barcode"));
        medicineTabUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        medicineTabWarnNum.setCellValueFactory(new PropertyValueFactory<>("warnNum"));

    }

    private void getDataToTable() {
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
                                    try {

                                        medicineTable.setItems(Medicine.getData());

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

                items = medicineTable.getItems();
                progress.setVisible(false);
                super.succeeded();
            }
        };
        service.start();
    }
    ObservableList<Medicine> items;

    @FXML
    private void addUnit(MouseEvent event) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Add Unit");
        dialog.setHeaderText("اضافة وحدة للادوية");

        ButtonType loginButtonType = new ButtonType("Save", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText("unit");
        TextField password = new TextField();
        password.setPromptText("value ex: 1 for علبة  .5 for شريط");

        grid.add(new Label("Unit:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Value:"), 0, 1);
        grid.add(password, 1, 1);

        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(() -> username.requestFocus());

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(usernamePassword -> {
            try {
                MedicineUnit un = new MedicineUnit();
                un.setUnit(usernamePassword.getKey());
                un.setValue(usernamePassword.getValue());
                un.Add();
                setCombo();
            } catch (Exception ex) {
                AlertDialogs.showErrors(ex);
            }
        });
    }

    private void setCombo() {
        try {
            medicineUnit.setItems(MedicineUnit.getData());
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
        medicineUnit.setConverter(new StringConverter<MedicineUnit>() {
            @Override
            public String toString(MedicineUnit contract) {
                return contract.getUnit();
            }

            @Override
            public MedicineUnit fromString(String string) {
                return null;
            }
        });
        medicineUnit.setCellFactory(cell -> new ListCell<MedicineUnit>() {

            GridPane gridPane = new GridPane();
            Label lblid = new Label();
            Label lblName = new Label();
            Label lblQuali = new Label();

            {

                gridPane.getColumnConstraints().addAll(
                        new ColumnConstraints(100, 100, 100), new ColumnConstraints(100, 100, 100),
                        new ColumnConstraints(100, 100, 100)
                );

                gridPane.add(lblid, 0, 1);
                gridPane.add(lblName, 1, 1);
                gridPane.add(lblQuali, 2, 1);

            }

            @Override
            protected void updateItem(MedicineUnit person, boolean empty) {
                super.updateItem(person, empty);

                if (!empty && person != null) {

                    // Update our Labels
                    lblid.setText("م: " + Integer.toString(person.getId()));
                    lblName.setText("الاسم: " + person.getUnit());
                    lblQuali.setText("القيمة: " + person.getValue());
                    // Set this ListCell's graphicProperty to display our GridPane
                    setGraphic(gridPane);
                } else {
                    // Nothing to display here
                    setGraphic(null);
                }
            }
        });
    }

}
