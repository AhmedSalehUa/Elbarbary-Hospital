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
public class SurgeriesType {

    int id;
    String type;

    public SurgeriesType() {
    }

    public SurgeriesType(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static boolean Add(String type) throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `admission_services_surgeries_type`(`type`) VALUES (?)");
        ps.setString(1, type);
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        PreparedStatement ps = db.get.Prepare("UPDATE `admission_services_surgeries_type` SET  `type`=? WHERE `id`=?");
        ps.setString(1, type);
        ps.setInt(2, id);
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `admission_services_surgeries_type` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public static ObservableList<SurgeriesType> getData() throws Exception {
        ObservableList<SurgeriesType> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT * FROM `admission_services_surgeries_type`");
        while (rs.next()) {
            data.add(new SurgeriesType(rs.getInt(1), rs.getString(2)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `admission_services_surgeries_type`").getValueAt(0, 0).toString();
    }
}
