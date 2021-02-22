package ru.startandroid.surfacedemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

public class BulletEnemy {
    public Bitmap bulletImage;
    public int x;
    public int y;
    public int width;
    public int height;
    private static final boolean isFilter = true;
    public static final Paint color = new Paint();
    public int speedy;
    public int speedx;
    private final Game game;
    public int damage = 5;

    public BulletEnemy(Game g, int X, int Y, double angle, int spdx, int spdy) {
        game = g;
        game.audioPlayer.playShotgun();
        color.setColor(Color.WHITE);

        speedx = spdx;
        speedy = spdy;

        Matrix matrix = new Matrix();
        matrix.postRotate((float) angle);

        bulletImage = BitmapFactory.decodeResource(game.context.getResources(), R.drawable.bullet_enemy);
        bulletImage = Bitmap.createScaledBitmap(bulletImage, 17, 50, isFilter);
        bulletImage = Bitmap.createBitmap(bulletImage, 0, 0, 17, 50, matrix, isFilter);
        width = bulletImage.getWidth();
        height = bulletImage.getHeight();

        x = X;
        y = Y;
    }

    public void update() {
        y += speedy;
        x += speedx;
//        game.canvas.drawRect(x, y, x + width, y + height, color);

        game.canvas.drawBitmap(bulletImage, x, y, color);
    }
}
