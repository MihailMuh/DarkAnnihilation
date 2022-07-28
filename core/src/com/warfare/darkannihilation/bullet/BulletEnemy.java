package com.warfare.darkannihilation.bullet;

import static com.warfare.darkannihilation.constants.Constants.BULLET_ENEMY_DAMAGE;
import static com.warfare.darkannihilation.constants.Constants.BULLET_ENEMY_SPEED;
import static com.warfare.darkannihilation.constants.Names.BULLET_ENEMY;
import static com.warfare.darkannihilation.hub.Resources.getImages;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

public class BulletEnemy extends BaseBullet {
    private float angle;

    public BulletEnemy() {
        super(getImages().laserImg, BULLET_ENEMY_DAMAGE, BULLET_ENEMY_SPEED);
        name = BULLET_ENEMY;
    }

    public void start(float X, float Y, float cos, float sin, float angle) {
        speedX = BULLET_ENEMY_SPEED * cos;
        speedY = BULLET_ENEMY_SPEED * sin;
        this.angle = angle;

        start(X, Y);
    }

    @Override
    public void kill() {
        explodeSmallTriple();
    }

    @Override
    public void update() {
        x += speedX;
        y += speedY;

        if (x < -width || x > SCREEN_WIDTH || y < -height || y > SCREEN_HEIGHT) {
            visible = false;
        }
    }

    @Override
    public void render() {
        image.draw(x, y, angle);
    }
}
