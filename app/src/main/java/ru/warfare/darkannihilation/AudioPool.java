package ru.warfare.darkannihilation;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;

import java.util.ArrayList;

public final class AudioPool {
    private static final SoundPool soundPool = new SoundPool
            .Builder()
            .setAudioAttributes(new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build())
            .setMaxStreams(1000)
            .build();
    private static final ArrayList<Float> volumes = new ArrayList<>(0);
    private static final ArrayList<Float> maxVolumes = new ArrayList<>(0);
    private static final ArrayList<Integer> sounds = new ArrayList<>(0);

    public static void addSound(Context context, int id, float volume) {
        sounds.add(soundPool.load(context, id, 1));
        volumes.add(volume);
        maxVolumes.add(volume);
    }

    public static void addSound(Context context, int id) {
        sounds.add(soundPool.load(context, id, 1));
    }

    public static void playSnd(int id) {
        if (id == 0) {
            soundPool.play(sounds.get(MATH.randInt(0, 1)), volumes.get(id), volumes.get(id), 1, 0, 1);
        } else {
            soundPool.play(sounds.get(id + 1), volumes.get(id), volumes.get(id), 1, 0, 1);
        }
    }

    public static void newVolumeForPool(float newVolume) {
        for (int i = 0; i < volumes.size(); i++) {
            volumes.set(i, maxVolumes.get(i) * newVolume);
        }
    }

    public static void newVolumeForSnd(int id, float newVolume) {
        volumes.set(id, maxVolumes.get(id) * newVolume);
    }

    public static void release() {
        soundPool.release();
    }
}
