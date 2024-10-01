package com.geo.geolocation.decorator;

import com.geo.geolocation.model.GeoLocationRequest;
import com.geo.geolocation.model.GeoLocationResponse;
import com.geo.geolocation.service.GeoLocationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class GeoLocationServiceValidationDecoratorTest {

    @Autowired
    @InjectMocks
    GeoLocationServiceValidationDecorator decorator;

    @MockBean
    GeoLocationServiceImpl geoLocationService;

    private final GeoLocationResponse response =
            new GeoLocationResponse(new double[]{45.062609, 41.923656},
                    new double[]{57.165054, 65.498056},
                    "start address",
                    "end address",
                    10);

    @BeforeEach
    void setUp() {
        when(geoLocationService.getGeoLocation(any(GeoLocationRequest.class)))
                .thenReturn(response);
    }

    @Test
    void validDataTest() {
        GeoLocationRequest request
                = new GeoLocationRequest(new double[]{45.062609, 41.923656}, new double[]{57.165054, 65.498056});

        GeoLocationResponse testResponse = decorator.getGeoLocation(request);

        Assertions.assertEquals(testResponse, response);
    }

    @Test
    void notValidDataTest() {
        GeoLocationRequest request
                = new GeoLocationRequest(null, new double[]{57.165054, 65.498056});

        Assertions.assertThrows(IllegalArgumentException.class, () -> decorator.getGeoLocation(request),
                "Coordinates must contain exactly two elements (latitude and longitude)");

        request.setEndPos(null);

        Assertions.assertThrows(IllegalArgumentException.class, () -> decorator.getGeoLocation(request),
                "Coordinates must contain exactly two elements (latitude and longitude)");

        request.setStartPos(new double[]{57.165054, 65.498056});

        Assertions.assertThrows(IllegalArgumentException.class, () -> decorator.getGeoLocation(request),
                "Coordinates must contain exactly two elements (latitude and longitude)");

        request.setEndPos(new double[]{91.062609, 41.923656, 31.312321});

        Assertions.assertThrows(IllegalArgumentException.class, () -> decorator.getGeoLocation(request),
                "Coordinates must contain exactly two elements (latitude and longitude)");

        request.setEndPos(new double[]{91.062609, 41.923656});

        Assertions.assertThrows(IllegalArgumentException.class, () -> decorator.getGeoLocation(request),
                "Latitude must be between -90 and 90, and longitude must be between -180 and 180");

        request.setEndPos(new double[]{41.062609, 200.923656});

        Assertions.assertThrows(IllegalArgumentException.class, () -> decorator.getGeoLocation(request),
                "Latitude must be between -90 and 90, and longitude must be between -180 and 180");

        request.setStartPos(new double[]{97.165054, 65.498056});

        Assertions.assertThrows(IllegalArgumentException.class, () -> decorator.getGeoLocation(request),
                "Latitude must be between -90 and 90, and longitude must be between -180 and 180");

        request.setStartPos(new double[]{57.165054, 200.498056});

        Assertions.assertThrows(IllegalArgumentException.class, () -> decorator.getGeoLocation(request),
                "Latitude must be between -90 and 90, and longitude must be between -180 and 180");
    }
}