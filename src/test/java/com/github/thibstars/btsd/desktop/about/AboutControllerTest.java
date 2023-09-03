package com.github.thibstars.btsd.desktop.about;

import com.github.thibstars.btsd.internal.PropertiesService;
import java.util.Optional;
import java.util.Properties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author Thibault Helsmoortel
 */
@ExtendWith(MockitoExtension.class)
class AboutControllerTest {

    @Mock
    private PropertiesService propertiesService;

    @Mock
    private AboutDialog aboutDialog;

    private AboutController aboutController;

    @BeforeEach
    void setUp() {
        Properties properties = Mockito.mock(Properties.class);
        Mockito.when(properties.getProperty(ArgumentMatchers.anyString())).thenReturn("https://example.org");
        Mockito.when(propertiesService.getApplicationProperties()).thenReturn(Optional.of(properties));

        this.aboutController = new AboutController(propertiesService, aboutDialog);
    }

    @Test
    void shouldSetAppName() {
        String appName = "The Greatest";
        aboutController.setAppName(appName);

        Mockito.verify(aboutDialog).setAppName(appName);
    }

    @Test
    void shouldShowView() {
        aboutController.showView();

        Mockito.verify(aboutDialog).pack();
        Mockito.verify(aboutDialog).setVisible(true);
    }
}