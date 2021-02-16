package ru.startandroid.surfacedemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class Bullet {
    public Bitmap bulletImage;
    public float x;
    public float y;
    public float width;
    public float height;
    private final boolean isFilter = true;
    public final Paint color = new Paint();
    public float speed = 10;
    private Game game;

    public Bullet(Game g, int X, int Y) {
        game = g;
        bulletImage = BitmapFactory.decodeResource(game.context.getResources(), R.drawable.bullet);
        bulletImage = Bitmap.createScaledBitmap(bulletImage, 7, 30, isFilter);
        width = bulletImage.getWidth();
        height = bulletImage.getHeight();
        x = X;
        y = Y;
    }

    public void update() {
        y -= speed;
//        canvas.drawRect(x, y, x + width, y + height, color);
        game.canvas.drawBitmap(bulletImage, x, y, color);
    }
}
