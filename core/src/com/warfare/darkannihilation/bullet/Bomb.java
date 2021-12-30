package com.warfare.darkannihilation.bullet;

import static com.warfare.darkannihilation.constants.Constants.BOMB_DAMAGE;
import static com.warfare.darkannihilation.constants.Constants.BOMB_SPEED;
import static com.warfare.darkannihilation.systemd.service.Watch.delta;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.abstraction.BaseBullet;
import com.warfare.darkannihilation.utils.PoolWrap;

public class Bomb extends BaseBullet {
    public Bomb(TextureAtlas.AtlasRegion texture, PoolWrap<Explosion> explosionPool) {
        super(texture, BOMB_DAMAGE, explosionPool);
    }

    @Override
    public void boomFromPlayer() {
        explodeSmallTriple();
        reset();
    }

    @Override
    public void update() {
        y -= BOMB_SPEED * delta;
        if (top() <= 0) {
            visible = false;
        }
    }
}
