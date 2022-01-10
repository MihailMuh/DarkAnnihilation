package com.warfare.darkannihilation.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class AnimationSuper {
    private final AtlasRegion[] images;
    private final int lastElem;
    private final float frameDuration;

    public final int size;
    public int index;

    public AnimationSuper(AtlasRegion[] images, float frameDuration) {
        this.images = images;
        this.frameDuration = frameDuration;
        size = images.length;
        lastElem = size - 1;
    }

    public AtlasRegion get(float time) {
        index = (int) (time / frameDuration);
        if (index < size) {
            return images[index];
        }
        return images[lastElem];
    }

    public boolean isFinished() {
        return index >= lastElem;
    }
}
