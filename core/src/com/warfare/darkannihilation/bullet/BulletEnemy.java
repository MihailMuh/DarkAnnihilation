package com.warfare.darkannihilation.bullet;

import static com.warfare.darkannihilation.constants.Constants.BULLET_ENEMY_DAMAGE;
import static com.warfare.darkannihilation.constants.Constants.BULLET_ENEMY_SPEED;
import static com.warfare.darkannihilation.constants.Names.BULLET_ENEMY;
import static com.warfare.darkannihilation.hub.Resources.getImages;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

public class BulletEnemy extends BaseBullet {
    public BulletEnemy() {
        super(getImages().laserImg, BULLET_ENEMY_DAMAGE, BULLET_ENEMY_SPEED);
        name = BULLET_ENEMY;
    }

    public void start(float X, float Y, float cos, float sin, float angle) {
        speedX = BULLET_ENEMY_SPEED * cos;
        speedY = BULLET_ENEMY_SPEED * sin;

        setRotation(angle);
        start(X, Y);
    }

    @Override
    public void kill() {
        explodeSmallTriple();
    }

    @Override
    public void update() {
        translate(speedX, speedY);
    }

    @Override
    public void updateInThread() {
        if (getX() < -getWidth() || getX() > SCREEN_WIDTH || getY() < -getHeight() || getY() > SCREEN_HEIGHT) {
            visible = false;
        }
    }
}
