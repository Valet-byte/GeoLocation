package com.geo.geolocation.decorator;

import com.geo.geolocation.model.GeoLocationRequest;
import com.geo.geolocation.model.GeoLocationResponse;
import com.geo.geolocation.service.GeoLocationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Primary
@RequiredArgsConstructor
public class GeoLocationServiceValidationDecorator implements GeoLocationService {

    private final GeoLocationService geoLocationService;
    private final Logger logger = LoggerFactory.getLogger(GeoLocationServiceValidationDecorator.class);

    @Override
    public GeoLocationResponse getGeoLocation(GeoLocationRequest request) {
        validateCoordinates(request.getStartPos());
        validateCoordinates(request.getEndPos());
        return geoLocationService.getGeoLocation(request);
    }

    private void validateCoordinates(double[] coordinates) {
        if (coordinates == null || coordinates.length != 2) {
            throw new IllegalArgumentException(
                    "Coordinates must contain exactly two elements (latitude and longitude)");
        }
        if (coordinates[0] < -90 || coordinates[0] > 90 || coordinates[1] < -180 || coordinates[1] > 180) {
            throw new IllegalArgumentException(
                    "Latitude must be between -90 and 90, and longitude must be between -180 and 180");
        }
    }
}
