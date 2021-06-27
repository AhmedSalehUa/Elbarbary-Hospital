/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.admission;

import assets.classes.AlertDialogs;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.net.URL;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.Alert.AlertType;
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
import javafx.util.StringConverter;
import screens.admission.assets.Admission;
import screens.admission.assets.Surgeries;
import screens.admission.assets.SurgeriesType;
import screens.mainDataScreen.assets.Contract;
import screens.mainDataScreen.assets.Doctors;
import screens.mainDataScreen.assets.PatientsEscort;

/**
 * FXML Controller class
 *
 * @author ahmed
 */
public class AdmissionScreenSurgiesController implements Initializable {

    AdmissionScreenController parentController;
    Admission admission;
    @FXML
    private Label surgiesId;
    @FXML
    private ComboBox<Doctors> surgiesDoctor;
    @FXML
    private ComboBox<Doctors> surgiesAnesthetization;
    @FXML
    private JFXDatePicker surgiesDate;
    @FXML
    private ComboBox<SurgeriesType> surgiesType;
    @FXML
    private ComboBox<PatientsEscort> surgiesEscort;
    @FXML
    private TextField surgiesCost;
    @FXML
    private ProgressIndicator process;
    @FXML
    private Button surgiesNew;
    @FXML
    private Button surgiesDelete;
    @FXML
    private Button surgiesEdite;
    @FXML
    private Button surgiesAdd;
    @FXML
    private JFXTextField search;
    @FXML
    private TableView<Surgeries> surgiesTable;
    @FXML
    private TableColumn<Surgeries, String> surgiesTabEscort;
    @FXML
    private TableColumn<Surgeries, String> surgiesTabDate;
    @FXML
    private TableColumn<Surgeries, String> surgiesTabType;
    @FXML
    private TableColumn<Surgeries, String> surgiesTabAnesthetization;
    @FXML
    private TableColumn<Surgeries, String> surgiesTabDoctor;
    @FXML
    private TableColumn<Surgeries, String> surgiesTabId;
    @FXML
    private MaterialDesignIconView addSurgiesType;
    @FXML
    private TableColumn<Surgeries, String> surgiesTabCost;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        surgiesDate.setConverter(new StringConverter<LocalDate>() {
            private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override
            public String toString(LocalDate localDate) {
                if (localDate == null) {
                    return "";
                }
                return dateTimeFormatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString) {
                if (dateString == null || dateString.trim().isEmpty()) {
                    return null;
                }
                return LocalDate.parse(dateString, dateTimeFormatter);
            }
        });
        process.setVisible(true);
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
                                    intialColumns();
                                    fillCombos();
                                    fillTypesCombos();
                                    clear();
                                    getData();
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

