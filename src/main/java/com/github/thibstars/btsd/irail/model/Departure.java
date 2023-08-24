package com.github.thibstars.btsd.irail.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Thibault Helsmoortel
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Departure(
        int id,

        int delay,

        String station,

        Station stationInfo,

        String time,

        String vehicle,

        String platform,

        int canceled,

        int left
) {
}
