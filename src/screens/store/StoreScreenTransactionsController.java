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
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author ahmed
 */
public class StoreScreenTransactionsController implements Initializable {

    @FXML
    private AnchorPane entrance;
    @FXML
    private AnchorPane transport;
    @FXML
    private AnchorPane shortOrder;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
                                    FXMLLoader loder = new FXMLLoader(getClass().getResource("/screens/store/StoreScreenTransactionsEntrance.fxml"));
                                    entrance.getChildren().clear();
                                    entrance.getChildren().add(loder.load());

                                    FXMLLoader loder2 = new FXMLLoader(getClass().getResource("/screens/store/StoreScreenTransactionsTransport.fxml"));
                                    transport.getChildren().clear();
                                    transport.getChildren().add(loder2.load());
                                    
                                    FXMLLoader loder3 = new FXMLLoader(getClass().getResource("/screens/store/StoreScreenTransactionsShortOrders.fxml"));
                                    shortOrder.getChildren().clear();
                                    shortOrder.getChildren().add(loder3.load());

                                    
                                    
                                    
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
    }    
    
}
