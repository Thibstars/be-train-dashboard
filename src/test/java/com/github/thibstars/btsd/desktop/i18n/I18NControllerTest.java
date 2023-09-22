package com.github.thibstars.btsd.desktop.i18n;

import com.github.thibstars.btsd.desktop.listeners.LocaleChangeListener;
import com.github.thibstars.btsd.internal.I18NService;
import com.github.thibstars.btsd.internal.SupportedLocale;
import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
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
class I18NControllerTest {

    @Mock
    private I18NService i18NService;

    private I18NController i18NController;

    @BeforeEach
    void setUp() {
        this.i18NController = new I18NController(i18NService);
    }

    @Test
    void shouldChangeLocale() {
        Locale locale = SupportedLocale.DUTCH.getLocale();

        i18NController.changeLocale(locale);

        Mockito.verify(i18NService).changeLocale(locale);
    }

    @SuppressWarnings("unchecked")
    @Test
    void shouldAddListener() throws NoSuchFieldException, IllegalAccessException {
        LocaleChangeListener listener = Mockito.mock(LocaleChangeListener.class);

        i18NController.addListener(listener);

        Field listenersField = I18NController.class.getDeclaredField("localeChangeListeners");
        listenersField.setAccessible(true);
        Set<LocaleChangeListener> listeners = (Set<LocaleChangeListener>) listenersField.get(i18NController);

        Assertions.assertFalse(listeners.isEmpty(), "Listeners must not be empty.");
    }

    @Test
    void shouldGetMessage() {
        String key = "key";
        String message = "message";

        Mockito.when(i18NService.getMessage(key)).thenReturn(message);

        String result = i18NController.getMessage(key);

        Assertions.assertNotNull(result, "Message must not be null.");
        Assertions.assertFalse(result.isBlank(), "Message must not be blank.");
        Assertions.assertEquals(message, result, "Message must match the expected.");
    }

    @Test
    void shouldInitLocale() {
        Locale preferredLocale = SupportedLocale.DUTCH.getLocale();
        Mockito.when(i18NService.getPreferredLocale()).thenReturn(preferredLocale);
        I18NController spy = Mockito.spy(i18NController);

        spy.initLocale();

        Mockito.verify(spy).changeLocale(preferredLocale);
    }

    @Test
    void shouldPreferredDateTimeFormat() {
        String dateTimeFormat = "greatestFormatEver";
        Mockito.when(i18NService.getPreferredDateTimeFormat()).thenReturn(dateTimeFormat);

        String result = i18NController.getPreferredDateTimeFormat();

        Assertions.assertNotNull(result, "Result must not be null.");
        Assertions.assertEquals(dateTimeFormat, result, "Result must match the expected.");
    }
}