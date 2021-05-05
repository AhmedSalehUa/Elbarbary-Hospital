package screens.drugs.assets;

import static assets.classes.statics.DRUGS_BRANCH_ACCOUNT_ID;
import db.get;
import elbarbary.hospital.ElBarbaryHospital;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.prefs.Preferences;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JTable;
import screens.accounts.assets.AccountTransactions;

public class DrugsPatientExpenses {

    int id;
    int patient_id;
    int acc_id;
    int cat_id;
    String cat_name;
    String amount;
    String date;
    String notes;

    public DrugsPatientExpenses() {
    }

    public DrugsPatientExpenses(int id, String cat_name, String amount, String date, String notes) {
        this.id = id;
        this.cat_name = cat_name;
        this.amount = amount;
        this.date = date;
        this.notes = notes;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatient_id() {
        return this.patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public int getAcc_id() {
        return this.acc_id;
    }

    public void setAcc_id(int acc_id) {
        this.acc_id = acc_id;
    }

    public int getCat_id() {
        return this.cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public String getCat_name() {
        return this.cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getAmount() {
        return this.amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean Add() throws Exception {
        Preferences prefs = Preferences.userNodeForPackage(ElBarbaryHospital.class);

        DrugsAccounts.addToTotalSpended(this.acc_id, this.amount);
        DrugsAccounts.removeFromRemaining(this.acc_id, this.amount);
        AccountTransactions.removeAmountFromAccount(Integer.parseInt(prefs.get(DRUGS_BRANCH_ACCOUNT_ID, "4")), this.amount);

        PreparedStatement ps = get.Prepare("INSERT INTO `drg_patient_expenses`(`id`, `patient_id`, `acc_id`, `cat_id`, `amount`, `date`,`notes`) VALUES (?,?,?,?,?,?,?)");
        ps.setInt(1, this.id);
        ps.setInt(2, this.patient_id);
        ps.setInt(3, this.acc_id);
        ps.setInt(4, this.cat_id);
        ps.setString(5, this.amount);
        ps.setString(6, this.date);
        ps.setString(7, this.notes);
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        Preferences prefs = Preferences.userNodeForPackage(ElBarbaryHospital.class);
        JTable am = get.getTableData("SELECT `acc_id`,`amount` FROM `drg_patient_expenses` WHERE `id`='" + this.id + "'");
        DrugsAccounts.removeFromTotalSpended(Integer.parseInt(am.getValueAt(0, 0).toString()), am.getValueAt(0, 1).toString());
        DrugsAccounts.addToRemaining(Integer.parseInt(am.getValueAt(0, 0).toString()), am.getValueAt(0, 1).toString());
        AccountTransactions.addAmountToAccount(Integer.parseInt(prefs.get(DRUGS_BRANCH_ACCOUNT_ID, "4")), am.getValueAt(0, 1).toString());

        PreparedStatement ps = get.Prepare("UPDATE `drg_patient_expenses` SET `patient_id`=?,`acc_id`=?,`cat_id`=?,`amount`=?,`date`=?,`notes`=? WHERE `id`=?");
        ps.setInt(7, this.id);
        ps.setInt(1, this.patient_id);
        ps.setInt(2, this.acc_id);
        ps.setInt(3, this.cat_id);
        ps.setString(4, this.amount);
        ps.setString(5, this.date);
        ps.setString(6, this.notes);
        DrugsAccounts.removeFromRemaining(this.acc_id, this.amount);
        DrugsAccounts.addToTotalSpended(this.acc_id, this.amount);
        AccountTransactions.removeAmountFromAccount(Integer.parseInt(prefs.get(DRUGS_BRANCH_ACCOUNT_ID, "4")), this.amount);

        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        Preferences prefs = Preferences.userNodeForPackage(ElBarbaryHospital.class);
        JTable am = get.getTableData("SELECT `acc_id`,`amount` FROM `drg_patient_expenses` WHERE `id`='" + this.id + "'");
        DrugsAccounts.removeFromTotalSpended(Integer.parseInt(am.getValueAt(0, 0).toString()), am.getValueAt(0, 1).toString());
        DrugsAccounts.addToRemaining(Integer.parseInt(am.getValueAt(0, 0).toString()), am.getValueAt(0, 1).toString());
        AccountTransactions.addAmountToAccount(Integer.parseInt(prefs.get(DRUGS_BRANCH_ACCOUNT_ID, "4")), am.getValueAt(0, 1).toString());

        PreparedStatement ps = get.Prepare("DELETE FROM `drg_patient_expenses` WHERE `id`=?");
        ps.setInt(1, this.id);
        ps.execute();
        return true;
    }

    public static ObservableList<DrugsPatientExpenses> getData(int patient_id) throws Exception {
        ObservableList<DrugsPatientExpenses> data = FXCollections.observableArrayList();
        ResultSet rs = get.getReportCon().createStatement().executeQuery("SELECT `drg_patient_expenses`.`id`,`drg_expense_cat`.`name` ,  `drg_patient_expenses`.`amount`, `drg_patient_expenses`.`date`,`drg_patient_expenses`.`notes` FROM `drg_patient_expenses`,`drg_expense_cat` WHERE `drg_patient_expenses`.`cat_id`=`drg_expense_cat`.`id` and `drg_patient_expenses`.`patient_id`='" + patient_id + "'");
        while (rs.next()) {
            data.add(new DrugsPatientExpenses(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `drg_patient_expenses`").getValueAt(0, 0).toString();
    }
}
