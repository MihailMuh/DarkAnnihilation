package ru.warfare.darkannihilation.systemd.service;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import ru.warfare.darkannihilation.math.Math;
import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.systemd.MainActivity;

import static ru.warfare.darkannihilation.systemd.service.Py.print;

public final class Service {
    public static MainActivity activity;
    public static Game game;

    public static String path;
    public static String packageName;
    public static Resources resources;

    public static void init(MainActivity mainActivity) {
        activity = mainActivity;
        game = mainActivity.game;

        resources = mainActivity.getResources();

        packageName = mainActivity.getPackageName();

        path = "android.resource://" + packageName + "/";
    }

    public static void systemExit() {
        ActivityManager activityManager = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.killBackgroundProcesses(activity.getApplication().getPackageName());
        activity.finishAndRemoveTask();
        System.exit(0);
    }

    public static void runOnUiThread(Runnable runnable) {
        activity.runOnUiThread(runnable);
    }

    public static void makeToast(String text, boolean longToast) {
        runOnUiThread(() -> Toast.makeText(activity, text, Math.boolToInt(longToast)).show());
    }

    public static void writeToFile(String fileName, String content) {
        try {
            OutputStreamWriter writer_str = new OutputStreamWriter(
                    activity.openFileOutput(fileName + ".txt", Context.MODE_PRIVATE));

            writer_str.write(content);
            writer_str.close();
        } catch (Exception e) {
            print("Can't save " + fileName + " " + e);
        }
    }

    public static String readFromFile(String fileName) {
        try {
            InputStreamReader reader_cooler = new InputStreamReader(activity.openFileInput(fileName + ".txt"));

            String string = new BufferedReader(reader_cooler).readLine();

            reader_cooler.close();

            return string;
        } catch (Exception e) {
            print("Can't recovery " + fileName + " " + e);
            print("Creating new file...");
            writeToFile(fileName, "");
            print("Successful");
            return null;
        }
    }
}
