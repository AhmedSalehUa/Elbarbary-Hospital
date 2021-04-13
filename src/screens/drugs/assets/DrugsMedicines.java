package screens.drugs.assets;

import db.get;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JTable;
import screens.accounts.assets.AccountTransactions;
import screens.store.assets.StoreProdcts;

public class DrugsMedicines {

    int id;
    int patient_id;
    int acc_id;
    int medicine_id;
    String medicine;
    String amount;
    String cost_of_one;
    String total_cost;

    public DrugsMedicines() {
    }

    public DrugsMedicines(int id, String medicine, String amount, String cost_of_one, String total_cost, int medicine_id) {
        this.id = id;
        this.medicine = medicine;
        this.amount = amount;
        this.cost_of_one = cost_of_one;
        this.total_cost = total_cost;
        this.medicine_id = medicine_id;
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

    public int getMedicine_id() {
        return this.medicine_id;
    }

    public void setMedicine_id(int medicine_id) {
        this.medicine_id = medicine_id;
    }

    public String getMedicine() {
        return this.medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public String getAmount() {
        return this.amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCost_of_one() {
        return this.cost_of_one;
    }

    public void setCost_of_one(String cost_of_one) {
        this.cost_of_one = cost_of_one;
    }

    public String getTotal_cost() {
        return this.total_cost;
    }

    public void setTotal_cost(String total_cost) {
        this.total_cost = total_cost;
    }

    private String getPatientAccId(int patientId) throws Exception {
        return get.getTableData("SELECT `id` FROM `drg_accounts` WHERE `patient_id`='" + patientId + "'").getValueAt(0, 0).toString();
    }

    private int getPatientAccId(String patientId) throws Exception {
        return Integer.parseInt(get.getTableData("SELECT `id` FROM `drg_accounts` WHERE `patient_id`='" + patientId + "'").getValueAt(0, 0).toString());
    }

    public boolean Add() throws Exception {
        DrugsAccounts.removeFromRemaining(Integer.parseInt(getPatientAccId(this.patient_id)), this.total_cost);
        DrugsAccounts.addToTotalSpended(Integer.parseInt(getPatientAccId(this.patient_id)), this.total_cost);
        AccountTransactions.addAmountToAccount(this.acc_id, this.total_cost);
        PreparedStatement ps = get.Prepare("INSERT INTO `drg_patient_medicine`(`id`, `patient_id`, `acc_id`, `medicine_id`, `amount`, `cost_for_one`, `total_cost`) VALUES (?,?,?,?,?,?,?)");
        StoreProdcts.reduceAmount(this.medicine_id, this.amount);
        ps.setInt(1, this.id);
        ps.setInt(2, this.patient_id);
        ps.setInt(3, this.acc_id);
        ps.setInt(4, this.medicine_id);
        ps.setString(5, this.amount);
        ps.setString(6, this.cost_of_one);
        ps.setString(7, this.total_cost);
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        JTable am = get.getTableData("SELECT `patient_id`,`total_cost`,`acc_id`, `medicine_id`, `amount` FROM `drg_patient_medicine` WHERE `id`='" + this.id + "'");
        AccountTransactions.removeAmountFromAccount(Integer.parseInt(am.getValueAt(0, 2).toString()), am.getValueAt(0, 1).toString());
        DrugsAccounts.removeFromTotalSpended(getPatientAccId(am.getValueAt(0, 0).toString()), am.getValueAt(0, 1).toString());
        DrugsAccounts.addToRemaining(getPatientAccId(am.getValueAt(0, 0).toString()), am.getValueAt(0, 1).toString());
        StoreProdcts.AddAmount(Integer.parseInt(am.getValueAt(0, 3).toString()), am.getValueAt(0, 4).toString());

        PreparedStatement ps = get.Prepare("UPDATE `drg_patient_medicine` SET `patient_id`=?,`acc_id`=?,`medicine_id`=?,`amount`=?,`cost_for_one`=?,`total_cost`=? WHERE `id`=?");
        ps.setInt(7, this.id);
        ps.setInt(1, this.patient_id);
        ps.setInt(2, this.acc_id);
        ps.setInt(3, this.medicine_id);
        ps.setString(4, this.amount);
        ps.setString(5, this.cost_of_one);
        ps.setString(6, this.total_cost);
        DrugsAccounts.removeFromRemaining(getPatientAccId(Integer.toString(this.patient_id)), this.total_cost);
        DrugsAccounts.addToTotalSpended(getPatientAccId(Integer.toString(this.patient_id)), this.total_cost);
        AccountTransactions.addAmountToAccount(this.acc_id, this.total_cost);
        StoreProdcts.reduceAmount(this.medicine_id, this.amount);
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        JTable am = get.getTableData("SELECT `patient_id`,`total_cost`,`acc_id`, `medicine_id`, `amount` FROM `drg_patient_medicine` WHERE `id`='" + this.id + "'");
        DrugsAccounts.removeFromTotalSpended(getPatientAccId(am.getValueAt(0, 0).toString()), am.getValueAt(0, 1).toString());
        AccountTransactions.removeAmountFromAccount(Integer.parseInt(am.getValueAt(0, 2).toString()), am.getValueAt(0, 1).toString());
        DrugsAccounts.addToRemaining(getPatientAccId(am.getValueAt(0, 0).toString()), am.getValueAt(0, 1).toString());
        StoreProdcts.AddAmount(Integer.parseInt(am.getValueAt(0, 3).toString()), am.getValueAt(0, 4).toString());

        PreparedStatement ps = get.Prepare("DELETE FROM `drg_patient_medicine` WHERE `id`=?");
        ps.setInt(1, this.id);
        ps.execute();
        return true;
    }

    public static ObservableList<DrugsMedicines> getData(int patient_id) throws Exception {
        ObservableList<DrugsMedicines> data = FXCollections.observableArrayList();
        ResultSet rs = get.getReportCon().createStatement().executeQuery("SELECT `drg_patient_medicine`.`id`,`medicine`.`name`, `drg_patient_medicine`.`amount`, `drg_patient_medicine`.`cost_for_one`, `total_cost`,`drg_patient_medicine`.`medicine_id` FROM `drg_patient_medicine`,`medicine` WHERE `drg_patient_medicine`.`patient_id` ='" + patient_id + "' and `medicine`.`id` in (SELECT stores_medicines.medicine_id from stores_medicines where stores_medicines.id = `drg_patient_medicine`.`medicine_id`)");
        while (rs.next()) {
            data.add(new DrugsMedicines(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `drg_patient_medicine`").getValueAt(0, 0).toString();
    }
}
