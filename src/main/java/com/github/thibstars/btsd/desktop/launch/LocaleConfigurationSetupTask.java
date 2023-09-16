package com.github.thibstars.btsd.desktop.launch;

import com.github.thibstars.btsd.desktop.launch.tasks.Creator;
import com.github.thibstars.btsd.desktop.launch.tasks.LaunchTask;
import com.github.thibstars.btsd.desktop.launch.tasks.ServicesSetupTask;
import java.util.Locale;

/**
 * @author Thibault Helsmoortel
 */
public class LocaleConfigurationSetupTask extends Creator<Locale> implements Runnable, LaunchTask {

    private final CountDownLatchContext countDownLatchContext;

    private final CountDownLatchContext dependentCountDownLatchContext;

    private final ServicesSetupTask servicesSetupTask;

    public LocaleConfigurationSetupTask(CountDownLatchContext countDownLatchContext,
            CountDownLatchContext dependentCountDownLatchContext, ServicesSetupTask servicesSetupTask) {
        this.countDownLatchContext = countDownLatchContext;
        this.dependentCountDownLatchContext = dependentCountDownLatchContext;
        this.servicesSetupTask = servicesSetupTask;
    }

    @Override
    public void run() {
        await(dependentCountDownLatchContext);

        this.creatable = servicesSetupTask.getCreatable().i18NService().getPreferredLocale();

        completeTask(countDownLatchContext);
    }
}
