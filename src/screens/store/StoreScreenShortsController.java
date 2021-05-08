/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.store;

import assets.classes.AlertDialogs;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
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
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javax.imageio.ImageIO;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
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
    @FXML
    private ProgressIndicator indProgress;
    @FXML
    private ProgressIndicator allProgress;

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
                indProgress.setVisible(false);
                allProgress.setVisible(false);
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
        if (indShortStoresTable.getSelectionModel().getSelectedIndex() == -1) {
            AlertDialogs.showError("اختار المخزن اولا");
        } else {
            Stores store = indShortStoresTable.getSelectionModel().getSelectedItem();
            indProgress.setVisible(true);
            Service<Void> service = new Service<Void>() {
                @Override
                protected Task<Void> createTask() {
                    return new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {

                            try {
                                HashMap hash = new HashMap();
                                BufferedImage image = ImageIO.read(getClass().getResource("/assets/icons/logo.png"));
                                hash.put("logo", image);
                                hash.put("storName", store.getName());
                                hash.put("storeId", Integer.toString(store.getId()));
                                InputStream a = getClass().getResourceAsStream("/screens/store/reports/OneStore.jrxml");

                                JasperDesign design = JRXmlLoader.load(a);
                                JasperReport jasperreport = JasperCompileManager.compileReport(design);
                                JasperPrint jasperprint = JasperFillManager.fillReport(jasperreport, hash, db.get.getReportCon());
                                JasperViewer.viewReport(jasperprint, false);
                            } catch (Exception ex) {
                                AlertDialogs.showErrors(ex);
                            }

                            return null;
                        }
                    };
                }

                @Override
                protected void succeeded() {
                    indProgress.setVisible(false);
                    super.succeeded();
                }
            };
            service.start();
        }
    }

    @FXML
    private void printAllStoreShorts(MouseEvent event) {

        allProgress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {

                        try {
                            HashMap hash = new HashMap();
                            BufferedImage image = ImageIO.read(getClass().getResource("/assets/icons/logo.png"));
                            hash.put("logo", image);
                            InputStream a = getClass().getResourceAsStream("/screens/store/reports/AllStores.jrxml");

                            JasperDesign design = JRXmlLoader.load(a);
                            JasperReport jasperreport = JasperCompileManager.compileReport(design);
                            JasperPrint jasperprint = JasperFillManager.fillReport(jasperreport, hash, db.get.getReportCon());
                            JasperViewer.viewReport(jasperprint, false);
                        } catch (Exception ex) {
                            AlertDialogs.showErrors(ex);
                        }

                        return null;
                    }
                };
            }

            @Override
            protected void succeeded() {
                allProgress.setVisible(false);
                super.succeeded();
            }
        };
        service.start();

    }

    @FXML
    private void StoreProducts(ActionEvent event) {
         allProgress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {

                        try {
                            HashMap hash = new HashMap();
                            BufferedImage image = ImageIO.read(getClass().getResource("/assets/icons/logo.png"));
                            hash.put("logo", image);
                            InputStream a = getClass().getResourceAsStream("/screens/store/reports/StoreProducts.jrxml");

                            JasperDesign design = JRXmlLoader.load(a);
                            JasperReport jasperreport = JasperCompileManager.compileReport(design);
                            JasperPrint jasperprint = JasperFillManager.fillReport(jasperreport, hash, db.get.getReportCon());
                            JasperViewer.viewReport(jasperprint, false);
                        } catch (Exception ex) {
                            AlertDialogs.showErrors(ex);
                        }

                        return null;
                    }
                };
            }

            @Override
            protected void succeeded() {
                allProgress.setVisible(false);
                super.succeeded();
            }
        };
        service.start();

    }

}
