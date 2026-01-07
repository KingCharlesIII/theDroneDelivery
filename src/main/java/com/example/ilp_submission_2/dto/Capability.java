package com.example.ilp_submission_2.dto;

public class Capability {
    private boolean cooling;
    private boolean heating;
    private double capacity;
    private int maxMoves;
    private double costPerMove;
    private double costInitial;
    private double costFinal;

    public Capability(boolean cooling, boolean heating, double capacity, int maxMoves,
                      double costPerMove,double costInitial, double costFinal ){
        this.cooling= cooling;
        this.heating= heating;
        this.capacity = capacity;
        this.maxMoves = maxMoves;
        this.costPerMove = costPerMove;
        this.costInitial = costInitial;
        this.costFinal = costFinal;
    }

    public double getCostPerMove() {
        return costPerMove;
    }

    public double getCostFinal() {
        return costFinal;
    }

    public double getCapacity() {
        return capacity;
    }

    public double getCostInitial() {
        return costInitial;
    }

    public int getMaxMoves() {
        return maxMoves;
    }

    public boolean getCooling(){
        return cooling;
    }

    public boolean getHeating(){
        return heating;
    }
}

