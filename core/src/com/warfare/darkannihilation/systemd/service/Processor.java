package com.warfare.darkannihilation.systemd.service;

import com.badlogic.gdx.Gdx;
import com.warfare.darkannihilation.utils.MultiProcessor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class Processor {
    private static final ExecutorService pool = Executors.newCachedThreadPool();
    private static final ExecutorService poolOnTouch = Executors.newSingleThreadExecutor();
    public static final MultiProcessor multiProcessor = new MultiProcessor();
    public static Thread UIThread;

    public static void post(Runnable runnable) {
        pool.execute(runnable);
    }

    public static void postOnTouch(Runnable runnable) {
        poolOnTouch.execute(runnable);
    }

    public static void postToUI(Runnable runnable) {
        if (Thread.currentThread() != UIThread) {
            Gdx.app.postRunnable(runnable);
        } else {
            runnable.run();
        }
    }

    public static void dispose() {
        pool.shutdown();
        poolOnTouch.shutdown();
    }
}
