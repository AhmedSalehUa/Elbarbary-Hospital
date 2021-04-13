/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.store;

import assets.classes.AlertDialogs;
import elbarbary.hospital.ElBarbaryHospital;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import screens.reception.assets.ShortsOrder;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class StoreScreenTransactionsShortOrdersController implements Initializable {

    @FXML
    private TableView<ShortsOrder> productsTable;
    @FXML
    private TableColumn<ShortsOrder, String> productsTabProduct;
    @FXML
    private TableColumn<ShortsOrder, String> productsTabAmount;
    @FXML
    private TableColumn<ShortsOrder, String> productsTabStatue;
    @FXML
    private Label requestedAmount;
    @FXML
    private Label amountInStore;
    @FXML
    private ProgressIndicator progress;

    Preferences prefs;

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
                                    intialColumn();
                                    getData();

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

        productsTable.setOnMouseClicked((e) -> {
            if (productsTable.getSelectionModel().getSelectedIndex() == -1) {
            } else {

                try {
                    ShortsOrder selected = productsTable.getSelectionModel().getSelectedItem();
                    requestedAmount.setText(selected.getAmount());
                    amountInStore.setText(db.get.getTableData("SELECT   SUM(`amount`) FROM `stores_medicines` WHERE  `store_id`='1' And `medicine_id`='" + selected.getProdct_id() + "'").getValueAt(0, 0).toString());

                } catch (Exception ex) {
                    AlertDialogs.showErrors(ex);
                }
            }
        });
    }

    private void intialColumn() {
        productsTabProduct.setCellValueFactory(new PropertyValueFactory<>("product"));

        productsTabAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        productsTabStatue.setCellValueFactory(new PropertyValueFactory<>("statue"));

    }

    private void getData() {
        try {
            productsTable.setItems(ShortsOrder.getData());
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }

    }

    @FXML
    private void refuse(ActionEvent event) {
        if (productsTable.getSelectionModel().getSelectedIndex() == -1) {
            AlertDialogs.showError("اختار من الجدول اولا");
        } else {
            try {
                ShortsOrder.update(productsTable.getSelectionModel().getSelectedItem().getId(), "تم الرفض");
                getData();
            } catch (Exception ex) {
                AlertDialogs.showErrors(ex);
            }
        }
    }

    @FXML
    private void accept(ActionEvent event) {
        if (productsTable.getSelectionModel().getSelectedIndex() == -1) {
            AlertDialogs.showError("اختار من الجدول اولا");
        } else {
            try {
                ShortsOrder.update(productsTable.getSelectionModel().getSelectedItem().getId(), "سيتم التحويل");
                getData();
            } catch (Exception ex) {
                AlertDialogs.showErrors(ex);
            }
        }
    }

}
