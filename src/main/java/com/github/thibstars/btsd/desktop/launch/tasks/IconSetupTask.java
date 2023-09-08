package com.github.thibstars.btsd.desktop.launch.tasks;

import com.github.thibstars.btsd.desktop.launch.CountDownLatchContext;
import com.github.thibstars.btsd.desktop.launch.LaunchFrame;
import com.github.thibstars.btsd.desktop.listeners.ActiveWindowChangeListener;
import java.awt.Image;
import java.awt.KeyboardFocusManager;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import javax.imageio.ImageIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Thibault Helsmoortel
 */
public class IconSetupTask extends Creator<BufferedImage> implements Runnable, LaunchTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(IconSetupTask.class);

    private final CountDownLatchContext countDownLatchContext;

    private final LaunchFrame launchFrame;

    public IconSetupTask(CountDownLatchContext countDownLatchContext, LaunchFrame launchFrame) {
        this.countDownLatchContext = countDownLatchContext;
        this.launchFrame = launchFrame;
    }

    @Override
    public void run() {
        try {
            BufferedImage image = ImageIO.read(
                    Objects.requireNonNull(
                            this.getClass().getClassLoader().getResource("icons/icon-512.png")
                    )
            );
            this.creatable = image;
            this.launchFrame.setIconImage(image);

            var propertyChangeListener = new ActiveWindowChangeListener(image);

            KeyboardFocusManager.getCurrentKeyboardFocusManager()
                    .addPropertyChangeListener("activeWindow", propertyChangeListener); // $NON-NLS-1$
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }

        completeTask(countDownLatchContext);
    }
}
