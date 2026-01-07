package com.example.ilp_submission_2.dto;

public class Limits {
    private int lower;
    private int upper;

    public Limits(int lower, int upper) {
        this.lower = lower;
        this.upper = upper;
    }

    public int getLower() {
        return lower;
    }
    public int getUpper() {
        return upper;
    }
}
