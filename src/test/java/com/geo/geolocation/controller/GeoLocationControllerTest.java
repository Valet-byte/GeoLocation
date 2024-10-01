package com.geo.geolocation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geo.geolocation.model.GeoLocationRequest;
import com.geo.geolocation.model.GeoLocationResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GeoLocationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetLocationSuccess() throws Exception {
        GeoLocationRequest request = new GeoLocationRequest(new double[]{45.062609, 41.923656}, new double[]{57.165054, 65.498056});
        GeoLocationResponse response = new GeoLocationResponse(
                new double[]{45.062609, 41.923656}, new double[]{57.165054, 65.498056},
                "29Д, проспект Кулакова, Северо-Западный, Промышленный район, Ставрополь, городской округ Ставрополь, Ставропольский край, Северо-Кавказский федеральный округ, 355000, Россия",
                "84, улица Свободы, Калининский административный округ, Тюмень, городской округ Тюмень, Тюменская область, Уральский федеральный округ, 625001, Россия",
                2106981
        );


        ResultActions result = mockMvc.perform(get("/api/location")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        assertEquals(objectMapper.readValue(result.andReturn().getResponse().getContentAsByteArray(), GeoLocationResponse.class), response);
    }

    @Test
    public void testGetLocationValidationError() throws Exception {
        GeoLocationRequest request = new GeoLocationRequest(new double[]{45.062609, 41.923656}, new double[]{57.165054, 65.498056, 65.498056});

        ResultActions result = mockMvc.perform(get("/api/location")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        assertEquals(result.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8), "Coordinates must contain exactly two elements (latitude and longitude)");

        request = new GeoLocationRequest(new double[]{45.062609}, new double[]{57.165054, 65.498056});

        result = mockMvc.perform(get("/api/location")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        assertEquals(result.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8), "Coordinates must contain exactly two elements (latitude and longitude)");

        request = new GeoLocationRequest(null, null);

        result = mockMvc.perform(get("/api/location")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        assertEquals(result.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8), "Coordinates must contain exactly two elements (latitude and longitude)");

        request = new GeoLocationRequest(new double[]{45.062609, 41.923656}, new double[]{57.165054, 200.498056});

        result = mockMvc.perform(get("/api/location")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        assertEquals(result.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8), "Latitude must be between -90 and 90, and longitude must be between -180 and 180");

        request = new GeoLocationRequest(new double[]{91.062609, 41.923656}, new double[]{57.165054, 65.498056});

        result = mockMvc.perform(get("/api/location")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        assertEquals(result.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8), "Latitude must be between -90 and 90, and longitude must be between -180 and 180");
    }

}