package ru.startandroid.surfacedemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.media.MediaPlayer;

public class Bullet {
    public Bitmap bulletImage;
    public int x;
    public int y;
    public int width;
    public int height;
    private static final boolean isFilter = true;
    public static final Paint color = new Paint();
    public static final int speed = 10;
    private final Game game;
    public int damage = 1;

    public Bullet(Game g, int X, int Y) {
        game = g;
        bulletImage = BitmapFactory.decodeResource(game.context.getResources(), R.drawable.bullet);
        bulletImage = Bitmap.createScaledBitmap(bulletImage, 7, 30, isFilter);
        width = bulletImage.getWidth();
        height = bulletImage.getHeight();
        x = X;
        y = Y;
        game.audioPlayer.playShoot();
    }

    public void update() {
        y -= speed;
//        canvas.drawRect(x, y, x + width, y + height, color);
        game.canvas.drawBitmap(bulletImage, x, y, color);
    }
}
