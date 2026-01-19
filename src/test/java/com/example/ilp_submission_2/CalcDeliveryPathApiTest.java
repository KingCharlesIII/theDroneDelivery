package com.example.ilp_submission_2;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CalcDeliveryPathApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void calcDeliveryPathAsGeoJsonReturnsValidLineString() throws Exception {

        String body = """
            [
              {
                "id": 1,
                "date": "2025-01-01",
                "time": "10:00",
                "delivery": { "lng": -3.1869, "lat": 55.9444 },
                "requirements": {
                  "capacity": 1.0,
                  "cooling": false,
                  "heating": false,
                  "maxCost": null
                }
              }
            ]
            """;

        MvcResult result = mockMvc.perform(
                        post("/api/v1/calcDeliveryPathAsGeoJson")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body)
                )
                .andExpect(status().isOk())
                .andReturn();

        JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString());

        assertEquals("LineString", json.get("type").asText());
        assertTrue(json.get("coordinates").isArray());
    }
}
