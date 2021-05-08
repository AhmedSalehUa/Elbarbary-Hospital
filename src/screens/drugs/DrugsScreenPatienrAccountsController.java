package screens.drugs;

import assets.classes.AlertDialogs;
import static assets.classes.statics.NoPermission;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import db.User;
import db.get;
import elbarbary.hospital.ElBarbaryHospital;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javax.imageio.ImageIO;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import screens.drugs.assets.DrugsAccounts;
import screens.drugs.assets.DrugsCategeroy;
import screens.drugs.assets.DrugsMedicines;
import screens.drugs.assets.DrugsPatientExpenses;
import screens.drugs.assets.DrugsPatients;
import screens.drugs.assets.DrugsRoom;
import screens.drugs.assets.DrugsServices;
import screens.mainDataScreen.assets.Doctors;
import screens.mainDataScreen.assets.DoctorsServices;
import screens.mainDataScreen.assets.Patients;
import screens.store.assets.StoreProdcts;
import screens.store.assets.Stores;

public class DrugsScreenPatienrAccountsController
        implements Initializable {

    @FXML
    private JFXTextField search;
    @FXML
    private TableView<DrugsAccounts> accountTable;
    @FXML
    private TableColumn<DrugsAccounts, String> accountTabRemaining;
    @FXML
    private TableColumn<DrugsAccounts, String> accountTabtotalSpended;
    @FXML
    private TableColumn<DrugsAccounts, String> accountTabTotalCollected;
    @FXML
    private TableColumn<DrugsAccounts, String> accountTabPatient;
    @FXML
    private TableColumn<DrugsAccounts, String> accountTabId;
    @FXML
    private AnchorPane dailyExpensees;
    @FXML
    private AnchorPane medicines;
    @FXML
    private AnchorPane services;
    @FXML
    private AnchorPane MoneyIn;
    @FXML
    private AnchorPane MoneyOut;
    @FXML
    private JFXTextField search11;
    @FXML
    private TableView<DrugsMedicines> admissionMedicineTable;
    @FXML
    private TableColumn<DrugsMedicines, String> admissionMedicineTabtotalCost;
    @FXML
    private TableColumn<DrugsMedicines, String> admissionMedicineTabCost;
    @FXML
    private TableColumn<DrugsMedicines, String> admissionMedicineTabAmount;
    @FXML
    private TableColumn<DrugsMedicines, String> admissionMedicineTabMedicine;
    @FXML
    private TableColumn<DrugsMedicines, String> admissionMedicineTabId;
    @FXML
    private Label admissionMedicineId;
    @FXML
    private ComboBox<StoreProdcts> admissionMedicineMedicines;
    @FXML
    private TextField admissionMedicineAmount;
    @FXML
    private ComboBox<Stores> admissionMedicineStore;
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
    private JFXTextField search1;
    @FXML
    private TableView<DrugsServices> serviceTable;
    @FXML
    private TableColumn<DrugsServices, String> serviceTabCost;
    @FXML
    private TableColumn<DrugsServices, String> serviceTabService;
    @FXML
    private TableColumn<DrugsServices, String> serviceTabDoctor;
    @FXML
    private TableColumn<DrugsServices, String> serviceTabId;
    @FXML
    private TableColumn<DrugsServices, String> serviceTabDate;
    @FXML
    private JFXTextField dailySearch;
    @FXML
    private TableView<DrugsPatientExpenses> dailyTable;
    @FXML
    private TableColumn<DrugsPatientExpenses, String> dailyTabNotes;
    @FXML
    private TableColumn<DrugsPatientExpenses, String> dailyTabDate;
    @FXML
    private TableColumn<DrugsPatientExpenses, String> dailyTabCat;
    @FXML
    private TableColumn<DrugsPatientExpenses, String> dailyTabAmount;
    @FXML
    private TableColumn<DrugsPatientExpenses, String> dailyTabId;
    @FXML
    private Label dailyId;
    @FXML
    private TextField dailyNotes;
    @FXML
    private TextField dailyCredit;
    @FXML
    private ComboBox<DrugsCategeroy> dailyCategory;
    @FXML
    private JFXDatePicker dailyDate;
    @FXML
    private ProgressIndicator dailyProgress;
    @FXML
    private Button dailyNew;
    @FXML
    private Button dailyDelete;
    @FXML
    private Button dailyEdite;
    @FXML
    private Button dailyAdd;
    @FXML
    private ProgressIndicator medicineProgress;
    @FXML
    private ProgressIndicator serviceProgress;
    Preferences prefs;
    @FXML
    private ProgressIndicator progress;
    @FXML
    private Button updateRoom;
    @FXML
    private Button addDailyCost;
    @FXML
    private TableColumn<DrugsAccounts, String> accountTabRoom;
    @FXML
    private Button medicineNew;
    @FXML
    private Button medicineDelete;
    @FXML
    private Button medicineEdite;
    @FXML
    private Button medicineAdd;

    ObservableList<DrugsAccounts> accountItems;
    DrugsAccounts selectedAccount;
    ObservableList<DrugsPatientExpenses> dailyItems;
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    ObservableList<DrugsMedicines> medicineItems;
    @FXML
    private JFXDatePicker serviceDate;
    @FXML
    private AnchorPane services1;
    @FXML
    private Label roomId;
    @FXML
    private JFXDatePicker roomDate;
    @FXML
    private TextField roomCost;
    @FXML
    private ProgressIndicator roomProgress;
    @FXML
    private Button roomNew;
    @FXML
    private Button roomDelete;
    @FXML
    private Button roomEdite;
    @FXML
    private Button roomAdd;
    @FXML
    private JFXTextField search12;
    @FXML
    private TableView<DrugsRoom> roomTable;
    @FXML
    private TableColumn<DrugsRoom, String> roomTabCost;
    @FXML
    private TableColumn<DrugsRoom, String> roomTabDate;
    @FXML
    private TableColumn<DrugsRoom, String> roomTabId;
    @FXML
    private AnchorPane MoneyTransactions;
    @FXML
    private Button print;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    protected Void call() throws Exception {
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(new Runnable() {
                            public void run() {
                                try {
                                    prefs = Preferences.userNodeForPackage(ElBarbaryHospital.class);
                                    configPanls();
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

            protected void succeeded() {
                progress.setVisible(false);

                super.succeeded();
            }
        };
        service.start();
        accountTable.setOnMouseClicked(e -> {
            if (accountTable.getSelectionModel().getSelectedIndex() != -1) {
                DrugsAccounts selected = accountTable.getSelectionModel().getSelectedItem();

                selectedAccount = selected;

                configDailyExpense(selected.getPaitent_id());
                configMedicines(selected.getPaitent_id());
                configServices(selected.getPaitent_id());
                configRoom(selected.getPaitent_id());
            }
        });
        dailyTable.setOnMouseClicked(e -> {
            if (dailyTable.getSelectionModel().getSelectedIndex() != -1) {
                DrugsPatientExpenses selected = (DrugsPatientExpenses) dailyTable.getSelectionModel().getSelectedItem();

                dailyId.setText(Integer.toString(selected.getId()));

                dailyCredit.setText(selected.getAmount());
                dailyDate.setValue(LocalDate.parse(selected.getDate()));
                dailyNotes.setText(selected.getNotes());
                ObservableList<DrugsCategeroy> items1 = dailyCategory.getItems();
                for (DrugsCategeroy a : items1) {
                    if (a.getName().equals(selected.getCat_name())) {
                        dailyCategory.getSelectionModel().select(a);
                    }
                }
                dailyNew.setDisable(false);
                dailyDelete.setDisable(false);
                dailyEdite.setDisable(false);
                dailyAdd.setDisable(true);
            }
        });
        admissionMedicineTable.setOnMouseClicked(e -> {
            if (this.admissionMedicineTable.getSelectionModel().getSelectedIndex() != -1) {
                DrugsMedicines selectedItem = admissionMedicineTable.getSelectionModel().getSelectedItem();

                admissionMedicineId.setText(Integer.toString(selectedItem.getId()));

                admissionMedicineAmount.setText(selectedItem.getAmount());

                try {
                    int storeid = Integer.parseInt(get.getTableData("select store_id from stores_medicines where id ='" + selectedItem.getMedicine_id() + "'").getValueAt(0, 0).toString());
                    ObservableList<Stores> items2 = admissionMedicineStore.getItems();
                    for (Stores a : items2) {
                        if (a.getId() == storeid) {
                            admissionMedicineStore.getSelectionModel().select(a);
                            getMedicine(Integer.toString(a.getId()));
                            ObservableList<StoreProdcts> items1 = admissionMedicineMedicines.getItems();
                            for (StoreProdcts b : items1) {
                                if (b.getId() == selectedItem.getMedicine_id()) {
                                    admissionMedicineMedicines.getSelectionModel().select(b);
                                }
                            }
                        }
                    }
                } catch (Exception ex) {
                    AlertDialogs.showErrors(ex);
                }
                medicineAdd.setDisable(true);
                medicineDelete.setDisable(false);
                medicineEdite.setDisable(false);
                medicineNew.setDisable(false);
            }
        });

        serviceTable.setOnMouseClicked((e) -> {
            if (serviceTable.getSelectionModel().getSelectedIndex() == -1) {
            } else {
                serviceNew.setDisable(false);
                serviceDelete.setDisable(false);
                serviceEdite.setDisable(false);
                serviceAdd.setDisable(true);

                DrugsServices selected = serviceTable.getSelectionModel().getSelectedItem();
                serviceId.setText(Integer.toString(selected.getId()));
                serviceDate.setValue(LocalDate.parse(selected.getDate()));
                ObservableList<Doctors> items1 = serviceDoctor.getItems();
                for (Doctors a : items1) {
                    if (a.getName().equals(selected.getDoctor())) {
                        serviceDoctor.getSelectionModel().select(a);
                        getDoctorServices(a.getId());
                        ObservableList<DoctorsServices> items2 = serviceService.getItems();
                        for (DoctorsServices b : items2) {
                            if (b.getName().equals(selected.getService())) {
                                serviceService.getSelectionModel().select(b);
                            }
                        }
                    }
                }
            }
        });
        roomTable.setOnMouseClicked((e) -> {
            if (roomTable.getSelectionModel().getSelectedIndex() == -1) {
            } else {
                roomAdd.setDisable(true);
                roomEdite.setDisable(false);
                roomDelete.setDisable(false);
                roomNew.setDisable(false);

                DrugsRoom selected = roomTable.getSelectionModel().getSelectedItem();
                roomId.setText(Integer.toString(selected.getId()));
                roomDate.setValue(LocalDate.parse(selected.getDate()));
                roomCost.setText(selected.getCost());
            }
        });

    }

    private void configPanls() {
        try {
            MoneyIn.getChildren().clear();
            FXMLLoader a = new FXMLLoader(getClass().getResource("DrugsScreenMoneyIn.fxml"));
            MoneyIn.getChildren().add(a.load());
            DrugsScreenMoneyInController controller = a.getController();
            controller.setParents(DrugsScreenPatienrAccountsController.this);

            MoneyOut.getChildren().clear();
            FXMLLoader b = new FXMLLoader(getClass().getResource("DrugsScreenMoneyOut.fxml"));
            MoneyOut.getChildren().add(b.load());
            DrugsScreenMoneyOutController controllers = b.getController();
            controllers.setParents(DrugsScreenPatienrAccountsController.this);

            FXMLLoader loader = new FXMLLoader(getClass().getResource(NoPermission));
            if (User.canAccess("drugsMoneyTransaction")) {
                loader = new FXMLLoader(getClass().getResource("DrugsScreenMoneyTransactions.fxml"));
                MoneyTransactions.getChildren().clear();
                MoneyTransactions.getChildren().add(loader.load());
                DrugsScreenMoneyTransactionsController tarnsControllers = loader.getController();
                tarnsControllers.setParents(DrugsScreenPatienrAccountsController.this);
            } else {
                MoneyTransactions.getChildren().clear();
                MoneyTransactions.getChildren().add(loader.load());
            }
        } catch (IOException ex) {
            AlertDialogs.showErrors(ex);
        }

    }

    private void intialColumn() {
        accountTabRoom.setCellValueFactory(new PropertyValueFactory("room"));
        accountTabRemaining.setCellValueFactory(new PropertyValueFactory("remaining"));
        accountTabtotalSpended.setCellValueFactory(new PropertyValueFactory("total_spended"));
        accountTabTotalCollected.setCellValueFactory(new PropertyValueFactory("total_paied"));
        accountTabPatient.setCellValueFactory(new PropertyValueFactory("patient_name"));
        accountTabId.setCellValueFactory(new PropertyValueFactory("id"));

        dailyTabNotes.setCellValueFactory(new PropertyValueFactory("notes"));
        dailyTabDate.setCellValueFactory(new PropertyValueFactory("date"));
        dailyTabCat.setCellValueFactory(new PropertyValueFactory("cat_name"));
        dailyTabAmount.setCellValueFactory(new PropertyValueFactory("amount"));
        dailyTabId.setCellValueFactory(new PropertyValueFactory("id"));

        admissionMedicineTabCost.setCellValueFactory(new PropertyValueFactory("cost_of_one"));
        admissionMedicineTabtotalCost.setCellValueFactory(new PropertyValueFactory("total_cost"));
        admissionMedicineTabAmount.setCellValueFactory(new PropertyValueFactory("amount"));
        admissionMedicineTabMedicine.setCellValueFactory(new PropertyValueFactory("medicine"));
        admissionMedicineTabId.setCellValueFactory(new PropertyValueFactory("id"));

        serviceTabCost.setCellValueFactory(new PropertyValueFactory("cost"));
        serviceTabService.setCellValueFactory(new PropertyValueFactory("service"));
        serviceTabDoctor.setCellValueFactory(new PropertyValueFactory("doctor"));
        serviceTabId.setCellValueFactory(new PropertyValueFactory("id"));
        serviceTabDate.setCellValueFactory(new PropertyValueFactory("date"));

        roomTabCost.setCellValueFactory(new PropertyValueFactory("cost"));
        roomTabDate.setCellValueFactory(new PropertyValueFactory("date"));
        roomTabId.setCellValueFactory(new PropertyValueFactory("id"));
    }

    private void getData() {
        try {
            accountTable.setItems(DrugsAccounts.getData());
            accountItems = accountTable.getItems();
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    @FXML
    private void search(KeyEvent event) {
    }

    public void updateParent() {
        getData();
        if (selectedAccount != null) {
            accountTable.getSelectionModel().select(this.selectedAccount);
        }
    }

    private void clearExpense() {
        try {
            dailyNew.setDisable(true);
            dailyDelete.setDisable(true);
            dailyEdite.setDisable(true);
            dailyAdd.setDisable(false);
            dailyId.setText(DrugsPatientExpenses.getAutoNum());
            dailyCredit.setText("");
            dailyDate.setValue(null);
            dailyNotes.setText("");
            dailyCategory.getItems();
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    @FXML
    private void addCategory(MouseEvent event) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Add Category");
        dialog.setHeaderText("اضافة تصنيف");
        dialog.setContentText("اسم التصنيف :");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            try {
                DrugsCategeroy.Add(result.get());
            } catch (Exception ex) {
                AlertDialogs.showErrors(ex);
            }
            fillExpensesCombo();
        }
    }

    @FXML
    private void dailySearch(KeyEvent event) {
        FilteredList<DrugsPatientExpenses> filteredData = new FilteredList(this.dailyItems, p -> true);

        filteredData.setPredicate(pa -> {
            if (this.search.getText() == null || this.search.getText().isEmpty()) {
                return true;
            }

            String lowerCaseFilter = this.search.getText().toLowerCase();

            return (pa.getCat_name().contains(lowerCaseFilter) || pa.getDate().contains(lowerCaseFilter));
        });

        SortedList<DrugsPatientExpenses> sortedData = new SortedList(filteredData);
        sortedData.comparatorProperty().bind((ObservableValue) dailyTable.comparatorProperty());
        this.dailyTable.setItems(sortedData);
    }

    @FXML
    private void dailyNew(ActionEvent event) {
        clearExpense();
    }

    @FXML
    private void dailyDelete(ActionEvent event) {
        this.dailyProgress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    protected Void call() throws Exception {
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(new Runnable() {
                            public void run() {
                                try {
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Deleting  Expenses");
                                    alert.setHeaderText("سيتم حذف المصروف ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        DrugsPatientExpenses ex = new DrugsPatientExpenses();
                                        ex.setId(Integer.parseInt(dailyId.getText()));

                                        ex.setPatient_id(selectedAccount.getPaitent_id());
                                        ex.setAcc_id(selectedAccount.getId());
                                        ex.setCat_id(dailyCategory.getSelectionModel().getSelectedItem().getId());
                                        ex.setNotes(dailyNotes.getText());
                                        ex.setDate(dailyDate.getValue().format(format));
                                        ex.setAmount(dailyCredit.getText());
                                        ex.Delete();
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

            protected void succeeded() {
                dailyProgress.setVisible(false);
                clearExpense();
                configDailyExpense(selectedAccount.getPaitent_id());
                updateParent();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void dailyEdite(ActionEvent event) {
        this.dailyProgress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    protected Void call() throws Exception {
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(new Runnable() {
                            public void run() {
                                try {
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Editing  Expenses");
                                    alert.setHeaderText("سيتم تعديل المصروف ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        DrugsPatientExpenses ex = new DrugsPatientExpenses();
                                        ex.setId(Integer.parseInt(dailyId.getText()));

                                        ex.setPatient_id(selectedAccount.getPaitent_id());
                                        ex.setAcc_id(selectedAccount.getId());
                                        ex.setCat_id(dailyCategory.getSelectionModel().getSelectedItem().getId());
                                        ex.setNotes(dailyNotes.getText());
                                        ex.setDate(dailyDate.getValue().format(format));
                                        ex.setAmount(dailyCredit.getText());
                                        ex.Edite();
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

            protected void succeeded() {
                dailyProgress.setVisible(false);
                clearExpense();
                configDailyExpense(selectedAccount.getPaitent_id());
                updateParent();
                super.succeeded();
            }
        };

        service.start();
    }

    @FXML
    private void dailyAdd(ActionEvent event) {
        this.dailyProgress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    protected Void call() throws Exception {
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(new Runnable() {
                            public void run() {
                                try {
                                    DrugsPatientExpenses ex = new DrugsPatientExpenses();
                                    ex.setId(Integer.parseInt(dailyId.getText()));
                                    ex.setPatient_id(selectedAccount.getPaitent_id());
                                    ex.setAcc_id(selectedAccount.getId());
                                    ex.setCat_id(dailyCategory.getSelectionModel().getSelectedItem().getId());
                                    ex.setNotes(dailyNotes.getText());
                                    ex.setDate(dailyDate.getValue().format(format));
                                    ex.setAmount(dailyCredit.getText());
                                    ex.Add();
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

            protected void succeeded() {
                dailyProgress.setVisible(false);
                clearExpense();
                configDailyExpense(selectedAccount.getPaitent_id());
                updateParent();
                super.succeeded();
            }
        };
        service.start();
    }

    private void configDailyExpense(int paitent_id) {
        try {
            fillExpensesCombo();
            dailyTable.setItems(DrugsPatientExpenses.getData(paitent_id));
            dailyItems = dailyTable.getItems();
            dailyProgress.setVisible(false);
            clearExpense();
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    private void fillExpensesCombo() {
        try {
            this.dailyCategory.setItems(DrugsCategeroy.getData());
            this.dailyCategory.setConverter(new StringConverter<DrugsCategeroy>() {
                public String toString(DrugsCategeroy patient) {
                    return patient.getName();
                }

                public DrugsCategeroy fromString(String string) {
                    return null;
                }
            });
            this.dailyCategory.setCellFactory(cell -> new ListCell<DrugsCategeroy>() {
                GridPane gridPane = new GridPane();
                Label lblid = new Label();
                Label lblName = new Label();

                // Static block to configure our layout
                {
                    // Ensure all our column widths are constant
                    gridPane.getColumnConstraints().addAll(
                            new ColumnConstraints(50, 50, 50),
                            new ColumnConstraints(150, 150, 150)
                    );

                    gridPane.add(lblid, 0, 1);
                    gridPane.add(lblName, 1, 1);

                }

                protected void updateItem(DrugsCategeroy person, boolean empty) {
                    super.updateItem(person, empty);

                    if (!empty && person != null) {

                        this.lblid.setText("م: " + Integer.toString(person.getId()));
                        this.lblName.setText("الاسم: " + person.getName());
                        setGraphic((Node) this.gridPane);
                    } else {
                        setGraphic(null);
                    }
                }
            });
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    private void getMedicine(String storeId) throws Exception {
        this.admissionMedicineMedicines.setItems(StoreProdcts.getDataForSell(storeId));
        this.admissionMedicineMedicines.setConverter(new StringConverter<StoreProdcts>() {
            public String toString(StoreProdcts patient) {
                return patient.getProduct();
            }

            public StoreProdcts fromString(String string) {
                return null;
            }
        });
        this.admissionMedicineMedicines.setCellFactory(cell -> new ListCell<StoreProdcts>() {
            GridPane gridPane = new GridPane();
            Label lblid = new Label();
            Label lblName = new Label();
            Label lblCost = new Label();

            // Static block to configure our layout
            {
                // Ensure all our column widths are constant
                gridPane.getColumnConstraints().addAll(
                        new ColumnConstraints(50, 50, 50), new ColumnConstraints(150, 150, 150),
                        new ColumnConstraints(100, 100, 100)
                );

                gridPane.add(lblid, 0, 1);
                gridPane.add(lblName, 1, 1);
                gridPane.add(lblCost, 2, 1);

            }

            protected void updateItem(StoreProdcts person, boolean empty) {
                super.updateItem(person, empty);

                if (!empty && person != null) {

                    this.lblid.setText("الكميةالمتاحة: " + person.getAmount());
                    this.lblName.setText("الاسم: " + person.getProduct());
                    this.lblCost.setText("سعر البيع: " + person.getCostOfSell());

                    setGraphic((Node) this.gridPane);
                } else {

                    setGraphic(null);
                }
            }
        });
    }

    @FXML
    private void getMedicine(ActionEvent event) {
        if (this.admissionMedicineStore.getSelectionModel().getSelectedIndex() != -1) {

            try {
                getMedicine(Integer.toString(admissionMedicineStore.getSelectionModel().getSelectedItem().getId()));
            } catch (Exception ex) {
                AlertDialogs.showErrors(ex);
            }
        }
    }

    @FXML
    private void medicineNew(ActionEvent event) {
        clearMedicine();
    }

    @FXML
    private void medicineDelete(ActionEvent event) {
        this.medicineProgress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    protected Void call() throws Exception {
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(new Runnable() {
                            public void run() {
                                try {
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Deleting  Medicine");
                                    alert.setHeaderText("سيتم حذف الدواء ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        DrugsMedicines dg = new DrugsMedicines();
                                        dg.setId(Integer.parseInt(admissionMedicineId.getText()));
                                        StoreProdcts selectedItem = admissionMedicineMedicines.getSelectionModel().getSelectedItem();
                                        dg.setMedicine_id(selectedItem.getId());
                                        dg.setAmount(admissionMedicineAmount.getText());
                                        dg.setCost_of_one(selectedItem.getCostOfSell());
                                        dg.setTotal_cost(Double.toString(Double.parseDouble(admissionMedicineAmount.getText()) * Double.parseDouble(selectedItem.getCostOfSell())));
                                        dg.setPatient_id(selectedAccount.getPaitent_id());
                                        dg.setAcc_id(Integer.parseInt(prefs.get("DRUGS_ACCOUNT_ID", "2")));
                                        dg.Delete();
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

            protected void succeeded() {
                medicineProgress.setVisible(false);
                configMedicines(selectedAccount.getPaitent_id());
                updateParent();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void medicineEdite(ActionEvent event) {
        medicineProgress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    protected Void call() throws Exception {
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(new Runnable() {
                            public void run() {
                                try {
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Editing  Medicine");
                                    alert.setHeaderText("سيتم تعديل الدواء ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        StoreProdcts selectedItem = admissionMedicineMedicines.getSelectionModel().getSelectedItem();
                                        if (Double.parseDouble(selectedItem.getAmount()) < Double.parseDouble(admissionMedicineAmount.getText())) {
                                            AlertDialogs.showError("الكمية المتاحة لا تكفي لصرف الكمية المطلوبة");
                                        } else {
                                            DrugsMedicines dg = new DrugsMedicines();
                                            dg.setId(Integer.parseInt(admissionMedicineId.getText()));
                                            dg.setMedicine_id(selectedItem.getId());
                                            dg.setAmount(admissionMedicineAmount.getText());
                                            dg.setCost_of_one(selectedItem.getCostOfSell());
                                            dg.setTotal_cost(Double.toString(Double.parseDouble(admissionMedicineAmount.getText()) * Double.parseDouble(selectedItem.getCostOfSell())));
                                            dg.setAcc_id(Integer.parseInt(prefs.get("DRUGS_ACCOUNT_ID", "2")));
                                            dg.setPatient_id(selectedAccount.getPaitent_id());
                                            dg.Edite();
                                        }
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

            protected void succeeded() {
                medicineProgress.setVisible(false);
                configMedicines(selectedAccount.getPaitent_id());
                updateParent();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void medicineAdd(ActionEvent event) {
        if (admissionMedicineMedicines.getSelectionModel().getSelectedIndex() == -1 || admissionMedicineAmount.getText() == null) {
            AlertDialogs.showError("يوجد بيانات ناقصه من الدواء والكمية");
        } else {
            medicineProgress.setVisible(true);
            Service<Void> service = new Service<Void>() {
                protected Task<Void> createTask() {
                    return new Task<Void>() {
                        protected Void call() throws Exception {
                            final CountDownLatch latch = new CountDownLatch(1);
                            Platform.runLater(new Runnable() {
                                public void run() {
                                    try {
                                        StoreProdcts selectedItem = admissionMedicineMedicines.getSelectionModel().getSelectedItem();
                                        if (Double.parseDouble(selectedItem.getAmount()) < Double.parseDouble(admissionMedicineAmount.getText())) {
                                            AlertDialogs.showError("الكمية المتاحة لا تكفي لصرف الكمية المطلوبة");
                                        } else {
                                            DrugsMedicines dg = new DrugsMedicines();
                                            dg.setId(Integer.parseInt(admissionMedicineId.getText()));
                                            dg.setMedicine_id(selectedItem.getId());
                                            dg.setAmount(admissionMedicineAmount.getText());
                                            dg.setCost_of_one(selectedItem.getCostOfSell());
                                            dg.setTotal_cost(Double.toString(Double.parseDouble(admissionMedicineAmount.getText()) * Double.parseDouble(selectedItem.getCostOfSell())));
                                            dg.setAcc_id(Integer.parseInt(prefs.get("DRUGS_ACCOUNT_ID", "2")));
                                            dg.setPatient_id(selectedAccount.getPaitent_id());
                                            dg.Add();
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

                protected void succeeded() {
                    medicineProgress.setVisible(false);
                    configMedicines(selectedAccount.getPaitent_id());
                    updateParent();
                    super.succeeded();
                }
            };
            service.start();
        }
    }

    private void configMedicines(int paitent_id) {
        try {
            admissionMedicineTable.setItems(DrugsMedicines.getData(paitent_id));
            medicineItems = admissionMedicineTable.getItems();
            fillMedicineCombos();
            clearMedicine();
            medicineProgress.setVisible(false);
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    private void fillMedicineCombos() throws Exception {
        admissionMedicineStore.setItems(Stores.getData());
        admissionMedicineStore.setConverter(new StringConverter<Stores>() {
            public String toString(Stores patient) {
                return patient.getName();
            }

            public Stores fromString(String string) {
                return null;
            }
        });
        admissionMedicineStore.setCellFactory(cell -> new ListCell<Stores>() {
            GridPane gridPane = new GridPane();
            Label lblid = new Label();
            Label lblName = new Label();

            // Static block to configure our layout
            {
                // Ensure all our column widths are constant
                gridPane.getColumnConstraints().addAll(
                        new ColumnConstraints(100, 100, 100),
                        new ColumnConstraints(100, 100, 100));

                gridPane.add(lblid, 0, 1);
                gridPane.add(lblName, 1, 1);

            }

            protected void updateItem(Stores person, boolean empty) {
                super.updateItem(person, empty);

                if (!empty && person != null) {

                    lblid.setText("اسم المخزن: " + person.getName());
                    lblName.setText("النوع: " + person.getType());

                    setGraphic((Node) gridPane);
                } else {

                    setGraphic(null);
                }
            }
        });
    }

    private void clearMedicine() {
        try {
            admissionMedicineId.setText(DrugsMedicines.getAutoNum());
            admissionMedicineAmount.setText("");
            admissionMedicineMedicines.getSelectionModel().clearSelection();
            medicineAdd.setDisable(false);
            medicineDelete.setDisable(true);
            medicineEdite.setDisable(true);
            medicineNew.setDisable(true);
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    private void configServices(int patient) {
        try {
            clearServices();
            serviceTable.setItems(DrugsServices.getData(patient));
            servicesItems = serviceTable.getItems();
            fillServicesCombo();
            serviceProgress.setVisible(false);
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }
    ObservableList<DrugsServices> servicesItems;

    private void clearServices() {
        try {
            serviceId.setText(DrugsServices.getAutoNum());
            serviceNew.setDisable(true);
            serviceDelete.setDisable(true);
            serviceEdite.setDisable(true);
            serviceDoctor.getSelectionModel().clearSelection();
            serviceService.getSelectionModel().clearSelection();
            serviceAdd.setDisable(false);
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    private void fillServicesCombo() {
        try {
            serviceDoctor.setItems(Doctors.getData());
            serviceDoctor.setConverter(new StringConverter<Doctors>() {
                @Override
                public String toString(Doctors patient) {
                    return patient.getName();
                }

                @Override
                public Doctors fromString(String string) {
                    return null;
                }
            });
            serviceDoctor.setCellFactory(cell -> new ListCell<Doctors>() {

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
                protected void updateItem(Doctors person, boolean empty) {
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
    }

    private void getDoctorServices(int id) {
        try {
            serviceService.setItems(DoctorsServices.getData(Integer.toString(id)));
            serviceService.setConverter(new StringConverter<DoctorsServices>() {
                @Override
                public String toString(DoctorsServices patient) {
                    return patient.getName();
                }

                @Override
                public DoctorsServices fromString(String string) {
                    return null;
                }
            });
            serviceService.setCellFactory(cell -> new ListCell<DoctorsServices>() {

                // Create our layout here to be reused for each ListCell
                GridPane gridPane = new GridPane();
                Label lblid = new Label();
                Label lblName = new Label();
                Label lblCost = new Label();

                // Static block to configure our layout
                {
                    // Ensure all our column widths are constant
                    gridPane.getColumnConstraints().addAll(
                            new ColumnConstraints(50, 50, 50), new ColumnConstraints(150, 150, 150),
                            new ColumnConstraints(100, 100, 100)
                    );

                    gridPane.add(lblid, 0, 1);
                    gridPane.add(lblName, 1, 1);
                    gridPane.add(lblCost, 2, 1);

                }

                // We override the updateItem() method in order to provide our own layout for this Cell's graphicProperty
                @Override
                protected void updateItem(DoctorsServices person, boolean empty) {
                    super.updateItem(person, empty);

                    if (!empty && person != null) {

                        // Update our Labels
                        lblid.setText("م: " + Integer.toString(person.getId()));
                        lblName.setText("الاسم: " + person.getName());
                        lblCost.setText("التكلفة: " + person.getCost());

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
    }

    @FXML
    private void getDoctorServices(ActionEvent event) {
        if (serviceDoctor.getSelectionModel().getSelectedIndex() == -1) {
        } else {
            getDoctorServices(serviceDoctor.getSelectionModel().getSelectedItem().getId());
        }
    }

    @FXML
    private void serviceNew(ActionEvent event) {
        clearServices();
    }

    @FXML
    private void serviceDelete(ActionEvent event) {
        roomProgress.setVisible(true);
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
                                    alert.setTitle("Deleting  Service");
                                    alert.setHeaderText("سيتم حذف الخدمة ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        DrugsServices ds = new DrugsServices();
                                        ds.setId(Integer.parseInt(serviceId.getText()));
                                        ds.setAcc_id(Integer.parseInt(prefs.get("DRUGS_ACCOUNT_ID", "2")));
                                        ds.setPatient_id(selectedAccount.getPaitent_id());
                                        ds.setDoctor_id(serviceDoctor.getSelectionModel().getSelectedItem().getId());
                                        ds.setService_id(serviceService.getSelectionModel().getSelectedItem().getId());
                                        ds.setCost(serviceService.getSelectionModel().getSelectedItem().getCost());
                                        ds.setDate(serviceDate.getValue().format(format));
                                        ds.Delete();
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
                roomProgress.setVisible(false);
                configServices(selectedAccount.getPaitent_id());
                updateParent();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void serviceEdite(ActionEvent event) {
        roomProgress.setVisible(true);
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
                                    alert.setTitle("Editing  Service");
                                    alert.setHeaderText("سيتم تعديل الخدمة ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        DrugsServices ds = new DrugsServices();
                                        ds.setId(Integer.parseInt(serviceId.getText()));
                                        ds.setAcc_id(Integer.parseInt(prefs.get("DRUGS_ACCOUNT_ID", "2")));
                                        ds.setPatient_id(selectedAccount.getPaitent_id());
                                        ds.setDoctor_id(serviceDoctor.getSelectionModel().getSelectedItem().getId());
                                        ds.setService_id(serviceService.getSelectionModel().getSelectedItem().getId());
                                        ds.setCost(serviceService.getSelectionModel().getSelectedItem().getCost());
                                        ds.setDate(serviceDate.getValue().format(format));
                                        ds.Edite();
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
                roomProgress.setVisible(false);
                configServices(selectedAccount.getPaitent_id());
                updateParent();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void serviceAdd(ActionEvent event) {

        roomProgress.setVisible(true);
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
                                    DrugsServices ds = new DrugsServices();
                                    ds.setId(Integer.parseInt(serviceId.getText()));
                                    ds.setAcc_id(Integer.parseInt(prefs.get("DRUGS_ACCOUNT_ID", "2")));
                                    ds.setPatient_id(selectedAccount.getPaitent_id());
                                    ds.setDoctor_id(serviceDoctor.getSelectionModel().getSelectedItem().getId());
                                    ds.setService_id(serviceService.getSelectionModel().getSelectedItem().getId());
                                    ds.setCost(serviceService.getSelectionModel().getSelectedItem().getCost());
                                    ds.setDate(serviceDate.getValue().format(format));
                                    ds.Add();
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
                roomProgress.setVisible(false);
                configServices(selectedAccount.getPaitent_id());
                updateParent();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void updateRoom(ActionEvent event) {
        if (this.accountTable.getSelectionModel().getSelectedIndex() == -1) {
            AlertDialogs.showError("ختار لحساب اولا");
        } else {
            TextInputDialog dialog = new TextInputDialog("0");
            dialog.setTitle("Add Room Cost");
            dialog.setHeaderText("من فضلك ادخل سعر الغرفة");
            dialog.setContentText("المبلغ");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                int id = accountTable.getSelectionModel().getSelectedItem().getId();
                try {
                    get.getReportCon().createStatement().execute("UPDATE `drg_accounts` SET `daily_room`='" + result.get() + "' WHERE `id`='" + id + "'");
                } catch (Exception ex) {
                    AlertDialogs.showErrors(ex);
                }
                updateParent();
            }
        }
    }

    @FXML
    private void addDailyCost(ActionEvent event) {
    }

    private void configRoom(int patient) {
        try {
            clearRoom();
            roomTable.setItems(DrugsRoom.getData(patient));
            roomItems = roomTable.getItems();
            roomProgress.setVisible(false);
            roomCost.setEditable(false);
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }

    }
    ObservableList<DrugsRoom> roomItems;

    private void clearRoom() {
        try {
            roomId.setText(DrugsRoom.getAutoNum());
            roomDate.setValue(null);
            roomCost.setText(selectedAccount.getRoom());
            roomAdd.setDisable(false);
            roomEdite.setDisable(true);
            roomDelete.setDisable(true);
            roomNew.setDisable(true);
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    @FXML
    private void roomNew(ActionEvent event) {
        clearRoom();
    }

    @FXML
    private void roomDelete(ActionEvent event) {
        roomProgress.setVisible(true);
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
                                    alert.setTitle("Deleting  Room");
                                    alert.setHeaderText("سيتم حذف الاقامة ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        DrugsRoom r = new DrugsRoom();
                                        r.setId(Integer.parseInt(roomId.getText()));
                                        r.setPatient_id(selectedAccount.getPaitent_id());
                                        r.setCost(selectedAccount.getRoom());
                                        r.setDate(roomDate.getValue().format(format));
                                        r.Delete();
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
                roomProgress.setVisible(false);
                configRoom(selectedAccount.getPaitent_id());
                updateParent();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void roomEdite(ActionEvent event) {
        roomProgress.setVisible(true);
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
                                    alert.setTitle("Editing  Room");
                                    alert.setHeaderText("سيتم تعديل الاقامة ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        DrugsRoom r = new DrugsRoom();
                                        r.setId(Integer.parseInt(roomId.getText()));
                                        r.setPatient_id(selectedAccount.getPaitent_id());
                                        r.setCost(selectedAccount.getRoom());
                                        r.setDate(roomDate.getValue().format(format));
                                        r.Edite();
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
                roomProgress.setVisible(false);
                configRoom(selectedAccount.getPaitent_id());
                updateParent();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void roomAdd(ActionEvent event) {
        roomProgress.setVisible(true);
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
                                    DrugsRoom r = new DrugsRoom();
                                    r.setId(Integer.parseInt(roomId.getText()));
                                    r.setPatient_id(selectedAccount.getPaitent_id());
                                    r.setCost(selectedAccount.getRoom());
                                    r.setDate(roomDate.getValue().format(format));
                                    r.Add();
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
                roomProgress.setVisible(false);
                configRoom(selectedAccount.getPaitent_id());
                updateParent();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void print(ActionEvent event) {
        if (accountTable.getSelectionModel().getSelectedIndex() == -1) {
            AlertDialogs.showError("اختار الحساب اولا");
        } else {
            Service<Void> service = new Service<Void>() {
                @Override
                protected Task<Void> createTask() {
                    return new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            try {
                                DrugsAccounts acc = accountTable.getSelectionModel().getSelectedItem();
                                DrugsPatients pa = DrugsPatients.getData(acc.getPaitent_id()).get(0);
                                HashMap hash = new HashMap();
                                BufferedImage image = ImageIO.read(getClass().getResource("/assets/icons/logo.png"));
                                hash.put("logo", image);
                                hash.put("patient_id", Integer.toString(acc.getPaitent_id()));
                                hash.put("name", pa.getName());
                                hash.put("government", pa.getGovernment());
                                hash.put("adress", pa.getAddress());
                                hash.put("nationalId", pa.getNational_id());
                                hash.put("trans", pa.getTranportOrg());
                                hash.put("disasea", pa.getDiagnosis());
                                hash.put("doctor", pa.getDoctor_name());
                                hash.put("date_of_birth", pa.getDateOfBirth());
                                hash.put("gender", pa.getGender());
                                hash.put("age", pa.getAge());
                                hash.put("tele1", pa.getTele1());
                                hash.put("tele2", pa.getTele2());

                                InputStream suprepo = getClass().getResourceAsStream("/screens/drugs/reports/CollectedMoney.jasper");
                                JasperReport subJasperReport = (JasperReport) JRLoader.loadObject(suprepo);
                                hash.put("collectedMoney", subJasperReport);

                                InputStream clincRep = getClass().getResourceAsStream("/screens/drugs/reports/ExportedMoney.jasper");
                                JasperReport clincRepsubJasperReport = (JasperReport) JRLoader.loadObject(clincRep);
                                hash.put("ExportedMoney", clincRepsubJasperReport);

                                InputStream contractRep = getClass().getResourceAsStream("/screens/drugs/reports/DailyExpenses.jasper");
                                JasperReport contractRepsubJasperReport = (JasperReport) JRLoader.loadObject(contractRep);
                                hash.put("dailyExpense", contractRepsubJasperReport);

                                InputStream medicineRep = getClass().getResourceAsStream("/screens/drugs/reports/DailyMedicine.jasper");
                                JasperReport medicineRepsubJasperReport = (JasperReport) JRLoader.loadObject(medicineRep);
                                hash.put("dailyMedicine", medicineRepsubJasperReport);

                                InputStream serviceRep = getClass().getResourceAsStream("/screens/drugs/reports/DailyRoom.jasper");
                                JasperReport serviceRepsubJasperReport = (JasperReport) JRLoader.loadObject(serviceRep);
                                hash.put("dailyRoom", serviceRepsubJasperReport);

                                InputStream surmedRep = getClass().getResourceAsStream("/screens/drugs/reports/Services.jasper");
                                JasperReport surmedRepsubJasperReport = (JasperReport) JRLoader.loadObject(surmedRep);
                                hash.put("services", surmedRepsubJasperReport);

                                 
                                InputStream a = getClass().getResourceAsStream("/screens/drugs/reports/PatientFile.jrxml");
                                JasperDesign design = JRXmlLoader.load(a);
                                JasperReport jasperreport = JasperCompileManager.compileReport(design);
                                JasperPrint jasperprint = JasperFillManager.fillReport(jasperreport, hash, db.get.getReportCon());
                                JasperViewer.viewReport(jasperprint, false);

                            } catch (Exception ex) {
                                AlertDialogs.showErrors(ex);
                            } finally {

                            }
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

}
