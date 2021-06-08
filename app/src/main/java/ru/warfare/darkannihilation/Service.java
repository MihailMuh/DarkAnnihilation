package ru.warfare.darkannihilation;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.WindowManager;

import static android.content.Context.WINDOW_SERVICE;

public final class Service {
    public static final String TAG = "D'Ark";
    private static final Point size = new Point();

    public static void init(Context context) {
        (((WindowManager) context.getSystemService(WINDOW_SERVICE)).getDefaultDisplay()).getRealSize(size);
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
