package com.example.ilp_submission_2.dto;

import java.util.List;

public class deliveries {
    private int deliveryId;
    private List<flightPath> flightPath;

    public deliveries(int deliveryId, List<flightPath> flightPath) {
        this.deliveryId = deliveryId;
        this.flightPath = flightPath;
    }

    public int getDeliveryId() {
        return deliveryId;
    }
    public  List<flightPath> getFlightPath() {
        return flightPath;
    }
}
