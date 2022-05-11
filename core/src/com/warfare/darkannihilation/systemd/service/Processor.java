package com.warfare.darkannihilation.systemd.service;

import com.warfare.darkannihilation.utils.MultiProcessor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class Processor {
    private static final ExecutorService pool = Executors.newCachedThreadPool();

    private static final ExecutorService looper = Executors.newSingleThreadExecutor();
    private static final ExecutorService looperSounds = Executors.newSingleThreadExecutor();

    public static final MultiProcessor multiProcessor = new MultiProcessor();
    public static Thread UIThread;

    public static void post(Runnable runnable) {
        pool.execute(runnable);
    }

    public static void postToLooper(Runnable runnable) {
        looper.execute(runnable);
    }

    public static void postToLooperSounds(Runnable runnable) {
        looperSounds.execute(runnable);
    }

    public static boolean isUIThread() {
        return Thread.currentThread() == UIThread;
    }

    public static void dispose() {
        pool.shutdown();
        looper.shutdown();
        looperSounds.shutdown();
    }
}
