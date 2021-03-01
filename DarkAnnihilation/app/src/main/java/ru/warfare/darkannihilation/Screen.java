package ru.warfare.darkannihilation;

import android.graphics.Bitmap;

public class Screen {
    public int x;
    public int y;
    private long lastUpdate;
    private static final int frameRate = 25;
    private long now;
    private int frame = -1;
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
            frame += 1;
            if (frame == screenImageLength) {
                frame = 0;
            }
        }
    }

    public void render() {
        game.canvas.drawBitmap(ImageHub.screen_image[frame], x, y, null);
    }

    public void render(Bitmap img) {
        game.canvas.drawBitmap(img, 0, 0, null);
    }
}
