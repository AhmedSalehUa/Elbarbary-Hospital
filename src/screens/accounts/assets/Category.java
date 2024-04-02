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
    int parentId;

    public Category(int id, String name, int parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
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

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static boolean Add(String cat, int parentId) throws Exception {
        if (parentId == 0) {
            PreparedStatement ps = db.get.Prepare("INSERT INTO `categorys`(`name`) VALUES (?)");
            ps.setString(1, cat);
            ps.execute();
            return true;
        } else {
            PreparedStatement ps = db.get.Prepare("INSERT INTO `categorys`(`name`,`parent_cat`) VALUES (?,?)");
            ps.setString(1, cat);
            ps.setInt(2, parentId);
            ps.execute();
            return true;
        }
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
        PreparedStatement ps = db.get.Prepare("UPDATE `categorys` SET `name`=?,`parent_cat`=? WHERE `id`=?");
        ps.setString(1, name);
        ps.setInt(2, parentId);
        ps.setInt(3, id);
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
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `id`, `name`,`parent_cat` FROM `categorys`");
        while (rs.next()) {
            data.add(new Category(rs.getInt(1), rs.getString(2), rs.getInt(3)));
        }
        return data;
    }

    public static ObservableList<Category> getMainData() throws Exception {
        ObservableList<Category> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `id`, `name`, `parent_cat` FROM `categorys` WHERE `parent_cat` IS Null");
        while (rs.next()) {
            data.add(new Category(rs.getInt(1), rs.getString(2), rs.getInt(3)));
        }
        return data;
    }

    public static ObservableList<Category> getSecData(int id) throws Exception {
        ObservableList<Category> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `id`, `name`, `parent_cat` FROM `categorys` WHERE `parent_cat` ='" + id + "'");
        while (rs.next()) {
            data.add(new Category(rs.getInt(1), rs.getString(2), rs.getInt(3)));
        }
        return data;
    }

    public static ObservableList<Category> getRecptionData() throws Exception {
        ObservableList<Category> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `id`, `name`, `parent_cat` FROM `categorys` where id in (SELECT `cat_id` FROM `reception_cat`)");
        while (rs.next()) {
            data.add(new Category(rs.getInt(1), rs.getString(2), rs.getInt(3)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `categorys`").getValueAt(0, 0).toString();
    }
}
