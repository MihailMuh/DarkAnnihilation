package ru.warfare.darkannihilation;

import android.content.Context;
import android.graphics.Point;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.WindowManager;

import static android.content.Context.WINDOW_SERVICE;
import static ru.warfare.darkannihilation.Constants.TAG;

public final class Service {
    private static final Point size = new Point();
    private static MainActivity mainActivity;

    public static void init(MainActivity mainActivity) {
        Service.mainActivity = mainActivity;
        (((WindowManager) mainActivity.getSystemService(WINDOW_SERVICE)).getDefaultDisplay()).getRealSize(size);
    }

    public static void vibrate(int millis) {
        if (Game.vibrate) {
            ((Vibrator) mainActivity.getSystemService(Context.VIBRATOR_SERVICE))
                    .vibrate(VibrationEffect.createOneShot(millis, 30));
        }
    }

    public static int getScreenWidth() {
        return size.x;
    }

    public static int getScreenHeight() {
        return size.y;
    }

    public static double getResizeCoefficient() {
        return (double) size.x / 1920f;
    }

    public static double getResizeCoefficientForLayout() {
        return (double) size.y / 720f;
    }

    public static void print(String text) {
        try {
            Log.e(TAG, text);
        } catch (Exception e) {
            Log.e(TAG, "Nope.");
        }
    }
}
