package com.warfare.darkannihilation.abstraction.sprite;

public abstract class Rect {
    public float x, y;
    public final float width, height, halfWidth, halfHeight;
    private float indentX, indentY, indentWidth, indentHeight;

    public Rect(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        halfWidth = width / 2f;
        halfHeight = height / 2f;
    }

    protected void setIndents(float X, float Y, float indentWidth, float indentHeight) {
        indentX = X;
        indentY = Y;
        this.indentWidth = indentWidth;
        this.indentHeight = indentHeight;
    }

    protected float x() {
        return x + indentX;
    }

    protected float y() {
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

    public boolean intersect(Rect r) {
        return x() < r.right() && right() > r.x() && y() < r.top() && top() > r.y();
    }
}
