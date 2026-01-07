package com.example.ilp_submission_2.dto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;


public class Position {
    private double lng;
    private double lat;

    @JsonCreator
    public Position(@JsonProperty("lng") double lng, @JsonProperty("lat") double lat){
        this.lng = lng;
        this.lat = lat;
    }

    public double getLng(){
        return this.lng;
    }

    public double getLat(){
        return this.lat;
    }

}