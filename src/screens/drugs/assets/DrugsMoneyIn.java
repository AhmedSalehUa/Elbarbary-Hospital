package screens.drugs.assets;

import db.get;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DrugsMoneyIn {

    int id;
    int patient_id;
    int escort_id;
    String escort_name;
    String amount;
    String date;

    public DrugsMoneyIn() {
    }

    public DrugsMoneyIn(int id, String escort_name, String amount, String date) {
        this.id = id;
        this.escort_name = escort_name;
        this.amount = amount;
        this.date = date;
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

    public int getEscort_id() {
        return this.escort_id;
    }

    public void setEscort_id(int escort_id) {
        this.escort_id = escort_id;
    }

    public String getEscort_name() {
        return this.escort_name;
    }

    public void setEscort_name(String escort_name) {
        this.escort_name = escort_name;
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

    public boolean Add() throws Exception {
        int acc_id = Integer.parseInt(get.getTableData("SELECT `id` FROM `drg_accounts` WHERE `patient_id`='" + this.patient_id + "'").getValueAt(0, 0).toString());
        DrugsAccounts.addToTotalPaied(acc_id, this.amount);
        DrugsAccounts.addToRemaining(acc_id, this.amount);

        PreparedStatement ps = get.Prepare("INSERT INTO `drg_money_in`(`id`, `patient_id`, `escort_id`, `amount`, `date`) VALUES (?,?,?,?,?)");
        ps.setInt(1, this.id);
        ps.setInt(2, this.patient_id);
        ps.setInt(3, this.escort_id);
        ps.setString(4, this.amount);
        ps.setString(5, this.date);
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        int acc_id = Integer.parseInt(get.getTableData("SELECT `id` FROM `drg_accounts` WHERE `patient_id`='" + this.patient_id + "'").getValueAt(0, 0).toString());
        DrugsAccounts.removeFromTotalPaied(acc_id, this.amount);
        DrugsAccounts.removeFromRemaining(acc_id, this.amount);

        PreparedStatement ps = get.Prepare("UPDATE `drg_money_in` SET `patient_id`=?,`escort_id`=?,`amount`=?,`date`=? WHERE `id`=?");
        ps.setInt(5, this.id);
        ps.setInt(1, this.patient_id);
        ps.setInt(2, this.escort_id);
        ps.setString(3, this.amount);
        ps.setString(4, this.date);
        DrugsAccounts.addToTotalPaied(acc_id, this.amount);
        DrugsAccounts.addToRemaining(acc_id, this.amount);

        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        int acc_id = Integer.parseInt(get.getTableData("SELECT `id` FROM `drg_accounts` WHERE `patient_id`='" + this.patient_id + "'").getValueAt(0, 0).toString());
        DrugsAccounts.removeFromTotalPaied(acc_id, this.amount);
        DrugsAccounts.removeFromRemaining(acc_id, this.amount);

        PreparedStatement ps = get.Prepare("DELETE FROM `drg_money_in` WHERE `id`=?");
        ps.setInt(1, this.id);
        ps.execute();
        return true;
    }

    public static ObservableList<DrugsMoneyIn> getData(int patient_id) throws Exception {
        ObservableList<DrugsMoneyIn> data = FXCollections.observableArrayList();
        ResultSet rs = get.getReportCon().createStatement().executeQuery("SELECT `drg_money_in`.`id`,`drg_patient_escort`.`name` , `drg_money_in`.`amount`, `drg_money_in`.`date` FROM `drg_money_in`,`drg_patient_escort` WHERE `drg_money_in`.`escort_id`=`drg_patient_escort`.`id` and `drg_money_in`.`patient_id`='" + patient_id + "'");
        while (rs.next()) {
            data.add(new DrugsMoneyIn(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `drg_money_in`").getValueAt(0, 0).toString();
    }
}
