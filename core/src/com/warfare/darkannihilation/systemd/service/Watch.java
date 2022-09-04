package com.warfare.darkannihilation.systemd.service;

import com.badlogic.gdx.Gdx;

public final class Watch {
    private static boolean stoppedTime = false;

    public static float delta, time;

    public static void update() {
        delta = Gdx.graphics.getDeltaTime();

        if (!stoppedTime) {
            time += delta;
        }
    }

    public static void stopTime() {
        stoppedTime = true;
    }

    public static void resetTime() {
        stoppedTime = false;
    }
}
