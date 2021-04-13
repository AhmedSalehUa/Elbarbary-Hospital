/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.store;

import assets.classes.AlertDialogs;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import screens.mainDataScreen.assets.Medicine;
import screens.store.assets.StoreProdcts;
import screens.store.assets.Stores;
import screens.store.assets.Transactions;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class StoreScreenTransactionsTransportController implements Initializable {

    @FXML
    private ComboBox<Stores> storesFrom;
    @FXML
    private Button showProducts;
    @FXML
    private TableView<StoreProdcts> invoiceDetailsTable;
    @FXML
    private TableColumn<StoreProdcts, String> invoiceDetailsTabCost;
    @FXML
    private TableColumn<StoreProdcts, String> invoiceDetailsTabAmount;
    @FXML
    private TableColumn<StoreProdcts, String> invoiceDetailsTabMediccine;
    @FXML
    private TableColumn<StoreProdcts, String> invoiceDetailsTabId;
    @FXML
    private Label product;
    @FXML
    private Label cost;
    @FXML
    private TextField amount;
    @FXML
    private ComboBox<Stores> storesTo;
    @FXML
    private Button saveToStore;
    @FXML
    private ProgressIndicator progress;

    ObservableList<Medicine> medicineItems;

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
                        //Background work                       
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    clear();
                                    intialColumn();
                                    fillCombo();
                                    medicineItems = Medicine.getData();
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
        invoiceDetailsTable.setOnMouseClicked((e) -> {
            if (invoiceDetailsTable.getSelectionModel().getSelectedIndex() == -1) {
            } else {
                StoreProdcts pro = invoiceDetailsTable.getSelectionModel().getSelectedItem();
                product.setText(pro.getProduct());
                cost.setText(pro.getCost());
                amount.setText(pro.getAmount());
            }
        });
        storesFrom.setOnAction((e) -> {
            invoiceDetailsTable.setItems(null);
            product.setText(".......");
            cost.setText("");
            amount.setText("");
        });

    }

    private void clear() {
        invoiceDetailsTable.setItems(null);

        storesFrom.getSelectionModel().clearSelection();
        storesTo.getSelectionModel().clearSelection();
        product.setText(".......");
        cost.setText("");
        amount.setText("");

    }

    private void intialColumn() {

        invoiceDetailsTabMediccine.setCellValueFactory(new PropertyValueFactory<>("product"));
 
        invoiceDetailsTabAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        invoiceDetailsTabCost.setCellValueFactory(new PropertyValueFactory<>("cost"));

        invoiceDetailsTabId.setCellValueFactory(new PropertyValueFactory<>("invoiceId"));
    }

    private void fillCombo() throws Exception {
        storesFrom.setItems(Stores.getData());
        storesFrom.setConverter(new StringConverter<Stores>() {
            @Override
            public String toString(Stores st) {
                return st.getName();
            }

            @Override
            public Stores fromString(String string) {
                return null;
            }
        });
        storesFrom.setCellFactory(cell -> new ListCell<Stores>() {

            GridPane gridPane = new GridPane();
            Label lblid = new Label();
            Label lblName = new Label();

            {
                gridPane.getColumnConstraints().addAll(
                        new ColumnConstraints(100, 100, 100),
                        new ColumnConstraints(100, 100, 100)
                );
                gridPane.add(lblid, 0, 1);
                gridPane.add(lblName, 1, 1);

            }

            @Override
            protected void updateItem(Stores person, boolean empty) {
                super.updateItem(person, empty);

                if (!empty && person != null) {
                    lblid.setText("الاسم: " + person.getName());
                    lblName.setText("النوع: " + person.getType());

                    setGraphic(gridPane);
                } else {
                    setGraphic(null);
                }
            }
        });
        storesTo.setItems(Stores.getData());
        storesTo.setConverter(new StringConverter<Stores>() {
            @Override
            public String toString(Stores st) {
                return st.getName();
            }

            @Override
            public Stores fromString(String string) {
                return null;
            }
        });
        storesTo.setCellFactory(cell -> new ListCell<Stores>() {

            GridPane gridPane = new GridPane();
            Label lblid = new Label();
            Label lblName = new Label();

            {
                gridPane.getColumnConstraints().addAll(
                        new ColumnConstraints(100, 100, 100),
                        new ColumnConstraints(100, 100, 100)
                );
                gridPane.add(lblid, 0, 1);
                gridPane.add(lblName, 1, 1);

            }

            @Override
            protected void updateItem(Stores person, boolean empty) {
                super.updateItem(person, empty);

                if (!empty && person != null) {
                    lblid.setText("الاسم: " + person.getName());
                    lblName.setText("النوع: " + person.getType());

                    setGraphic(gridPane);
                } else {
                    setGraphic(null);
                }
            }
        });
    }

    @FXML
    private void showProducts(ActionEvent event) {
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
                                    invoiceDetailsTable.setItems(StoreProdcts.getData(Integer.toString(storesFrom.getSelectionModel().getSelectedItem().getId())));
                                    product.setText(".......");
                                    cost.setText("");
                                    amount.setText("");
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

    @FXML
    private void saveToStore(ActionEvent event) {
        try {

            if (amount.getText().isEmpty()) {
                AlertDialogs.showError("الكمية لايجب ان تكون فارغه");
            } else if (storesFrom.getSelectionModel().getSelectedIndex() == -1) {
                AlertDialogs.showError("اختار المخزن اولا");
            } else if (storesTo.getSelectionModel().getSelectedIndex() == -1) {
                AlertDialogs.showError("اختار المخزن المنقول اليه اولا");
            } else if (storesFrom.getSelectionModel().getSelectedItem().getId() == storesTo.getSelectionModel().getSelectedItem().getId()) {
                AlertDialogs.showError("يجب ان يكون المخزن المنقول اليه مختلف عن المنقول منه");
            } else if (invoiceDetailsTable.getSelectionModel().getSelectedIndex() == -1) {
                AlertDialogs.showError("اختار الصنف المراد نقل ن الجدول اولا");
            } else if (Integer.parseInt(invoiceDetailsTable.getSelectionModel().getSelectedItem().getAmount()) < Integer.parseInt(amount.getText())) {
                AlertDialogs.showError("الكمية المراد نقلها اكبر من الكمية المتاحة");
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
                                            int oldId = storesFrom.getSelectionModel().getSelectedItem().getId();
                                            int medicineId = 0;
                                            for (Medicine a : medicineItems) {
                                                if (a.getName().equals(product.getText())) {
                                                    medicineId = a.getId();
                                                }
                                            }
                                            String amountValue = amount.getText();
                                            String costValue = cost.getText();
                                            int newId = storesTo.getSelectionModel().getSelectedItem().getId();

                                            Transactions tra = new Transactions();
                                            tra.setOldStoreId(oldId);
                                            tra.setStoreId(newId);
                                            tra.setMedicineId(medicineId);
                                            tra.setAmount(amountValue);
                                            tra.setInvoiceId(invoiceDetailsTable.getSelectionModel().getSelectedItem().getInvoiceId()); 
                                            tra.setOldAmount(invoiceDetailsTable.getSelectionModel().getSelectedItem().getAmount());
                                            tra.setCost(costValue);
                                            tra.TransportBetweenStores();

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
                        AlertDialogs.showmessage("تم"); 
                        clear();
                        super.succeeded();
                    }
                };
                service.start();
            }
        } catch (NumberFormatException e) {
             AlertDialogs.showError("خطافى الكمية");
        } catch (Exception e) {
             AlertDialogs.showErrors(e);
        }
    }

}
