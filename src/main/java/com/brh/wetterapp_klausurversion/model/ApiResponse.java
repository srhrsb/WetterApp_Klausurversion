package com.brh.wetterapp_klausurversion.model;

import java.util.List;

public record ApiResponse(Double currTemperature, Double currWind, Double currRain,
                          List<String> time, List<Double> temp , List<Double> wind, List<Double> rain )
{ }
