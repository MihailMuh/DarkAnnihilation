package ru.warfare.darkannihilation;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class BulletEnemy {
    private final Bitmap img;
    public int x;
    public int y;
    public int width;
    public int height;
    public int halfWidth;
    public int halfHeight;
    public int speedy;
    public int speedx;
    private final Game game;
    public int damage = 5;

    public BulletEnemy(Game g, int X, int Y, double angle, int spdx, int spdy) {
        game = g;
        AudioPlayer.playShotgun();
//        color.setColor(Color.WHITE);

        speedx = spdx;
        speedy = spdy;

        Matrix matrix = new Matrix();
        matrix.postRotate((float) angle);

        width = ImageHub.bulletEnemyImage.getWidth();
        height = ImageHub.bulletEnemyImage.getHeight();
        halfWidth = width / 2;
        halfHeight = height / 2;

        img = Bitmap.createBitmap(ImageHub.bulletEnemyImage, 0, 0, width, height, matrix, ImageHub.isFilter);

        x = X;
        y = Y;
    }

    public void update() {
        y += speedy;
        x += speedx;
    }

    public void render () {
        game.canvas.drawBitmap(img, x, y, null);
    }
}
