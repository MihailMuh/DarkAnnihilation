package ru.warfare.darkannihilation.bullet;

import static ru.warfare.darkannihilation.constant.Constants.BULLET_ENEMY_DAMAGE;
import static ru.warfare.darkannihilation.constant.NamesConst.BULLET_ENEMY;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import android.graphics.Bitmap;

import ru.warfare.darkannihilation.arts.ImageHub;
import ru.warfare.darkannihilation.base.BaseBullet;
import ru.warfare.darkannihilation.systemd.Game;

public class BulletEnemy extends BaseBullet {
    public BulletEnemy(Game game) {
        super(game, ImageHub.pauseButtonImg, BULLET_ENEMY_DAMAGE);
        name = BULLET_ENEMY;
    }

    @Override
    public void start(int... v) {
        image = ImageHub.rotateBitmap(ImageHub.bulletEnemyImage, v[2]);
        makeParams();

        speedX = v[3];
        speedY = v[4];

        x = v[0] - halfWidth;
        y = v[1];

        lock = false;
    }

    @Override
    public Object[] getBox(Bitmap image) {
        return new Object[]{ImageHub.bulletEnemyImage};
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
        lock = true;
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
