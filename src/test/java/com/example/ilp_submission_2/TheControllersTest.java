package com.example.ilp_submission_2;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TheControllersTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    /* ---------------- /distanceTo ---------------- */

    @Test
    void distanceToValidRequestReturns200() throws Exception {
        String body = """
            {
              "position1": { "lng": 0.0, "lat": 0.0 },
              "position2": { "lng": 3.0, "lat": 4.0 }
            }
            """;

        mockMvc.perform(post("/api/v1/distanceTo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(content().string("5.0"));
    }

    @Test
    void distanceToInvalidLatLngReturns400() throws Exception {
        String body = """
            {
              "position1": { "lng": 0.0, "lat": 200.0 },
              "position2": { "lng": 0.0, "lat": 0.0 }
            }
            """;

        mockMvc.perform(post("/api/v1/distanceTo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    void distanceToNullBodyReturns400() throws Exception {
        mockMvc.perform(post("/api/v1/distanceTo")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /* ---------------- /isCloseTo ---------------- */

    @Test
    void isCloseToReturnsTrueForClosePoints() throws Exception {
        String body = """
            {
              "position1": { "lng": 0.0, "lat": 0.0 },
              "position2": { "lng": 0.0001, "lat": 0.0 }
            }
            """;

        mockMvc.perform(post("/api/v1/isCloseTo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void isCloseToReturnsFalseForFarPoints() throws Exception {
        String body = """
            {
              "position1": { "lng": 0.0, "lat": 0.0 },
              "position2": { "lng": 1.0, "lat": 1.0 }
            }
            """;

        mockMvc.perform(post("/api/v1/isCloseTo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    /* ---------------- /nextPosition ---------------- */

    @Test
    void nextPositionValidRequestReturns200() throws Exception {
        String body = """
        {
          "start": { "lng": 0.0, "lat": 0.0 },
          "angle": 0.0
        }
        """;

        mockMvc.perform(post("/api/v1/nextPosition")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());
    }

    @Test
    void nextPositionInvalidAngleReturns400() throws Exception {
        String body = """
            {
              "start": { "lng": 0.0, "lat": 0.0 },
              "angle": { "value": 360.0 }
            }
            """;

        mockMvc.perform(post("/api/v1/nextPosition")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    /* ---------------- /isInRegion ---------------- */

    @Test
    void isInRegionValidClosedRegionReturns200() throws Exception {
        String body = """
            {
              "position": { "lng": 0.5, "lat": 0.5 },
              "region": {
                "name": "square",
                "vertices": [
                  { "lng": 0.0, "lat": 0.0 },
                  { "lng": 1.0, "lat": 0.0 },
                  { "lng": 1.0, "lat": 1.0 },
                  { "lng": 0.0, "lat": 1.0 },
                  { "lng": 0.0, "lat": 0.0 }
                ]
              }
            }
            """;

        mockMvc.perform(post("/api/v1/isInRegion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());
    }

    @Test
    void isInRegionNonClosedRegionReturns400() throws Exception {
        String body = """
            {
              "position": { "lng": 0.5, "lat": 0.5 },
              "region": {
                "name": "open",
                "vertices": [
                  { "lng": 0.0, "lat": 0.0 },
                  { "lng": 1.0, "lat": 0.0 },
                  { "lng": 1.0, "lat": 1.0 },
                  { "lng": 0.0, "lat": 1.0 }
                ]
              }
            }
            """;

        mockMvc.perform(post("/api/v1/isInRegion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }
}
