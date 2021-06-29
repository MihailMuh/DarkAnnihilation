package ru.warfare.darkannihilation;

import static ru.warfare.darkannihilation.Constants.BULLET_DAMAGE;
import static ru.warfare.darkannihilation.Constants.BULLET_SPEED;

public class Bullet extends Sprite {
    public Bullet(int X, int Y) {
        super(ImageHub.bulletImage.getWidth(), ImageHub.bulletImage.getHeight());
        damage = BULLET_DAMAGE;
        isPassive = true;
        isBullet = true;

        x = X;
        y = Y;
    }

    @Override
    public void intersection() {
        createSmallExplosion();
        Game.bullets.remove(this);
        Game.allSprites.remove(this);
    }

    @Override
    public void update() {
        y -= BULLET_SPEED;

        if (y < -height) {
            Game.bullets.remove(this);
            Game.allSprites.remove(this);
        }
    }

    @Override
    public void render () {
        Game.canvas.drawBitmap(ImageHub.bulletImage, x, y, null);
    }
}
