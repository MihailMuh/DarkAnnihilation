package ru.startandroid.surfacedemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;

public class Bullet {
    public int x;
    public int y;
    public int width;
    public int height;
    public static final Paint color = new Paint();
    public static final int speed = 10;
    private final Game game;
    public int damage = 1;

    public Bullet(Game g, int X, int Y) {
        game = g;
//        color.setColor(Color.WHITE);

        width = ImageHub.bulletImage.getWidth();
        height = ImageHub.bulletImage.getHeight();
        x = X;
        y = Y;
        game.audioPlayer.playShoot();
    }

    public void update() {
        y -= speed;
        game.canvas.drawBitmap(ImageHub.bulletImage, x, y, color);
//        game.canvas.drawRect(x, y, x + width, y + height, color);
    }
}
