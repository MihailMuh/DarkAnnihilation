package com.warfare.darkannihilation.systemd.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.StringBuilder;

public final class Service {
    private static final StringBuilder stringBuilder = new StringBuilder();

    private static void print(String s) {
        Gdx.app.error("DART", s);
    }

    public synchronized static void print(Object... objects) {
        try {
            if (objects.length == 1) {
                print(objects[0].toString());
                return;
            }
            for (Object o : objects) {
                stringBuilder.append(o).append(" ");
            }
            print(stringBuilder.deleteCharAt(stringBuilder.length - 1).toString());
            stringBuilder.clear();
        } catch (Exception exception) {
            print("Error When Log:", exception);
        }
    }

    public static void printErr(String comment, Exception e) {
        print(comment, e.getStackTrace());
    }

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            print("Error When Sleep:", e);
            sleep(millis);
        }
    }
}
