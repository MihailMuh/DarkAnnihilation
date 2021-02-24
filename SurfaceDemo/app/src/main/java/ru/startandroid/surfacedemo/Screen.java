package ru.startandroid.surfacedemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class Screen {
    public static final Paint color = new Paint();
    public int x;
    public int y;
    private long lastUpdate;
    private static final int frameRate = 25;
    private long now;
    private int frame = 0;
    private final Game game;
    private static final int screenImageLength = ImageHub.screen_image.length;

    public Screen(Game g) {
        game = g;

        x = (int) (game.screenWidth * -0.2);
        y = 0;

        lastUpdate = System.nanoTime();
    }

    public void update() {
        now = System.nanoTime();
        if (now - lastUpdate > frameRate) {
            lastUpdate = now;
            if (frame == screenImageLength) {
                frame = 0;
            }
            game.canvas.drawBitmap(ImageHub.screen_image[frame], x, y, color);
            frame += 1;
        }
    }

    public void update(Bitmap img) {
        game.canvas.drawBitmap(img, 0, 0, color);
    }
}
