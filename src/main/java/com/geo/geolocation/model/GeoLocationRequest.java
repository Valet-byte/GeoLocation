package com.geo.geolocation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GeoLocationRequest {
    private double[] startPos;
    private double[] endPos;
}
