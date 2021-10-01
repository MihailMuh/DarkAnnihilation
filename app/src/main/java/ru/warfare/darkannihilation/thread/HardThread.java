package ru.warfare.darkannihilation.thread;

import static ru.warfare.darkannihilation.systemd.service.Py.print;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.warfare.darkannihilation.interfaces.Function;

public class HardThread {
    private static final ExecutorService threadPool = Executors.newWorkStealingPool();
    static ArrayList<GameTask> tasks = new ArrayList<>(0);
    private static ArrayList<Function> functions = new ArrayList<>(0);
    private static Handler handler;
    private Thread thread;
    private static volatile boolean work;
    private static volatile boolean blackHole;

    public HardThread() {
        startJob();
    }

    public static void createBlackHole(Function function) {
        if (!blackHole) {
            blackHole = true;

            threadPool.execute(() -> {
                if (function != null) {
                    functions.add(function);
                }

                for (int i = 0; i < functions.size(); i++) {
                    functions.get(i).run();
                }

                closeBlackHole();
            });
        }
    }

    public static void createBlackHole() {
        createBlackHole(null);
    }

    public static void closeBlackHole() {
        if (blackHole) {
            blackHole = false;
            functions = new ArrayList<>(0);
        }
    }

    public static void doInBackGround(Function function) {
        if (!work) {
            work = true;
            handler.post(() -> {
                function.run();
                work = false;
            });
        } else {
            doInPool(function);
        }
    }

    public static void doInPool(Function function) {
        if (!blackHole) {
            threadPool.execute(function::run);
        } else {
            functions.add(function);
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
        work = true;

        handler.getLooper().quitSafely();
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

        thread = new Thread(() -> {
            Looper.prepare();
            handler = new Handler();
            Looper.loop();
        });
        thread.setDaemon(true);
        thread.start();
    }
}
