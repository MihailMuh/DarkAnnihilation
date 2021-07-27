package ru.warfare.darkannihilation;

import android.content.Context;

import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.systemd.Service;

import static android.os.VibrationEffect.createOneShot;

public class Vibrator {
    private static android.os.Vibrator vibrator;

    public static void init(Context context) {
        vibrator = (android.os.Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public static void vibrate(int millis) {
        if (Game.vibrate) {
            vibrator.vibrate(createOneShot(millis, 255));
        }
    }

    public static void vibrateInBackGround(int millis) {
        Service.runOnUiThread(() -> vibrate(millis));
    }
}
