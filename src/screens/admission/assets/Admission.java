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
import screens.reception.assets.ReceptionYields;

/**
 *
 * @author ahmed
 */
public class Admission {

    int id;
    int patient_id;
    String patient_name;
    int contract_id;
    String contract_name;
    String dateOfEntrance;
    String dateOfExite;
    String type;
    int type_id;
    String statue;
    int statue_id;
    String spacification;
    int spacification_id;

    String totalCost;

    public Admission(int id, String patient_name, String contract_name, String dateOfEntrance, String dateOfExite, String type, String statue, String spacification, String totalCost) {
        this.id = id;
        this.patient_name = patient_name;
        this.contract_name = contract_name;
        this.dateOfEntrance = dateOfEntrance;
        if (dateOfExite == null) {
            this.dateOfExite = "0000-00-00";
        } else {
            this.dateOfExite = dateOfExite;
        }

        this.type = type;
        this.statue = statue;
        this.spacification = spacification;
        this.totalCost = totalCost;
    }

    public Admission() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpacification() {
        return spacification;
    }

    public void setSpacification(String spacification) {
        this.spacification = spacification;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_name) {
        this.patient_id = patient_name;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getContract_name() {
        return contract_name;
    }

    public void setContract_name(String contract_name) {
        this.contract_name = contract_name;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public int getStatue_id() {
        return statue_id;
    }

    public void setStatue_id(int statue_id) {
        this.statue_id = statue_id;
    }

    public int getSpacification_id() {
        return spacification_id;
    }

    public void setSpacification_id(int spacification_id) {
        this.spacification_id = spacification_id;
    }

    public int getContract_id() {
        return contract_id;
    }

    public void setContract_id(int contract_id) {
        this.contract_id = contract_id;
    }

    public String getDateOfEntrance() {
        return dateOfEntrance;
    }

    public void setDateOfEntrance(String date) {
        this.dateOfEntrance = date;
    }

    public String getDateOfExite() {
        return dateOfExite;
    }

    public void setDateOfExite(String dateOfExite) {
        this.dateOfExite = dateOfExite;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatue() {
        return statue;
    }

    public void setStatue(String statue) {
        this.statue = statue;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public boolean Add() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `admission`( `patient_id`, `contract_id`, `date_of_enter`,`date_of_exit`,`type`, `statue`, `spacification`, `total_cost`) VALUES (?,?,?,?,?,?,?,?)");
        ps.setInt(1, patient_id);
        ps.setInt(2, contract_id);
        ps.setString(3, dateOfEntrance);
        ps.setString(4, dateOfExite);
        ps.setInt(5, type_id);
        ps.setInt(6, statue_id);
        ps.setInt(7, spacification_id);
        ps.setString(8, "0");
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        PreparedStatement ps = db.get.Prepare("UPDATE `admission` SET `patient_id`=?,`contract_id`=?,`date_of_enter`=?,`date_of_exit`=?,`type`=?,`statue`=?,`spacification`=? WHERE `id`=?");
        ps.setInt(1, patient_id);
        ps.setInt(2, contract_id);
        ps.setString(3, dateOfEntrance);
        ps.setString(4, dateOfExite);
        ps.setInt(5, type_id);
        ps.setInt(6, statue_id);
        ps.setInt(7, spacification_id);
        ps.setInt(8, id);
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `admission` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public static ObservableList<Admission> getData() throws Exception {
        ObservableList<Admission> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `admission`.`id`,`patients`.`name` as 'patient' , `contracts`.`name` as 'contract',`admission`.`date_of_enter`,`admission`.`date_of_exit`,`admission_type`.`name` as 'type',`admission_statue`.`name` as 'statue',`admission_spacification`.`name` as 'spacification',`admission`.`total_cost` "
                + "from `admission`,`admission_type`,`admission_statue`,`admission_spacification`,`patients`,`contracts`"
                + "where `patients`.`id`=`admission`.`patient_id` and `contracts`.`id`=`admission`.`contract_id`and `admission`.`type` =`admission_type`.`id` and `admission`.`statue` = `admission_statue`.`id` and `admission`.`spacification` = `admission_spacification`.`id` ORDER by `admission`.`id` DESC;");
        while (rs.next()) {
            data.add(new Admission(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9)));
        }
        return data;
    }

    public static ObservableList<Admission> getDataNotPaied() throws Exception {
        ObservableList<Admission> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `admission`.`id`,`patients`.`name` as 'patient' , `contracts`.`name` as 'contract',`admission`.`date_of_enter`,`admission`.`date_of_exit`,`admission_type`.`name` as 'type',`admission_statue`.`name` as 'statue',`admission_spacification`.`name` as 'spacification',`admission`.`total_cost` "
                + "from `admission`,`admission_type`,`admission_statue`,`admission_spacification`,`patients`,`contracts`"
                + "where `patients`.`id`=`admission`.`patient_id` and `contracts`.`id`=`admission`.`contract_id` and `contracts`.`id`= '1' and `admission`.`type` =`admission_type`.`id` and `admission`.`statue` = `admission_statue`.`id` and `admission`.`spacification` = `admission_spacification`.`id` and `admission`.`id` not in (SELECT `admission_id` FROM `admission_paied`) ORDER by `admission`.`id` DESC;");
        while (rs.next()) {
            data.add(new Admission(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9)));
        }
        return data;
    }

