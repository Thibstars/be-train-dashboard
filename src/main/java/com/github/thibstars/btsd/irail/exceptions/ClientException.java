package com.github.thibstars.btsd.irail.exceptions;

/**
 * @author Thibault Helsmoortel
 */
public class ClientException extends RuntimeException {

    public ClientException(Exception exception) {
        super(exception);
    }
}
