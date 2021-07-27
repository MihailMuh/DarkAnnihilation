package ru.warfare.darkannihilation.bullet;

import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.base.BaseBullet;
import ru.warfare.darkannihilation.systemd.Game;

import static ru.warfare.darkannihilation.constant.Constants.BUCKSHOT_DAMAGE;
import static ru.warfare.darkannihilation.constant.Constants.BUCKSHOT_SPEED;

public class Buckshot extends BaseBullet {
    public Buckshot(Game game, int X, int Y, int speed) {
        super(game, ImageHub.buckshotImg, X, Y, BUCKSHOT_DAMAGE);
        isPassive = true;

        speedX = speed;
    }

    @Override
    public void intersection() {
        createSmallTripleExplosion();
        kill();
    }

    @Override
    public void update() {
        y -= BUCKSHOT_SPEED;
        x += speedX;

        if (y < -height | x < -width | x > Game.screenWidth) {
            kill();
        }
    }
}
