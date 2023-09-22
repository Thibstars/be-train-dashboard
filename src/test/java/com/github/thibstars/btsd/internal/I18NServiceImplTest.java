package com.github.thibstars.btsd.internal;

import java.util.Locale;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author Thibault Helsmoortel
 */
@ExtendWith(MockitoExtension.class)
class I18NServiceImplTest {

    private static final String LOCALE_PREFERENCE_KEY = "locale";

    @Mock
    private PreferencesService preferencesService;

    @InjectMocks
    private I18NServiceImpl i18NService;

    @Test
    void shouldGetPreferredLocaleFromPreferences() {
        Locale locale = SupportedLocale.ENGLISH.getLocale();

        Mockito.when(preferencesService.get(LOCALE_PREFERENCE_KEY))
                .thenReturn(Optional.of(locale.toString()));

        Locale result = i18NService.getPreferredLocale();

        Assertions.assertNotNull(result, "Result must not be null.");
        Assertions.assertEquals(locale, result, "Result must match the expected.");
    }

    @Test
    void shouldGetPreferredLocaleDefaultAndStoreToPreferences() {
        Locale locale = SupportedLocale.ENGLISH.getLocale();

        Mockito.when(preferencesService.get(LOCALE_PREFERENCE_KEY))
                .thenReturn(Optional.empty());

        Locale result = i18NService.getPreferredLocale();

        Assertions.assertNotNull(result, "Result must not be null.");
        Assertions.assertEquals(locale, result, "Result must match the expected.");

        Mockito.verify(preferencesService).put(LOCALE_PREFERENCE_KEY, locale.toString());
    }

    @Test
    void shouldGetMessage() {
        String key = "main.title";

        String result = i18NService.getMessage(key);

        Assertions.assertNotNull(result, "Result must not be null.");
        Assertions.assertFalse(result.isBlank(), "Result must not be blank.");
        Assertions.assertEquals("Belgian Train Station Dashboard", result, "Result must match the expected.");
    }

    @Test
    void shouldGetKeyWhenMessageDoesNotExist() {
        String key = "unknown.key";

        String result = i18NService.getMessage(key);

        Assertions.assertNotNull(result, "Result must not be null.");
        Assertions.assertFalse(result.isBlank(), "Result must not be blank.");
        Assertions.assertEquals(key, result, "Result must match the key.");
    }

    @Test
    void shouldGetMessageWithLocale() {
        String key = "main.title";
        Locale locale = Locale.ENGLISH;

        String result = i18NService.getMessage(key, locale);

        Assertions.assertNotNull(result, "Result must not be null.");
        Assertions.assertFalse(result.isBlank(), "Result must not be blank.");
        Assertions.assertEquals("Belgian Train Station Dashboard", result, "Result must match the expected.");
    }

    @Test
    void shouldGetKeyWhenMessageWithLocaleDoesNotExist() {
        String key = "unknown.key";

        Locale locale = Locale.ENGLISH;
        String result = i18NService.getMessage(key, locale);

        Assertions.assertNotNull(result, "Result must not be null.");
        Assertions.assertFalse(result.isBlank(), "Result must not be blank.");
        Assertions.assertEquals(key, result, "Result must match the key.");
    }

    @Test
    void shouldGetPreferredDateTimeFormat() {
        String dateTimeFormat = "dd/MM/yyyy";
        Mockito.when(preferencesService.getDateTimeFormatPreference()).thenReturn(dateTimeFormat);

        String result = i18NService.getPreferredDateTimeFormat();

        Assertions.assertNotNull(result, "Result must not be null.");
        Assertions.assertFalse(result.isBlank(), "Result must not be blank.");
        Assertions.assertEquals(dateTimeFormat, result, "Result match the expected.");
    }
}