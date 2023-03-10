package screens.drugs.assets;

import db.get;
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

public class DrugsPatients {

    int id;
    String name;
    String government;
    String address;
    String age;
    int tranportOrgId;
    String tranportOrg;
    String dateOfBirth;
    String dateOfEntrance;
    String dateOfExit;
    String national_id;
    String gender;
    String diagnosis;
    String doctor_name;
    int doctor_id;
    String tele1;
    String tele2;
    InputStream photo;
    String photoExt;

    public DrugsPatients() {
    }

    public DrugsPatients(int id, String name, String address, String age, String national_id, String diagnosis, int doctor_id, String tele1, String tele2) {
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

    public DrugsPatients(int id, String name, String government, String address, String dateOfBirth, String age, String national_id, String doctor_name, String tranportOrg, String diagnosis, String tele1, String tele2, String gender) {
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

    public DrugsPatients(int id, String name, String government, String address, String dateOfBirth, String age, String national_id, String doctor_name, String tranportOrg, String diagnosis, String tele1, String tele2, String gender, String dateOfEntrance, String dateOfExit) {
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
        this.dateOfEntrance = dateOfEntrance;
        this.dateOfExit = dateOfExit; 
    }

    public DrugsPatients(int id, String name, String address, String age, String national_id, String doctor_name, String diagnosis, String tele1, String tele2) {
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

    public DrugsPatients(int id, String name, String address, String age, String national_id, String diagnosis, String doctor_name, String tele1, String tele2, InputStream photo, String photoExt) {
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

    public DrugsPatients(int id, String name, String address, String age, String national_id, String diagnosis, int doctor_id, String tele1, String tele2, InputStream photo, String photoExt) {
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
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return this.name;
    }

    public String getDateOfEntrance() {
        return dateOfEntrance;
    }

    public void setDateOfEntrance(String dateOfEntrance) {
        this.dateOfEntrance = dateOfEntrance;
    }

    public String getDateOfExit() {
        return dateOfExit;
    }

    public void setDateOfExit(String dateOfExit) {
        this.dateOfExit = dateOfExit;
    }
    

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAge() {
        return this.age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getTele1() {
        return this.tele1;
    }

    public void setTele1(String tele1) {
        this.tele1 = tele1;
    }

    public String getTele2() {
        return this.tele2;
    }

    public void setTele2(String tele2) {
        this.tele2 = tele2;
    }

    public String getNational_id() {
        return this.national_id;
    }

    public void setNational_id(String national_id) {
        this.national_id = national_id;
    }

    public String getDiagnosis() {
        return this.diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public InputStream getPhoto() {
        return this.photo;
    }

    public void setPhoto(InputStream photo) {
        this.photo = photo;
    }

    public String getPhotoExt() {
        return this.photoExt;
    }

    public void setPhotoExt(String photoExt) {
        this.photoExt = photoExt;
    }

    public String getDoctor_name() {
        return this.doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public int getDoctor_id() {
        return this.doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getGovernment() {
        return this.government;
    }

    public void setGovernment(String government) {
        this.government = government;
    }

    public int getTranportOrgId() {
        return this.tranportOrgId;
    }

    public void setTranportOrgId(int tranportOrgId) {
        this.tranportOrgId = tranportOrgId;
    }

    public String getTranportOrg() {
        return this.tranportOrg;
    }

    public void setTranportOrg(String tranportOrg) {
        this.tranportOrg = tranportOrg;
    }

    public String getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean Add() throws Exception {
        PreparedStatement st = get.Prepare("INSERT INTO `drg_patient`(`id`, `name`, `government`, `address`, `date_of_birth`, `age`, `national_id`, `doctor_id`, `transport_org_id`, `diagnosis`, `tele1`, `tele2`,`gender`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");
        st.setInt(1, this.id);
        st.setString(2, this.name);
        st.setString(3, this.government);
        st.setString(4, this.address);
        st.setString(5, this.dateOfBirth);
        st.setString(6, this.age);
        st.setString(7, this.national_id);
        st.setInt(8, this.doctor_id);
        st.setInt(9, this.tranportOrgId);
        st.setString(10, this.diagnosis);
        st.setString(11, this.tele1);
        st.setString(12, this.tele2);
        st.setString(13, this.gender);
        st.execute();
        return true;
    }

    public boolean AddWithPhoto() throws Exception {
        Add();
        PreparedStatement st = get.Prepare("INSERT INTO `drg_patient_photo`(`patient_id`, `photo`, `ext`) VALUES (?,?,?)");
        st.setInt(1, this.id);
        st.setBlob(2, this.photo);
        st.setString(3, this.photoExt);
        st.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        PreparedStatement st = get.Prepare("UPDATE `drg_patient` SET `name`=?,`address`=?,`age`=?,`national_id`=?,`doctor_id`=?,`diagnosis`=?,`tele1`=?,`tele2`=?,`government`=?,`date_of_birth`=?,`transport_org_id`=?,`gender`=? WHERE `id`=?");
        st.setInt(13, this.id);
        st.setString(1, this.name);
        st.setString(2, this.address);
        st.setString(3, this.age);
        st.setString(4, this.national_id);
        st.setInt(5, this.doctor_id);
        st.setString(6, this.diagnosis);
        st.setString(7, this.tele1);
        st.setString(8, this.tele2);
        st.setString(9, this.government);
        st.setString(10, this.dateOfBirth);
        st.setInt(11, this.tranportOrgId);
        st.setString(12, this.gender);
        st.execute();
        return true;
    }

    public boolean EditeWithPhoto() throws Exception {
        Edite();
        PreparedStatement st = get.Prepare("INSERT INTO drg_patient_photo (`patient_id`, `photo`, `ext`) VALUES (?,?,?) ON DUPLICATE KEY UPDATE `photo` = ?,`ext`=?");
        st.setInt(1, this.id);
        st.setBlob(2, this.photo);
        st.setString(3, this.photoExt);
        st.setBlob(4, this.photo);
        st.setString(5, this.photoExt);
        st.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement st = get.Prepare("DELETE FROM `drg_patient` WHERE `id`=?");
        st.setInt(1, this.id);

        PreparedStatement sta = get.Prepare("DELETE FROM `drg_patient_photo` WHERE `patient_id`=?");
        sta.setInt(1, this.id);
        sta.execute();
        DrugsAccounts dg = new DrugsAccounts();
        dg.setPaitent_id(id);
        dg.DeleteByPatient();
        st.execute();
        return true;
    }

    public static ObservableList<DrugsPatients> getData() throws Exception {
        ObservableList<DrugsPatients> data = FXCollections.observableArrayList();

        String SQL = "SELECT `drg_patient`.`id`, `drg_patient`.`name`,`drg_patient`.`government`, `drg_patient`.`address`, `drg_patient`.`date_of_birth`, `drg_patient`.`age`, `drg_patient`.`national_id`, `doctors`.`name`, `patient_transporting_org`.`name`, `drg_patient`.`diagnosis`, `drg_patient`.`tele1`, `drg_patient`.`tele2`,`drg_patient`.`gender` FROM `patient_transporting_org`,`drg_patient`,`doctors` WHERE `doctors`.`id` =`drg_patient`.`doctor_id` AND `patient_transporting_org`.`id` =  `drg_patient`.`transport_org_id`";

        ResultSet rs = get.getReportCon().createStatement().executeQuery(SQL);

        while (rs.next()) {
            data.add(new DrugsPatients(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13)));
        }
        return data;
    }

    public static ObservableList<DrugsPatients> getOverrideData() throws Exception {
        ObservableList<DrugsPatients> data = FXCollections.observableArrayList();

        String SQL = "SELECT `drg_patient`.`id`, `drg_patient`.`name`, `drg_patient`.`government`, `drg_patient`.`address`, `drg_patient`.`date_of_birth`, `drg_patient`.`age`, `drg_patient`.`national_id`, `doctors`.`name`, `patient_transporting_org`.`name`, `drg_patient`.`diagnosis`, `drg_patient`.`tele1`, `drg_patient`.`tele2`, `drg_patient`.`gender`, `drg_accounts`.`date_of_entrance`, `drg_accounts`.`date_of_exite` FROM `patient_transporting_org`, `drg_patient`, `doctors`,`drg_accounts` WHERE `doctors`.`id` = `drg_patient`.`doctor_id` AND `patient_transporting_org`.`id` = `drg_patient`.`transport_org_id` AND `drg_accounts`.`patient_id` = `drg_patient`.`id`";

        ResultSet rs = get.getReportCon().createStatement().executeQuery(SQL);

        while (rs.next()) { 
            data.add(new DrugsPatients(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15)));
        }
        return data;
    }

    public static ObservableList<DrugsPatients> getData(int id) throws Exception {
        ObservableList<DrugsPatients> data = FXCollections.observableArrayList();

        String SQL = "SELECT `drg_patient`.`id`, `drg_patient`.`name`,`drg_patient`.`government`, `drg_patient`.`address`, `drg_patient`.`date_of_birth`, `drg_patient`.`age`, `drg_patient`.`national_id`, `doctors`.`name`, `patient_transporting_org`.`name`, `drg_patient`.`diagnosis`, `drg_patient`.`tele1`, `drg_patient`.`tele2`,`drg_patient`.`gender` FROM `patient_transporting_org`,`drg_patient`,`doctors` WHERE `doctors`.`id` =`drg_patient`.`doctor_id` AND `patient_transporting_org`.`id` =  `drg_patient`.`transport_org_id` and `drg_patient`.`id`='" + id + "'";

        ResultSet rs = get.getReportCon().createStatement().executeQuery(SQL);

        while (rs.next()) {
            data.add(new DrugsPatients(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return get.getTableData("SELECT IFNULL(max(`id`) + 1,'1') FROM `drg_patient`").getValueAt(0, 0).toString();
    }

    public void getNationalIdPhoto() throws Exception {
        File file = null;
        String selectSQL = "SELECT `photo` FROM `drg_patient_photo` WHERE `patient_id`='" + this.id + "'";
        JTable tab = get.getTableData("SELECT `ext` FROM `drg_patient_photo` WHERE `patient_id`='" + this.id + "'");

        if (tab.getRowCount() <= 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("لا يوجد صورة متوفرة");
            alert.show();
        } else {
            String ext = tab.getValueAt(0, 0).toString();
            ResultSet rs = null;

            try {
                PreparedStatement pstmt = get.Prepare(selectSQL);

                rs = pstmt.executeQuery();

                File directory = new File(System.getProperty("user.home") + "\\Desktop\\Elbarbary System\\img");
                directory.mkdirs();

                file = new File(directory + "\\" + this.id + "-" + this.name + "." + ext);

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
