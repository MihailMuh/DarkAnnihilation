package com.warfare.darkannihilation.abstraction;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public abstract class Warrior extends AggressiveSprite {
    protected final int maxHealth;
    private int health;

    public Warrior(AtlasRegion texture, int maxHealth, int damage) {
        super(texture, damage);
        this.maxHealth = maxHealth;

        reset();
    }

    public Warrior(AtlasRegion texture, int maxHealth, int damage, float Y) {
        super(texture, damage, Y);
        this.maxHealth = maxHealth;

        reset();
    }

    @Override
    public void reset() {
        health = maxHealth;

        y = SHh;
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

    public boolean collidesWithBullet(BaseBullet bullet) {
        if (intersect(bullet)) {
            bullet.boom();
            return damage(bullet.damage);
        }
        return false;
    }
}
