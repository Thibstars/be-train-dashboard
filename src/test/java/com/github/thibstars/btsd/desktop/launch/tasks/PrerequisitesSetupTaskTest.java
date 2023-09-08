package com.github.thibstars.btsd.desktop.launch.tasks;

import com.github.thibstars.btsd.desktop.launch.CountDownLatchContext;
import org.junit.jupiter.api.Assertions;
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
class PrerequisitesSetupTaskTest {

    @InjectMocks
    private PrerequisitesSetupTask prerequisitesSetupTask;

    @Mock
    private CountDownLatchContext countDownLatchContext;

    @Test
    void shouldRun() throws InterruptedException {
        Thread thread = new Thread(prerequisitesSetupTask);
        thread.start();
        thread.join();

        Prerequisites result = prerequisitesSetupTask.getCreatable();

        Assertions.assertNotNull(result, "Result must not be null.");
        Assertions.assertNotNull(result.okHttpClient(), "OkHttpClient must not be null.");
        Assertions.assertNotNull(result.objectMapper(), "ObjectMapper must not be null.");

        Mockito.verify(countDownLatchContext).countDown();
    }
}