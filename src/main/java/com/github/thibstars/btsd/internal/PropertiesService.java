package com.github.thibstars.btsd.internal;

import java.util.Optional;
import java.util.Properties;

/**
 * @author Thibault Helsmoortel
 */
public interface PropertiesService {

    /**
     * Retrieves application properties.
     *
     * @return application properties
     */
    Optional<Properties> getApplicationProperties();

}
