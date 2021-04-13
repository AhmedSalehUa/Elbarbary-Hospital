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
import screens.store.assets.StoreProdcts;

/**
 *
 * @author AHMED
 */
public class AdmisiionSurgMedicine {

    int id;
    int admissionId;
    int medicineId;
    int storeId;
    String store;
    String medicine;
    String amount;

    String oldAmount;

    public AdmisiionSurgMedicine() {
    }

    public AdmisiionSurgMedicine(int id, String medicine, String amount, int storeId) {
        this.id = id;
        this.medicine = medicine;
        this.amount = amount;
        this.storeId = storeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOldAmount() {
        return oldAmount;
    }

    public void setOldAmount(String oldAmount) {
        this.oldAmount = oldAmount;
    }

    public int getAdmissionId() {
        return admissionId;
    }

    public void setAdmissionId(int admissionId) {
        this.admissionId = admissionId;
    }

    public int getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public boolean Add() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `admission_surgies_medicine`(`id`, `admission_id`, `medicine_id`, `amount`) VALUES (?,?,?,?)");
        ps.setInt(1, id);
        ps.setInt(2, admissionId);
        ps.setInt(3, medicineId);
        ps.setString(4, amount);
        StoreProdcts.reduceAmount(medicineId, amount);
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        PreparedStatement ps = db.get.Prepare("UPDATE `admission_surgies_medicine` SET `admission_id`=?,`medicine_id`=?,`amount`=? WHERE `id`=?");
        ps.setInt(1, admissionId);
        ps.setInt(2, medicineId);
        ps.setString(3, amount);
        ps.setInt(4, id);
        ps.execute();
        StoreProdcts.reduceAmount(medicineId, amount);
        return true;
    }

    public boolean Delete() throws Exception {
        StoreProdcts.AddAmount(medicineId, oldAmount);
        PreparedStatement ps = db.get.Prepare("DELETE FROM `admission_surgies_medicine` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public static ObservableList<AdmisiionSurgMedicine> getData(int admissionId) throws Exception {
        ObservableList<AdmisiionSurgMedicine> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `admission_surgies_medicine`.`id`, `medicine`.`name`, `admission_surgies_medicine`.`amount`, `stores_medicines`.`store_id` FROM `medicine`,`stores_medicines`, `admission_surgies_medicine` WHERE `admission_surgies_medicine`.`admission_id` = '" + admissionId + "' AND `stores_medicines`.`id` = `admission_surgies_medicine`.`medicine_id` AND `medicine`.`id`=`stores_medicines`.`medicine_id`");
        while (rs.next()) {
            data.add(new AdmisiionSurgMedicine(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `admission_surgies_medicine`").getValueAt(0, 0).toString();
    }

}
