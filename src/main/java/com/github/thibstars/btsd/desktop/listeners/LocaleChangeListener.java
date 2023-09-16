package com.github.thibstars.btsd.desktop.listeners;

import com.github.thibstars.btsd.desktop.i18n.I18NController;
import java.util.Locale;

/**
 * @author Thibault Helsmoortel
 */
@FunctionalInterface
public interface LocaleChangeListener {

    void localeChanged(Locale locale, I18NController i18NController);
}
