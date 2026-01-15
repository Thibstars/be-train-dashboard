package com.github.thibstars.btsd.desktop.preferences;

import com.github.thibstars.btsd.desktop.i18n.I18NController;
import com.github.thibstars.btsd.internal.PreferencesService;
import com.github.thibstars.btsd.internal.SupportedLocale;
import java.util.Locale;
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
class PreferencesControllerTest {

    @Mock
    private PreferencesService preferencesService;

    @Mock
    private I18NController i18NController;
    
    @Mock
    private PreferencesDialog preferencesDialog;

    private PreferencesController preferencesController;

    @BeforeEach
    void setUp() {
        Locale preferredLocale = SupportedLocale.DUTCH.getLocale();
        Mockito.when(i18NController.getPreferredLocale()).thenReturn(preferredLocale);

        this.preferencesController = new PreferencesController(preferencesService, i18NController, preferencesDialog);
    }

    @Test
    void shouldChangeLocale() {
        Locale locale = Locale.GERMAN;

        preferencesController.changeLocale(locale);

        Mockito.verify(i18NController).changeLocale(locale);
    }

    @Test
    void shouldGetMessage() {
        String message = "message";

        preferencesController.getMessage(message);

        Mockito.verify(i18NController).getMessage(message);
    }

    @Test
    void shouldChangeDateTimeFormat() {
        String dateTimeFormat = "dd/MM/yyyy HH:mm:ss";
        Locale locale = Locale.ENGLISH;
        Mockito.when(i18NController.getPreferredLocale()).thenReturn(locale);

        preferencesController.changeDateTimeFormat(dateTimeFormat);

        Mockito.verify(preferencesService).putDateTimeFormatPreference(dateTimeFormat);
    }

    @Test
    void shouldNotChangeDateTimeFormatWhenFormatResultsInDateTimeException() {
        String dateTimeFormat = "woohoo";
        Locale locale = Locale.ENGLISH;
        Mockito.when(i18NController.getPreferredLocale()).thenReturn(locale);

        preferencesController.changeDateTimeFormat(dateTimeFormat);

       Mockito.verify(preferencesService, Mockito.never()).putDateTimeFormatPreference(dateTimeFormat);
    }
}