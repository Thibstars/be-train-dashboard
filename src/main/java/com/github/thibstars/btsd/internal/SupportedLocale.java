package com.github.thibstars.btsd.internal;

import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

/**
 * @author Thibault Helsmoortel
 */
public enum SupportedLocale {

    ENGLISH(Locale.ENGLISH),

    DUTCH(new Locale("nl")),

    FRENCH(Locale.FRENCH),

    GERMAN(Locale.GERMAN);

    private final Locale locale;

    SupportedLocale(Locale locale) {
        this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }

    public static Optional<SupportedLocale> from(Locale locale) {
        return Arrays.stream(values())
                .filter(supportedLocale -> supportedLocale.getLocale().equals(locale))
                .findFirst();
    }
}
