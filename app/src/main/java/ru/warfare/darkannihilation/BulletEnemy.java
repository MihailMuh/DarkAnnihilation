package ru.warfare.darkannihilation;

import android.graphics.Bitmap;

public class BulletEnemy extends Sprite {
    private final Bitmap image;

    public BulletEnemy(int X, int Y, int angle, int spdx, int spdy) {
        super();
        damage = 5;
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
        Game.canvas.drawBitmap(image, x, y, null);
    }
}


class BulletEnemyCut extends Sprite {
    private final Bitmap image;
    private final float spdX;
    private final float spdY;
    private float X;
    private float Y;

    public BulletEnemyCut(float X, float Y, float angle, float spdx, float spdy) {
        super();
        damage = 5;
        status = "bulletEnemy";
        isBullet = true;

        this.spdX = spdx;
        this.spdY = spdy;

        this.X = X;
        this.Y = Y;

        image = ImageHub.rotateImage(ImageHub.bulletEnemyImage, angle);

        width = image.getWidth();
        height = image.getHeight();
        halfWidth = width / 2;
        halfHeight = height / 2;

        left = (int) X;
        top = (int) Y;
        right = (int) (X + width);
        bottom = (int) (Y + height);
    }

    @Override
    public int centerX() {
        return (int) (X + halfWidth);
    }

    @Override
    public int centerY() {
        return (int) (Y + halfHeight);
    }

    @Override
    public Sprite getRect() {
        right += X - left;
        bottom += Y - top;
        left = (int) X;
        top = (int) Y;
        return this;
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
        Y += spdY;
        X += spdX;

        if (X < -width | X > Game.screenWidth | Y > Game.screenHeight | Y < -height) {
            Game.allSprites.remove(this);
        }
    }

    @Override
    public void render () {
        Game.canvas.drawBitmap(image, X, Y, null);
    }
}
