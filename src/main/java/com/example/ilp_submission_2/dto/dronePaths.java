package com.example.ilp_submission_2.dto;

import java.util.List;

public class dronePaths {
    private String droneId;
    private List<deliveries> deliveries;


    public dronePaths(String droneId, List<deliveries> deliveries) {
        this.droneId = droneId;
        this.deliveries = deliveries;
    }

    public String getDroneId() {
        return droneId;
    }

    public List<deliveries> getDeliveries() {
        return deliveries;
    }
}
