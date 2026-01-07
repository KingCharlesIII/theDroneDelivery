package com.example.ilp_submission_2.service;

import com.example.ilp_submission_2.dto.Angle;
import com.example.ilp_submission_2.dto.Position;

/*
The service class for the nextPosition method in the
controller class
 */
public class NextPositionService {
    private Position start;
    private Angle angle;

    public NextPositionService(Position start, Angle angle){
        this.start = start;
        this.angle = angle;
    }

    public Position getStart() {
        return start;
    }

    public Angle getAngle() {
        return angle;
    }

    /*
    Calculates the new position
     */
    public Position newPosition(Position position1, Angle angle){
        double cosValue = angle.getCosValue();
        double sinValue = angle.getSinValue();
        double lng = position1.getLng() + (0.00015 *cosValue);
        double lat = position1.getLat() + (0.00015 *sinValue);
        Position newPosition1 = new Position(lng, lat);

        return newPosition1;
    }


}
