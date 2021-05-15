package ru.warfare.darkannihilation;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class BulletEnemy extends Sprite {
    private final Bitmap img;

    public BulletEnemy(Game g, int X, int Y, int angle, int spdx, int spdy) {
        super(g, ImageHub.bulletEnemyImage.getWidth(), ImageHub.bulletEnemyImage.getHeight());
        damage = 5;
        status = "bulletEnemy";
        isBullet = true;

        speedX = spdx;
        speedY = spdy;

        Matrix matrix = new Matrix();
        matrix.postRotate((float) angle);

        img = Bitmap.createBitmap(ImageHub.bulletEnemyImage, 0, 0, width, height, matrix, true);

        x = X;
        y = Y;
    }

    @Override
    public void intersectionPlayer() {
        createSmallExplosion();
        Game.allSprites.remove(this);
    }

    @Override
    public void update() {
        y += speedY;
        x += speedX;

        if (x < -width | x > Game.screenWidth | y > Game.screenHeight | y < -height) {
            Game.allSprites.remove(this);
        }
    }

    @Override
    public void render () {
        Game.canvas.drawBitmap(img, x, y, null);
    }
}
