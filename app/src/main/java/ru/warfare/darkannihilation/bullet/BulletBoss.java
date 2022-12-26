package ru.warfare.darkannihilation.bullet;

import android.graphics.Bitmap;

import ru.warfare.darkannihilation.base.BaseBullet;
import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.arts.ImageHub;

import static ru.warfare.darkannihilation.constant.Constants.BULLET_BOSS_DAMAGE;
import static ru.warfare.darkannihilation.constant.Constants.BULLET_BOSS_SPEED;
import static ru.warfare.darkannihilation.constant.NamesConst.BULLET_ENEMY;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

public class BulletBoss extends BaseBullet {
    public BulletBoss(Game game) {
        super(game, BULLET_BOSS_DAMAGE);
        name = BULLET_ENEMY;
    }

    @Override
    public void start(int X, int Y, int Z) {
        x = X - halfWidth;
        y = Y;

        speedX = 0;

        switch (Z)
        {
            case 1:
                image = ImageHub.laserImage;
                break;
            case 2:
                speedX = 6;
                image = ImageHub.rotateBitmap(ImageHub.laserImage, 45);
                break;
            case 3:
                speedX = -6;
                image = ImageHub.rotateBitmap(ImageHub.laserImage, -45);
                break;
        }

        makeParams();

        lock = false;
    }

    @Override
    public Object[] getBox(Bitmap image) {
        return new Object[] {ImageHub.laserImage};
    }

    @Override
    public void intersectionPlayer() {
        kill();
    }

    @Override
    public void kill() {
        createSmallTripleExplosion();
        hide();
    }

    @Override
    public void hide() {
        lock = true;
    }

    @Override
    public void update() {
        y += BULLET_BOSS_SPEED;
        x -= speedX;

        if (y > SCREEN_HEIGHT || x < -height || x > SCREEN_WIDTH) {
            hide();
        }
    }
}
