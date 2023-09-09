package com.github.thibstars.btsd.internal;

import java.util.Locale;
import java.util.Optional;

/**
 * @author Thibault Helsmoortel
 */
public class I18NServiceImpl implements I18NService {

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

        preferencesService.put(LOCALE_PREFERENCE_KEY, systemDefaultOrElseFixedDefault.toString());

        return systemDefaultOrElseFixedDefault;
    }

    private static Locale getSystemDefaultOrElseFixedDefault() {
        return SupportedLocale.from(Locale.getDefault())
                .map(SupportedLocale::getLocale)
                .orElse(SupportedLocale.ENGLISH.getLocale());
    }
}
