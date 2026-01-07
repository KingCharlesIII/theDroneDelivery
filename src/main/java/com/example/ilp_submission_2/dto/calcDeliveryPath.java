package com.example.ilp_submission_2.dto;

import java.util.List;

public class calcDeliveryPath {
    private double totalCost;
    private int totalMoves;
    List<dronePaths> dronePaths;

    public calcDeliveryPath(double totalCost, int totalMoves, List<dronePaths> dronePaths) {
        this.totalCost = totalCost;
        this.totalMoves = totalMoves;
        this.dronePaths = dronePaths;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public int getTotalMoves() {
        return totalMoves;
    }

    public List<dronePaths> getDronePaths() {
        return dronePaths;
    }
}
