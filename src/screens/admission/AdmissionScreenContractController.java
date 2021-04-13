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
import screens.admission.assets.Admission;
import screens.admission.assets.AdmissionContract;
import screens.mainDataScreen.assets.ContractServices;
import screens.mainDataScreen.assets.ContractServicesName;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class AdmissionScreenContractController implements Initializable {

    Admission admission;
    AdmissionScreenController parentController;
    @FXML
    private JFXTextField search1;
    @FXML
    private TableView<AdmissionContract> serviceContractTable;
    @FXML
    private TableColumn<AdmissionContract, String> serviceContractTabCost;
    @FXML
    private TableColumn<AdmissionContract, String> serviceContractTabServ;
    @FXML
    private TableColumn<AdmissionContract, String> serviceContractTabId;
    @FXML
    private Label serviceContractId;
    @FXML
    private ComboBox<ContractServices> serviceContractServices;
    @FXML
    private Button serviceContractNew;
    @FXML
    private Button serviceContractDelete;
    @FXML
    private Button serviceContractEdite;
    @FXML
    private Button serviceContractAdd;
    @FXML
    private ProgressIndicator progress;

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
                                    intialColumn();
                                    getData();
                                    fillCombo();
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
        serviceContractTable.setOnMouseClicked((w) -> {
            if (serviceContractTable.getSelectionModel().getSelectedIndex() == -1) {

            } else {
                serviceContractAdd.setDisable(true);

                serviceContractDelete.setDisable(false);
                serviceContractNew.setDisable(false);
                serviceContractEdite.setDisable(false);

                AdmissionContract selectedItem = serviceContractTable.getSelectionModel().getSelectedItem();
                serviceContractId.setText(Integer.toString(selectedItem.getId()));

                ObservableList<ContractServices> items1 = serviceContractServices.getItems();
                for (ContractServices a : items1) {
                    if (a.getService().equals(selectedItem.getService())) {
                        serviceContractServices.getSelectionModel().select(a);
                    }
                }

            }
        });

    }

    void initData(Admission admission) {
        this.admission = admission;
    }

    void setParentController(AdmissionScreenController parentController) {
        this.parentController = parentController;
    }

    @FXML
    private void search(KeyEvent event) {
        FilteredList<AdmissionContract> filteredData = new FilteredList<>(items, p -> true);

        filteredData.setPredicate(pa -> {

            if (search1.getText() == null || search1.getText().isEmpty()) {
                return true;
            }

            String lowerCaseFilter = search1.getText().toLowerCase();

            return (pa.getService().contains(lowerCaseFilter));

        });

        SortedList<AdmissionContract> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(serviceContractTable.comparatorProperty());
        serviceContractTable.setItems(sortedData);

    }

    @FXML
    private void serviceContractNew(ActionEvent event) {
        clear();
    }

    @FXML
    private void serviceContractDelete(ActionEvent event) {
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
                                    alert.setTitle("Deleting Service");
                                    alert.setHeaderText("سيتم حذف الخدمة");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        AdmissionContract ad = new AdmissionContract();
                                        ad.setId(Integer.parseInt(serviceContractId.getText()));
                                        ad.setAdmissionId(admission.getId());
                                        ad.setServiceId(serviceContractServices.getSelectionModel().getSelectedItem().getId());
                                        ad.setCost(serviceContractServices.getSelectionModel().getSelectedItem().getCost());
                                        ad.Delete();
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
                try {
                    getData();
                    updateParent();
                } catch (Exception ex) {
                    AlertDialogs.showErrors(ex);
                }
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void serviceContractEdite(ActionEvent event) {
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
                                    alert.setTitle("Editing Service");
                                    alert.setHeaderText("سيتم تعديل  الخدمة");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        AdmissionContract ad = new AdmissionContract();
                                        ad.setId(Integer.parseInt(serviceContractId.getText()));
                                        ad.setAdmissionId(admission.getId());
                                        ad.setServiceId(serviceContractServices.getSelectionModel().getSelectedItem().getId());
                                        ad.setCost(serviceContractServices.getSelectionModel().getSelectedItem().getCost());
                                        ad.Edite();
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
                try {
                    getData();
                    updateParent();
                } catch (Exception ex) {
                    AlertDialogs.showErrors(ex);
                }
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void serviceContractAdd(ActionEvent event) {
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
                                    AdmissionContract ad = new AdmissionContract();
                                    ad.setId(Integer.parseInt(serviceContractId.getText()));
                                    ad.setAdmissionId(admission.getId());
                                    ad.setServiceId(serviceContractServices.getSelectionModel().getSelectedItem().getId());
                                    ad.setCost(serviceContractServices.getSelectionModel().getSelectedItem().getCost());
                                    ad.Add();
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
                try {
                    getData();
                    updateParent();
                } catch (Exception ex) {
                    AlertDialogs.showErrors(ex);
                }
                super.succeeded();
            }

        };
        service.start();
    }

    private void intialColumn() {
        serviceContractTabCost.setCellValueFactory(new PropertyValueFactory<>("cost"));

        serviceContractTabServ.setCellValueFactory(new PropertyValueFactory<>("service"));

        serviceContractTabId.setCellValueFactory(new PropertyValueFactory<>("id"));
    }

    private void getData() throws Exception {

        serviceContractTable.setItems(AdmissionContract.getData(admission.getId()));
        items = serviceContractTable.getItems();
    }
    ObservableList<AdmissionContract> items;

    private void fillCombo() throws Exception {
        serviceContractServices.setItems(ContractServices.getDataFromName(admission.getContract_name()));
        serviceContractServices.setConverter(new StringConverter<ContractServices>() {
            @Override
            public String toString(ContractServices patient) {
                return patient.getService();
            }

            @Override
            public ContractServices fromString(String string) {
                return null;
            }
        });
        serviceContractServices.setCellFactory(cell -> new ListCell<ContractServices>() {

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
            protected void updateItem(ContractServices person, boolean empty) {
                super.updateItem(person, empty);

                if (!empty && person != null) {

                    // Update our Labels
                    lblid.setText("م: " + Integer.toString(person.getId()));
                    lblName.setText("الاسم: " + person.getService());
                    lblCost.setText("السعر: " + person.getCost());

                    // Set this ListCell's graphicProperty to display our GridPane
                    setGraphic(gridPane);
                } else {
                    // Nothing to display here
                    setGraphic(null);
                }
            }
        });
    }

    private void updateParent() {
        parentController.getAdmissionDataToTable();
        parentController.setSlectedItem(admission);
        parentController.updateParent();
    }

    private void clear() {
        try {
            setAutoNum();

            serviceContractAdd.setDisable(false);

            serviceContractDelete.setDisable(true);
            serviceContractNew.setDisable(true);
            serviceContractEdite.setDisable(true);

            serviceContractServices.getSelectionModel().clearSelection();
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    private void setAutoNum() throws Exception {
        serviceContractId.setText(AdmissionContract.getAutoNum());
    }
}
