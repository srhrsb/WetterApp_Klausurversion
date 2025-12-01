package com.brh.wetterapp_klausurversion.controller;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SettingsManager {
    private final String SETTINGS_PATH = "settings.json";

    private double latitude;
    private double longitude;
    private long updateFrequency;

    private static SettingsManager instance;

    private SettingsManager(){
        latitude = 49.40768;
        longitude = 8.69079;
        updateFrequency = 120;
        loadSettings();
    }

    public static SettingsManager getInstance(){
        if(instance == null){
            instance = new SettingsManager();
        }
        return instance;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public long getUpdateFrequency() {
        return updateFrequency;
    }

    public void setAll(double latitude, double longitude, long updateFrequency) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.updateFrequency = updateFrequency;
        saveSettings();
    }

    private void loadSettings(){
        try {
            String json = Files.readString(
                            Paths.get( SETTINGS_PATH )
                    );
            JSONParser parser = new JSONParser();
            JSONObject root = (JSONObject) parser.parse(json);

            latitude = (double)root.get("latitude");
            longitude = (double)root.get("longitude");
            updateFrequency = (long)root.get("update");

        } catch (IOException | ParseException e) {
            throw new RuntimeException( e.getMessage() );
        }
    }

    private void saveSettings(){

        try (FileWriter fileWriter = new FileWriter(SETTINGS_PATH)) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("latitude", latitude);
            jsonObject.put("longitude", longitude);
            jsonObject.put("update", updateFrequency);

            fileWriter.write( jsonObject.toJSONString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
