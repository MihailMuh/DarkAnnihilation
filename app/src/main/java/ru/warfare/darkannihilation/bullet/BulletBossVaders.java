package ru.warfare.darkannihilation.bullet;

import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.base.Sprite;

import static ru.warfare.darkannihilation.constant.Constants.BULLET_BOSS_VADERS_DAMAGE;

public class BulletBossVaders extends Sprite {
    public BulletBossVaders(Game game, int X, int Y, int spdx, int spdy) {
        super(game, ImageHub.bulletBossVadersImg);
        damage = BULLET_BOSS_VADERS_DAMAGE;

        speedX = spdx;
        speedY = spdy;

        x = X - halfWidth;
        y = Y - halfWidth;

        recreateRect(x + 25, y + 25, x + width - 25, y + height - 25);
    }

    @Override
    public Sprite getRect() {
        return newRect(x + 25, y + 25);
    }

    @Override
    public void intersectionPlayer() {
        createSkullExplosion();
        kill();
    }

    @Override
    public void check_intersectionBullet(Sprite bullet) {
        if (intersect(bullet)) {
            bullet.intersection();
        }
    }

    @Override
    public void update() {
        y += speedY;
        x += speedX;

        if (x < -width | x > Game.screenWidth | y > Game.screenHeight | y < -height) {
            kill();
        }
    }
}
