package ru.warfare.darkannihilation;

import android.content.Context;
import android.media.MediaPlayer;

import java.util.ArrayList;

public final class AudioHub {
    public static ArrayList<MediaPlayer> sounds = new ArrayList<>(0);
    public static ArrayList<Float> volumes = new ArrayList<>(0);

    public static MediaPlayer menuMusic;
    public static MediaPlayer pauseMusic;
    public static MediaPlayer jingleMusic;
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

    public static void init(Context context) {
        new Thread(() -> {
            AudioPool.addSound(context, R.raw.reload0, 1f);
            AudioPool.addSound(context, R.raw.reload1);
            AudioPool.addSound(context, R.raw.boom, 0.13f);
            AudioPool.addSound(context, R.raw.laser, 0.17f);
            AudioPool.addSound(context, R.raw.metal, 0.45f);
            AudioPool.addSound(context, R.raw.shotgun, 0.25f);
            AudioPool.addSound(context, R.raw.megaboom, 1f);
            AudioPool.addSound(context, R.raw.falling_bomb, 0.2f);
            AudioPool.addSound(context, R.raw.spacebar, 1f);
            AudioPool.addSound(context, R.raw.deagle, 1f);
            AudioPool.addSound(context, R.raw.heal, 0.8f);
            AudioPool.addSound(context, R.raw.boss_shoot, 1f);

            menuMusic = MediaPlayer.create(context, R.raw.menu);
            menuMusic.setLooping(true);
            sounds.add(menuMusic);
            volumes.add(1f);

            readySnd = MediaPlayer.create(context, R.raw.ready);
            readySnd.setVolume(1f, 1f);
            sounds.add(readySnd);
            volumes.add(1f);

            attentionSnd = MediaPlayer.create(context, R.raw.attention);
            attentionSnd.setVolume(0.6f, 0.6f);
            sounds.add(attentionSnd);
            volumes.add(0.6f);

            jingleMusic = MediaPlayer.create(context, R.raw.jingle);
            jingleMusic.setLooping(true);
            jingleMusic.setVolume(0.5f, 0.5f);
            sounds.add(jingleMusic);
            volumes.add(0.5f);

            gameoverSnd = MediaPlayer.create(context, R.raw.gameover_phrase);
            gameoverSnd.setVolume(1f, 1f);
            sounds.add(gameoverSnd);
            volumes.add(1f);

            pauseMusic = MediaPlayer.create(context, R.raw.pause);
            pauseMusic.setVolume(0.75f, 0.75f);
            pauseMusic.setLooping(true);
            sounds.add(pauseMusic);
            volumes.add(0.75f);

            bossMusic = MediaPlayer.create(context, R.raw.shadow_boss);
            bossMusic.setVolume(0.45f, 0.45f);
            bossMusic.setLooping(true);
            sounds.add(bossMusic);
            volumes.add(0.45f);

            flightSnd = MediaPlayer.create(context, R.raw.fly);
            flightSnd.setVolume(1f, 1f);
            sounds.add(flightSnd);
            volumes.add(1f);

            winMusic = MediaPlayer.create(context, R.raw.win);
            winMusic.setVolume(0.3f, 0.3f);
            winMusic.setLooping(true);
            sounds.add(winMusic);
            volumes.add(0.3f);

            portalSound = MediaPlayer.create(context, R.raw.portal);
            portalSound.setVolume(0.5f, 0.5f);
            sounds.add(portalSound);
            volumes.add(0.5f);

            timeMachineFirstSnd = MediaPlayer.create(context, R.raw.time_machine);
            timeMachineFirstSnd.setVolume(1f, 1f);
            sounds.add(timeMachineFirstSnd);
            volumes.add(1f);

            timeMachineSecondSnd = MediaPlayer.create(context, R.raw.time_machine1);
            timeMachineSecondSnd.setVolume(1f, 1f);
            sounds.add(timeMachineSecondSnd);
            volumes.add(1f);

            timeMachineNoneSnd = MediaPlayer.create(context, R.raw.time_machine_none);
            timeMachineNoneSnd.setVolume(0f, 0f);
            sounds.add(timeMachineNoneSnd);
            volumes.add(0f);

            forgottenMusic = MediaPlayer.create(context, R.raw.forgotten_snd);
            forgottenMusic.setVolume(1f, 1f);
            forgottenMusic.setLooping(true);
            sounds.add(forgottenMusic);
            volumes.add(1f);

            forgottenBossMusic = MediaPlayer.create(context, R.raw.forgotten_boss);
            forgottenBossMusic.setVolume(1f, 1f);
            forgottenBossMusic.setLooping(true);
            sounds.add(forgottenBossMusic);
            volumes.add(1f);
        }).start();
    }

    public static void changeVolumeForAllPlayers(float newVolume) {
        try {
            for (int i = 0; i < sounds.size(); i++) {
                float volume = volumes.get(i) * newVolume;
                sounds.get(i).setVolume(volume, volume);
            }
        } catch (Exception e) {
            Service.print(e.toString());
        }
    }

    public static void releaseAP() {
        try {
            for (int i = 0; i < sounds.size(); i++) {
                sounds.get(i).release();
            }
            AudioPool.release();
        } catch (Exception e) {
            Service.print("Can't release AP " + e);
        }
    }

    public static void playBoom() {
        AudioPool.playSnd(1);
    }
    public static void playShoot() {
        AudioPool.playSnd(2);
    }
    public static void playMetal() {
        AudioPool.playSnd(3);
    }
    public static void playShotgun() {
        AudioPool.playSnd(4);
    }
    public static void playMegaBoom() {
        AudioPool.playSnd(5);
    }
    public static void playReload() {
        AudioPool.playSnd(0);
    }
    public static void playFallingBomb() {
        AudioPool.playSnd(6);
    }
    public static void playHealSnd() {
        AudioPool.playSnd(9);
    }
    public static void playClick() {
        AudioPool.playSnd(7);
    }
    public static void playDeagle() {
        AudioPool.playSnd(8);
    }
    public static void playBossShoot() {
        AudioPool.playSnd(10);
    }


    public static void resumeBackgroundMusic() {
        switch (Game.level)
        {
            case 1:
                jingleMusic.start();
                break;
            case 2:
                forgottenMusic.start();
                break;
        }
    }

    public static void pauseBackgroundMusic() {
        if (jingleMusic.isPlaying()) {
            jingleMusic.pause();
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
                jingleMusic.seekTo(0);
                jingleMusic.start();
                break;
            case 2:
                AudioHub.forgottenMusic.seekTo(0);
                AudioHub.forgottenMusic.start();
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
