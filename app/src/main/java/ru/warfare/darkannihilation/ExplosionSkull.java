package ru.warfare.darkannihilation;

import android.graphics.Bitmap;

public class ExplosionSkull extends BaseExplosion {
    private long lastShoot;

    public ExplosionSkull() {
        super(ImageHub.explosionLarge[0].getWidth(), ImageHub.explosionLarge[0].getHeight());
        lock = true;
        img = new Bitmap[ImageHub.explosionLarge.length];
        img = ImageHub.explosionLarge.clone();

        lastShoot = System.currentTimeMillis();
    }

    @Override
    public void update() {
        long now = System.currentTimeMillis();
        if (now - lastShoot > 33) {
            lastShoot = now;
            if (frame != 12) {
                frame++;
            } else {
                frame = 0;
                lock = true;
            }
        }
    }

    @Override
    public void render () {
        Game.canvas.drawBitmap(img[frame], x, y, null);
    }
}
