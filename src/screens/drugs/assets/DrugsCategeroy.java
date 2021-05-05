package screens.drugs.assets;

import db.get;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DrugsCategeroy {

    int id;
    String name;

    public DrugsCategeroy() {
    }

    public DrugsCategeroy(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static boolean Add(String value) throws Exception {
        PreparedStatement ps = get.Prepare("INSERT INTO `drg_expense_cat`( `name`) VALUES (?)");
        ps.setString(1, value);
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        PreparedStatement ps = get.Prepare("UPDATE `drg_expense_cat` SET `name`=? WHERE `id`=?");
        ps.setString(1, this.name);
        ps.setInt(2, this.id);
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = get.Prepare("DELETE FROM `drg_expense_cat` WHERE `id`=?");
        ps.setInt(1, this.id);
        ps.execute();
        return true;
    }

    public static ObservableList<DrugsCategeroy> getData() throws Exception {
        ObservableList<DrugsCategeroy> data = FXCollections.observableArrayList();
        ResultSet rs = get.getReportCon().createStatement().executeQuery("SELECT * FROM `drg_expense_cat`");
        while (rs.next()) {
            data.add(new DrugsCategeroy(rs.getInt(1), rs.getString(2)));
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
        return get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `drg_expense_cat`").getValueAt(0, 0).toString();
    }
}
