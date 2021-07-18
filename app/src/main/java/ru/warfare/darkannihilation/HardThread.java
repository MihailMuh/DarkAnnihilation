package ru.warfare.darkannihilation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.warfare.darkannihilation.systemd.Service;

public class HardThread implements Runnable {
    private static final ExecutorService threadPool = Executors.newCachedThreadPool();
    private Thread thread;
    private static Function function;
    private static volatile boolean playing = false;
    private static volatile boolean work = true;

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
        playing = false;
        work = true;
        while (!thread.isInterrupted()) {
            try {
                thread.interrupt();
            } catch (Exception e) {
                Service.print("HardThread " + e.toString());
            }
        }
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
