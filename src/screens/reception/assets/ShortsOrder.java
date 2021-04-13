/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.reception.assets;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author AHMED
 */
public class ShortsOrder {

    int id;
    int store_id;
    int prodct_id;
    String product;
    String amount;
    int user_id;
    String statue;

    public ShortsOrder() {
    }

    public ShortsOrder(int id, int store_id, String product, String amount, int user_id, String statue, int prodct_id) {
        this.id = id;
        this.store_id = store_id;
        this.product = product;
        this.prodct_id = prodct_id;
        this.amount = amount;
        this.user_id = user_id;
        this.statue = statue;
    }

    public ShortsOrder(int id, int store_id, int prodct_id, String amount, int user_id, String statue) {
        this.id = id;
        this.store_id = store_id;
        this.prodct_id = prodct_id;
        this.amount = amount;
        this.user_id = user_id;
        this.statue = statue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public int getProdct_id() {
        return prodct_id;
    }

    public void setProdct_id(int prodct_id) {
        this.prodct_id = prodct_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getStatue() {
        return statue;
    }

    public void setStatue(String statue) {
        this.statue = statue;
    }

    public boolean Add() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `store_short_order`(`store_id`, `product_id`, `amount`, `user_id`, `statue`) VALUES (?,?,?,?,?)");
        ps.setInt(1, store_id);
        ps.setInt(2, prodct_id);
        ps.setString(3, amount);
        ps.setInt(4, user_id);
        ps.setString(5, statue);
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `store_short_order` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public static ObservableList<ShortsOrder> getData() throws Exception {
        ObservableList<ShortsOrder> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `store_short_order`.`id`, `store_short_order`.`store_id`, `medicine`.`name`, `store_short_order`.`amount`, `store_short_order`.`user_id`, `store_short_order`.`statue`,`store_short_order`.`product_id` FROM `medicine`, `store_short_order` WHERE `store_short_order`.`product_id`=`medicine`.`id`");
        while (rs.next()) {
            data.add(new ShortsOrder(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6), rs.getInt(7)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `store_short_order`").getValueAt(0, 0).toString();
    }

    public static boolean update(int id, String statue) throws Exception {
        PreparedStatement ps = db.get.Prepare(" UPDATE `store_short_order` SET `statue`=? WHERE `id`=?");
        ps.setString(1, statue);
        ps.setInt(2, id);
        ps.execute();
        return true;

    }
}
