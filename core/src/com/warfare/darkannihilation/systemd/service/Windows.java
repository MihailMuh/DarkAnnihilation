package com.warfare.darkannihilation.systemd.service;

import com.badlogic.gdx.Gdx;

public final class Windows {
    public static final int SCREEN_HEIGHT = 1080;
    public static final int HALF_SCREEN_HEIGHT = 540;

    public static float HALF_SCREEN_WIDTH;
    public static float SCREEN_WIDTH;

    public static int PHONE_WIDTH;
    public static int PHONE_HEIGHT;

    public static void refresh() {
        PHONE_WIDTH = Gdx.graphics.getWidth();
        PHONE_HEIGHT = Gdx.graphics.getHeight();

        SCREEN_WIDTH = PHONE_WIDTH / (PHONE_HEIGHT / (float) SCREEN_HEIGHT);
        HALF_SCREEN_WIDTH = SCREEN_WIDTH / 2f;
    }
}
