package com.github.thibstars.btsd.internal;

import java.util.Optional;
import java.util.prefs.Preferences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Thibault Helsmoortel
 */
public class PreferencesServiceImpl implements PreferencesService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PreferencesServiceImpl.class);

    private static final String PREF_KEY_PREFIX = "btsd_";

    private final Preferences preferences;

    public PreferencesServiceImpl() {
        this.preferences = Preferences.userRoot();
    }

    @Override
    public Optional<String> get(String key) {
        String value = preferences.get(PREF_KEY_PREFIX + key, null);

        return Optional.ofNullable(value);
    }

    @Override
    public void put(String key, String value) {
        LOGGER.info("Storing preference: [{}: {}]", key, value);

        preferences.put(PREF_KEY_PREFIX + key, value);
    }
}
