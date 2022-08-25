package com.warfare.darkannihilation.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class AnimationSuper {
    private final AtlasRegion[] regions;
    private final int lastElementIndex;
    private final float frameDuration;

    public final int size;
    public int index;

    public AnimationSuper(AtlasRegion[] regions, float frameDuration) {
        this.regions = regions;
        this.frameDuration = frameDuration;
        size = regions.length;
        lastElementIndex = size - 1;
    }

    public AtlasRegion get(float time) {
        index = (int) (time / frameDuration);
        if (index < size) {
            return regions[index];
        }
        return regions[lastElementIndex];
    }

    public boolean isFinished() {
        return index >= lastElementIndex;
    }
}
