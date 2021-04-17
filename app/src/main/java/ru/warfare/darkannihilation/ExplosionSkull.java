package ru.warfare.darkannihilation;

import android.graphics.Bitmap;

public class ExplosionSkull extends Explosion {
    private final int shotgunTime;
    private long lastShoot;
    private long now;

    public ExplosionSkull(Game g) {
        super(g, ImageHub.explosionLarge[0].getWidth(), ImageHub.explosionLarge[0].getHeight());
        lock = true;
        shotgunTime = 27;
        img = new Bitmap[ImageHub.explosionLarge.length];
        img = ImageHub.explosionLarge.clone();

        lastShoot = System.currentTimeMillis();
    }

    @Override
    public void update() {
        now = System.currentTimeMillis();
        if (now - lastShoot > shotgunTime) {
            lastShoot = now;
            if (frame != 13) {
                frame += 1;
            } else {
                frame = 0;
                lock = true;
            }
        }
    }

    @Override
    public void render () {
        if (frame != 13) {
            game.canvas.drawBitmap(img[frame], x, y, null);
        }
    }
}
