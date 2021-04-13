/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.admission;

import assets.classes.AlertDialogs;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import screens.admission.assets.Admission;
import screens.admission.assets.AdmissionServices;
import screens.admission.assets.Services;
import screens.mainDataScreen.assets.Doctors;
import screens.mainDataScreen.assets.DoctorsServices;

/**
 * FXML Controller class
 *
 * @author ahmed
 */
public class AdmissionScreenServicesController implements Initializable {

    AdmissionScreenController parentController;
    @FXML
    private Label serviceId;
    @FXML
    private ComboBox<Doctors> serviceDoctor;
    @FXML
    private ComboBox<DoctorsServices> serviceService;
    @FXML
    private Button serviceNew;
    @FXML
    private Button serviceDelete;
    @FXML
    private Button serviceEdite;

    @FXML
    private Button serviceAdd;
    @FXML
    private JFXTextField search;
    @FXML
    private TableView<Services> serviceTable;
    @FXML
    private TableColumn<Services, String> serviceTabCost;
    @FXML
    private TableColumn<Services, String> serviceTabService;
    @FXML
    private TableColumn<Services, String> serviceTabDoctor;
    @FXML
    private TableColumn<Services, String> serviceTabId;
    @FXML
    private ProgressIndicator progress;

    Admission admission;

    @Override
    public void initialize(URL url, ResourceBundle rb) {//addService();
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
                                    setTableColumns();
                                    getDataToTable();
                                    fillCombos();
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
        serviceDoctor.setOnAction((event) -> {
            setServices(serviceDoctor.getSelectionModel().getSelectedItem().getId());
        });
        serviceTable.setOnMouseClicked((e) -> {
            if (serviceTable.getSelectionModel().getSelectedIndex() == -1) {
            } else {

                Services pa = serviceTable.getSelectionModel().getSelectedItem();
                serviceId.setText(Integer.toString(pa.getId()));
                ObservableList<Doctors> items1 = serviceDoctor.getItems();
                for (Doctors a : items1) {
                    if (a.getName().equals(pa.getDoctor())) {

                        serviceDoctor.getSelectionModel().select(a);
                    }
                }

                ObservableList<DoctorsServices> items2 = serviceService.getItems();
                for (DoctorsServices a : items2) {
                    if (a.getName().equals(pa.getService())) {

                        serviceService.getSelectionModel().select(a);
                    }
                }
                serviceAdd.setDisable(true);
                serviceDelete.setDisable(false);
                serviceEdite.setDisable(false);
                serviceNew.setDisable(false);
            }

        });

    }

    private void relooad() {
        clear();
        getDataToTable();
        parentController.getAdmissionDataToTable();
        parentController.setSlectedItem(admission);
        parentController.updateParent();

    }

