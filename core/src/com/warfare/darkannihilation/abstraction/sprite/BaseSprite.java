package com.warfare.darkannihilation.abstraction.sprite;

import static com.warfare.darkannihilation.constants.Names.NO_NAME;
import static com.warfare.darkannihilation.hub.Resources.getBatch;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Pool;

public abstract class BaseSprite extends Sprite implements Pool.Poolable {
    private float indentX, indentY, indentWidth, indentHeight;
    private boolean shrunkBorders = false;

    public float halfWidth, halfHeight;

    public boolean visible = true;
    public byte name = NO_NAME;

    public BaseSprite(AtlasRegion region) {
        this(region, region.originalWidth, region.originalHeight);
    }

    public BaseSprite(AtlasRegion region, float width, float height) {
        super(region);

        setSize(width, height);
        setOriginCenter();

        halfWidth = width / 2f;
        halfHeight = height / 2f;
        indentWidth = width;
        indentHeight = height;
    }

    protected void shrinkBounds(float left, float bottom, float right, float top) {
        indentX = left;
        indentY = bottom;
        indentWidth = getWidth() - right;
        indentHeight = getHeight() - top;

        shrunkBorders = true;
    }

    @Override
    public void reset() {
    }

    public abstract void update();

    public void render() {
        draw(getBatch());
    }

    public float centerX() {
        return getX() + halfWidth;
    }

    public float centerY() {
        return getY() + halfHeight;
    }

    public float right() {
        return getX() + getWidth();
    }

    public float top() {
        return getY() + getHeight();
    }

    public boolean intersect(BaseSprite sprite) {
        float x = getX();
        float y = getY();
        float spriteX = sprite.getX();
        float spriteY = sprite.getY();

        if (shrunkBorders) {
            return x + indentX < spriteX + sprite.indentWidth &&
                    x + indentWidth > spriteX + sprite.indentX &&
                    y + indentY < spriteY + sprite.indentHeight &&
                    y + indentHeight > spriteY + sprite.indentY;
        }
        return x < sprite.right() &&
                right() > spriteX &&
                y < sprite.top() &&
                top() > spriteY;
    }
}
