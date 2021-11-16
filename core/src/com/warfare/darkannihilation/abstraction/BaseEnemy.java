package com.warfare.darkannihilation.abstraction;

import static com.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public abstract class BaseEnemy extends Sprite {
    protected int health, maxHealth;
    protected float speedX, speedY;
    protected float customY;

    public BaseEnemy(AtlasRegion texture, int damage, int health, boolean extra) {
        super(texture, damage);

        maxHealth = health;
        if (extra) {
            customY = SCREEN_HEIGHT + height;
        }

        reset();
    }

    protected void reset() {
        health = maxHealth;

        y = customY;
    }

    public void collidesWithBullet() {

    }

    protected void shoot() {

    }

    @Override
    public void render() {
        if (visible) {
            super.render();
        }
    }
}
