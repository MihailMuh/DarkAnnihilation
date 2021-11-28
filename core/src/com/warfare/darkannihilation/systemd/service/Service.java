package com.warfare.darkannihilation.systemd.service;

import com.badlogic.gdx.Gdx;

public final class Service {
    private static final StringBuilder stringBuilder = new StringBuilder();

    public synchronized static void print(Object... objects) {
        try {
            for (Object o : objects) {
                stringBuilder.append(o).append(" ");
            }
            Gdx.app.error("DART", stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString());
            stringBuilder.setLength(0);
        } catch (Exception exception) {
            print("Error When Log:", exception);
        }
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
