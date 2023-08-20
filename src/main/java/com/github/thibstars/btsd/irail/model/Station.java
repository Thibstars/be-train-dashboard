package com.github.thibstars.btsd.irail.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Thibault Helsmoortel
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Station(
        String locationX,

        String locationY,

        String id,

        String name,

        @JsonProperty("standardname")
        String standardName
) {

}
