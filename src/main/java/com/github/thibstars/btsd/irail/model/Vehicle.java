package com.github.thibstars.btsd.irail.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Thibault Helsmoortel
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Vehicle(
        String name,

        @JsonProperty("shortname")
        String shortName
) {

}
