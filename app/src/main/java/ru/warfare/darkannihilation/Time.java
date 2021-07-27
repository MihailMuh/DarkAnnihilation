package ru.warfare.darkannihilation;

public class Time {
    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Py.print("Sleep" + e.toString());
        }
    }
}
