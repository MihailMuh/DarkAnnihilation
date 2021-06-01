package ru.warfare.darkannihilation;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;

import java.util.ArrayList;

import static ru.warfare.darkannihilation.Sprite.randInt;

public final class AudioPlayer {
    private static final int MAX_STREAMS = 100;

    public static ArrayList<MediaPlayer> sounds = new ArrayList<>(0);
    public static int[] reloadSounds = new int[2];
    private static final SoundPool soundPool = new SoundPool
            .Builder()
            .setAudioAttributes(new AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_GAME)
                            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                            .build())
            .setMaxStreams(MAX_STREAMS)
            .build();

    public static MediaPlayer menuMusic;
    public static MediaPlayer pauseMusic;
    public static MediaPlayer pirateMusic;
    public static MediaPlayer gameoverSnd;
    public static MediaPlayer bossMusic;
    public static MediaPlayer flightSnd;
    public static MediaPlayer winMusic;
    public static MediaPlayer portalSound;
    public static MediaPlayer timeMachineFirstSnd;
    public static MediaPlayer timeMachineSecondSnd;
    public static MediaPlayer timeMachineNoneSnd;

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

    public static void init(Context context) {
        new Thread(() -> {
            boomSnd = soundPool.load(context, R.raw.boom, 1);
            shootSnd = soundPool.load(context, R.raw.laser, 1);
            metalSnd = soundPool.load(context, R.raw.metal, 1);
            shotgunSnd = soundPool.load(context, R.raw.shotgun, 1);
            megaBoom = soundPool.load(context, R.raw.megaboom, 1);
            reloadSounds[0] = soundPool.load(context, R.raw.reload0, 1);
            reloadSounds[1] = soundPool.load(context, R.raw.reload1, 1);
            fallingBombSnd = soundPool.load(context, R.raw.falling_bomb, 1);
            buttonSound = soundPool.load(context, R.raw.spacebar, 1);
            deagleSnd = soundPool.load(context, R.raw.deagle, 1);
            healSound = soundPool.load(context, R.raw.heal, 1);
            bossShootSnd = soundPool.load(context, R.raw.boss_shoot, 1);

            menuMusic = MediaPlayer.create(context, R.raw.menu);
            menuMusic.setLooping(true);
            sounds.add(menuMusic);

            readySnd = MediaPlayer.create(context, R.raw.ready);
            readySnd.setVolume(1f, 1f);
            sounds.add(readySnd);

            attentionSnd = MediaPlayer.create(context, R.raw.attention);
            attentionSnd.setVolume(0.6f, 0.6f);
            sounds.add(attentionSnd);

            pirateMusic = MediaPlayer.create(context, R.raw.pirate);
            pirateMusic.setLooping(true);
            pirateMusic.setVolume(0.7f, 0.7f);
            sounds.add(pirateMusic);

            gameoverSnd = MediaPlayer.create(context, R.raw.gameover_phrase);
            gameoverSnd.setVolume(1f, 1f);
            sounds.add(gameoverSnd);

            pauseMusic = MediaPlayer.create(context, R.raw.pause);
            pauseMusic.setVolume(0.75f, 0.75f);
            pauseMusic.setLooping(true);
            sounds.add(pauseMusic);

            bossMusic = MediaPlayer.create(context, R.raw.shadow_boss);
            bossMusic.setVolume(0.45f, 0.45f);
            bossMusic.setLooping(true);
            sounds.add(bossMusic);

            flightSnd = MediaPlayer.create(context, R.raw.fly);
            flightSnd.setVolume(1f, 1f);
            sounds.add(flightSnd);

            winMusic = MediaPlayer.create(context, R.raw.win);
            winMusic.setVolume(0.3f, 0.3f);
            winMusic.setLooping(true);
            sounds.add(winMusic);

            portalSound = MediaPlayer.create(context, R.raw.portal);
            portalSound.setVolume(0.5f, 0.5f);
            sounds.add(portalSound);

            timeMachineFirstSnd = MediaPlayer.create(context, R.raw.time_machine);
            timeMachineFirstSnd.setVolume(1f, 1f);
            sounds.add(timeMachineFirstSnd);

            timeMachineSecondSnd = MediaPlayer.create(context, R.raw.time_machine1);
            timeMachineSecondSnd.setVolume(1f, 1f);
            sounds.add(timeMachineSecondSnd);

            timeMachineNoneSnd = MediaPlayer.create(context, R.raw.time_machine_none);
            timeMachineNoneSnd.setVolume(0f, 0f);
            sounds.add(timeMachineNoneSnd);

            forgottenMusic = MediaPlayer.create(context, R.raw.forgotten_snd);
            forgottenMusic.setVolume(1f, 1f);
            forgottenMusic.setLooping(true);
            sounds.add(forgottenMusic);

            forgottenBossMusic = MediaPlayer.create(context, R.raw.forgotten_boss);
            forgottenBossMusic.setVolume(1f, 1f);
            forgottenBossMusic.setLooping(true);
            sounds.add(forgottenBossMusic);

        }).start();
    }

    public static void releaseAP() {
        try {
            for (int i = 0; i < sounds.size(); i++) {
                sounds.get(i).release();
                sounds.remove(i);
            }
            soundPool.release();
        } catch (Exception e) {
            Service.print("Can't release AP " + e);
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


    public static void resumeBackgroundMusic() {
        switch (Game.level)
        {
            case 1:
                pirateMusic.start();
                break;
            case 2:
                forgottenMusic.start();
                break;
        }
    }

    public static void pauseBackgroundMusic() {
        if (pirateMusic.isPlaying()) {
            pirateMusic.pause();
        }
        if (forgottenMusic.isPlaying()) {
            forgottenMusic.pause();
        }
    }

    public static void restartBackgroundMusic() {
        pauseBackgroundMusic();
        switch (Game.level)
        {
            case 1:
                pirateMusic.seekTo(0);
                pirateMusic.start();
                break;
            case 2:
                AudioPlayer.forgottenMusic.seekTo(0);
                AudioPlayer.forgottenMusic.start();
                break;
        }
    }

    public static void restartBossMusic() {
        switch (Game.level)
        {
            case 1:
                if (bossMusic.isPlaying()) {
                    bossMusic.pause();
                }
                bossMusic.seekTo(0);
                bossMusic.start();
                break;
            case 2:
                if (forgottenBossMusic.isPlaying()) {
                    forgottenBossMusic.pause();
                }
                forgottenBossMusic.seekTo(0);
                forgottenBossMusic.start();
                break;
        }
    }

    public static void pauseBossMusic() {
        if (bossMusic.isPlaying()) {
            bossMusic.pause();
        }
        if (forgottenBossMusic.isPlaying()) {
            forgottenBossMusic.pause();
        }
    }

    public static void resumeBossMusic() {
        switch (Game.level)
        {
            case 1:
                bossMusic.start();
                break;
            case 2:
                forgottenBossMusic.start();
                break;
        }
    }

    public static void pausePauseMusic() {
        if (pauseMusic.isPlaying()) {
            pauseMusic.pause();
        }
    }

    public static void restartPauseMusic() {
        pausePauseMusic();
        pauseMusic.seekTo(0);
        pauseMusic.start();
    }

    public static void pauseMenuMusic() {
        if (menuMusic.isPlaying()) {
            menuMusic.pause();
        }
    }

    public static void restartMenuMusic() {
        if (!menuMusic.isPlaying()) {
            menuMusic.seekTo(0);
            menuMusic.start();
        }
    }

    public static void pauseFlightMusic() {
        if (flightSnd.isPlaying()) {
            flightSnd.pause();
        }
    }

    public static void restartFlightMusic() {
        pauseFlightMusic();
        flightSnd.seekTo(0);
        flightSnd.start();
    }

    public static void pauseReadySound() {
        if (readySnd.isPlaying()) {
            readySnd.pause();
        }
    }

    public static void restartReadySound() {
        pauseReadySound();
        readySnd.seekTo(0);
        readySnd.start();
    }
}
