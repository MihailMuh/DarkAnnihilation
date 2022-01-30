package com.warfare.darkannihilation.bullet;

import static com.warfare.darkannihilation.constants.Constants.BOMB_DAMAGE;
import static com.warfare.darkannihilation.constants.Constants.BOMB_SPEED;
import static com.warfare.darkannihilation.constants.Names.BOMB;
import static com.warfare.darkannihilation.systemd.service.Watch.delta;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.utils.PoolWrap;

public class Bomb extends BaseBullet {
    public Bomb(PoolWrap<Explosion> explosionPool, TextureAtlas.AtlasRegion texture) {
        super(explosionPool, texture, BOMB_DAMAGE);

        name = BOMB;
    }

    @Override
    public void update() {
        y -= BOMB_SPEED * delta;

        if (y < -height) visible = false;
    }

    @Override
    public void reset() {
        explodeSmallTriple();
        visible = false;
    }
}
