package com.warfare.darkannihilation.systemd.service;

import com.badlogic.gdx.Gdx;

public final class Watch {
    public static float delta, time;

    public static void update() {
        delta = Gdx.graphics.getDeltaTime();
        if (delta > 1) {
            delta = 1;
        }
        time += delta;
    }
}
