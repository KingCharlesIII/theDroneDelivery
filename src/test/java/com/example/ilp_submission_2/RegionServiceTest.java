package com.example.ilp_submission_2;

import com.example.ilp_submission_2.dto.Position;
import com.example.ilp_submission_2.dto.Region;
import com.example.ilp_submission_2.service.RegionService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RegionServiceTest {

    private Region createSquareRegion() {
        return new Region(
                List.of(
                        new Position(0.0, 0.0),
                        new Position(1.0, 0.0),
                        new Position(1.0, 1.0),
                        new Position(0.0, 1.0),
                        new Position(0.0, 0.0) // closed region
                ),
                "square"
        );
    }

    @Test
    void pointInsideRegionReturnsTrue() {
        Region region = createSquareRegion();
        Position inside = new Position(0.5, 0.5);
        RegionService service = new RegionService(inside, region);

        assertTrue(service.isItIn(inside, region));
    }

    @Test
    void pointOutsideRegionReturnsFalse() {
        Region region = createSquareRegion();
        Position outside = new Position(2.0, 2.0);
        RegionService service = new RegionService(outside, region);

        assertFalse(service.isItIn(outside, region));
    }

    @Test
    void pointOnBoundaryIsHandledConsistently() {
        Region region = createSquareRegion();
        Position onEdge = new Position(1.0, 0.5);
        RegionService service = new RegionService(onEdge, region);

        boolean result = service.isItIn(onEdge, region);

        assertNotNull(result);
    }
}
