package com.warfare.darkannihilation.systemd.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class Processor {
    private static final ExecutorService pool = Executors.newCachedThreadPool();

    public static void post(Runnable runnable) {
        pool.execute(runnable);
    }
}
