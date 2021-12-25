package com.warfare.darkannihilation.abstraction;

import static com.warfare.darkannihilation.constants.Names.HUGE_EXPLOSION;
import static com.warfare.darkannihilation.constants.Names.MEDIUM_EXPLOSION_DEFAULT;
import static com.warfare.darkannihilation.constants.Names.MEDIUM_EXPLOSION_TRIPLE;
import static com.warfare.darkannihilation.constants.Names.SMALL_EXPLOSION_DEFAULT;
import static com.warfare.darkannihilation.constants.Names.SMALL_EXPLOSION_TRIPLE;
import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.utils.PoolWrap;

public abstract class AggressiveSprite extends LiveSprite {
    protected final PoolWrap<Explosion> explosionPool;
    protected float startY;
    public final int damage;

    public AggressiveSprite(AtlasRegion texture, int damage, PoolWrap<Explosion> explosionPool) {
        super(texture);
        this.damage = damage;
        this.explosionPool = explosionPool;

        startY = SCREEN_HEIGHT + height;
    }

    public AggressiveSprite(AtlasRegion texture, int damage, float startY, PoolWrap<Explosion> explosionPool) {
        super(texture);
        this.damage = damage;
        this.explosionPool = explosionPool;

        this.startY = startY;
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
        explosionPool.obtain().start(centerX(), centerY(), SMALL_EXPLOSION_DEFAULT);
    }
    protected void explodeSmallTriple() {
        explosionPool.obtain().start(centerX(), centerY(), SMALL_EXPLOSION_TRIPLE);
    }
    protected void explodeDefault() {
        explosionPool.obtain().start(centerX(), centerY(), MEDIUM_EXPLOSION_DEFAULT);
    }
    protected void explodeDefaultTriple() {
        explosionPool.obtain().start(centerX(), centerY(), MEDIUM_EXPLOSION_TRIPLE);
    }
    protected void explodeHuge() {
        explosionPool.obtain().start(centerX(), centerY(), HUGE_EXPLOSION);
    }
}
