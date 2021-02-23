package ru.startandroid.surfacedemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.util.Log;

public class Explosion {
    private Bitmap[] img = new Bitmap[28];
    public static final Paint color = new Paint();
    public int x = 0;
    public int y = 0;
    private int frame = 0;
    private final Game game;
    public boolean lock = true;
    public int width = 0;

    public Explosion(Game g, String size) {
        game = g;
        switch (size)
        {
            case "small":
                img = ImageHub.explosionImageSmall.clone();
                width = img[0].getWidth();
                break;
            case "default":
                img = ImageHub.explosionImageDefault.clone();
                width = img[0].getWidth();
                break;
            case "large":
                img = ImageHub.explosionImageLarge.clone();
                width = img[0].getWidth();
                break;
        }
    }

    public void start(int X, int Y) {
        x = X - width / 2;
        y = Y - width / 2;
        lock = false;
    }

    public void update() {
        if (frame != 28) {
            game.canvas.drawBitmap(img[frame], x, y, color);
            frame += 1;
        } else {
            frame = 0;
            lock = true;
        }
    }
}
