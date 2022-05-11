package com.warfare.darkannihilation.systemd.service;

import static com.warfare.darkannihilation.systemd.service.Service.print;

import com.badlogic.gdx.Gdx;

public final class Windows {
    public static int SCREEN_HEIGHT;
    public static int HALF_SCREEN_HEIGHT;

    public static int HALF_SCREEN_WIDTH;
    public static int SCREEN_WIDTH;

    public static int DEVICE_WIDTH;
    public static int DEVICE_HEIGHT;

    public static void refresh() {
        DEVICE_WIDTH = Gdx.graphics.getWidth();
        DEVICE_HEIGHT = Gdx.graphics.getHeight();

        float height = 1180;
        if (DEVICE_HEIGHT <= 860) {
            height = 860;
        }

        SCREEN_HEIGHT = (int) ((height / (float) DEVICE_HEIGHT) * height);
        HALF_SCREEN_HEIGHT = SCREEN_HEIGHT / 2;

        SCREEN_WIDTH = (int) ((DEVICE_WIDTH / (float) DEVICE_HEIGHT) * SCREEN_HEIGHT);
        HALF_SCREEN_WIDTH = SCREEN_WIDTH / 2;
    }
}
