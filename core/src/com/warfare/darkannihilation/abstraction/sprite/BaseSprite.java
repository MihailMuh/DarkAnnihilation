package com.warfare.darkannihilation.abstraction.sprite;

import static com.warfare.darkannihilation.constants.Names.NO_NAME;

import com.badlogic.gdx.utils.Pool.Poolable;
import com.warfare.darkannihilation.utils.Image;

public abstract class BaseSprite implements Poolable {
    private int indentX, indentY, indentWidth, indentHeight;

    protected Image image;

    public float x, y;
    public final int width, height, halfWidth, halfHeight;

    public boolean visible = true;
    public byte name = NO_NAME;

    public BaseSprite(Image image, int width, int height) {
        this.image = image;
        this.width = width;
        this.height = height;

        if (width == image.width) halfWidth = image.halfWidth;
        else halfWidth = width / 2;

        if (height == image.height) halfHeight = image.halfHeight;
        else halfHeight = height / 2;
    }

    public BaseSprite(Image image) {
        this(image, image.width, image.height);
    }

    protected void shrinkBorders(int X, int Y, int indentWidth, int indentHeight) {
        indentX = X;
        indentY = Y;
        this.indentWidth = indentWidth;
        this.indentHeight = indentHeight;
    }

    public void render() {
        image.draw(x, y, width, height);
    }

    public abstract void update();

    @Override
    public void reset() {
    }

    public float x() {
        return x + indentX;
    }

    public float y() {
        return y + indentY;
    }

    public float centerX() {
        return x + halfWidth;
    }

    public float centerY() {
        return y + halfHeight;
    }

    public float right() {
        return x + width - indentWidth;
    }

    public float top() {
        return y + height - indentHeight;
    }

    public boolean intersect(BaseSprite r) {
        return x() < r.right() && right() > r.x() && y() < r.top() && top() > r.y();
    }
}
