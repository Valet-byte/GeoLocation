package com.geo.geolocation.service;

import com.geo.geolocation.model.GeoLocationRequest;
import com.geo.geolocation.model.GeoLocationResponse;
import com.geo.geolocation.model.OpenStreetMapResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class GeoLocationServiceImpl implements GeoLocationService {

    private final RestTemplate restTemplate;
    private static final int EARTH_RADIUS = 6371000;

    private static final String GEOCODE_URL = "https://nominatim.openstreetmap.org/reverse?lat=%s&lon=%s&format=json";

    public GeoLocationResponse getGeoLocation(GeoLocationRequest request) {
        double[] startPos = request.getStartPos();
        double[] endPos = request.getEndPos();

        return new GeoLocationResponse(
                startPos,
                endPos,
                getAddress(startPos[0], startPos[1]),
                getAddress(endPos[0], endPos[1]),
                calculateDistance(startPos[0], startPos[1], endPos[0], endPos[1]));

    }

    private String getAddress(double lat, double lon) {
        OpenStreetMapResponse openStreetMapResponse = restTemplate
                .getForObject(String.format(GEOCODE_URL, lat, lon), OpenStreetMapResponse.class);

        if (openStreetMapResponse == null || StringUtils.hasText(openStreetMapResponse.getError())) {
            return "Address not found";
        }

        return openStreetMapResponse.getDisplayName();
    }

    private long calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (long) (EARTH_RADIUS * c);
    }
}
