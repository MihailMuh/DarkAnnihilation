package com.warfare.darkannihilation.bullet;

import static com.warfare.darkannihilation.Constants.BULLET_DAMAGE;
import static com.warfare.darkannihilation.Constants.BULLET_SPEED;
import static com.warfare.darkannihilation.systemd.service.Watch.delta;

import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.abstraction.BaseBullet;
import com.warfare.darkannihilation.hub.ImageHub;
import com.warfare.darkannihilation.utils.PoolWrap;

public class Bullet extends BaseBullet {
    public Bullet(PoolWrap<Explosion> explosionPool) {
        super(ImageHub.bulletImg, BULLET_DAMAGE, explosionPool);
    }

    @Override
    public void update() {
        y += BULLET_SPEED * delta;

        if (y > SHh) {
            visible = false;
        }
    }
}
