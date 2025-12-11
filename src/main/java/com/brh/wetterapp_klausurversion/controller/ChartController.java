package com.brh.wetterapp_klausurversion.controller;

import com.brh.wetterapp_klausurversion.model.APIHelper;
import com.brh.wetterapp_klausurversion.model.ApiResponse;
import com.brh.wetterapp_klausurversion.view.StageManager;
import com.brh.wetterapp_klausurversion.view.StageType;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.util.List;


public class ChartController {
    @FXML
    private LineChart temperatureChart;

    @FXML
    private LineChart rainChart;

    @FXML
    private LineChart windChart;

    @FXML
    private Label averageTemperature;
    @FXML
    private Label averageRain;
    @FXML
    private Label averageWind;

    private final int CHART_RESOLUTION = 4;

    /**
     * Initialisiert und weist die Daten (Regen, Wind, Temperartur) zu
     */
    @FXML
    private void initialize(){
        String json = APIHelper.loadCache();
        ApiResponse data = APIHelper.parseApiResponse(json);
        setChartData(temperatureChart, data.time(), data.temp());
        setChartData(rainChart, data.time(), data.rain());
        setChartData(windChart, data.time(), data.wind());

        showAverage( averageTemperature, "Durchschnitt Temperatur: ", data.temp());
        showAverage( averageRain, "Durchschnitt Regen: ", data.rain());
        showAverage( averageWind, "Durchschnitt Wind: ", data.wind());
    }

    /**
     * Schließt das Chart-Fenster nach Event des Schliessen-Buttons
     */
    @FXML
    protected void closeChart(){
        StageManager.getInstance().closeStageByStageType(StageType.Chart);
    }


    /**
     * Setzt die Chartdaten
     * @param chart der Chart der gesetzt werden soll
     * @param time Liste mit den Zeiten (7 * 24 Zeitpunkte)
     * @param data Liste mit den Daten (7 * 24 Daten)
     */
    private void setChartData( LineChart chart, List<String> time, List<Double> data){
        XYChart.Series series = new XYChart.Series();
        series.setName("7-Tage Vorschau (Auflösung: "+CHART_RESOLUTION+"h)");

        for( int i= 0 ; i < data.size(); i+= CHART_RESOLUTION ){
            series.getData().add(new XYChart.Data(time.get(i).replace("T","\n"), data.get(i))) ;
            i++;
        }

        chart.getData().add(series);
    }

    private void showAverage( Label label, String text, List<Double>data){
        Double total = 0.0;

        int i;
        for( i= 0 ; i < data.size(); i++ ){
            total += ((Number)data.get(i)).doubleValue();
        }

        label.setText(text + total/i);
    }
}
