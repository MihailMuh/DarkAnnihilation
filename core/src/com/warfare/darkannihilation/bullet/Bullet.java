package com.warfare.darkannihilation.bullet;

import static com.warfare.darkannihilation.Constants.BULLET_DAMAGE;
import static com.warfare.darkannihilation.Constants.BULLET_SPEED;
import static com.warfare.darkannihilation.systemd.service.Watch.delta;

import com.warfare.darkannihilation.abstraction.BaseBullet;
import com.warfare.darkannihilation.hub.ImageHub;

public class Bullet extends BaseBullet {
    public Bullet() {
        super(ImageHub.bulletImg, BULLET_DAMAGE);
    }

    @Override
    protected void explode() {

    }

    @Override
    public void update() {
        y += BULLET_SPEED * delta;

        if (y > SHh) {
            visible = false;
        }
    }
}
