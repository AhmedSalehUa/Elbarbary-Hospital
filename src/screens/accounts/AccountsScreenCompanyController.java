/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.accounts;

import assets.classes.AlertDialogs;
import static assets.classes.statics.*;
import com.jfoenix.controls.JFXDatePicker;
import elbarbary.hospital.ElBarbaryHospital;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.application.Platform;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;
import screens.accounts.assets.CompanyAccounts;
import screens.store.assets.Company;
import screens.store.assets.Invoice;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class AccountsScreenCompanyController implements Initializable {

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
    private Label compRemainingAmount;
    @FXML
    private Label compTotalPaied;
    @FXML
    private Label compTotalAccounts;
    @FXML
    private TableView<CompanyAccounts> comAccTable;
    @FXML
    private TableColumn<CompanyAccounts, String> comAccTabDate;
    @FXML
    private TableColumn<CompanyAccounts, String> comAccTabType;
    @FXML
    private TableColumn<CompanyAccounts, String> comAccTabAmount;
    @FXML
    private TableColumn<CompanyAccounts, String> comAccTabInvoice;
    @FXML
    private TextField comAccAmount;
    @FXML
    private ComboBox<Invoice> comAccInvoices;
    @FXML
    private JFXDatePicker comAccDate;
    @FXML
    private ProgressIndicator progress;
    @FXML
    private Button formEdite;
    @FXML
    private Button formAdd;
    @FXML
    private Button formNew;
    @FXML
    private Button formDelete;
    Preferences prefs;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
         comAccDate.setConverter(new StringConverter<LocalDate>() {
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
                                    prefs = Preferences.userNodeForPackage(ElBarbaryHospital.class);
                                    clear();
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

            @Override
            protected void succeeded() {
                progress.setVisible(false);

                super.succeeded();
            }
        };
        service.start();
        compTable.setOnMouseClicked((e) -> {
            compTotalAccounts.setText("0");
            compTotalPaied.setText("0");
            compRemainingAmount.setText("0");
            if (compTable.getSelectionModel().getSelectedIndex() == -1) {
            } else {

                try {
                    Company selected = compTable.getSelectionModel().getSelectedItem();
                    comAccTable.setItems(CompanyAccounts.getData(selected.getId()));
                    fillCombo(selected.getId());

                    ObservableList<CompanyAccounts> items1 = comAccTable.getItems();
                    for (CompanyAccounts a : items1) {
                        if (a.getType().equals("مستحق للشركة")) {
                            compTotalAccounts.setText(Double.toString(Double.parseDouble(compTotalAccounts.getText()) + Double.parseDouble(a.getAmount())));
                        } else {
                            compTotalPaied.setText(Double.toString(Double.parseDouble(compTotalPaied.getText()) + Double.parseDouble(a.getAmount())));
                        }
                    }
                    compRemainingAmount.setText(Double.toString(Double.parseDouble(compTotalAccounts.getText()) - Double.parseDouble(compTotalPaied.getText())));
                } catch (Exception ex) {
                    AlertDialogs.showErrors(ex);
                }
            }
        });
        comAccTable.setOnMouseClicked((e) -> {
            if (comAccTable.getSelectionModel().getSelectedIndex() == -1) {
            } else {
                CompanyAccounts selected = comAccTable.getSelectionModel().getSelectedItem();
                if (selected.getType().equals("مستحق للشركة")) {
                    formNew.setDisable(false);

                    formDelete.setDisable(true);

                    formEdite.setDisable(true);

                    formAdd.setDisable(true);
                } else {
                    formNew.setDisable(false);

                    formDelete.setDisable(false);

                    formEdite.setDisable(false);

                    formAdd.setDisable(true);
                }

                comAccAmount.setText(selected.getAmount());
                comAccDate.setValue(LocalDate.parse(selected.getDate()));
                
                ObservableList<Invoice> items1 = comAccInvoices.getItems();
                for (Invoice a : items1) {
                    if (a.getId() == selected.getInvoiceid()) {
                        comAccInvoices.getSelectionModel().select(a);
                    }
                }
            }
        });

    }

    private void intialColumn() {
        compTabTele.setCellValueFactory(new PropertyValueFactory<>("tele"));

        compTabAcc.setCellValueFactory(new PropertyValueFactory<>("account"));

        compTabType.setCellValueFactory(new PropertyValueFactory<>("type"));

        compTabName.setCellValueFactory(new PropertyValueFactory<>("name"));

        compTabId.setCellValueFactory(new PropertyValueFactory<>("id"));

        comAccTabDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        comAccTabType.setCellValueFactory(new PropertyValueFactory<>("type"));

        comAccTabAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        comAccTabInvoice.setCellValueFactory(new PropertyValueFactory<>("invoiceid"));
    }

    private void clear() {
        getAutoNum();
        formNew.setDisable(true);

        formDelete.setDisable(true);

        formEdite.setDisable(true);

        formAdd.setDisable(false);

        comAccAmount.setText("");
        comAccDate.setValue(null);

        comAccInvoices.getSelectionModel().clearSelection();

    }

    private void getAutoNum() {

    }

    private void getData() throws Exception {
        compTable.setItems(Company.getData());
    }

    private void fillCombo(int id) throws Exception {
        comAccInvoices.setItems(Invoice.getData(id));
        comAccInvoices.setConverter(new StringConverter<Invoice>() {
            @Override
            public String toString(Invoice patient) {
                return Integer.toString(patient.getId());
            }

            @Override
            public Invoice fromString(String string) {
                return null;
            }
        });
        comAccInvoices.setCellFactory(cell -> new ListCell<Invoice>() {
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
            protected void updateItem(Invoice person, boolean empty) {
                super.updateItem(person, empty);

                if (!empty && person != null) {
                    lblid.setText("م: " + Integer.toString(person.getId()));
                    lblName.setText("التاريخ: " + person.getDate());
                    setGraphic(gridPane);
                } else {
                    setGraphic(null);
                }
            }
        });

    }

    @FXML
    private void formEdite(ActionEvent event) {
        CompanyAccounts comp = comAccTable.getSelectionModel().getSelectedItem();
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
                                    alert.setTitle("Editing  Account");
                                    alert.setHeaderText("سيتم تعديل العملية ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        CompanyAccounts com = new CompanyAccounts();
                                        com.setId(comp.getId());
                                        com.setCompanyId(comp.getCompanyId());
                                        com.setInvoiceid(comAccInvoices.getSelectionModel().getSelectedItem().getId());
                                        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                        com.setDate(comAccDate.getValue().format(format));
                                        com.setAmount(comAccAmount.getText());
                                        com.setType("دفعة");
                                        com.setAcc_id(Integer.parseInt(prefs.get(MAIN_ACC_ID, "0")));
                                        com.setUserId(Integer.parseInt(prefs.get(USER_ID, "0")));
                                        com.Edite();
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
                try {
                    progress.setVisible(false);
                    clear();

                    comAccTable.setItems(CompanyAccounts.getData(comp.getCompanyId()));
                    fillCombo(comp.getCompanyId());
                    super.succeeded();
                } catch (Exception ex) {
                    AlertDialogs.showErrors(ex);
                }
            }
        };
        service.start();
    }

    @FXML
    private void formAdd(ActionEvent event) {
        CompanyAccounts companyId = comAccTable.getItems().get(0);
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
                                    CompanyAccounts com = new CompanyAccounts();
                                    com.setCompanyId(companyId.getCompanyId());
                                    com.setInvoiceid(comAccInvoices.getSelectionModel().getSelectedItem().getId());
                                    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                    com.setDate(comAccDate.getValue().format(format));
                                    com.setAmount(comAccAmount.getText());
                                    com.setType("دفعة");
                                    com.setAcc_id(Integer.parseInt(prefs.get(MAIN_ACC_ID, "0")));
                                    com.setUserId(Integer.parseInt(prefs.get(USER_ID, "0")));
                                    com.Add();
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
                try {
                    progress.setVisible(false);
                    clear();

                    comAccTable.setItems(CompanyAccounts.getData(companyId.getCompanyId()));
                    fillCombo(companyId.getCompanyId());
                    super.succeeded();
                } catch (Exception ex) {
                    AlertDialogs.showErrors(ex);
                }
            }
        };
        service.start();
    }

    @FXML
    private void formNew(ActionEvent event) {
        clear();
    }

    @FXML
    private void formDelete(ActionEvent event) {
        CompanyAccounts comp = comAccTable.getSelectionModel().getSelectedItem();
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
                                    alert.setTitle("Deleting  Account");
                                    alert.setHeaderText("سيتم حذف العملية ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        CompanyAccounts com = new CompanyAccounts();
                                        com.setId(comp.getId());
                                        com.Delete();
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
                try {
                    progress.setVisible(false);
                    clear();

                    comAccTable.setItems(CompanyAccounts.getData(comp.getCompanyId()));
                    fillCombo(comp.getCompanyId());
                    super.succeeded();
                } catch (Exception ex) {
                    AlertDialogs.showErrors(ex);
                }
            }
        };
        service.start();
    }

}
