package com.brh.wetterapp_klausurversion.controller;

import com.brh.wetterapp_klausurversion.App;
import com.brh.wetterapp_klausurversion.view.StageManager;
import com.brh.wetterapp_klausurversion.view.StageType;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

/**
 * Verwaltet die Einstellung/Änderung der Updaterate und des Längen und Breitengrades
 */
public class OptionController {
    @FXML
    private TextField latitudeTf;
    @FXML
    private TextField longitudeTf;
    @FXML
    private Slider updateFrequencySlider;
    @FXML
    private Label updateTimeLabel;

    /**
     * Setzt die gespeicherten Daten aus dem Settings-Manager in die UI-Elemente
     */
    public void initialize() {
        latitudeTf.setText( Double.toString( SettingsManager.getInstance().getLatitude() ) );
        longitudeTf.setText( Double.toString( SettingsManager.getInstance().getLongitude() ) );
        updateFrequencySlider.setValue( SettingsManager.getInstance().getUpdateFrequency() );
    }

    /**
     * Aktualisierung der Anzeige für die Update-Rate
     */
    @FXML
    protected void onChangeUpdateSlider(){
       updateTimeLabel.setText(
               (int)updateFrequencySlider.getValue() + " min"
       );
    }

    /**
     * Eingestellten Werte werden an den Settings-Manager übergeben
     * und zeitgleich findet die Aktualisierung statt
     */
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
