package com.warfare.darkannihilation.utils.audio;

import com.badlogic.gdx.audio.Sound;

public class SoundWrap extends Audio {
    private final Sound sound;

    public SoundWrap(Sound sound, float volume) {
        super(volume);
        this.sound = sound;
    }

    @Override
    public synchronized void play() {
        sound.play(volume);
    }
}
