package ru.warfare.darkannihilation.bullet;

import ru.warfare.darkannihilation.arts.ImageHub;
import ru.warfare.darkannihilation.base.BaseBullet;
import ru.warfare.darkannihilation.systemd.Game;

import static ru.warfare.darkannihilation.constant.Constants.BULLET_DAMAGE;
import static ru.warfare.darkannihilation.constant.Constants.BULLET_SPEED;

public class Bullet extends BaseBullet {
    public Bullet(Game game) {
        super(game, ImageHub.bulletImage, BULLET_DAMAGE);
    }

    @Override
    public void start(int X, int Y) {
        x = X - halfWidth;
        y = Y;
        lock = false;
    }

    @Override
    public void update() {
        y -= BULLET_SPEED;

        if (y < -height) {
            lock = true;
        }
    }

    @Override
    public void kill() {
        super.kill();
        lock = true;
    }
}
