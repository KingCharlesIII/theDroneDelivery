package com.example.ilp_submission_2;

import com.example.ilp_submission_2.dto.Angle;
import com.example.ilp_submission_2.dto.Position;
import com.example.ilp_submission_2.service.NextPositionService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NextPositionServiceTest {

    private static final double STEP = 0.00015;
    private static final double TOLERANCE = 1e-9;

    @Test
    void angleZeroMovesEast() {
        Position start = new Position(0.0, 0.0);
        Angle angle = new Angle(0.0);
        NextPositionService service = new NextPositionService(start, angle);

        Position next = service.newPosition(start, angle);

        assertEquals(start.getLng() + STEP, next.getLng(), TOLERANCE);
        assertEquals(start.getLat(), next.getLat(), TOLERANCE);
    }

    @Test
    void angleNinetyMovesNorth() {
        Position start = new Position(0.0, 0.0);
        Angle angle = new Angle(90.0);
        NextPositionService service = new NextPositionService(start, angle);

        Position next = service.newPosition(start, angle);

        assertEquals(start.getLng(), next.getLng(), TOLERANCE);
        assertEquals(start.getLat() + STEP, next.getLat(), TOLERANCE);
    }

    @Test
    void angleOneEightyMovesWest() {
        Position start = new Position(0.0, 0.0);
        Angle angle = new Angle(180.0);
        NextPositionService service = new NextPositionService(start, angle);

        Position next = service.newPosition(start, angle);

        assertEquals(start.getLng() - STEP, next.getLng(), TOLERANCE);
        assertEquals(start.getLat(), next.getLat(), TOLERANCE);
    }

    @Test
    void angleTwoSeventyMovesSouth() {
        Position start = new Position(0.0, 0.0);
        Angle angle = new Angle(270.0);
        NextPositionService service = new NextPositionService(start, angle);

        Position next = service.newPosition(start, angle);

        assertEquals(start.getLng(), next.getLng(), TOLERANCE);
        assertEquals(start.getLat() - STEP, next.getLat(), TOLERANCE);
    }

    @Test
    void movementDistanceIsConstant() {
        Position start = new Position(1.0, 1.0);
        Angle angle = new Angle(45.0);
        NextPositionService service = new NextPositionService(start, angle);

        Position next = service.newPosition(start, angle);

        double dx = next.getLng() - start.getLng();
        double dy = next.getLat() - start.getLat();
        double distance = Math.sqrt(dx * dx + dy * dy);

        assertEquals(STEP, distance, 1e-6);
    }
}
