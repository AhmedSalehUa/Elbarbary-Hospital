/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.mainDataScreen.assets;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;

/**
 *
 * @author ahmed
 */
public class Contract {

    int id;
    String name;
    String discount;
    String examinationCost;
    String date;
    InputStream photo;
    String photoExt;

    public Contract() {
    }

    public Contract(int id, String name, String discount, String examinationCost, String date, InputStream photo, String photoExt) {
        this.id = id;
        this.name = name;
        this.discount = discount;
        this.date = date;
        this.photo = photo;
        this.photoExt = photoExt;
        this.examinationCost = examinationCost;
    }

    public Contract(int id, String name, String discount, String examinationCost, String date) {
        this.id = id;
        this.name = name;
        this.discount = discount;
        this.examinationCost = examinationCost;
        this.date = date;
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

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public InputStream getPhoto() {
        return photo;
    }

    public void setPhoto(InputStream photo) {
        this.photo = photo;
    }

    public String getPhotoExt() {
        return photoExt;
    }

    public void setPhotoExt(String photoExt) {
        this.photoExt = photoExt;
    }

    public String getExaminationCost() {
        return examinationCost;
    }

    public void setExaminationCost(String examinationCost) {
        this.examinationCost = examinationCost;
    }

    public boolean Add() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `contracts`(`id`, `name`, `discount`, `examination_cost`, `date`, `photo`, `photoExt`) VALUES (?,?,?,?,?,?,?)");
        ps.setInt(1, id);
        ps.setString(2, name);
        ps.setString(3, discount);
        ps.setString(4, examinationCost == null ? "0" : examinationCost);
        ps.setString(5, date);
        ps.setBlob(6, photo);
        ps.setString(7, photoExt);
        ps.execute();
        return true;
    }

    public boolean AddWithoutPhoto() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `contracts`(`id`, `name`, `discount`, `examination_cost`, `date`) VALUES (?,?,?,?,?)");
        ps.setInt(1, id);
        ps.setString(2, name);
        ps.setString(3, discount);
        ps.setString(4, examinationCost == null ? "0" : examinationCost);
        ps.setString(5, date);

        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        PreparedStatement ps = db.get.Prepare("UPDATE `contracts` SET `name`=?,`discount`=?, `examination_cost`=?,`date`=?,`photo`=?,`photoExt`=? WHERE `id`=?");
        ps.setInt(7, id);
        ps.setString(1, name);
        ps.setString(2, discount);
        ps.setString(3, examinationCost == null ? "0" : examinationCost);
        ps.setString(4, date);
        ps.setBlob(5, photo);
        ps.setString(6, photoExt);
        ps.execute();
        return true;
    }

    public boolean EditeWithoutPhoto() throws Exception {
        PreparedStatement ps = db.get.Prepare("UPDATE `contracts` SET `name`=?,`discount`=?, `examination_cost`=?,`date`=? WHERE `id`=?");
        ps.setInt(5, id);
        ps.setString(1, name);
        ps.setString(2, discount);
        ps.setString(3, examinationCost == null ? "0" : examinationCost);
        ps.setString(4, date);
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `contracts` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public static ObservableList<Contract> getData() throws Exception {
        ObservableList<Contract> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `id`, `name`, `discount`, `examination_cost`, `date` FROM `contracts`");
        while (rs.next()) {
            data.add(new Contract(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(max(`id`)+1,1) FROM `contracts`").getValueAt(0, 0).toString();
    }

    public void getContractPhoto() throws Exception {

        File file = null;
        String selectSQL = "SELECT `photo` FROM `contracts` WHERE `id`='" + id + "'";
        String ext = db.get.getTableData("SELECT `photoExt` FROM `contracts` WHERE `id`='" + id + "'").getValueAt(0, 0).toString();
        ResultSet rs = null;

        try {
            PreparedStatement pstmt = db.get.Prepare(selectSQL);
            // set parameter;

            rs = pstmt.executeQuery();

            File directory = new File(System.getProperty("user.home") + "\\Desktop\\Elbarbary System\\img");
            directory.mkdirs();

            file = new File(directory + "\\" + id + "-" + date + "." + ext);

            FileOutputStream output = new FileOutputStream(file);

            String payPath = file.getAbsolutePath();
            while (rs.next()) {
                InputStream input = rs.getBinaryStream("photo");
                byte[] buffer = new byte[1024];
                while (input.read(buffer) > 0) {
                    output.write(buffer);
                }
            }
            Desktop d = Desktop.getDesktop();
            d.open(file);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }
}
