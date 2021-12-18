package com.warfare.darkannihilation.systemd.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class Processor {
    private static final ExecutorService pool = Executors.newWorkStealingPool();
    private static final ExecutorService poolOnTouch = Executors.newCachedThreadPool();

    public static void post(Runnable runnable) {
        pool.execute(runnable);
    }

    public static void postOnTouch(Runnable runnable) {
        poolOnTouch.execute(runnable);
    }

    public static void dispose() {
        pool.shutdown();
        poolOnTouch.shutdown();
    }
}
