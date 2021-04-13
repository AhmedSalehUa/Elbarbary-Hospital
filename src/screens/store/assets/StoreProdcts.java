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

/**
 *
 * @author AHMED
 */
public class StoreProdcts {

    int id;
    int stoeId;
    int invoiceId;
    String product;
    String amount;
    String cost;
    String costOfSell;

    public StoreProdcts() {
    }

    public StoreProdcts(int id, int invoiceId, String product, String amount, String cost, String costOfSell) {
        this.id = id;
        this.invoiceId = invoiceId;
        this.product = product;
        this.amount = amount;
        this.cost = cost;
        this.costOfSell = costOfSell;
    }

    public StoreProdcts(int invoiceId, String product, String amount, String cost, String costOfSell) {
        this.invoiceId = invoiceId;
        this.product = product;
        this.amount = amount;
        this.cost = cost;
        this.costOfSell = costOfSell;
    }

    public StoreProdcts(String product, String amount, String cost) {
        this.product = product;
        this.amount = amount;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getStoeId() {
        return stoeId;
    }

    public void setStoeId(int stoeId) {
        this.stoeId = stoeId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
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

    public String getCostOfSell() {
        return costOfSell;
    }

    public void setCostOfSell(String costOfSell) {
        this.costOfSell = costOfSell;
    }

    public static ObservableList<StoreProdcts> getData(String id) throws Exception {
        ObservableList<StoreProdcts> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT  `stores_medicines`.`invoice_id`,`medicine`.`name`,  `stores_medicines`.`amount`, `stores_medicines`.`cost`, `stores_medicines`.`cost_of_sell` FROM `stores_medicines`,`medicine` WHERE `store_id`='" + id + "' and `stores_medicines`.`medicine_id` = `medicine`.`id`;  ");
        while (rs.next()) {
            data.add(new StoreProdcts(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
        }
        return data;
    }

    public static ObservableList<StoreProdcts> getDataForSell(String id) throws Exception {
        ObservableList<StoreProdcts> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT  `stores_medicines`.`id`,`stores_medicines`.`invoice_id`,`medicine`.`name`,  `stores_medicines`.`amount`, `stores_medicines`.`cost`, `stores_medicines`.`cost_of_sell` FROM `stores_medicines`,`medicine` WHERE `store_id`='" + id + "' and `stores_medicines`.`medicine_id` = `medicine`.`id`;  ");
        while (rs.next()) {
            data.add(new StoreProdcts(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
        }
        return data;
    }

    public static ObservableList<StoreProdcts> getDataForShortsOrder(String id) throws Exception {
        ObservableList<StoreProdcts> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT  `stores_medicines`.`medicine_id`,`medicine`.`name`,  `stores_medicines`.`amount`, `stores_medicines`.`cost`, `stores_medicines`.`cost_of_sell`  FROM `stores_medicines`,`medicine` WHERE `store_id`='" + id + "' and `stores_medicines`.`medicine_id` = `medicine`.`id`;  ");
        while (rs.next()) {
            data.add(new StoreProdcts(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
        }
        return data;
    }

    public static boolean reduceAmount(int id, String amount) throws Exception {
        Double a = Double.parseDouble(db.get.getTableData("SELECT `amount` FROM `stores_medicines` WHERE `id`='" + id + "'").getValueAt(0, 0).toString()) - Double.parseDouble(amount);
        PreparedStatement ps = db.get.Prepare("UPDATE `stores_medicines` SET `amount`=? WHERE `id`=?");
        ps.setString(1, Double.toString(a));
        ps.setInt(2, id);
        ps.execute();
        return true;
    }

    public static boolean AddAmount(int id, String amount) throws Exception {
        Double a = Double.parseDouble(db.get.getTableData("SELECT `amount` FROM `stores_medicines` WHERE `id`='" + id + "'").getValueAt(0, 0).toString()) + Double.parseDouble(amount);
        PreparedStatement ps = db.get.Prepare("UPDATE `stores_medicines` SET `amount`=? WHERE `id`=?");
        ps.setString(1, Double.toString(a));
        ps.setInt(2, id);
        ps.execute();
        return true;
    }
}
