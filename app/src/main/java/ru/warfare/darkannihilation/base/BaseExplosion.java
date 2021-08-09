package ru.warfare.darkannihilation.base;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import ru.warfare.darkannihilation.systemd.Game;

public abstract class BaseExplosion extends Sprite {
    public Bitmap[] img;
    public int frame;

    public BaseExplosion(Game game, @NonNull Bitmap[] bitmap) {
        super(game, bitmap[0]);
        img = bitmap;
        lock = true;
    }

    public void start(int X, int Y) {
        x = X - halfWidth;
        y = Y - halfHeight;
        frame = 0;
        lock = false;
    }

    @Override
    public void kill() {
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(img[frame], x, y, null);
    }
}
