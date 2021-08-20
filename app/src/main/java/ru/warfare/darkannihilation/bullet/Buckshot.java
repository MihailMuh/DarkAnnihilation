package ru.warfare.darkannihilation.bullet;

import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.base.BaseBullet;
import ru.warfare.darkannihilation.systemd.Game;

import static ru.warfare.darkannihilation.constant.Constants.BUCKSHOT_DAMAGE;
import static ru.warfare.darkannihilation.constant.Constants.BUCKSHOT_SPEED;
import static ru.warfare.darkannihilation.constant.NamesConst.SUPER;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

public class Buckshot extends BaseBullet {
    public Buckshot(Game game, int X, int Y, int speed) {
        super(game, ImageHub.buckshotImg, X, Y, BUCKSHOT_DAMAGE);

        speedX = speed;
        power = SUPER;
    }

    @Override
    public void kill() {
        createSmallTripleExplosion();
        hide();
    }

    @Override
    public void hide() {
        game.bullets.remove(this);
    }

    @Override
    public void update() {
        y -= BUCKSHOT_SPEED;
        x += speedX;

        if (y < -height | x < -width | x > SCREEN_WIDTH) {
            hide();
        }
    }
}
