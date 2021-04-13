/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.store.assets;

import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell; 
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter; 
import screens.mainDataScreen.assets.Medicine;
import screens.store.StoreScreenInvoicesController;
import screens.store.StoreScreenInvoicesEditeController;

/**
 *
 * @author ahmed
 */
public class InvoiceTable {

    int id;
    ComboBox medicine;
    TextField amount;
    TextField cost;
    TextField costOfSell;
    StoreScreenInvoicesController pa;
    StoreScreenInvoicesEditeController pp;

    String medic;
    String amountString;
    String costString;
    int medicineID;
//    public InvoiceTable(int id, ComboBox medicine, String amount, String cost) {
//        this.id = id;
//        this.medicine = medicine;
//        this.amount = new TextField(amount);
//        this.cost = new TextField(cost);
//    }

    public InvoiceTable(int id, String medic, String amountString, String costString) {
        this.id = id;
        this.medic = medic;
        this.amountString = amountString;
        this.costString = costString;
    }

    public InvoiceTable(int id, String medic, String amountString, String costString, String costOfSell,int medicineID) {
        this.id = id;
        this.medic = medic;
        this.amountString = amountString;
        this.costString = costString;
        this.costOfSell=new TextField(costOfSell); this.medicineID=medicineID;
    }

    public InvoiceTable(int id, ObservableList<Medicine> data, String amount, String cost, StoreScreenInvoicesController pa) {
        this.id = id;
        this.medicine = new ComboBox(data);
        medicine.setConverter(new StringConverter<Medicine>() {
            @Override
            public String toString(Medicine contract) {
                return contract.getName();
            }

            @Override
            public Medicine fromString(String string) {
                return null;
            }
        });
        medicine.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        medicine.setOnAction((e) -> {

        });
        medicine.setCellFactory(cell -> new ListCell<Medicine>() {

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
            protected void updateItem(Medicine person, boolean empty) {
                super.updateItem(person, empty);

                if (!empty && person != null) {

                    // Update our Labels
                    lblid.setText("م: " + Integer.toString(person.getId()));
                    lblName.setText("الاسم: " + person.getName());
                    lblQuali.setText("الوحدة: " + person.getUnit());
                    setGraphic(gridPane);
                } else {
                    // Nothing to display here
                    setGraphic(null);
                }
            }
        });
        this.amount = new TextField(amount);
        this.cost = new TextField(cost);
        this.pa = pa;
    }

    public InvoiceTable(int id, ObservableList<Medicine> data, String selectedmed, String amount, String cost) {
        this.id = id;
        this.medicine = new ComboBox(data);
        medicine.setConverter(new StringConverter<Medicine>() {
            @Override
            public String toString(Medicine contract) {
                return contract.getName();
            }

            @Override
            public Medicine fromString(String string) {
                return null;
            }
        });
        medicine.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        for (Medicine a : data) {
            if (a.getName().equals(selectedmed)) {
                medicine.getSelectionModel().select(a);

            }
        }
        medicine.setCellFactory(cell -> new ListCell<Medicine>() {

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
            protected void updateItem(Medicine person, boolean empty) {
                super.updateItem(person, empty);

                if (!empty && person != null) {

                    // Update our Labels
                    lblid.setText("م: " + Integer.toString(person.getId()));
                    lblName.setText("الاسم: " + person.getName());
                    lblQuali.setText("التخصص: " + person.getUnit());
                    // Set this ListCell's graphicProperty to display our GridPane
                    setGraphic(gridPane);
                } else {
                    // Nothing to display here
                    setGraphic(null);
                }
            }
        });
        this.amount = new TextField(amount);
        this.cost = new TextField(cost);
        this.pa = pa;
    }

