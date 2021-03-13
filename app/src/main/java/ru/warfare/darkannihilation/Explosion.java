package ru.warfare.darkannihilation;

import android.graphics.Bitmap;

public class Explosion extends Sprite {
    private Bitmap[] img = new Bitmap[28];
    private int frame = 0;

    public Explosion(Game g, String size) {
        super(g, ImageHub.explosionImageSmall[0].getWidth(), 0);
        lock = true;
        switch (size)
        {
            case "small":
                img = ImageHub.explosionImageSmall.clone();
                break;
            case "default":
                img = ImageHub.explosionImageDefault.clone();
                width = img[0].getWidth();
                halfWidth = width / 2;
                break;
            case "large":
                img = ImageHub.explosionImageLarge.clone();
                width = img[0].getWidth();
                halfWidth = width / 2;
                break;
        }
    }

    public void start(int X, int Y) {
        x = X - halfWidth;
        y = Y - halfWidth;
        lock = false;
    }

    public void stop() {
        lock = true;
        frame = 0;
    }

    @Override
    public void update() {
        if (!lock) {
            if (frame != 28) {
                frame += 1;
            } else {
                frame = 0;
                lock = true;
            }
        }
    }

    @Override
    public void render () {
        if (!lock & frame != 28) {
            game.canvas.drawBitmap(img[frame], x, y, null);
        }
    }
}
