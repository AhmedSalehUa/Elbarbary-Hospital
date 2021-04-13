/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.mainDataScreen.assets;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javax.swing.JTable;

/**
 *
 * @author ahmed
 */
public class PatientsEscort {

    int id;
    int patientId;
    String name;
    String relation;
    String Address;
    String nationaId;
    String tele1;
    String tele2;
    InputStream photo;
    String photoExt;

    public PatientsEscort() {
    }

    public PatientsEscort(int id, String name, String relation, String Address, String nationaId, String tele1, String tele2) {
        this.id = id;
        this.name = name;
        this.relation = relation;
        this.Address = Address;
        this.nationaId = nationaId;
        this.tele1 = tele1;
        this.tele2 = tele2; 
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getTele1() {
        return tele1;
    }

    public void setTele1(String tele1) {
        this.tele1 = tele1;
    }

    public String getTele2() {
        return tele2;
    }

    public void setTele2(String tele2) {
        this.tele2 = tele2;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getNationaId() {
        return nationaId;
    }

    public void setNationaId(String nationaId) {
        this.nationaId = nationaId;
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

    public boolean Add() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `patient_escort`(`id`, `patient_id`, `name`, `relation`, `address`, `national_id`, `tele1`, `tele2`, `national_photo`, `ext`) VALUES (?,?,?,?,?,?,?,?,?,?)");
        ps.setInt(1, id);
        ps.setInt(2, patientId);
        ps.setString(3, name);
        ps.setString(4, relation);
        ps.setString(5, Address);
        ps.setString(6, nationaId);
        ps.setString(7, tele1);
        ps.setString(8, tele2);
        ps.setBlob(9, photo);
        ps.setString(10, photoExt);
        ps.execute();
        return true;
    }

    public boolean AddWithoutPhoto() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `patient_escort`(`id`, `patient_id`, `name`, `relation`, `address`, `national_id`, `tele1`, `tele2`) VALUES (?,?,?,?,?,?,?,?)");
        ps.setInt(1, id);
        ps.setInt(2, patientId);
        ps.setString(3, name);
        ps.setString(4, relation);
        ps.setString(5, Address);
        ps.setString(6, nationaId);
        ps.setString(7, tele1);
        ps.setString(8, tele2);
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        PreparedStatement ps = db.get.Prepare("UPDATE `patient_escort` SET `patient_id`=?,`name`=?,`relation`=?,`national_id`=?,`tele1`=?,`national_photo`=?,`ext`=?,`address`=?,`tele2`=? WHERE `id`=?");
        ps.setInt(10, id);
        ps.setInt(1, patientId);
        ps.setString(2, name);
        ps.setString(3, relation);
        ps.setString(4, nationaId);
        ps.setString(5, tele1);
        ps.setBlob(6, photo);
        ps.setString(7, photoExt);
        ps.setString(8, Address);
        ps.setString(9, tele2);
        ps.execute();
        return true;
    }

    public boolean EditeWithoutPhoto() throws Exception {
        PreparedStatement ps = db.get.Prepare("UPDATE `patient_escort` SET `patient_id`=?,`name`=?,`relation`=?,`national_id`=?,`tele1`=?,`address`=?,`tele2`=? WHERE `id`=?");
        ps.setInt(8, id);
        ps.setInt(1, patientId);
        ps.setString(2, name);
        ps.setString(3, relation);
        ps.setString(4, nationaId);
        ps.setString(5, tele1);
        ps.setString(6, Address);
        ps.setString(7, tele2);
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `patient_escort` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public static ObservableList<PatientsEscort> getData(String patientId) throws Exception {
        ObservableList<PatientsEscort> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `id`,`name`, `relation`, `address`,`national_id`, `tele1`,`tele2` FROM `patient_escort` WHERE `patient_id` in (SELECT `id` FROM `patients` WHERE `name`='"+ patientId+"')");
        while (rs.next()) {
            data.add(new PatientsEscort(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("select IFNULL(MAX(`id`)+1,1) from patient_escort").getValueAt(0, 0).toString();
    }
    
    public void getNationalIdPhoto() throws Exception {

        File file = null;
        String selectSQL = "SELECT `national_photo` FROM `patient_escort` WHERE `id`='" + id + "'";
        JTable tab = db.get.getTableData("SELECT `ext` FROM `patient_escort` WHERE `id`='" + id + "'");

        if (tab.getRowCount() <= 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("لا يوجد صورة متوفرة");
            alert.show();
        } else {
            String ext = tab.getValueAt(0, 0).toString();
            ResultSet rs = null;

            try {
                PreparedStatement pstmt = db.get.Prepare(selectSQL);
                // set parameter;

                rs = pstmt.executeQuery();

                File directory = new File(System.getProperty("user.home") + "\\Desktop\\Elbarbary System\\img");
                directory.mkdirs();

                file = new File(directory + "\\" + id + "-" + name + "." + ext);

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
}
