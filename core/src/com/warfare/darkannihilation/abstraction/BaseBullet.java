package com.warfare.darkannihilation.abstraction;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.utils.PoolWrap;

public abstract class BaseBullet extends AggressiveSprite {
    public BaseBullet(AtlasRegion texture, int damage, PoolWrap<Explosion> explosionPool) {
        super(texture, damage, explosionPool);

        visible = false;
    }

    @Override
    protected void explode() {
        explodeSmall();
    }

    public void start(float X, float Y) {
        x = X - halfWidth;
        y = Y - halfHeight;

        visible = true;
    }

    @Override
    public void reset() {
        visible = false;
    }
}
