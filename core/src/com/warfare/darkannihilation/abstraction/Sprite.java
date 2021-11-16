package com.warfare.darkannihilation.abstraction;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class Sprite extends BaseSprite {
    public boolean visible = true;
    public final int damage;

    public Sprite(AtlasRegion texture, float x, float y, int width, int height, int damage) {
        super(texture, x, y, width, height);
        this.damage = damage;
    }

    public Sprite(AtlasRegion texture, float x, float y, int damage) {
        super(texture, x, y);
        this.damage = damage;
    }

    public Sprite(AtlasRegion texture, int damage) {
        super(texture);
        this.damage = damage;
    }

    @Override
    public void update() {

    }
}
