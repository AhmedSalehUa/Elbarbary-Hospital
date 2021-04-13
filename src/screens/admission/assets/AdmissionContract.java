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
 * @author AHMED
 */
public class AdmissionContract {

    int id;
    int admissionId;
    int serviceId;
    String service;
    String cost;

    public AdmissionContract() {
    }

    public AdmissionContract(int id, String service, String cost) {
        this.id = id;
        this.service = service;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAdmissionId() {
        return admissionId;
    }

    public void setAdmissionId(int admissionId) {
        this.admissionId = admissionId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
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

    public boolean Add() throws Exception {
        Admission.addCostToAdmission(admissionId, cost);
        PreparedStatement ps = db.get.Prepare("INSERT INTO `admission_services_contract`(`id`, `admission_id`, `services_id`, `cost`) VALUES (?,?,?,?)");
        ps.setInt(1, id);
        ps.setInt(2, admissionId);
        ps.setInt(3, serviceId);
        ps.setString(4, cost);
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        Admission.removeCostToAdmission(admissionId, db.get.getTableData("SELECT `cost` FROM `admission_services_contract` WHERE `admission_id`='" + admissionId + "' And `id`='" + id + "'").getValueAt(0, 0).toString());

        PreparedStatement ps = db.get.Prepare("UPDATE `admission_services_contract` SET `services_id`=?,`cost`=? WHERE `id`=?");
        ps.setInt(1, serviceId);
        ps.setString(2, cost);
        ps.setInt(3, id);
        Admission.addCostToAdmission(admissionId, cost);
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        Admission.removeCostToAdmission(admissionId, db.get.getTableData("SELECT `cost` FROM `admission_services_contract` WHERE `admission_id`='" + admissionId + "' And `id`='" + id + "'").getValueAt(0, 0).toString());

        PreparedStatement ps = db.get.Prepare("DELETE FROM `admission_services_contract` WHERE  `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public static ObservableList<AdmissionContract> getData(int admissionId) throws Exception {
        ObservableList<AdmissionContract> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `admission_services_contract`.`id`, `contracts_services_names`.`name`, `admission_services_contract`.`cost` FROM `admission_services_contract`,`contracts_services_names`,`contracts_services` WHERE `admission_services_contract`.`admission_id`='" + admissionId + "' "
                + "and  `admission_services_contract`.`services_id` = `contracts_services`.`id` "
                + "and `contracts_services`.`service_id`=`contracts_services_names`.`id`");
        while (rs.next()) {
            data.add(new AdmissionContract(rs.getInt(1), rs.getString(2), rs.getString(3)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `admission_services_contract`").getValueAt(0, 0).toString();
    }
}
