package com.github.thibstars.btsd.desktop.i18n;

import com.github.thibstars.btsd.desktop.listeners.LocaleChangeListener;
import com.github.thibstars.btsd.internal.I18NService;
import com.github.thibstars.btsd.internal.SupportedLocale;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * @author Thibault Helsmoortel
 */
public class I18NController {

    private final Set<LocaleChangeListener> localeChangeListeners;

    private final LocaleDialog localeDialog;

    private final I18NService i18NService;

    public I18NController(I18NService i18NService) {
        this.i18NService = i18NService;
        this.localeChangeListeners = new HashSet<>();
        this.localeDialog = new LocaleDialog(this);
        addListener(localeDialog);

        localeDialog.setSelectedLocale(SupportedLocale.from(i18NService.getPreferredLocale()).orElseThrow());
    }

    public void changeLocale(Locale locale) {
        i18NService.changeLocale(locale);

        this.localeChangeListeners.forEach(listener -> listener.localeChanged(locale, this));
    }

    public void addListener(LocaleChangeListener localeChangeListener) {
        this.localeChangeListeners.add(localeChangeListener);
    }

    public void showView() {
        this.localeDialog.setVisible(true);
    }

    public String getMessage(String key) {
        return i18NService.getMessage(key);
    }

    public void initLocale() {
        changeLocale(i18NService.getPreferredLocale());
    }
}
