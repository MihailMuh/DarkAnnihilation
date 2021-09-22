package ru.warfare.darkannihilation.thread;

import static ru.warfare.darkannihilation.systemd.service.Py.print;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.warfare.darkannihilation.interfaces.Function;

public class HardThread {
    private static final ExecutorService threadPool = Executors.newCachedThreadPool();
    private static ArrayList<Function> functions = new ArrayList<>(0);
    static ArrayList<GameTask> tasks = new ArrayList<>(0);
    private static Handler handler;
    private Thread thread;
    private static volatile boolean work;
    private static volatile boolean blackHole;

    public HardThread() {
        startJob();
    }

    public static void createBlackHole(Function function) {
        blackHole = true;

        threadPool.execute(() -> {
            functions.add(function);

            for (int i = 0; i < functions.size(); i++) {
                functions.get(i).run();
            }

            blackHole = false;

            functions = new ArrayList<>(0);
        });
    }

    public static void doInBackGround(Function function) {
        if (!work) {
            work = true;
            handler.post(() -> {
                function.run();
                work = false;
            });
        } else {
            if (!blackHole) {
                threadPool.execute(function::run);
            } else {
                functions.add(function);
            }
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
        thread.start();
    }
}
