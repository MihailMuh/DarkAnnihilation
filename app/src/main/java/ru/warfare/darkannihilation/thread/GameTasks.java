package ru.warfare.darkannihilation.thread;

public class GameTasks {
    private final Runnable runnable;
    private final int delay;

    public GameTasks(Runnable runnable, int delayMillis) {
        this.runnable = runnable;
        delay = delayMillis;
    }

    public void start() {
        HardThread.startSchedule(runnable, delay);
    }
}
