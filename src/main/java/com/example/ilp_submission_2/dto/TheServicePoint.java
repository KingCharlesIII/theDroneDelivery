package com.example.ilp_submission_2.dto;

public class TheServicePoint {
    private int id;
    private String name;
    private LngLatAlt location;

    public TheServicePoint(int id, String name, LngLatAlt location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public LngLatAlt getLocation() {
        return location;
    }

    public int getservicePointId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
