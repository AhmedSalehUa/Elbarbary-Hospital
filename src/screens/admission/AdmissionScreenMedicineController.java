/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.admission;

import assets.classes.AlertDialogs;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import screens.admission.assets.Admission;
import screens.admission.assets.AdmissionMedicines;
import screens.store.assets.StoreProdcts;

/**
 * FXML Controller class
 *
 * @author ahmed
 */
public class AdmissionScreenMedicineController implements Initializable {

    @FXML
    private JFXTextField search1;
    AdmissionScreenController parentController;
    Admission admission;
    @FXML
    private TableView<AdmissionMedicines> admissionMedicineTable;
    @FXML
    private TableColumn<AdmissionMedicines, String> admissionMedicineTabtotalCost;
    @FXML
    private TableColumn<AdmissionMedicines, String> admissionMedicineTabCost;
    @FXML
    private TableColumn<AdmissionMedicines, String> admissionMedicineTabAmount;
    @FXML
    private TableColumn<AdmissionMedicines, String> admissionMedicineTabMedicine;
    @FXML
    private TableColumn<AdmissionMedicines, String> admissionMedicineTabId;
    @FXML
    private Label admissionMedicineId;
    @FXML
    private ComboBox<StoreProdcts> admissionMedicineMedicines;
    @FXML
    private TextField admissionMedicineAmount;
    @FXML
    private ProgressIndicator progress;
    @FXML
    private Button admissionMedicineNew;
    @FXML
    private Button admissionMedicineDelete;

