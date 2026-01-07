package com.example.ilp_submission_2.dto;

import java.util.List;

public class flightPath {
    private double lat;
    private double lng;

    public flightPath(double lng, double lat){
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}