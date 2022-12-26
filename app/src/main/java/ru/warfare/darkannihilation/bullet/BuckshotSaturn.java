package ru.warfare.darkannihilation.bullet;

import android.graphics.Bitmap;

import ru.warfare.darkannihilation.base.BaseBullet;
import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.arts.ImageHub;
import ru.warfare.darkannihilation.base.Sprite;
import ru.warfare.darkannihilation.math.Vector;

import static ru.warfare.darkannihilation.constant.Constants.BUCKSHOT_SATURN_DAMAGE;
import static ru.warfare.darkannihilation.constant.NamesConst.BULLET_SATURN;

public class BuckshotSaturn extends BaseBullet {
    private float X;
    private float Y;
    private boolean orbit;
    private float fly;
    private final Vector vector = new Vector();

    public BuckshotSaturn(Game game) {
        super(game, ImageHub.bulletBuckshotSaturnImg, BUCKSHOT_SATURN_DAMAGE);
        name = BULLET_SATURN;
    }

    @Override
    public void start(int X, int Y) {
        fly = 0;
        orbit = false;

        this.X = X - halfWidth;
        this.Y = Y;

        left = X;
        top = Y;
        right = X + width;
        bottom = Y + height;

        int XX = centerX();
        vector.basisVector(XX, centerY(), XX, 0, 4);
        vector.rads -= 0.0002;

        lock = false;
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
    public Object[] getBox(Bitmap image) {
        return new Object[]{X, Y, orbit, vector.rads, fly, vector.len, image};
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
    public void kill() {
        super.kill();
        lock = true;
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
        }

        float[] speeds = vector.rotateVector();
        X += speeds[0] + game.player.speedX;
        Y += speeds[1] + game.player.speedY;
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(image, X, Y, null);
    }
}