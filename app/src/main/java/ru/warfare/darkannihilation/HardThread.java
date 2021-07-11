package ru.warfare.darkannihilation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.warfare.darkannihilation.systemd.Service;

public class HardThread implements Runnable {
    private static final ExecutorService threadPool = Executors.newCachedThreadPool();
    private Thread thread;
    private static volatile boolean work;
    private static Function function;
    private static volatile boolean playing = false;

    public HardThread() {
        startJob();
    }

    public static void newJob(Function func) {
        if (!work) {
            function = func;
            work = true;
        } else {
            threadPool.execute(func::run);
        }
    }

    public void stopJob() {
        while (playing) {
            try {
                thread.join();
                playing = false;
                work = false;
            } catch (Exception e) {
                Service.print("Thread join " + e.toString());
            }
        }
    }

    public void startJob() {
        if (!playing) {
            playing = true;
            thread = new Thread(this);
            thread.start();
        }
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
