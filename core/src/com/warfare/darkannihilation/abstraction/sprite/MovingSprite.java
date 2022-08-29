package com.warfare.darkannihilation.abstraction.sprite;

import static com.warfare.darkannihilation.constants.Names.HUGE_EXPLOSION;
import static com.warfare.darkannihilation.constants.Names.MEDIUM_EXPLOSION_DEFAULT;
import static com.warfare.darkannihilation.constants.Names.MEDIUM_EXPLOSION_TRIPLE;
import static com.warfare.darkannihilation.constants.Names.SMALL_EXPLOSION_DEFAULT;
import static com.warfare.darkannihilation.constants.Names.SMALL_EXPLOSION_TRIPLE;
import static com.warfare.darkannihilation.hub.Resources.getPools;
import static com.warfare.darkannihilation.hub.Resources.getSounds;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public abstract class MovingSprite extends BaseSprite {
    protected final int maxHealth;
    protected int health;

    public final int damage;
    public final int killScore;

    public float speedX, speedY;

    public MovingSprite(AtlasRegion region, int maxHealth, int damage, int killScore) {
        this(region, region.originalWidth, region.originalHeight, maxHealth, damage, killScore);
    }

    public MovingSprite(AtlasRegion region, int width, int height, int maxHealth, int damage, int killScore) {
        super(region, width, height);

        this.maxHealth = maxHealth;
        this.damage = damage;
        this.killScore = killScore;

        health = maxHealth;
    }

    public void damage(int damage) {
        health -= damage;

        if (isDead()) {
            kill();
        }
    }

    public boolean killedBy(MovingSprite sprite) {
        boolean killed = false;

        if (intersect(sprite)) {
            killed = sprite.damage >= health;

            sprite.damage(damage);
            damage(sprite.damage);
        }
        return killed;
    }

    public final boolean isDead() {
        return health <= 0;
    }

    public abstract void kill();

    protected void explosion(byte type) {
        getPools().explosionPool.obtain(centerX(), centerY(), type);
    }

    protected void explodeSmall() {
        explosion(SMALL_EXPLOSION_DEFAULT);
    }

    protected void explodeSmallTriple() {
        explosion(SMALL_EXPLOSION_TRIPLE);
    }

    protected void explodeDefault() {
        explosion(MEDIUM_EXPLOSION_DEFAULT);
        getSounds().boomSound.play();
    }

    protected void explodeDefaultTriple() {
        explosion(MEDIUM_EXPLOSION_TRIPLE);
        getSounds().boomSound.play();
    }

    protected void explodeHuge() {
        explosion(HUGE_EXPLOSION);
        getSounds().megaBoomSound.play();
    }
}
