package ru.warfare.darkannihilation.explosion;

import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.base.BaseExplosion;

import static ru.warfare.darkannihilation.constant.Constants.NUMBER_DEFAULT_EXPLOSION_IMAGES;

public class DefaultExplosion extends BaseExplosion {
    public DefaultExplosion(String size) {
        super(ImageHub.explosionDefaultImageSmall[0]);

        switch (size)
        {
            case "small":
                img = ImageHub.explosionDefaultImageSmall;
                break;
            case "default":
                img = ImageHub.explosionDefaultImageMedium;
                width = img[0].getWidth();
                halfWidth = width / 2;
                height = img[0].getHeight();
                halfHeight = height / 2;
                break;
        }
    }

    @Override
    public void update() {
        if (frame != NUMBER_DEFAULT_EXPLOSION_IMAGES) {
            frame++;
        } else {
            frame = 0;
            lock = true;
        }
    }

    @Override
    public void render () {
        if (frame != NUMBER_DEFAULT_EXPLOSION_IMAGES) {
            Game.canvas.drawBitmap(img[frame], x, y, null);
        }
    }
}