                process.setVisible(false);
                super.succeeded();
            }
        };
        service.start();
        surgiesTable.setOnMouseClicked((e) -> {
            if (surgiesTable.getSelectionModel().getFocusedIndex() == -1) {
            } else {
                Surgeries sur = surgiesTable.getSelectionModel().getSelectedItem();
                surgiesId.setText(Integer.toString(sur.getId()));
                surgiesDate.setValue(LocalDate.parse(sur.getDate()));
                surgiesCost.setText(sur.getCost());
                ObservableList<Doctors> items1 = surgiesDoctor.getItems();
                for (Doctors a : items1) {
                    if (a.getName().equals(sur.getDoctor())) {

                        surgiesDoctor.getSelectionModel().select(a);
                    }
                }
                ObservableList<Doctors> items2 = surgiesAnesthetization.getItems();
                for (Doctors a : items2) {
                    if (a.getName().equals(sur.getAnesthetization())) {

                        surgiesAnesthetization.getSelectionModel().select(a);
                    }
                }
                ObservableList<PatientsEscort> items3 = surgiesEscort.getItems();
                for (PatientsEscort a : items3) {
                    if (a.getName().equals(sur.getEscort())) {

                        surgiesEscort.getSelectionModel().select(a);
                    }
                }
                ObservableList<SurgeriesType> items4 = surgiesType.getItems();
                for (SurgeriesType a : items4) {
                    if (a.getType().equals(sur.getType())) {

                        surgiesType.getSelectionModel().select(a);
                    }
                }
                surgiesAdd.setDisable(true);
                surgiesEdite.setDisable(false);
                surgiesDelete.setDisable(false);
                surgiesNew.setDisable(false);
            }

        });
    }

    void initData(Admission admission) {
        this.admission = admission;
    }

    void setParentController(AdmissionScreenController parentController) {
        this.parentController = parentController;
    }

    private void setAutoNum() throws Exception {
        surgiesId.setText(Surgeries.getAutoNum());
    }

    private void fillTypesCombos() throws Exception {
        surgiesType.setItems(SurgeriesType.getData());
        surgiesType.setConverter(new StringConverter<SurgeriesType>() {
            @Override
            public String toString(SurgeriesType contract) {
                return contract.getType();
            }

            @Override
            public SurgeriesType fromString(String string) {
                return null;
            }
        });
        surgiesType.setCellFactory(cell -> new ListCell<SurgeriesType>() {

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
            protected void updateItem(SurgeriesType person, boolean empty) {
                super.updateItem(person, empty);

                if (!empty && person != null) {
                    lblid.setText("م: " + Integer.toString(person.getId()));
                    lblName.setText("الاسم: " + person.getType());
                    setGraphic(gridPane);
                } else {
                    setGraphic(null);
                }
            }
        });
    }

    private void fillCombos() throws Exception {

        surgiesEscort.setItems(PatientsEscort.getData(admission.getPatient_name()));
        surgiesEscort.setConverter(new StringConverter<PatientsEscort>() {
            @Override
            public String toString(PatientsEscort contract) {
                return contract.getName();
            }

            @Override
            public PatientsEscort fromString(String string) {
                return null;
            }
        });
        surgiesEscort.setCellFactory(cell -> new ListCell<PatientsEscort>() {

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
            protected void updateItem(PatientsEscort person, boolean empty) {
                super.updateItem(person, empty);

                if (!empty && person != null) {
                    lblid.setText("الاسم: " + person.getName());
                    lblName.setText("صلة القرابة: " + person.getRelation());
                    lblQuali.setText("رقم الموبايل: " + person.getTele1());
                    setGraphic(gridPane);
                } else {
                    setGraphic(null);
                }
            }
        });
        surgiesAnesthetization.setItems(Doctors.getData());
        surgiesAnesthetization.setConverter(new StringConverter<Doctors>() {
            @Override
            public String toString(Doctors contract) {
                return contract.getName();
            }

            @Override
            public Doctors fromString(String string) {
                return null;
            }
        });
        surgiesAnesthetization.setCellFactory(cell -> new ListCell<Doctors>() {

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
        surgiesDoctor.setItems(Doctors.getData());
        surgiesDoctor.setConverter(new StringConverter<Doctors>() {
            @Override
            public String toString(Doctors contract) {
                return contract.getName();
            }

            @Override
            public Doctors fromString(String string) {
                return null;
            }
        });
        surgiesDoctor.setCellFactory(cell -> new ListCell<Doctors>() {

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

    }

    private void clear() {
        try {
            setAutoNum();
            surgiesAdd.setDisable(false);
            surgiesEdite.setDisable(true);
            surgiesDelete.setDisable(true);
            surgiesNew.setDisable(true);

            surgiesDate.setValue(null);
            surgiesCost.setText("");
            surgiesDoctor.getSelectionModel().clearSelection();
            surgiesAnesthetization.getSelectionModel().clearSelection();
            surgiesType.getSelectionModel().clearSelection();
            surgiesEscort.getSelectionModel().clearSelection();
            surgiesTable.getSelectionModel().clearSelection();
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    private void updateData() {
        process.setVisible(true);
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
                                        clear();
                                        getData();
                                        parentController.getAdmissionDataToTable();
                                        parentController.setSlectedItem(admission);
                                        parentController.updateParent();
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

                process.setVisible(false);
                super.succeeded();
            }
        };
        service.start();

    }

    private void intialColumns() {
        surgiesTabCost.setCellValueFactory(new PropertyValueFactory<>("cost"));

        surgiesTabEscort.setCellValueFactory(new PropertyValueFactory<>("escort"));

        surgiesTabDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        surgiesTabType.setCellValueFactory(new PropertyValueFactory<>("type"));

        surgiesTabAnesthetization.setCellValueFactory(new PropertyValueFactory<>("anesthetization"));

        surgiesTabDoctor.setCellValueFactory(new PropertyValueFactory<>("doctor"));

        surgiesTabId.setCellValueFactory(new PropertyValueFactory<>("id"));
    }

    private void getData() {
        try {
            surgiesTable.setItems(Surgeries.getData(admission.getId()));
            items = surgiesTable.getItems();
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }
    ObservableList<Surgeries> items;

    @FXML
    private void surgiesNew(ActionEvent event) {
        clear();
    }

    @FXML
    private void surgiesDelete(ActionEvent event) {
        process.setVisible(true);
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
                                        alert.setTitle("Deleting Surgies");
                                        alert.setHeaderText("سيتم حذف العملية");
                                        alert.setContentText("هل انت متاكد؟");

                                        Optional<ButtonType> result = alert.showAndWait();
                                        if (result.get() == ButtonType.OK) {
                                            Surgeries su = new Surgeries();
                                            su.setId(Integer.parseInt(surgiesId.getText()));
                                            su.setAdmissionId(admission.getId());
                                            su.Delete();
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
                updateData();
                process.setVisible(false);
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void surgiesEdite(ActionEvent event) {
        process.setVisible(true);
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
                                        alert.setTitle("Editting Surgies");
                                        alert.setHeaderText("سيتم تعديل العملية");
                                        alert.setContentText("هل انت متاكد؟");

                                        Optional<ButtonType> result = alert.showAndWait();
                                        if (result.get() == ButtonType.OK) {
                                            Surgeries su = new Surgeries();
                                            su.setId(Integer.parseInt(surgiesId.getText()));
                                            su.setAdmissionId(admission.getId());
                                            su.setDoctorId(surgiesDoctor.getSelectionModel().getSelectedItem().getId());
                                            su.setAnesthetizationId(surgiesAnesthetization.getSelectionModel().getSelectedItem().getId());
                                            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                            su.setDate(surgiesDate.getValue().format(format));
                                            su.setSurgeyTypeId(surgiesType.getSelectionModel().getSelectedItem().getId());
                                            su.setCost(surgiesCost.getText());
                                            su.setEscortId(surgiesEscort.getSelectionModel().getSelectedItem().getId());
                                            su.Edite();
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
                updateData();
                process.setVisible(false);
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void surgiesAdd(ActionEvent event) {
        process.setVisible(true);
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

                                        Surgeries su = new Surgeries();
                                        su.setId(Integer.parseInt(surgiesId.getText()));
                                        su.setAdmissionId(admission.getId());
                                        su.setDoctorId(surgiesDoctor.getSelectionModel().getSelectedItem().getId());
                                        su.setAnesthetizationId(surgiesAnesthetization.getSelectionModel().getSelectedItem().getId());
                                        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                        su.setDate(surgiesDate.getValue().format(format));
                                        su.setSurgeyTypeId(surgiesType.getSelectionModel().getSelectedItem().getId());
                                        su.setCost(surgiesCost.getText());
                                        su.setEscortId(surgiesEscort.getSelectionModel().getSelectedItem().getId());
                                        su.Add();

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
                updateData();
                process.setVisible(false);
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void search(KeyEvent event) {
        FilteredList<Surgeries> filteredData = new FilteredList<>(items, p -> true);

        filteredData.setPredicate(pa -> {

            if (search.getText() == null || search.getText().isEmpty()) {
                return true;
            }

            String lowerCaseFilter = search.getText().toLowerCase();

            return (pa.getDoctor().contains(lowerCaseFilter)
                    || pa.getAnesthetization().contains(lowerCaseFilter)
                    || pa.getDate().contains(lowerCaseFilter)
                    || pa.getEscort().contains(lowerCaseFilter));

        });

        SortedList< Surgeries> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(surgiesTable.comparatorProperty());
        surgiesTable.setItems(sortedData);
    }

    @FXML
    private void addSurgiesType(MouseEvent event) {
        TextInputDialog dialog = new TextInputDialog("اضافة نوع عملية");

        dialog.setHeaderText("اضافة نوع عملية");
        dialog.setContentText("ادخل اسم النوع: ");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            try {

                SurgeriesType.Add(result.get());
                fillTypesCombos();
            } catch (Exception ex) {
                AlertDialogs.showErrors(ex);
            }
        }

    }

}
