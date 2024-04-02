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
import screens.mainDataScreen.assets.Medicine;

/**
 *
 * @author AHMED
 */
public class Invoice {

    int id;
    int companyId;
    String company;
    String date;
    String total;
    String discoun;
    String discountPercent;
    String totalAfterDiscount;
    String notes;
    ObservableList<InvoiceTable> invoiceData;

    public Invoice() {
    }

    public Invoice(int id, String company, String date, String total, String discoun, String discountPercent, String totalAfterDiscount, String notes) {
        this.id = id;
        this.company = company;
        this.date = date;
        this.total = total;
        this.discoun = discoun;
        this.discountPercent = discountPercent;
        this.totalAfterDiscount = totalAfterDiscount;
        this.notes = notes;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ObservableList<InvoiceTable> getInvoiceData() {
        return invoiceData;
    }

    public void setinvoiceData(ObservableList<InvoiceTable> invoiceData) {
        this.invoiceData = invoiceData;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getDiscoun() {
        return discoun;
    }

    public void setDiscoun(String discoun) {
        this.discoun = discoun;
    }

    public String getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(String discountPercent) {
        this.discountPercent = discountPercent;
    }

    public String getTotalAfterDiscount() {
        return totalAfterDiscount;
    }

    public void setTotalAfterDiscount(String totalAfterDiscount) {
        this.totalAfterDiscount = totalAfterDiscount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean Add() throws Exception {
        // add INVOICE
        PreparedStatement ps = db.get.Prepare("INSERT INTO `medicine_invoices`(`id`, `company_id`, `date`, `total`, `discount`, `discount_percent`, `total_after_descount`, `notes`) VALUES (?,?,?,?,?,?,?,?)");
        ps.setInt(1, id);
        ps.setInt(2, companyId);
        ps.setString(3, date);
        ps.setString(4, total);
        ps.setString(5, discoun);
        ps.setString(6, discountPercent);
        ps.setString(7, totalAfterDiscount);
        ps.setString(8, notes);
        ps.execute();

        //add INVOICE DEAILS
        AddDetails();

        //add COMPANY ACCOUNT
        AddAccount();

        return true;
    }

    public boolean AddDetails() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `medicine_invoices_details`(`invoice_id`, `medicine_id`, `amount`, `cost`) VALUES (?,?,?,?)");

        for (InvoiceTable a : invoiceData) {
            ps.setInt(1, id); 
            Medicine b = ((Medicine) a.getMedicine().getItems().get(a.getMedicine().getSelectionModel().getSelectedIndex()));
            ps.setInt(2, b.getId());
            ps.setString(3, a.getAmount().getText());
            ps.setString(4, a.getCost().getText());
            ps.addBatch();
        }
        ps.executeBatch();
        return true;
    }

    public boolean AddAccount() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `medicine_company_accounts`(`company_id`, `invoice_id`, `date`, `type`, `amount`) VALUES (?,?,?,?,?)");
        ps.setInt(1, companyId);
        ps.setInt(2, id);
        ps.setString(3, date);
        ps.setString(4, "مستحق للشركة");
        ps.setString(5, totalAfterDiscount);
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        //edite INVOICE
        PreparedStatement ps = db.get.Prepare("UPDATE `medicine_invoices` SET `company_id`=?,`date`=?,`total`=?,`discount`=?, `discount_percent`=?,`total_after_descount`=?,`notes`=? WHERE `id`=?");
        ps.setInt(8, id);
        ps.setInt(1, companyId);
        ps.setString(2, date);
        ps.setString(3, total);
        ps.setString(4, discoun);
        ps.setString(5, discountPercent);
        ps.setString(6, totalAfterDiscount);
        ps.setString(7, notes);
        
        //edite ACCOUNT
        EditeAccount();
        
        //delete DETAILS
        DeleteDetails();
        
        //add new DETAILS
        AddDetails();
        ps.execute();
        return true;
    }

    public boolean DeleteDetails() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `medicine_invoices_details` WHERE `invoice_id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public boolean EditeAccount() throws Exception {
        PreparedStatement ps = db.get.Prepare("UPDATE `medicine_company_accounts` SET `date`=?,`type`=?,`amount`=? WHERE `company_id`=? AND `invoice_id`=? AND `type`='مستحق للشركة'");
        ps.setInt(4, companyId);
        ps.setInt(5, id);
        ps.setString(1, date);
        ps.setString(2, "مستحق للشركة");
        ps.setString(3, totalAfterDiscount);
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `medicine_company_accounts` WHERE `invoice_id`=?");
        ps.setInt(1, id);
        ps.execute();
        ps = db.get.Prepare("DELETE FROM `medicine_invoices_details` WHERE `invoice_id`=?");
        ps.setInt(1, id);
        ps.execute();
        ps = db.get.Prepare("DELETE FROM `medicine_invoices` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }
 

    public static ObservableList<Invoice> getData() throws Exception {
        ObservableList<Invoice> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `medicine_invoices`.`id`, `medicine_company`.`name`, `medicine_invoices`.`date`, `medicine_invoices`.`total`, `medicine_invoices`.`discount`, `medicine_invoices`.`discount_percent`, `medicine_invoices`.`total_after_descount`, `medicine_invoices`.`notes` FROM  `medicine_company`, `medicine_invoices` WHERE `medicine_invoices`.`company_id`= `medicine_company`.`id`");
        while (rs.next()) {
            data.add(new Invoice(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
        }
        return data;
    }public static ObservableList<Invoice> getData(int compantId) throws Exception {
        ObservableList<Invoice> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `medicine_invoices`.`id`, `medicine_company`.`name`, `medicine_invoices`.`date`, `medicine_invoices`.`total`, `medicine_invoices`.`discount`, `medicine_invoices`.`discount_percent`, `medicine_invoices`.`total_after_descount`, `medicine_invoices`.`notes` FROM  `medicine_company`, `medicine_invoices` WHERE `medicine_invoices`.`company_id`= `medicine_company`.`id` AND `medicine_invoices`.`company_id`='"+compantId+"'");
        while (rs.next()) {
            data.add(new Invoice(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
        }
        return data;
    }

    
    public static ObservableList<Invoice> getCutomData(String sql) throws Exception {
        ObservableList<Invoice> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery(sql);
        while (rs.next()) {
            data.add(new Invoice(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
        }
        return data;
    }

      public static ObservableList<Invoice> getDataNotInStores() throws Exception {
        ObservableList<Invoice> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `medicine_invoices`.`id`, `medicine_company`.`name`, `medicine_invoices`.`date`, `medicine_invoices`.`total`, `medicine_invoices`.`discount`, `medicine_invoices`.`discount_percent`, `medicine_invoices`.`total_after_descount`, `medicine_invoices`.`notes` FROM  `medicine_company`, `medicine_invoices` "
                + "WHERE `medicine_invoices`.`company_id`= `medicine_company`.`id` AND `medicine_invoices`.`id` NOT IN (SELECT `invoice_id` FROM `stores_medicines`)");
        while (rs.next()) {
            data.add(new Invoice(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
        }
        return data;
    }
    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `medicine_invoices`").getValueAt(0, 0).toString();
    }
}
