package com.github.thibstars.btsd.desktop.launch.tasks;

import com.github.thibstars.btsd.desktop.about.AboutController;
import com.github.thibstars.btsd.desktop.about.AboutDialog;
import com.github.thibstars.btsd.desktop.i18n.I18NController;
import com.github.thibstars.btsd.desktop.issue.ReportIssueController;
import com.github.thibstars.btsd.desktop.issue.ReportIssueDialog;
import com.github.thibstars.btsd.desktop.launch.CountDownLatchContext;
import com.github.thibstars.btsd.desktop.liveboard.LiveBoardController;
import com.github.thibstars.btsd.desktop.stations.StationsController;
import com.github.thibstars.btsd.desktop.stations.StationsTable;
import com.github.thibstars.btsd.internal.PropertiesService;

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

        I18NController i18NController = new I18NController(services.i18NService());

        LiveBoardController liveBoardController = new LiveBoardController(services.liveBoardService(), i18NController);
        PropertiesService propertiesService = services.propertiesService();

        AboutDialog aboutDialog = new AboutDialog();
        i18NController.addListener(aboutDialog);
        StationsTable stationsTable = new StationsTable();
        i18NController.addListener(stationsTable);
        ReportIssueDialog reportIssueDialog = new ReportIssueDialog();
        i18NController.addListener(reportIssueDialog);

        this.creatable = new Controllers(
          new AboutController(propertiesService, aboutDialog),
                liveBoardController,
          new StationsController(stationsTable, services.stationService(), liveBoardController, i18NController),
                new ReportIssueController(propertiesService, reportIssueDialog),
                i18NController
        );

        i18NController.initLocale();

        completeTask(countDownLatchContext);
    }
}
