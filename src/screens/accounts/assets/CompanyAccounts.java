/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.accounts.assets;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JTable;

/**
 *
 * @author AHMED
 */
public class CompanyAccounts {

    int id;
    int companyId;
    int invoiceid;
    String date;
    String type;
    String amount;
    int acc_id;
    int userId;

    public CompanyAccounts() {
    }

    public CompanyAccounts(int id, int companyId, int invoiceid, String date, String type, String amount, int acc_id, int userId) {
        this.id = id;
        this.companyId = companyId;
        this.invoiceid = invoiceid;
        this.date = date;
        this.type = type;
        this.amount = amount;
        this.acc_id = acc_id;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getInvoiceid() {
        return invoiceid;
    }

    public void setInvoiceid(int invoiceid) {
        this.invoiceid = invoiceid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getAcc_id() {
        return acc_id;
    }

    public void setAcc_id(int acc_id) {
        this.acc_id = acc_id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean Add() throws Exception {
        AccountTransactions.removeAmountFromAccount(acc_id, amount);
        PreparedStatement ps = db.get.Prepare("INSERT INTO `medicine_company_accounts`(`company_id`, `invoice_id`, `date`, `type`, `amount`, `acc_id`, `user_id`) VALUES (?,?,?,?,?,?,?)");
        ps.setInt(1, companyId);
        ps.setInt(2, invoiceid);
        ps.setString(3, date);
        ps.setString(4, type);
        ps.setString(5, amount);
        ps.setInt(6, acc_id);
        ps.setInt(7, userId);
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        JTable tab = db.get.getTableData("SELECT `amount`, `acc_id` FROM `medicine_company_accounts` WHERE `id`='" + id + "'");
        AccountTransactions.addAmountToAccount(Integer.parseInt(tab.getValueAt(0, 1).toString()), tab.getValueAt(0, 0).toString());
        PreparedStatement ps = db.get.Prepare("UPDATE `medicine_company_accounts` SET `company_id`=?,`invoice_id`=?,`date`=?,`type`=?,`amount`=?,`acc_id`=?,`user_id`=? WHERE `id`=?");
        ps.setInt(1, companyId);
        ps.setInt(2, invoiceid);
        ps.setString(3, date);
        ps.setString(4, type);
        ps.setString(5, amount);
        ps.setInt(6, acc_id);
        ps.setInt(7, userId);
        ps.setInt(8, id);
        AccountTransactions.removeAmountFromAccount(acc_id, amount);
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        JTable tab = db.get.getTableData("SELECT `amount`, `acc_id` FROM `medicine_company_accounts` WHERE `id`='" + id + "'");
        AccountTransactions.addAmountToAccount(Integer.parseInt(tab.getValueAt(0, 1).toString()), tab.getValueAt(0, 0).toString());
        PreparedStatement ps = db.get.Prepare("DELETE FROM `medicine_company_accounts` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public static ObservableList<CompanyAccounts> getData(int comId) throws Exception {
        ObservableList<CompanyAccounts> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT * FROM `medicine_company_accounts` WHERE `company_id`='" + comId + "'");
        while (rs.next()) {
            data.add(new CompanyAccounts(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `medicine_company_accounts`").getValueAt(0, 0).toString();
    }
}
