package com.example.ilp_submission_2.dto;

import java.util.List;

public class DronesAvailability {
    private String id;
    private List<Availability> availability;

    public DronesAvailability(String id, List<Availability> availability) {
        this.id = id;
        this.availability = availability;
    }

    public List<Availability> getAvailability() {
        return availability;
    }

    public String getId() {
        return id;
    }
}
