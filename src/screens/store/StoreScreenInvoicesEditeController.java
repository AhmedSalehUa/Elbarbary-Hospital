/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.store;

import assets.classes.AlertDialogs;
import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import screens.mainDataScreen.assets.Medicine;
import screens.store.assets.Company;
import screens.store.assets.Invoice;
import screens.store.assets.InvoiceTable;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class StoreScreenInvoicesEditeController implements Initializable {

    @FXML
    private TextArea notes;
    @FXML
    private Button showInvoice;
    @FXML
    private ComboBox<Invoice> invoiceId;
    @FXML
    private ComboBox<Company> companys;
    @FXML
    private JFXDatePicker date;
    @FXML
    private TableView<InvoiceTable> invoiceTable;
    @FXML
    private TableColumn<InvoiceTable, String> invoiceTabCost;
    @FXML
    private TableColumn<InvoiceTable, String> invoiceTabAmount;
    @FXML
    private TableColumn<InvoiceTable, String> invoiceTabMedicine;
    @FXML
    private TableColumn<InvoiceTable, String> invoiceTabId;
    @FXML
    private Button invoiceDelete;
    @FXML
    private Button invoiveAdd;
    @FXML
    private ProgressIndicator progress;
    @FXML
    private TextField invoiceTotal;
    @FXML
    private TextField invoicedisc;
    @FXML
    private TextField invoiceDiscPercent;
    @FXML
    private TextField invoiceLastTotal;
    @FXML
    private MenuItem deleteRow;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
 date.setConverter(new StringConverter<LocalDate>() {
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

                                    initColumn();
                                    getData();
                                    fillCombo();
                                    clear();
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
        invoiceDiscPercent.setOnKeyPressed((w) -> {
            setTotal("");
        });
        invoicedisc.setOnKeyPressed((w) -> {
            setTotal("");
        });
    }

    private void clear() {
        try {

            date.setValue(null);
            invoicedisc.setText("");
            notes.setText("");
            invoiceDiscPercent.setText("");
            invoiceLastTotal.setText("");
            invoiceTotal.setText("0");
            invoiceTable.setItems(null);
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);

        }
    }

    private void initColumn() {
        invoiceTabCost.setCellValueFactory(new PropertyValueFactory<>("cost"));

        invoiceTabAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        invoiceTabMedicine.setCellValueFactory(new PropertyValueFactory<>("medicine"));

        invoiceTabId.setCellValueFactory(new PropertyValueFactory<>("id"));
    }

    private void getData() throws Exception {
        invoiceTable.setOnKeyReleased((event) -> {

            if (event.getCode() == KeyCode.ENTER) {
                setTotal("");
            }
            if (event.getCode() == KeyCode.ENTER && invoiceTable.getSelectionModel().getSelectedItem().getMedicine().getSelectionModel().getSelectedIndex() != -1
                    && !invoiceTable.getSelectionModel().getSelectedItem().getAmount().getText().equals("0")
                    && !invoiceTable.getSelectionModel().getSelectedItem().getCost().getText().equals("0")) {
                try {
                    ObservableList<Medicine> data = Medicine.getData();
                    setTotal("");
                    invoiceTable.getItems().add(new InvoiceTable(invoiceTable.getItems().size() + 1, data, "0", "0", this));
                    invoiceTable.getSelectionModel().clearAndSelect(invoiceTable.getItems().size() - 1);
                } catch (Exception ex) {
                    AlertDialogs.showErrors(ex);
                }
            }

        });

    }

    private void fillCombo() throws Exception {
        invoiceId.setItems(Invoice.getData());
        invoiceId.setConverter(new StringConverter<Invoice>() {
            @Override
            public String toString(Invoice contract) {
                return "id: " + contract.getId() + " date: " + contract.getDate();
            }

            @Override
            public Invoice fromString(String string) {
                return null;
            }
        });
        invoiceId.setCellFactory(cell -> new ListCell<Invoice>() {

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
            protected void updateItem(Invoice person, boolean empty) {
                super.updateItem(person, empty);

                if (!empty && person != null) {
                    lblid.setText("م: " + Integer.toString(person.getId()));
                    lblName.setText("التاريخ: " + person.getDate());
                    lblType.setText("الشركة: " + person.getCompany());
                    setGraphic(gridPane);
                } else {
                    setGraphic(null);
                }
            }
        });
        companys.setItems(Company.getData());
        companys.setConverter(new StringConverter<Company>() {
            @Override
            public String toString(Company contract) {
                return contract.getName();
            }

            @Override
            public Company fromString(String string) {
                return null;
            }
        });
        companys.setCellFactory(cell -> new ListCell<Company>() {

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

    public void setTotal(String toString) {
        try {

            ObservableList<InvoiceTable> items1 = invoiceTable.getItems();
            double total = 0;
            for (InvoiceTable a : items1) {
                total += Double.parseDouble(a.getAmount().getText()) * Double.parseDouble(a.getCost().getText());
            }
            invoiceTotal.setText(Double.toString(total));

            double discount = 0;
            double discountPercent = 0;
            if (invoicedisc.getText().isEmpty()) {
            } else {

                discount = Double.parseDouble(invoicedisc.getText().isEmpty() ? "0" : invoicedisc.getText());

            }
            if (invoiceDiscPercent.getText().isEmpty()) {
            } else {
                String a = invoiceDiscPercent.getText().isEmpty() ? "0" : invoiceDiscPercent.getText();
                discountPercent = ((Double.parseDouble(a) * total) / 100);

            }

            invoiceLastTotal.setText(Double.toString(total - discount - discountPercent));
        } catch (Exception e) {
            AlertDialogs.showErrors(e);
        }
    }

    @FXML
    private void showInvoice(ActionEvent event) {
        if (invoiceId.getSelectionModel().getSelectedIndex() == -1) {
            AlertDialogs.showError("اختار الفاتورة اولا");
        } else {
            try {
                getInvoiceDetails(invoiceId.getSelectionModel().getSelectedItem().getId());
                ObservableList<Invoice> cutomData = Invoice.getCutomData("SELECT `medicine_invoices`.`id`, `medicine_company`.`name`, `medicine_invoices`.`date`, `medicine_invoices`.`total`, `medicine_invoices`.`discount`, `medicine_invoices`.`discount_percent`, `medicine_invoices`.`total_after_descount`, `medicine_invoices`.`notes` FROM  `medicine_company`, `medicine_invoices` WHERE `medicine_invoices`.`company_id`= `medicine_company`.`id` AND `medicine_invoices`.`id`='" + invoiceId.getSelectionModel().getSelectedItem().getId() + "'");
                Invoice get = cutomData.get(0);
                date.setValue(LocalDate.parse(get.getDate()));
                notes.setText(get.getNotes());
                invoiceTotal.setText(get.getTotal());
                invoiceLastTotal.setText(get.getTotalAfterDiscount());
                invoiceDiscPercent.setText(get.getDiscountPercent());
                invoicedisc.setText(get.getDiscoun());

                ObservableList<Company> items = companys.getItems();
                for (Company a : items) {
                    if (a.getName().equals(get.getCompany())) {
                        companys.getSelectionModel().select(a);
                    }
                }
            } catch (Exception ex) {
                AlertDialogs.showErrors(ex);
            }
        }

    }

    @FXML
    private void invoiceDelete(ActionEvent event) {
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
                                    alert.setTitle("Deleting Invoice");
                                    alert.setHeaderText("سيتم حذف الفاتورة");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        Invoice i = new Invoice();
                                        i.setId(invoiceId.getSelectionModel().getSelectedItem().getId());
                                        i.Delete();
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
                progress.setVisible(false);

                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void invoiveAdd(ActionEvent event) {

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
                                    setTotal("");
                                    if (date.getValue() == null) {
                                        AlertDialogs.showError("برجاء ادخال تاريخ الفاتورة");
                                    } else if (companys.getSelectionModel().getSelectedIndex() == -1) {
                                        AlertDialogs.showError("برجاء اختيار شركة الادوية");
                                    } else if (invoiceTable.getItems().isEmpty()) {
                                        AlertDialogs.showError("لا يجب ان يكون الجدول فارغ");
                                    } else if (invoiceTable.getItems().size() == 1 && invoiceTotal.getText().equals("0") || invoiceTotal.getText().equals("0.0")) {
                                        AlertDialogs.showError("لا يجب ان يكون الجدول فارغ");
                                    } else {
                                        Invoice in = new Invoice();
                                        in.setId(invoiceId.getSelectionModel().getSelectedItem().getId());
                                        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                        in.setDate(date.getValue().format(format));
                                        in.setCompanyId(companys.getSelectionModel().getSelectedItem().getId());
                                        in.setTotal(invoiceTotal.getText());
                                        in.setDiscoun(invoicedisc.getText().isEmpty() ? "0" : invoicedisc.getText());
                                        in.setDiscountPercent(invoiceDiscPercent.getText().isEmpty() ? "0" : invoiceDiscPercent.getText());
                                        in.setTotalAfterDiscount(invoiceLastTotal.getText());
                                        in.setNotes(notes.getText().isEmpty() ? "لايوجد" : notes.getText());

                                        ObservableList<InvoiceTable> items = invoiceTable.getItems();

                                        items.remove(items.size() - 1);

                                        in.setinvoiceData(items);
                                        in.Edite();
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
                progress.setVisible(false);
                clear();
                super.succeeded();
            }
        };
        service.start();
    }

    private void getInvoiceDetails(int id) {
        try {
            invoiceTable.setItems(InvoiceTable.getCutomeData(id));
            invoiceTable.getItems().add(new InvoiceTable(1, Medicine.getData(), "0", "0", this));
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    @FXML
    private void deleteRow(ActionEvent event) {
        if (invoiceTable.getSelectionModel().getSelectedIndex() == -1) {
            AlertDialogs.showError("اختار الصف اولا");
        } else {
            if (invoiceTable.getSelectionModel().getSelectedItem().getMedicine().getSelectionModel().getSelectedIndex() != -1
                    && !invoiceTable.getSelectionModel().getSelectedItem().getAmount().getText().equals("0")
                    && !invoiceTable.getSelectionModel().getSelectedItem().getCost().getText().equals("0")) {
                invoiceTable.getItems().remove(invoiceTable.getSelectionModel().getSelectedIndex());
            }
        }
    }
}
