package ru.warfare.darkannihilation.thread;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.warfare.darkannihilation.interfaces.Function;

import static ru.warfare.darkannihilation.systemd.service.Py.print;

public class HardThread implements Runnable {
    private static final ExecutorService threadPool = Executors.newWorkStealingPool();
    static ArrayList<GameTask> tasks = new ArrayList<>(0);
    private static ArrayList<Function> blackHole = new ArrayList<>(0);
    private static volatile boolean hole;
    private Thread thread;
    private static Function function;
    private boolean playing;
    private static volatile boolean work;

    public HardThread() {
        startJob();
    }

    public static void createBlackHole(Function func) {
        threadPool.execute(() -> {
            hole = true;

            blackHole.add(func);

            for (int i = 0; i < blackHole.size(); i++) {
                blackHole.get(i).run();
            }

            hole = false;

            blackHole = new ArrayList<>(0);
        });
    }

    public static void doInBackGround(Function func) {
        if (!work) {
            function = func;
            work = true;
        } else {
            if (!hole) {
                threadPool.execute(func::run);
            } else {
                blackHole.add(func);
            }
        }
    }

    public static void doInPool(Function function) {
        if (!hole) {
            threadPool.execute(function::run);
        } else {
            blackHole.add(function);
        }
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
        thread.setDaemon(true);
        thread.setPriority(Thread.MIN_PRIORITY);
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
