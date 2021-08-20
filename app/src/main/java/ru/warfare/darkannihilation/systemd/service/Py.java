package ru.warfare.darkannihilation.systemd.service;

import android.util.Log;

import static ru.warfare.darkannihilation.constant.Constants.TAG;

public final class Py {
    public static void print(Object object) {
        try {
            Log.e(TAG, object.toString());
        } catch (Exception e) {
            print(e);
        }
    }
}
