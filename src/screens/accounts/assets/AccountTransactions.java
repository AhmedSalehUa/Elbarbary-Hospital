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

/**
 *
 * @author AHMED
 */
public class AccountTransactions {

    Preferences prefs;
    int id;
    int acc_from;
    String acc_from_name;
    int acc_to;
    String acc_to_name;
    String date;
    String amount;
    int user_Id;
    String username;

    public AccountTransactions() {
    }

    public AccountTransactions(int id, int acc_from, int acc_to, String date, String amount, int user_Id) {
        this.id = id;
        this.acc_from = acc_from;
        this.acc_to = acc_to;
        this.date = date;
        this.amount = amount;
        this.user_Id = user_Id;
    }

    public AccountTransactions(int id, String acc_from_name, String acc_to_name, String date, String amount, String username) {
        this.id = id;
        this.acc_from_name = acc_from_name;
        this.acc_to_name = acc_to_name;
        this.date = date;
        this.amount = amount;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAcc_from() {
        return acc_from;
    }

    public void setAcc_from(int acc_from) {
        this.acc_from = acc_from;
    }

    public int getAcc_to() {
        return acc_to;
    }

    public void setAcc_to(int acc_to) {
        this.acc_to = acc_to;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(int user_Id) {
        this.user_Id = user_Id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAcc_from_name() {
        return acc_from_name;
    }

    public void setAcc_from_name(String acc_from_name) {
        this.acc_from_name = acc_from_name;
    }

    public String getAcc_to_name() {
        return acc_to_name;
    }

    public void setAcc_to_name(String acc_to_name) {
        this.acc_to_name = acc_to_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean Add() throws Exception {
        prefs = Preferences.userNodeForPackage(ElBarbaryHospital.class);
        PreparedStatement ps = db.get.Prepare("INSERT INTO `acc_transactions`(`id`, `acc_id_from`, `acc_id_to`, `amount`, `date`, `user_id`) VALUES (?,?,?,?,?,?)");
        ps.setInt(1, id);
        ps.setInt(2, acc_from);
        ps.setInt(3, acc_to);
        ps.setString(4, amount);
        ps.setString(5, date);
        ps.setInt(6, prefs.getInt(USER_ID, 0));
        addAmountToAccount(acc_to, amount);
        removeAmountFromAccount(acc_from, amount);
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        prefs = Preferences.userNodeForPackage(ElBarbaryHospital.class); 
        JTable tab = db.get.getTableData("SELECT `acc_id_from`, `acc_id_to`, `amount` FROM `acc_transactions` WHERE `id`='" + id + "'");
        addAmountToAccount(Integer.parseInt(tab.getValueAt(0, 0).toString()), tab.getValueAt(0, 2).toString());
        removeAmountFromAccount(Integer.parseInt(tab.getValueAt(0, 1).toString()), tab.getValueAt(0, 2).toString());
        PreparedStatement ps = db.get.Prepare("UPDATE `acc_transactions` SET `acc_id_from`=?,`acc_id_to`=?,`amount`=?,`date`=?,`user_id`=? WHERE `id`=?");
        ps.setInt(6, id);
        ps.setInt(1, acc_from);
        ps.setInt(2, acc_to);
        ps.setString(3, amount);
        ps.setString(4, date);
        ps.setInt(5, prefs.getInt(USER_ID, 0));
        addAmountToAccount(acc_to, amount);
        removeAmountFromAccount(acc_from, amount);
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        JTable tab = db.get.getTableData("SELECT `acc_id_from`, `acc_id_to`, `amount` FROM `acc_transactions` WHERE `id`='" + id + "'");
        addAmountToAccount(Integer.parseInt(tab.getValueAt(0, 0).toString()), tab.getValueAt(0, 2).toString());
        removeAmountFromAccount(Integer.parseInt(tab.getValueAt(0, 1).toString()), tab.getValueAt(0, 2).toString());
        PreparedStatement ps = db.get.Prepare("DELETE FROM `acc_transactions` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public static ObservableList<AccountTransactions> getData() throws Exception {
        ObservableList<AccountTransactions> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT c.id, p1.name AS 'from', p2.name AS 'to', c.date, c.amount, p3.user_name AS 'user' FROM acc_transactions c JOIN accounts p1 ON p1.id = c.acc_id_from JOIN accounts p2 ON p2.id = c.acc_id_to JOIN users p3 ON p3.id = c.user_id");
        while (rs.next()) {
            data.add(new AccountTransactions(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getInt(6)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `acc_transactions`").getValueAt(0, 0).toString();
    }

    public static boolean addAmountToAccount(int id, String amount) throws Exception {
        Double a = Double.parseDouble(db.get.getTableData("select credit from accounts where id='" + id + "'").getValueAt(0, 0).toString()) + Double.parseDouble(amount);
        PreparedStatement ps = db.get.Prepare("UPDATE `accounts` SET `credit`=? WHERE `id`=?");
        ps.setString(1, Double.toString(a));
        ps.setInt(2, id); 
        ps.execute();
        return true;
    }

    public static boolean removeAmountFromAccount(int id, String amount) throws Exception {
        Double a = Double.parseDouble(db.get.getTableData("select credit from accounts where id='" + id + "'").getValueAt(0, 0).toString()) - Double.parseDouble(amount);
        if (a < 0) {
            throw new Exception("الحساب لا يكفي لخصم المبلغ");
        } else {
            PreparedStatement ps = db.get.Prepare("UPDATE `accounts` SET `credit`=? WHERE `id`=?");
            ps.setString(1, Double.toString(a));
            ps.setInt(2, id); 
            ps.execute();
            return true;
        }
    }
}
