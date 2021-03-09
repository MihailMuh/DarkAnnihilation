package ru.warfare.darkannihilation;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;

import java.util.ArrayList;

public class AudioPlayer {
    private final Game game;

    public static ArrayList<MediaPlayer> sounds = new ArrayList(0);
    public static int[] reloadSounds = new int[2];
    public static MediaPlayer menuMusic;
    public static MediaPlayer pauseMusic;
    public static MediaPlayer pirateMusic;
    public static MediaPlayer buttonSnd;
    public static MediaPlayer gameoverSnd;
    public static MediaPlayer readySnd;
    public static MediaPlayer bossMusic;
    public static MediaPlayer healSnd;
    public static MediaPlayer attentionSnd;

    private static int boomSnd;
    private static int shootSnd;
    private static int metalSnd;
    private static int shotgunSnd;
    private static int megaBoom;
    private static int fallingBombSnd;

    private static SoundPool soundPool;
    private static final int MAX_STREAMS = 500000;

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
        megaBoom = soundPool.load(game.context.getApplicationContext(), R.raw.megaboom, 1);
        reloadSounds[0] = soundPool.load(game.context.getApplicationContext(), R.raw.reload0, 1);
        reloadSounds[1] = soundPool.load(game.context.getApplicationContext(), R.raw.reload1, 1);
        fallingBombSnd = soundPool.load(game.context.getApplicationContext(), R.raw.falling_bomb, 1);

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
        pauseMusic.setVolume(1f, 1f);
        pauseMusic.setLooping(true);
        sounds.add(pauseMusic);

        bossMusic = MediaPlayer.create(game.context.getApplicationContext(), R.raw.shadow_boss);
        bossMusic.setVolume(0.45f, 0.45f);
        bossMusic.setLooping(true);
        sounds.add(bossMusic);

        healSnd = MediaPlayer.create(game.context.getApplicationContext(), R.raw.heal);
        healSnd.setVolume(0.35f, 0.35f);
        sounds.add(healSnd);

        attentionSnd = MediaPlayer.create(game.context.getApplicationContext(), R.raw.attention);
        attentionSnd.setVolume(0.6f, 0.6f);
        sounds.add(attentionSnd);
    }

    public static int randInt(int min, int max){
        max -= min;
        return (int) (Math.random() * ++max) + min;
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
    public static void playMegaBoom() {
        soundPool.play(megaBoom, 1f, 1f, 1, 0, 1);
    }
    public static void playReload() {
        soundPool.play(reloadSounds[randInt(0, 1)], 1f, 1f, 1, 0,1);
    }
    public static void playFallingBomb() {
        soundPool.play(fallingBombSnd, 0.2f, 0.2f, 1, 0,1);
    }

}
