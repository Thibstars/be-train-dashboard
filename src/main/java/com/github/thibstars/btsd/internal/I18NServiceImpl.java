package com.github.thibstars.btsd.internal;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Thibault Helsmoortel
 */
public class I18NServiceImpl implements I18NService {

    private static final Logger LOGGER = LoggerFactory.getLogger(I18NServiceImpl.class);

    private static final String LOCALE_PREFERENCE_KEY = "locale";

    private final PreferencesService preferencesService;

    public I18NServiceImpl(PreferencesService preferencesService) {
        this.preferencesService = preferencesService;
    }

    @Override
    public Locale getPreferredLocale() {
        Optional<String> preferredLocale = preferencesService.get(LOCALE_PREFERENCE_KEY);

        if (preferredLocale.isPresent()) {
            return preferredLocale.map(Locale::new)
                    .flatMap(SupportedLocale::from)
                    .map(SupportedLocale::getLocale)
                    .orElse(getSystemDefaultOrElseFixedDefault());
        }

        Locale systemDefaultOrElseFixedDefault = getSystemDefaultOrElseFixedDefault();

        changeLocale(systemDefaultOrElseFixedDefault);

        return systemDefaultOrElseFixedDefault;
    }

    @Override
    public void changeLocale(Locale locale) {
        LOGGER.info("Changing locale: {}", locale);

        preferencesService.put(LOCALE_PREFERENCE_KEY, locale.toString());
    }

    @Override
    public String getMessage(String key) {
        String value;
        try {
            value = getMessageBundle().getString(key);
        } catch (MissingResourceException e) {
            LOGGER.warn("No value found for key: {}", key);
            value = key;
        }

        return value;
    }

    @Override
    public String getMessage(String key, Locale locale) {
        String value;
        try {
            value = getMessageBundle(locale).getString(key);
        } catch (MissingResourceException e) {
            LOGGER.warn("No value found for key: {}", key);
            value = key;
        }

        return value;
    }

    @Override
    public String getPreferredDateTimeFormat() {
        return preferencesService.getDateTimeFormatPreference();
    }

    private ResourceBundle getMessageBundle() {
        return getMessageBundle(getPreferredLocale());
    }

    private ResourceBundle getMessageBundle(Locale locale) {
        return ResourceBundle.getBundle("messages", locale);
    }

    private static Locale getSystemDefaultOrElseFixedDefault() {
        return SupportedLocale.from(Locale.getDefault())
                .map(SupportedLocale::getLocale)
                .orElse(SupportedLocale.ENGLISH.getLocale());
    }
}
