package ru.warfare.darkannihilation;

import android.graphics.Bitmap;

public class BuckshotSaturn extends Sprite {
    private final Vector vector = new Vector();
    private float X;
    private float Y;
    private boolean orbit = false;
    private float fly = 0;

    public BuckshotSaturn(Game game, int X, int Y) {
        super(game, ImageHub.bulletBuckshotSaturnImg.getWidth(), ImageHub.bulletBuckshotSaturnImg.getHeight());

        damage = 1;
        isPassive = true;
        isBullet = true;

        status = "saturn";

        this.X = X - halfWidth;
        this.Y = Y;

        left = X;
        top = Y;
        right = X + width;
        bottom = Y + height;

        vector.basisVector(centerX(), centerY(), centerX(), 0, 4);
        vector.rads -= 0.0002;
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
    public Object[] getBox(int enemyX, int enemyY, Bitmap image) {
        return new Object[]{game, enemyX, enemyY, orbit, vector.rads, fly, vector.len, image};
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
    public void intersection() {
        createSmallExplosion();
        Game.bullets.remove(this);
        Game.allSprites.remove(this);
    }

    @Override
    public void update() {
        if (!orbit) {
            if (vector.len <= 4.4) {
                vector.rads -= 0.0615;
                vector.len += 0.01;
            } else {
                orbit = true;
            }
        } else {
            vector.rads -= 0.03 - fly;
            fly += 0.000008;
//            62.43
//            0.0003333333;
        }

        float[] speeds = vector.rotateVector();
        X += (speeds[0] + game.player.speedX);
        Y += (speeds[1] + game.player.speedY);
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(ImageHub.bulletBuckshotSaturnImg, X, Y, null);
    }
}