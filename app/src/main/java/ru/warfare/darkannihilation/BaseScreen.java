package ru.warfare.darkannihilation;

import android.graphics.Bitmap;

public class BaseScreen extends Sprite {
    public int frame = -1;
    private final Bitmap[] bitmaps;

    public BaseScreen(Bitmap[] bitmaps) {
        super(bitmaps[0].getWidth(), bitmaps[0].getHeight());

        x = (int) (Game.screenWidth * -0.175);

        this.bitmaps = bitmaps;
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(bitmaps[frame], x, 0, null);
    }
}
