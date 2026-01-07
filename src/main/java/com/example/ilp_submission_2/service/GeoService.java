package com.example.ilp_submission_2.service;

import com.example.ilp_submission_2.dto.Position;

/*
The service class for the distanceTo and isCloseTo methods in
the controller class
 */
public class GeoService {
    private Position position1;
    private Position position2;

    public GeoService(Position position1, Position position2){
        this.position1 = position1;
        this.position2 = position2;
    }

    public Position getPosition1() {
        return position1;
    }

    public Position getPosition2() {
        return position2;
    }

    /*
    The method to calculate the distance between two positions
     */
    public double euclidianDistance(Position position1, Position position2){
        return (Math.sqrt(Math.pow((position2.getLat() - position1.getLat()),2) + Math.pow((position2.getLng() - position1.getLng()),2)));
    }


    /*
    The method that checks if two positions are within a
    close range, it returns true if is, and false if it isn't
     */
    public boolean isItClose(Position position1, Position position2){
        double theDistance = this.euclidianDistance(position1, position2);
        return (0.00015 > theDistance);
    }


}
