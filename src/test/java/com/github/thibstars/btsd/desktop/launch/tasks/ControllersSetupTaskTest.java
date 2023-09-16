package com.github.thibstars.btsd.desktop.launch.tasks;

import com.github.thibstars.btsd.desktop.launch.CountDownLatchContext;
import com.github.thibstars.btsd.internal.I18NService;
import com.github.thibstars.btsd.internal.PropertiesService;
import com.github.thibstars.btsd.internal.SupportedLocale;
import java.util.Optional;
import java.util.Properties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author Thibault Helsmoortel
 */
@ExtendWith(MockitoExtension.class)
class ControllersSetupTaskTest {

    private ControllersSetupTask controllersSetupTask;

    @Mock
    private CountDownLatchContext countDownLatchContext;

    @Mock
    private CountDownLatchContext dependentCountDownLatchContext;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private ServicesSetupTask servicesSetupTask;

    @BeforeEach
    void setUp() {
        this.controllersSetupTask = new ControllersSetupTask(
                countDownLatchContext,
                dependentCountDownLatchContext,
                servicesSetupTask
        );
    }

    @Test
    void shouldRun() throws InterruptedException {
        PropertiesService propertiesService = Mockito.mock(PropertiesService.class);
        Properties properties = Mockito.mock(Properties.class);
        Mockito.when(properties.isEmpty()).thenReturn(true);
        Mockito.when(propertiesService.getApplicationProperties()).thenReturn(Optional.of(properties));
        Mockito.when(servicesSetupTask.getCreatable().propertiesService()).thenReturn(propertiesService);
        I18NService i18NService = Mockito.mock(I18NService.class);
        Mockito.when(i18NService.getPreferredLocale()).thenReturn(SupportedLocale.ENGLISH.getLocale());
        Mockito.when(i18NService.getMessage(ArgumentMatchers.anyString()))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
        Mockito.when(servicesSetupTask.getCreatable().i18NService()).thenReturn(i18NService);

        Thread thread = new Thread(controllersSetupTask);
        thread.start();
        thread.join();

        Controllers result = controllersSetupTask.getCreatable();

        Assertions.assertNotNull(result, "Result must not be null.");
        Assertions.assertNotNull(result.aboutController(), "AboutController must not be null.");
        Assertions.assertNotNull(result.liveBoardController(), "LiveBoardController must not be null.");
        Assertions.assertNotNull(result.stationsController(), "StationsController must not be null.");
        Assertions.assertNotNull(result.reportIssueController(), "ReportIssueController must not be null.");
        Assertions.assertNotNull(result.i18NController(), "I18NController must not be null.");

        Mockito.verify(dependentCountDownLatchContext).await();
        Mockito.verify(countDownLatchContext).countDown();
    }
}