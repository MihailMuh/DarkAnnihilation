package ru.warfare.darkannihilation;

import android.annotation.SuppressLint;
import android.content.Context;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public final class Clerk {
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    public static String nickname = "";

    public static void init(Context context) {
        Clerk.context = context;
    }

    public static void saveNickname() {
        try {
            FileOutputStream writer = context.openFileOutput("NICKNAME.txt", Context.MODE_PRIVATE);
            OutputStreamWriter writer_str = new OutputStreamWriter(writer);

            writer_str.write(nickname);
            writer_str.close();
        } catch (Exception e) {
            Service.print("Can't save NICKNAME " + e);
        }
    }

    public static void getNickname() {
        try {
            InputStreamReader reader_cooler = new InputStreamReader(context.openFileInput("NICKNAME.txt"));
            BufferedReader reader_buffer = new BufferedReader(reader_cooler);

            nickname = reader_buffer.readLine();

            reader_cooler.close();
        } catch (Exception e) {
            Service.print("Can't recovery NICKNAME " + e);
            Service.print("Creating new file...");
            try {
                FileOutputStream writer = context.openFileOutput("NICKNAME.txt", Context.MODE_PRIVATE);
                OutputStreamWriter writer_str = new OutputStreamWriter(writer);

                writer_str.write("");
                writer_str.close();
                Service.print("Successful");
            } catch (Exception e2) {
                Service.print("Err: " + e2);
            }
            nickname = "";
        }
    }

    public static void saveBestScore(int bestScore) {
        try {
            FileOutputStream writer = context.openFileOutput("SCORE.txt", Context.MODE_PRIVATE);
            OutputStreamWriter writer_str = new OutputStreamWriter(writer);

            writer_str.write(bestScore + "");
            writer_str.close();
        } catch (Exception e) {
            Service.print("Can't save SCORE " + e);
        }
    }

    public static int getMaxScore() {
        try {
            InputStreamReader reader_cooler = new InputStreamReader(context.openFileInput("SCORE.txt"));
            BufferedReader reader_buffer = new BufferedReader(reader_cooler);

            String string = reader_buffer.readLine();
            int bestScore = 0;
            if (string != null) {
                bestScore = Integer.parseInt(string);
            }
            reader_cooler.close();

            return bestScore;

        } catch (Exception e) {
            Service.print("Can't recovery SCORE: " + e);
            Service.print("Creating new file...");
            try {
                FileOutputStream writer = context.openFileOutput("SCORE.txt", Context.MODE_PRIVATE);
                OutputStreamWriter writer_str = new OutputStreamWriter(writer);

                writer_str.write("");
                writer_str.close();
                Service.print("Successful");
            } catch (Exception e2) {
                Service.print("Err: " + e2);
            }
            return 0;
        }
    }

    public static void saveSettings(String string) {
        try {
            FileOutputStream writer = context.openFileOutput("SETTINGS.txt", Context.MODE_PRIVATE);
            OutputStreamWriter writer_str = new OutputStreamWriter(writer);

            writer_str.write(string);
            writer_str.close();
        } catch (Exception e) {
            Service.print("Can't save SETTINGS " + e);
        }
    }

    public static String getSettings() {
        try {
            InputStreamReader reader_cooler = new InputStreamReader(context.openFileInput("SETTINGS.txt"));
            BufferedReader reader_buffer = new BufferedReader(reader_cooler);

            String string = reader_buffer.readLine();
            reader_cooler.close();

            if (string == null) {
                return "1 1 1 en 1";
            }
            return string;

        } catch (Exception e) {
            Service.print("Can't recovery SETTINGS: " + e);
            Service.print("Creating new file...");
            try {
                FileOutputStream writer = context.openFileOutput("SETTINGS.txt", Context.MODE_PRIVATE);
                OutputStreamWriter writer_str = new OutputStreamWriter(writer);

                writer_str.write("");
                writer_str.close();
                Service.print("Successful");
            } catch (Exception e2) {
                Service.print("Err: " + e2);
            }
        }
        return "1 1 1 en 1";
    }

}