    private void clear() {
        try {
            getAutoNum();
            serviceDoctor.getSelectionModel().clearSelection();
            serviceService.getSelectionModel().clearSelection();
            serviceTable.getSelectionModel().clearSelection();
            serviceAdd.setDisable(false);
            serviceDelete.setDisable(true);
            serviceEdite.setDisable(true);
            serviceNew.setDisable(true);
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    private void setServices(int id) {
        try {
            serviceService.setItems(DoctorsServices.getData(Integer.toString(id)));
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    private void setTableColumns() {
        serviceTabCost.setCellValueFactory(new PropertyValueFactory<>("cost"));

        serviceTabService.setCellValueFactory(new PropertyValueFactory<>("service"));

        serviceTabDoctor.setCellValueFactory(new PropertyValueFactory<>("doctor"));

        serviceTabId.setCellValueFactory(new PropertyValueFactory<>("id"));
    }

    private void getDataToTable() {
        try {
            serviceTable.setItems(Services.getData(admission.getId()));
            items = serviceTable.getItems();
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }
    ObservableList<Services> items;

    public void fillCombos() throws Exception {
        serviceDoctor.setItems(Doctors.getData());
        serviceDoctor.setConverter(new StringConverter<Doctors>() {
            @Override
            public String toString(Doctors contract) {
                return contract.getName();
            }

            @Override
            public Doctors fromString(String string) {
                return null;
            }
        });
        serviceDoctor.setCellFactory(cell -> new ListCell<Doctors>() {

            GridPane gridPane = new GridPane();
            Label lblid = new Label();
            Label lblName = new Label();
            Label lblQuali = new Label();

            {

                gridPane.getColumnConstraints().addAll(
                        new ColumnConstraints(100, 100, 100), new ColumnConstraints(100, 100, 100),
                        new ColumnConstraints(100, 100, 100)
                );

                gridPane.add(lblid, 0, 1);
                gridPane.add(lblName, 1, 1);
                gridPane.add(lblQuali, 2, 1);

            }

            @Override
            protected void updateItem(Doctors person, boolean empty) {
                super.updateItem(person, empty);

                if (!empty && person != null) {

                    // Update our Labels
                    lblid.setText("م: " + Integer.toString(person.getId()));
                    lblName.setText("الاسم: " + person.getName());
                    lblQuali.setText("التخصص: " + person.getQualification_name());
                    // Set this ListCell's graphicProperty to display our GridPane
                    setGraphic(gridPane);
                } else {
                    // Nothing to display here
                    setGraphic(null);
                }
            }
        });
        serviceService.setConverter(new StringConverter<DoctorsServices>() {
            @Override
            public String toString(DoctorsServices contract) {
                return contract.getName();
            }

            @Override
            public DoctorsServices fromString(String string) {
                return null;
            }
        });
        serviceService.setCellFactory(cell -> new ListCell<DoctorsServices>() {

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
            protected void updateItem(DoctorsServices person, boolean empty) {
                super.updateItem(person, empty);

                if (!empty && person != null) {

                    // Update our Labels
                    lblid.setText("الخدمة: " + person.getName());
                    lblName.setText("التكلفة: " + person.getCost());

                    // Set this ListCell's graphicProperty to display our GridPane
                    setGraphic(gridPane);
                } else {
                    // Nothing to display here
                    setGraphic(null);
                }
            }
        });
    }

    void initData(Admission id) {
        this.admission = id;
    }

    void setParentController(AdmissionScreenController parentController) {
        this.parentController = parentController;
    }

    @FXML
    private void serviceNew(ActionEvent event) {
        clear();
    }

    @FXML
    private void serviceDelete(ActionEvent event) {
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
                                    alert.setTitle("Deletting Service");
                                    alert.setHeaderText("سيتم حذف الخدمة");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        Services sv = new Services();
                                        sv.setId(Integer.parseInt(serviceId.getText()));
                                        sv.setAdmissionId(admission.getId());
                                        sv.Delete();
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
                relooad();
                progress.setVisible(false);
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void serviceEdite(ActionEvent event) {
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
                                    alert.setTitle("Editting Service");
                                    alert.setHeaderText("سيتم تعديل الخدمة");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        Services sv = new Services();
                                        sv.setId(Integer.parseInt(serviceId.getText()));
                                        DoctorsServices a = serviceService.getSelectionModel().getSelectedItem();
                                        sv.setServiceId(a.getId());
                                        sv.setCost(a.getCost());
                                        sv.setAdmissionId(admission.getId());
                                        sv.setDoctorId(serviceDoctor.getSelectionModel().getSelectedItem().getId());
                                        sv.Edite();
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
                relooad();
                progress.setVisible(false);
                super.succeeded();
            }
        };
        service.start();
    }

    private void addService() {
        try {
            Stage popupwindow = new Stage();

            popupwindow.initModality(Modality.APPLICATION_MODAL);
            popupwindow.setTitle("This is a pop up window");

            Button button1 = new Button("Close this pop up window");

            button1.setOnAction(e -> popupwindow.close());

            VBox layout = new VBox(10);
            FXMLLoader lod = new FXMLLoader(getClass().getResource("/screens/mainDataScreen/MainDataScreenDoctors.fxml"));
            layout.getChildren().addAll(lod.load(), button1);

            layout.setAlignment(Pos.CENTER);

            Scene scene1 = new Scene(layout, 300, 250);

            popupwindow.setScene(scene1);

            popupwindow.showAndWait();
        } catch (IOException ex) {
            AlertDialogs.showErrors(ex);
        }

    }

    @FXML
    private void serviceAdd(ActionEvent event) {
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
                                    Services sv = new Services();
                                    sv.setId(Integer.parseInt(serviceId.getText()));
                                    DoctorsServices a = serviceService.getSelectionModel().getSelectedItem();
                                    sv.setServiceId(a.getId());
                                    sv.setCost(a.getCost());
                                    sv.setAdmissionId(admission.getId());
                                    sv.setDoctorId(serviceDoctor.getSelectionModel().getSelectedItem().getId());
                                    sv.Add();
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
                relooad();
                progress.setVisible(false);
                super.succeeded();
            }
        };
        service.start();
    }

    private void getAutoNum() throws Exception {
        serviceId.setText(Services.getAutoNum());
    }

    @FXML
    private void search(KeyEvent event) {
        FilteredList<Services> filteredData = new FilteredList<>(items, p -> true);

        filteredData.setPredicate(pa -> {

            if (search.getText() == null || search.getText().isEmpty()) {
                return true;
            }

            String lowerCaseFilter = search.getText().toLowerCase();

            return (pa.getDoctor().contains(lowerCaseFilter)
                    || pa.getService().contains(lowerCaseFilter));

        });

        SortedList< Services> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(serviceTable.comparatorProperty());
        serviceTable.setItems(sortedData);
    }

}
