/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.accounts.assets;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author AHMED
 */
public class Category {

    int id;
    String name;

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Category() {
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

    public static boolean Add(String cat) throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `categorys`(`name`) VALUES (?)");
        ps.setString(1, cat);
        ps.execute();
        return true;
    }

    public static boolean AddFromRecption(String cat) throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `categorys`(`name`) VALUES (?)");
        ps.setString(1, cat);
        ps.execute();

        PreparedStatement pss = db.get.Prepare("INSERT INTO `reception_cat`(`cat_id`) SELECT `id` FROM `categorys` WHERE `name`=?");
        pss.setString(1, cat);
        pss.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        PreparedStatement ps = db.get.Prepare("UPDATE `categorys` SET `name`=? WHERE `id`=?");
        ps.setString(1, name);
        ps.setInt(2, id);
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `categorys` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public static ObservableList<Category> getData() throws Exception {
        ObservableList<Category> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `id`, `name` FROM `categorys`");
        while (rs.next()) {
            data.add(new Category(rs.getInt(1), rs.getString(2)));
        }
        return data;
    }

    public static ObservableList<Category> getRecptionData() throws Exception {
        ObservableList<Category> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `id`, `name` FROM `categorys` where id in (SELECT `cat_id` FROM `reception_cat`)");
        while (rs.next()) {
            data.add(new Category(rs.getInt(1), rs.getString(2)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `categorys`").getValueAt(0, 0).toString();
    }
}
