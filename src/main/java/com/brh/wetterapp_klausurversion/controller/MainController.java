package com.brh.wetterapp_klausurversion.controller;

import com.brh.wetterapp_klausurversion.App;
import com.brh.wetterapp_klausurversion.Scheduler;
import com.brh.wetterapp_klausurversion.model.ApiRequest;
import com.brh.wetterapp_klausurversion.model.ApiResponse;
import com.brh.wetterapp_klausurversion.view.StageManager;
import com.brh.wetterapp_klausurversion.view.StageType;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MainController {
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private Label temperatureLabel;
    @FXML
    private Label windLabel;
    @FXML
    private Label rainLabel;

    public void initialize(){
        App.setMainController(this);
        update();
        Platform.runLater(this::setDragDrop);
    }

    private void showCurrentValues( ApiResponse response ){
        Platform.runLater(()->{
            String t = response.currTemperature().toString();
            String w = response.currWind().toString();
            String r = response.currRain().toString();
            temperatureLabel.setText( t + "C°");
            windLabel.setText( w + "km/h" );
            rainLabel.setText( r + "%");
        });
    }

    @FXML
    protected void openOptions(){
        openAdditionalWindow( StageType.Option, 500,200 );
    }

    @FXML
    protected void openChart(){
        openAdditionalWindow( StageType.Chart, 1200, 600 );
    }

    private void openAdditionalWindow(StageType stageType, int width, int height ){
        boolean optionExists = StageManager.getInstance().getStageByStageType(StageType.Option) != null;
        boolean chartExists = StageManager.getInstance().getStageByStageType(StageType.Chart) != null;
        if(optionExists || chartExists) return;

        StageManager.getInstance().setScene(
                stageType, width,height
        );
    }

    public  void update( ){
        double longitude = SettingsManager.getInstance().getLongitude();
        double latitude = SettingsManager.getInstance().getLatitude();
        System.out.println("update request lng: "+longitude+" lat: "+latitude);
        ApiRequest request = new ApiRequest();
        request.sendRequest(longitude, latitude, this::showCurrentValues );
        new Scheduler( this::update ).start();
    }

    private void setDragDrop( ){
        Stage stage = StageManager.getInstance().getStageByStageType(StageType.Main);
        Scene scene = stage.getScene();

        scene.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        scene.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }
}