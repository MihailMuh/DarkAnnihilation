package ru.warfare.darkannihilation.thread;

public class PROGameTask extends GameTask {
    private volatile boolean dead = false;

    public PROGameTask(Runnable runnable, int delayMillis) {
        super(runnable, delayMillis);
    }

    public void necromancy() {
        dead = false;
    }

    public void kill() {
        stop();
        dead = true;
    }

    @Override
    public void start() {
        if (!dead) {
            super.start();
        }
    }

    @Override
    public void resume() {
        if (!dead) {
            super.resume();
        }
    }
}
