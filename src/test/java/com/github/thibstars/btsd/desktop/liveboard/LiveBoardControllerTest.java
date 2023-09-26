package com.github.thibstars.btsd.desktop.liveboard;

import com.github.thibstars.btsd.desktop.i18n.I18NController;
import com.github.thibstars.btsd.irail.client.LiveBoardService;
import java.awt.Dimension;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author Thibault Helsmoortel
 */
@ExtendWith(MockitoExtension.class)
class LiveBoardControllerTest {

    @Mock
    private LiveBoardService liveBoardService;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private I18NController i18NController;

    @InjectMocks
    private LiveBoardController liveBoardController;

    @Test
    void shouldShowLiveBoardForStation() {
        String stationId = "myAwesomeStation";
        String language = "en";
        Mockito.when(i18NController.getPreferredLocale().getLanguage()).thenReturn(language);

        liveBoardController.showLiveBoardForStation(null, stationId, new Dimension());

        Mockito.verify(liveBoardService).getForStation(stationId, language);
    }

    @Test
    void shouldGetMessage() {
        String key = "key";

        liveBoardController.getMessage(key);

        Mockito.verify(i18NController).getMessage(key);
    }

    @Test
    void shouldFormatDateTime() {
        LocalDateTime now = LocalDateTime.now();

        String dateTimeFormat = "dd/MM/yyyy HH:mm:ss";
        Mockito.when(i18NController.getPreferredDateTimeFormat()).thenReturn(dateTimeFormat);
        Locale locale = Locale.ENGLISH;
        Mockito.when(i18NController.getPreferredLocale()).thenReturn(locale);

        String expected = now.format(DateTimeFormatter.ofPattern(dateTimeFormat, locale));

        String result = liveBoardController.formatDateTime(now);

        Assertions.assertNotNull(result, "Result must not be null.");
        Assertions.assertEquals(expected, result, "Result must match the expected.");
    }
}