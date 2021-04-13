/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.store;

import assets.classes.AlertDialogs;
import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
import screens.store.assets.Company;
import screens.store.assets.Invoice;
import screens.store.assets.InvoiceTable;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class StoreScreenInvoicesShowController implements Initializable {

    @FXML
    private Button showInvoices;
    @FXML
    private ComboBox<Company> company;
    @FXML
    private CheckBox withCompany;
    @FXML
    private JFXDatePicker dateFrom;
    @FXML
    private CheckBox withDateFrom;
    @FXML
    private JFXDatePicker dateTo;
    @FXML
    private CheckBox withDateTo;
    @FXML
    private TableView<Invoice> invoiceTable;
    @FXML
    private TableColumn<Invoice, String> invoiceTabNotes;
    @FXML
    private TableColumn<Invoice, String> invoiceTabTotalCost;
    @FXML
    private TableColumn<Invoice, String> invoiceTabDiscPerc;
    @FXML
    private TableColumn<Invoice, String> invoiceTabDisc;
    @FXML
    private TableColumn<Invoice, String> invoiceTabCost;
    @FXML
    private TableColumn<Invoice, String> invoiceTabDate;
    @FXML
    private TableColumn<Invoice, String> invoiceTabComp;
    @FXML
    private TableColumn<Invoice, String> invoiceTabId;
    @FXML
    private TableView<InvoiceTable> invoiceDetailsTable;
    @FXML
    private TableColumn<InvoiceTable, String> invoiceDetailsTabCost;
    @FXML
    private TableColumn<InvoiceTable, String> invoiceDetailsTabAmount;
    @FXML
    private TableColumn<InvoiceTable, String> invoiceDetailsTabMediccine;
    @FXML
    private TableColumn<InvoiceTable, String> invoiceDetailsTabId;
    @FXML
    private ProgressIndicator progress;

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
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    initColumn();
                                    filCombo();

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
        invoiceTable.setOnMouseClicked((e) -> {
            if (invoiceTable.getSelectionModel().getSelectedIndex() == -1) {
                invoiceDetailsTable.setItems(null);
                invoiceTable.getSelectionModel().clearSelection();
            } else {
                getInvoiceDetails(invoiceTable.getSelectionModel().getSelectedItem().getId());
            }
        });
    }

    @FXML
    private void showInvoices(ActionEvent event) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String sql = "SELECT `medicine_invoices`.`id`, `medicine_company`.`name`, `medicine_invoices`.`date`, `medicine_invoices`.`total`, `medicine_invoices`.`discount`, `medicine_invoices`.`discount_percent`, `medicine_invoices`.`total_after_descount`, `medicine_invoices`.`notes` FROM  `medicine_company`, `medicine_invoices` WHERE `medicine_invoices`.`company_id`= `medicine_company`.`id` ";
        if (withCompany.isSelected()) {
            int compId = company.getSelectionModel().getSelectedItem().getId();
            sql += " AND `medicine_invoices`.`company_id`='" + compId + "'";
        }
        if (withDateFrom.isSelected()) {
            String dateFrom = this.dateFrom.getValue().format(format);
            sql += " AND `medicine_invoices`.`date` >= '" + dateFrom + "'";
        }
        if (withDateTo.isSelected()) {
            String dateTo = this.dateTo.getValue().format(format);
            sql += " AND `medicine_invoices`.`date` <= '" + dateTo + "'";
        }
        try {
invoiceDetailsTable.setItems(null);
            invoiceTable.setItems(Invoice.getCutomData(sql));
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }

    }

    private void initColumn() {
        invoiceTabNotes.setCellValueFactory(new PropertyValueFactory<>("notes"));

        invoiceTabTotalCost.setCellValueFactory(new PropertyValueFactory<>("totalAfterDiscount"));

        invoiceTabDiscPerc.setCellValueFactory(new PropertyValueFactory<>("discountPercent"));

        invoiceTabDisc.setCellValueFactory(new PropertyValueFactory<>("discoun"));

        invoiceTabCost.setCellValueFactory(new PropertyValueFactory<>("total"));

        invoiceTabDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        invoiceTabComp.setCellValueFactory(new PropertyValueFactory<>("company"));

        invoiceTabId.setCellValueFactory(new PropertyValueFactory<>("id"));

        invoiceDetailsTabCost.setCellValueFactory(new PropertyValueFactory<>("costString"));

        invoiceDetailsTabAmount.setCellValueFactory(new PropertyValueFactory<>("amountString"));

        invoiceDetailsTabMediccine.setCellValueFactory(new PropertyValueFactory<>("medic"));

        invoiceDetailsTabId.setCellValueFactory(new PropertyValueFactory<>("id"));
    }

    private void filCombo() throws Exception {
        company.setItems(Company.getData());
        company.setConverter(new StringConverter<Company>() {
            @Override
            public String toString(Company contract) {
                return contract.getName();
            }

            @Override
            public Company fromString(String string) {
                return null;
            }
        });
        company.setCellFactory(cell -> new ListCell<Company>() {

            GridPane gridPane = new GridPane();
            Label lblid = new Label();
            Label lblName = new Label();
            Label lblType = new Label();

            {
                gridPane.getColumnConstraints().addAll(
                        new ColumnConstraints(100, 100, 100), new ColumnConstraints(100, 100, 100),
                        new ColumnConstraints(100, 100, 100)
                );
                gridPane.add(lblid, 0, 1);
                gridPane.add(lblName, 1, 1);
                gridPane.add(lblType, 2, 1);
            }

            @Override
            protected void updateItem(Company person, boolean empty) {
                super.updateItem(person, empty);

                if (!empty && person != null) {
                    lblid.setText("م: " + Integer.toString(person.getId()));
                    lblName.setText("الاسم: " + person.getName());
                    lblType.setText("النوع: " + person.getType());
                    setGraphic(gridPane);
                } else {
                    setGraphic(null);
                }
            }
        });
    }

    private void getData() {

    }

    private void getInvoiceDetails(int id) {
        try {
            invoiceDetailsTable.setItems(InvoiceTable.getData(id));
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

}
