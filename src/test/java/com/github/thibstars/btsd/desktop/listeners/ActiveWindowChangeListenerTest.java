package com.github.thibstars.btsd.desktop.listeners;

import java.awt.KeyboardFocusManager;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * @author Thibault Helsmoortel
 */
class ActiveWindowChangeListenerTest {

    private BufferedImage image;

    private ActiveWindowChangeListener activeWindowChangeListener;

    @BeforeEach
    void setUp() {
        this.image = Mockito.mock(BufferedImage.class);
        this.activeWindowChangeListener = new ActiveWindowChangeListener(image);
    }

    @Test
    void shouldSetIconImageOnNewWindowWithoutImages() throws NoSuchFieldException, IllegalAccessException {
        Field previousWindowField = ActiveWindowChangeListener.class.getDeclaredField("previouslyActiveWindow");
        previousWindowField.setAccessible(true);
        Window previouslyActiveWindow = Mockito.mock(Window.class);
        previousWindowField.set(activeWindowChangeListener, previouslyActiveWindow);

        try(var keyboardFocusManagerMockedStatic = Mockito.mockStatic(KeyboardFocusManager.class)) {
            KeyboardFocusManager keyboardFocusManager = Mockito.mock(KeyboardFocusManager.class);
            keyboardFocusManagerMockedStatic.when(KeyboardFocusManager::getCurrentKeyboardFocusManager)
                            .thenReturn(keyboardFocusManager);
            Window activeWindow = Mockito.mock(Window.class);
            Mockito.when(activeWindow.getIconImages()).thenReturn(Collections.emptyList());
            Mockito.when(keyboardFocusManager.getActiveWindow()).thenReturn(activeWindow);

            activeWindowChangeListener.propertyChange(null);

            Mockito.verify(activeWindow).setIconImage(image);
        }
    }
}