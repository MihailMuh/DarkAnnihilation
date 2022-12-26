package ru.warfare.darkannihilation.systemd.service;

import android.graphics.Point;

import static android.content.Context.WINDOW_SERVICE;
import static ru.warfare.darkannihilation.systemd.service.Service.activity;
import static ru.warfare.darkannihilation.systemd.service.Service.resources;

public final class Windows {
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;

    public static int HALF_SCREEN_WIDTH;
    public static int HALF_SCREEN_HEIGHT;

    private static float DENSITY;
    public static float LAYOUT_DENSITY;

    public static void init() {
        Point size = new Point();
        (((android.view.WindowManager) activity.getSystemService(WINDOW_SERVICE)).getDefaultDisplay()).getRealSize(size);

        SCREEN_WIDTH = size.x;
        SCREEN_HEIGHT = size.y;

        HALF_SCREEN_WIDTH = SCREEN_WIDTH / 2;
        HALF_SCREEN_HEIGHT = SCREEN_HEIGHT / 2;

        DENSITY = size.y / 1080f;
        LAYOUT_DENSITY = (size.y / 720f) * resources.getDisplayMetrics().density;
    }

    public static int calculate(int oldInt) {
        return (int) (oldInt * DENSITY);
    }

    public static int calculate(double oldInt) {
        return (int) (oldInt * DENSITY);
    }
}
