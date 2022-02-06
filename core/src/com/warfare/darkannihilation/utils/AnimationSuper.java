package com.warfare.darkannihilation.utils;

public class AnimationSuper {
    private final Image[] images;
    private final int lastElem;
    private final float frameDuration;

    public final int size;
    public int index;

    public AnimationSuper(Image[] images, float frameDuration) {
        this.images = images;
        this.frameDuration = frameDuration;
        size = images.length;
        lastElem = size - 1;
    }

    public Image get(float time) {
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
