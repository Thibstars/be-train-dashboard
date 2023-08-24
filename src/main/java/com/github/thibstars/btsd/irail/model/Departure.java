package com.github.thibstars.btsd.irail.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Thibault Helsmoortel
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Departure(
        int id,

        int delay,

        String station,

        @JsonProperty("stationinfo")
        Station stationInfo,

        String time,

        String vehicle,

        String platform,

        int canceled,

        int left
) {
}