    public InvoiceTable(int id, ObservableList<Medicine> data, String amount, String cost, StoreScreenInvoicesEditeController pp) {
        this.id = id;
        this.medicine = new ComboBox(data);
        medicine.setConverter(new StringConverter<Medicine>() {
            @Override
            public String toString(Medicine contract) {
                return contract.getName();
            }

            @Override
            public Medicine fromString(String string) {
                return null;
            }
        });
        medicine.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        medicine.setOnAction((e) -> {

        });
        medicine.setCellFactory(cell -> new ListCell<Medicine>() {

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
            protected void updateItem(Medicine person, boolean empty) {
                super.updateItem(person, empty);

                if (!empty && person != null) {

                    // Update our Labels
                    lblid.setText("م: " + Integer.toString(person.getId()));
                    lblName.setText("الاسم: " + person.getName());
                    lblQuali.setText("التخصص: " + person.getUnit());
                    // Set this ListCell's graphicProperty to display our GridPane
                    setGraphic(gridPane);
                } else {
                    // Nothing to display here
                    setGraphic(null);
                }
            }
        });
        this.amount = new TextField(amount);
        this.cost = new TextField(cost);
        this.pp = pp;
    }

    public TextField getCostOfSell() {
        return costOfSell;
    }

    public void setCostOfSell(TextField costOfSell) {
        this.costOfSell = costOfSell;
    }

    public int getMedicineID() {
        return medicineID;
    }

    public void setMedicineID(int medicineID) {
        this.medicineID = medicineID;
    }

    void setTotal() {
        pa.setTotal("");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMedic() {
        return medic;
    }

    public void setMedic(String medic) {
        this.medic = medic;
    }

    public String getAmountString() {
        return amountString;
    }

    public void setAmountString(String amountString) {
        this.amountString = amountString;
    }

    public String getCostString() {
        return costString;
    }

    public void setCostString(String costString) {
        this.costString = costString;
    }

    public ComboBox getMedicine() {
        return medicine;
    }

    public void setMedicine(ComboBox medicine) {
        this.medicine = medicine;
    }

    public TextField getAmount() {
        return amount;
    }

    public void setAmount(TextField amount) {
        this.amount = amount;
    }

    public TextField getCost() {
        return cost;
    }

    public void setCost(TextField cost) {
        this.cost = cost;
    }

    public static ObservableList<InvoiceTable> getData(int invoiceid) throws Exception {
        ObservableList<InvoiceTable> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `medicine_invoices_details`.`invoice_id`,`medicine`.`name`, `medicine_invoices_details`.`amount`, `medicine_invoices_details`.`cost` FROM `medicine_invoices_details`,`medicine` WHERE `medicine`.`id` =`medicine_invoices_details`.`medicine_id` AND `medicine_invoices_details`.`invoice_id`='" + invoiceid + "'");
        while (rs.next()) {
            data.add(new InvoiceTable(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
        }
        return data;
    }

    public static ObservableList<InvoiceTable> getDataCost(int invoiceid) throws Exception {
        ObservableList<InvoiceTable> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `medicine_invoices_details`.`invoice_id`,`medicine`.`name`, `medicine_invoices_details`.`amount`, `medicine_invoices_details`.`cost`, `medicine_invoices_details`.`cost`,`medicine_invoices_details`.`medicine_id` FROM `medicine_invoices_details`,`medicine` WHERE `medicine`.`id` =`medicine_invoices_details`.`medicine_id` AND `medicine_invoices_details`.`invoice_id`='" + invoiceid + "'");
        while (rs.next()) {
            data.add(new InvoiceTable(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getInt(6)));
        }
        return data;
    }

    public static ObservableList<InvoiceTable> getCutomeData(int invoiceid) throws Exception {
        ObservableList<InvoiceTable> data = FXCollections.observableArrayList();
        ObservableList<Medicine> MedicineData = Medicine.getData();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `medicine_invoices_details`.`invoice_id`,`medicine`.`name`, `medicine_invoices_details`.`amount`, `medicine_invoices_details`.`cost` FROM `medicine_invoices_details`,`medicine` WHERE `medicine`.`id` =`medicine_invoices_details`.`medicine_id` AND `medicine_invoices_details`.`invoice_id`='" + invoiceid + "'");
        while (rs.next()) {
            data.add(new InvoiceTable(rs.getInt(1), MedicineData, rs.getString(2), rs.getString(3), rs.getString(4)));
        }
        return data;
    }

}
