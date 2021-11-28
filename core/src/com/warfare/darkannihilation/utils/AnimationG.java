package com.warfare.darkannihilation.utils;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

public class AnimationG<T> implements Disposable {
    private final T[] images;
    private final int len_1;
    private final float frameDuration;
    public final int size;
    public int index;

    public AnimationG(T[] images, float frameDuration) {
        this.images = images;
        this.frameDuration = frameDuration;
        len_1 = images.length - 1;
        size = images.length;
    }

    public T get(float time) {
        index = (int) (time / frameDuration);
        if (index < size) {
            return images[index];
        }
        return images[len_1];
    }

    public boolean isFinished() {
        return index == len_1;
    }

    @Override
    public void dispose() {
        ((TextureRegion) images[0]).getTexture().dispose();
    }
}
