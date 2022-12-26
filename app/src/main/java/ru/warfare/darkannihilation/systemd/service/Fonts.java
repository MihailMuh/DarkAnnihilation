package ru.warfare.darkannihilation.systemd.service;

import android.graphics.Typeface;

import static ru.warfare.darkannihilation.systemd.service.Service.activity;

public final class Fonts {
    public static Typeface canisMinor;

    public static void init() {
        canisMinor = Typeface.createFromAsset(activity.getAssets(), "fonts/canis_minor.otf");
    }
}
