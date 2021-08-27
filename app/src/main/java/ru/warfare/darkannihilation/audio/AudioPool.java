package ru.warfare.darkannihilation.audio;

import android.media.AudioAttributes;
import android.media.SoundPool;

import java.util.ArrayList;

import static ru.warfare.darkannihilation.systemd.service.Service.activity;

final class AudioPool {
    private static final SoundPool soundPool = new SoundPool
            .Builder()
            .setAudioAttributes(new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build())
            .setMaxStreams(32)
            .build();
    private final ArrayList<Float> volumes = new ArrayList<>(0);
    private final ArrayList<Float> maxes = new ArrayList<>(0);
    private final ArrayList<Integer> sounds = new ArrayList<>(0);

    public int addSound(int id, float volume) {
        int i = soundPool.load(activity, id, 1);
        sounds.add(i);
        volumes.add(volume);
        maxes.add(volume);
        return sounds.indexOf(i);
    }

    public void play(int id) {
        play(sounds.get(id), volumes.get(id));
    }

    private void play(int id, float snd) {
        soundPool.play(id, snd, snd, 1, 0, 1);
    }

    public void newVolume(float newVolume) {
        for (int i = 0; i < volumes.size(); i++) {
            volumes.set(i, maxes.get(i) * newVolume);
        }
    }

    public void newVolumeForSnd(int id, float newVolume) {
        volumes.set(id, maxes.get(id) * newVolume);
    }

    public void release() {
        soundPool.release();
    }
}
