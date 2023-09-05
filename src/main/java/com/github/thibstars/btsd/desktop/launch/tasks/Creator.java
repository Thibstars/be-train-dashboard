package com.github.thibstars.btsd.desktop.launch.tasks;

/**
 * @author Thibault Helsmoortel
 */
public abstract class Creator<T> {

    protected T creatable;

    public T getCreatable() {
        return creatable;
    }

}