    @FXML
    private Button admissionMedicineEdite;
    @FXML
    private Button admissionMedicineAdd;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    fillCombo();
                                    intialColumn();
                                    getData();
                                    clear();
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
        admissionMedicineTable.setOnMouseClicked((e) -> {
            if (admissionMedicineTable.getSelectionModel().getSelectedIndex() == -1) {
            } else {
                AdmissionMedicines selectedItem = admissionMedicineTable.getSelectionModel().getSelectedItem();
                admissionMedicineId.setText(Integer.toString(selectedItem.getId()));
                admissionMedicineAmount.setText(selectedItem.getAmount());
                ObservableList<StoreProdcts> items1 = admissionMedicineMedicines.getItems();
                for (StoreProdcts a : items1) {
                    if (a.getProduct().equals(selectedItem.getMedicine())) {
                        admissionMedicineMedicines.getSelectionModel().select(a);
                    }
                }

                admissionMedicineAdd.setDisable(true);
                admissionMedicineDelete.setDisable(false);
                admissionMedicineEdite.setDisable(false);
                admissionMedicineNew.setDisable(false);
            }
        });

    }

    private void fillCombo() throws Exception {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            ObservableList<StoreProdcts> dataForSell;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {

                            dataForSell = StoreProdcts.getDataForSell("2");
                        } catch (Exception ex) {
                            AlertDialogs.showErrors(ex);
                        }
                        return null;
                    }
                };

            }

            @Override
            protected void succeeded() {
                progress.setVisible(false);
                admissionMedicineMedicines.setItems(dataForSell);
                admissionMedicineMedicines.setConverter(new StringConverter<StoreProdcts>() {
                    @Override
                    public String toString(StoreProdcts patient) {
                        return patient.getProduct();
                    }

                    @Override
                    public StoreProdcts fromString(String string) {
                        return null;
                    }
                });
                admissionMedicineMedicines.setCellFactory(cell -> new ListCell<StoreProdcts>() {

                    // Create our layout here to be reused for each ListCell
                    GridPane gridPane = new GridPane();
                    Label lblid = new Label();
                    Label lblName = new Label();
                    Label lblCost = new Label();

                    // Static block to configure our layout
                    {
                        // Ensure all our column widths are constant
                        gridPane.getColumnConstraints().addAll(
                                new ColumnConstraints(100, 100, 100), new ColumnConstraints(100, 100, 100),
                                new ColumnConstraints(100, 100, 100)
                        );

                        gridPane.add(lblid, 0, 1);
                        gridPane.add(lblName, 1, 1);
                        gridPane.add(lblCost, 2, 1);

                    }

                    // We override the updateItem() method in order to provide our own layout for this Cell's graphicProperty
                    @Override
                    protected void updateItem(StoreProdcts person, boolean empty) {
                        super.updateItem(person, empty);

                        if (!empty && person != null) {

                            // Update our Labels
                            lblid.setText("الكميةالمتاحة: " + person.getAmount());
                            lblName.setText("الاسم: " + person.getProduct());
                            lblCost.setText("سعر البيع: " + person.getCostOfSell());

                            // Set this ListCell's graphicProperty to display our GridPane
                            setGraphic(gridPane);
                        } else {
                            // Nothing to display here
                            setGraphic(null);
                        }
                    }
                });
                super.succeeded();
            }
        };
        service.start();

    }

    private void intialColumn() {
        admissionMedicineTabtotalCost.setCellValueFactory(new PropertyValueFactory<>("totalcost"));

        admissionMedicineTabCost.setCellValueFactory(new PropertyValueFactory<>("cost"));

        admissionMedicineTabAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        admissionMedicineTabMedicine.setCellValueFactory(new PropertyValueFactory<>("medicine"));

        admissionMedicineTabId.setCellValueFactory(new PropertyValueFactory<>("id"));
    }

    private void getData() throws Exception {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            ObservableList<AdmissionMedicines> data;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            data = AdmissionMedicines.getData(admission.getId());

                        } catch (Exception ex) {
                            AlertDialogs.showErrors(ex);
                        }
                        return null;
                    }
                };

            }

            @Override
            protected void succeeded() {
                progress.setVisible(false);
                admissionMedicineTable.setItems(data);
                items = data;
                super.succeeded();
            }
        };
        service.start();

    }
    ObservableList<AdmissionMedicines> items;

    private void clear() {
        try {
            setAutoNum();
            admissionMedicineAmount.setText("");
            admissionMedicineMedicines.getSelectionModel().clearSelection();
            admissionMedicineAdd.setDisable(false);
            admissionMedicineDelete.setDisable(true);
            admissionMedicineEdite.setDisable(true);
            admissionMedicineNew.setDisable(true);

        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    @FXML
    private void search(KeyEvent event) {

        FilteredList<AdmissionMedicines> filteredData = new FilteredList<>(items, p -> true);

        filteredData.setPredicate(pa -> {
            if (search1.getText() == null || search1.getText().isEmpty()) {
                return true;
            }

            String lowerCaseFilter = search1.getText().toLowerCase();

            return (pa.getMedicine().contains(lowerCaseFilter));

        });

        SortedList<AdmissionMedicines> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(admissionMedicineTable.comparatorProperty());
        admissionMedicineTable.setItems(sortedData);
    }

    private void updateParen() {
        parentController.getAdmissionDataToTable();
        parentController.setSlectedItem(admission);
        parentController.updateParent();
    }

    void initData(Admission admission) {
        this.admission = admission;
    }

    void setParentController(AdmissionScreenController parentController) {
        this.parentController = parentController;
    }

    @FXML
    private void admissionMedicineNew(ActionEvent event) {
        clear();
    }

    @FXML
    private void admissionMedicineDelete(ActionEvent event) {
        if (admissionMedicineMedicines.getSelectionModel().getSelectedIndex() == -1) {
            AlertDialogs.showError("اختار الدواء اولا");
        } else {
            progress.setVisible(true);
            Service<Void> service = new Service<Void>() {
                @Override
                protected Task<Void> createTask() {
                    return new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                              
                            final CountDownLatch latch = new CountDownLatch(1);
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setTitle("Deleting Medicine");
                                        alert.setHeaderText("سيتم حذف الدواء");
                                        alert.setContentText("هل انت متاكد؟");

                                        Optional<ButtonType> result = alert.showAndWait();
                                        if (result.get() == ButtonType.OK) {
                                            StoreProdcts selectedItem = admissionMedicineMedicines.getSelectionModel().getSelectedItem();

                                            AdmissionMedicines am = new AdmissionMedicines();
                                            am.setAdmissionId(admission.getId());
                                            am.setId(Integer.parseInt(admissionMedicineId.getText()));
                                            am.setOldAmount(admissionMedicineTable.getSelectionModel().getSelectedItem().getAmount());
                                            System.out.println(am.getOldAmount());
                                            double sostOfOne = Double.parseDouble(selectedItem.getCostOfSell());
                                            double totalcost = Double.parseDouble(admissionMedicineAmount.getText()) * sostOfOne;

                                            am.setMedicineId(selectedItem.getId());
                                            am.setAmount(admissionMedicineAmount.getText());
                                            am.setCost(Double.toString(sostOfOne));
                                            am.setTotalcost(Double.toString(totalcost));
                                            am.Delete();

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
                    updateParen();
                    clear();
                    try {
                        getData();
                    } catch (Exception ex) {
                        AlertDialogs.showErrors(ex);
                    }
                    super.succeeded();
                }
            };
            service.start();
        }
    }

    @FXML
    private void admissionMedicineEdite(ActionEvent event) {
        if (admissionMedicineMedicines.getSelectionModel().getSelectedIndex() == -1) {
            AlertDialogs.showError("اختار الدواء اولا");
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
                                        alert.setTitle("Editing Medicine");
                                        alert.setHeaderText("سيتم تعديل الدواء");
                                        alert.setContentText("هل انت متاكد؟");

                                        Optional<ButtonType> result = alert.showAndWait();
                                        if (result.get() == ButtonType.OK) {
                                            StoreProdcts selectedItem = admissionMedicineMedicines.getSelectionModel().getSelectedItem();
                                            if (Double.parseDouble(selectedItem.getAmount()) < Double.parseDouble(admissionMedicineAmount.getText())) {
                                                AlertDialogs.showError("الكمية المتاحة لا تكفي لصرف الكمية المطلوبة");
                                            } else {
                                                AdmissionMedicines am = new AdmissionMedicines();
                                                am.setAdmissionId(admission.getId());
                                                am.setId(Integer.parseInt(admissionMedicineId.getText()));
                                                am.setOldAmount(admissionMedicineTable.getSelectionModel().getSelectedItem().getAmount());

                                                double sostOfOne = Double.parseDouble(selectedItem.getCostOfSell());
                                                double totalcost = Double.parseDouble(admissionMedicineAmount.getText()) * sostOfOne;

                                                am.setMedicineId(selectedItem.getId());
                                                am.setAmount(admissionMedicineAmount.getText());
                                                am.setCost(Double.toString(sostOfOne));
                                                am.setTotalcost(Double.toString(totalcost));
                                                am.Edite();
                                            }
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
                    updateParen();
                    clear();
                    try {
                        getData();
                    } catch (Exception ex) {
                        AlertDialogs.showErrors(ex);
                    }
                    super.succeeded();
                }
            };
            service.start();
        }
    }

    @FXML
    private void admissionMedicineAdd(ActionEvent event) {
        if (admissionMedicineMedicines.getSelectionModel().getSelectedIndex() == -1) {
            AlertDialogs.showError("اختار الدواء اولا");
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
                                        StoreProdcts selectedItem = admissionMedicineMedicines.getSelectionModel().getSelectedItem();
                                        if (Double.parseDouble(selectedItem.getAmount()) < Double.parseDouble(admissionMedicineAmount.getText())) {
                                            AlertDialogs.showError("الكمية المتاحة لا تكفي لصرف الكمية المطلوبة");
                                        } else {
                                            AdmissionMedicines am = new AdmissionMedicines();
                                            am.setAdmissionId(admission.getId());
                                            am.setId(Integer.parseInt(admissionMedicineId.getText()));

                                            double sostOfOne = Double.parseDouble(selectedItem.getCostOfSell());
                                            double totalcost = Double.parseDouble(admissionMedicineAmount.getText()) * sostOfOne;

                                            am.setMedicineId(selectedItem.getId());
                                            am.setAmount(admissionMedicineAmount.getText());
                                            am.setCost(Double.toString(sostOfOne));
                                            am.setTotalcost(Double.toString(totalcost));
                                            am.Add();
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
                    updateParen();
                    clear();
                    try {
                        getData();
                    } catch (Exception ex) {
                        AlertDialogs.showErrors(ex);
                    }
                    super.succeeded();
                }
            };
            service.start();
        }
    }

    private void setAutoNum() throws Exception {
         progress.setVisible(true); 
        Service<Void> service = new Service<Void>() { String autoNum;
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                                try {
                                    
                                      autoNum = AdmissionMedicines.getAutoNum();
                                } catch (Exception ex) {
                                    AlertDialogs.showErrors(ex);
                                } 
                        return null;
                    }
                };

            }

            @Override
            protected void succeeded() {
                progress.setVisible(false);
               admissionMedicineId.setText(autoNum);
                super.succeeded();
            }
        };
        service.start();
       
    }

}
