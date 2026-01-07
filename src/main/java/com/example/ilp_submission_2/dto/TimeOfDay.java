package com.example.ilp_submission_2.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import java.time.LocalTime;

public class TimeOfDay {
    private int hour;
    private int minute;
    private int second;
    private int nano;

    @JsonCreator
    public TimeOfDay(JsonNode node) {
        if (node.isTextual()) {
            LocalTime t = LocalTime.parse(node.asText());
            this.hour = t.getHour();
            this.minute = t.getMinute();
            this.second = t.getSecond();
            this.nano = t.getNano();
        }
    }

    public int getHour() { return hour; }
    public int getMinute() { return minute; }
    public int getSecond() { return second; }
    public int getNano() { return nano; }
}
