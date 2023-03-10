/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.drugs;

import assets.classes.AlertDialogs;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.StringConverter;
import javax.imageio.ImageIO;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.view.JasperViewer;
import screens.drugs.assets.DrugsAccounts;
import screens.drugs.assets.DrugsMedicines;
import screens.drugs.assets.DrugsMoneyIn;
import screens.drugs.assets.DrugsMoneyOut;
import screens.drugs.assets.DrugsPatientExpenses;
import screens.drugs.assets.DrugsPatients;
import screens.drugs.assets.DrugsRoom;
import screens.drugs.assets.DrugsServices;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class DrugsScreenShowAccController implements Initializable {

    @FXML
    private Label total;
    @FXML
    private ComboBox<DrugsPatients> patient;
    @FXML
    private ProgressIndicator progress;
    @FXML
    private ScrollPane viewReport;
    @FXML
    private Label remains;
    @FXML
    private Label paied;
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
    private TableView<DrugsRoom> roomTable;
    @FXML
    private TableColumn<DrugsRoom, String> roomTabCost;
    @FXML
    private TableColumn<DrugsRoom, String> roomTabDate;
    @FXML
    private TableColumn<DrugsRoom, String> roomTabId;
    @FXML
    private TableView<DrugsMoneyIn> tableIn;
    @FXML
    private TableColumn<DrugsMoneyIn, String> tabInDate;
    @FXML
    private TableColumn<DrugsMoneyIn, String> tabInAmount;
    @FXML
    private TableColumn<DrugsMoneyIn, String> tabInEscort;
    @FXML
    private TableColumn<DrugsMoneyIn, String> tabInId;
    @FXML
    private TableView<DrugsMoneyOut> tableOut;
    @FXML
    private TableColumn<DrugsMoneyOut, String> tabOutDate;
    @FXML
    private TableColumn<DrugsMoneyOut, String> tabOutAmount;
    @FXML
    private TableColumn<DrugsMoneyOut, String> tabOutEscort;
    @FXML
    private TableColumn<DrugsMoneyOut, String> tabOutId;

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
                                    clear();
                                    fillCombo();
                                    intialColumn();
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
    }

    private void intialColumn() {
        tabOutDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tabOutAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        tabOutEscort.setCellValueFactory(new PropertyValueFactory<>("escort_name"));
        tabOutId.setCellValueFactory(new PropertyValueFactory<>("id"));

        tabInDate.setCellValueFactory(new PropertyValueFactory("date"));
        tabInAmount.setCellValueFactory(new PropertyValueFactory("amount"));
        tabInEscort.setCellValueFactory(new PropertyValueFactory("escort_name"));
        tabInId.setCellValueFactory(new PropertyValueFactory("id"));

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

    private void clear() {
        total.setText("0");
        paied.setText("0");
        remains.setText("0");

        roomTable.setItems(null);
        serviceTable.setItems(null);
        admissionMedicineTable.setItems(null);
        dailyTable.setItems(null);
        tableIn.setItems(null);
        tableOut.setItems(null);

    }

    private void fillCombo() {

        Service<Void> service = new Service<Void>() {
            ObservableList<DrugsPatients> clientData;
            ObservableList<DrugsPatients> clientDataSearch;

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {

                            clientData = DrugsPatients.getData();
                        } catch (Exception ex) {
                            AlertDialogs.showErrors(ex);
                        }
                        return null;
                    }
                };

            }

            @Override
            protected void succeeded() {
                progress.setVisible(false);
                patient.setItems(clientData);
                patient.setEditable(true);
                patient.setOnKeyReleased((event) -> {

                    if (patient.getEditor().getText().length() == 0) {
                        patient.setItems(clientData);
                    } else {
                        clientDataSearch = FXCollections.observableArrayList();

                        for (DrugsPatients a : clientData) {
                            if (a.getName().contains(patient.getEditor().getText())) {
                                clientDataSearch.add(a);
                            }
                        }
                        patient.setItems(clientDataSearch);
                        for (DrugsPatients drugsPatients : clientDataSearch) {
                            System.out.println(drugsPatients.getId());
                        }
                        patient.show();
                    }
                });
                patient.setConverter(new StringConverter<DrugsPatients>() {
                    @Override
                    public String toString(DrugsPatients object) {
                        return object != null ? object.getName() : "";
                    }

                    @Override
                    public DrugsPatients fromString(String string) {
                        return patient.getItems().stream().filter(object
                                -> object.getName().equals(string)).findFirst().orElse(null);
                    }
                });
                patient.setCellFactory(cell -> new ListCell<DrugsPatients>() {

                    GridPane gridPane = new GridPane();
                    Label lblid = new Label();
                    Label lblName = new Label();

                    {
                        gridPane.getColumnConstraints().addAll(
                                new ColumnConstraints(50, 50, 50),
                                new ColumnConstraints(200, 200, 200)
                        );

                        gridPane.add(lblid, 0, 1);
                        gridPane.add(lblName, 1, 1);
                    }

                    @Override
                    protected void updateItem(DrugsPatients person, boolean empty) {
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

    @FXML
    private void show(ActionEvent event) {
        if (patient.getSelectionModel().getSelectedIndex() == -1) {
            AlertDialogs.showError("اختار المريض اولا");
        } else {
            try {
                clear();
                configDailyExpense(patient.getSelectionModel().getSelectedItem().getId());
                configMedicines(patient.getSelectionModel().getSelectedItem().getId());
                configServices(patient.getSelectionModel().getSelectedItem().getId());
                configRoom(patient.getSelectionModel().getSelectedItem().getId());
                configMoneyOut(patient.getSelectionModel().getSelectedItem().getId());
                configMoneyIn(patient.getSelectionModel().getSelectedItem().getId());

            } catch (Exception ex) {
                AlertDialogs.showErrors(ex);
            }
        }
    }

    private void configMoneyIn(int patien) {
        try {
            tableIn.setItems(DrugsMoneyIn.getData(patien));
            DrugsAccounts data = DrugsAccounts.getData(patien).get(0);
            total.setText(data.getTotal_spended());

            paied.setText(data.getTotal_paied());

            remains.setText(data.getRemaining());
            ObservableList<DrugsMoneyIn> items = tableIn.getItems();
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    private void configMoneyOut(int patient) {
        try {
            tableOut.setItems(DrugsMoneyOut.getData(patient));
            ObservableList<DrugsMoneyOut> items = tableOut.getItems();
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);

        }
    }

    private void configDailyExpense(int paitent_id) {
        try {
            dailyTable.setItems(DrugsPatientExpenses.getData(paitent_id));
            ObservableList<DrugsPatientExpenses> items = dailyTable.getItems();
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    private void configMedicines(int paitent_id) {
        try {
            admissionMedicineTable.setItems(DrugsMedicines.getData(paitent_id));
            ObservableList<DrugsMedicines> items = admissionMedicineTable.getItems();
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    private void configServices(int patient) {
        try {
            serviceTable.setItems(DrugsServices.getData(patient));
            ObservableList<DrugsServices> items = serviceTable.getItems();
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    private void configRoom(int patient) {
        try {
            roomTable.setItems(DrugsRoom.getData(patient));
            ObservableList<DrugsRoom> items = roomTable.getItems();
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }

    }

    @FXML
    private void print() {
        if (patient.getSelectionModel().getSelectedIndex() == -1) {
            AlertDialogs.showError("اختار المريض اولا");
        } else {
            Service<Void> service = new Service<Void>() {
                @Override
                protected Task<Void> createTask() {
                    return new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            try {
                                DrugsAccounts acc = DrugsAccounts.getData(patient.getSelectionModel().getSelectedItem().getId()).get(0);
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
                                JRViewer vw = new JRViewer(jasperprint);
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
