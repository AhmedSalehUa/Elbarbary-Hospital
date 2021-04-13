package screens.drugs.assets;

import db.get;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class DrugsAccounts {

    int id;
    int paitent_id;
    String patient_name;
    String total_paied;
    String total_spended;
    String remaining;
    String room;

    public DrugsAccounts() {
    }

    public DrugsAccounts(int id, String patient_name, String total_paied, String total_spended, String remaining, String room, int paitent_id) {
        this.id = id;
        this.patient_name = patient_name;
        this.total_paied = total_paied;
        this.total_spended = total_spended;
        this.remaining = remaining;
        this.room = room;
        this.paitent_id = paitent_id;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPaitent_id() {
        return this.paitent_id;
    }

    public void setPaitent_id(int paitent_id) {
        this.paitent_id = paitent_id;
    }

    public String getPatient_name() {
        return this.patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getTotal_paied() {
        return this.total_paied;
    }

    public void setTotal_paied(String total_paied) {
        this.total_paied = total_paied;
    }

    public String getTotal_spended() {
        return this.total_spended;
    }

    public void setTotal_spended(String total_spended) {
        this.total_spended = total_spended;
    }

    public String getRemaining() {
        return this.remaining;
    }

    public void setRemaining(String remaining) {
        this.remaining = remaining;
    }

    public String getRoom() {
        return this.room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public boolean Add() throws Exception {
        PreparedStatement ps = get.Prepare("INSERT INTO `drg_accounts`(`id`, `patient_id`, `total_paied`, `total_spended`, `remaining`,`daily_room`) VALUES (?,?,?,?,?,?)");
        ps.setInt(1, this.id);
        ps.setInt(2, this.paitent_id);
        ps.setString(3, this.total_paied);
        ps.setString(4, this.total_spended);
        ps.setString(5, this.remaining);
        ps.setString(6, this.room);
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        PreparedStatement ps = get.Prepare("UPDATE `drg_accounts` SET `patient_id`=?,`total_paied`=?,`total_spended`=?,`remaining`=?,`daily_room`=? WHERE `id`=?");
        ps.setInt(6, this.id);
        ps.setInt(1, this.paitent_id);
        ps.setString(2, this.total_paied);
        ps.setString(3, this.total_spended);
        ps.setString(4, this.remaining);
        ps.setString(5, this.room);
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = get.Prepare("DELETE FROM `drg_accounts` WHERE `id`=?");
        ps.setInt(1, this.id);
        ps.execute();
        return true;
    }

    public static ObservableList<DrugsAccounts> getData() throws Exception {
        ObservableList<DrugsAccounts> data = FXCollections.observableArrayList();
        ResultSet rs = get.getReportCon().createStatement().executeQuery("SELECT `drg_accounts`.`id`, `drg_patient`.`name`, `drg_accounts`.`total_paied`, `drg_accounts`.`total_spended`, `drg_accounts`.`remaining`,`drg_accounts`.`daily_room`,`drg_accounts`.`patient_id` FROM `drg_accounts`,`drg_patient` WHERE `drg_accounts`.`patient_id` =`drg_patient`.`id`");
        while (rs.next()) {
            data.add(new DrugsAccounts(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7)));
        }
        return data;
    }

    public static ObservableList<DrugsAccounts> getData(int patientId) throws Exception {
        ObservableList<DrugsAccounts> data = FXCollections.observableArrayList();
        ResultSet rs = get.getReportCon().createStatement().executeQuery("SELECT `drg_accounts`.`id`, `drg_patient`.`name`, `drg_accounts`.`total_paied`, `drg_accounts`.`total_spended`, `drg_accounts`.`remaining`,`drg_accounts`.`daily_room`,`drg_accounts`.`patient_id` FROM `drg_accounts`,`drg_patient` WHERE `drg_accounts`.`patient_id` =`drg_patient`.`id` and `drg_accounts`.`patient_id`='" + patientId + "'");
        while (rs.next()) {
            data.add(new DrugsAccounts(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `drg_accounts`").getValueAt(0, 0).toString();
    }

    public static boolean addToTotalPaied(int acc_id, String amount) throws Exception {
        Double a = Double.parseDouble(get.getTableData("select total_paied from drg_accounts where id='" + acc_id + "'").getValueAt(0, 0).toString()) + Double.parseDouble(amount);
        PreparedStatement ps = get.Prepare("UPDATE `drg_accounts` SET `total_paied`=? WHERE `id`=?");
        ps.setString(1, Double.toString(a));
        ps.setInt(2, acc_id);
        ps.execute();
        return true;
    }

    public static boolean removeFromTotalPaied(int acc_id, String amount) throws Exception {
        Double a = Double.parseDouble(get.getTableData("select total_paied from drg_accounts where id='" + acc_id + "'").getValueAt(0, 0).toString()) - Double.parseDouble(amount);
        boolean statue = true;
        if (a <= 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Deleting  ");
            alert.setHeaderText("اجمالي المصروف لا يكفي لخصم المبلغ");
            alert.setContentText("هل انت متاكد؟");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                statue = true;
            } else {
                statue = false;
            }
        }
        if (statue) {
            PreparedStatement ps = get.Prepare("UPDATE `drg_accounts` SET `total_paied`=? WHERE `id`=?");
            ps.setString(1, Double.toString(a));
            ps.setInt(2, acc_id);
            ps.execute();
            return true;
        } else {
            throw new Exception("اجمالي المصروف لا يكفي لخصم المبلغ");
        }
    }

    public static boolean addToTotalSpended(int acc_id, String amount) throws Exception {
        Double a = Double.parseDouble(get.getTableData("select total_spended from drg_accounts where id='" + acc_id + "'").getValueAt(0, 0).toString()) + Double.parseDouble(amount);
        PreparedStatement ps = get.Prepare("UPDATE `drg_accounts` SET `total_spended`=? WHERE `id`=?");
        ps.setString(1, Double.toString(a));
        ps.setInt(2, acc_id);
        ps.execute();
        return true;
    }

    public static boolean removeFromTotalSpended(int acc_id, String amount) throws Exception {
        Double a = Double.parseDouble(get.getTableData("select total_spended from drg_accounts where id='" + acc_id + "'").getValueAt(0, 0).toString()) - Double.parseDouble(amount);
        boolean statue = true;
        if (a <= 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Deleting  ");
            alert.setHeaderText("اجمالي المصروف لا يكفي لخصم المبلغ");
            alert.setContentText("هل انت متاكد؟");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                statue = true;
            } else {
                statue = false;
            }
        }
        if (statue) {
            PreparedStatement ps = get.Prepare("UPDATE `drg_accounts` SET `total_spended`=? WHERE `id`=?");
            ps.setString(1, Double.toString(a));
            ps.setInt(2, acc_id);
            ps.execute();
            return true;
        } else {
            throw new Exception("اجمالي المصروف لا يكفي لخصم المبلغ");
        }

    }

    public static boolean addToRemaining(int acc_id, String amount) throws Exception {
        Double a = Double.parseDouble(get.getTableData("select remaining from drg_accounts where id='" + acc_id + "'").getValueAt(0, 0).toString()) + Double.parseDouble(amount);
        PreparedStatement ps = get.Prepare("UPDATE `drg_accounts` SET `remaining`=? WHERE `id`=?");
        ps.setString(1, Double.toString(a));
        ps.setInt(2, acc_id);
        ps.execute();
        return true;
    }

    public static boolean removeFromRemaining(int acc_id, String amount) throws Exception {
        Double a = Double.parseDouble(get.getTableData("select remaining from drg_accounts where id='" + acc_id + "'").getValueAt(0, 0).toString()) - Double.parseDouble(amount);
        boolean statue = true;
        if (a <= 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Deleting  ");
            alert.setHeaderText("اجمالي المصروف لا يكفي لخصم المبلغ");
            alert.setContentText("هل انت متاكد؟");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                statue = true;
            } else {
                statue = false;
            }
        }
        if (statue) {
            PreparedStatement ps = get.Prepare("UPDATE `drg_accounts` SET `remaining`=? WHERE `id`=?");
            ps.setString(1, Double.toString(a));
            ps.setInt(2, acc_id);
            ps.execute();
            return true;
        } else {
            throw new Exception("اجمالي المصروف لا يكفي لخصم المبلغ");
        }

    }
}
