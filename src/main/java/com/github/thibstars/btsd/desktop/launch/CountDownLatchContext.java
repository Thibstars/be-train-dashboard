package com.github.thibstars.btsd.desktop.launch;

import java.util.concurrent.CountDownLatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Thibault Helsmoortel
 */
public record CountDownLatchContext(
        CountDownLatch countDownLatch,
        int originalCount,
        String name
) {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountDownLatchContext.class);

    public CountDownLatchContext(int amountOfTasks, String prerequisites) {
        this(new CountDownLatch(amountOfTasks), amountOfTasks, prerequisites);
    }

    public long getCount() {
        return countDownLatch.getCount();
    }

    public void countDown() {
        countDownLatch.countDown();
    }

    public void await() {
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
    }

}
