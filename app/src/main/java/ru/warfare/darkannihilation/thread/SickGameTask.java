package ru.warfare.darkannihilation.thread;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class SickGameTask extends GameTask {
    public SickGameTask(Runnable runnable, int delayMillis) {
        super(runnable, delayMillis);
    }

    @Override
    public void resume() {
        future = scheduleAtFixedRate(runnable, 0, delay, MILLISECONDS);
    }
}
