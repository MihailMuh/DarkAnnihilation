package com.warfare.darkannihilation.utils.audio;

import com.badlogic.gdx.audio.Music;

public class MusicWrap extends Audio {
    private final Music music;

    public MusicWrap(Music music, float volume) {
        super(volume);
        this.music = music;

        music.setVolume(volume);
        music.setLooping(true);
    }

    @Override
    public void play() {
        music.play();
    }

    public void pause() {
        music.pause();
    }

    public boolean isPlaying() {
        return music.isPlaying();
    }

    @Override
    public void setVolume(float newVolume) {
        super.setVolume(newVolume);
        music.setVolume(volume);
    }
}
