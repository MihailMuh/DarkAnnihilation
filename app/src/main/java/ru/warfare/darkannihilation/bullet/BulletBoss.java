package ru.warfare.darkannihilation.bullet;

import android.graphics.Bitmap;

import ru.warfare.darkannihilation.base.BaseBullet;
import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.ImageHub;

import static ru.warfare.darkannihilation.constant.Constants.BULLET_BOSS_DAMAGE;
import static ru.warfare.darkannihilation.constant.Constants.BULLET_BOSS_SPEED;
import static ru.warfare.darkannihilation.constant.NamesConst.BULLET_ENEMY;

public class BulletBoss extends BaseBullet {
    public BulletBoss(Game game, int X, int Y, int type) {
        super(game, ImageHub.laserImage, X, Y, BULLET_BOSS_DAMAGE);
        name = BULLET_ENEMY;

        switch (type)
        {
            case 2:
                speedX = 6;
                image = ImageHub.rotateImage(ImageHub.laserImage, 45);
                break;
            case 3:
                speedX = -6;
                image = ImageHub.rotateImage(ImageHub.laserImage, -45);
                break;
        }

        makeParams();
    }

    @Override
    public Object[] getBox(int a, int b, Bitmap image) {
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
        game.intersectOnlyPlayer.remove(this);
    }

    @Override
    public void update() {
        y += BULLET_BOSS_SPEED;
        x -= speedX;

        if (y > Game.screenHeight | x < -height | x > Game.screenWidth) {
            hide();
        }
    }
}
