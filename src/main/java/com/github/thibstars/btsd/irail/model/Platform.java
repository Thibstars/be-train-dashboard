package com.github.thibstars.btsd.irail.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Thibault Helsmoortel
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Platform(
        String name,

        String normal
) {

}
