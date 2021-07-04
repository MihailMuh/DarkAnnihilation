package ru.warfare.darkannihilation;

import android.media.MediaPlayer;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;

import java.util.ArrayList;

public final class AudioHub {
    private static MainActivity mainActivity;

    public static ArrayList<MediaPlayer> sounds = new ArrayList<>(0);
    public static ArrayList<Float> volumes = new ArrayList<>(0);

    public static SimpleExoPlayer menuMusic;
    public static MediaPlayer pauseMusic;
    public static SimpleExoPlayer jingleMusic;
    public static SimpleExoPlayer gameoverSnd;
    public static SimpleExoPlayer bossMusic;
    public static MediaPlayer flightSnd;
    public static MediaPlayer winMusic;
    public static MediaPlayer portalSound;
    public static MediaPlayer timeMachineFirstSnd;
    public static MediaPlayer timeMachineSecondSnd;
    public static MediaPlayer timeMachineNoneSnd;
    public static MediaPlayer readySnd;
    public static MediaPlayer forgottenMusic;
    public static MediaPlayer forgottenBossMusic;
    public static SimpleExoPlayer attentionSnd;

    public static void init(MainActivity context) {
        mainActivity = context;

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
        AudioPool.addSound(context, R.raw.dynamite, 1f);
        AudioPool.addSound(context, R.raw.boom, 0.58f);
        AudioPool.addSound(context, R.raw.thunderstorm, 1f);

        String path = "android.resource://" + context.getPackageName() + "/";

//        menuMusic = MediaPlayer.create(context, R.raw.menu);
//        menuMusic.setLooping(true);
//        sounds.add(menuMusic);
//        volumes.add(1f);

        menuMusic = new SimpleExoPlayer.Builder(context).build();
        menuMusic.setMediaItem(MediaItem.fromUri(path + R.raw.menu));
        menuMusic.prepare();
        menuMusic.addListener(new Player.Listener() {
            @Override
            public void onIsPlayingChanged(boolean isPlaying) {
                if (!isPlaying) {
                    menuMusic.seekTo(0);
                }
            }
        });

        readySnd = MediaPlayer.create(context, R.raw.ready);
        sounds.add(readySnd);
        volumes.add(1f);

        attentionSnd = new SimpleExoPlayer.Builder(context).build();
        attentionSnd.setMediaItem(MediaItem.fromUri(path + R.raw.attention));
        attentionSnd.prepare();
        attentionSnd.addListener(new Player.Listener() {
            @Override
            public void onIsPlayingChanged(boolean isPlaying) {
                if (!isPlaying) {
                    if (context.game.attention != null) {
                        context.game.attention.fire();
                    }
                    attentionSnd.seekTo(0);
                    attentionSnd.pause();
                }
            }
        });

        jingleMusic = new SimpleExoPlayer.Builder(context).build();
        jingleMusic.setMediaItem(MediaItem.fromUri(path + R.raw.jingle));
        jingleMusic.prepare();
        jingleMusic.addListener(new Player.Listener() {
            @Override
            public void onIsPlayingChanged(boolean isPlaying) {
                if (!isPlaying) {
                    jingleMusic.seekTo(0);
                }
            }
        });

        gameoverSnd = new SimpleExoPlayer.Builder(context).build();
        gameoverSnd.setMediaItem(MediaItem.fromUri(path + R.raw.gameover_phrase));
        gameoverSnd.prepare();
        gameoverSnd.addListener(new Player.Listener() {
            @Override
            public void onIsPlayingChanged(boolean isPlaying) {
                if (!isPlaying) {
                    gameoverSnd.seekTo(0);
                    gameoverSnd.pause();
                }
            }
        });

        bossMusic = new SimpleExoPlayer.Builder(context).build();
        bossMusic.setMediaItem(MediaItem.fromUri(path + R.raw.shadow_boss));
        bossMusic.prepare();
        bossMusic.addListener(new Player.Listener() {
            @Override
            public void onIsPlayingChanged(boolean isPlaying) {
                if (!isPlaying) {
                    bossMusic.seekTo(0);
                }
            }
        });

//        jingleMusic = MediaPlayer.create(context, R.raw.jingle);
//        jingleMusic.setLooping(true);
//        jingleMusic.setVolume(0.5f, 0.5f);
//        sounds.add(jingleMusic);
//        volumes.add(0.5f);

//        gameoverSnd = MediaPlayer.create(context, R.raw.gameover_phrase);
//        sounds.add(gameoverSnd);
//        volumes.add(1f);

        pauseMusic = MediaPlayer.create(context, R.raw.pause);
        pauseMusic.setLooping(true);
        sounds.add(pauseMusic);
        volumes.add(0.75f);
//
//        bossMusic = MediaPlayer.create(context, R.raw.shadow_boss);
//        bossMusic.setLooping(true);
//        sounds.add(bossMusic);
//        volumes.add(0.45f);

        flightSnd = MediaPlayer.create(context, R.raw.fly);
        sounds.add(flightSnd);
        volumes.add(1f);

        winMusic = MediaPlayer.create(context, R.raw.win);
        winMusic.setLooping(true);
        sounds.add(winMusic);
        volumes.add(0.3f);

        portalSound = MediaPlayer.create(context, R.raw.portal);
        sounds.add(portalSound);
        volumes.add(0.5f);

        timeMachineFirstSnd = MediaPlayer.create(context, R.raw.time_machine);
        sounds.add(timeMachineFirstSnd);
        volumes.add(1f);

        timeMachineSecondSnd = MediaPlayer.create(context, R.raw.time_machine1);
        sounds.add(timeMachineSecondSnd);
        volumes.add(1f);

        timeMachineNoneSnd = MediaPlayer.create(context, R.raw.time_machine_none);
        sounds.add(timeMachineNoneSnd);
        volumes.add(0f);

        forgottenMusic = MediaPlayer.create(context, R.raw.forgotten_snd);
        forgottenMusic.setLooping(true);
        sounds.add(forgottenMusic);
        volumes.add(1f);

        forgottenBossMusic = MediaPlayer.create(context, R.raw.forgotten_boss);
        forgottenBossMusic.setLooping(true);
        sounds.add(forgottenBossMusic);
        volumes.add(1f);
    }

