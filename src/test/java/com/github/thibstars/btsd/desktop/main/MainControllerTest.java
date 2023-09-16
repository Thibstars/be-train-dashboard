package com.github.thibstars.btsd.desktop.main;

import com.github.thibstars.btsd.desktop.about.AboutController;
import com.github.thibstars.btsd.desktop.i18n.I18NController;
import com.github.thibstars.btsd.desktop.issue.ReportIssueController;
import com.github.thibstars.btsd.desktop.listeners.LocaleChangeListener;
import com.github.thibstars.btsd.desktop.stations.StationsController;
import java.awt.Dimension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author Thibault Helsmoortel
 */
@ExtendWith(MockitoExtension.class)
class MainControllerTest {

    @Mock
    private MainFrame mainFrame;

    @Mock
    private AboutController aboutController;

    @Mock
    private StationsController stationsController;

    @Mock
    private ReportIssueController reportIssueController;

    @Mock
    private I18NController i18NController;

    @InjectMocks
    private MainController mainController;

    @Test
    void shouldShowView() {
        mainController.showView();

        Mockito.verify(mainFrame).setVisible(true);
    }

    @Test
    void shouldSetAppName() {
        String appName = "App Titude";

        mainController.setAppName(appName);

        Mockito.verify(aboutController).setAppName(appName, i18NController);
        Mockito.verifyNoInteractions(i18NController);
    }

    @Test
    void shouldShowAboutView() {
        mainController.showAboutView();

        Mockito.verify(aboutController).showView();
    }

    @Test
    void shouldInitStationsTable() {
        Dimension dimension = new Dimension();

        mainController.initStationsTable(dimension);

        Mockito.verify(stationsController).initStationsTable(dimension);
    }

    @Test
    void shouldGetStationsTableScrollPane() {
        mainController.getStationsTableScrollPane();

        Mockito.verify(stationsController).getStationsTableScrollPane();
    }

    @Test
    void shouldFilterStationsTableByName() {
        String name = "Myself";

        mainController.filterStationsTableByName(name);

        Mockito.verify(stationsController).filterStationsTableByName(name);
    }

    @Test
    void shouldShowReportIssueView() {
        mainController.showReportIssueView();

        Mockito.verify(reportIssueController).showView();
    }

    @Test
    void shouldAddLocaleChangeListener() {
        LocaleChangeListener listener = Mockito.mock(LocaleChangeListener.class);

        mainController.addLocaleChangeListener(listener);

        Mockito.verify(i18NController).addListener(listener);
    }

    @Test
    void shouldGetMessage() {
        String key = "key";

        mainController.getMessage(key);

        Mockito.verify(i18NController).getMessage(key);
    }

    @Test
    void shouldShowLocaleView() {
        mainController.showLocaleView();

        Mockito.verify(i18NController).showView();
    }
}