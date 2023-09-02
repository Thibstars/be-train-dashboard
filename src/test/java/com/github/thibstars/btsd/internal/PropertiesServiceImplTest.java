package com.github.thibstars.btsd.internal;

import java.util.Properties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

/**
 * @author Thibault Helsmoortel
 */
class PropertiesServiceImplTest {

    private PropertiesService propertiesService;

    @BeforeEach
    void setUp() {
        this.propertiesService = new PropertiesServiceImpl();
    }

    @Test
    void shouldGetApplicationProperties() {
        Properties applicationProperties = propertiesService.getApplicationProperties().orElseThrow();

        Assertions.assertNotNull(applicationProperties, "Result must not be null.");
        Assertions.assertFalse(applicationProperties.isEmpty(), "Result must not be empty.");

        applicationProperties.forEach(
                (key, value) -> {
                    Assertions.assertFalse(StringUtils.isBlank((String) key), "Key must not be blank.");
                    Assertions.assertFalse(StringUtils.isBlank((String) value), "Value must not be blank.");
                });
    }
}