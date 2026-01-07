package com.example.ilp_submission_2.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class IsInRegion {
    private Position position;
    private Region region;

    @JsonCreator
    public IsInRegion(@JsonProperty("position") Position position, @JsonProperty("region") Region region){
        this.position = position;
        this.region = region;}


    public Position getPosition() {
        return this.position;
    }

    public Region getRegion(){
        return this.region;
    }
}