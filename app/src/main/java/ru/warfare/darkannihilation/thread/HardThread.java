package ru.warfare.darkannihilation.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import ru.warfare.darkannihilation.interfaces.Function;

import static ru.warfare.darkannihilation.systemd.service.Py.print;

public class HardThread implements Runnable {
    private static final ExecutorService threadPool = Executors.newCachedThreadPool();
    private static final ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(8);
    private static ScheduledFuture<?> future;
    private Thread thread;
    private static Function function;
    private volatile boolean playing;
    private static volatile boolean work;

    public HardThread() {
        startJob();
    }

    public static void doInBackGround(Function func) {
        if (!work) {
            function = func;
            work = true;
        } else {
            threadPool.execute(func::run);
        }
    }

    public static void doInPool(Runnable runnable) {
        threadPool.execute(runnable);
    }

    public static void stopSchedule() {
        if (future != null) {
            if (!future.isCancelled()) {
                future.cancel(false);
            }
        }
    }

    public static void startSchedule(Runnable runnable, int millis) {
        future = scheduledThreadPool.scheduleWithFixedDelay(runnable, 0, millis, TimeUnit.MILLISECONDS);
    }

    public void stopJob() {
        playing = false;
        work = true;
        while (!thread.isInterrupted()) {
            try {
                thread.interrupt();
            } catch (Exception e) {
                print("HardThread " + e);
            }
        }
        stopSchedule();
    }

    public void startJob() {
        work = false;
        playing = true;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (playing) {
            if (work) {
                function.run();
                work = false;
            }
        }
    }
}
