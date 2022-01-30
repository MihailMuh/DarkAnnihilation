package com.warfare.darkannihilation.bullet;

import static com.warfare.darkannihilation.constants.Constants.BULLET_DAMAGE;
import static com.warfare.darkannihilation.constants.Constants.BULLET_SPEED;
import static com.warfare.darkannihilation.constants.Names.BULLET;
import static com.warfare.darkannihilation.systemd.service.Watch.delta;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.utils.PoolWrap;

public class Bullet extends BaseBullet {
    private final float borderY;

    public Bullet(PoolWrap<Explosion> explosionPool, TextureAtlas.AtlasRegion texture) {
        super(explosionPool, texture, BULLET_DAMAGE);

        borderY = SCREEN_HEIGHT + height;
        name = BULLET;
    }

    @Override
    public void update() {
        y += BULLET_SPEED * delta;

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
