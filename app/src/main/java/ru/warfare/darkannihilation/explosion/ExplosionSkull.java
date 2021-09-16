package ru.warfare.darkannihilation.explosion;

import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.base.BaseExplosion;
import ru.warfare.darkannihilation.systemd.Game;

import static ru.warfare.darkannihilation.constant.Constants.NUMBER_SKULL_EXPLOSION_IMAGES;
import static ru.warfare.darkannihilation.constant.Constants.SKULL_EXPLOSION_FRAME_TIME;
import static ru.warfare.darkannihilation.systemd.Game.now;

public class ExplosionSkull extends BaseExplosion {
    private long lastShoot = now;
    
    public ExplosionSkull(Game game) {
        super(game, ImageHub.explosionLarge);
    }

    @Override
    public void update() {
        if (now - lastShoot > SKULL_EXPLOSION_FRAME_TIME) {
            lastShoot = now;
            frame++;
            if (frame == NUMBER_SKULL_EXPLOSION_IMAGES) {
                lock = true;
            }
        }
    }
}
