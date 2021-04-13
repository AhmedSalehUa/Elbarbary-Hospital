/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.hr.assets;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JTable;
import screens.accounts.assets.AccountTransactions;

/**
 *
 * @author ahmed
 */
public class EmployeeSolfa {

    int id;
    int employee_id;
    String employee;
    int accId;
    String acc;
    String amount;
    String date;

    public EmployeeSolfa(int id, int employee_id, String acc, String amount, String date) {
        this.id = id;
        this.employee_id = employee_id;
        this.acc = acc;
        this.amount = amount;
        this.date = date;
    }

    public EmployeeSolfa() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public int getAccId() {
        return accId;
    }

    public void setAccId(int accId) {
        this.accId = accId;
    }

    public String getAcc() {
        return acc;
    }

    public void setAcc(String acc) {
        this.acc = acc;
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

        PreparedStatement ps = db.get.Prepare("INSERT INTO `att_employee_solfa`(`id`, `emp_id`, `acc_id`, `amount`, `date`) VALUES (?,?,?,?,?)");
        ps.setInt(1, id);
        ps.setInt(2, employee_id);
        ps.setInt(3, accId);
        ps.setString(4, amount);
        ps.setString(5, date);
        AccountTransactions.removeAmountFromAccount(accId, amount);
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        JTable am = db.get.getTableData("SELECT `acc_id`,`amount` FROM `att_employee_solfa` WHERE `id`='" + id + "'");
        AccountTransactions.addAmountToAccount(Integer.parseInt(am.getValueAt(0, 0).toString()), am.getValueAt(0, 1).toString());

        PreparedStatement ps = db.get.Prepare("UPDATE `att_employee_solfa` SET `emp_id`=?,`acc_id`=?,`amount`=?,`date`=? WHERE `id`=?");
        ps.setInt(1, employee_id);
        ps.setInt(2, accId);
        ps.setString(3, amount);
        ps.setString(4, date);
        ps.setInt(5, id);
        AccountTransactions.removeAmountFromAccount(accId, amount);
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        JTable am = db.get.getTableData("SELECT `acc_id`,`amount` FROM `att_employee_solfa` WHERE `id`='" + id + "'");
        AccountTransactions.addAmountToAccount(Integer.parseInt(am.getValueAt(0, 0).toString()), am.getValueAt(0, 1).toString());

        PreparedStatement ps = db.get.Prepare("DELETE FROM `att_employee_solfa` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public static ObservableList<EmployeeSolfa> getData(int id) throws Exception {
        ObservableList<EmployeeSolfa> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `att_employee_solfa`.`id`,`att_employee_solfa`.`emp_id`, `accounts`.`name`, `att_employee_solfa`.`amount`, `att_employee_solfa`.`date` FROM `accounts`,`att_employee_solfa`  WHERE `accounts`.`id`=`att_employee_solfa`.`acc_id` and `att_employee_solfa`.`emp_id`='" + id + "'");
        while (rs.next()) {
            data.add(new EmployeeSolfa(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `att_employee_solfa`").getValueAt(0, 0).toString();
    }
}
