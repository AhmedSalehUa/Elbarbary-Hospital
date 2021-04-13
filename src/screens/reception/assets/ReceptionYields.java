/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.reception.assets;

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
public class ReceptionYields {

    int id;
    int admission_id;
    int acc_id;
    int user_id;
    String user;
    String amount;
    String date;

    public ReceptionYields() {
    }

    public ReceptionYields(int id, int admission_id, int acc_id, int user_id, String amount, String date) {
        this.id = id;
        this.admission_id = admission_id;
        this.acc_id = acc_id;
        this.user_id = user_id;
        this.amount = amount;
        this.date = date;
    }

    public ReceptionYields(int id, int admission_id, int acc_id, String user, String amount, String date) {
        this.id = id;
        this.admission_id = admission_id;
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

    public int getAdmission_id() {
        return admission_id;
    }

    public void setAdmission_id(int admission_id) {
        this.admission_id = admission_id;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean Add() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `yields`(`id`, `admission_id`, `acc_id`, `user_id`, `date`, `amount`) VALUES (?,?,?,?,?,?)");
        ps.setInt(1, id);
        ps.setInt(2, admission_id);
        ps.setInt(3, acc_id);
        ps.setInt(4, user_id);
        ps.setString(5, date);
        ps.setString(6, amount);
        ps.execute();
        //add amount to account
        //markadmission as payied

        AccountTransactions.addAmountToAccount(acc_id, amount);
        MarkAsPaied();
        return true;
    }

    public boolean Edite() throws Exception {
        //remove amount from account
        //markadmission as Unpayied
        JTable tab = db.get.getTableData("SELECT  `admission_id`, `acc_id`, `amount` FROM `yields` WHERE `id`='" + this.id + "'");
        try {
            AccountTransactions.removeAmountFromAccount(Integer.parseInt(tab.getValueAt(0, 1).toString()), tab.getValueAt(0, 2).toString());
        } catch (Exception ex) {
            AlertDialogs.showError("الحساب لا يكفي لخصم المبلغ");
            return false;
        }
        UnMarkAsPaied();

        PreparedStatement ps = db.get.Prepare("UPDATE `yields` SET `admission_id`=?,`acc_id`=?,`user_id`=?,`date`=?,`amount`=? WHERE `id`=?");
        ps.setInt(5, id);
        ps.setInt(1, admission_id);
        ps.setInt(2, acc_id);
        ps.setInt(3, user_id);
        ps.setString(4, date);
        ps.setString(5, amount);
        ps.execute();
        //add amount to account
        //markadmission as payied
        AccountTransactions.addAmountToAccount(acc_id, amount);
        MarkAsPaied();

        return true;
    }

    public boolean Delete() throws Exception {
        //remove amount from account
        //markadmission as Unpayied
        JTable tab = db.get.getTableData("SELECT  `admission_id`, `acc_id`, `amount` FROM `yields` WHERE `id`='" + this.id + "'");
        try {
            AccountTransactions.removeAmountFromAccount(Integer.parseInt(tab.getValueAt(0, 1).toString()), tab.getValueAt(0, 2).toString());
        } catch (Exception ex) {
            AlertDialogs.showError("الحساب لا يكفي لخصم المبلغ");
            return false;
        }
        UnMarkAsPaied();
        PreparedStatement ps = db.get.Prepare("DELETE FROM `yields` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public static ObservableList<ReceptionYields> getData() throws Exception {
        ObservableList<ReceptionYields> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `yields`.`id`, `yields`.`admission_id`, `yields`.`acc_id`, `users`.`user_name`, `yields`.`date`, `yields`.`amount` FROM `yields`,`users` WHERE `users`.`id`=`yields`.`user_id` and `acc_id` in (SELECT `value` FROM `static_values` where `attribute` = 'RECEPTION_ACC_ID' )");
        while (rs.next()) {
            data.add(new ReceptionYields(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(6), rs.getString(5)));
        }
        return data;
    }

    public static ObservableList<ReceptionYields> getDataAccounts() throws Exception {
        ObservableList<ReceptionYields> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `yields`.`id`, `yields`.`admission_id`, `yields`.`acc_id`, `users`.`user_name`, `yields`.`date`, `yields`.`amount` FROM `yields`,`users` WHERE `users`.`id`=`yields`.`user_id`");
        while (rs.next()) {
            data.add(new ReceptionYields(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(6), rs.getString(5)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `yields`").getValueAt(0, 0).toString();
    }

    public boolean MarkAsPaied() throws Exception {
        PreparedStatement ps = db.get.Prepare(" INSERT INTO `admission_paied`(`admission_id`, `yields_id`) VALUES (?,?)");
        ps.setInt(1, admission_id);
        ps.setInt(2, id);
        ps.execute();
        return true;

    }

    public boolean UnMarkAsPaied() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `admission_paied` WHERE `yields_id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;

    }

    public static boolean UnMarkAsPaied(int admissionId) throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `admission_paied` WHERE `admission_id`=?");
        ps.setInt(1, admissionId);
        ps.execute();
        return true;

    }

    public static boolean rollBack(int admissionID) throws Exception {
        if (db.get.getTableData("SELECT `admission_id`, `yields_id` FROM `admission_paied` WHERE `admission_id`='"+admissionID+"'").getRowCount() == 0) {
            return true;
        } else {
            JTable tab = db.get.getTableData("SELECT  `admission_id`, `acc_id`, `amount` FROM `yields` WHERE `admission_id`='" + admissionID + "'");
            try {
                AccountTransactions.removeAmountFromAccount(Integer.parseInt(tab.getValueAt(0, 1).toString()), tab.getValueAt(0, 2).toString());
            } catch (Exception ex) {
                AlertDialogs.showError("الحساب لا يكفي لخصم المبلغ");
                return false;
            }
            PreparedStatement ps = db.get.Prepare("DELETE FROM `admission_paied` WHERE `admission_id`=?");
            ps.setInt(1, admissionID);
            ps.execute();

            PreparedStatement pss = db.get.Prepare("DELETE FROM `yields` WHERE `admission_id`=?");
            pss.setInt(1, admissionID);
            pss.execute();
            return true;
        }
    }
}
