/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.admission;

import assets.classes.AlertDialogs;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import javafx.application.Platform;
import javafx.collections.FXCollections;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import screens.admission.assets.Admission;
import screens.admission.assets.Examination;
import screens.mainDataScreen.assets.Clincs;
import screens.mainDataScreen.assets.Doctors;

/**
 * FXML Controller class
 *
 * @author ahmed
 */
public class AdmissionScreenExaminationController implements Initializable {

    @FXML
    private Label examinationId;
    @FXML
    private ComboBox<Clincs> examinationClinic;
    @FXML
    private ComboBox<String> examinationType;
    @FXML
    private ProgressIndicator process;
    @FXML
    private Button examinationNew;
    @FXML
    private Button examinationDelete;
    @FXML
    private Button examinationEdite;
    @FXML
    private Button examinationAdd;
    @FXML
    private JFXTextField search;
    @FXML
    private TableView<Examination> examinationTable;
    @FXML
    private TableColumn<Examination, String> examinationTabCost;
    @FXML
    private TableColumn<Examination, String> examinationTabDoctor;
    @FXML
    private TableColumn<Examination, String> examinationTabType;
    @FXML
    private TableColumn<Examination, String> examinationTabClinic;
    @FXML
    private TableColumn<Examination, String> examinationTabId;
    Admission admission;
    @FXML
    private ComboBox<Doctors> examinationDoctor;
    AdmissionScreenController parentController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        process.setVisible(true);
        Service<Void> service2 = new Service<Void>() {
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
                                    clear();
                                    initialColumns();
                                    updateData();
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
                process.setVisible(false);

                super.succeeded();
            }
        };
        service2.start();

        examinationTable.setOnMouseClicked((event) -> {
            if (examinationTable.getSelectionModel().getFocusedIndex() == -1) {
            } else {
                examinationAdd.setDisable(true);
                examinationEdite.setDisable(false);
                examinationDelete.setDisable(false);
                examinationNew.setDisable(false);

                Examination pa = examinationTable.getSelectionModel().getSelectedItem();
                ObservableList<Clincs> items2 = examinationClinic.getItems();
                examinationId.setText(Integer.toString(pa.getId()));
                for (Clincs a : items2) {
                    if (a.getName().equals(pa.getClinc())) {

                        examinationClinic.getSelectionModel().select(a);
                    }
                }
                ObservableList<Doctors> itemss2 = examinationDoctor.getItems();
                for (Doctors a : itemss2) {
                    if (a.getName().equals(pa.getDoctor())) {

                        examinationDoctor.getSelectionModel().select(a);
                    }
                }
                examinationType.getSelectionModel().select(pa.getType());

            }
        });
    }
    ObservableList<Examination> items;

    public void fillCombo() {
        Service<Void> service = new Service<Void>() {
            ObservableList<Clincs> clincData;
            ObservableList<Clincs> clincDataSearch;
            ObservableList<Doctors> DoctorData;
            ObservableList<Doctors> DoctorDataSearch;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            clincData = Clincs.getData();
                            DoctorData = Doctors.getData();
                        } catch (Exception ex) {
                            AlertDialogs.showErrors(ex);
                        }
                        return null;
                    }
                };

            }

            @Override
            protected void succeeded() {
                examinationType.getItems().add("كشف");
                examinationType.getItems().add("اعادة");
                examinationType.getItems().add("استشارة");
                examinationDoctor.setItems(DoctorData);
                examinationDoctor.setEditable(true);
                examinationDoctor.setOnKeyReleased((event) -> {

                    if (examinationDoctor.getEditor().getText().length() == 0) {
                        examinationDoctor.setItems(DoctorData);
                    } else {
                        DoctorDataSearch = FXCollections.observableArrayList();

                        for (Doctors a : DoctorData) {
                            if (a.getName().contains(examinationDoctor.getEditor().getText())) {
                                DoctorDataSearch.add(a);
                            }
                        }
                        examinationDoctor.setItems(DoctorDataSearch);
                        examinationDoctor.show();
                    }
                });
                examinationDoctor.setConverter(new StringConverter<Doctors>() {
                    @Override
                    public String toString(Doctors patient) {
                        return patient != null ? patient.getName() : "";
                    }

                    @Override
                    public Doctors fromString(String string) {
                        Doctors b = null;
                        for (Doctors a : (ObservableList<Doctors>) examinationDoctor.getItems()) {
                            if (a.getName().contains(examinationDoctor.getEditor().getText())) {
                                b = a;
                            }
                        }
                        return b;
                    }
                });
                examinationDoctor.setCellFactory(cell -> new ListCell<Doctors>() {

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
                            lblid.setText("م: " + Integer.toString(person.getId()));
                            lblName.setText("الاسم: " + person.getName());
                            lblQuali.setText("التخصص: " + person.getQualification_name());

                            setGraphic(gridPane);
                        } else {
                            setGraphic(null);
                        }
                    }
                });

                examinationClinic.setItems(clincData);
                examinationClinic.setEditable(true);
                examinationClinic.setOnKeyReleased((event) -> {

                    if (examinationClinic.getEditor().getText().length() == 0) {
                        examinationClinic.setItems(clincData);
                    } else {
                        clincDataSearch = FXCollections.observableArrayList();

                        for (Clincs a : clincData) {
                            if (a.getName().contains(examinationClinic.getEditor().getText())) {
                                clincDataSearch.add(a);
                            }
                        }
                        examinationClinic.setItems(clincDataSearch);
                        examinationClinic.show();
                    }
                });
                examinationClinic.setConverter(new StringConverter<Clincs>() {
                    @Override
                    public String toString(Clincs patient) {
                        return patient != null ? patient.getName() : "";
                    }

                    @Override
                    public Clincs fromString(String string) {
                        Clincs b = null;
                        for (Clincs a : (ObservableList<Clincs>) examinationClinic.getItems()) {
                            if (a.getName().contains(examinationClinic.getEditor().getText())) {
                                b = a;
                            }
                        }
                        return b;
                    }
                });
                examinationClinic.setCellFactory(cell -> new ListCell<Clincs>() {

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
                    protected void updateItem(Clincs person, boolean empty) {
                        super.updateItem(person, empty);

                        if (!empty && person != null) {

                            lblid.setText("م: " + Integer.toString(person.getId()));
                            lblName.setText("الاسم: " + person.getName());

                            setGraphic(gridPane);
                        } else {

                            setGraphic(null);
                        }
                    }
                });
                super.succeeded();
            }
        };
        service.start();

    }

    public void updateData() {
        process.setVisible(true);
        Service<Void> service2 = new Service<Void>() {
            ObservableList<Examination> data;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {

                        try {
                            data = Examination.getData(admission.getId());

                        } catch (Exception ex) {
                            AlertDialogs.showErrors(ex);
                        }

                        return null;
                    }
                };

            }

            @Override
            protected void succeeded() {
                process.setVisible(false);
                examinationTable.setItems(data);
                items = data;
                super.succeeded();
            }
        };
        service2.start();
    }

    public void updateDataParent() {
        process.setVisible(true);
        Service<Void> service2 = new Service<Void>() {
            ObservableList<Examination> data;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {

                        try {
                            clear();
                            data = Examination.getData(admission.getId());
                            parentController.getAdmissionDataToTable();
                            parentController.setSlectedItem(admission);
                            parentController.updateParent();
                        } catch (Exception ex) {
                            AlertDialogs.showErrors(ex);
                        }
                        return null;
                    }
                };

            }

            @Override
            protected void succeeded() {
                process.setVisible(false);
                examinationTable.setItems(data);
                items = data;
                super.succeeded();
            }
        };
        service2.start();
    }

    void initData(Admission ad) {
        this.admission = ad;
    }

    private void initialColumns() {
        examinationTabCost.setCellValueFactory(new PropertyValueFactory<>("cost"));

        examinationTabDoctor.setCellValueFactory(new PropertyValueFactory<>("doctor"));

        examinationTabType.setCellValueFactory(new PropertyValueFactory<>("type"));

        examinationTabClinic.setCellValueFactory(new PropertyValueFactory<>("clinc"));

        examinationTabId.setCellValueFactory(new PropertyValueFactory<>("id"));
    }

    @FXML
    private void examinationNew(ActionEvent event) {
        clear();
    }

    @FXML
    private void examinationDelete(ActionEvent event) {
        if (examinationType.getSelectionModel().getSelectedIndex() == -1
                || examinationClinic.getSelectionModel().getSelectedIndex() == -1
                || examinationDoctor.getSelectionModel().getSelectedIndex() == -1) {
            AlertDialogs.showError("لا يجب ترك اختيار فارغ");
        } else {
            process.setVisible(true);
            Service<Void> service2 = new Service<Void>() {
                boolean ok = true;

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
                                        alert.setTitle("Deleting Examination");
                                        alert.setHeaderText("سيتم حذف الكشف");
                                        alert.setContentText("هل انت متاكد؟");

                                        Optional<ButtonType> result = alert.showAndWait();
                                        if (result.get() == ButtonType.OK) {
                                            Examination ex = new Examination();
                                            ex.setId(Integer.parseInt(examinationId.getText()));
                                            ex.setAdmissionId(admission.getId());

                                            ex.Delete();
                                        }
                                    } catch (Exception ex) {
                                        AlertDialogs.showErrors(ex);
                                        ok = false;
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
                    process.setVisible(false);
                    if (ok) {
                        updateDataParent();
                    }
                    super.succeeded();
                }
            };
            service2.start();
        }
    }

    @FXML
    private void examinationEdite(ActionEvent event) {
        if (examinationType.getSelectionModel().getSelectedIndex() == -1
                || examinationClinic.getSelectionModel().getSelectedIndex() == -1
                || examinationDoctor.getSelectionModel().getSelectedIndex() == -1) {
            AlertDialogs.showError("لا يجب ترك اختيار فارغ");
        } else {
            process.setVisible(true);
            Service<Void> service2 = new Service<Void>() {
                boolean ok = true;

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
                                        alert.setTitle("Editting Examination");
                                        alert.setHeaderText("سيتم تعديل الكشف");
                                        alert.setContentText("هل انت متاكد؟");

                                        Optional<ButtonType> result = alert.showAndWait();
                                        if (result.get() == ButtonType.OK) {
                                            Examination ex = new Examination();
                                            ex.setId(Integer.parseInt(examinationId.getText()));
                                            ex.setAdmissionId(admission.getId());
                                            ex.setType(examinationType.getSelectionModel().getSelectedItem());
                                            ex.setDoctor_id(examinationDoctor.getItems().get(examinationDoctor.getSelectionModel().getSelectedIndex()).getId());
                                            ex.setClincId(examinationClinic.getItems().get(examinationClinic.getSelectionModel().getSelectedIndex()).getId());
                                            ex.Edite();
                                        }
                                    } catch (Exception ex) {
                                        AlertDialogs.showErrors(ex);
                                        ok = false;
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
                    process.setVisible(false);
                    if (ok) {
                        updateDataParent();
                    }
                    super.succeeded();
                }
            };
            service2.start();
        }
    }

    @FXML
    private void examinationAdd(ActionEvent event) {
        if (examinationType.getSelectionModel().getSelectedIndex() == -1
                || examinationClinic.getSelectionModel().getSelectedIndex() == -1
                || examinationDoctor.getSelectionModel().getSelectedIndex() == -1) {
            AlertDialogs.showError("لا يجب ترك اختيار فارغ");
        } else {
            process.setVisible(true);

            Service<Void> service2 = new Service<Void>() {
                boolean ok = true;

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
                                        Examination ex = new Examination();
                                        ex.setId(Integer.parseInt(examinationId.getText()));
                                        ex.setAdmissionId(admission.getId());
                                        ex.setType(examinationType.getSelectionModel().getSelectedItem());
                                        ex.setDoctor_id(examinationDoctor.getItems().get(examinationDoctor.getSelectionModel().getSelectedIndex()).getId());
                                        ex.setClincId(examinationClinic.getItems().get(examinationClinic.getSelectionModel().getSelectedIndex()).getId());
                                        ex.Add();
                                    } catch (Exception ex) {
                                        AlertDialogs.showErrors(ex);
                                        ok = false;
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
                    process.setVisible(false);
                    if (ok) {
                        updateDataParent();
                    }
                    super.succeeded();
                }
            };
            service2.start();
        }
    }

    @FXML
    private void search(ActionEvent event) {
        FilteredList<Examination> filteredData = new FilteredList<>(items, p -> true);

        filteredData.setPredicate(pa -> {

            if (search.getText() == null || search.getText().isEmpty()) {
                return true;
            }

            String lowerCaseFilter = search.getText().toLowerCase();

            return (pa.getClinc().contains(lowerCaseFilter)
                    || pa.getDoctor().contains(lowerCaseFilter));

        });

        SortedList< Examination> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(examinationTable.comparatorProperty());
        examinationTable.setItems(sortedData);
    }

    private void clear() {
        setAutoNum();
        examinationAdd.setDisable(false);
        examinationEdite.setDisable(true);
        examinationDelete.setDisable(true);
        examinationNew.setDisable(true);

        examinationDoctor.getSelectionModel().clearSelection();
        examinationClinic.getSelectionModel().clearSelection();
        examinationType.getSelectionModel().clearSelection();
        examinationTable.getSelectionModel().clearSelection();
    }

    private void setAutoNum() {
        Service<Void> service = new Service<Void>() {
            String autoNum;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                              autoNum = Examination.getAutoNum();

                        } catch (Exception ex) {
                            AlertDialogs.showErrors(ex);
                        }
                        return null;
                    }
                };

            }

            @Override
            protected void succeeded() {
                examinationId.setText(autoNum);
                super.succeeded();
            }
        };
        service.start();

    }

    void setParentController(AdmissionScreenController parentController) {
        this.parentController = parentController;
    }
}
