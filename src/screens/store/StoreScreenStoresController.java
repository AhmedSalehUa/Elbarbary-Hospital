/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.store;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import screens.store.assets.StoreProdcts;
import screens.store.assets.Stores;

/**
 * FXML Controller class
 *
 * @author ahmed
 */
public class StoreScreenStoresController implements Initializable {

    @FXML
    private HBox doctorBox;
    @FXML
    private JFXTextField search;
    @FXML
    private TableView<Stores> storeTable;
    @FXML
    private TableColumn<Stores, String> storeTabType;
    @FXML
    private TableColumn<Stores, String> storeTabName;
    @FXML
    private TableColumn<Stores, String> storeTabId;
    @FXML
    private Label storeId;
    @FXML
    private TextField storeName;
    @FXML
    private ComboBox<String> storeType;
    @FXML
    private ProgressIndicator progress;
    @FXML
    private Button storeNew;
    @FXML
    private Button storeDelete;
    @FXML
    private Button storeEdite;
    @FXML
    private Button storeAdd;
    @FXML
    private TableView<StoreProdcts> productsTable;
    @FXML
    private TableColumn<StoreProdcts, String> productsTabProduct;
    @FXML
    private TableColumn<StoreProdcts, String> productsTabAmount;
    @FXML
    private TableColumn<StoreProdcts, String> productsTabCost;
    @FXML
    private TableColumn<StoreProdcts, String> productsTabInvoice;
    @FXML
    private TableColumn<StoreProdcts, String> productsTabCostOfSell;

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
                                    intitColumns();
                                    getData();
                                    clear();
                                    storeType.getItems().add("رئيسي");
                                    storeType.getItems().add("عهدة");
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
        storeTable.setOnMouseClicked((e) -> {
            if (storeTable.getSelectionModel().getSelectedIndex() == -1) {
            } else {
                Stores a = storeTable.getSelectionModel().getSelectedItem();
                storeId.setText(Integer.toString(a.getId()));
                storeName.setText(a.getName());
                storeType.getSelectionModel().select(a.getType());
                storeAdd.setDisable(true);

                storeDelete.setDisable(false);

                storeEdite.setDisable(false);

                storeNew.setDisable(false);

                getProductInStore(Integer.toString(a.getId()));
            }

        });

    }

    private void intitColumns() {
        storeTabType.setCellValueFactory(new PropertyValueFactory<>("type"));

        storeTabName.setCellValueFactory(new PropertyValueFactory<>("name"));

        storeTabId.setCellValueFactory(new PropertyValueFactory<>("id"));
        
        productsTabCostOfSell.setCellValueFactory(new PropertyValueFactory<>("costOfSell"));
                
        productsTabProduct.setCellValueFactory(new PropertyValueFactory<>("product"));

        productsTabAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        productsTabCost.setCellValueFactory(new PropertyValueFactory<>("cost"));

        productsTabInvoice.setCellValueFactory(new PropertyValueFactory<>("invoiceId"));
    }

    private void getData() {
        try {
            storeTable.setItems(Stores.getData());
            items = storeTable.getItems();
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }
    ObservableList<Stores> items;

    private void clear() {
        try {
            getAutoNum();
            storeAdd.setDisable(false);

            storeDelete.setDisable(true);

            storeEdite.setDisable(true);

            storeNew.setDisable(true);

            storeName.setText("");
            storeType.getSelectionModel().clearSelection();
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }

    }

    @FXML
    private void search(KeyEvent event) {
        FilteredList<Stores> filteredData = new FilteredList<>(items, p -> true);

        filteredData.setPredicate(pa -> {

            if (search.getText() == null || search.getText().isEmpty()) {
                return true;
            }

            String lowerCaseFilter = search.getText().toLowerCase();

            return (pa.getName().contains(lowerCaseFilter)
                    || pa.getType().contains(lowerCaseFilter));

        });

        SortedList< Stores> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(storeTable.comparatorProperty());
        storeTable.setItems(sortedData);
    }

    @FXML
    private void storeNew(ActionEvent event) {
        clear();
    }

    @FXML
    private void storeDelete(ActionEvent event) {
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
                                    alert.setTitle("Deletting Store");
                                    alert.setHeaderText("سيتم حذف المخزن");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        Stores st = new Stores();
                                        st.setId(Integer.parseInt(storeId.getText()));
                                        st.setName(storeName.getText());
                                        st.setType(storeType.getSelectionModel().getSelectedItem());
                                        st.Delete();
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
                getData();
                clear();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void storeEdite(ActionEvent event) {
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
                                    alert.setTitle("Editting Store");
                                    alert.setHeaderText("سيتم تعديل المخزن");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        Stores st = new Stores();
                                        st.setId(Integer.parseInt(storeId.getText()));
                                        st.setName(storeName.getText());
                                        st.setType(storeType.getSelectionModel().getSelectedItem());
                                        st.Edite();
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
                getData();
                clear();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void storeAdd(ActionEvent event) {
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
                                    Stores st = new Stores();
                                    st.setId(Integer.parseInt(storeId.getText()));
                                    st.setName(storeName.getText());
                                    st.setType(storeType.getSelectionModel().getSelectedItem());
                                    st.Add();

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
                getData();
                clear();
                super.succeeded();
            }
        };
        service.start();
    }

    private void getAutoNum() throws Exception {
        storeId.setText(Stores.getAutoNum());
    }

    private void getProductInStore(String id) {
        try {
            productsTable.setItems(StoreProdcts.getData(id));
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

}
