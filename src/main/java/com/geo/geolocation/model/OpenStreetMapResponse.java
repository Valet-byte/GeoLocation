package com.geo.geolocation.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OpenStreetMapResponse {
    @JsonProperty("display_name")
    private String displayName;
    private String error;
}
