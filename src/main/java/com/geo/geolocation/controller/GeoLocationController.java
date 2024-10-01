package com.geo.geolocation.controller;

import com.geo.geolocation.model.GeoLocationRequest;
import com.geo.geolocation.service.GeoLocationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GeoLocationController {

    private final GeoLocationService geoLocationService;
    private static final Logger logger = LoggerFactory.getLogger(GeoLocationController.class);

    @GetMapping("/location")
    public ResponseEntity<?> getLocation(@RequestBody GeoLocationRequest request) {
        try {
            return ResponseEntity.ok(geoLocationService.getGeoLocation(request));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
