package ru.warfare.darkannihilation.base;

import android.graphics.Bitmap;

import ru.warfare.darkannihilation.systemd.Game;

public abstract class BaseButton extends Sprite {

    public BaseButton(Game game, Bitmap bitmap) {
        super(game, bitmap);
    }

    public abstract void setCoords(int X, int Y);

    public boolean checkCoords(int X, int Y) {
        if (x < X) {
            if (X < right()) {
                if (y < Y) {
                    return Y < bottom();
                }
            }
        }
        return false;
    }

    @Override
    public void kill() {
    }

    @Override
    public void update() {
    }

    @Override
    public void check_intersectionBullet(BaseBullet bullet) {
    }

    @Override
    public void start() {
    }
}
