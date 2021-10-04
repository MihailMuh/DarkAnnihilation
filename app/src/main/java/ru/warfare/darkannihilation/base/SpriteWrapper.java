package ru.warfare.darkannihilation.base;

import android.graphics.Bitmap;

import ru.warfare.darkannihilation.systemd.Game;

public abstract class SpriteWrapper extends Sprite {
    protected SpriteWrapper(Game game) {
        super(game);
    }

    protected SpriteWrapper(Game g, Bitmap bitmap) {
        super(g, bitmap);
    }

    @Override
    public void update() {
    }

    @Override
    public void kill() {
    }

    @Override
    public void check_intersectionBullet(BaseBullet bullet) {
    }
}
