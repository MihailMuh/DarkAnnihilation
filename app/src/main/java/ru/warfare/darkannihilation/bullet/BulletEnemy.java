package ru.warfare.darkannihilation.bullet;

import android.graphics.Bitmap;

import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.base.Sprite;

import static ru.warfare.darkannihilation.Constants.BULLET_ENEMY_DAMAGE;

public class BulletEnemy extends Sprite {
    public BulletEnemy(int X, int Y, int angle, int spdx, int spdy) {
        super(ImageHub.rotateImage(ImageHub.bulletEnemyImage, angle));
        damage = BULLET_ENEMY_DAMAGE;
        status = "bulletEnemy";
        isBullet = true;

        speedX = spdx;
        speedY = spdy;

        x = X;
        y = Y;
    }

    @Override
    public Object[] getBox(int a, int b, Bitmap image) {
        return new Object[] {ImageHub.bulletEnemyImage};
    }

    @Override
    public void intersection() {
        intersectionPlayer();
    }

    @Override
    public void intersectionPlayer() {
        createSmallExplosion();
        Game.allSprites.remove(this);
    }

    @Override
    public void update() {
        y += speedY;
        x += speedX;

        if (x < -width | x > Game.screenWidth | y > Game.screenHeight | y < -height) {
            Game.allSprites.remove(this);
        }
    }
}
