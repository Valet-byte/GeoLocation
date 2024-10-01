package com.geo.geolocation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GeoLocationResponse {
    private double[] startPos;
    private double[] endPos;
    private String startAddress;
    private String endAddress;
    private long distance;
}
