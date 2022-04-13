package com.warfare.darkannihilation.systemd.service;

import com.badlogic.gdx.Gdx;

public final class Watch {
    public static float delta, time;
    public static int frameCount;

    public static void update() {
        delta = Gdx.graphics.getDeltaTime();
        time += delta;
        frameCount++;
    }
}
