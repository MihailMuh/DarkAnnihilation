package ru.warfare.darkannihilation.systemd.service;

import static ru.warfare.darkannihilation.arts.ImageHub.endImgInit;

public final class Time {
    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Py.print("Sleep " + e.toString());
            sleep(millis);
        }
    }

    public static void waitImg() {
        while (!endImgInit) {
            sleep(150);
        }
    }
}
