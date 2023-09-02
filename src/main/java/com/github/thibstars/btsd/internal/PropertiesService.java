package com.github.thibstars.btsd.internal;

import java.util.Optional;
import java.util.Properties;

/**
 * @author Thibault Helsmoortel
 */
public interface PropertiesService {

    Optional<Properties> getApplicationProperties();

}
