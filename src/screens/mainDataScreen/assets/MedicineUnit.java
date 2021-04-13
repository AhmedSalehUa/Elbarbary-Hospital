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
public class MedicineUnit {

    int id;
    String unit;
    String value;

    public MedicineUnit() {
    }

    public MedicineUnit(int id, String unit, String value) {
        this.id = id;
        this.unit = unit;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean Add() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `medicine_units`(`name`, `value`) VALUES (?,?)");
        ps.setString(1, unit);
        ps.setString(2, value);
        ps.execute();
        return true;

    }

    public boolean Edite() throws Exception {
        PreparedStatement ps = db.get.Prepare("UPDATE `medicine_units` SET `name`=?,`value`=? WHERE `id`=?");
        ps.setInt(3, id);
        ps.setString(1, unit);
        ps.setString(2, value);
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `medicine_units` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public static ObservableList<MedicineUnit> getData() throws Exception {
        ObservableList<MedicineUnit> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT * FROM `medicine_units`");
        while (rs.next()) {
            data.add(new MedicineUnit(rs.getInt(1), rs.getString(2), rs.getString(3)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `medicine_units`").getValueAt(0, 0).toString();
    }
}
