package ru.warfare.darkannihilation.bullet;

import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.base.BaseBullet;
import ru.warfare.darkannihilation.systemd.Game;

import static ru.warfare.darkannihilation.constant.Constants.BULLET_SATURN_DAMAGE;
import static ru.warfare.darkannihilation.math.Randomize.randInt;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

public class BulletSaturn extends BaseBullet {
    public BulletSaturn(Game game, int X, int Y) {
        super(game, ImageHub.bulletSaturnImg, X, Y, BULLET_SATURN_DAMAGE);

        speedY = randInt(6, 13);
        speedX = randInt(-6, 6);
    }

    @Override
    public void update() {
        y -= speedY;
        x += speedX;

        if (y < -height | x < -width | x > SCREEN_WIDTH) {
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
