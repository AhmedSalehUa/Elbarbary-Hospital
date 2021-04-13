package screens.drugs.assets;

import db.get;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JTable;
import screens.accounts.assets.AccountTransactions;

public class DrugsServices {

    int id;
    int patient_id;
    int acc_id;
    int doctor_id;
    String doctor;
    int service_id;
    String service;
    String cost;
    String date;

    public DrugsServices(int id, String doctor, String service, String cost, String date) {
        this.id = id;
        this.doctor = doctor;
        this.service = service;
        this.cost = cost;
        this.date = date;
    }

    public DrugsServices() {
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

    public int getDoctor_id() {
        return this.doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getDoctor() {
        return this.doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public int getService_id() {
        return this.service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public String getService() {
        return this.service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getCost() {
        return this.cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String getPatientAccId(int patientId) throws Exception {
        return get.getTableData("SELECT `id` FROM `drg_accounts` WHERE `patient_id`='" + patientId + "'").getValueAt(0, 0).toString();
    }

    private int getPatientAccId(String patientId) throws Exception {
        return Integer.parseInt(get.getTableData("SELECT `id` FROM `drg_accounts` WHERE `patient_id`='" + patientId + "'").getValueAt(0, 0).toString());
    }

    public boolean Add() throws Exception {
        DrugsAccounts.removeFromRemaining(Integer.parseInt(getPatientAccId(this.patient_id)), this.cost);
        DrugsAccounts.addToTotalSpended(Integer.parseInt(getPatientAccId(this.patient_id)), this.cost);
        AccountTransactions.addAmountToAccount(this.acc_id, this.cost);
        PreparedStatement ps = get.Prepare("INSERT INTO `drg_patient_service`(`id`, `patient_id`, `acc_id`, `doctor_id`, `service_id`, `cost`, `date`) VALUES (?,?,?,?,?,?,?)");
        ps.setInt(1, this.id);
        ps.setInt(2, this.patient_id);
        ps.setInt(3, this.acc_id);
        ps.setInt(4, this.doctor_id);
        ps.setInt(5, this.service_id);
        ps.setString(6, this.cost);
        ps.setString(7, this.date);
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        JTable am = get.getTableData("SELECT `patient_id`,`cost`,`acc_id` FROM `drg_patient_service` WHERE `id`='" + this.id + "'");
        DrugsAccounts.removeFromTotalSpended(getPatientAccId(am.getValueAt(0, 0).toString()), am.getValueAt(0, 1).toString());
        AccountTransactions.removeAmountFromAccount(Integer.parseInt(am.getValueAt(0, 2).toString()), am.getValueAt(0, 1).toString());
        DrugsAccounts.addToRemaining(getPatientAccId(am.getValueAt(0, 0).toString()), am.getValueAt(0, 1).toString());

        PreparedStatement ps = get.Prepare("UPDATE `drg_patient_service` SET `patient_id`=?,`acc_id`=?,`doctor_id`=?,`service_id`=?,`cost`=?,`date`=? WHERE `id`=?");
        ps.setInt(7, this.id);
        ps.setInt(1, this.patient_id);
        ps.setInt(2, this.acc_id);
        ps.setInt(3, this.doctor_id);
        ps.setInt(4, this.service_id);
        ps.setString(5, this.cost);
        ps.setString(6, this.date);
        DrugsAccounts.removeFromRemaining(Integer.parseInt(getPatientAccId(this.patient_id)), this.cost);
        DrugsAccounts.addToTotalSpended(Integer.parseInt(getPatientAccId(this.patient_id)), this.cost);
        AccountTransactions.addAmountToAccount(this.acc_id, this.cost);
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        JTable am = get.getTableData("SELECT `patient_id`,`cost`,`acc_id` FROM `drg_patient_service` WHERE `id`='" + this.id + "'");
        DrugsAccounts.removeFromTotalSpended(getPatientAccId(am.getValueAt(0, 0).toString()), am.getValueAt(0, 1).toString());
        AccountTransactions.removeAmountFromAccount(Integer.parseInt(am.getValueAt(0, 2).toString()), am.getValueAt(0, 1).toString());
        DrugsAccounts.addToRemaining(getPatientAccId(am.getValueAt(0, 0).toString()), am.getValueAt(0, 1).toString());

        PreparedStatement ps = get.Prepare("DELETE FROM `drg_patient_service` WHERE `id`=?");
        ps.setInt(1, this.id);
        ps.execute();
        return true;
    }

    public static ObservableList<DrugsServices> getData(int patient_id) throws Exception {
        ObservableList<DrugsServices> data = FXCollections.observableArrayList();
        ResultSet rs = get.getReportCon().createStatement().executeQuery("SELECT `drg_patient_service`.`id`,`doctors`.`name`,`doctor_services_names`.`name`, `drg_patient_service`.`cost`, `drg_patient_service`.`date` FROM `doctor_services_names`,`doctors`,`drg_patient_service` WHERE `drg_patient_service`.`doctor_id`=`doctors`.`id` and `doctor_services_names`.`id` in(SELECT `doctor_services`.`services_id` from `doctor_services` where `doctor_services`.`id`=`drg_patient_service`.`service_id`) and `drg_patient_service`.`patient_id`='" + patient_id + "'");
        while (rs.next()) {
            data.add(new DrugsServices(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `drg_patient_service`").getValueAt(0, 0).toString();
    }
}
