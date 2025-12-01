package com.brh.wetterapp_klausurversion.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class APIHelper {

    private static final String CACHE_PATH = "offlineCache.json";

    public static boolean apiIsAvailable() {
        try {
            HttpURLConnection connection =
                    (HttpURLConnection) new URL("https://api.openmeteo.com").openConnection();

            connection.setConnectTimeout(2000);
            connection.connect();
            boolean apiIsReady = connection.getResponseCode()== 200;
            System.out.println("API is ready: "+ apiIsReady);
            return apiIsReady;
        } catch (Exception e) {
            System.err.println( e );
            return false;
        }
    }

    public static void saveCache(String json) {
        try (FileWriter writer = new FileWriter(CACHE_PATH)) {
            writer.write(json);
        } catch (IOException e) {
            throw new RuntimeException( e.getMessage() );
        }
    }

    public static String loadCache() {
        try {
            return Files.readString(
                            Paths.get(CACHE_PATH)
            );
        } catch (IOException e) {
            throw new RuntimeException( e.getMessage() );
        }
    }

    public static ApiResponse parseApiResponse(String json) {

        try {
            JSONParser parser = new JSONParser();
            JSONObject root = (JSONObject) parser.parse(json);

            JSONObject current = (JSONObject) root.get("current");
            double currentTemp = (double) current.get("temperature_2m");
            double currentWindSpeed = (double) current.get("wind_speed_10m");
            double currentRain = (double) current.get("precipitation");

            long interval = (long) current.get("interval");

            JSONObject hourly = (JSONObject) root.get("hourly");

            JSONArray times = (JSONArray) hourly.get("time");
            var time = APIHelper.<String>jsonArrayOfType(times);

            JSONArray temperatures = (JSONArray) hourly.get("temperature_2m");
            var temperature = APIHelper.<Double>jsonArrayOfType(temperatures);

            JSONArray windSpeeds = (JSONArray) hourly.get("wind_speed_10m");
            var wind = APIHelper.<Double>jsonArrayOfType(windSpeeds);

            JSONArray rainProbability = (JSONArray) hourly.get("precipitation_probability");
            var rain = APIHelper.<Double>jsonArrayOfType(rainProbability);

            var res =  new ApiResponse(currentTemp, currentWindSpeed,
                    currentRain, time, temperature, wind, rain);

            return res;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> jsonArrayOfType( JSONArray jsonArray ){
        List<T> list = new ArrayList<>();

        for( var value : jsonArray){
            list.add( (T)value );
        }
        return list;

    }
}
