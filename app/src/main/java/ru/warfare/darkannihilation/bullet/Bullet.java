package ru.warfare.darkannihilation.bullet;

import ru.warfare.darkannihilation.arts.ImageHub;
import ru.warfare.darkannihilation.base.BaseBullet;
import ru.warfare.darkannihilation.systemd.Game;

import static ru.warfare.darkannihilation.constant.Constants.BULLET_DAMAGE;
import static ru.warfare.darkannihilation.constant.Constants.BULLET_SPEED;

public class Bullet extends BaseBullet {
    public Bullet(Game game, int X, int Y) {
        super(game, ImageHub.bulletImage, X, Y, BULLET_DAMAGE);
    }

    @Override
    public void update() {
        y -= BULLET_SPEED;

        if (y < -height) {
            hide();
        }
    }

    @Override
    public void hide() {
        game.bullets.remove(this);
    }

    @Override
    public void kill() {
        super.kill();
        hide();
    }
}
