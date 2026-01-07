package com.example.ilp_submission_2.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LngLatAlt {
    private double lng;
    private double lat;
    private double alt;

    @JsonCreator
    public LngLatAlt(@JsonProperty("lng") double lng,
                     @JsonProperty("lat") double lat,
                     @JsonProperty("alt") double alt) {
        this.lng = lng;
        this.lat = lat;
        this.alt = alt;
    }

    public double getAlt() {
        return alt;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

}
