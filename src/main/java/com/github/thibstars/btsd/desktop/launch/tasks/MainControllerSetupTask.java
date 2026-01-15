package com.github.thibstars.btsd.desktop.launch.tasks;

import com.github.thibstars.btsd.desktop.i18n.I18NController;
import com.github.thibstars.btsd.desktop.launch.CountDownLatchContext;
import com.github.thibstars.btsd.desktop.main.MainController;
import com.github.thibstars.btsd.desktop.main.MainFrame;

/**
 * @author Thibault Helsmoortel
 */
public class MainControllerSetupTask extends Creator<MainController> implements Runnable, LaunchTask {

    private final CountDownLatchContext countDownLatchContext;

    private final CountDownLatchContext dependentCountDownLatchContext;

    private final ControllersSetupTask controllersSetupTask;

    public MainControllerSetupTask(CountDownLatchContext countDownLatchContext,
            CountDownLatchContext dependentCountDownLatchContext, ControllersSetupTask controllersSetupTask) {
        this.countDownLatchContext = countDownLatchContext;
        this.dependentCountDownLatchContext = dependentCountDownLatchContext;
        this.controllersSetupTask = controllersSetupTask;
    }

    @Override
    public void run() {
        await(dependentCountDownLatchContext);

        Controllers controllers = controllersSetupTask.getCreatable();
        I18NController i18NController = controllers.i18NController();
        MainFrame mainFrame = createMainFrame();
        i18NController.addListener(mainFrame);

        this.creatable = new MainController(
                mainFrame,
                controllers.aboutController(),
                controllers.stationsController(),
                controllers.reportIssueController(),
                i18NController,
                controllers.preferencesController());

        completeTask(countDownLatchContext);
    }

    protected MainFrame createMainFrame() {
        return new MainFrame();
    }
}
