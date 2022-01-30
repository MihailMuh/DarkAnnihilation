package com.warfare.darkannihilation.abstraction.sprite;

import static com.warfare.darkannihilation.constants.Names.NO_NAME;
import static com.warfare.darkannihilation.systemd.Frontend.spriteBatch;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Pool.Poolable;

public abstract class BaseSprite extends Rect implements Poolable {
    protected AtlasRegion image;
    public boolean visible = true;
    public byte name = NO_NAME;

    public BaseSprite(AtlasRegion texture, float width, float height) {
        super(0, 0, width, height);
        image = texture;
    }

    public BaseSprite(AtlasRegion texture) {
        this(texture, texture.originalWidth, texture.originalHeight);
    }

    public void render() {
        spriteBatch.draw(image, x, y, width, height);
    }

    public abstract void update();

    @Override
    public void reset() {
    }
}
