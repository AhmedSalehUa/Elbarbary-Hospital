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
public class AdmissionServices {

    int id;
    int admissionId;
    int serviceId;
    String type;
    String cost;

    public AdmissionServices() {
    }

    public AdmissionServices(int id, int addmissionId, int serviceId, String type, String cost) {
        this.id = id;
        this.admissionId = addmissionId;
        this.serviceId = serviceId;
        this.type = type;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAddmissionId() {
        return admissionId;
    }

    public void setAddmissionId(int addmissionId) {
        this.admissionId = addmissionId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public boolean Add() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `admission_services`(`admission_id`, `services_id`, `type`, `cost`) VALUES (?,?,?,?)");
        ps.setInt(1, admissionId);
        ps.setInt(2, serviceId);
        ps.setString(3, type);
        ps.setString(4, cost);
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        PreparedStatement ps = db.get.Prepare("UPDATE `admission_services` SET `admission_id`=?,`services_id`=?,`type`=?,`cost`=? WHERE `id`=?");
        ps.setInt(1, admissionId);
        ps.setInt(2, serviceId);
        ps.setString(3, type);
        ps.setString(4, cost);
        ps.setInt(5, id);
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `admission_services` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;

    }

    public static ObservableList<AdmissionServices> getServicesData() throws Exception {
        ObservableList<AdmissionServices> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT * FROM `admission_services`");
        while (rs.next()) {
            data.add(new AdmissionServices(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5)));

        }
        return data;
    }
     public static ObservableList<AdmissionServices> getSurgeriesData() throws Exception {
        ObservableList<AdmissionServices> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT * FROM `admission_services`");
        while (rs.next()) {
            data.add(new AdmissionServices(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5)));

        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("select IFNULL(max(`id`)+1,1) from admission_services").getValueAt(0, 0).toString();
    }
}
