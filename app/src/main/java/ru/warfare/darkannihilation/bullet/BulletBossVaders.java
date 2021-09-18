package ru.warfare.darkannihilation.bullet;

import ru.warfare.darkannihilation.base.BaseBullet;
import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.arts.ImageHub;
import ru.warfare.darkannihilation.base.Sprite;

import static ru.warfare.darkannihilation.constant.Constants.BULLET_BOSS_VADERS_DAMAGE;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

public class BulletBossVaders extends BaseBullet {
    public BulletBossVaders(Game game, int X, int Y, int spdx, int spdy) {
        super(game, ImageHub.bulletBossVadersImg, X, Y, BULLET_BOSS_VADERS_DAMAGE);

        speedX = spdx;
        speedY = spdy;

        y = Y - halfWidth;

        recreateRect(x + 25, y + 25, x + width - 25, y + height - 25);
    }

    @Override
    public Sprite getRect() {
        return newRect(x + 25, y + 25);
    }

    @Override
    public void intersectionPlayer() {
        kill();
    }

    @Override
    public void check_intersectionBullet(BaseBullet bullet) {
        if (intersect(bullet)) {
            bullet.kill();
        }
    }

    @Override
    public void kill() {
        createSkullExplosion();
        hide();
    }

    @Override
    public void hide() {
        game.enemies.remove(this);
    }

    @Override
    public void update() {
        y += speedY;
        x += speedX;

        if (x < -width || x > SCREEN_WIDTH || y > SCREEN_HEIGHT || y < -height) {
            hide();
        }
    }
}
