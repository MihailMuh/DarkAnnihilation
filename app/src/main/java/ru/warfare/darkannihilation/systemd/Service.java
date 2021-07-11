package ru.warfare.darkannihilation.systemd;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Point;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import ru.warfare.darkannihilation.math.Math;

import static android.content.Context.WINDOW_SERVICE;
import static android.os.VibrationEffect.createOneShot;
import static ru.warfare.darkannihilation.Constants.TAG;

public final class Service {
    private static MainActivity mainActivity;
    private static final Point size = new Point();
    private static final DisplayMetrics cutSize = new DisplayMetrics();
    private static int interval;
    private static Vibrator vibrator;

    public static void init(MainActivity mainActivity) {
        Service.mainActivity = mainActivity;

        (((WindowManager) mainActivity.getSystemService(WINDOW_SERVICE)).getDefaultDisplay()).getRealSize(size);

        mainActivity.getWindowManager().getDefaultDisplay().getMetrics(cutSize);
        interval = (size.x - cutSize.widthPixels) / 2;

        vibrator = (Vibrator) mainActivity.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public static void systemExit() {
        ActivityManager activityManager = (ActivityManager) mainActivity.getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.killBackgroundProcesses(mainActivity.getApplication().getPackageName());
        mainActivity.finishAndRemoveTask();
        System.exit(0);
    }

    public static void vibrate(int millis) {
        if (Game.vibrate) {
            vibrator.vibrate(createOneShot(millis, 255));
        }
    }

    public static void runOnUiThread(Runnable runnable) {
        mainActivity.runOnUiThread(runnable);
    }

    public static void makeToast(String text, boolean longToast) {
        runOnUiThread(() -> Toast.makeText(mainActivity, text, Math.boolToInt(longToast)).show());
    }

    public static double getSizeAppInRAM() {
        return ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576f);
    }

    public static MainActivity getContext() {
        return mainActivity;
    }

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            print(e.toString());
        }
    }

    public static int getWidthInterval() {
        return interval;
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
            Log.e(TAG, e.toString());
        }
    }
}
