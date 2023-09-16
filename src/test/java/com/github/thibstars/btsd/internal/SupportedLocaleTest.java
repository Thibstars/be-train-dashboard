package com.github.thibstars.btsd.internal;

import java.util.Locale;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Thibault Helsmoortel
 */
class SupportedLocaleTest {

    @Test
    void fromShouldReturnSupportedLocale() {
        SupportedLocale supportedLocale = SupportedLocale.ENGLISH;

        Optional<SupportedLocale> result = SupportedLocale.from(supportedLocale.getLocale());

        Assertions.assertSame(supportedLocale, result.orElseThrow(), "Correct locale must be returned.");
    }

    @Test
    void fromShouldReturnEmptyOptional() {
        Optional<SupportedLocale> result = SupportedLocale.from(new Locale("Tequila"));

        Assertions.assertTrue(result.isEmpty(), "Result must be empty.");
    }
}