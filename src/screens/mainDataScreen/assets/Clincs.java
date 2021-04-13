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
public class Clincs {

    int id;
    String name;
    String examinationCost;
    String reExaminationCost;
    String illustarionCost;

    public Clincs() {
    }

    public Clincs(int id, String name, String examinationCost, String reExaminationCost, String illustarionCost) {
        this.id = id;
        this.name = name;
        this.examinationCost = examinationCost;
        this.reExaminationCost = reExaminationCost;
        this.illustarionCost = illustarionCost;
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

    public String getExaminationCost() {
        return examinationCost;
    }

    public void setExaminationCost(String examinationCost) {
        this.examinationCost = examinationCost;
    }

    public String getReExaminationCost() {
        return reExaminationCost;
    }

    public void setReExaminationCost(String reExaminationCost) {
        this.reExaminationCost = reExaminationCost;
    }

    public String getIllustarionCost() {
        return illustarionCost;
    }

    public void setIllustarionCost(String illustarionCost) {
        this.illustarionCost = illustarionCost;
    }

    public boolean Add() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `clincs`(`id`, `name`, `examinationCost`, `reExaminationCost`, `illustarionCost`) VALUES (?,?,?,?,?)");
        ps.setInt(1, id);
        ps.setString(2, name);
        ps.setString(3, examinationCost);
        ps.setString(4, reExaminationCost);
        ps.setString(5, illustarionCost);
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        PreparedStatement ps = db.get.Prepare("UPDATE `clincs` SET `name`=?,`examinationCost`=?,`reExaminationCost`=?,`illustarionCost`=? WHERE `id`=?");
        ps.setInt(5, id);
        ps.setString(1, name);
        ps.setString(2, examinationCost);
        ps.setString(3, reExaminationCost);
        ps.setString(4, illustarionCost);
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare(" DELETE FROM `clincs` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public static ObservableList<Clincs> getData() throws Exception {
        ObservableList<Clincs> data = FXCollections.observableArrayList();
        String sql ="select * from `clincs`";
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery(sql); 
        while(rs.next()){
            data.add(new Clincs(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
        }
        return data;
    } 

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("select IFNULL(max(`id`)+1,1) from `clincs`").getValueAt(0, 0).toString();
    } 
}
