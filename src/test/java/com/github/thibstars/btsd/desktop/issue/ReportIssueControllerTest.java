package com.github.thibstars.btsd.desktop.issue;

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
class ReportIssueControllerTest {

    @Mock
    private PropertiesService propertiesService;

    @Mock
    private ReportIssueDialog reportIssueDialog;

    private ReportIssueController reportIssueController;

    @BeforeEach
    void setUp() {
        Properties properties = Mockito.mock(Properties.class);
        Mockito.when(properties.getProperty(ArgumentMatchers.anyString())).thenReturn("https://example.org");
        Mockito.when(propertiesService.getApplicationProperties()).thenReturn(Optional.of(properties));

        this.reportIssueController = new ReportIssueController(propertiesService, reportIssueDialog);
    }

    @Test
    void shouldShowView() {
        reportIssueController.showView();

        Mockito.verify(reportIssueDialog).pack();
        Mockito.verify(reportIssueDialog).setVisible(true);
    }
}