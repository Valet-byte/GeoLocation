package com.geo.geolocation.service;

import com.geo.geolocation.model.GeoLocationRequest;
import com.geo.geolocation.model.GeoLocationResponse;

public interface GeoLocationService {
    GeoLocationResponse getGeoLocation(GeoLocationRequest request);
}
