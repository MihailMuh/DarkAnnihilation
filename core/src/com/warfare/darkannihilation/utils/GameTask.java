package com.warfare.darkannihilation.utils;

import static com.warfare.darkannihilation.systemd.service.Service.printErr;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

import com.badlogic.gdx.Gdx;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class GameTask extends ScheduledThreadPoolExecutor {
    protected final Runnable runnable;
    protected final int delay;
    protected ScheduledFuture<?> future;

    public GameTask(Runnable runnable, int delayMillis, boolean inUI) {
        super(1);
        delay = delayMillis;

        if (inUI) this.runnable = () -> Gdx.app.postRunnable(runnable);
        else this.runnable = runnable;
    }

    public void start() {
        try {
            future = scheduleWithFixedDelay(runnable, 0, delay, MILLISECONDS);
        } catch (Exception e) {
            printErr("Error in GameTask", e);
        }
    }

    public void stop() {
        if (future != null) {
            future.cancel(false);
        }
    }
}