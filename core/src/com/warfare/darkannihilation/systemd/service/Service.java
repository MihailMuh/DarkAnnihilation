package com.warfare.darkannihilation.systemd.service;

import com.badlogic.gdx.Gdx;

public final class Service {
    public static void print(Object o) {
        try {
            Gdx.app.error("DART", o.toString());
        } catch (Exception exception) {
            print("Error When Log " + exception);
        }
    }

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            print("Error When Sleep " + e);
            sleep(millis);
        }
    }
}
