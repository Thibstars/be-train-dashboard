package com.github.thibstars.btsd.desktop.launch.tasks;

import com.github.thibstars.btsd.desktop.launch.CountDownLatchContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Thibault Helsmoortel
 */
public interface LaunchTask {

    Logger LOGGER = LoggerFactory.getLogger(LaunchTask.class);

    default void await(CountDownLatchContext countDownLatchContext) {
        countDownLatchContext.await();
    }

    default void completeTask(CountDownLatchContext countDownLatchContext) {
        int originalCount = countDownLatchContext.originalCount();
        LOGGER.info("Completed task {} of {} of type: {}.", originalCount - countDownLatchContext.getCount() + 1, originalCount, countDownLatchContext.name());

        countDownLatchContext.countDown();
    }

}
