package com.github.thibstars.btsd.desktop.launch.tasks;

import com.github.thibstars.btsd.desktop.launch.CountDownLatchContext;
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
class ServicesSetupTaskTest {

    private ServicesSetupTask servicesSetupTask;

    @Mock
    private CountDownLatchContext countDownLatchContext;

    @Mock
    private CountDownLatchContext dependentCountDownLatchContext;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private PrerequisitesSetupTask prerequisitesSetupTask;

    @BeforeEach
    void setUp() {
        this.servicesSetupTask = new ServicesSetupTask(
                countDownLatchContext,
                dependentCountDownLatchContext,
                prerequisitesSetupTask
                );
    }

    @Test
    void shouldRun() throws InterruptedException {
        Thread thread = new Thread(servicesSetupTask);
        thread.start();
        thread.join();

        Services result = servicesSetupTask.getCreatable();

        Assertions.assertNotNull(result, "Result must not be null.");
        Assertions.assertNotNull(result.propertiesService(), "PropertiesService must not be null.");
        Assertions.assertNotNull(result.liveBoardService(), "LiveBoardService must not be null.");
        Assertions.assertNotNull(result.stationService(), "StationService must not be null.");

        Mockito.verify(prerequisitesSetupTask).getCreatable();

        Mockito.verify(dependentCountDownLatchContext).await();
        Mockito.verify(countDownLatchContext).countDown();
    }
}