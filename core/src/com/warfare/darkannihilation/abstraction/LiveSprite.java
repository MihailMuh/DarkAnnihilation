package com.warfare.darkannihilation.abstraction;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Pool.Poolable;

public abstract class LiveSprite extends BaseSprite implements Poolable {
    public float speedX, speedY;

    public LiveSprite(TextureAtlas.AtlasRegion texture, float x, float y) {
        super(texture, x, y);
    }

    public LiveSprite(TextureAtlas.AtlasRegion texture) {
        super(texture);
    }

    @Override
    public void render() {
        if (visible) {
            draw();
        }
    }
}
