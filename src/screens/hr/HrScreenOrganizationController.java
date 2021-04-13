/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.hr;

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
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import screens.hr.assets.Organization;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class HrScreenOrganizationController implements Initializable {
    
    @FXML
    private JFXTextField search;
    @FXML
    private TableView<Organization> orgTable;
    @FXML
    private TableColumn<Organization, String> orgTabLocation;
    @FXML
    private TableColumn<Organization, String> orgTabName;
    @FXML
    private TableColumn<Organization, String> orgTabId;
    @FXML
    private Label orgId;
    @FXML
    private TextField orgName;
    @FXML
    private TextField orgLocation;
    @FXML
    private ProgressIndicator progress;
    @FXML
    private Button orgNew;
    @FXML
    private Button orgDelete;
    @FXML
    private Button orgEdite;
    @FXML
    private Button orgAdd;
    
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
        orgTable.setOnMouseClicked((e) -> {
            if (orgTable.getSelectionModel().getSelectedIndex() == -1) {
            } else {
                orgNew.setDisable(false);
                
                orgDelete.setDisable(false);
                
                orgEdite.setDisable(false);
                
                orgAdd.setDisable(true);
                
                Organization selected = orgTable.getSelectionModel().getSelectedItem();
                orgId.setText(Integer.toString(selected.getId()));
                orgName.setText(selected.getName());
                orgLocation.setText(selected.getLocation());
                
            }
        });
    }
    
    private void intialColumn() {
        orgTabLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        
        orgTabName.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        orgTabId.setCellValueFactory(new PropertyValueFactory<>("id"));
    }
    
    private void clear() {
        try {
            getAutoNum();
            orgNew.setDisable(true);
            
            orgDelete.setDisable(true);
            
            orgEdite.setDisable(true);
            
            orgAdd.setDisable(false);
            orgName.setText("");
            orgLocation.setText("");
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }
    
    private void getAutoNum() throws Exception {
        orgId.setText(Organization.getAutoNum());
    }
    
    private void getData() {
        try {
            orgTable.setItems(Organization.getData());
            items = orgTable.getItems();
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
        
    }
    ObservableList<Organization> items;
    
    @FXML
    private void search(KeyEvent event) {
        FilteredList<Organization> filteredData = new FilteredList<>(items, p -> true);
        
        filteredData.setPredicate(pa -> {
            
            if (search.getText() == null || search.getText().isEmpty()) {
                return true;
            }
            
            String lowerCaseFilter = search.getText().toLowerCase();
            
            return (pa.getName().contains(lowerCaseFilter)
                    || pa.getLocation().contains(lowerCaseFilter));
            
        });
        
        SortedList<Organization> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(orgTable.comparatorProperty());
        orgTable.setItems(sortedData);
    }
    
    @FXML
    private void orgNew(ActionEvent event) {
        clear();
    }
    
    @FXML
    private void orgDelete(ActionEvent event) {
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
                                    alert.setTitle("Deleting  Organization");
                                    alert.setHeaderText("سيتم حذف الشركة ");
                                    alert.setContentText("هل انت متاكد؟");
                                    
                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        Organization org = new Organization();
                                        org.setId(Integer.parseInt(orgId.getText()));
                                        
                                        org.Delete();
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
                getData();
                super.succeeded();
            }
        };
        service.start();
    }
    
    @FXML
    private void orgEdite(ActionEvent event) {
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
                                    alert.setTitle("Editing  Organization");
                                    alert.setHeaderText("سيتم تعديل الشركة ");
                                    alert.setContentText("هل انت متاكد؟");
                                    
                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        Organization org = new Organization();
                                        org.setId(Integer.parseInt(orgId.getText()));
                                        org.setName(orgName.getText());
                                        org.setLocation(orgLocation.getText());
                                        org.Edite();
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
                getData();
                super.succeeded();
            }
        };
        service.start();
    }
    
    @FXML
    private void orgAdd(ActionEvent event) {
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
                                    Organization org = new Organization();
                                    org.setId(Integer.parseInt(orgId.getText()));
                                    org.setName(orgName.getText());
                                    org.setLocation(orgLocation.getText());
                                    org.Add();
                                    
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
                getData();
                super.succeeded();
            }
        };
        service.start();
    }
    
}
