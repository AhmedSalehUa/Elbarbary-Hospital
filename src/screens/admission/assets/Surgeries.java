/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.admission.assets;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ahmed
 */
public class Surgeries {

    int id;
    String doctor;
    String anesthetization;
    String type;
    String date;
    String escort;
    String cost;

    String doctorCost = "0";
    String anesthetizationCost = "0";

    int admissionId;
    int doctorId;
    int escortId;
    int anesthetizationId;
    int surgeyTypeId;

    public Surgeries() {
    }

    public Surgeries(int id, String doctor, String anesthetization, String date, String type, String escort, String cost) {
        this.id = id;
        this.doctor = doctor;
        this.anesthetization = anesthetization;
        this.type = type;
        this.date = date;
        this.escort = escort;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getAnesthetization() {
        return anesthetization;
    }

    public void setAnesthetization(String anesthetization) {
        this.anesthetization = anesthetization;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEscort() {
        return escort;
    }

    public void setEscort(String escort) {
        this.escort = escort;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDoctorCost() {
        return doctorCost;
    }

    public void setDoctorCost(String doctorCost) {
        this.doctorCost = doctorCost;
    }

    public String getAnesthetizationCost() {
        return anesthetizationCost;
    }

    public void setAnesthetizationCost(String anesthetizationCost) {
        this.anesthetizationCost = anesthetizationCost;
    }

    public int getAdmissionId() {
        return admissionId;
    }

    public void setAdmissionId(int admissionId) {
        this.admissionId = admissionId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getEscortId() {
        return escortId;
    }

    public void setEscortId(int escortId) {
        this.escortId = escortId;
    }

    public int getAnesthetizationId() {
        return anesthetizationId;
    }

    public void setAnesthetizationId(int anesthetizationId) {
        this.anesthetizationId = anesthetizationId;
    }

    public int getSurgeyTypeID() {
        return surgeyTypeId;
    }

    public void setSurgeyTypeId(int surgeyTypeID) {
        this.surgeyTypeId = surgeyTypeID;
    }

    public boolean Add() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `admission_services_surgeries`(`id`, `admission_id`, `doctor_id`, `Anesthetization_doctor`, `escort_id`, `doctor_cost`, `Anesthetization_cost`, `surgey_type`, `date`, `totalcost`) VALUES (?,?,?,?,?,?,?,?,?,?)");
        ps.setInt(1, id);
        ps.setInt(2, admissionId);
        ps.setInt(3, doctorId);
        ps.setInt(4, anesthetizationId);
        ps.setInt(5, escortId);
        ps.setString(6, doctorCost);
        ps.setString(7, anesthetizationCost);
        ps.setInt(8, surgeyTypeId);
        ps.setString(9, date);
        String costs = getActualCost();
        ps.setString(10, costs);
        Admission.addCostToAdmission(admissionId, costs);
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        Admission.removeCostToAdmission(admissionId, db.get.getTableData("SELECT `totalcost` FROM `admission_services_surgeries` WHERE `admission_id`='" + admissionId + "' And `id`='" + id + "'").getValueAt(0, 0).toString());
        PreparedStatement ps = db.get.Prepare("UPDATE `admission_services_surgeries` SET `admission_id`=?,`doctor_id`=?,`Anesthetization_doctor`=?,`escort_id`=?,`doctor_cost`=?,`Anesthetization_cost`=?,`surgey_type`=?,`date`=?,`totalcost`=? WHERE `id`=?");
        ps.setInt(1, admissionId);
        ps.setInt(2, doctorId);
        ps.setInt(3, anesthetizationId);
        ps.setInt(4, escortId);
        ps.setString(5, doctorCost);
        ps.setString(6, anesthetizationCost);
        ps.setInt(7, surgeyTypeId);
        ps.setString(8, date);
        String costs = getActualCost();
        ps.setString(9, costs);
        Admission.addCostToAdmission(admissionId, costs);
        ps.setInt(10, id);
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        Admission.removeCostToAdmission(admissionId, getActualCost());
        PreparedStatement ps = db.get.Prepare("DELETE FROM `admission_services_surgeries` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public static ObservableList<Surgeries> getData(int id) throws Exception {
        ObservableList<Surgeries> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT p3.id,p1.name as 'doctor', p2.name as 'Anesthetization_doctor',p3.date,p4.type, p5.name as 'escort' ,p3.totalcost "
                + "FROM admission_services_surgeries c "
                + "JOIN doctors p1 ON p1.id = c.doctor_id "
                + "JOIN doctors p2 on p2.id = c.Anesthetization_doctor "
                + "JOIN admission_services_surgeries p3 on p3.id = c.id "
                + "JOIN admission_services_surgeries_type p4 on p4.id = c.surgey_type "
                + "JOIN patient_escort p5 on p5.id = c.escort_id "
                + "WHERE c.admission_id='"+id+"'");
        while (rs.next()) {
            data.add(new Surgeries(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("select ifnull(max(`id`)+1,1) from `admission_services_surgeries`").getValueAt(0, 0).toString();
    }

    public String getActualCost() throws Exception {
        String contractDiscpunt = db.get.getTableData("SELECT IFNULL(sum( `discount`),'0') as 'cost' FROM `contracts` WHERE `id`in(SELECT `contract_id` FROM `admission` WHERE `id`='" + admissionId + "')").getValueAt(0, 0).toString();

        if (contractDiscpunt.equals("0")) {
            return cost;
        } else {
            double clinc = Double.parseDouble(cost);
            double discount = Double.parseDouble(contractDiscpunt);

            double total = clinc * discount;
            total = total / 100;
            total = clinc - total;
            return Double.toString(total);
        }

    }
}
