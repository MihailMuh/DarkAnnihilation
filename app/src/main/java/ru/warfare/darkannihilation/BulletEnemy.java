package ru.warfare.darkannihilation;

import android.graphics.Bitmap;

public class BulletEnemy extends Sprite {
    private final Bitmap image;

    public BulletEnemy(int X, int Y, int angle, int spdx, int spdy) {
        super(ImageHub.bulletEnemyImage.getWidth(), ImageHub.bulletEnemyImage.getHeight());
        damage = 5;
        status = "bulletEnemy";
        isBullet = true;

        speedX = spdx;
        speedY = spdy;

        image = ImageHub.rotateImage(ImageHub.bulletEnemyImage, angle);

        x = X;
        y = Y;
    }

    @Override
    public Object[] getBox(int a, int b, Bitmap image) {
        return new Object[] {ImageHub.bulletEnemyImage};
    }

    @Override
    public void intersection() {
        createSmallExplosion();
        Game.allSprites.remove(this);
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
        Game.canvas.drawBitmap(image, x, y, null);
    }
}
