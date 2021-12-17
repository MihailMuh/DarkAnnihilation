package com.warfare.darkannihilation.abstraction;

import static com.warfare.darkannihilation.systemd.Frontend.spriteBatch;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public abstract class LiveSprite extends BaseSprite {
    public float speedX, speedY;

    public LiveSprite(AtlasRegion texture, float x, float y) {
        super(texture, x, y);
    }

    public LiveSprite(AtlasRegion texture) {
        super(texture);
    }

    @Override
    public void render() {
        if (visible) {
            spriteBatch.draw(image, x, y, width, height);
        }
    }
}
