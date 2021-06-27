/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.reports.assets;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author AHMED
 */
public class Baby {

    int id;
    String name;
    String father;
    String mother;
    String gender;
    String dateOfBirth;
    String Country;
    String doctor;

    public Baby() {
    }

    public Baby(int id, String name, String father, String mother, String gender, String dateOfBirth, String Country, String doctor) {
        this.id = id;
        this.name = name;
        this.father = father;
        this.mother = mother;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.Country = Country;
        this.doctor = doctor;
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

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getMother() {
        return mother;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String Country) {
        this.Country = Country;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public boolean Add() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `rep_baby`(`name`, `father`, `mother`, `gender`, `date_of_birth`, `country`, `doctor`) VALUES (?,?,?,?,?,?,?)");
        ps.setString(1, name);
        ps.setString(2, father);
        ps.setString(3, mother);
        ps.setString(4, gender);
        ps.setString(5, dateOfBirth);
        ps.setString(6, Country);
        ps.setString(7, doctor);
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        PreparedStatement ps = db.get.Prepare("UPDATE `rep_baby` SET `name`=?,`father`=?,`mother`=?,`gender`=?,`date_of_birth`=?,`country`=?,`doctor`=? WHERE `id`=?");
        ps.setString(1, name);
        ps.setString(2, father);
        ps.setString(3, mother);
        ps.setString(4, gender);
        ps.setString(5, dateOfBirth);
        ps.setString(6, Country);
        ps.setString(7, doctor);
        ps.setInt(8, id);
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `rep_baby` WHERE `id`=?");
         ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public static ObservableList<Baby> getData() throws Exception {
        ObservableList<Baby> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT * FROM `rep_baby`");
        while (rs.next()) {
            data.add(new Baby(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `rep_baby`").getValueAt(0, 0).toString();
    }
}
