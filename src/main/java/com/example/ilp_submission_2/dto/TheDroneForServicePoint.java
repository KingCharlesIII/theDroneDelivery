package com.example.ilp_submission_2.dto;

import java.util.List;

public class TheDroneForServicePoint {
    private int servicePointId;
    private List<DronesAvailability> drones;

    public TheDroneForServicePoint(int servicePointId, List<DronesAvailability> drones) {
        this.servicePointId = servicePointId;
        this.drones = drones;
    }

    public int getservicePointId() {
        return servicePointId;
    }

    public List<DronesAvailability> getDrones() {
        return drones;
    }

}
