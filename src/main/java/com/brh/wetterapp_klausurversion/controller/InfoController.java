package com.brh.wetterapp_klausurversion.controller;

import javafx.event.ActionEvent;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
public class InfoController {

    @FXML
    private Label infoLabel;

    public void initialize(){
        showSettings();
    }

    private void  showSettings(){
       String text = "Latitude:"+ SettingsManager.getInstance().getLatitude() + "Longitude: " + SettingsManager.getInstance().getLongitude() + "Update: " + SettingsManager.getInstance().getUpdateFrequency();
       infoLabel.setText( text );
    }

    public void closeInfo(ActionEvent actionEvent) {
    }
}
