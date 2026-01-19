package com.example.ilp_submission_2;

import com.example.ilp_submission_2.dto.Position;
import com.example.ilp_submission_2.service.GeoService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GeoServiceTest {

    @Test
    void distanceBetweenSamePointIsZero() {
        Position p = new Position(0.0, 0.0);
        GeoService geoService = new GeoService(p, p);

        double distance = geoService.euclidianDistance(p, p);

        assertEquals(0.0, distance, 1e-9);
    }

    @Test
    void distanceIsSymmetric() {
        Position p1 = new Position(0.0, 0.0);
        Position p2 = new Position(3.0, 4.0);
        GeoService geoService = new GeoService(p1, p2);

        double d1 = geoService.euclidianDistance(p1, p2);
        double d2 = geoService.euclidianDistance(p2, p1);

        assertEquals(d1, d2, 1e-9);
    }

    @Test
    void distanceMatchesExpectedEuclideanValue() {
        Position p1 = new Position(0.0, 0.0);
        Position p2 = new Position(3.0, 4.0);
        GeoService geoService = new GeoService(p1, p2);

        double distance = geoService.euclidianDistance(p1, p2);

        assertEquals(5.0, distance, 1e-9);
    }

    @Test
    void isCloseReturnsTrueJustUnderThreshold() {
        Position p1 = new Position(0.0, 0.0);
        Position p2 = new Position(0.0001, 0.0);
        GeoService geoService = new GeoService(p1, p2);

        assertTrue(geoService.isItClose(p1, p2));
    }

    @Test
    void isCloseReturnsFalseJustOverThreshold() {
        Position p1 = new Position(0.0, 0.0);
        Position p2 = new Position(0.001, 0.0);
        GeoService geoService = new GeoService(p1, p2);

        assertFalse(geoService.isItClose(p1, p2));
    }
}
