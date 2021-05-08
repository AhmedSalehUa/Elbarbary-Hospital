/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.accounts;

import assets.classes.AlertDialogs;
import com.jfoenix.controls.JFXDatePicker;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
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
import screens.mainDataScreen.assets.Contract;
import screens.mainDataScreen.assets.Patients;
import screens.store.assets.Company;

public class AccountsScreenReportsController implements Initializable {

    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @FXML
    private JFXDatePicker accFrom;
    @FXML
    private JFXDatePicker accTo;
    @FXML
    private Button accShow;
    @FXML
    private ProgressIndicator accProgress;
    @FXML
    private RadioButton yieldsAndExpenses;
    @FXML
    private RadioButton expenses;
    @FXML
    private RadioButton yields;
    @FXML
    private JFXDatePicker contractFrom;
    @FXML
    private Button printContract;
    @FXML
    private ProgressIndicator contractProgress;
    @FXML
    private ComboBox<Contract> contract;
    @FXML
    private JFXDatePicker contractTo;
    @FXML
    private JFXDatePicker companyFrom;
    @FXML
    private Button printCompany;
    @FXML
    private ProgressIndicator companyProgress;
    @FXML
    private ComboBox<Company> company;
    @FXML
    private JFXDatePicker companyTo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

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
                                    ToggleGroup group = new ToggleGroup();
                                    yields.setToggleGroup(group);
                                    yields.setSelected(true);
                                    expenses.setToggleGroup(group);
                                    yieldsAndExpenses.setToggleGroup(group);
                                    accProgress.setVisible(false);
                                    contractProgress.setVisible(false);
                                    companyProgress.setVisible(false);
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
                super.succeeded();
            }
        };
        service.start();
    }

    private void fillCombo() throws Exception {
        company.setItems(Company.getData());
        company.setConverter(new StringConverter<Company>() {
            @Override
            public String toString(Company patient) {
                return patient.getName();
            }

            @Override
            public Company fromString(String string) {
                return null;
            }
        });
        company.setCellFactory(cell -> new ListCell<Company>() {

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
            protected void updateItem(Company person, boolean empty) {
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
        contract.setItems(Contract.getData());
        contract.setConverter(new StringConverter<Contract>() {
            @Override
            public String toString(Contract patient) {
                return patient.getName();
            }

            @Override
            public Contract fromString(String string) {
                return null;
            }
        });
        contract.setCellFactory(cell -> new ListCell<Contract>() {

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
            protected void updateItem(Contract person, boolean empty) {
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
    private void accShow(ActionEvent event) {
        if (accFrom.getValue() == null || accTo.getValue() == null) {
            AlertDialogs.showError("حدد الفترة اولا");
        } else {
            accProgress.setVisible(true);
            Service<Void> service = new Service<Void>() {
                @Override
                protected Task<Void> createTask() {
                    return new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            if (yields.isSelected()) {
                                try {
                                    HashMap hash = new HashMap();
                                    BufferedImage image = ImageIO.read(getClass().getResource("/assets/icons/logo.png"));
                                    hash.put("logo", image);
                                    hash.put("date_from", accFrom.getValue().format(format));
                                    hash.put("date_to", accTo.getValue().format(format));
                                    InputStream subadmission = getClass().getResourceAsStream("/screens/accounts/reports/YiedsAdmission.jasper");
                                    InputStream subother = getClass().getResourceAsStream("/screens/accounts/reports/YiedsOthers.jasper");
                                    hash.put("yieldsAdmission", subadmission);
                                    hash.put("yieldsOther", subother);
                                    hash.put("subrepo", true);
                                    InputStream a = getClass().getResourceAsStream("/screens/accounts/reports/Yieds.jrxml");

                                    JasperDesign design = JRXmlLoader.load(a);
                                    JasperReport jasperreport = JasperCompileManager.compileReport(design);
                                    JasperPrint jasperprint = JasperFillManager.fillReport(jasperreport, hash, db.get.getReportCon());
                                    JasperViewer.viewReport(jasperprint, false);
                                } catch (Exception ex) {
                                    AlertDialogs.showErrors(ex);
                                }
                            } else if (expenses.isSelected()) {
                                try {
                                    HashMap hash = new HashMap();
                                    BufferedImage image = ImageIO.read(getClass().getResource("/assets/icons/logo.png"));
                                    hash.put("logo", image);
                                    hash.put("date_from", accFrom.getValue().format(format));
                                    hash.put("date_to", accTo.getValue().format(format));
                                    hash.put("subrepo", true);
                                    InputStream a = getClass().getResourceAsStream("/screens/accounts/reports/Expenses.jrxml");

                                    JasperDesign design = JRXmlLoader.load(a);
                                    JasperReport jasperreport = JasperCompileManager.compileReport(design);
                                    JasperPrint jasperprint = JasperFillManager.fillReport(jasperreport, hash, db.get.getReportCon());
                                    JasperViewer.viewReport(jasperprint, false);
                                } catch (Exception ex) {
                                    AlertDialogs.showErrors(ex);
                                }
                            } else if (yieldsAndExpenses.isSelected()) {
                                try {
                                    HashMap hash = new HashMap();
                                    BufferedImage image = ImageIO.read(getClass().getResource("/assets/icons/logo.png"));
                                    hash.put("logo", image);
                                    hash.put("date_from", accFrom.getValue().format(format));
                                    hash.put("date_to", accTo.getValue().format(format));
                                    InputStream subadmission = getClass().getResourceAsStream("/screens/accounts/reports/YiedsAdmission.jasper");
                                    InputStream subother = getClass().getResourceAsStream("/screens/accounts/reports/YiedsOthers.jasper");
                                    InputStream expens = getClass().getResourceAsStream("/screens/accounts/reports/Expenses.jasper");
                                    InputStream yield = getClass().getResourceAsStream("/screens/accounts/reports/Yieds.jasper");
                                    hash.put("yieldsAdmission", subadmission);
                                    hash.put("yieldsOther", subother);
                                    hash.put("yields", yield);
                                    hash.put("expenses", expens);

                                    InputStream medicineRep = getClass().getResourceAsStream("/screens/accounts/reports/CompanyExpenses.jasper");
                                    JasperReport medicineRepsubJasperReport = (JasperReport) JRLoader.loadObject(medicineRep);
                                    hash.put("company", medicineRepsubJasperReport);

                                    InputStream a = getClass().getResourceAsStream("/screens/accounts/reports/AccountStatement.jrxml");

                                    JasperDesign design = JRXmlLoader.load(a);
                                    JasperReport jasperreport = JasperCompileManager.compileReport(design);
                                    JasperPrint jasperprint = JasperFillManager.fillReport(jasperreport, hash, db.get.getReportCon());
                                    JasperViewer.viewReport(jasperprint, false);
                                } catch (Exception ex) {
                                    AlertDialogs.showErrors(ex);
                                }
                            }

                            return null;
                        }
                    };
                }

                @Override
                protected void succeeded() {
                    accProgress.setVisible(false);
                    super.succeeded();
                }
            };
            service.start();
        }
    }

    @FXML
    private void printContract(ActionEvent event) {
        if (contractFrom.getValue() == null || contractTo.getValue() == null || contract.getSelectionModel().getSelectedIndex() == -1) {
            AlertDialogs.showError("حدد الفترة والتعاقد اولا");
        } else {
            try {
                contractProgress.setVisible(false);
                HashMap hash = new HashMap();
                BufferedImage image = ImageIO.read(getClass().getResource("/assets/icons/logo.png"));
                hash.put("logo", image);
                hash.put("date_from", contractFrom.getValue().format(format));
                hash.put("date_to", contractTo.getValue().format(format));
                hash.put("contract_id", Integer.toString(contract.getSelectionModel().getSelectedItem().getId()));
                hash.put("contract_name", contract.getSelectionModel().getSelectedItem().getName());

                InputStream suprepo = getClass().getResourceAsStream("/screens/mainDataScreen/reports/Admissions.jasper");
                JasperReport subJasperReport = (JasperReport) JRLoader.loadObject(suprepo);
                hash.put("admissionReport", subJasperReport);

                InputStream clincRep = getClass().getResourceAsStream("/screens/mainDataScreen/reports/AdmissionDetailsClincs.jasper");
                JasperReport clincRepsubJasperReport = (JasperReport) JRLoader.loadObject(clincRep);
                hash.put("clincRep", clincRepsubJasperReport);

                InputStream contractRep = getClass().getResourceAsStream("/screens/mainDataScreen/reports/AdmissionDetailsContract.jasper");
                JasperReport contractRepsubJasperReport = (JasperReport) JRLoader.loadObject(contractRep);
                hash.put("contractRep", contractRepsubJasperReport);

                InputStream medicineRep = getClass().getResourceAsStream("/screens/mainDataScreen/reports/AdmissionDetailsMedicines.jasper");
                JasperReport medicineRepsubJasperReport = (JasperReport) JRLoader.loadObject(medicineRep);
                hash.put("medicineRep", medicineRepsubJasperReport);

                InputStream serviceRep = getClass().getResourceAsStream("/screens/mainDataScreen/reports/AdmissionDetailsServices.jasper");
                JasperReport serviceRepsubJasperReport = (JasperReport) JRLoader.loadObject(serviceRep);
                hash.put("serviceRep", serviceRepsubJasperReport);

                InputStream surmedRep = getClass().getResourceAsStream("/screens/mainDataScreen/reports/AdmissionDetailsSurMed.jasper");
                JasperReport surmedRepsubJasperReport = (JasperReport) JRLoader.loadObject(surmedRep);
                hash.put("surmedRep", surmedRepsubJasperReport);

                InputStream surgiresRep = getClass().getResourceAsStream("/screens/mainDataScreen/reports/AdmissionDetailsSurgires.jasper");
                JasperReport surgiresRepsubJasperReport = (JasperReport) JRLoader.loadObject(surgiresRep);
                hash.put("surgiresRep", surgiresRepsubJasperReport);

                InputStream a = getClass().getResourceAsStream("/screens/accounts/reports/contracts.jrxml");
                JasperDesign design = JRXmlLoader.load(a);
                JasperReport jasperreport = JasperCompileManager.compileReport(design);
                JasperPrint jasperprint = JasperFillManager.fillReport(jasperreport, hash, db.get.getReportCon());
                JasperViewer.viewReport(jasperprint, false);

            } catch (Exception ex) {
                AlertDialogs.showErrors(ex);
            }
            contractProgress.setVisible(true);
        }
    }

    @FXML
    private void printCompany(ActionEvent event) {
        if (companyFrom.getValue() == null || companyTo.getValue() == null || company.getSelectionModel().getSelectedIndex() == -1) {
            AlertDialogs.showError("حدد الفترة والشركة اولا");
        } else {
            companyProgress.setVisible(false);
            try {
                HashMap hash = new HashMap();
                BufferedImage image = ImageIO.read(getClass().getResource("/assets/icons/logo.png"));
                hash.put("logo", image);
                hash.put("date_from", companyFrom.getValue().format(format));
                hash.put("date_to", companyTo.getValue().format(format));
                hash.put("company_id", Integer.toString(company.getSelectionModel().getSelectedItem().getId()));
                hash.put("company_name", company.getSelectionModel().getSelectedItem().getName());

                InputStream a = getClass().getResourceAsStream("/screens/accounts/reports/CompanyAccounts.jrxml");
                JasperDesign design = JRXmlLoader.load(a);
                JasperReport jasperreport = JasperCompileManager.compileReport(design);
                JasperPrint jasperprint = JasperFillManager.fillReport(jasperreport, hash, db.get.getReportCon());
                JasperViewer.viewReport(jasperprint, false);

            } catch (Exception ex) {
                AlertDialogs.showErrors(ex);
            }
            companyProgress.setVisible(true);

        }
    }

}
