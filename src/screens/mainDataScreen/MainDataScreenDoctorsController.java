/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.mainDataScreen;

import assets.animation.ZoomInDown;
import assets.animation.ZoomInUp;
import assets.classes.AlertDialogs;
import assets.classes.AutoCompleteBox;
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
import javafx.scene.control.ListCell;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;
import screens.mainDataScreen.assets.Contract;
import screens.mainDataScreen.assets.DoctorQualifications;
import screens.mainDataScreen.assets.Doctors;
import screens.mainDataScreen.assets.DoctorsServices;
import screens.mainDataScreen.assets.DoctorsServicesName;

public class MainDataScreenDoctorsController implements Initializable {

    @FXML
    private TableView<Doctors> doctorTable;
    @FXML
    private TableColumn<Doctors, String> doctorTabTele2;
    @FXML
    private TableColumn<Doctors, String> doctorTabTele1;
    @FXML
    private TableColumn<Doctors, String> doctorTabAge;
    @FXML
    private TableColumn<Doctors, String> doctorTabName;
    @FXML
    private TableColumn<Doctors, String> doctorTabId;
    @FXML
    private Label doctorId;
    @FXML
    private TextField doctorName;
    @FXML
    private TextField doctorAge;
    @FXML
    private TextField doctorTele1;
    @FXML
    private TextField doctorTele2;
    @FXML
    private Button doctorNew;
    @FXML
    private Button doctorEdite;
    @FXML
    private Button doctorAdd;
    @FXML
    private Button doctorDelete;
    @FXML
    private JFXTextField search;
    @FXML
    private ProgressIndicator progress;
    @FXML
    private TableView<DoctorsServices> serviceTable;
    @FXML
    private TableColumn<DoctorsServices, String> serviceTabCost;
    @FXML
    private TableColumn<DoctorsServices, String> serviceTabName;
    @FXML
    private TextField serviceCost;
    @FXML
    private MaterialDesignIconView serviceNameAdd;
    @FXML
    private ComboBox<DoctorsServicesName> serviceName;
    @FXML
    private Button serviceNew;
    @FXML
    private Button serviceEdite;
    @FXML
    private Button serviceAdd;
    @FXML
    private Button serviceDelete;
    @FXML
    private ProgressIndicator serviceProgress;
    @FXML
    private HBox serviceBox;
    @FXML
    private HBox doctorBox;
    @FXML
    private TableColumn<DoctorsServices, String> doctorTabQualification;
    @FXML
    private MaterialDesignIconView qualificationNameAdd;
    @FXML
    private ComboBox< DoctorQualifications> qualificationName;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        serviceBox.setVisible(false);
        new ZoomInUp(doctorBox).play();

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
                                    setServiceTableColumns();
                                    getDataToTable();
                                    fillCombo();
                                    serviceClear();

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
                serviceProgress.setVisible(false);
                super.succeeded();
            }
        };
        service.start();
        doctorTable.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            if (doctorTable.getSelectionModel().getSelectedIndex() == -1) {
                serviceBox.setVisible(false);
            } else {
                if (!serviceBox.isVisible()) {
                    serviceBox.setVisible(true);
                    new ZoomInDown(serviceBox).play();
                }
                getServiceDataToTable(doctorTable.getSelectionModel().getSelectedIndex());
                doctorAdd.setDisable(true);
                doctorEdite.setDisable(false);
                doctorDelete.setDisable(false);
                doctorNew.setDisable(false);

                Doctors pa = doctorTable.getSelectionModel().getSelectedItem();
                doctorId.setText(Integer.toString(pa.getId()));
                doctorName.setText(pa.getName());
                doctorAge.setText(pa.getAge());
                doctorTele1.setText(pa.getTele1());
                doctorTele2.setText(pa.getTele2());
                ObservableList<DoctorQualifications> items2 = qualificationName.getItems();
                for (DoctorQualifications a : items2) {
                    if (a.getName().equals(pa.getQualification_name())) {

                        qualificationName.getSelectionModel().select(a);
                    }
                }
            }
        });
        serviceTable.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {

            serviceName.setDisable(true);
            DoctorsServices sa = serviceTable.getSelectionModel().getSelectedItem();
            serviceCost.setText(sa.getCost());

            ObservableList<DoctorsServicesName> items2 = serviceName.getItems();
            for (DoctorsServicesName a : items2) {
                if (a.getName().equals(sa.getName())) {

                    serviceName.getSelectionModel().select(a);
                }
            }
            serviceAdd.setDisable(true);
            serviceEdite.setDisable(false);
            serviceDelete.setDisable(false);
            serviceNew.setDisable(false);

        });
    }

    private void fillCombo() throws Exception {
        qualificationName.getItems().clear();
        Doctors dos = new Doctors();
        qualificationName.setItems(DoctorQualifications.getData());
        qualificationName.setConverter(new StringConverter<DoctorQualifications>() {
            @Override
            public String toString(DoctorQualifications contract) {
                return contract.getName();
            }

            @Override
            public DoctorQualifications fromString(String string) {
                return null;
            }
        });
        qualificationName.setCellFactory(cell -> new ListCell<DoctorQualifications>() {

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
            protected void updateItem(DoctorQualifications person, boolean empty) {
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
    private void doctorNew(ActionEvent event) {
        clear();
        serviceBox.setVisible(false);
    }

    @FXML
    private void doctorEdite(ActionEvent event) {
        if (doctorName.getText().isEmpty() || doctorName.getText() == null) {
            AlertDialogs.showError("اسم الطبيب لا يجب ان يكون فارغا!!");
        } else {
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
                                        try {
                                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                            alert.setTitle("Editting Doctor");
                                            alert.setHeaderText("سيتم تعديل بيانات الطبيب");
                                            alert.setContentText("هل انت متاكد؟");

                                            Optional<ButtonType> result = alert.showAndWait();
                                            if (result.get() == ButtonType.OK) {
                                                Doctors doct = new Doctors();
                                                doct.setId(Integer.parseInt(doctorId.getText()));
                                                doct.setName(doctorName.getText());
                                                doct.setAge(doctorAge.getText());
                                                doct.setTele1(doctorTele1.getText());
                                                doct.setTele2(doctorTele2.getText());
                                                doct.setQualification_id(qualificationName.getSelectionModel().getSelectedItem().getId());
                                                doct.Edite();
                                            }
                                        } catch (Exception ex) {
                                            AlertDialogs.showErrors(ex);
                                        }
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
                    getDataToTable();
                    progress.setVisible(false);
                    super.succeeded();
                }
            };
            service.start();
        }
    }

    @FXML
    private void doctorAdd(ActionEvent event) {
        if (doctorName.getText().isEmpty() || doctorName.getText() == null) {
            AlertDialogs.showError("اسم الطبيب لا يجب ان يكون فارغا!!");
        } else {
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
                                        try {
                                            Doctors Doc = new Doctors();
                                            Doc.setId(Integer.parseInt(doctorId.getText()));
                                            Doc.setName(doctorName.getText());
                                            Doc.setAge(doctorAge.getText());
                                            Doc.setTele1(doctorTele1.getText());
                                            Doc.setTele2(doctorTele2.getText());
                                            Doc.setQualification_id(qualificationName.getSelectionModel().getSelectedItem().getId());
                                            Doc.Add();

                                        } catch (Exception ex) {
                                            AlertDialogs.showErrors(ex);
                                        }
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
                    getDataToTable();
                    progress.setVisible(false);
                    super.succeeded();
                }
            };
            service.start();
        }
    }

    @FXML
    private void doctorDelete(ActionEvent event) {
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
                                    try {
                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setTitle("Deleting Doctor");
                                        alert.setHeaderText("سيتم حذف الطبيب");
                                        alert.setContentText("هل انت متاكد؟");

                                        Optional<ButtonType> result = alert.showAndWait();
                                        if (result.get() == ButtonType.OK) {
                                            Doctors Doc = new Doctors();
                                            Doc.setId(Integer.parseInt(doctorId.getText()));
                                            Doc.Delete();
                                        }

                                    } catch (Exception ex) {
                                        AlertDialogs.showErrors(ex);
                                    }
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
                getDataToTable();
                progress.setVisible(false);
                super.succeeded();
            }
        };
        service.start();

    }

    @FXML
    private void search(KeyEvent event) {
        FilteredList<Doctors> filteredData = new FilteredList<>(items, p -> true);

        filteredData.setPredicate(pa -> {

            if (search.getText() == null || search.getText().isEmpty()) {
                return true;
            }

            String lowerCaseFilter = search.getText().toLowerCase();

            return (pa.getName().contains(lowerCaseFilter)
                    || pa.getAge().contains(lowerCaseFilter)
                    || pa.getQualification_name().contains(lowerCaseFilter)
                    || pa.getTele1().contains(lowerCaseFilter)
                    || pa.getTele2().contains(lowerCaseFilter));

        });

        SortedList< Doctors> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(doctorTable.comparatorProperty());
        doctorTable.setItems(sortedData);
    }

    private void setAutoNumber() {
        try {
            doctorId.setText(Doctors.getAutoNum());
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    private void clear() {
        setAutoNumber();
        doctorName.setText("");
        doctorAge.setText("");
        doctorTele1.setText("");
        doctorTele2.setText("");
        doctorAdd.setDisable(false);
        doctorEdite.setDisable(true);
        doctorDelete.setDisable(true);
        doctorNew.setDisable(true);
        doctorTable.getSelectionModel().clearSelection();
        qualificationName.getSelectionModel().clearSelection();
    }

    private void setTableColumns() {
        doctorTabTele2.setCellValueFactory(new PropertyValueFactory<>("tele2"));
        doctorTabTele1.setCellValueFactory(new PropertyValueFactory<>("tele1"));
        doctorTabAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        doctorTabQualification.setCellValueFactory(new PropertyValueFactory<>("qualification_name"));
        doctorTabName.setCellValueFactory(new PropertyValueFactory<>("name"));
        doctorTabId.setCellValueFactory(new PropertyValueFactory<>("id"));
    }

    private void getDataToTable() {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        //Background work                       
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(() -> {
                            try {
                                try {
                                    doctorTable.setItems(Doctors.getData());
                                } catch (Exception ex) {
                                    AlertDialogs.showErrors(ex);
                                }
                            } finally {
                                latch.countDown();
                            }
                        });
                        latch.await();

                        return null;
                    }
                };
            }

            @Override
            protected void succeeded() {

                items = doctorTable.getItems();
                progress.setVisible(false);
                super.succeeded();
            }
        };
        service.start();
    }
    ObservableList<Doctors> items;

    //Doctor Services Methods
    @FXML
    private void serviceNameAdd(MouseEvent event) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Add Doctor Service");
        dialog.setHeaderText("اضافة اسم الخدمة");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (result.get().isEmpty() || result.get() == null) {
                AlertDialogs.showError("خطا!! يرجي ادخال اسم الخدمة");
            } else {
                final String results = result.get();
                try {
                    Service service = new Service() {
                        @Override
                        protected Task createTask() {
                            return new Task() {
                                @Override
                                protected Object call() throws Exception {
                                    final CountDownLatch latch = new CountDownLatch(1);
                                    Platform.runLater(() -> {
                                        try {
                                            DoctorsServicesName.Add(results);
                                        } catch (Exception ex) {
                                            AlertDialogs.showErrors(ex);
                                        } finally {
                                            latch.countDown();
                                        }
                                    });
                                    latch.await();

                                    return null;
                                }

                                @Override
                                protected void succeeded() {
                                    getServiceNameToCombobox();
                                }
                            };
                        }
                    };
                    service.start();

                } catch (Exception ex) {
                    AlertDialogs.showErrors(ex);
                }
            }
        }

    }

    @FXML
    private void serviceNew(ActionEvent event) {
        serviceClear();

    }

    @FXML
    private void serviceEdite(ActionEvent event) {
        int docor_id = serviceTable.getSelectionModel().getSelectedItem().getDoctor_id();
        serviceProgress.setVisible(true);
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
                                    try {
                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setTitle("Editing Service");
                                        alert.setHeaderText("سيتم تعديل بيانات الخدمة");
                                        alert.setContentText("هل انت متاكد؟");

                                        Optional<ButtonType> result = alert.showAndWait();
                                        if (result.get() == ButtonType.OK) {
                                            DoctorsServices ds = new DoctorsServices();
                                            ds.setId(serviceTable.getSelectionModel().getSelectedItem().getId());
                                            ds.setDoctor_id(docor_id);
                                            ds.setCost(serviceCost.getText());
                                            ds.setName(serviceName.getSelectionModel().getSelectedItem().getName());
                                            ds.Edite();
                                        }

                                    } catch (Exception ex) {
                                        AlertDialogs.showErrors(ex);
                                    }
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
                getServiceDataToTableDoctorId(docor_id);
                serviceProgress.setVisible(false);
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void serviceAdd(ActionEvent event) {
        int docor_id = doctorTable.getSelectionModel().getSelectedItem().getId();
        serviceProgress.setVisible(true);
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
                                    try {

                                        DoctorsServices ds = new DoctorsServices();
                                        ds.setDoctor_id(docor_id);
                                        ds.setCost(serviceCost.getText());
                                        ds.setService_id(serviceName.getSelectionModel().getSelectedItem().getId());
                                        ds.Add();

                                    } catch (Exception ex) {
                                        AlertDialogs.showErrors(ex);
                                    }
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
                getServiceDataToTableDoctorId(docor_id);
                serviceProgress.setVisible(false);
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void serviceDelete(ActionEvent event) {
        int docor_id = serviceTable.getSelectionModel().getSelectedItem().getDoctor_id();
        serviceProgress.setVisible(true);
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
                                    try {
                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setTitle("Deleting Service");
                                        alert.setHeaderText("سيتم حذف بيانات الخدمة");
                                        alert.setContentText("هل انت متاكد؟");

                                        Optional<ButtonType> result = alert.showAndWait();
                                        if (result.get() == ButtonType.OK) {
                                            DoctorsServices ds = new DoctorsServices();
                                            ds.setId(serviceTable.getSelectionModel().getSelectedItem().getId());

                                            ds.Delete();
                                        }

                                    } catch (Exception ex) {
                                        AlertDialogs.showErrors(ex);
                                    }
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
                getServiceDataToTableDoctorId(docor_id);
                serviceProgress.setVisible(false);
                super.succeeded();
            }
        };
        service.start();
    }

    private void serviceClear() {

        getServiceNameToCombobox();
        serviceName.setDisable(false);
        serviceCost.setText("");
        serviceName.getSelectionModel().select(-1);
        serviceAdd.setDisable(false);
        serviceEdite.setDisable(true);
        serviceDelete.setDisable(true);
        serviceNew.setDisable(true);
    }

    private void setServiceTableColumns() {
        serviceTabName.setCellValueFactory(new PropertyValueFactory<>("name"));
        serviceTabCost.setCellValueFactory(new PropertyValueFactory<>("cost"));

    }

    private void getServiceDataToTable(int selectedIndex) {
        try {
            serviceProgress.setVisible(true);
            serviceTable.setItems(DoctorsServices.getData(Integer.toString(doctorTable.getItems().get(selectedIndex).getId())));
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
        serviceProgress.setVisible(false);
    }

    private void getServiceDataToTableDoctorId(int doctor) {
        try {
            serviceProgress.setVisible(true);
            serviceTable.setItems(DoctorsServices.getData(Integer.toString(doctor)));
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
        serviceProgress.setVisible(false);
    }

    private void getServiceNameToCombobox() {
        try {
            serviceProgress.setVisible(true);
            serviceName.setItems(DoctorsServicesName.getData());
            serviceName.setConverter(new StringConverter<DoctorsServicesName>() {
                @Override
                public String toString(DoctorsServicesName patient) {
                    return patient.getName();
                }

                @Override
                public DoctorsServicesName fromString(String string) {
                    return null;
                }
            });
            serviceName.setCellFactory(cell -> new ListCell<DoctorsServicesName>() {

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
                protected void updateItem(DoctorsServicesName person, boolean empty) {
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
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
        serviceProgress.setVisible(false);
    }

    @FXML
    private void qualificationNameAdd(MouseEvent event) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Add Doctor Qualification");
        dialog.setHeaderText("اضافة اسم التخصص");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (result.get().isEmpty() || result.get() == null) {
                AlertDialogs.showError("خطا!! يرجي ادخال اسم التخصص");
            } else {
                final String results = result.get();
                try {
                    Service service = new Service() {
                        @Override
                        protected Task createTask() {
                            return new Task() {
                                @Override
                                protected Object call() throws Exception {
                                    final CountDownLatch latch = new CountDownLatch(1);
                                    Platform.runLater(() -> {
                                        try {
                                            DoctorQualifications.Add(results);
                                        } catch (Exception ex) {
                                            AlertDialogs.showErrors(ex);
                                        } finally {
                                            latch.countDown();
                                        }
                                    });
                                    latch.await();

                                    return null;
                                }

                                @Override
                                protected void succeeded() {
                                    try {
                                        fillCombo();
                                    } catch (Exception ex) {
                                        AlertDialogs.showErrors(ex);
                                    }
                                }
                            };
                        }
                    };
                    service.start();

                } catch (Exception ex) {
                    AlertDialogs.showErrors(ex);
                }
            }
        }
    }
}
