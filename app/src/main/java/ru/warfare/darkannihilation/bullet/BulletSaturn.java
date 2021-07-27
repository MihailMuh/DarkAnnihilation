package ru.warfare.darkannihilation.bullet;

import ru.warfare.darkannihilation.base.BaseBullet;
import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.math.Math;

import static ru.warfare.darkannihilation.constant.Constants.BULLET_SATURN_DAMAGE;

public class BulletSaturn extends BaseBullet {
    public BulletSaturn(Game game, int X, int Y) {
        super(game, ImageHub.bulletSaturnImg, X, Y, BULLET_SATURN_DAMAGE);

        speedY = Math.randInt(6, 13);
        speedX = Math.randInt(-6, 6);
        isPassive = true;
    }

    @Override
    public void update() {
        y -= speedY;
        x += speedX;

        if (y < -height | x < -width | x > Game.screenWidth) {
            kill();
        }
    }
}
