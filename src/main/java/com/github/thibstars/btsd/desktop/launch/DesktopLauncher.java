package com.github.thibstars.btsd.desktop.launch;

import com.github.thibstars.btsd.desktop.launch.tasks.ControllersSetupTask;
import com.github.thibstars.btsd.desktop.launch.tasks.IconSetupTask;
import com.github.thibstars.btsd.desktop.launch.tasks.MainControllerSetupTask;
import com.github.thibstars.btsd.desktop.launch.tasks.PrerequisitesSetupTask;
import com.github.thibstars.btsd.desktop.launch.tasks.ServicesSetupTask;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.SwingUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Thibault Helsmoortel
 */
public class DesktopLauncher {

    private static final Logger LOGGER = LoggerFactory.getLogger(DesktopLauncher.class);

    public void launch() throws InterruptedException {
        LOGGER.info("Preparing application.");

        var preRequisitesCountdownLatchContext = new CountDownLatchContext(2, "Prerequisites");
        var servicesCountdownLatchContext = new CountDownLatchContext(1, "Services");
        var configurationCountdownLatchContext = new CountDownLatchContext(1, "Configuration");
        var controllerCountDownLatchContext = new CountDownLatchContext(1, "Controllers");
        var launchCountDownLatchContext = new CountDownLatchContext(1, "Launch");

        List<CountDownLatchContext> latchContexts = List.of(
                preRequisitesCountdownLatchContext,
                servicesCountdownLatchContext,
                configurationCountdownLatchContext,
                controllerCountDownLatchContext,
                launchCountDownLatchContext
        );

        Integer taskCount = latchContexts.stream().map(CountDownLatchContext::originalCount).reduce(0, Integer::sum);
        LOGGER.info("Registered {} launch tasks.", taskCount);

        LaunchFrame launchFrame = new LaunchFrame(taskCount);

        Runnable statusUpdater = () -> {
            Long tasksLeft = latchContexts.stream().map(CountDownLatchContext::getCount).reduce(0L, Long::sum);
            String progressName = latchContexts.stream()
                    .filter(latchContext -> latchContext.getCount() > 0)
                    .findFirst()
                    .map(CountDownLatchContext::name)
                    .orElse(null);

            SwingUtilities.invokeLater(() -> {
                launchFrame.updateProgress(taskCount - tasksLeft.intValue());
                launchFrame.updateProgressName(progressName);
                if (tasksLeft == 0) {
                    launchFrame.setVisible(false);
                }
            });
        };

        SwingUtilities.invokeLater(() -> launchFrame.setVisible(true));

        var prerequisitesSetupTask = new PrerequisitesSetupTask(preRequisitesCountdownLatchContext);
        var iconSetupTask = new IconSetupTask(preRequisitesCountdownLatchContext, launchFrame);
        var servicesSetupTask = new ServicesSetupTask(servicesCountdownLatchContext, preRequisitesCountdownLatchContext, prerequisitesSetupTask);
        var localeSetupTask = new LocaleConfigurationSetupTask(configurationCountdownLatchContext, servicesCountdownLatchContext, servicesSetupTask);
        var controllersSetupTask = new ControllersSetupTask(controllerCountDownLatchContext, servicesCountdownLatchContext, servicesSetupTask);
        var mainControllerSetupTask = new MainControllerSetupTask(launchCountDownLatchContext, controllerCountDownLatchContext, controllersSetupTask);
        List<Runnable> tasks = List.of(
                prerequisitesSetupTask,
                iconSetupTask,
                servicesSetupTask,
                localeSetupTask,
                controllersSetupTask,
                mainControllerSetupTask,
                () -> launchApplication(launchCountDownLatchContext, mainControllerSetupTask)
        );

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() - 1);
        tasks.forEach(executorService::submit);

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(
                statusUpdater,
                0,
                10,
                TimeUnit.MILLISECONDS
        );

        shutDownLaunchProcess(executorService, scheduledExecutorService, statusUpdater);
    }

    private void launchApplication(CountDownLatchContext launchCountDownLatchContext, MainControllerSetupTask mainControllerSetupTask) {
        launchCountDownLatchContext.await();

        LOGGER.info("Launching application.");
        mainControllerSetupTask.getCreatable().showView();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored") // Can't do anything with the result
    private static void shutDownLaunchProcess(ExecutorService executorService, ScheduledExecutorService scheduledExecutorService,
            Runnable statusUpdater)
            throws InterruptedException {
        statusUpdater.run();
        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.SECONDS);
        scheduledExecutorService.shutdown();
    }

}
