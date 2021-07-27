package ru.warfare.darkannihilation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.warfare.darkannihilation.interfaces.Function;
import ru.warfare.darkannihilation.systemd.Service;

import static ru.warfare.darkannihilation.Py.print;

public class HardThread implements Runnable {
    private static final ExecutorService threadPool = Executors.newCachedThreadPool();
    private Thread thread;
    private static Function function;
    private static volatile boolean playing;
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

    public static void doInUI(Function func) {
        doInBackGround(() -> Service.runOnUiThread(func::run));
    }

    public void stopJob() {
        playing = false;
        work = true;
        while (!thread.isInterrupted()) {
            try {
                thread.interrupt();
            } catch (Exception e) {
                print("HardThread " + e.toString());
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