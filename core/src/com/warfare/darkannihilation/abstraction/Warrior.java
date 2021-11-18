package com.warfare.darkannihilation.abstraction;

import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public abstract class Warrior extends AggressiveSprite {
    protected final float startY;

    protected final int maxHealth;
    private int health;

    public Warrior(TextureAtlas.AtlasRegion texture, int maxHealth, int damage) {
        super(texture, damage);
        this.maxHealth = maxHealth;

        startY = SCREEN_HEIGHT + height;

        reset();
    }

    public Warrior(TextureAtlas.AtlasRegion texture, int maxHealth, int damage, float Y) {
        super(texture, damage);
        this.maxHealth = maxHealth;

        startY = Y;

        reset();
    }

    @Override
    public void reset() {
        health = maxHealth;

        y = startY;
    }

    @Override
    public void damage(int dmg) {
        health -= dmg;

        if (health <= 0) {
            boom();
        }
    }
}
