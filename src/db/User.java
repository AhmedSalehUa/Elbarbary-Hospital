/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import assets.classes.AlertDialogs;
import static assets.classes.statics.USER_ID;
import static assets.classes.statics.USER_ROLE;
import elbarbary.hospital.ElBarbaryHospital;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author AHMED
 */
public class User {

    int id;
    String name;
    String password;
    String role;

    public User() {
    }

    public User(int id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean checkSignIn() {
        try {
            ResultSet rs = db.get.getReportCon().createStatement().executeQuery("select id,role from users where user_name='" + name + "' and password='" + password + "'");
            while (rs.next()) {
                this.id = rs.getInt(1);
                this.role = rs.getString(2);
                configPermissions();
                return true;
            }
            return false;
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
        return false;
    }

    public boolean changePassword() {
        try {
            PreparedStatement Prepare = db.get.Prepare("UPDATE `users` SET `password`=? WHERE `id`=?");
            Prepare.setString(1, password);
            Prepare.setInt(2, id);
            Prepare.execute();
            return true;
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
            return false;
        }
    }

    public static boolean canAccess(String priviliages) {
        Preferences prefs = Preferences.userNodeForPackage(ElBarbaryHospital.class);
        if (prefs.get(USER_ROLE, "user").equals("super_admin")) {
            return true;
        }
        try {
            db.get.getReportCon().createStatement().execute("INSERT IGNORE INTO `priviliges_name`(`name`) VALUES ('" + priviliages + "')");
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
        try {
            String strt = "SELECT value FROM `users_permissions` WHERE `user_id`='" + prefs.get(USER_ID, "0") + "'";
            ResultSet rs = db.get.getReportCon().createStatement().executeQuery(strt);
            while (rs.next()) {
                return Boolean.parseBoolean(rs.getString(1));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private boolean configPermissions() throws Exception {
//        Preferences prefs = Preferences.userNodeForPackage(ElBarbaryHospital.class);
//         ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT * FROM `users_permissions` WHERE `user_id`='" + id + "'");
//        while (rs.next()) {
//            prefs.put(rs.getString(2), rs.getString(3));
//        }
        return true;
    }

    public boolean Add() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `users`(`id`, `user_name`, `password`, `role`) VALUES (?,?,?,?)");
        ps.setInt(1, id);
        ps.setString(2, name);
        ps.setString(3, "123456");
        ps.setString(4, role);
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        PreparedStatement ps = db.get.Prepare("UPDATE `users` SET `user_name`=?,`role`=? WHERE `id`=?");
        ps.setInt(3, id);
        ps.setString(1, name);
        ps.setString(2, role);
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `users` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public static ObservableList<User> getData() throws Exception {
        ObservableList<User> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT * FROM `users`");
        while (rs.next()) {
            data.add(new User(rs.getInt(1), rs.getString(2), rs.getString(4)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `users`").getValueAt(0, 0).toString();
    }

}
