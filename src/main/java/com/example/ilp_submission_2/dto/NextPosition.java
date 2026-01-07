package com.example.ilp_submission_2.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NextPosition {
    private Position start;
    private Angle angle;


    @JsonCreator
    public NextPosition(@JsonProperty("start") Position start, @JsonProperty("angle") Angle angle){
        this.start = start;
        this.angle = angle;
    }

    public Angle getAngle(){
        return this.angle;
    }

    public Position getPosition(){
        return this.start;
    }
}