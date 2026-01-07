package com.example.ilp_submission_2;
import com.example.ilp_submission_2.dto.*;
import com.example.ilp_submission_2.service.*;

import java.util.*;

public class TestAvailability {

    public static void main(String[] args) {

        DroneServices droneServices = new DroneServices();
        CalcDeliveryPathService calcService = new CalcDeliveryPathService();

        Requirements req = new Requirements(
                0.75,
                false,
                true,
                13.5
        );

        Position pos = new Position(-3.00, 55.121);

        MedDispatchRec rec = new MedDispatchRec(
                123,
                "2025-12-22",
                "14:30",
                req,
                pos
        );

        List<MedDispatchRec> list = List.of(rec);

        System.out.println("\n--- getQueryId() ---");
        List<String> idList = droneServices.getQueryId(list);
        System.out.println("Capability matches: " + idList);

        System.out.println("\n--- getDroneIdsFromAvailability() DEBUG ---");
        List<String> availList = calcService.getDroneIdsFromAvailability(idList, list);
        System.out.println("Availability matches: " + availList);
    }
}
