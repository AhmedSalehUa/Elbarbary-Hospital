/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.admission.assets;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ahmed
 */
public class Examination {

    int id;
    int clincId;
    String clinc;
    String doctor;
    int doctor_id;
    int admissionId;
    String date;
    String type;
    String cost;

    public Examination() {
    }

    public Examination(int id, int clincId, int admissionId, String date, String type, String cost) {
        this.id = id;
        this.clincId = clincId;
        this.admissionId = admissionId;
        this.date = date;
        this.type = type;
        this.cost = cost;
    }

    public Examination(int id, String clinc, String doctor, String type, String cost) {
        this.id = id;
        this.clinc = clinc;
        this.doctor = doctor;
        this.type = type;
        this.cost = cost;
    }

    public String getClinc() {
        return clinc;
    }

    public void setClinc(String clinc) {
        this.clinc = clinc;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClincId() {
        return clincId;
    }

    public void setClincId(int clincId) {
        this.clincId = clincId;
    }

    public int getAdmissionId() {
        return admissionId;
    }

    public void setAdmissionId(int admissionId) {
        this.admissionId = admissionId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public boolean Add() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `examinations`(`id`, `clinc_id`, `admission_id`, `doctor_id`, `date`, `type`, `cost`) VALUES (?,?,?,?,?,?,?)");
        ps.setInt(1, id);
        ps.setInt(2, clincId);
        ps.setInt(3, admissionId);
        ps.setInt(4, doctor_id);
        ps.setString(5, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        ps.setString(6, type);
        String costs = getActualCost();
        ps.setString(7, costs);
        Admission.addCostToAdmission(admissionId, costs);
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        Admission.removeCostToAdmission(admissionId, db.get.getTableData("SELECT `cost` FROM `examinations` WHERE `admission_id`='" + admissionId + "' And `id`='" + id + "'").getValueAt(0, 0).toString());
        PreparedStatement ps = db.get.Prepare("UPDATE `examinations` SET `clinc_id`=?,`admission_id`=?,`doctor_id`=?,`type`=?,`cost`=? WHERE `id`=?");
        ps.setInt(6, id);
        ps.setInt(1, clincId);
        ps.setInt(2, admissionId);
        ps.setInt(3, doctor_id);
        ps.setString(4, type);
        String costs = getActualCost();
        ps.setString(5, costs);
        Admission.addCostToAdmission(admissionId, costs);
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        Admission.removeCostToAdmission(admissionId, db.get.getTableData("SELECT `cost` FROM `examinations` WHERE `admission_id`='" + admissionId + "' And `id`='" + id + "'").getValueAt(0, 0).toString());
        PreparedStatement ps = db.get.Prepare("DELETE FROM `examinations` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public static ObservableList<Examination> getData(int admissionId) throws Exception {
        ObservableList<Examination> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `examinations`.`id`,`clincs`.`name`, `doctors`.`name` ,  `examinations`.`type`, `examinations`.`cost` FROM `examinations`,`clincs`,`doctors` WHERE  `examinations`.`clinc_id`=`clincs`.`id` and `examinations`.`admission_id`='" + admissionId + "' and `examinations`.`doctor_id`=`doctors`.`id`");
        while (rs.next()) {
            data.add(new Examination(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("select IFNULL(max(`id`)+1,1) from examinations").getValueAt(0, 0).toString();
    }

    public String getActualCost() throws Exception {
        String contractExaminationCost = db.get.getTableData("SELECT IFNULL(sum( `examination_cost`),'0') as 'cost' FROM `contracts` WHERE `id`in(SELECT `contract_id` FROM `admission` WHERE `id`='" + admissionId + "')").getValueAt(0, 0).toString();
        String contractDiscpunt = db.get.getTableData("SELECT IFNULL(sum( `discount`),'0') as 'cost' FROM `contracts` WHERE `id`in(SELECT `contract_id` FROM `admission` WHERE `id`='" + admissionId + "')").getValueAt(0, 0).toString();

        String clincCost = "";
        switch (type) {

            case "كشف":
                clincCost = db.get.getTableData("SELECT `examinationCost` FROM `clincs` WHERE `id`='" + clincId + "'").getValueAt(0, 0).toString();
                break;
            case "اعادة":
                clincCost = db.get.getTableData("SELECT `reExaminationCost` FROM `clincs` WHERE `id`='" + clincId + "'").getValueAt(0, 0).toString();
                break;
            case "استشارة":
                clincCost = db.get.getTableData("SELECT `illustarionCost` FROM `clincs` WHERE `id`='" + clincId + "'").getValueAt(0, 0).toString();
                break;

        }
        if (contractExaminationCost.equals("0")) {
            if (contractDiscpunt.equals("0")) {
                return clincCost;
            } else {
                double clinc = Double.parseDouble(clincCost);
                double discount = Double.parseDouble(contractDiscpunt);

                double total = clinc * discount;
                total = total / 100;
                total = clinc - total;
                return Double.toString(total);
            }
        } else {
            return contractExaminationCost;
        }

    }
}
