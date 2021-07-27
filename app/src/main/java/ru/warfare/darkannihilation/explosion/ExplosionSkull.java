package ru.warfare.darkannihilation.explosion;

import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.base.BaseExplosion;

import static ru.warfare.darkannihilation.constant.Constants.NUMBER_SKULL_EXPLOSION;
import static ru.warfare.darkannihilation.constant.Constants.SKULL_EXPLOSION_FRAME_TIME;

public class ExplosionSkull extends BaseExplosion {
    private long lastShoot = System.currentTimeMillis();
    private static final int len = NUMBER_SKULL_EXPLOSION - 1;

    public ExplosionSkull() {
        super(ImageHub.explosionLarge[0]);
    }

    @Override
    public void update() {
        long now = System.currentTimeMillis();
        if (now - lastShoot > SKULL_EXPLOSION_FRAME_TIME) {
            lastShoot = now;
            if (frame != len) {
                frame++;
            } else {
                frame = 0;
                lock = true;
            }
        }
    }

    @Override
    public void render () {
        Game.canvas.drawBitmap(ImageHub.explosionLarge[frame],
                x, y, null);
    }
}
