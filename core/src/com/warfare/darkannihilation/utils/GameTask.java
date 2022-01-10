package com.warfare.darkannihilation.utils;

import static com.warfare.darkannihilation.systemd.service.Service.printErr;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class GameTask extends ScheduledThreadPoolExecutor {
    protected final Runnable runnable;
    protected final int delay;
    protected ScheduledFuture<?> future;

    public GameTask(Runnable runnable, int delayMillis) {
        super(1);
        this.runnable = runnable;
        delay = delayMillis;
    }

    public void start() {
        resume();
    }

    public void resume() {
        try {
            future = scheduleWithFixedDelay(runnable, 0, delay, MILLISECONDS);
        } catch (Exception e) {
            printErr("Error in GameTask", e);
        }
    }

    public void stop() {
        pause();
    }

    public void pause() {
        if (future != null) {
            future.cancel(false);
        }
    }
}