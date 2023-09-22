package com.github.thibstars.btsd.internal;

import java.util.Optional;

/**
 * @author Thibault Helsmoortel
 */
public interface PreferencesService {

    Optional<String> get(String key);

    void put(String key, String value);

    String getDateTimeFormatPreference();

    void putDateTimeFormatPreference(String dateTimeFormat);
}
