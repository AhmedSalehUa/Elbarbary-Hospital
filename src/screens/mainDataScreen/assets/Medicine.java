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
public class Medicine {

    int id;
    String name;
    String barcode;
    String unit;
    String warnNum; 

    int unitId;
    public Medicine() {
    }

    public Medicine(int id, String name, String barcode, String unit, String warnNum ) {
        this.id = id;
        this.name = name;
        this.barcode = barcode;
        this.unit = unit;
        this.warnNum = warnNum;
       
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
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

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getWarnNum() {
        return warnNum;
    }

    public void setWarnNum(String warnNum) {
        this.warnNum = warnNum;
    }
 

    public boolean Add() throws Exception {
        PreparedStatement st = db.get.Prepare("INSERT INTO `medicine`(`id`, `name`, `barcode`, `unit`, `warn_num`) VALUES (?,?,?,?,?)");
        st.setInt(1, id);
        st.setString(2, name);
        st.setString(3, barcode);
        st.setInt(4, unitId);
        st.setString(5, warnNum);
        
        st.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        PreparedStatement st = db.get.Prepare("UPDATE `medicine` SET `name`=?,`barcode`=?,`unit`=?,`warn_num`=? WHERE `id`=?");
        st.setInt(5, id);
        st.setString(1, name);
        st.setString(2, barcode);
        st.setInt(3, unitId);
        st.setString(4, warnNum);
      
        st.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement st = db.get.Prepare("DELETE FROM `medicine` WHERE `id`=?");
        st.setInt(1, id);
        st.execute();
        return true;
    }

    public static ObservableList<Medicine> getData() throws Exception {
        ObservableList<Medicine> data = FXCollections.observableArrayList();

        String SQL = "SELECT `medicine`.`id`, `medicine`.`name`, `medicine`.`barcode`,`medicine_units`.`name`, `medicine`.`warn_num` FROM `medicine`,`medicine_units` WHERE `medicine`.`unit`=`medicine_units`.`id`";

        ResultSet rs = db.get.getReportCon().createStatement().executeQuery(SQL);

        while (rs.next()) {
            data.add(new Medicine(rs.getInt(1), rs.getString(2), rs.getString(3),rs.getString(4), rs.getString(5)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(max(`id`) + 1,'1') FROM `medicine`").getValueAt(0, 0).toString();
    }

}
