package com.github.thibstars.btsd.desktop.launch.tasks;

import com.github.thibstars.btsd.desktop.launch.CountDownLatchContext;
import com.github.thibstars.btsd.desktop.launch.LaunchFrame;
import java.awt.image.BufferedImage;
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
class IconSetupTaskTest {

    @InjectMocks
    private IconSetupTask iconSetupTask;

    @Mock
    private CountDownLatchContext countDownLatchContext;

    @Mock
    private LaunchFrame launchFrame;

    @Test
    void shouldRun() throws InterruptedException {
        Thread thread = new Thread(iconSetupTask);
        thread.start();
        thread.join();

        BufferedImage result = iconSetupTask.getCreatable();

        Assertions.assertNotNull(result, "Result must not be null.");

        Mockito.verify(launchFrame).setIconImage(result);
        Mockito.verify(countDownLatchContext).countDown();
    }
}