package com.example.ilp_submission_2.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Requirements {

    private double capacity;
    private boolean cooling = false;
    private boolean heating = false;
    private Double maxCost;

    @JsonCreator
    public Requirements(
            @JsonProperty(value = "capacity", required = true) double capacity,
            @JsonProperty("cooling") Boolean cooling,
            @JsonProperty("heating") Boolean heating,
            @JsonProperty("maxCost") Double maxCost
    ) {
        this.capacity = capacity;
        this.cooling = (cooling != null) ? cooling : false;
        this.heating = (heating != null) ? heating : false;
        this.maxCost = maxCost;
    }

    public double getCapacity() { return capacity; }
    public boolean getCooling() { return cooling; }
    public boolean getHeating() { return heating; }
    public Double getMaxCost() { return maxCost; }
}
