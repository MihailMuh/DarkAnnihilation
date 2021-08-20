package ru.warfare.darkannihilation.base;

import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import ru.warfare.darkannihilation.systemd.Game;

public abstract class BaseScreen extends Sprite {
    public int frame = 0;
    private final Bitmap[] bitmaps;

    public BaseScreen(@NonNull Bitmap[] bitmaps) {
        super(null, bitmaps[0]);

        x = (int) (SCREEN_WIDTH * -0.175);

        this.bitmaps = bitmaps;
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(bitmaps[frame], x, 0, null);
    }

    @Override
    public void kill() {
    }

    @Override
    public void check_intersectionBullet(BaseBullet bullet) {
    }

    @Override
    public void start() {
    }
}
