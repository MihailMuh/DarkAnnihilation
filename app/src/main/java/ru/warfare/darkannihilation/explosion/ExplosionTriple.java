package ru.warfare.darkannihilation.explosion;

import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.base.BaseExplosion;
import ru.warfare.darkannihilation.systemd.Game;

import static ru.warfare.darkannihilation.constant.Constants.NUMBER_TRIPLE_EXPLOSION_IMAGES;
import static ru.warfare.darkannihilation.constant.Constants.TRIPLE_EXPLOSION_FRAME_TIME;
import static ru.warfare.darkannihilation.constant.NamesConst.SMALL_EXPLOSION;

public class ExplosionTriple extends BaseExplosion {
    private long lastShoot = System.currentTimeMillis();

    public ExplosionTriple(Game game, byte size) {
        super(game, ImageHub.explosionTripleImageMedium);

        if (size == SMALL_EXPLOSION) {
            img = ImageHub.explosionTripleImageSmall;
            image = img[0];

            makeParams();
        }
    }

    @Override
    public void update() {
        long now = System.currentTimeMillis();
        if (now - lastShoot > TRIPLE_EXPLOSION_FRAME_TIME) {
            lastShoot = now;
            frame++;
            if (frame == NUMBER_TRIPLE_EXPLOSION_IMAGES) {
                lock = true;
            }
        }
    }
}
