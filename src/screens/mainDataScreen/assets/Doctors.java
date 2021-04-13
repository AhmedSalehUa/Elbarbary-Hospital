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

/**
 *
 * @author ahmed
 */
public class Doctors {

    int id;
    String name;
    String age;
    int qualification_id;
    String qualification_name;
    String tele1;
    String tele2;

    public Doctors() {
    }

    public Doctors(int id, String name, String age, int qualification_id, String tele1, String tele2) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.qualification_id = qualification_id;
        this.tele1 = tele1;
        this.tele2 = tele2;
    }

    public Doctors(int id, String name, String age, String qualification_name, String tele1, String tele2) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.qualification_name = qualification_name;
        this.tele1 = tele1;
        this.tele2 = tele2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getTele1() {
        return tele1;
    }

    public void setTele1(String tele1) {
        this.tele1 = tele1;
    }

    public String getTele2() {
        return tele2;
    }

    public void setTele2(String tele2) {
        this.tele2 = tele2;
    }

    public int getQualification_id() {
        return qualification_id;
    }

    public void setQualification_id(int qualification_id) {
        this.qualification_id = qualification_id;
    }

    public String getQualification_name() {
        return qualification_name;
    }

    public void setQualification_name(String qualification_name) {
        this.qualification_name = qualification_name;
    }

    public boolean Add() throws Exception {
        PreparedStatement st = db.get.Prepare("INSERT INTO `doctors`(`id`, `name`, `age`,`qualification_id`, `tele1`, `tele2`) VALUES (?,?,?,?,?,?)");
        st.setInt(1, id);
        st.setString(2, name);
        st.setString(3, age);
        st.setInt(4, qualification_id);
        st.setString(5, tele1);
        st.setString(6, tele2);
        st.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        PreparedStatement st = db.get.Prepare("UPDATE `doctors` SET `name`=?,`age`=?,`tele1`=?,`tele2`=?,`qualification_id`=? WHERE `id`=?");
        st.setInt(5, qualification_id);
        st.setInt(6, id);
        st.setString(1, name);
        st.setString(2, age);
        st.setString(3, tele1);
        st.setString(4, tele2);
        st.execute();
        return true;

    }

    public boolean Delete() throws Exception {
        PreparedStatement st = db.get.Prepare("DELETE FROM `doctors` WHERE `id`=?");
        st.setInt(1, id);
        st.execute();
        return true;
    }

    public static ObservableList<Doctors> getData() throws Exception {
        ObservableList<Doctors> data = FXCollections.observableArrayList();

        String SQL = "SELECT `doctors`.`id`, `doctors`.`name`, `doctors`.`age`, `qualifications`.`name`, `doctors`.`tele1`, `doctors`.`tele2` FROM `doctors`,`qualifications` WHERE `qualifications`.`id`=`doctors`.`qualification_id`";

        ResultSet rs = db.get.getReportCon().createStatement().executeQuery(SQL);

        while (rs.next()) {
            data.add(new Doctors(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
        }
        return data;
    }

     

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(max(`id`) + 1,'1') FROM `doctors`").getValueAt(0, 0).toString();
    }
 
     
}
