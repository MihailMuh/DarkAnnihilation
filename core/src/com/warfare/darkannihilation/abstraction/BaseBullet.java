package com.warfare.darkannihilation.abstraction;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public abstract class BaseBullet extends AggressiveSprite {
    public BaseBullet(TextureAtlas.AtlasRegion texture, int damage) {
        super(texture, damage);

        visible = false;
    }

    public BaseBullet start(float X, float Y) {
        x = X;
        y = Y;

        visible = true;

        return this;
    }

    @Override
    public void reset() {
        visible = false;
    }
}
