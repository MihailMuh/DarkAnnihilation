package com.warfare.darkannihilation.bullet;

import static com.warfare.darkannihilation.constants.Constants.BULLET_DAMAGE;
import static com.warfare.darkannihilation.constants.Constants.BULLET_SPEED;
import static com.warfare.darkannihilation.hub.Resources.getImages;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;

import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.utils.PoolWrap;

public class Bullet extends BaseBullet {
    private final float borderY;

    public Bullet(PoolWrap<Explosion> explosionPool) {
        super(explosionPool, getImages().bulletImg, BULLET_DAMAGE);

        borderY = SCREEN_HEIGHT + height;
    }

    @Override
    public void update() {
        y += BULLET_SPEED;

        if (y > borderY) {
            visible = false;
        }
    }

    @Override
    public void kill() {
        visible = false;
        explodeSmall();
    }
}
