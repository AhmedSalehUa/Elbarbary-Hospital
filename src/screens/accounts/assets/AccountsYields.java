/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.accounts.assets;

import assets.classes.AlertDialogs;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JTable;
import screens.accounts.assets.AccountTransactions;

/**
 *
 * @author AHMED
 */
public class AccountsYields {

    int id;
    int cat_id;
    String cat;
    int acc_id;
    int user_id;
    String user;
    String amount;
    String date;

    public AccountsYields() {
    }

    public AccountsYields(int id, int admission_id, int acc_id, int user_id, String amount, String date) {
        this.id = id;
        this.cat_id = admission_id;
        this.acc_id = acc_id;
        this.user_id = user_id;
        this.amount = amount;
        this.date = date;
    }

    public AccountsYields(int id, int admission_id, int acc_id, String user, String amount, String date) {
        this.id = id;
        this.cat_id = admission_id;
        this.acc_id = acc_id;
        this.user = user;
        this.amount = amount;
        this.date = date;
    }

    public AccountsYields(int id, String cat, int acc_id, String user, String amount, String date) {
        this.id = id;
        this.cat = cat;
        this.acc_id = acc_id;
        this.user = user;
        this.amount = amount;
        this.date = date;
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

    public int getAcc_id() {
        return acc_id;
    }

    public void setAcc_id(int acc_id) {
        this.acc_id = acc_id;
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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

    public boolean Add() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `yields_accounts`(`id`, `cat_id`, `acc_id`, `user_id`, `date`, `amount`) VALUES (?,?,?,?,?,?)");
        ps.setInt(1, id);
        ps.setInt(2, cat_id);
        ps.setInt(3, acc_id);
        ps.setInt(4, user_id);
        ps.setString(5, date);
        ps.setString(6, amount);
        ps.execute();
        //add amount to account
        //markadmission as payied

        AccountTransactions.addAmountToAccount(acc_id, amount);

        return true;
    }

    public boolean Edite() throws Exception {
        //remove amount from account
        //markadmission as Unpayied
        JTable tab = db.get.getTableData("SELECT  `cat_id`, `acc_id`, `amount` FROM `yields_accounts` WHERE `id`='" + this.id + "'");
        try {
            AccountTransactions.removeAmountFromAccount(Integer.parseInt(tab.getValueAt(0, 1).toString()), tab.getValueAt(0, 2).toString());
        } catch (Exception ex) {
            AlertDialogs.showError("الحساب لا يكفي لخصم المبلغ");
            return false;
        }

        PreparedStatement ps = db.get.Prepare("UPDATE `yields_accounts` SET `cat_id`=?,`acc_id`=?,`user_id`=?,`date`=?,`amount`=? WHERE `id`=?");
        ps.setInt(5, id);
        ps.setInt(1, cat_id);
        ps.setInt(2, acc_id);
        ps.setInt(3, user_id);
        ps.setString(4, date);
        ps.setString(5, amount);
        ps.execute();
        //add amount to account
        //markadmission as payied
        AccountTransactions.addAmountToAccount(acc_id, amount);

        return true;
    }

    public boolean Delete() throws Exception {
        //remove amount from account
        //markadmission as Unpayied
        JTable tab = db.get.getTableData("SELECT  `cat_id`, `acc_id`, `amount` FROM `yields_accounts` WHERE `id`='" + this.id + "'");
        try {
            AccountTransactions.removeAmountFromAccount(Integer.parseInt(tab.getValueAt(0, 1).toString()), tab.getValueAt(0, 2).toString());
        } catch (Exception ex) {
            AlertDialogs.showError("الحساب لا يكفي لخصم المبلغ");
            return false;
        }

        PreparedStatement ps = db.get.Prepare("DELETE FROM `yields_accounts` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public static ObservableList<AccountsYields> getData() throws Exception {
        ObservableList<AccountsYields> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `yields_accounts`.`id`, `yields_cat`.`name`, `yields_accounts`.`acc_id`, `users`.`user_name`, `yields_accounts`.`date`, `yields_accounts`.`amount` FROM `yields_accounts`,`users`,`yields_cat` WHERE `users`.`id`=`yields_accounts`.`user_id` and `yields_accounts`.`cat_id`=`yields_cat`.`id`");
        while (rs.next()) {
            data.add(new AccountsYields(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(6), rs.getString(5)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `yields_accounts`").getValueAt(0, 0).toString();
    }

}
