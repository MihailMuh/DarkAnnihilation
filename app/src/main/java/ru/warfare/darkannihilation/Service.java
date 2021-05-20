package ru.warfare.darkannihilation;

import android.util.Log;

public final class Service {
    public static final String TAG = "D'Ark";

    public static void print(String text) {
        try {
            Log.e(TAG, text);
        } catch (Exception e) {
            Log.e(TAG, "Nope.");
        }
    }
}
