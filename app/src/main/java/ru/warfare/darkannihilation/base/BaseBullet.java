package ru.warfare.darkannihilation.base;

import android.graphics.Bitmap;

import ru.warfare.darkannihilation.systemd.Game;

public abstract class BaseBullet extends Sprite {
    public byte power = 0;

    public BaseBullet(Game game, Bitmap bitmap, int X, int Y, int dmg) {
        super(game, bitmap);
        damage = dmg;

        x = X - halfWidth;
        y = Y;
    }

    @Override
    public void check_intersectionBullet(BaseBullet bullet) {
    }

    @Override
    public void start() {
    }

    @Override
    public void kill() {
        createSmallExplosion();
    }
}
