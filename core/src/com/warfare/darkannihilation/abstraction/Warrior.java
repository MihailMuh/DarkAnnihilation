package com.warfare.darkannihilation.abstraction;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.warfare.darkannihilation.Explosion;
import com.warfare.darkannihilation.utils.PoolWrap;

public abstract class Warrior extends AggressiveSprite {
    protected final int maxHealth;
    protected int health;
    public final int killScore;

    public Warrior(AtlasRegion texture, int maxHealth, int damage, int killSCore, PoolWrap<Explosion> explosionPool) {
        super(texture, damage, explosionPool);
        this.maxHealth = maxHealth;
        this.killScore = killSCore;

        reset();
    }

    public Warrior(AtlasRegion texture, int maxHealth, int damage, float startY, int killSCore, PoolWrap<Explosion> explosionPool) {
        super(texture, damage, startY, explosionPool);
        this.maxHealth = maxHealth;
        this.killScore = killSCore;

        reset();
    }

    @Override
    public void reset() {
        health = maxHealth;

        y = startY;
    }

    @Override
    public boolean damage(int dmg) {
        health -= dmg;

        if (health <= 0) {
            boom();
            return true;
        }
        return false;
    }

    public boolean collidedWithBullet(BaseBullet bullet) {
        boolean collide = false;
        if (intersect(bullet)) {
            collide = damage(bullet.damage);
            bullet.boom();
        }
        return collide;
    }
}
