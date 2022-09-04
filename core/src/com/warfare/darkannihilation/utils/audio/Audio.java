package com.warfare.darkannihilation.utils.audio;

public abstract class Audio {
    private final float START_VOLUME;

    protected float volume;

    protected Audio(float start_volume) {
        START_VOLUME = start_volume;
        volume = start_volume;
    }

    public abstract void play();

    public float getVolume() {
        return volume;
    }

    public void setVolume(float newVolume) {
        volume = START_VOLUME * newVolume;
    }
}
