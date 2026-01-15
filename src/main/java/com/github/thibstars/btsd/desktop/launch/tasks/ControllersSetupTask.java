package com.github.thibstars.btsd.desktop.launch.tasks;

import com.github.thibstars.btsd.desktop.about.AboutController;
import com.github.thibstars.btsd.desktop.about.AboutDialog;
import com.github.thibstars.btsd.desktop.i18n.I18NController;
import com.github.thibstars.btsd.desktop.issue.ReportIssueController;
import com.github.thibstars.btsd.desktop.issue.ReportIssueDialog;
import com.github.thibstars.btsd.desktop.launch.CountDownLatchContext;
import com.github.thibstars.btsd.desktop.liveboard.LiveBoardController;
import com.github.thibstars.btsd.desktop.preferences.PreferencesController;
import com.github.thibstars.btsd.desktop.preferences.PreferencesDialog;
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
        
        AboutDialog aboutDialog = createAboutDialog();
        i18NController.addListener(aboutDialog);
        StationsTable stationsTable = createStationsTable();
        i18NController.addListener(stationsTable);
        ReportIssueDialog reportIssueDialog = createReportIssueDialog();
        i18NController.addListener(reportIssueDialog);

        PreferencesDialog preferencesDialog = createPreferencesDialog();
        PreferencesController preferencesController = new PreferencesController(services.preferencesService(), i18NController, preferencesDialog);
        this.creatable = new Controllers(
          new AboutController(propertiesService, aboutDialog),
                liveBoardController,
          new StationsController(stationsTable, services.stationService(), liveBoardController, i18NController),
                new ReportIssueController(propertiesService, reportIssueDialog),
                i18NController,
                preferencesController
        );

        i18NController.initLocale();

        completeTask(countDownLatchContext);
    }

    protected AboutDialog createAboutDialog() {
        return new AboutDialog();
    }

    protected StationsTable createStationsTable() {
        return new StationsTable();
    }

    protected ReportIssueDialog createReportIssueDialog() {
        return new ReportIssueDialog();
    }

    protected PreferencesDialog createPreferencesDialog() {
        return null;
    }
}
