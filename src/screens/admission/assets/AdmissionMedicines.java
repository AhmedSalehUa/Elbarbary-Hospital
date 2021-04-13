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
public class AdmissionMedicines {

    int id;
    int admissionId;
    int medicineId;
    String medicine;
    String amount;
    String cost;
    String totalcost;

    String oldAmount;

    public AdmissionMedicines() {
    }

    public AdmissionMedicines(int id, String medicine, String amount, String cost, String totalcost) {
        this.id = id;
        this.medicine = medicine;
        this.amount = amount;
        this.cost = cost;
        this.totalcost = totalcost;
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

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getTotalcost() {
        return totalcost;
    }

    public void setTotalcost(String totalcost) {
        this.totalcost = totalcost;
    }

    public boolean Add() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `admission_medicine`(`id`, `admission_id`, `medicine_id`, `amount`, `cost`, `total_cost`) VALUES (?,?,?,?,?,?)");
        ps.setInt(1, id);
        ps.setInt(2, admissionId);
        ps.setInt(3, medicineId);
        ps.setString(4, amount);
        ps.setString(5, cost);
        String costs = getActualCost();
        ps.setString(6, costs);
        Admission.addCostToAdmission(admissionId, costs);
        StoreProdcts.reduceAmount(medicineId, amount);
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        StoreProdcts.AddAmount(medicineId, oldAmount);
        Admission.removeCostToAdmission(admissionId, db.get.getTableData("SELECT `total_cost` FROM `admission_medicine` WHERE `admission_id`='" + admissionId + "' And `id`='" + id + "'").getValueAt(0, 0).toString());
        PreparedStatement ps = db.get.Prepare("UPDATE `admission_medicine` SET `admission_id`=?,`medicine_id`=?,`amount`=?,`cost`=?,`total_cost`=? WHERE `id`=?");
        ps.setInt(1, admissionId);
        ps.setInt(2, medicineId);
        ps.setString(3, amount);
        ps.setString(4, cost);
        String costs = getActualCost();
        ps.setString(5, costs);
        ps.setInt(6, id);
        Admission.addCostToAdmission(admissionId, costs);

        ps.execute();
        StoreProdcts.reduceAmount(medicineId, amount);
        return true;
    }

    public boolean Delete() throws Exception {
        StoreProdcts.AddAmount(medicineId, oldAmount);
        Admission.removeCostToAdmission(admissionId, db.get.getTableData("SELECT `total_cost` FROM `admission_medicine` WHERE `admission_id`='" + admissionId + "' And `id`='" + id + "'").getValueAt(0, 0).toString());
        PreparedStatement ps = db.get.Prepare("DELETE FROM `admission_medicine` WHERE `id`=?");
        ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public static ObservableList<AdmissionMedicines> getData(int admissionId) throws Exception {
        ObservableList<AdmissionMedicines> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `admission_medicine`.`id`,`medicine`.`name`, `admission_medicine`.`amount`, `admission_medicine`.`cost`, `admission_medicine`.`total_cost` FROM `medicine`,`admission_medicine` WHERE `admission_medicine`.`admission_id`='" + admissionId + "'and `medicine`.`id` in (SELECT `medicine_id` FROM `stores_medicines` WHERE `id`=`admission_medicine`.`medicine_id`)");
        while (rs.next()) {
            data.add(new AdmissionMedicines(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `admission_medicine`").getValueAt(0, 0).toString();
    }

    public String getActualCost() throws Exception {
        String contractDiscpunt = db.get.getTableData("SELECT IFNULL(sum( `discount`),'0') as 'cost' FROM `contracts` WHERE `id`in(SELECT `contract_id` FROM `admission` WHERE `id`='" + admissionId + "')").getValueAt(0, 0).toString();

        if (contractDiscpunt.equals("0")) {
            return totalcost;
        } else {
            double clinc = Double.parseDouble(totalcost);
            double discount = Double.parseDouble(contractDiscpunt);

            double total = clinc * discount;
            total = total / 100;
            total = clinc - total;
            return Double.toString(total);
        }

    }
}
