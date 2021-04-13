/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.store;

import java.util.concurrent.CountDownLatch;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import assets.classes.AlertDialogs;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.scene.layout.HBox;
import screens.store.assets.Company;

/**
 * FXML Controller class
 *
 * @author ahmed
 */
public class StoreScreenCompanyController implements Initializable {

    @FXML
    private JFXTextField search;
    @FXML
    private TableView<Company> compTable;
    @FXML
    private TableColumn<Company, String> compTabTele;
    @FXML
    private TableColumn<Company, String> compTabAcc;
    @FXML
    private TableColumn<Company, String> compTabType;
    @FXML
    private TableColumn<Company, String> compTabName;
    @FXML
    private TableColumn<Company, String> compTabId;
    @FXML
    private Label compId;
    @FXML
    private TextField compName;
    @FXML
    private ComboBox<String> compType;
    @FXML
    private TextField compAcc;
    @FXML
    private TextField compTele;
    @FXML
    private ProgressIndicator progress;
    @FXML
    private Button compNew;
    @FXML
    private Button compDelete;
    @FXML
    private Button compEdite;
    @FXML
    private Button compAdd;
    @FXML
    private HBox doctorBox;

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
                                    compType.getItems().add("شركة");
                                    compType.getItems().add("صيدلية");
                                    intitColumns();
                                    getData();
                                    clear();
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
        compTable.setOnMouseClicked((e) -> {
            if (compTable.getSelectionModel().getSelectedIndex() == -1) {
            } else {

                Company a = compTable.getSelectionModel().getSelectedItem();
                compName.setText(a.getName());

                compType.getSelectionModel().clearSelection();

                compAcc.setText(a.getAccount());

                compTele.setText(a.getTele());
                compId.setText(Integer.toString(a.getId()));
                compType.getSelectionModel().select(a.getType());
                compAdd.setDisable(true);
                compNew.setDisable(false);
                compDelete.setDisable(false);
                compEdite.setDisable(false);

            }

        });
    }

    @FXML
    private void search(KeyEvent event) {
        FilteredList<Company> filteredData = new FilteredList<>(items, p -> true);

        filteredData.setPredicate(pa -> {

            if (search.getText() == null || search.getText().isEmpty()) {
                return true;
            }

            String lowerCaseFilter = search.getText().toLowerCase();

            return (pa.getName().contains(lowerCaseFilter));

        });

        SortedList<Company> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(compTable.comparatorProperty());
        compTable.setItems(sortedData);
    }

    private void intitColumns() {
        compTabTele.setCellValueFactory(new PropertyValueFactory<>("tele"));

        compTabAcc.setCellValueFactory(new PropertyValueFactory<>("account"));

        compTabType.setCellValueFactory(new PropertyValueFactory<>("type"));

        compTabName.setCellValueFactory(new PropertyValueFactory<>("name"));

        compTabId.setCellValueFactory(new PropertyValueFactory<>("id"));
    }

    private void getData() {
        try {
            compTable.setItems(Company.getData());
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }
    ObservableList<Company> items;

    private void clear() {
        setAutoNumber();
        compAdd.setDisable(false);
        compNew.setDisable(true);
        compDelete.setDisable(true);
        compEdite.setDisable(true);

        compName.setText("");
        compTable.getSelectionModel().clearSelection();
        compType.getSelectionModel().clearSelection();

        compAcc.setText("");

        compTele.setText("");

    }

    private void setAutoNumber() {
        try {
            compId.setText(Company.getAutoNum());
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    @FXML
    private void compNew(ActionEvent event) {
        clear();
    }

    @FXML
    private void compDelete(ActionEvent event) {
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
                                    alert.setTitle("Delete Company");
                                    alert.setHeaderText("سيتم حذف الشركة");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        Company co = new Company();
                                        co.setId(Integer.parseInt(compId.getText()));

                                        co.Delete();
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
                clear();
                getData();
                progress.setVisible(false);

                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void compEdite(ActionEvent event) {
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
                                    alert.setTitle("Edite Company");
                                    alert.setHeaderText("سيتم تعديل الشركة");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        Company co = new Company();
                                        co.setId(Integer.parseInt(compId.getText()));
                                        co.setName(compName.getText());
                                        co.setAccount(compAcc.getText());
                                        co.setTele(compTele.getText());
                                        co.setType(compType.getSelectionModel().getSelectedItem());
                                        co.Edite();
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
                clear();
                getData();
                progress.setVisible(false);

                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void compAdd(ActionEvent event) {
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
                                    Company co = new Company();
                                    co.setId(Integer.parseInt(compId.getText()));
                                    co.setName(compName.getText());
                                    co.setAccount(compAcc.getText());
                                    co.setTele(compTele.getText());
                                    co.setType(compType.getSelectionModel().getSelectedItem());
                                    co.Add();

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
                clear();
                getData();
                progress.setVisible(false);

                super.succeeded();
            }
        };
        service.start();
    }

}
