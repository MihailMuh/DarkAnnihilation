package ru.warfare.darkannihilation;

import android.annotation.SuppressLint;
import android.content.Context;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

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
            String line;
            StringBuilder builder = new StringBuilder();

            while ((line = reader_buffer.readLine()) != null) {
                builder.append(line);
            }

            reader_cooler.close();

            nickname = builder.toString();
        } catch (IOException e) {
            Service.print("Can't recovery NICKNAME " + e);
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
            int bestScore = Integer.parseInt(reader_buffer.readLine());
            reader_cooler.close();
            return bestScore;

        } catch (IOException e) {
            Service.print("Can't recovery SCORE: " + e);
            Service.print("Creating new file...");
            try {
                FileOutputStream writer = context.openFileOutput("SCORE.txt", Context.MODE_PRIVATE);
                OutputStreamWriter writer_str = new OutputStreamWriter(writer);

                writer_str.write("");
                writer_str.close();
                Service.print("Successful");
            } catch (IOException e2) {
                Service.print("Err: " + e2);
            }
        }
        return 0;
    }
}
