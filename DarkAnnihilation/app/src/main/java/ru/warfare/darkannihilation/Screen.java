package ru.warfare.darkannihilation;

import android.graphics.Bitmap;

public class Screen {
    public int x;
    public int y;
    private long lastUpdate;
    private static final int frameRate = 25;
    private long now;
    private int frame = 0;
    private final Game game;
    private static final int screenImageLength = ImageHub.screen_image.length;

    public Screen(Game g) {
        game = g;

        x = (int) (game.screenWidth * -0.2);
        y = 0;

        lastUpdate = System.nanoTime();
    }

    public void update() {
        now = System.nanoTime();
        if (now - lastUpdate > frameRate) {
            lastUpdate = now;
            if (frame == screenImageLength) {
                frame = 0;
            }
            game.canvas.drawBitmap(ImageHub.screen_image[frame], x, y, null);
            frame += 1;
        }
    }

    public void update(Bitmap img) {
        game.canvas.drawBitmap(img, 0, 0, null);
    }
}
