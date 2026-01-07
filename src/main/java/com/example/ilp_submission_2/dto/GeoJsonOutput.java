package com.example.ilp_submission_2.dto;

import java.util.List;

public class GeoJsonOutput {
    private String type;
    private List<List<Double>> coordinates;

    public GeoJsonOutput(String type, List<List<Double>> coordinates) {
        this.type = type;
        this.coordinates = coordinates;
    }

    public String getType() {
        return type;
    }

    public List<List<Double>> getCoordinates() {
        return coordinates;
    }

}
