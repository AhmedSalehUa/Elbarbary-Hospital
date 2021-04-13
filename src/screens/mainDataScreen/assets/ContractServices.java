/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.mainDataScreen.assets;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author AHMED
 */
public class ContractServices {

    int id;
    int contractId;
    int serviceId;
    String service;
    String cost;

    public ContractServices() {
    }

    public ContractServices(int id, String service, String cost) {
        this.id = id;
        this.service = service;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public boolean Add() throws Exception {
        PreparedStatement ps = db.get.Prepare("INSERT INTO `contracts_services`(`id`, `contract_id`, `service_id`, `cost`) VALUES (?,?,?,?)");
        ps.setInt(1, id);
        ps.setInt(2, contractId);
        ps.setInt(3, serviceId);
        ps.setString(4, cost);
        ps.execute();
        return true;
    }

    public boolean Edite() throws Exception {
        PreparedStatement ps = db.get.Prepare("UPDATE `contracts_services` SET `service_id`=?,`cost`=? WHERE `id`=?");
        ps.setInt(1, serviceId);
        ps.setString(2, cost);
        ps.setInt(3, id);
        ps.execute();
        return true;
    }

    public boolean Delete() throws Exception {
        PreparedStatement ps = db.get.Prepare("DELETE FROM `contracts_services` WHERE `id`=?");
         ps.setInt(1, id);
        ps.execute();
        return true;
    }

    public static ObservableList<ContractServices> getData(int contractId) throws Exception {
        ObservableList<ContractServices> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `contracts_services`.`id`, `contracts_services_names`.`name`, `contracts_services`.`cost` FROM `contracts_services`,`contracts_services_names` WHERE `contracts_services`.`contract_id` ='"+contractId+"' AND `contracts_services`.`service_id` = `contracts_services_names`.`id`");
        while (rs.next()) {
            data.add(new ContractServices(rs.getInt(1), rs.getString(2), rs.getString(3)));
        }
        return data;
    }
public static ObservableList<ContractServices> getDataFromName(String contractId) throws Exception {
        ObservableList<ContractServices> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT `contracts_services`.`id`, `contracts_services_names`.`name`, `contracts_services`.`cost` FROM `contracts_services`,`contracts_services_names` WHERE `contracts_services`.`contract_id` in (SELECT `id` FROM `contracts` WHERE  `name`='"+contractId+"') AND `contracts_services`.`service_id` = `contracts_services_names`.`id`");
        while (rs.next()) {
            data.add(new ContractServices(rs.getInt(1), rs.getString(2), rs.getString(3)));
        }
        return data;
    }
    public static String getAutoNum() throws Exception {
        return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `contracts_services`").getValueAt(0, 0).toString();
    }
}
