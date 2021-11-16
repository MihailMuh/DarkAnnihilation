package com.warfare.darkannihilation.abstraction;

import static com.warfare.darkannihilation.systemd.Frontend.spriteBatch;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.warfare.darkannihilation.Rect;

public abstract class BaseSprite extends Rect {
    private final AtlasRegion image;

    public BaseSprite(AtlasRegion texture, float x, float y, int width, int height) {
        super(x, y, width, height);
        image = texture;
    }

    public BaseSprite(AtlasRegion texture, float x, float y) {
        super(x, y, texture.originalWidth, texture.originalHeight);
        image = texture;
    }

    public BaseSprite(AtlasRegion texture) {
        super(0, 0, texture.originalWidth, texture.originalHeight);
        image = texture;
    }

    public void render() {
        spriteBatch.draw(image, x, y, width, height);
    }

    public abstract void update();
}
