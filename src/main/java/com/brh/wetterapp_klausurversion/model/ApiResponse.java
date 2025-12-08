package com.brh.wetterapp_klausurversion.model;

import java.util.List;

/**
 * Daten der API Antwort werden durch dieses Datenkonstrukt (Record) gespeichert
 * @param currTemperature
 * @param currWind
 * @param currRain
 * @param time
 * @param temp
 * @param wind
 * @param rain
 */
public record ApiResponse(Double currTemperature, Double currWind, Double currRain,
                          List<String> time, List<Double> temp , List<Double> wind, List<Double> rain )
{ }
