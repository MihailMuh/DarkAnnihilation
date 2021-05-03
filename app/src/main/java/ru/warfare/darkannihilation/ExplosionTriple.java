package ru.warfare.darkannihilation;

import android.graphics.Bitmap;

public class ExplosionTriple extends BaseExplosion {
    private final int shotgunTime;
    private long lastShoot;

    public ExplosionTriple(Game g, String size) {
        super(g, ImageHub.explosionTripleImageSmall[0].getWidth(), ImageHub.explosionTripleImageSmall[0].getHeight());
        lock = true;
        shotgunTime = 30;
        img = new Bitmap[ImageHub.explosionTripleImageSmall.length];

        switch (size)
        {
            case "small":
                img = ImageHub.explosionTripleImageSmall.clone();
                break;
            case "default":
                img = ImageHub.explosionTripleImageMedium.clone();
                width = img[0].getWidth();
                halfWidth = width / 2;
                height = img[0].getHeight();
                halfHeight = height / 2;
                break;
        }
        lastShoot = System.currentTimeMillis();
    }

    @Override
    public void update() {
        long now = System.currentTimeMillis();
        if (now - lastShoot > shotgunTime) {
            lastShoot = now;
            if (frame != 23) {
                frame += 1;
            } else {
                frame = 0;
                lock = true;
            }
        }
    }

    @Override
    public void render () {
        if (frame != 23) {
            game.canvas.drawBitmap(img[frame], x, y, null);
        }
    }
}
