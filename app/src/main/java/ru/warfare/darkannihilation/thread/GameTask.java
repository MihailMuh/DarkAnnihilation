package ru.warfare.darkannihilation.thread;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class GameTask extends ScheduledThreadPoolExecutor {
    private final Runnable runnable;
    private final int delay;
    private ScheduledFuture<?> future;

    public GameTask(Runnable runnable, int delayMillis) {
        super(1);
        this.runnable = runnable;
        delay = delayMillis;
    }

    public void start() {
        HardThread.tasks.add(this);
        resume();
    }

    public void resume() {
        future = scheduleWithFixedDelay(runnable, 0, delay, MILLISECONDS);
    }

    public void stop() {
        HardThread.tasks.remove(this);
        pause();
    }

    public void pause() {
        if (future != null) {
            if (!future.isCancelled()) {
                future.cancel(false);
            }
        }
    }
}