    public static void changeVolumeForAllPlayers(float newVolume) {
        mainActivity.runOnUiThread(() -> {
            for (int i = 0; i < sounds.size(); i++) {
                float volume = volumes.get(i) * newVolume;
                sounds.get(i).setVolume(volume, volume);
            }
            attentionSnd.setVolume(0.6f * newVolume);
            jingleMusic.setVolume(0.5f * newVolume);
            gameoverSnd.setVolume(newVolume);
            bossMusic.setVolume(0.45f * newVolume);
            menuMusic.setVolume(newVolume);
        });
    }

    public static void releaseAP() {
        mainActivity.runOnUiThread(() -> {
            for (int i = 0; i < sounds.size(); i++) {
                sounds.get(i).release();
            }
            AudioPool.release();
            attentionSnd.release();
            jingleMusic.release();
            gameoverSnd.release();
            bossMusic.release();
            menuMusic.release();
        });
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
    public static void playDynamite() {
        AudioPool.playSnd(11);
    }
    public static void playDynamiteBoom() {
        AudioPool.playSnd(12);
    }
    public static void playThunderStorm() {
        AudioPool.playSnd(13);
    }

    public static void resumeBackgroundMusic() {
        switch (Game.level)
        {
            case 1:
                mainActivity.runOnUiThread(() -> jingleMusic.play());
                break;
            case 2:
                forgottenMusic.start();
                break;
        }
    }

    public static void pauseBackgroundMusic() {
        mainActivity.runOnUiThread(() -> jingleMusic.pause());
//        if (jingleMusic.isPlaying()) {
//            jingleMusic.pause();
//        }
        if (forgottenMusic.isPlaying()) {
            forgottenMusic.pause();
        }
    }

    public static void restartBackgroundMusic() {
        pauseBackgroundMusic();
        switch (Game.level)
        {
            case 1:
                mainActivity.runOnUiThread(() -> {
                    jingleMusic.seekTo(0);
                    jingleMusic.play();
                });
                break;
            case 2:
                AudioHub.forgottenMusic.seekTo(0);
                AudioHub.forgottenMusic.start();
                break;
        }
    }

    public static void restartBossMusic() {
        pauseBossMusic();
        switch (Game.level)
        {
            case 1:
                mainActivity.runOnUiThread(() -> {
                    bossMusic.seekTo(0);
                    bossMusic.play();
                });
                break;
            case 2:
                forgottenBossMusic.seekTo(0);
                forgottenBossMusic.start();
                break;
        }
    }

    public static void pauseBossMusic() {
        mainActivity.runOnUiThread(() -> {
            if (bossMusic.isPlaying()) {
                bossMusic.pause();
            }
            if (forgottenBossMusic.isPlaying()) {
                forgottenBossMusic.pause();
            }
        });
    }

    public static void resumeBossMusic() {
        switch (Game.level)
        {
            case 1:
                mainActivity.runOnUiThread(() -> bossMusic.play());
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
        mainActivity.runOnUiThread(() -> menuMusic.pause());
    }

    public static void restartMenuMusic() {
        mainActivity.runOnUiThread(() -> {
            if (!menuMusic.isPlaying()) {
                menuMusic.seekTo(0);
                menuMusic.play();
            }
        });
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
