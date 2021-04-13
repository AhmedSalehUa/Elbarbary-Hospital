/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.store.assets;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JTable;

/**
 *
 * @author AHMED
 */
public class Transactions {

    int id;
    int invoiceId;
    int storeId;
    int medicineId;
    String amount;
    String oldAmount;
    String cost;
    String costOfSell;
    int oldStoreId;

    public Transactions() {
    }

    public Transactions(int id, int invoiceId, int storeId, int medicineId, String amount, String cost) {
        this.id = id;
        this.invoiceId = invoiceId;
        this.storeId = storeId;
        this.medicineId = medicineId;
        this.amount = amount;
        this.cost = cost;
    }

    public String getCostOfSell() {
        return costOfSell;
    }

    public void setCostOfSell(String costOfSell) {
        this.costOfSell = costOfSell;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOldStoreId() {
        return oldStoreId;
    }

    public void setOldStoreId(int oldStoreId) {
        this.oldStoreId = oldStoreId;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getStoreId() {
        return storeId;
    }

    public String getOldAmount() {
        return oldAmount;
    }

    public void setOldAmount(String oldAmount) {
        this.oldAmount = oldAmount;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public boolean AddToStores() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `stores_medicines`(`store_id`, `medicine_id`, `invoice_id`, `amount`, `cost`,`cost_of_sell`) VALUES (?,?,?,?,?,?)");
        ps.setInt(1, storeId);
        ps.setInt(2, medicineId);
        ps.setInt(3, invoiceId);
        ps.setString(4, amount);
        ps.setString(5, cost);
        ps.setString(6, costOfSell);
        ps.execute();
        return true;
    }

    public boolean TransportBetweenStores() throws Exception {
        PreparedStatement ps = db.get.Prepare("UPDATE `stores_medicines` SET `amount`=? WHERE `store_id`=? and `medicine_id`=? and `invoice_id`=?");
        ps.setString(1, Integer.toString(Integer.parseInt(oldAmount) - Integer.parseInt(amount)));
        ps.setInt(2, oldStoreId);
        ps.setInt(3, medicineId);
        ps.setInt(4, invoiceId);
        ps.execute();
        JTable tableData = db.get.getTableData("select amount from `stores_medicines` where `store_id`='" + storeId + "' and `medicine_id`='" + medicineId + "' and `invoice_id`='" + invoiceId + "'");
        if (tableData.getRowCount() == 0) {
            PreparedStatement pa = db.get.Prepare("INSERT INTO `stores_medicines`(`store_id`, `medicine_id`, `invoice_id`, `amount`, `cost`) VALUES (?,?,?,?,?)");
            pa.setInt(1, storeId);
            pa.setInt(2, medicineId);
            pa.setInt(3, invoiceId);
            pa.setString(4, amount);
            pa.setString(5, cost);
            pa.execute();
        } else {
            PreparedStatement pd = db.get.Prepare("UPDATE `stores_medicines` SET `amount`=? WHERE `store_id`=? and `medicine_id`=? and `invoice_id`=?");
            pd.setString(1, Integer.toString(Integer.parseInt(tableData.getValueAt(0, 0).toString()) + Integer.parseInt(amount)));
            pd.setInt(2, storeId);
            pd.setInt(3, medicineId);
            pd.setInt(4, invoiceId);
            pd.execute();
        }
        return true;
    }

    public boolean Edite() throws Exception {
        PreparedStatement ps = db.get.Prepare("");
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("");
        ps.execute();
        return true;
    }

    public static ObservableList<Transactions> getData() throws Exception {
        ObservableList<Transactions> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("");
        while (rs.next()) {
//            data.add();
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("").getValueAt(0, 0).toString();
    }
}
