package com.warfare.darkannihilation.bullet;

import static com.warfare.darkannihilation.constants.Constants.BULLET_DAMAGE;
import static com.warfare.darkannihilation.constants.Constants.BULLET_SPEED;
import static com.warfare.darkannihilation.hub.Resources.getImages;

public class Bullet extends BaseBullet {
    public Bullet() {
        super(getImages().bulletImg, BULLET_DAMAGE);
    }

    @Override
    public void update() {
        y += BULLET_SPEED;

        if (y > topY) {
            visible = false;
        }
    }

    @Override
    public void kill() {
        explodeSmall();
    }
}
