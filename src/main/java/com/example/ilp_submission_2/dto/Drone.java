package com.example.ilp_submission_2.dto;

import java.util.List;

public class Drone {
    private String name;
    private String id;
    private Capability capability;


    public Drone(String name, String id, Capability capability){
        this.name = name;
        this.id = id;
        this.capability = capability;
    }

    public String getId() {
        return id;
    }

    public Capability getCapability() {
        return capability;
    }

    public String getName() {
        return name;
    }
}