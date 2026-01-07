package com.example.ilp_submission_2;

import com.example.ilp_submission_2.dto.*;
import com.example.ilp_submission_2.service.CalcDeliveryPathService;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CalcDeliveryPathServiceTest {

    @Test
    void testAvailabilityCheck() {

        CalcDeliveryPathService service = new CalcDeliveryPathService();

        // Build Requirements (matches your JSON)
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


        List<MedDispatchRec> recList = List.of(rec);

        List<String> droneIds = List.of("1", "5", "7", "10");

        List<String> available = service.getDroneIdsFromAvailability(droneIds, recList);

        System.out.println("Available drones = " + available);
    }
}
