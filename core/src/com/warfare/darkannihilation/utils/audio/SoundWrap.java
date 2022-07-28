package com.warfare.darkannihilation.utils.audio;

import static com.warfare.darkannihilation.Settings.SOUNDS_IN_THREAD;

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
        if (SOUNDS_IN_THREAD) {
            Processor.postToLooper(() -> sound.play(volume));
        } else {
            sound.play(volume);
        }
    }
}
