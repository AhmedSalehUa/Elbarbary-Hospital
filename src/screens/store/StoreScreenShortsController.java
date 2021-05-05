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
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import screens.store.assets.Shorts;
import screens.store.assets.Stores;

/**
 * FXML Controller class
 *
 * @author ahmed
 */
public class StoreScreenShortsController implements Initializable {

    @FXML
    private HBox doctorBox;
    @FXML
    private TableView<Stores> indShortStoresTable;
    @FXML
    private TableColumn<Stores, String> indShortStoresTabName;
    @FXML
    private TableColumn<Stores, String> indShortStoresTabId;
    @FXML
    private TableView<Shorts> indShortProTable;
    @FXML
    private TableColumn<Shorts, String> indShortProTabProduct;
    @FXML
    private TableColumn<Shorts, String> indShortProTabWarnNum;
    @FXML
    private TableColumn<Shorts, String> indShortProTabAvailable;
    @FXML
    private TableView<Shorts> allShortsTable;
    @FXML
    private TableColumn<Shorts, String> allShortsTabProduct;
    @FXML
    private TableColumn<Shorts, String> allShortsTabWarnNum;
    @FXML
    private TableColumn<Shorts, String> allShortsTabAmountInMain;
    @FXML
    private TableColumn<Shorts, String> allShortsTabAmountInBranchs;
    @FXML
    private TableColumn<Shorts, String> allShortsTabTotalAmount;
    @FXML
    private ImageView printOneStoreShorts;
    @FXML
    private ImageView printAllStoreShorts;

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

                super.succeeded();
            }
        };
        service.start();
        indShortStoresTable.setOnMouseClicked((e) -> {
            if (indShortStoresTable.getSelectionModel().getSelectedIndex() == -1) {
                indShortProTable.setItems(null);
            } else {
                try {
                    indShortProTable.setItems(Shorts.getIndividualData(indShortStoresTable.getSelectionModel().getSelectedItem().getId()));
                } catch (Exception ex) {
                    AlertDialogs.showErrors(ex);
                }
            }
        });
    }

    private void intialColumn() {
        indShortStoresTabName.setCellValueFactory(new PropertyValueFactory<>("name"));

        indShortStoresTabId.setCellValueFactory(new PropertyValueFactory<>("id"));

        indShortProTabProduct.setCellValueFactory(new PropertyValueFactory<>("product"));

        indShortProTabWarnNum.setCellValueFactory(new PropertyValueFactory<>("warnNum"));

        indShortProTabAvailable.setCellValueFactory(new PropertyValueFactory<>("Available"));

        allShortsTabProduct.setCellValueFactory(new PropertyValueFactory<>("product"));

        allShortsTabWarnNum.setCellValueFactory(new PropertyValueFactory<>("warnNum"));

        allShortsTabAmountInMain.setCellValueFactory(new PropertyValueFactory<>("mainStoreAvailable"));

        allShortsTabAmountInBranchs.setCellValueFactory(new PropertyValueFactory<>("branchStoreAvailable"));

        allShortsTabTotalAmount.setCellValueFactory(new PropertyValueFactory<>("Available"));

    }

    private void getData() throws Exception {
        indShortStoresTable.setItems(Stores.getData());
        allShortsTable.setItems(Shorts.getAllData());
    }

    @FXML
    private void printOneStoreShorts(MouseEvent event) {
    }

    @FXML
    private void printAllStoreShorts(MouseEvent event) {
    }

    @FXML
    private void StoreProducts(ActionEvent event) {
    }

}
