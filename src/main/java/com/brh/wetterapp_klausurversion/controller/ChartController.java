package com.brh.wetterapp_klausurversion.controller;

import com.brh.wetterapp_klausurversion.model.APIHelper;
import com.brh.wetterapp_klausurversion.model.ApiResponse;
import com.brh.wetterapp_klausurversion.view.StageManager;
import com.brh.wetterapp_klausurversion.view.StageType;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChartController {
    @FXML
    private LineChart temperatureChart;

    @FXML
    private LineChart rainChart;

    @FXML
    private LineChart windChart;

    private final int CHART_RESOLUTION = 4;

    @FXML
    private void initialize(){
        String json = APIHelper.loadCache();
        ApiResponse data = APIHelper.parseApiResponse(json);
        setChartData(temperatureChart, data.time(), data.temp());
        setChartData(rainChart, data.time(), data.rain());
        setChartData(windChart, data.time(), data.wind());
    }

    @FXML
    protected void closeChart(){
        StageManager.getInstance().closeStageByStageType(StageType.Chart);
    }

    private void setChartData( LineChart chart, List<String> time, List<Double> data){
        XYChart.Series series = new XYChart.Series();
        series.setName("7-Tage Vorschau (Auflösung: "+CHART_RESOLUTION+"h)");

        for( int i= 0 ; i < data.size(); i+= CHART_RESOLUTION ){
            series.getData().add(new XYChart.Data(time.get(i).replace("T","\n"), data.get(i))) ;
            i++;
        }

        chart.getData().add(series);

    }
}
