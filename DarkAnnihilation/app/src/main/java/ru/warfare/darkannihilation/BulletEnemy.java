package ru.warfare.darkannihilation;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class BulletEnemy extends Sprite {
    private final Bitmap img;
    public int damage = 5;

    public BulletEnemy(Game g, int X, int Y, double angle, int spdx, int spdy) {
        super(g, ImageHub.bulletEnemyImage.getWidth(), ImageHub.bulletEnemyImage.getHeight());
        AudioPlayer.playShotgun();

        speedX = spdx;
        speedY = spdy;

        Matrix matrix = new Matrix();
        matrix.postRotate((float) angle);

        img = Bitmap.createBitmap(ImageHub.bulletEnemyImage, 0, 0, width, height, matrix, ImageHub.isFilter);

        x = X;
        y = Y;
    }

    @Override
    public void update() {
        y += speedY;
        x += speedX;

        game.player.check_intersectionBullet(this);

        if (y > screenHeight | x < -100 | x > screenWidth | y < -height) {
            game.bulletEnemies.remove(this);
            game.numberBulletsEnemy -= 1;
        }
    }

    @Override
    public void render () {
        game.canvas.drawBitmap(img, x, y, null);
    }
}
