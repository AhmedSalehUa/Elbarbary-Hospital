/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.reception;

import assets.classes.AlertDialogs;
import static assets.classes.statics.USER_ID;
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
import screens.reception.assets.ShortsOrder;
import screens.store.assets.StoreProdcts;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class ReceptionScreenShortsOrderController implements Initializable {
    
    @FXML
    private TableView<ShortsOrder> productsTable;
    @FXML
    private TableColumn<ShortsOrder, String> productsTabProduct;
    @FXML
    private TableColumn<ShortsOrder, String> productsTabAmount;
    @FXML
    private TableColumn<ShortsOrder, String> productsTabStatue;
    @FXML
    private Button oderShorts;
    @FXML
    private TextField order;
    @FXML
    private Label available;
    @FXML
    private ComboBox<StoreProdcts> product;
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
    
    private void fillCombo() {
        try {
            product.setItems(StoreProdcts.getDataForShortsOrder("2"));
            product.setConverter(new StringConverter<StoreProdcts>() {
                @Override
                public String toString(StoreProdcts patient) {
                    available.setText(patient.getAmount());                    
                    return patient.getProduct();
                }
                
                @Override
                public StoreProdcts fromString(String string) {
                    return null;
                }
            });
            product.setCellFactory(cell -> new ListCell<StoreProdcts>() {
                
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
                protected void updateItem(StoreProdcts person, boolean empty) {
                    super.updateItem(person, empty);
                    
                    if (!empty && person != null) {
                        
                        lblid.setText("متاح: " + person.getAmount());
                        lblName.setText("الاسم: " + person.getProduct());
                        
                        setGraphic(gridPane);
                    } else {
                        
                        setGraphic(null);
                    }
                }
            });
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }
    
    @FXML
    private void oderShorts(ActionEvent event) {
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
                                    ShortsOrder sh = new ShortsOrder();
                                    sh.setAmount(order.getText());
                                    sh.setStore_id(2);
                                    sh.setProdct_id(product.getSelectionModel().getSelectedItem().getInvoiceId());
                                    sh.setStatue("فى انتظار الرد");
                                    sh.setUser_id(Integer.parseInt(prefs.get(USER_ID, "0")));
                                    sh.Add();
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
                fillCombo();
                getData();
                available.setText("0");
                order.setText(""); 
                product.getSelectionModel().clearSelection();
                super.succeeded();
            }
        };
        service.start();
    }
    
}
