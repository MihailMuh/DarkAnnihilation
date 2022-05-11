package com.warfare.darkannihilation.utils.audio;

import com.badlogic.gdx.audio.Sound;
import com.warfare.darkannihilation.systemd.service.Processor;

public class SoundWrap extends Audio {
    private final Sound sound;

    public SoundWrap(Sound sound, float volume) {
        super(volume);
        this.sound = sound;
    }

    @Override
    public void play() {
        Processor.postToLooperSounds(() -> sound.play(volume));
    }
}
