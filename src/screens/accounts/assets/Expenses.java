/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.accounts.assets;

import static assets.classes.statics.USER_ID;
import elbarbary.hospital.ElBarbaryHospital;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.prefs.Preferences;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JTable;

public class Expenses {

    Preferences prefs;

    int id;

    int acc_id;
    String acc;

    int cat_id;
    String cat;

    String date;
    String amount;
    int user_id;
    String user;
    String details;

    String notes;

    public Expenses() {
    }

    public Expenses(int id, String acc, String cat, String date, String amount, String user, String details, String notes) {
        this.id = id;
        this.acc = acc;
        this.cat = cat;
        this.date = date;
        this.amount = amount;
        this.user = user;
        this.details = details;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getAcc_id() {
        return acc_id;
    }

    public void setAcc_id(int acc_id) {
        this.acc_id = acc_id;
    }

    public String getAcc() {
        return acc;
    }

    public void setAcc(String acc) {
        this.acc = acc;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean Add() throws Exception {
        prefs = Preferences.userNodeForPackage(ElBarbaryHospital.class);
        PreparedStatement ps = db.get.Prepare("INSERT INTO `expenses`(`cat_id`, `date`, `amount`, `user_id`, `notes`,`acc_id`,`details`) VALUES (?,?,?,?,?,?,?)");
        ps.setInt(1, cat_id);
        ps.setString(2, date);
        ps.setString(3, amount);
        ps.setInt(4, Integer.parseInt(prefs.get(USER_ID, "0")));
        ps.setString(5, notes);
        ps.setInt(6, acc_id);
        ps.setString(7, details);
        removeAmountFromAccount();
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        prefs = Preferences.userNodeForPackage(ElBarbaryHospital.class);
        JTable am = db.get.getTableData("SELECT `acc_id`,`amount` FROM `expenses` WHERE `id`='" + id + "'");
        addAmountToAccount(am.getValueAt(0, 0).toString(), am.getValueAt(0, 1).toString());
        PreparedStatement ps = db.get.Prepare("UPDATE `expenses` SET `cat_id`=?,`date`=?,`amount`=?,`user_id`=?,`details`=?,`notes`=?,`acc_id`=? WHERE `id`=?");
        ps.setInt(1, cat_id);
        ps.setString(2, date);
        ps.setString(3, amount);
        ps.setInt(4, Integer.parseInt(prefs.get(USER_ID, "0")));
        ps.setString(5, details);
        ps.setString(6, notes);
        ps.setInt(7, acc_id);
        ps.setInt(8, id);
        removeAmountFromAccount();
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `expenses` WHERE `id`=?");
        ps.setInt(1, id);
        JTable am = db.get.getTableData("SELECT `acc_id`,`amount` FROM `expenses` WHERE `id`='" + id + "'");
        addAmountToAccount(am.getValueAt(0, 0).toString(), am.getValueAt(0, 1).toString());
        ps.execute();
        return true;
    }

    public static ObservableList<Expenses> getData() throws Exception {
        ObservableList<Expenses> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `expenses`.`id`,`accounts`.`name`,`categorys`.`name`, `expenses`.`date`, `expenses`.`amount`,`users`.`user_name`, `expenses`.`details`, `expenses`.`notes` FROM `expenses`,`users`,`categorys`,`accounts` WHERE `categorys`.`id`=`expenses`.`cat_id` and  `expenses`.`user_id`=`users`.`id` and `accounts`.`id` = `expenses`.`acc_id`");
        while (rs.next()) {
            data.add(new Expenses(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
        }
        return data;
    }

    public static ObservableList<Expenses> getReceptionData() throws Exception {
        ObservableList<Expenses> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `expenses`.`id`,`accounts`.`name`,`categorys`.`name`, `expenses`.`date`, `expenses`.`amount`,`users`.`user_name`, `expenses`.`details`, `expenses`.`notes` FROM `expenses`,`users`,`categorys`,`accounts` WHERE `categorys`.`id`=`expenses`.`cat_id` and  `expenses`.`user_id`=`users`.`id` and `accounts`.`id` = `expenses`.`acc_id` and `expenses`.`cat_id` in(SELECT `cat_id` FROM `reception_cat`)");
        while (rs.next()) {
            data.add(new Expenses(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `expenses`").getValueAt(0, 0).toString();
    }

    public boolean addAmountToAccount(String acc_id, String amount) throws Exception {
        Double a = Double.parseDouble(db.get.getTableData("select credit from accounts where `id`='" + acc_id + "'").getValueAt(0, 0).toString()) + Double.parseDouble(amount);
        PreparedStatement ps = db.get.Prepare("UPDATE `accounts` SET `credit`=? WHERE `id`=?");
        ps.setString(1, Double.toString(a));
        ps.setInt(2, Integer.parseInt(acc_id));
        ps.execute();
        return true;
    }

    public boolean removeAmountFromAccount() throws Exception {
        Double a = Double.parseDouble(db.get.getTableData("select credit from accounts where `id`='" + acc_id + "'").getValueAt(0, 0).toString()) - Double.parseDouble(amount);
        if (a < 0) {
            throw new Exception("الرصيد فالحساب لا يكفي");
        }
        PreparedStatement ps = db.get.Prepare("UPDATE `accounts` SET `credit`=? WHERE `id`=?");
        ps.setString(1, Double.toString(a));
        ps.setInt(2, acc_id);
        ps.execute();
        return true;
    }
}
