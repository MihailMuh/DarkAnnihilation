package com.warfare.darkannihilation.abstraction;

import static com.warfare.darkannihilation.systemd.Frontend.spriteBatch;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Pool.Poolable;

public abstract class BaseSprite extends Rect implements Poolable {
    protected final AtlasRegion image;
    public boolean visible = true;

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
