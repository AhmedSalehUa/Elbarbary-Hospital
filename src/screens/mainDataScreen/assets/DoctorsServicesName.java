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
public class DoctorsServicesName {
      int id;
    String name;

    public DoctorsServicesName() {
    }

    public DoctorsServicesName(int id, String name) {
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
        PreparedStatement pt = db.get.Prepare("insert into `doctor_services_names`(`name`) values (?)");
        pt.setString(1, results);
        pt.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        PreparedStatement ps = db.get.Prepare("UPDATE `doctor_services_names` SET `name`=? WHERE `id`=?");
        ps.setString(1, name);
        ps.setInt(2, id);
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `doctor_services_names` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public static ObservableList<DoctorsServicesName> getData() throws Exception {
        ObservableList<DoctorsServicesName> data = FXCollections.observableArrayList();
        String SQL = "SELECT * FROM `doctor_services_names`";

        ResultSet rs = db.get.getReportCon().createStatement().executeQuery(SQL);

        while (rs.next()) {
            data.add(new DoctorsServicesName(rs.getInt(1), rs.getString(2)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `doctor_services_names`").getValueAt(0, 0).toString();
    }
}
