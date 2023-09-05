package com.github.thibstars.btsd.desktop.launch.tasks;

import com.github.thibstars.btsd.desktop.about.AboutController;
import com.github.thibstars.btsd.desktop.about.AboutDialog;
import com.github.thibstars.btsd.desktop.launch.Controllers;
import com.github.thibstars.btsd.desktop.launch.CountDownLatchContext;
import com.github.thibstars.btsd.desktop.liveboard.LiveBoardController;
import com.github.thibstars.btsd.desktop.stations.StationsController;
import com.github.thibstars.btsd.desktop.stations.StationsTable;

/**
 * @author Thibault Helsmoortel
 */
public class ControllersSetupTask extends Creator<Controllers> implements Runnable, LaunchTask {

    private final CountDownLatchContext countDownLatchContext;

    private final CountDownLatchContext dependentCountDownLatchContext;

    private final ServicesSetupTask servicesSetupTask;

    public ControllersSetupTask(CountDownLatchContext countDownLatchContext,
            CountDownLatchContext dependentCountDownLatchContext, ServicesSetupTask servicesSetupTask) {
        this.countDownLatchContext = countDownLatchContext;
        this.dependentCountDownLatchContext = dependentCountDownLatchContext;
        this.servicesSetupTask = servicesSetupTask;
    }

    @Override
    public void run() {
        await(dependentCountDownLatchContext);

        Services services = servicesSetupTask.getCreatable();

        LiveBoardController liveBoardController = new LiveBoardController(services.liveBoardService());
        this.creatable = new Controllers(
          new AboutController(services.propertiesService(), new AboutDialog()),
                liveBoardController,
          new StationsController(new StationsTable(), services.stationService(), liveBoardController)
        );

        completeTask(countDownLatchContext);
    }
}
