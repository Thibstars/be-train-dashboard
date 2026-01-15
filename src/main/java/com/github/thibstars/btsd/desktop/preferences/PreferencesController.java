package com.github.thibstars.btsd.desktop.preferences;

import com.github.thibstars.btsd.desktop.i18n.I18NController;
import com.github.thibstars.btsd.internal.PreferencesService;
import com.github.thibstars.btsd.internal.SupportedLocale;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Thibault Helsmoortel
 */
public class PreferencesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PreferencesController.class);

    private final PreferencesDialog preferencesDialog;

    private final PreferencesService preferencesService;

    private final I18NController i18NController;

    public PreferencesController(PreferencesService preferencesService, I18NController i18NController) {
        this(preferencesService, i18NController, null);
    }

    public PreferencesController(PreferencesService preferencesService, I18NController i18NController, PreferencesDialog preferencesDialog) {
        this.preferencesService = preferencesService;
        this.i18NController = i18NController;
        this.preferencesDialog = preferencesDialog != null ? preferencesDialog : new PreferencesDialog(this);
        i18NController.addListener(this.preferencesDialog);

        Locale preferredLocale = i18NController.getPreferredLocale();
        this.preferencesDialog.setSelectedLocale(SupportedLocale.from(preferredLocale).orElseThrow());

        this.preferencesDialog.setPreferredDateTimeFormat(preferencesService.getDateTimeFormatPreference());
    }

    protected void changeLocale(Locale locale) {
        i18NController.changeLocale(locale);
    }

    protected String getMessage(String key) {
        return i18NController.getMessage(key);
    }

    public void showView() {
        this.preferencesDialog.setVisible(true);
        this.preferencesDialog.localeChanged(i18NController.getPreferredLocale(), i18NController);
        this.preferencesDialog.setSelectedLocale(SupportedLocale.from(i18NController.getPreferredLocale()).orElseThrow());
    }

    protected void changeDateTimeFormat(String dateTimeFormat) {
        try {
            LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern(
                            dateTimeFormat, i18NController.getPreferredLocale()
                    )
            );
            preferencesService.putDateTimeFormatPreference(dateTimeFormat);
        } catch (Exception e) {
            LOGGER.warn("Could not set date time format {}, as that will result in parse exceptions.", dateTimeFormat, e);
        }
    }

    protected I18NController getI18NController() {
        return i18NController;
    }
}
