package ru.warfare.darkannihilation.explosion;

import ru.warfare.darkannihilation.arts.ImageHub;
import ru.warfare.darkannihilation.base.BaseExplosion;
import ru.warfare.darkannihilation.systemd.Game;

import static ru.warfare.darkannihilation.constant.Constants.NUMBER_TRIPLE_EXPLOSION_IMAGES;
import static ru.warfare.darkannihilation.constant.Constants.TRIPLE_EXPLOSION_FRAME_TIME;
import static ru.warfare.darkannihilation.constant.NamesConst.SMALL_EXPLOSION;
import static ru.warfare.darkannihilation.systemd.Game.now;

public class ExplosionTriple extends BaseExplosion {
    private long lastShoot = now;

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
        if (now - lastShoot > TRIPLE_EXPLOSION_FRAME_TIME) {
            lastShoot = now;
            frame++;
            if (frame == NUMBER_TRIPLE_EXPLOSION_IMAGES) {
                lock = true;
            }
        }
    }
}
