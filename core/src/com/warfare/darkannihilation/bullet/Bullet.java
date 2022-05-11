package com.warfare.darkannihilation.bullet;

import static com.warfare.darkannihilation.constants.Constants.BULLET_DAMAGE;
import static com.warfare.darkannihilation.constants.Constants.BULLET_SPEED;
import static com.warfare.darkannihilation.hub.Resources.getImages;

public class Bullet extends BaseBullet {
    public Bullet() {
        super(getImages().bulletImg, BULLET_DAMAGE, BULLET_SPEED);
    }

    @Override
    public void update() {
        y += speedY;

        if (y > topY) {
            visible = false;
        }
    }

    @Override
    public void kill() {
        explodeSmall();
    }
}
