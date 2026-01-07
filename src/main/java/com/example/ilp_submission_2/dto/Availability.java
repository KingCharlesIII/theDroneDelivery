package com.example.ilp_submission_2.dto;

public class Availability {
    private String dayOfWeek;
    private TimeOfDay from;
    private TimeOfDay until;

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public TimeOfDay getFrom() {
        return from;
    }

    public TimeOfDay getUntil() {
        return until;
    }
}