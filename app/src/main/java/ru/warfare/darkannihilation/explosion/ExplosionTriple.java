package ru.warfare.darkannihilation.explosion;

import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.base.BaseExplosion;

import static ru.warfare.darkannihilation.Constants.NUMBER_TRIPLE_EXPLOSION_IMAGES;
import static ru.warfare.darkannihilation.Constants.TRIPLE_EXPLOSION_FRAME_TIME;

public class ExplosionTriple extends BaseExplosion {
    private long lastShoot = System.currentTimeMillis();

    public ExplosionTriple(String size) {
        super(ImageHub.explosionTripleImageSmall[0]);
        switch (size)
        {
            case "small":
                img = ImageHub.explosionTripleImageSmall;
                break;
            case "default":
                img = ImageHub.explosionTripleImageMedium;
                width = img[0].getWidth();
                halfWidth = width / 2;
                height = img[0].getHeight();
                halfHeight = height / 2;
                break;
        }
    }

    @Override
    public void update() {
        long now = System.currentTimeMillis();
        if (now - lastShoot > TRIPLE_EXPLOSION_FRAME_TIME) {
            lastShoot = now;
            if (frame != NUMBER_TRIPLE_EXPLOSION_IMAGES) {
                frame++;
            } else {
                frame = 0;
                lock = true;
            }
        }
    }

    @Override
    public void render () {
        if (frame != NUMBER_TRIPLE_EXPLOSION_IMAGES) {
            Game.canvas.drawBitmap(img[frame], x, y, null);
        }
    }
}
