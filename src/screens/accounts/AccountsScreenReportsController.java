/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.accounts;

import assets.classes.AlertDialogs;
import com.jfoenix.controls.JFXDatePicker;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javax.imageio.ImageIO;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import screens.mainDataScreen.assets.Patients;

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ToggleGroup group = new ToggleGroup();
        yields.setToggleGroup(group);
        yields.setSelected(true);
        expenses.setToggleGroup(group);
        yieldsAndExpenses.setToggleGroup(group);
        accProgress.setVisible(false);
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

}
