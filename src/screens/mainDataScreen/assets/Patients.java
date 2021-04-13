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
public class Patients {

    int id;
    String name;
    String government;
    String address;
    String age;
    int tranportOrgId;
    String tranportOrg;
    String dateOfBirth;
    String national_id;
    String gender;
    String diagnosis;
    String doctor_name;
    int doctor_id;
    String tele1;
    String tele2;

    InputStream photo;
    String photoExt;

    public Patients() {
    }

    public Patients(int id, String name, String address, String age, String national_id, String diagnosis, int doctor_id, String tele1, String tele2) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.age = age;
        this.national_id = national_id;
        this.diagnosis = diagnosis;
        this.doctor_id = doctor_id;
        this.tele1 = tele1;
        this.tele2 = tele2;

    }

    public Patients(int id, String name, String government, String address, String dateOfBirth, String age, String national_id, String doctor_name, String tranportOrg, String diagnosis, String tele1, String tele2, String gender) {
        this.id = id;
        this.name = name;
        this.government = government;
        this.address = address;
        this.age = age;
        this.tranportOrg = tranportOrg;
        this.dateOfBirth = dateOfBirth;
        this.national_id = national_id;
        this.diagnosis = diagnosis;
        this.doctor_name = doctor_name;
        this.tele1 = tele1;
        this.tele2 = tele2;
        this.gender = gender;
    }

    public Patients(int id, String name, String address, String age, String national_id, String doctor_name, String diagnosis, String tele1, String tele2) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.age = age;
        this.national_id = national_id;
        this.diagnosis = diagnosis;
        this.doctor_name = doctor_name;
        this.tele1 = tele1;
        this.tele2 = tele2;

    }

    public Patients(int id, String name, String address, String age, String national_id, String diagnosis, String doctor_name, String tele1, String tele2, InputStream photo, String photoExt) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.age = age;
        this.national_id = national_id;
        this.doctor_name = doctor_name;
        this.diagnosis = diagnosis;
        this.tele1 = tele1;
        this.tele2 = tele2;
        this.photo = photo;
        this.photoExt = photoExt;
    }

    public Patients(int id, String name, String address, String age, String national_id, String diagnosis, int doctor_id, String tele1, String tele2, InputStream photo, String photoExt) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.age = age;
        this.national_id = national_id;
        this.doctor_id = doctor_id;
        this.diagnosis = diagnosis;
        this.tele1 = tele1;
        this.tele2 = tele2;
        this.photo = photo;
        this.photoExt = photoExt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
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

    public String getNational_id() {
        return national_id;
    }

    public void setNational_id(String national_id) {
        this.national_id = national_id;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
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

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getGovernment() {
        return government;
    }

    public void setGovernment(String government) {
        this.government = government;
    }

    public int getTranportOrgId() {
        return tranportOrgId;
    }

    public void setTranportOrgId(int tranportOrgId) {
        this.tranportOrgId = tranportOrgId;
    }

    public String getTranportOrg() {
        return tranportOrg;
    }

    public void setTranportOrg(String tranportOrg) {
        this.tranportOrg = tranportOrg;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean Add() throws Exception {
        PreparedStatement st = db.get.Prepare("INSERT INTO `patients`(`id`, `name`, `government`, `address`, `date_of_birth`, `age`, `national_id`, `doctor_id`, `transport_org_id`, `diagnosis`, `tele1`, `tele2`,`gender`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");
        st.setInt(1, id);
        st.setString(2, name);
        st.setString(3, government);
        st.setString(4, address);
        st.setString(5, dateOfBirth);
        st.setString(6, age);
        st.setString(7, national_id);
        st.setInt(8, doctor_id);
        st.setInt(9, tranportOrgId);
        st.setString(10, diagnosis);
        st.setString(11, tele1);
        st.setString(12, tele2);
        st.setString(13, gender);
        st.execute();
        return true;
    }

    public boolean AddWithPhoto() throws Exception {
        Add();
        PreparedStatement st = db.get.Prepare("INSERT INTO `patients_photo`(`patient_id`, `photo`, `ext`) VALUES (?,?,?)");
        st.setInt(1, id);
        st.setBlob(2, photo);
        st.setString(3, photoExt);
        st.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        PreparedStatement st = db.get.Prepare("UPDATE `patients` SET `name`=?,`address`=?,`age`=?,`national_id`=?,`doctor_id`=?,`diagnosis`=?,`tele1`=?,`tele2`=?,`government`=?,`date_of_birth`=?,`transport_org_id`=?,`gender`=? WHERE `id`=?");
        st.setInt(13, id);
        st.setString(1, name);
        st.setString(2, address);
        st.setString(3, age);
        st.setString(4, national_id);
        st.setInt(5, doctor_id);
        st.setString(6, diagnosis);
        st.setString(7, tele1);
        st.setString(8, tele2);
        st.setString(9, government);
        st.setString(10, dateOfBirth);
        st.setInt(11, tranportOrgId);
        st.setString(12, gender);
        st.execute();
        return true;

    }

    public boolean EditeWithPhoto() throws Exception {
        Edite();
        PreparedStatement st = db.get.Prepare("INSERT INTO patients_photo (`patient_id`, `photo`, `ext`) VALUES (?,?,?) ON DUPLICATE KEY UPDATE `photo` = ?,`ext`=?");
        st.setInt(1, id);
        st.setBlob(2, photo);
        st.setString(3, photoExt);
        st.setBlob(4, photo);
        st.setString(5, photoExt);
        st.execute();
        return true;

    }

    public boolean Delete() throws Exception {
        PreparedStatement st = db.get.Prepare("DELETE FROM `patients` WHERE `id`=?");
        st.setInt(1, id);

        PreparedStatement sta = db.get.Prepare("DELETE FROM `patients_photo` WHERE `patient_id`=?");
        sta.setInt(1, id);
        sta.execute();
        st.execute();
        return true;
    }

    public static ObservableList<Patients> getData() throws Exception {

        ObservableList<Patients> data = FXCollections.observableArrayList();

        String SQL = "SELECT `patients`.`id`, `patients`.`name`,`patients`.`government`, `patients`.`address`, `patients`.`date_of_birth`, `patients`.`age`, `patients`.`national_id`, `doctors`.`name`, `patient_transporting_org`.`name`, `patients`.`diagnosis`, `patients`.`tele1`, `patients`.`tele2`,`patients`.`gender` FROM `patient_transporting_org`,`patients`,`doctors` WHERE `doctors`.`id` =`patients`.`doctor_id` AND `patient_transporting_org`.`id` =  `patients`.`transport_org_id`";

        ResultSet rs = db.get.getReportCon().createStatement().executeQuery(SQL);

        while (rs.next()) {
            data.add(new Patients(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(max(`id`) + 1,'1') FROM `patients`").getValueAt(0, 0).toString();
    }

    public void getNationalIdPhoto() throws Exception {

        File file = null;
        String selectSQL = "SELECT `photo` FROM `patients_photo` WHERE `patient_id`='" + id + "'";
        JTable tab = db.get.getTableData("SELECT `ext` FROM `patients_photo` WHERE `patient_id`='" + id + "'");

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
