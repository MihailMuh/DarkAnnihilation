package ru.warfare.darkannihilation.bullet;

import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.base.Sprite;

import static ru.warfare.darkannihilation.Constants.BULLET_DAMAGE;
import static ru.warfare.darkannihilation.Constants.BULLET_SPEED;

public class Bullet extends Sprite {
    public Bullet(int X, int Y) {
        super(ImageHub.bulletImage);
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
}
