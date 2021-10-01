package ru.warfare.darkannihilation.base;

import android.graphics.Bitmap;

import ru.warfare.darkannihilation.systemd.Game;

public abstract class BaseBullet extends Sprite {
    public byte power = 0;

    public BaseBullet(Game game, Bitmap bitmap, int dmg) {
        super(game, bitmap);
        damage = dmg;
        lock = true;
    }

    public BaseBullet(Game game, int dmg) {
        super(game);
        damage = dmg;
        lock = true;
    }

    public void start(int X, int Y) {
    }

    public void start(int X, int Y, int Z) {
    }

    public void start(int ... v) {
    }

    public void start(Object[] objects) {
    }

    @Override
    public void check_intersectionBullet(BaseBullet bullet) {
    }

    @Override
    public void kill() {
        createSmallExplosion();
    }
}
