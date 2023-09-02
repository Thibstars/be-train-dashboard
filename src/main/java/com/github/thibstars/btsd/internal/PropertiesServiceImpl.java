package com.github.thibstars.btsd.internal;

import java.io.IOException;
import java.util.Optional;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Thibault Helsmoortel
 */
public class PropertiesServiceImpl implements PropertiesService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesServiceImpl.class);

    private static final String APPLICATION_PROPERTIES = "application.properties";

    @Override
    public Optional<Properties> getApplicationProperties() {
        LOGGER.info("Fetching application properties.");

        final Properties properties = new Properties();
        try {
            properties.load(this.getClass().getClassLoader().getResourceAsStream(APPLICATION_PROPERTIES));
        } catch (IOException e) {
            LOGGER.error("Failed to load application properties.", e);

            return Optional.empty();
        }

        return Optional.of(properties);
    }
}
