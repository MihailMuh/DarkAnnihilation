package ru.warfare.darkannihilation.base;

import android.graphics.Bitmap;

public class BaseExplosion extends Sprite {
    public Bitmap[] img;
    public int frame = 0;

    public BaseExplosion(Bitmap bitmap) {
        super(bitmap);
        lock = true;
        isPassive = true;
        isBullet = true;
    }

    public void start(int X, int Y) {
        x = X - halfWidth;
        y = Y - halfHeight;
        lock = false;
    }

    public void stop() {
        lock = true;
        frame = 0;
    }
}
