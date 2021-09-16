package ru.warfare.darkannihilation.thread;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class GameTask extends ScheduledThreadPoolExecutor {
    private final Runnable runnable;
    private final int delay;
    private ScheduledFuture<?> future;
    private boolean dead;

    public GameTask(Runnable runnable, int delayMillis) {
        super(1);
        this.runnable = runnable;
        delay = delayMillis;
    }

    public void necromancy() {
        dead = false;
    }

    public void kill() {
        stop();
        dead = true;
    }

    public void start() {
        if (!dead) {
            HardThread.tasks.add(this);
            resume();
        }
    }

    public void resume() {
        if (!dead) {
            future = scheduleWithFixedDelay(runnable, 0, delay, MILLISECONDS);
        }
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
