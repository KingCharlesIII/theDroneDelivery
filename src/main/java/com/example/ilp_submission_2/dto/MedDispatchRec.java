package com.example.ilp_submission_2.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MedDispatchRec {
    private int id;
    private String date;
    private String time;
    private Requirements requirements;
    private Position delivery;

    @JsonCreator
    public MedDispatchRec(@JsonProperty(value = "id", required = true) int id, @JsonProperty("date") String date,
                          @JsonProperty("time") String time, @JsonProperty(value = "requirements", required = true) Requirements requirements,
    @JsonProperty("delivery") Position delivery) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.requirements = requirements;
        this.delivery = delivery;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }
    public String getTime() {
        return time;
    }
    public Requirements getRequirements() {
        return requirements;
    }

    public Position getDelivery() {
        return delivery;
    }
}
