/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.drugs.assets;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

/**
 *
 * @author AhmedSalehPc
 */
public class DrugsStatics {

    public static String getAllPatient() throws Exception {
        String data = "0";
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT count(`id`)  FROM `drg_patient`");
        while (rs.next()) {
            data = rs.getString(1);
        }
        return data;
    }

    public static String getCurrentPatient() throws Exception {
        String data = "0";
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT count(`id`) FROM `drg_accounts` WHERE `date_of_exite` IS Null");
        while (rs.next()) {
            data = rs.getString(1);
        }
        return data;
    }

    public static PieChart getPatientCatPie() throws Exception {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT drg_patient.diagnosis,count(drg_patient.id) from drg_patient GROUP BY drg_patient.diagnosis");
        while (rs.next()) {
            pieChartData.add(new PieChart.Data(rs.getString(1), rs.getInt(2)));
        }
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("تصنيف المرضي");
        chart.setLabelLineLength(10);

        chart.setLegendSide(Side.LEFT);

        return chart;
    }

    public static PieChart getDoctorCatPie() throws Exception {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT doctors.name ,count(drg_patient.id) from drg_patient,doctors where doctors.id=drg_patient.doctor_id GROUP BY doctors.name");
        while (rs.next()) {
            pieChartData.add(new PieChart.Data(rs.getString(1), rs.getInt(2)));
        }
         
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("تصنيف الاطباء");
        chart.setLabelLineLength(10);

        chart.setLegendSide(Side.LEFT);

        return chart;
    }

    private void applyCustomColorSequence(ObservableList<PieChart.Data> pieChartData, String... pieColors) {
        int i = 0;
        for (PieChart.Data data : pieChartData) {
            data.getNode().setStyle(
                    "-fx-pie-color: " + pieColors[i % pieColors.length] + ";"
            );
            i++;
        }
    }

    public static LineChart getEntranceLineChart() throws Exception {
        final CategoryAxis ProviderxAxis = new CategoryAxis();
        final NumberAxis ProvideryAxis = new NumberAxis();
        ProviderxAxis.setLabel("Month");
        final LineChart<String, Number> ProviderlineChart
                = new LineChart<String, Number>(ProviderxAxis, ProvideryAxis);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy");
        ProviderlineChart.setTitle("دخول وخروج المرضي خلال  " + LocalDate.now().format(format));

        XYChart.Series Providerseries1 = getEntranceByMonth();
        Providerseries1.setName("دخول");

        XYChart.Series Providerseries2 = getExitByMonth();
        Providerseries2.setName("خروج");

        ProviderlineChart.getData().addAll(Providerseries1, Providerseries2);

        return ProviderlineChart;
    }

    private static XYChart.Series getEntranceByMonth() throws Exception {
        XYChart.Series series1 = new XYChart.Series();

        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT sys_months.month, COUNT( drg_accounts.id ) cnt FROM sys_months LEFT JOIN drg_accounts ON (sys_months.monthNumber = MONTH(drg_accounts.date_of_entrance) ) GROUP BY sys_months.month ORDER BY sys_months.id ASC");
        while (rs.next()) {

            series1.getData().add(new XYChart.Data(rs.getString(1), rs.getInt(2)));
        }
        return series1;
    }

    private static XYChart.Series getExitByMonth() throws Exception {
        XYChart.Series series1 = new XYChart.Series();

        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("SELECT sys_months.month, COUNT( drg_accounts.id ) cnt FROM sys_months LEFT JOIN drg_accounts ON (sys_months.monthNumber = MONTH(drg_accounts.date_of_exite) ) GROUP BY sys_months.month ORDER BY sys_months.id ASC");
        while (rs.next()) {

            series1.getData().add(new XYChart.Data(rs.getString(1), rs.getInt(2)));
        }
        return series1;
    }
}
