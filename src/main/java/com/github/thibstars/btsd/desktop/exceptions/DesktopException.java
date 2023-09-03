package com.github.thibstars.btsd.desktop.exceptions;

/**
 * @author Thibault Helsmoortel
 */
public class DesktopException extends RuntimeException {

    public DesktopException(Exception exception) {
        super(exception);
    }

    public DesktopException(String message) {
        super(message);
    }
}
