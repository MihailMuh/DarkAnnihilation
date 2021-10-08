package ru.warfare.darkannihilation.thread;

import static ru.warfare.darkannihilation.systemd.service.Py.print;
import static ru.warfare.darkannihilation.systemd.service.Time.sleep;

import android.os.Handler;
import android.os.Looper;

public class Process {
    private Handler handler;
    private Thread thread;

    public void start() {
        thread = new Thread(() -> {
            Looper.prepare();
            handler = new Handler();
            Looper.loop();
        });
        thread.setDaemon(true);
        thread.start();
    }

    public void terminate() {
        handler.getLooper().quitSafely();
        while (!thread.isInterrupted()) {
            try {
                thread.interrupt();
            } catch (Exception e) {
                print("HardThread " + e);
            }
        }
    }

    public void post(Runnable runnable) {
        if (handler != null) {
            handler.post(runnable);
        } else {
            sleep(100);
            post(runnable);
        }
    }
}
