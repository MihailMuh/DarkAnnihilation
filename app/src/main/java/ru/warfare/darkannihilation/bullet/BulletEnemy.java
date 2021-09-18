package ru.warfare.darkannihilation.bullet;

import android.graphics.Bitmap;

import ru.warfare.darkannihilation.arts.ImageHub;
import ru.warfare.darkannihilation.base.BaseBullet;
import ru.warfare.darkannihilation.systemd.Game;

import static ru.warfare.darkannihilation.constant.Constants.BULLET_ENEMY_DAMAGE;
import static ru.warfare.darkannihilation.constant.NamesConst.BULLET_ENEMY;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

public class BulletEnemy extends BaseBullet {
    public BulletEnemy(Game game, int X, int Y, int angle, int spdx, int spdy) {
        super(game, ImageHub.rotateBitmap(ImageHub.bulletEnemyImage, angle), X, Y, BULLET_ENEMY_DAMAGE);
        name = BULLET_ENEMY;

        speedX = spdx;
        speedY = spdy;
    }

    @Override
    public Object[] getBox(int a, int b, Bitmap image) {
        return new Object[] {ImageHub.bulletEnemyImage};
    }

    @Override
    public void intersectionPlayer() {
        kill();
    }

    @Override
    public void kill() {
        super.kill();
        hide();
    }

    @Override
    public void hide() {
        game.intersectOnlyPlayer.remove(this);
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
