package com.github.thibstars.btsd.desktop.launch.tasks;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.thibstars.btsd.desktop.launch.CountDownLatchContext;
import okhttp3.OkHttpClient;

/**
 * @author Thibault Helsmoortel
 */
public class PrerequisitesSetupTask extends Creator<Prerequisites> implements Runnable, LaunchTask {

    private final CountDownLatchContext countDownLatchContext;

    public PrerequisitesSetupTask(CountDownLatchContext countDownLatchContext) {
        this.countDownLatchContext = countDownLatchContext;
    }

    @Override
    public void run() {
        this.creatable = new Prerequisites(new OkHttpClient(), new ObjectMapper());

        completeTask(countDownLatchContext);
    }
}
