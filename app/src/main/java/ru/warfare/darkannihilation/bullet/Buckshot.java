package ru.warfare.darkannihilation.bullet;

import ru.warfare.darkannihilation.arts.ImageHub;
import ru.warfare.darkannihilation.base.BaseBullet;
import ru.warfare.darkannihilation.systemd.Game;

import static ru.warfare.darkannihilation.constant.Constants.BUCKSHOT_DAMAGE;
import static ru.warfare.darkannihilation.constant.Constants.BUCKSHOT_SPEED;
import static ru.warfare.darkannihilation.constant.NamesConst.SUPER;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

public class Buckshot extends BaseBullet {
    public Buckshot(Game game) {
        super(game, ImageHub.buckshotImg, BUCKSHOT_DAMAGE);
        power = SUPER;
    }

    @Override
    public void start(int X, int Y, int Z) {
        x = X - halfWidth;
        y = Y;
        speedX = Z;
        lock = false;
    }

    @Override
    public void kill() {
        createSmallTripleExplosion();
        lock = true;
    }

    @Override
    public void update() {
        y -= BUCKSHOT_SPEED;
        x += speedX;

        if (y < -height || x < -width || x > SCREEN_WIDTH) {
            lock = true;
        }
    }
}
