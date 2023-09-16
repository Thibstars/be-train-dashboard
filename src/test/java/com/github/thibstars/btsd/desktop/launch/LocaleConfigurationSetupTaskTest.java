package com.github.thibstars.btsd.desktop.launch;

import com.github.thibstars.btsd.desktop.launch.tasks.ServicesSetupTask;
import com.github.thibstars.btsd.internal.I18NService;
import com.github.thibstars.btsd.internal.SupportedLocale;
import java.util.Locale;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author Thibault Helsmoortel
 */
@ExtendWith(MockitoExtension.class)
class LocaleConfigurationSetupTaskTest {

    private LocaleConfigurationSetupTask localeConfigurationSetupTask;

    @Mock
    private CountDownLatchContext countDownLatchContext;

    @Mock
    private CountDownLatchContext dependentCountDownLatchContext;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private ServicesSetupTask servicesSetupTask;

    @BeforeEach
    void setUp() {
        this.localeConfigurationSetupTask = new LocaleConfigurationSetupTask(
                countDownLatchContext,
                dependentCountDownLatchContext,
                servicesSetupTask
        );
    }

    @Test
    void shouldRun() throws InterruptedException {
        I18NService i18NService = Mockito.mock(I18NService.class);
        Mockito.when(i18NService.getPreferredLocale()).thenReturn(SupportedLocale.ENGLISH.getLocale());
        Mockito.when(servicesSetupTask.getCreatable().i18NService()).thenReturn(i18NService);

        Thread thread = new Thread(localeConfigurationSetupTask);
        thread.start();
        thread.join();

        Locale result = localeConfigurationSetupTask.getCreatable();

        Assertions.assertNotNull(result, "Result must not be null.");
        Assertions.assertEquals(SupportedLocale.ENGLISH.getLocale(), result, "Result must match the expected.");

        Mockito.verify(dependentCountDownLatchContext).await();
        Mockito.verify(countDownLatchContext).countDown();
    }
}