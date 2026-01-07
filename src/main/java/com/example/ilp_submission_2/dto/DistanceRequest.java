package com.example.ilp_submission_2.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DistanceRequest{
    private Position position1;
    private Position position2;

    @JsonCreator
    public DistanceRequest(@JsonProperty("position1") Position position1, @JsonProperty("position2") Position position2){
        this.position1 = position1;
        this.position2 = position2;
    }

    public Position getPosition1(){

        return this.position1;
    }

    public Position getPosition2(){

        return this.position2;
    }

}