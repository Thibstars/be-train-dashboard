package com.github.thibstars.btsd.internal;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.prefs.Preferences;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author Thibault Helsmoortel
 */
@ExtendWith(MockitoExtension.class)
class PreferencesServiceImplTest {

    private static final String PREF_KEY_PREFIX = "btsd_";

    private static final String DATE_TIME_FORMAT_PREFERENCE_KEY =  "dateTimeFormat";

    @Mock
    private Preferences preferences;

    private PreferencesServiceImpl preferencesService;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        this.preferencesService = new PreferencesServiceImpl();

        Field preferencesField = PreferencesServiceImpl.class.getDeclaredField("preferences");
        preferencesField.setAccessible(true);
        preferencesField.set(preferencesService, preferences);
    }

    @Test
    void shouldGetValue() {
        String key = "key";
        String value = "value";
        Mockito.when(preferences.get(PREF_KEY_PREFIX + key, null)).thenReturn(value);

        Optional<String> result = preferencesService.get(key);

        Assertions.assertEquals(value, result.orElseThrow(), "Value must be correct.");
    }

    @Test
    void shouldGetEmptyOptional() {
        Optional<String> result = preferencesService.get("key");

        Assertions.assertTrue(result.isEmpty(), "Result must be empty.");
    }

    @Test
    void shouldPutValue() {
        String key = "key";
        String value = "value";
        preferencesService.put(key, value);

        Mockito.verify(preferences).put(PREF_KEY_PREFIX + key, value);
    }

    @Test
    void shouldGetDateTimeFormatPreference() {
        String value = "value";
        Mockito.when(preferences.get(PREF_KEY_PREFIX + DATE_TIME_FORMAT_PREFERENCE_KEY, null)).thenReturn(value);

        String result = preferencesService.getDateTimeFormatPreference();

        Assertions.assertEquals(value, result, "Value must be correct.");
    }

    @Test
    void shouldPutDateTimeFormatPreference() {
        String value = "value";
        preferencesService.putDateTimeFormatPreference(value);

        Mockito.verify(preferences).put(PREF_KEY_PREFIX + DATE_TIME_FORMAT_PREFERENCE_KEY, value);
    }
}