/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.store;

import assets.classes.AlertDialogs;
import com.jfoenix.controls.JFXDatePicker;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
import javafx.scene.control.Button;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import org.apache.log4j.helpers.DateTimeDateFormat;
import screens.admission.assets.SurgeriesType;
import screens.mainDataScreen.assets.Medicine;
import screens.store.assets.Company;
import screens.store.assets.Invoice;
import screens.store.assets.InvoiceTable;

/**
 * FXML Controller class
 *
 * @author ahmed
 */
public class StoreScreenInvoicesController implements Initializable {

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
    private TextField invoiceTotal;
    @FXML
    private TextField invoicedisc;
    @FXML
    private TextField invoiceDiscPercent;
    @FXML
    private TextField invoiceLastTotal;
    @FXML
    private ProgressIndicator progress;
    @FXML
    private TextArea notes;
    @FXML
    private Label id;
    @FXML
    private ComboBox<Company> companys;
    @FXML
    private JFXDatePicker date;
    @FXML
    private Button invoiveAdd;
    @FXML
    private AnchorPane showInviceSection;
    @FXML
    private AnchorPane editeInviceSection;
    @FXML
    private MenuItem deleteRow;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

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
                                    clear();
                                    initColumn();
                                    getData();
                                    fillCombo();

                                    intialPanels();
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
            setAutoNum();
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

    private void intialPanels() throws Exception {
        showInviceSection.getChildren().clear();
        FXMLLoader fxShow = new FXMLLoader(getClass().getResource("/screens/store/StoreScreenInvoicesShow.fxml"));
        showInviceSection.getChildren().add(fxShow.load());

        editeInviceSection.getChildren().clear();
        FXMLLoader fxEdite = new FXMLLoader(getClass().getResource("/screens/store/StoreScreenInvoicesEdite.fxml"));
        editeInviceSection.getChildren().add(fxEdite.load());
    }

    private void initColumn() {
        invoiceTabCost.setCellValueFactory(new PropertyValueFactory<>("cost"));

        invoiceTabAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        invoiceTabMedicine.setCellValueFactory(new PropertyValueFactory<>("medicine"));

        invoiceTabId.setCellValueFactory(new PropertyValueFactory<>("id"));
    }

    private void getData() throws Exception {
        ObservableList<Medicine> data = Medicine.getData();

        ObservableList<InvoiceTable> list = FXCollections.observableArrayList();
        list.add(new InvoiceTable(1, data, "0", "0", StoreScreenInvoicesController.this));
        invoiceTable.setItems(list);
        invoiceTable.setOnKeyReleased((event) -> {

            if (event.getCode() == KeyCode.ENTER) {
                setTotal("");
            }
            if (event.getCode() == KeyCode.ENTER && invoiceTable.getSelectionModel().getSelectedItem().getMedicine().getSelectionModel().getSelectedIndex() != -1
                    && !invoiceTable.getSelectionModel().getSelectedItem().getAmount().getText().equals("0")
                    && !invoiceTable.getSelectionModel().getSelectedItem().getCost().getText().equals("0")) {
                setTotal("");
                invoiceTable.getItems().add(new InvoiceTable(invoiceTable.getItems().size() + 1, data, "0", "0", StoreScreenInvoicesController.this));
                invoiceTable.getSelectionModel().clearAndSelect(invoiceTable.getItems().size() - 1);
            }

        });
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

    private void fillCombo() throws Exception {
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
    private void invoiveAdd(ActionEvent event) {

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
                                    setTotal("");
                                    if (date.getValue() == null) {
                                        AlertDialogs.showError("برجاء ادخال تاريخ الفاتورة");
                                    } else if (companys.getSelectionModel().getSelectedIndex() == -1) {
                                        AlertDialogs.showError("برجاء اختيار شركة الادوية");
                                    } else if (invoiceTable.getItems().isEmpty()) {
                                        AlertDialogs.showError("لا يجب ان يكون الجدول فارغ");
                                    } else if (invoiceTable.getItems().size() == 1 && invoiceTotal.getText().equals("0") || invoiceTotal.getText().equals("0.0")) {
                                        AlertDialogs.showError("لا يجب ان يكون الجدول فارغ");
                                    } else if (invoiceTotal.getText().equals("0")) {
                                        setTotal("");
                                    } else if (invoiceTable.getItems().size() == 1) {
                                        AlertDialogs.showError("لا يجب ان يكون الجدول فارغ");
                                    } else {
                                        ObservableList<InvoiceTable> items = invoiceTable.getItems();

                                        if (items.size() - 1 == 0) {
                                              AlertDialogs.showError("اضغط Enter اذا كان الجدول غير فارغ على اخر خانة");
                                        } else {
                                            Invoice in = new Invoice();
                                            in.setId(Integer.parseInt(id.getText()));
                                            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                            in.setDate(date.getValue().format(format));
                                            in.setCompanyId(companys.getSelectionModel().getSelectedItem().getId());
                                            in.setTotal(invoiceTotal.getText());
                                            in.setDiscoun(invoicedisc.getText().isEmpty() ? "0" : invoicedisc.getText());
                                            in.setDiscountPercent(invoiceDiscPercent.getText().isEmpty() ? "0" : invoiceDiscPercent.getText());
                                            in.setTotalAfterDiscount(invoiceLastTotal.getText());
                                            in.setNotes(notes.getText().isEmpty() ? "لايوجد" : notes.getText());

                                            items.remove(items.size() - 1);

                                            in.setinvoiceData(items);
                                            in.Add();
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

            @Override
            protected void succeeded() {
                progress.setVisible(false);
                clear();
                super.succeeded();
            }
        };
        service.start();
    }

    private void setAutoNum() throws Exception {
        id.setText(Invoice.getAutoNum());
    }

}