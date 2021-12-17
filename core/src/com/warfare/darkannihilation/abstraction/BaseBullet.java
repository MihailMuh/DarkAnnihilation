package com.warfare.darkannihilation.abstraction;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public abstract class BaseBullet extends AggressiveSprite {
    public BaseBullet(AtlasRegion texture, int damage) {
        super(texture, damage);

        visible = false;
    }

    public void start(float X, float Y) {
        x = X - halfWidth;
        y = Y - halfHeight;

        visible = true;
    }

    @Override
    public void reset() {
        visible = false;
    }
}
