/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.admission.assets;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ahmed
 */
public class Services {

    int id;
    int admissionId;
    int doctorId;
    int serviceId;
    String date;
    String cost;

    String doctor;
    String service;

    public Services() {
    }

    public Services(int id, String doctor, String service, String cost) {
        this.id = id;
        this.doctor = doctor;
        this.service = service;
        this.cost = cost;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
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

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean Add() throws Exception {

        PreparedStatement ps = db.get.Prepare("INSERT INTO `admission_services_doctors`(`id`, `admission_id`, `doctor_id`, `service_id`, `date`, `cost`) VALUES (?,?,?,?,?,?)");
        ps.setInt(1, id);
        ps.setInt(2, admissionId);
        ps.setInt(3, doctorId);
        ps.setInt(4, serviceId);
        ps.setString(5, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        String costs = getActualCost();
        ps.setString(6, costs);
        Admission.addCostToAdmission(admissionId, costs);
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        Admission.removeCostToAdmission(admissionId, db.get.getTableData("SELECT `cost` FROM `admission_services_doctors` WHERE `admission_id`='" + admissionId + "' And `id`='" + id + "'").getValueAt(0, 0).toString());
        PreparedStatement ps = db.get.Prepare("UPDATE `admission_services_doctors` SET `admission_id`=?,`doctor_id`=?,`service_id`=?,`cost`=? WHERE `id`=?");
        ps.setInt(5, id);
        ps.setInt(1, admissionId);
        ps.setInt(2, doctorId);
        ps.setInt(3, serviceId);
        String costs = getActualCost();
        ps.setString(4, costs);
        Admission.addCostToAdmission(admissionId, costs);
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        Admission.removeCostToAdmission(admissionId, db.get.getTableData("SELECT `cost` FROM `admission_services_doctors` WHERE `admission_id`='" + admissionId + "' And `id`='" + id + "'").getValueAt(0, 0).toString());

        PreparedStatement ps = db.get.Prepare("DELETE FROM `admission_services_doctors` WHERE `id`=? and `admission_id`=?");
        ps.setInt(1, id);
        ps.setInt(2, admissionId);
        ps.execute();
        return true;
    }

    public static ObservableList<Services> getData(int admissionId) throws Exception {
        ObservableList<Services> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `admission_services_doctors`.`id`,`doctors`.`name` as 'doctor', `doctor_services_names`.`name` as 'service',`admission_services_doctors`.`cost` FROM `doctors`,`admission_services_doctors`,`doctor_services_names` WHERE `admission_services_doctors`.`admission_id`='" + admissionId + "' and `doctors`.`id`= `admission_services_doctors`.`doctor_id` and `doctor_services_names`.`id` in (SELECT `services_id` FROM `doctor_services` WHERE `id`=`admission_services_doctors`.`service_id`)");
        while (rs.next()) {
            data.add(new Services(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(Max(`id`)+1,1) FROM `admission_services_doctors`").getValueAt(0, 0).toString();
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
