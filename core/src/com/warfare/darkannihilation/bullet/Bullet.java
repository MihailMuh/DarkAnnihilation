package com.warfare.darkannihilation.bullet;

import static com.warfare.darkannihilation.constants.Constants.BULLET_DAMAGE;
import static com.warfare.darkannihilation.constants.Constants.BULLET_SPEED;
import static com.warfare.darkannihilation.systemd.service.Watch.delta;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.abstraction.BaseBullet;
import com.warfare.darkannihilation.utils.PoolWrap;

public class Bullet extends BaseBullet {
    public Bullet(TextureAtlas.AtlasRegion texture, PoolWrap<Explosion> explosionPool) {
        super(texture, BULLET_DAMAGE, explosionPool);
    }

    @Override
    public void update() {
        y += BULLET_SPEED * delta;

        if (y > startY) {
            visible = false;
        }
    }
}
