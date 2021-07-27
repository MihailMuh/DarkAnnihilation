package ru.warfare.darkannihilation.base;

import android.graphics.Bitmap;

import ru.warfare.darkannihilation.systemd.Game;

public class BaseBullet extends Sprite {
    public BaseBullet(Game game, Bitmap bitmap, int X, int Y, int dmg) {
        super(game, bitmap);
        damage = dmg;
        isBullet = true;

        x = X - halfWidth;
        y = Y;
    }

    @Override
    public void kill() {
        super.kill();
        game.bullets.remove(this);
    }

    @Override
    public void intersection() {
        createSmallExplosion();
        kill();
    }
}
