package ru.warfare.darkannihilation.systemd.service;

import ru.warfare.darkannihilation.systemd.Game;

import static android.content.Context.VIBRATOR_SERVICE;
import static android.os.VibrationEffect.createOneShot;
import static ru.warfare.darkannihilation.systemd.service.Service.activity;

public final class Vibrator {
    private static android.os.Vibrator vibrator;

    public static void init() {
        vibrator = (android.os.Vibrator) activity.getSystemService(VIBRATOR_SERVICE);
    }

    public static void vibrate(int millis) {
        if (Game.vibrate) {
            vibrator.vibrate(createOneShot(millis, 255));
        }
    }
}
