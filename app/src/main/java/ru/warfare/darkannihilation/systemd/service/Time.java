package ru.warfare.darkannihilation.systemd.service;

import static ru.warfare.darkannihilation.arts.ImageHub.endImgInit;

public final class Time {
    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Py.print("Sleep " + e.toString());
        }
    }

    public static void waitImg() {
        while (!endImgInit) {
            relax();
        }
    }

    public static void relax() {
        Thread.yield();
    }
}
