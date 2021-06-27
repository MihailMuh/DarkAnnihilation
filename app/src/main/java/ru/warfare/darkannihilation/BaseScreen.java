package ru.warfare.darkannihilation;

import android.graphics.Bitmap;

public class BaseScreen extends Sprite {
    public int frame = -1;
    private final Bitmap[] bitmaps;

    public BaseScreen(int w, int h, Bitmap[] bitmaps) {
        super(w, h);

        x = (int) (Game.screenWidth * -0.2);

        this.bitmaps = bitmaps;
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(bitmaps[frame], x, 0, null);
    }
}
