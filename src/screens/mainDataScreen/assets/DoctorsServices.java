/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.mainDataScreen.assets;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

/**
 *
 * @author ahmed
 */
public class DoctorsServices {

    int id;
    int doctor_id;
    int service_id;
    String name;
    String cost;

    public DoctorsServices() {
    }

    public DoctorsServices(int id, int doctor_id, String name, String cost) {
        this.id = id;
        this.doctor_id = doctor_id;
        this.name = name;
        this.cost = cost;
    }

    

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public boolean Add() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `doctor_services`(`doctors_id`, `services_id`, `cost`) VALUES (?,?,?)");
        ps.setInt(1, doctor_id);
        ps.setInt(2, service_id);
        ps.setString(3, cost);
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        PreparedStatement ps = db.get.Prepare("UPDATE `doctor_services` SET `services_id`=?,`cost`=? WHERE `id`=? and`doctors_id`=?");
        ps.setInt(4, doctor_id);
        ps.setInt(3, id);
        ps.setInt(1, service_id);
        ps.setString(2, cost);
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare(" DELETE FROM `doctor_services` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

   

    public static ObservableList<DoctorsServices> getData(String doctor_id) throws Exception {
        ObservableList<DoctorsServices> data = FXCollections.observableArrayList();
        ResultSet st = db.get.getReportCon().createStatement().executeQuery("SELECT `doctor_services`.`id`,`doctor_services`.`doctors_id`,`doctor_services_names`.`name`,`doctor_services`.`cost` FROM `doctor_services`,`doctor_services_names` WHERE `doctor_services_names`.`id`=`doctor_services`.`services_id` AND `doctor_services`.`doctors_id`='" + doctor_id + "'");
        while (st.next()) {
           
            data.add(new DoctorsServices(st.getInt(1), st.getInt(2), st.getString(3), st.getString(4)));
        }
        return data;
    }
}
