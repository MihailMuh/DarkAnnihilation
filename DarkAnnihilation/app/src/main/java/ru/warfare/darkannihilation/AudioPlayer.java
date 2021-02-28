package ru.warfare.darkannihilation;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;

import java.util.ArrayList;

public class AudioPlayer {
    private final Game game;

    public static ArrayList<MediaPlayer> sounds = new ArrayList(0);
    public static MediaPlayer menuMusic;
    public static MediaPlayer pauseMusic;
    public static MediaPlayer pirateMusic;
    public static MediaPlayer buttonSnd;
    public static MediaPlayer gameoverSnd;
    public static MediaPlayer readySnd;
    public static int boomSnd;
    public static int shootSnd;
    public static int metalSnd;
    public static int shotgunSnd;

    public static SoundPool soundPool;
    public static final int MAX_STREAMS = 500000;

    public AudioPlayer(Game g) {
        game = g;

        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .setMaxStreams(MAX_STREAMS)
                .build();
        boomSnd = soundPool.load(game.context.getApplicationContext(), R.raw.boom, 1);
        shootSnd = soundPool.load(game.context.getApplicationContext(), R.raw.laser, 1);
        metalSnd = soundPool.load(game.context.getApplicationContext(), R.raw.metal, 1);
        shotgunSnd = soundPool.load(game.context.getApplicationContext(), R.raw.shotgun, 1);

        menuMusic = MediaPlayer.create(game.context.getApplicationContext(), R.raw.menu);
        menuMusic.setLooping(true);
        sounds.add(menuMusic);

        pirateMusic = MediaPlayer.create(game.context.getApplicationContext(), R.raw.pirate);
        pirateMusic.setLooping(true);
        pirateMusic.setVolume(0.7f, 0.7f);
        sounds.add(pirateMusic);

        buttonSnd = MediaPlayer.create(game.context.getApplicationContext(), R.raw.spacebar);
        sounds.add(buttonSnd);

        gameoverSnd = MediaPlayer.create(game.context.getApplicationContext(), R.raw.gameover_phrase);
        gameoverSnd.setVolume(3f, 3f);
        sounds.add(gameoverSnd);

        readySnd = MediaPlayer.create(game.context.getApplicationContext(), R.raw.ready);
        readySnd.setVolume(3f, 3f);
        sounds.add(readySnd);

        pauseMusic = MediaPlayer.create(game.context.getApplicationContext(), R.raw.pause);
        readySnd.setVolume(1f, 1f);
        pauseMusic.setLooping(true);
        sounds.add(pauseMusic);
    }

    public static void releaseAP() {
        try {
            for (int i = 0; i < sounds.size(); i++) {
                sounds.get(i).release();
                sounds.remove(i);
            }
            soundPool.release();
            soundPool = null;
        } catch (Exception e) {
            Log.e("Error ", "Can't release AP " + e);
        }
    }

    public static void playBoom() {
        soundPool.play(boomSnd, 0.13f, 0.13f, 1, 0, 1);
    }
    public static void playShoot() {
        soundPool.play(shootSnd, 0.17f, 0.17f, 1, 0, 1);
    }
    public static void playMetal() {
        soundPool.play(metalSnd, 0.45f, 0.45f, 1, 0, 1);
    }
    public static void playShotgun() {
        soundPool.play(shotgunSnd, 0.3f, 0.3f, 1, 0, 1);
    }
}
