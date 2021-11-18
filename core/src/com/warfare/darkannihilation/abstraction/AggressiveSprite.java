package com.warfare.darkannihilation.abstraction;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public abstract class AggressiveSprite extends LiveSprite {
    public final int damage;

    public AggressiveSprite(TextureAtlas.AtlasRegion texture, int damage) {
        super(texture);
        this.damage = damage;
    }

    public void damage(int dmg) {

    }

    public void boom() {
        reset();
    }
}
