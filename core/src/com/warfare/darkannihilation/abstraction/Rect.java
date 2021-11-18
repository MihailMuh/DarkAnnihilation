package com.warfare.darkannihilation.abstraction;

public abstract class Rect {
    public float x, y, width, height, halfWidth, halfHeight;

    public Rect(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        halfWidth = width / 2f;
        halfHeight = height / 2f;
    }

    public void setParams(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public float centerX() {
        return x + halfWidth;
    }

    public float centerY() {
        return y + halfHeight;
    }

    public float right() {
        return x + width;
    }

    public float bottom() {
        return y + height;
    }

    public boolean intersect(Rect r) {
        return x < r.right() && right() > r.x && y < r.bottom() && bottom() > r.y;
    }
}
