package ru.warfare.darkannihilation.systemd.service;

import android.net.Uri;

public final class UriManager {
    private static final String pathImages = "file:///android_asset/images/";

    public static Uri images(String name) {
        return Uri.parse(pathImages + name);
    }

    public static Uri starScreen(int name) {
        return images("star_screen/" + name + ".jpg");
    }

    public static Uri skullExplosion(int name) {
        return images("skull_explosion/" + name + ".png");
    }

    public static Uri defaultExplosion(int name) {
        return images("default_explosion/" + name + ".png");
    }

    public static Uri tripleExplosion(int name) {
        return images("triple_explosion/" + name + ".png");
    }

    public static Uri loadingScreen(int name) {
        return images("loading_screen/" + name + ".jpg");
    }

    public static Uri portal(int name) {
        return images("portal/" + name + ".png");
    }

    public static Uri thunderScreen(int name) {
        return images("thunder_screen/" + name + ".jpg");
    }

    public static Uri atomicBomb(int name) {
        return images("atomic_bomb/" + name + ".png");
    }

    public static Uri laser(int name) {
        return images("laser/" + name + ".png");
    }

    public static Uri vader(int name) {
        return images("vaders/" + name + ".png");
    }

    public static Uri vaderOld(int name) {
        return images("vaders/" + (name + 3) + ".png");
    }

    public static Uri millennuimGuns(int name) {
        return images("changer_guns/" + name + ".png");
    }

    public static Uri saturnGuns(int name) {
        return images("changer_guns/" + (name + 3) + ".png");
    }

    public static Uri emeraldGuns(int name) {
        return images("changer_guns/" + (name + 6) + ".png");
    }
}
