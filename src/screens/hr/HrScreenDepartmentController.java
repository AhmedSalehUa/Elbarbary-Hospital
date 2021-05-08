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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import screens.hr.assets.Department;
import screens.hr.assets.Organization; 

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class HrScreenDepartmentController implements Initializable {
    
    @FXML
    private JFXTextField search;
    @FXML
    private TableView<Department> deptTable;
    @FXML
    private TableColumn<Department, String> deptTabOrg;
    @FXML
    private TableColumn<Department, String> deptTabName;
    @FXML
    private TableColumn<Department, String> deptTabId;
    @FXML
    private Label deptId;
    @FXML
    private TextField deptName;
    @FXML
    private ComboBox<Organization> deptOrg;
    @FXML
    private ProgressIndicator progress;
    @FXML
    private Button deptNew;
    @FXML
    private Button deptDelete;
    @FXML
    private Button deptEdite;
    @FXML
    private Button deptAdd;
    
    ObservableList<Department> items;
    
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
        deptTable.setOnMouseClicked((e) -> {
            if (deptTable.getSelectionModel().getSelectedIndex() == -1) {
            } else {
                deptNew.setDisable(false);
                
                deptDelete.setDisable(false);
                
                deptEdite.setDisable(false);
                
                deptAdd.setDisable(true);
                
                Department selected = deptTable.getSelectionModel().getSelectedItem();
                deptId.setText(Integer.toString(selected.getId()));
                deptName.setText(selected.getName());
                
                ObservableList<Organization> items1 = deptOrg.getItems();
                for (Organization a : items1) {
                    if (a.getName().equals(selected.getOrganization())) {
                        deptOrg.getSelectionModel().select(a);
                    }
                }
            }
        });
        
    }
    
    private void intialColumn() {
        deptTabOrg.setCellValueFactory(new PropertyValueFactory<>("Organization"));
        
        deptTabName.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        deptTabId.setCellValueFactory(new PropertyValueFactory<>("id"));
        
    }
    
    private void clear() {
        try {
            getAutoNum();
            deptNew.setDisable(true);
            
            deptDelete.setDisable(true);
            
            deptEdite.setDisable(true);
            
            deptAdd.setDisable(false);
            deptName.setText("");
            deptOrg.getSelectionModel().clearSelection();
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }
    
    private void getAutoNum() throws Exception {
        deptId.setText(Department.getAutoNum());
    }
    
    private void getData() {
        try {
            deptTable.setItems(Department.getData());
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
        items = deptTable.getItems();
    }
    
    private void fillCombo() throws Exception {
        deptOrg.setItems(Organization.getData());
        deptOrg.setConverter(new StringConverter<Organization>() {
            @Override
            public String toString(Organization patient) {
                return patient.getName();
            }
            
            @Override
            public Organization fromString(String string) {
                return null;
            }
        });
        deptOrg.setCellFactory(cell -> new ListCell<Organization>() {

            // Create our layout here to be reused for each ListCell
            GridPane gridPane = new GridPane();
            Label lblid = new Label();
            Label lblName = new Label();

            // Static block to configure our layout
            {
                // Ensure all our column widths are constant
                gridPane.getColumnConstraints().addAll(
                        new ColumnConstraints(100, 100, 100),
                        new ColumnConstraints(100, 100, 100)
                );
                
                gridPane.add(lblid, 0, 1);
                gridPane.add(lblName, 1, 1);
                
            }

            // We override the updateItem() method in order to provide our own layout for this Cell's graphicProperty
            @Override
            protected void updateItem(Organization person, boolean empty) {
                super.updateItem(person, empty);
                
                if (!empty && person != null) {

                    // Update our Labels
                    lblid.setText("م: " + Integer.toString(person.getId()));
                    lblName.setText("الاسم: " + person.getName());

                    // Set this ListCell's graphicProperty to display our GridPane
                    setGraphic(gridPane);
                } else {
                    // Nothing to display here
                    setGraphic(null);
                }
            }
        });
    }
    
    @FXML
    private void search(KeyEvent event) {
        FilteredList<Department> filteredData = new FilteredList<>(items, p -> true);
        
        filteredData.setPredicate(pa -> {
            
            if (search.getText() == null || search.getText().isEmpty()) {
                return true;
            }
            
            String lowerCaseFilter = search.getText().toLowerCase();
            
            return (pa.getName().contains(lowerCaseFilter));
            
        });
        
        SortedList<Department> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(deptTable.comparatorProperty());
        deptTable.setItems(sortedData);
    }
    
    @FXML
    private void deptNew(ActionEvent event) {
        clear();
    }
    
    @FXML
    private void deptDelete(ActionEvent event) {
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
                                    alert.setTitle("Deleting  Department");
                                    alert.setHeaderText("سيتم حذف الفرع ");
                                    alert.setContentText("هل انت متاكد؟");
                                    
                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        Department dp = new Department();
                                        dp.setId(Integer.parseInt(deptId.getText()));
                                        dp.Delete();
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
    private void deptEdite(ActionEvent event) {
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
                                    alert.setTitle("Editing  Department");
                                    alert.setHeaderText("سيتم تعديل الفرع ");
                                    alert.setContentText("هل انت متاكد؟");
                                    
                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        Department dp = new Department();
                                        dp.setId(Integer.parseInt(deptId.getText()));
                                        dp.setName(deptName.getText());
                                        dp.setOrg_id(deptOrg.getSelectionModel().getSelectedItem().getId());
                                        dp.Edite();
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
    private void deptAdd(ActionEvent event) {
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
                                    Department dp = new Department();
                                    dp.setId(Integer.parseInt(deptId.getText()));
                                    dp.setName(deptName.getText());
                                    dp.setOrg_id(deptOrg.getSelectionModel().getSelectedItem().getId());
                                    dp.Add();
                                    
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
