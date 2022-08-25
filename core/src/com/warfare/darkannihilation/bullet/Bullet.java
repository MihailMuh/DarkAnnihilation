package com.warfare.darkannihilation.bullet;

import static com.warfare.darkannihilation.constants.Constants.BULLET_DAMAGE;
import static com.warfare.darkannihilation.constants.Constants.BULLET_SPEED;
import static com.warfare.darkannihilation.hub.Resources.getImages;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;

public class Bullet extends BaseBullet {
    public Bullet() {
        super(getImages().bulletImg, BULLET_DAMAGE, BULLET_SPEED);
    }

    @Override
    public void update() {
        translateY(speedY);
    }

    @Override
    public void updateInThread() {
        if (getY() > SCREEN_HEIGHT) {
            visible = false;
        }
    }

    @Override
    public void kill() {
        explodeSmall();
    }
}
