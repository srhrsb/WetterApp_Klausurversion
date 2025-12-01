package com.brh.wetterapp_klausurversion.model;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;


public class ApiRequest {
    private Consumer<ApiResponse> callback;

    /**
     * Sendet Anfrage an die Wetter-API
     * @param longitude Longitude
     * @param latitude Latitude
     * @param callback Callback
     */
    public void sendRequest(Double longitude, Double latitude, Consumer<ApiResponse> callback) {
        this.callback = callback;

        if ( APIHelper.apiIsAvailable() ) {
            String uriString = "https://api.open-meteo.com/v1/forecast?latitude="
                    + latitude + "&longitude=" + longitude +
                    "&hourly=temperature_2m,wind_speed_10m,"+
                    "precipitation_probability&current=temperature_2m,"+
                    "precipitation,wind_speed_10m&timezone=Europe%2FBerlin";

            try{
                //get Request an open-meteo
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder(
                        URI.create(uriString)).build();

                CompletableFuture<HttpResponse<String>> future =
                        client.sendAsync(
                                request, HttpResponse.BodyHandlers.ofString()
                        );
                future.thenAccept(this::handleApiResponse );
            }
            catch(Exception e){
                throw new RuntimeException(e);
            }
        }
        else {
            String json = APIHelper.loadCache();
            if (!json.isBlank()) {
                ApiResponse response = APIHelper.parseApiResponse(json);
                callback.accept(response);
            } else {
                System.err.println("Keine Internet");
            }
        }
    }


    /**
     * Auswertung ob es sich um eine erfolgreiche Anfrage handelt
     * falls das so ist, wird die Antwort über eine Hilfsmethode
     * in ein Objekt mit den entsprechenden Daten gewandelt
     * @param apiResponse
     */
    private void handleApiResponse(HttpResponse<String> apiResponse) {
        if (apiResponse.statusCode() == 200) {
             System.out.println(apiResponse.body());
             APIHelper.saveCache( apiResponse.body());
             ApiResponse response = APIHelper.parseApiResponse( apiResponse.body() );
             callback.accept(response);
        } else {
            System.err.println(apiResponse.body());
        }
    }





}