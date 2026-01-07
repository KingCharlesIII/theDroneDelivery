package com.example.ilp_submission_2.dto;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Angle {
    private double angle;

    public Angle( double angle){
        this.angle = angle;
    }

    public double value() {
        return angle;
    }


    public double getCosValue(){
        return Math.cos(Math.toRadians(this.angle));
    }

    public double getSinValue(){
        return Math.sin(Math.toRadians(this.angle));
    }
}