package com.github.thibstars.btsd.desktop.launch.tasks;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.thibstars.btsd.desktop.launch.CountDownLatchContext;
import com.github.thibstars.btsd.internal.PropertiesServiceImpl;
import com.github.thibstars.btsd.irail.client.LiveBoardServiceImpl;
import com.github.thibstars.btsd.irail.client.StationServiceImpl;
import okhttp3.OkHttpClient;

/**
 * @author Thibault Helsmoortel
 */
public class ServicesSetupTask extends Creator<Services> implements Runnable, LaunchTask {

    private final CountDownLatchContext countDownLatchContext;

    private final CountDownLatchContext dependentCountDownLatchContext;

    private final PrerequisitesSetupTask prerequisitesSetupTask;

    public ServicesSetupTask(CountDownLatchContext countDownLatchContext,
            CountDownLatchContext dependentCountDownLatchContext, PrerequisitesSetupTask prerequisitesSetupTask) {
        this.countDownLatchContext = countDownLatchContext;
        this.dependentCountDownLatchContext = dependentCountDownLatchContext;
        this.prerequisitesSetupTask = prerequisitesSetupTask;
    }

    @Override
    public void run() {
        await(dependentCountDownLatchContext);

        Prerequisites prerequisites = prerequisitesSetupTask.getCreatable();
        OkHttpClient okHttpClient = prerequisites.okHttpClient();
        ObjectMapper objectMapper = prerequisites.objectMapper();

        this.creatable = new Services(
                new PropertiesServiceImpl(),
                new LiveBoardServiceImpl(okHttpClient, objectMapper),
                new StationServiceImpl(okHttpClient, objectMapper)
        );

        completeTask(countDownLatchContext);
    }
}