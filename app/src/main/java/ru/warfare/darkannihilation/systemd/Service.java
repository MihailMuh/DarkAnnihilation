package ru.warfare.darkannihilation.systemd;

import android.app.ActivityManager;
import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import ru.warfare.darkannihilation.math.Math;

import static ru.warfare.darkannihilation.Py.print;

public final class Service {
    private static MainActivity mainActivity;
    private static String path;

    public static void init(MainActivity mainActivity) {
        Service.mainActivity = mainActivity;

        path = "android.resource://" + mainActivity.getPackageName() + "/";
    }

    public static void systemExit() {
        ActivityManager activityManager = (ActivityManager) mainActivity.getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.killBackgroundProcesses(mainActivity.getApplication().getPackageName());
        mainActivity.finishAndRemoveTask();
        System.exit(0);
    }

    public static void runOnUiThread(Runnable runnable) {
        mainActivity.runOnUiThread(runnable);
    }

    public static void makeToast(String text, boolean longToast) {
        runOnUiThread(() -> Toast.makeText(mainActivity, text, Math.boolToInt(longToast)).show());
    }

    public static MainActivity getContext() {
        return mainActivity;
    }

    public static String getResPath() {
        return path;
    }

    public static void writeToFile(String fileName, String content) {
        try {
            OutputStreamWriter writer_str = new OutputStreamWriter(
                    mainActivity.openFileOutput(fileName + ".txt", Context.MODE_PRIVATE));

            writer_str.write(content);
            writer_str.close();
        } catch (Exception e) {
            print("Can't save " + fileName + " " + e);
        }
    }

    public static String readFromFile(String fileName) {
        try {
            InputStreamReader reader_cooler = new InputStreamReader(mainActivity.openFileInput(fileName + ".txt"));

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
