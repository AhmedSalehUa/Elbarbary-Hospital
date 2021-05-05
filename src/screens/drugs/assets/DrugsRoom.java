/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.drugs.assets;

import db.get;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JTable;  

public class DrugsRoom {

    int id;
    int patient_id;
    String date;
    String cost;

    public DrugsRoom() {
    }

    public DrugsRoom(int id, int patient_id, String date, String cost) {
        this.id = id;
        this.patient_id = patient_id;
        this.date = date;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    private String getPatientAccId(int patientId) throws Exception {
        return get.getTableData("SELECT `id` FROM `drg_accounts` WHERE `patient_id`='" + patientId + "'").getValueAt(0, 0).toString();
    }

    private int getPatientAccId(String patientId) throws Exception {
        return Integer.parseInt(get.getTableData("SELECT `id` FROM `drg_accounts` WHERE `patient_id`='" + patientId + "'").getValueAt(0, 0).toString());
    }

    public boolean Add() throws Exception {
        DrugsAccounts.addToTotalSpended(Integer.parseInt(getPatientAccId(patient_id)), this.cost);
        DrugsAccounts.removeFromRemaining(Integer.parseInt(getPatientAccId(patient_id)), this.cost);

        PreparedStatement ps = db.get.Prepare("INSERT INTO `drg_patient_room`(`id`, `patient_id`, `date`, `cost`) VALUES (?,?,?,?)");
        ps.setInt(1, id);
        ps.setInt(2, patient_id);
        ps.setString(3, date);
        ps.setString(4, cost);
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        JTable am = get.getTableData("SELECT `patient_id`,`cost` FROM `drg_patient_room` WHERE `id`='" + this.id + "'");
        DrugsAccounts.removeFromTotalSpended(getPatientAccId(am.getValueAt(0, 0).toString()), am.getValueAt(0, 1).toString());
        DrugsAccounts.addToRemaining(getPatientAccId(am.getValueAt(0, 0).toString()), am.getValueAt(0, 1).toString());

        PreparedStatement ps = db.get.Prepare("UPDATE `drg_patient_room` SET `patient_id`=?,`date`=?,`cost`=? WHERE `id`=?");
        ps.setInt(4, id);
        ps.setInt(1, patient_id);
        ps.setString(2, date);
        ps.setString(3, cost);
        DrugsAccounts.addToTotalSpended(Integer.parseInt(getPatientAccId(patient_id)), this.cost);
        DrugsAccounts.removeFromRemaining(Integer.parseInt(getPatientAccId(patient_id)), this.cost);

        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        JTable am = get.getTableData("SELECT `patient_id`,`cost` FROM `drg_patient_room` WHERE `id`='" + this.id + "'");
        DrugsAccounts.removeFromTotalSpended(getPatientAccId(am.getValueAt(0, 0).toString()), am.getValueAt(0, 1).toString());
        DrugsAccounts.addToRemaining(getPatientAccId(am.getValueAt(0, 0).toString()), am.getValueAt(0, 1).toString());

        PreparedStatement ps = db.get.Prepare("DELETE FROM `drg_patient_room` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public static ObservableList<DrugsRoom> getData(int patient) throws Exception {
        ObservableList<DrugsRoom> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT * FROM `drg_patient_room` WHERE `patient_id`='" + patient + "'");
        while (rs.next()) {
            data.add(new DrugsRoom(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `drg_patient_room`").getValueAt(0, 0).toString();
    }
}
