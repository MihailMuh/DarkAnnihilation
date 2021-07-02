package ru.warfare.darkannihilation;

import android.graphics.Bitmap;

import static ru.warfare.darkannihilation.Constants.BULLET_ENEMY_DAMAGE;

public class BulletEnemy extends Sprite {
    private final Bitmap image;

    public BulletEnemy(int X, int Y, int angle, int spdx, int spdy) {
        super();
        damage = BULLET_ENEMY_DAMAGE;
        status = "bulletEnemy";
        isBullet = true;

        speedX = spdx;
        speedY = spdy;

        image = ImageHub.rotateImage(ImageHub.bulletEnemyImage, angle);

        x = X;
        y = Y;

        width = image.getWidth();
        height = image.getHeight();
        halfWidth = width / 2;
        halfHeight = height / 2;

        left = x;
        top = y;
        right = x + width;
        bottom = y + height;
    }

    @Override
    public Object[] getBox(int a, int b, Bitmap image) {
        return new Object[] {ImageHub.bulletEnemyImage};
    }

    @Override
    public void intersection() {
        intersectionPlayer();
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
        Game.canvas.drawBitmap(image, x, y, Game.nicePaint);
    }
}
