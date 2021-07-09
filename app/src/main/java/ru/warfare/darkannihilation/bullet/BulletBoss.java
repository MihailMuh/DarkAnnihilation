package ru.warfare.darkannihilation.bullet;

import android.graphics.Bitmap;

import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.hub.ImageHub;
import ru.warfare.darkannihilation.base.Sprite;

import static ru.warfare.darkannihilation.Constants.BULLET_BOSS_DAMAGE;
import static ru.warfare.darkannihilation.Constants.BULLET_BOSS_SPEED;

public class BulletBoss extends Sprite {
    public BulletBoss(int X, int Y, int type) {
        super();
        damage = BULLET_BOSS_DAMAGE;
        status = "bulletEnemy";
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
        Game.allSprites.remove(this);
    }

    @Override
    public void update() {
        y += BULLET_BOSS_SPEED;
        x -= speedX;

        if (y > Game.screenHeight | x < -height | x > Game.screenWidth) {
            Game.allSprites.remove(this);
        }
    }
}
