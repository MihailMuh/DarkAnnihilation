package com.warfare.darkannihilation.abstraction.sprite;

import static com.warfare.darkannihilation.constants.Names.NO_NAME;
import static com.warfare.darkannihilation.hub.Resources.getBatch;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Pool;

public abstract class BaseSprite extends Sprite implements Pool.Poolable {
    private float indentX, indentY, indentWidth, indentHeight;

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
        onNewSize();
    }

    protected void onNewSize() {
        halfWidth = getWidth() / 2f;
        halfHeight = getHeight() / 2f;
        indentWidth = getWidth();
        indentHeight = getHeight();
    }

    protected void shrinkBounds(float left, float bottom, float right, float top) {
        indentX = left;
        indentY = bottom;
        indentWidth = getWidth() - right;
        indentHeight = getHeight() - top;
    }

    @Override
    public void reset() {
    }

    public abstract void update();

    public void updateInThread() {

    }

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

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        onNewSize();
    }

    public boolean intersect(BaseSprite sprite) {
        float x = getX();
        float y = getY();
        float spriteX = sprite.getX();
        float spriteY = sprite.getY();

        return x + indentX < spriteX + sprite.indentWidth &&
                x + indentWidth > spriteX + sprite.indentX &&
                y + indentY < spriteY + sprite.indentHeight &&
                y + indentHeight > spriteY + sprite.indentY;
    }
}
