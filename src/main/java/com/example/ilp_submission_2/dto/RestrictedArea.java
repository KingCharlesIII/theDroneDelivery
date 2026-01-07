package com.example.ilp_submission_2.dto;

import java.util.List;

public class RestrictedArea{
    private String name;
    private int id;
    private Limits limits;
    private List<LngLatAlt> vertices;

    public RestrictedArea(String name, int id, Limits limits, List<LngLatAlt> vertices) {
        this.name = name;
        this.id = id;
        this.limits = limits;
        this.vertices = vertices;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public Limits getLimits() {
        return limits;
    }

    public List<LngLatAlt> getVertices() {
        return vertices;
    }
}
