package com.example.ilp_submission_2.service;

import com.example.ilp_submission_2.dto.Position;
import com.example.ilp_submission_2.dto.Region;
/*
The service class for the isInRegion method in
the controller class
 */
public class RegionService {
    private Position position1;
    private Region region;

    public RegionService(Position position1, Region region){
        this.position1 = position1;
        this.region = region;
    }

    public Position getPosition1() {
        return position1;
    }

    public Region getRegion() {
        return region;
    }

    /*
    Checks if a position lies inside a region, calls the
    boundary method in the region class which does the
    calculation
     */
    public boolean isItIn(Position position1, Region region){
        int theBoundary = region.boundary(region.getVertices(), position1);
        return (theBoundary % 2 == 1);
    }
}
