package ru.warfare.darkannihilation.thread;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.warfare.darkannihilation.interfaces.Function;

import static ru.warfare.darkannihilation.systemd.service.Py.print;

public class HardThread implements Runnable {
    private static final ExecutorService threadPool = Executors.newWorkStealingPool();
    static ArrayList<GameTask> tasks = new ArrayList<>(0);
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

    public static void finishAndRemoveTasks() {
        pauseTasks();
        tasks = new ArrayList<>(0);
    }

    public static void pauseTasks() {
        for (GameTask gameTask : tasks) {
            gameTask.pause();
        }
    }

    public static void resumeTasks() {
        for (GameTask gameTask : tasks) {
            gameTask.resume();
        }
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
        pauseTasks();
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
