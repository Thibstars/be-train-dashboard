package com.github.thibstars.btsd.internal;

import java.util.Locale;

/**
 * @author Thibault Helsmoortel
 */
public interface I18NService {

    Locale getPreferredLocale();

    void changeLocale(Locale locale);

    String getMessage(String key);

    String getMessage(String key, Locale locale);

    String getPreferredDateTimeFormat();
}
