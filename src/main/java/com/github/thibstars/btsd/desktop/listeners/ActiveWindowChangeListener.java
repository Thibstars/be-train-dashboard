package com.github.thibstars.btsd.desktop.listeners;

import java.awt.Image;
import java.awt.KeyboardFocusManager;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

/**
 * @author Thibault Helsmoortel
 */
public class ActiveWindowChangeListener implements PropertyChangeListener {

    private final BufferedImage image;

    private Window previouslyActiveWindow;

    public ActiveWindowChangeListener(BufferedImage image) {
        this.image = image;
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        final Window activeWindow = KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .getActiveWindow();
        if (activeWindow != null && previouslyActiveWindow != activeWindow) {
            previouslyActiveWindow = activeWindow;
            List<Image> windowIcons = activeWindow.getIconImages();
            if (windowIcons == null || windowIcons.isEmpty()) {
                activeWindow.setIconImage(image);
            }
        }
    }
}
