package com.warfare.darkannihilation.abstraction;

import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.utils.PoolWrap;

public abstract class AggressiveSprite extends LiveSprite {
    protected final PoolWrap<Explosion> explosionPool;
    protected final float SHh;
    public final int damage;

    public AggressiveSprite(AtlasRegion texture, int damage, PoolWrap<Explosion> explosionPool) {
        super(texture);
        this.damage = damage;
        this.explosionPool = explosionPool;

        SHh = SCREEN_HEIGHT + height;
    }

    public AggressiveSprite(AtlasRegion texture, int damage, float Y, PoolWrap<Explosion> explosionPool) {
        super(texture);
        this.damage = damage;
        this.explosionPool = explosionPool;

        SHh = Y;
    }

    public boolean damage(int dmg) {
        return false;
    }

    public void boom() {
        explode();
        reset();
    }

    public void boomFromPlayer() {
        explodeSmall();
        reset();
    }

    protected abstract void explode();

    protected void explodeSmall() {
        explosionPool.obtain().start(centerX(), centerY(), true);
    }
}
