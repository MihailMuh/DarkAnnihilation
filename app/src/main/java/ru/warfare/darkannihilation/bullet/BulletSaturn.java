package ru.warfare.darkannihilation.bullet;

import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.hub.ImageHub;
import ru.warfare.darkannihilation.base.Sprite;
import ru.warfare.darkannihilation.math.Math;

import static ru.warfare.darkannihilation.Constants.BULLET_SATURN_DAMAGE;

public class BulletSaturn extends Sprite {
    public BulletSaturn(int X, int Y) {
        super(ImageHub.bulletSaturnImg);

        speedY = Math.randInt(6, 13);
        speedX = Math.randInt(-6, 6);
        damage = BULLET_SATURN_DAMAGE;
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
        y -= speedY;
        x += speedX;

        if (y < -height | x < -width | x > Game.screenWidth) {
            Game.bullets.remove(this);
            Game.allSprites.remove(this);
        }
    }
}
