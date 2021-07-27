package ru.warfare.darkannihilation.bullet;

import android.graphics.Bitmap;

import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.base.Sprite;

import static ru.warfare.darkannihilation.constant.Constants.BULLET_BOSS_DAMAGE;
import static ru.warfare.darkannihilation.constant.Constants.BULLET_BOSS_SPEED;
import static ru.warfare.darkannihilation.constant.NamesConst.BULLET_ENEMY;

public class BulletBoss extends Sprite {
    public BulletBoss(Game game, int X, int Y, int type) {
        super(game);
        damage = BULLET_BOSS_DAMAGE;
        name = BULLET_ENEMY;
        isBullet = true;

        switch (type)
        {
            case 1:
                image = ImageHub.laserImage;
                break;
            case 2:
                speedX = 6;
                image = ImageHub.rotateImage(ImageHub.laserImage, 45);
                break;
            case 3:
                speedX = -6;
                image = ImageHub.rotateImage(ImageHub.laserImage, -45);
                break;
        }

        x = X;
        y = Y;

        makeParams();
    }

    @Override
    public Object[] getBox(int a, int b, Bitmap image) {
        return new Object[] {ImageHub.laserImage};
    }

    @Override
    public void intersectionPlayer() {
        createSmallTripleExplosion();
        kill();
    }

    @Override
    public void update() {
        y += BULLET_BOSS_SPEED;
        x -= speedX;

        if (y > Game.screenHeight | x < -height | x > Game.screenWidth) {
            kill();
        }
    }
}
