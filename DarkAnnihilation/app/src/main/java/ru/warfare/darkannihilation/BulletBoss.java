package ru.warfare.darkannihilation;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class BulletBoss {
    private Bitmap img;

    public int x;
    public int y;
    public int width;
    public int height;
    public int halfWidth;
    public int halfHeight;
    public static final int speedy = 6;
    public int speedx = 0;
    private final Game game;
    public int damage = 5;

    public BulletBoss(Game g, int X, int Y, int type) {
        game = g;

        width = ImageHub.laserImage.getWidth();
        height = ImageHub.laserImage.getHeight();
        halfWidth = width / 2;
        halfHeight = height / 2;

        Matrix matrix = new Matrix();
        switch (type)
        {
            case 1:
                img = ImageHub.laserImage;
                break;
            case 2:
                speedx = 4;
                matrix.postRotate(30);
                img = Bitmap.createBitmap(ImageHub.laserImage, 0, 0, width, height, matrix, ImageHub.isFilter);
                break;
            case 3:
                speedx = -4;
                matrix.postRotate(330);
                img = Bitmap.createBitmap(ImageHub.laserImage, 0, 0, width, height, matrix, ImageHub.isFilter);
                break;
        }

        x = X;
        y = Y;
    }

    public void update() {
        y += speedy;
        x -= speedx;
        game.canvas.drawBitmap(img, x, y, null);
//        game.canvas.drawRect(x, y, x + width, y + height, color);
    }
}
