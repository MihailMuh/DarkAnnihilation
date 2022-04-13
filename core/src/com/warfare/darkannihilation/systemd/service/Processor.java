package com.warfare.darkannihilation.systemd.service;

import com.warfare.darkannihilation.utils.MultiProcessor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class Processor {
    private static final ExecutorService pool = Executors.newCachedThreadPool();

    private static final ExecutorService looper = Executors.newSingleThreadExecutor();
    private static final ExecutorService looperOnTouch = Executors.newSingleThreadExecutor();

    public static final MultiProcessor multiProcessor = new MultiProcessor();
    public static Thread UIThread;

    public static void post(Runnable runnable) {
        pool.execute(runnable);
    }

    public static void postToLooper(Runnable runnable) {
        looper.execute(runnable);
    }

    public static void postToTouchLooper(Runnable runnable) {
        looperOnTouch.execute(runnable);
    }

    public static void dispose() {
        pool.shutdown();
        looper.shutdown();
        looperOnTouch.shutdown();
    }
}
