package ru.warfare.darkannihilation;

import android.graphics.Bitmap;

public class Screen extends Sprite {
    private int frame = -1;
    private static final int screenImageLength = ImageHub.screenImage.length;

    public Screen(Game g) {
        super(g, ImageHub.screenImage[0].getWidth(), ImageHub.screenImage[0].getHeight());

        x = (int) (game.screenWidth * -0.2);
    }

    @Override
    public void update() {
        frame += 1;
        if (frame == screenImageLength) {
            frame = 0;
        }
    }

    @Override
    public void render() {
        game.canvas.drawBitmap(ImageHub.screenImage[frame], x, y, null);
    }

    public void render(Bitmap img) {
        game.canvas.drawBitmap(img, 0, 0, null);
    }
}
