package ru.warfare.darkannihilation.bullet;

import ru.warfare.darkannihilation.arts.ImageHub;
import ru.warfare.darkannihilation.base.BaseBullet;
import ru.warfare.darkannihilation.systemd.Game;

import static ru.warfare.darkannihilation.constant.Constants.BULLET_SATURN_DAMAGE;
import static ru.warfare.darkannihilation.math.Randomize.randInt;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

public class BulletSaturn extends BaseBullet {
    public BulletSaturn(Game game) {
        super(game, ImageHub.bulletSaturnImg, BULLET_SATURN_DAMAGE);
    }

    @Override
    public void start(int X, int Y) {
        x = X - halfWidth;
        y = Y;
        speedY = randInt(6, 13);
        speedX = randInt(-6, 6);
        lock = false;
    }

    @Override
    public void update() {
        y -= speedY;
        x += speedX;

        if (y < -height || x < -width || x > SCREEN_WIDTH) {
            lock = true;
        }
    }

    @Override
    public void kill() {
        super.kill();
        lock = true;
    }
}
