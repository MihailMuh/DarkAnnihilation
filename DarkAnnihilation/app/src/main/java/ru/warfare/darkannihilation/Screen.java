package ru.warfare.darkannihilation;

import android.graphics.Bitmap;

public class Screen extends Sprite {
    private long lastUpdate;
    private static final int frameRate = 25;
    private long now;
    private int frame = -1;
    private static final int screenImageLength = ImageHub.screenImage.length;

    public Screen(Game g) {
        super(g, 0, 0);

        x = (int) (game.screenWidth * -0.2);
        y = 0;

        lastUpdate = System.nanoTime();
    }

    @Override
    public void update() {
        now = System.nanoTime();
        if (now - lastUpdate > frameRate) {
            lastUpdate = now;
            frame += 1;
            if (frame == screenImageLength) {
                frame = 0;
            }
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
