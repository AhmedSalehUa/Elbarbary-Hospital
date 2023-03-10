/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.drugs.assets;

import db.get;
import elbarbary.hospital.ElBarbaryHospital;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.prefs.Preferences;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JTable;
import screens.accounts.assets.AccountTransactions;

/**
 *
 * @author AHMED
 */
public class DrugsMoneyTransactions {

    int id;
    String amount;
    String date;
    int from_acc_id;
    String from_acc;
    int to_acc_id;
    String to_acc;
    int user_id;
    String user;

    public DrugsMoneyTransactions() {
    }

    public DrugsMoneyTransactions(int id, String amount, String date, String from_acc, String to_acc, String user) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.from_acc = from_acc;
        this.to_acc = to_acc;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getFrom_acc_id() {
        return from_acc_id;
    }

    public void setFrom_acc_id(int from_acc_id) {
        this.from_acc_id = from_acc_id;
    }

    public String getFrom_acc() {
        return from_acc;
    }

    public void setFrom_acc(String from_acc) {
        this.from_acc = from_acc;
    }

    public int getTo_acc_id() {
        return to_acc_id;
    }

    public void setTo_acc_id(int to_acc_id) {
        this.to_acc_id = to_acc_id;
    }

    public String getTo_acc() {
        return to_acc;
    }

    public void setTo_acc(String to_acc) {
        this.to_acc = to_acc;
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

    public boolean Add() throws Exception {
        Preferences prefs = Preferences.userNodeForPackage(ElBarbaryHospital.class);
        AccountTransactions.addAmountToAccount(to_acc_id, amount);
        AccountTransactions.removeAmountFromAccount(from_acc_id, amount);
        PreparedStatement ps = db.get.Prepare("INSERT INTO `drg_accounts_transactions`(`id`, `acc_id_from`, `acc_id_to`, `user_id`, `amount`, `date`) VALUES (?,?,?,?,?,?)");
        ps.setInt(1, id);
        ps.setInt(2, from_acc_id);
        ps.setInt(3, to_acc_id);
        ps.setInt(4, user_id);
        ps.setString(5, amount);
        ps.setString(6, date);
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        Preferences prefs = Preferences.userNodeForPackage(ElBarbaryHospital.class);
        JTable am = get.getTableData("SELECT `acc_id_from`,`acc_id_to`,`amount` FROM `drg_accounts_transactions` WHERE `id`='" + this.id + "'");
        AccountTransactions.removeAmountFromAccount(Integer.parseInt(am.getValueAt(0, 1).toString()), am.getValueAt(0, 2).toString());
        AccountTransactions.addAmountToAccount(Integer.parseInt(am.getValueAt(0, 0).toString()), am.getValueAt(0, 2).toString());

        PreparedStatement ps = db.get.Prepare("UPDATE `drg_accounts_transactions` SET `acc_id_from`=?,`acc_id_to`=?,`user_id`=?,`amount`=?,`date`=? WHERE `id`=?");
        ps.setInt(6, id);
        ps.setInt(1, from_acc_id);
        ps.setInt(2, to_acc_id);
        ps.setInt(3, user_id);
        ps.setString(4, amount);
        ps.setString(5, date);
        AccountTransactions.removeAmountFromAccount(from_acc_id, amount);
        AccountTransactions.addAmountToAccount(to_acc_id, amount);
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        Preferences prefs = Preferences.userNodeForPackage(ElBarbaryHospital.class);
        JTable am = get.getTableData("SELECT `acc_id_from`,`acc_id_to`,`amount` FROM `drg_accounts_transactions` WHERE `id`='" + this.id + "'");
        AccountTransactions.removeAmountFromAccount(Integer.parseInt(am.getValueAt(0, 1).toString()), am.getValueAt(0, 2).toString());
        AccountTransactions.addAmountToAccount(Integer.parseInt(am.getValueAt(0, 0).toString()), am.getValueAt(0, 2).toString());

        PreparedStatement ps = db.get.Prepare("DELETE FROM `drg_accounts_transactions` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public static ObservableList<DrugsMoneyTransactions> getData() throws Exception {
        ObservableList<DrugsMoneyTransactions> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT c.id,c.amount,c.date, p1.name AS 'from', p2.name AS 'to', p3.user_name FROM drg_accounts_transactions c JOIN accounts p1 ON p1.id = c.acc_id_from JOIN accounts p2 ON p2.id = c.acc_id_to JOIN users p3 ON p3.id = c.user_id");
        while (rs.next()) {
            data.add(new DrugsMoneyTransactions(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `drg_accounts_transactions`").getValueAt(0, 0).toString();
    }
}
