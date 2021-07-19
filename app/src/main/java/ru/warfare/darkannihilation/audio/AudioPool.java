package ru.warfare.darkannihilation.audio;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;

import java.util.ArrayList;

import ru.warfare.darkannihilation.math.Math;

public final class AudioPool {
    private static final SoundPool soundPool = new SoundPool
            .Builder()
            .setAudioAttributes(new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build())
            .setMaxStreams(1000)
            .build();
    private final ArrayList<Float> volumes = new ArrayList<>(0);
    private final ArrayList<Float> maxes = new ArrayList<>(0);
    private final ArrayList<Integer> sounds = new ArrayList<>(0);
    private final ArrayList<int[]> packs = new ArrayList<>(0);
    private final ArrayList<float[]> sndOfPacks = new ArrayList<>(0);
    private final ArrayList<float[]> maxesOfPacks = new ArrayList<>(0);

    public int addSound(Context context, int id, float volume) {
        int i = soundPool.load(context, id, 1);
        sounds.add(i);
        volumes.add(volume);
        maxes.add(volume);
        return sounds.indexOf(i);
    }

    public int addSoundsToPack(Context context, float[][] content) {
        int[] end = new int[content.length];
        float[] snd = new float[content.length];
        for (int i = 0; i < content.length; i++) {
            end[i] = soundPool.load(context, (int) content[i][0], 1);
            snd[i] = content[i][1];
        }
        packs.add(end);
        sndOfPacks.add(snd);
        maxesOfPacks.add(snd);
        return packs.indexOf(end);
    }

    public void playFromPack(int id) {
        int i = Math.randInt(0, packs.get(id).length - 1);
        playSnd(packs.get(id)[i], sndOfPacks.get(id)[i]);
    }

    public void play(int id) {
        playSnd(sounds.get(id), volumes.get(id));
    }

    private void playSnd(int id, float snd) {
        soundPool.play(id, snd, snd, 1, 0, 1);
    }

    public void newVolume(float newVolume) {
        for (int i = 0; i < volumes.size(); i++) {
            volumes.set(i, maxes.get(i) * newVolume);
        }
        for (int i = 0; i < sndOfPacks.size(); i++) {
            float[] snd = sndOfPacks.get(i);
            for (int j = 0; j < snd.length; j++) {
                snd[j] = newVolume * maxesOfPacks.get(i)[j];
            }
        }
    }

    public void newVolumeForSnd(int id, float newVolume) {
        volumes.set(id, maxes.get(id) * newVolume);
    }

    public void release() {
        soundPool.release();
    }
}
