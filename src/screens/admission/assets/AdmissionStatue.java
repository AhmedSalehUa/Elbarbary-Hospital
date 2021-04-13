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
public class AdmissionStatue {
        int id;
    String name;

    public AdmissionStatue() {
    }

    public AdmissionStatue(int id, String name) {
        this.id = id;
        this.name = name;
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

    public static boolean Add(String results) throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `admission_statue`(`name`) VALUES (?)");
        ps.setString(1, results);
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        PreparedStatement ps = db.get.Prepare("");
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("");
        ps.execute();
        return true;
    }

    public static ObservableList<AdmissionStatue> getData() throws Exception {
        ObservableList<AdmissionStatue> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT * FROM `admission_statue`");
        while (rs.next()) {
            data.add(new AdmissionStatue(rs.getInt(1), rs.getString(2)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `admission_statue`").getValueAt(0, 0).toString();
    }
}
