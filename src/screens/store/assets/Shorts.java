/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.store.assets;

import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JTable;

/**
 *
 * @author AHMED
 */
public class Shorts {

    String product;
    String warnNum;
    String Available;

    String mainStoreAvailable;
    String branchStoreAvailable;

    public Shorts() {
    }

    public Shorts(String product, String warnNum, String Available) {
        this.product = product;
        this.warnNum = warnNum;
        this.Available = Available;
    }

    public Shorts(String product, String mainStoreAvailable, String branchStoreAvailable, String warnNum, String Available) {
        this.product = product;
        this.warnNum = warnNum;
        this.Available = Available;
        this.mainStoreAvailable = mainStoreAvailable;
        this.branchStoreAvailable = branchStoreAvailable;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getWarnNum() {
        return warnNum;
    }

    public void setWarnNum(String warnNum) {
        this.warnNum = warnNum;
    }

    public String getAvailable() {
        return Available;
    }

    public void setAvailable(String Available) {
        this.Available = Available;
    }

    public String getMainStoreAvailable() {
        return mainStoreAvailable;
    }

    public void setMainStoreAvailable(String mainStoreAvailable) {
        this.mainStoreAvailable = mainStoreAvailable;
    }

    public String getBranchStoreAvailable() {
        return branchStoreAvailable;
    }

    public void setBranchStoreAvailable(String branchStoreAvailable) {
        this.branchStoreAvailable = branchStoreAvailable;
    }

    public static ObservableList<Shorts> getIndividualData(int StroreId) throws Exception {
        ObservableList<Shorts> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `medicine`.`name`,`medicine`.`warn_num`, SUM(`amount`) FROM `medicine`,`stores_medicines` WHERE `store_id`='" + StroreId + "' and `medicine`.`id`=`stores_medicines`.`medicine_id` GROUP BY `medicine_id` HAVING Sum(`amount`)<`medicine`.`warn_num`");
        while (rs.next()) {
            data.add(new Shorts(rs.getString(1), rs.getString(2), rs.getString(3)));
        }
        return data;
    }

    public static ObservableList<Shorts> getAllData() throws Exception {
        ObservableList<Shorts> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `id`, `name`,`warn_num` FROM `medicine`");

        while (rs.next()) {
            JTable medicine = db.get.getTableData("SELECT IFNULL(SUM(`amount`),0) FROM `stores_medicines` WHERE  `medicine_id`='" + rs.getString(1) + "' and `store_id` = '1'");
            JTable Othermedicine = db.get.getTableData("SELECT IFNULL(SUM(`amount`),0) FROM `stores_medicines` WHERE  `medicine_id`='" + rs.getString(1) + "' and `store_id` != '1'");

            double total = Double.parseDouble(medicine.getValueAt(0, 0).toString()) + Double.parseDouble(Othermedicine.getValueAt(0, 0).toString());
            if (Double.parseDouble(rs.getString(3)) > total) {
                data.add(new Shorts(rs.getString(2), medicine.getValueAt(0, 0).toString(), Othermedicine.getValueAt(0, 0).toString(), rs.getString(3), Double.toString(total))); 
            }

        }
        return data;
    }

}
