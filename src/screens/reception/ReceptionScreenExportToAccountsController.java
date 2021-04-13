/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.reception;

import assets.classes.AlertDialogs;
import static assets.classes.statics.*;
import elbarbary.hospital.ElBarbaryHospital;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import screens.accounts.assets.AccountTransactions;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class ReceptionScreenExportToAccountsController implements Initializable {
    
    @FXML
    private Button transport;
    @FXML
    private TextField transAmount;
    @FXML
    private Label currentCredite;
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
    }
    
    private void getData() {
        try {
            currentCredite.setText(db.get.getTableData("SELECT `credit` FROM `accounts` WHERE `id`='" + prefs.get(RECEPTION_ACC_ID, "2") + "'").getValueAt(0, 0).toString());
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
        
    }
    
    @FXML
    private void transport(ActionEvent event) {
        
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
                                    double fromAmount = Double.parseDouble(currentCredite.getText());
                                    double traAmount = Double.parseDouble(transAmount.getText());
                                    if (traAmount > fromAmount) {
                                        AlertDialogs.showError("الرصيد الحالي لا يكفي لتصدير المبلغ");
                                    } else {
                                        AccountTransactions tr = new AccountTransactions();
                                        tr.setAcc_from(Integer.parseInt(prefs.get(RECEPTION_ACC_ID, "2")));
                                        tr.setAcc_to(Integer.parseInt(prefs.get(MAIN_ACC_ID, "2")));
                                        tr.setAmount(transAmount.getText());
                                        tr.setUser_Id(Integer.parseInt(prefs.get(USER_ID, "0")));
                                        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                        tr.setDate(LocalDate.now().format(format));
                                        tr.Add();
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
                AlertDialogs.showmessage("تم");
                transAmount.setText("");
                progress.setVisible(false); 
                getData();
                super.succeeded();
            }
        };
        service.start();
    }
    
}