    public static ObservableList<Admission> getDataOfCotract(int id, String from, String to) throws Exception {
        ObservableList<Admission> data = FXCollections.observableArrayList();
        String sql = "";
        if (from == null || to == null) {
            sql = "SELECT `admission`.`id`,`patients`.`name` as 'patient' , `contracts`.`name` as 'contract',`admission`.`date_of_enter`,`admission`.`date_of_exit`,`admission_type`.`name` as 'type',`admission_statue`.`name` as 'statue',`admission_spacification`.`name` as 'spacification',`admission`.`total_cost` "
                    + "from `admission`,`admission_type`,`admission_statue`,`admission_spacification`,`patients`,`contracts`"
                    + "where `patients`.`id`=`admission`.`patient_id` and `contracts`.`id`=`admission`.`contract_id` and `contracts`.`id`= '" + id + "' and `admission`.`type` =`admission_type`.`id` and `admission`.`statue` = `admission_statue`.`id` and `admission`.`spacification` = `admission_spacification`.`id` and `admission`.`id` not in (SELECT `admission_id` FROM `admission_paied`) ORDER by `admission`.`id` DESC;";
        } else {
            sql = "SELECT `admission`.`id`,`patients`.`name` as 'patient' , `contracts`.`name` as 'contract',`admission`.`date_of_enter`,`admission`.`date_of_exit`,`admission_type`.`name` as 'type',`admission_statue`.`name` as 'statue',`admission_spacification`.`name` as 'spacification',`admission`.`total_cost` "
                    + "from `admission`,`admission_type`,`admission_statue`,`admission_spacification`,`patients`,`contracts`"
                    + "where `admission`.`date_of_enter` >= '" + from + "' and `admission`.`date_of_enter` <= '" + to + "' and`patients`.`id`=`admission`.`patient_id` and `contracts`.`id`=`admission`.`contract_id` and `contracts`.`id`= '" + id + "' and `admission`.`type` =`admission_type`.`id` and `admission`.`statue` = `admission_statue`.`id` and `admission`.`spacification` = `admission_spacification`.`id` and `admission`.`id` not in (SELECT `admission_id` FROM `admission_paied`) ORDER by `admission`.`id` DESC;";
        }
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery(sql);
        while (rs.next()) {
            data.add(new Admission(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("select ifnull(max(`id`)+1,1) from `admission`").getValueAt(0, 0).toString();
    }

    public static boolean addCostToAdmission(int id, String amount) throws Exception {
        Double a = Double.parseDouble(db.get.getTableData("select total_cost from admission where id='" + id + "'").getValueAt(0, 0).toString()) + Double.parseDouble(amount);
        PreparedStatement ps = db.get.Prepare("UPDATE `admission` SET `total_cost`=? WHERE `id`=?");
        ps.setString(1, Double.toString(a));
        ps.setInt(2, id);
        ReceptionYields.rollBack(id);
        ps.execute();
        return true;

    }

    public static boolean removeCostToAdmission(int id, String amount) throws Exception {
        Double a = Double.parseDouble(db.get.getTableData("select total_cost from admission where id='" + id + "'").getValueAt(0, 0).toString()) - Double.parseDouble(amount);
        PreparedStatement ps = db.get.Prepare("UPDATE `admission` SET `total_cost`=? WHERE `id`=?");
        ps.setString(1, Double.toString(a));
        ps.setInt(2, id);
        ReceptionYields.rollBack(id);
        ps.execute();

        return true;

        //rollback in yields
    }

//    public static boolean updateStatue(int id, String state) throws Exception {
//        PreparedStatement ps = db.get.Prepare("UPDATE `admission` SET `statue`=? WHERE `id`=?");
//        ps.setString(1, state);
//        ps.setInt(2, id);
//        ps.execute();
//        return true;
//    }
}
