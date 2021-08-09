package ru.warfare.darkannihilation.explosion;

import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.base.BaseExplosion;
import ru.warfare.darkannihilation.systemd.Game;

import static ru.warfare.darkannihilation.constant.Constants.NUMBER_DEFAULT_EXPLOSION_IMAGES;
import static ru.warfare.darkannihilation.constant.NamesConst.SMALL_EXPLOSION;

public class DefaultExplosion extends BaseExplosion {
    public DefaultExplosion(Game game, byte size) {
        super(game, ImageHub.explosionDefaultImageMedium);

        if (size == SMALL_EXPLOSION) {
            img = ImageHub.explosionDefaultImageSmall;
            image = img[0];

            makeParams();
        }
    }

    @Override
    public void update() {
        frame++;
        if (frame == NUMBER_DEFAULT_EXPLOSION_IMAGES) {
            lock = true;
        }
    }
}