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
public class Accounts {

    int id;
    String name;
    String credite;

    public Accounts() {
    }

    public Accounts(int id, String name, String credite) {
        this.id = id;
        this.name = name;
        this.credite = credite;
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

    public String getCredite() {
        return credite;
    }

    public void setCredite(String credite) {
        this.credite = credite;
    }

    public boolean Add() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `accounts`(`id`, `name`, `credit`) VALUES (?,?,?)");
        ps.setInt(1, id);
        ps.setString(2, name);
        ps.setString(3, credite);
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        PreparedStatement ps = db.get.Prepare("UPDATE `accounts` SET `name`=?,`credit`=? WHERE `id`=?");
        ps.setInt(3, id);
        ps.setString(1, name);
        ps.setString(2, credite);
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `accounts` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public static ObservableList<Accounts> getData() throws Exception {
        ObservableList<Accounts> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT * FROM `accounts`");
        while (rs.next()) {
            data.add(new Accounts(rs.getInt(1), rs.getString(2), rs.getString(3)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `accounts`").getValueAt(0, 0).toString();
    }
}
