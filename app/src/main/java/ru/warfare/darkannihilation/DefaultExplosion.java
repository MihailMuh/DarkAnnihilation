package ru.warfare.darkannihilation;

import android.graphics.Bitmap;

public class DefaultExplosion extends BaseExplosion {
    public DefaultExplosion(Game g, String size) {
        super(g, ImageHub.explosionDefaultImageSmall[0].getWidth(), ImageHub.explosionDefaultImageSmall[0].getHeight());
        lock = true;
        img = new Bitmap[ImageHub.explosionDefaultImageSmall.length];

        switch (size)
        {
            case "small":
                img = ImageHub.explosionDefaultImageSmall.clone();
                break;
            case "default":
                img = ImageHub.explosionDefaultImageMedium.clone();
                width = img[0].getWidth();
                halfWidth = width / 2;
                height = img[0].getHeight();
                halfHeight = height / 2;
                break;
        }
    }

    @Override
    public void update() {
        if (frame != 28) {
            frame += 1;
        } else {
            frame = 0;
            lock = true;
        }
    }

    @Override
    public void render () {
        if (frame != 28) {
            game.canvas.drawBitmap(img[frame], x, y, null);
        }
    }
}