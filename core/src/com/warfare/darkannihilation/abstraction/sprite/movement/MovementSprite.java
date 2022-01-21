package com.warfare.darkannihilation.abstraction.sprite.movement;

import static com.warfare.darkannihilation.constants.Names.HUGE_EXPLOSION;
import static com.warfare.darkannihilation.constants.Names.MEDIUM_EXPLOSION_DEFAULT;
import static com.warfare.darkannihilation.constants.Names.MEDIUM_EXPLOSION_TRIPLE;
import static com.warfare.darkannihilation.constants.Names.SMALL_EXPLOSION_DEFAULT;
import static com.warfare.darkannihilation.constants.Names.SMALL_EXPLOSION_TRIPLE;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.abstraction.BaseBullet;
import com.warfare.darkannihilation.abstraction.sprite.BaseSprite;
import com.warfare.darkannihilation.utils.PoolWrap;

public abstract class MovementSprite extends BaseSprite {
    private final PoolWrap<Explosion> explosionPool;

    protected final int maxHealth;
    protected int health;

    public final int damage;
    public final int killScore;

    public float speedX, speedY;

    public MovementSprite(PoolWrap<Explosion> explosionPool, TextureAtlas.AtlasRegion texture, int maxHealth, int damage, int killScore) {
        super(texture);

        this.maxHealth = maxHealth;
        this.damage = damage;
        this.explosionPool = explosionPool;
        this.killScore = killScore;
    }

    //    damage and check if dead
    protected boolean damage(int dmg) {
        health -= dmg;

        return health <= 0;
    }

    public boolean killedByBullet(BaseBullet bullet) {
        boolean killed = false;
        if (intersect(bullet)) {
            killed = damage(bullet.damage);

            if (killed) {
                kill();
            }
            bullet.kill();
        }
        return killed;
    }

    public void kill() {
        visible = false;
        reset();
    }

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
