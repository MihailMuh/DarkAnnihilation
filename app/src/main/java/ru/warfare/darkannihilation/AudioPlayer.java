package ru.warfare.darkannihilation;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;

import java.util.ArrayList;

public class AudioPlayer {
    public static ArrayList<MediaPlayer> sounds = new ArrayList<>(0);
    public static int[] reloadSounds = new int[2];

    public static MediaPlayer menuMusic;
    public static MediaPlayer pauseMusic;
    public static MediaPlayer pirateMusic;
    public static MediaPlayer gameoverSnd;
    public static MediaPlayer bossMusic;
    public static MediaPlayer flightSnd;
    public static MediaPlayer winMusic;
    public static MediaPlayer portalSound;
    public static MediaPlayer timeMachineSnd;
    public static MediaPlayer attentionSnd;
    public static MediaPlayer readySnd;
    public static MediaPlayer forgottenMusic;
    public static MediaPlayer forgottenBossMusic;

    private static int healSound;
    private static int buttonSound;
    private static int boomSnd;
    private static int shootSnd;
    private static int metalSnd;
    private static int shotgunSnd;
    private static int megaBoom;
    private static int fallingBombSnd;
    private static int deagleSnd;
    private static int bossShootSnd;

    private static SoundPool soundPool;
    private static final int MAX_STREAMS = 50000;

    public AudioPlayer(Game g) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                soundPool = new SoundPool.Builder()
                        .setAudioAttributes(new AudioAttributes.Builder()
                                .setUsage(AudioAttributes.USAGE_GAME)
                                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                                .build())
                        .setMaxStreams(MAX_STREAMS)
                        .build();
                boomSnd = soundPool.load(g.context.getApplicationContext(), R.raw.boom, 1);
                shootSnd = soundPool.load(g.context.getApplicationContext(), R.raw.laser, 1);
                metalSnd = soundPool.load(g.context.getApplicationContext(), R.raw.metal, 1);
                shotgunSnd = soundPool.load(g.context.getApplicationContext(), R.raw.shotgun, 1);
                megaBoom = soundPool.load(g.context.getApplicationContext(), R.raw.megaboom, 1);
                reloadSounds[0] = soundPool.load(g.context.getApplicationContext(), R.raw.reload0, 1);
                reloadSounds[1] = soundPool.load(g.context.getApplicationContext(), R.raw.reload1, 1);
                fallingBombSnd = soundPool.load(g.context.getApplicationContext(), R.raw.falling_bomb, 1);
                buttonSound = soundPool.load(g.context.getApplicationContext(), R.raw.spacebar, 1);
                deagleSnd = soundPool.load(g.context.getApplicationContext(), R.raw.deagle, 1);
                healSound = soundPool.load(g.context.getApplicationContext(), R.raw.heal, 1);
                bossShootSnd = soundPool.load(g.context.getApplicationContext(), R.raw.boss_shoot, 1);

                menuMusic = MediaPlayer.create(g.context.getApplicationContext(), R.raw.menu);
                menuMusic.setLooping(true);
                sounds.add(menuMusic);

                readySnd = MediaPlayer.create(g.context.getApplicationContext(), R.raw.ready);
                readySnd.setVolume(3f, 3f);
                sounds.add(readySnd);

                attentionSnd = MediaPlayer.create(g.context.getApplicationContext(), R.raw.attention);
                attentionSnd.setVolume(0.6f, 0.6f);
                sounds.add(attentionSnd);

                pirateMusic = MediaPlayer.create(g.context.getApplicationContext(), R.raw.pirate);
                pirateMusic.setLooping(true);
                pirateMusic.setVolume(0.7f, 0.7f);
                sounds.add(pirateMusic);

                gameoverSnd = MediaPlayer.create(g.context.getApplicationContext(), R.raw.gameover_phrase);
                gameoverSnd.setVolume(1f, 1f);
                sounds.add(gameoverSnd);

                pauseMusic = MediaPlayer.create(g.context.getApplicationContext(), R.raw.pause);
                pauseMusic.setVolume(0.75f, 0.75f);
                pauseMusic.setLooping(true);
                sounds.add(pauseMusic);

                bossMusic = MediaPlayer.create(g.context.getApplicationContext(), R.raw.shadow_boss);
                bossMusic.setVolume(0.45f, 0.45f);
                bossMusic.setLooping(true);
                sounds.add(bossMusic);

                MediaPlayer attentionSnd = MediaPlayer.create(g.context.getApplicationContext(), R.raw.attention);
                attentionSnd.setVolume(0.6f, 0.6f);
                sounds.add(attentionSnd);

                flightSnd = MediaPlayer.create(g.context.getApplicationContext(), R.raw.fly);
                flightSnd.setVolume(1f, 1f);
                sounds.add(flightSnd);

                winMusic = MediaPlayer.create(g.context.getApplicationContext(), R.raw.win);
                winMusic.setVolume(0.3f, 0.3f);
                winMusic.setLooping(true);
                sounds.add(winMusic);

                portalSound = MediaPlayer.create(g.context.getApplicationContext(), R.raw.portal);
                portalSound.setVolume(0.5f, 0.5f);
                sounds.add(portalSound);

                timeMachineSnd = MediaPlayer.create(g.context.getApplicationContext(), R.raw.time_machine);
                timeMachineSnd.setVolume(1f, 1f);
                sounds.add(timeMachineSnd);

                forgottenMusic = MediaPlayer.create(g.context.getApplicationContext(), R.raw.forgotten_snd);
                forgottenMusic.setVolume(1f, 1f);
                forgottenMusic.setLooping(true);
                sounds.add(forgottenMusic);

                forgottenBossMusic = MediaPlayer.create(g.context.getApplicationContext(), R.raw.forgotten_boss);
                forgottenBossMusic.setVolume(1f, 1f);
                forgottenBossMusic.setLooping(true);
                sounds.add(forgottenBossMusic);

            }
        }; thread.start();
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
            Log.e(MainActivity.TAG, "Can't release AP " + e);
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
        soundPool.play(shotgunSnd, 0.23f, 0.23f, 1, 0, 1);
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
    public static void playHealSnd() {
        soundPool.play(healSound, 0.8f, 0.8f, 1, 0, 1);
    }
    public static void playClick() {
        soundPool.play(buttonSound, 1f, 1f, 1, 0, 1);
    }
    public static void playDeagle() {
        soundPool.play(deagleSnd, 1f, 1f, 1, 0, 1);
    }
    public static void playBossShoot() {
        soundPool.play(bossShootSnd, 1f, 1f, 1, 0, 1);
    }

}
