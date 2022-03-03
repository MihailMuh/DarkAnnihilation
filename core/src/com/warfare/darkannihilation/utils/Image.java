package com.warfare.darkannihilation.utils;

import static com.warfare.darkannihilation.systemd.Frontend.spriteBatch;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class Image {
    private final AtlasRegion atlasRegion;

    public final int width, height, halfWidth, halfHeight;

    public Image(AtlasRegion atlasRegion) {
        this(atlasRegion, 1);
    }

    public Image(AtlasRegion atlasRegion, float scale) {
        this.atlasRegion = atlasRegion;
        width = (int) (atlasRegion.originalWidth * scale);
        height = (int) (atlasRegion.originalHeight * scale);

        halfWidth = width / 2;
        halfHeight = height / 2;
    }

    public void draw(float x, float y, int width, int height) {
        spriteBatch.draw(atlasRegion, x, y, width, height);
    }

    public void draw(float x, float y) {
        draw(x, y, width, height);
    }

    public void draw(float x, float y, float angle) {
        spriteBatch.draw(atlasRegion, x, y, halfWidth, halfHeight, width, height, 1, 1, angle);
    }
}
