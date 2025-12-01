package com.brh.wetterapp_klausurversion.controller;

import com.brh.wetterapp_klausurversion.App;
import com.brh.wetterapp_klausurversion.view.StageManager;
import com.brh.wetterapp_klausurversion.view.StageType;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class OptionController {
    @FXML
    private TextField latitudeTf;
    @FXML
    private TextField longitudeTf;
    @FXML
    private Slider updateFrequencySlider;
    @FXML
    private Label updateTimeLabel;


    public void initialize() {
        latitudeTf.setText( Double.toString( SettingsManager.getInstance().getLatitude() ) );
        longitudeTf.setText( Double.toString( SettingsManager.getInstance().getLongitude() ) );
        updateFrequencySlider.setValue( SettingsManager.getInstance().getUpdateFrequency() );
    }

    @FXML
    protected void onChangeUpdateSlider(){
       updateTimeLabel.setText(
               (int)updateFrequencySlider.getValue() + " min"
       );
    }

    @FXML
    protected void onUseClick(){
        try {
            double longitude = Double.parseDouble( longitudeTf.getText() );
            double latitude = Double.parseDouble( latitudeTf.getText() );
            long frequency = (long)updateFrequencySlider.getValue();
            SettingsManager.getInstance().setAll( latitude, longitude, frequency);
            App.getMainController().update();
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
