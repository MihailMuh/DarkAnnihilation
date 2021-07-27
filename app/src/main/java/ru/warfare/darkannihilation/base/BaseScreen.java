package ru.warfare.darkannihilation.base;

import android.graphics.Bitmap;

import ru.warfare.darkannihilation.systemd.Game;

public class BaseScreen extends Sprite {
    public int frame = -1;
    private final Bitmap[] bitmaps;

    public BaseScreen(Bitmap[] bitmaps) {
        super(null, bitmaps[0]);

        x = (int) (Game.screenWidth * -0.175);

        this.bitmaps = bitmaps;
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(bitmaps[frame], x, 0, null);
    }
}
