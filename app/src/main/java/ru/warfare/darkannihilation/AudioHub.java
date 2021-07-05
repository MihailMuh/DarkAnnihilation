package ru.warfare.darkannihilation;

import android.media.MediaPlayer;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;

import java.util.ArrayList;

public final class AudioHub {
    private static MainActivity mainActivity;
    private static String path;
    private static float volume;

    public static final AudioPool audioPool = new AudioPool();

    public static ArrayList<MediaPlayer> sounds = new ArrayList<>(0);
    public static ArrayList<Float> volumes = new ArrayList<>(0);

    public static SimpleExoPlayer menuMusic;
    public static MediaPlayer pauseMusic;
    public static SimpleExoPlayer jingleMusic;
    public static SimpleExoPlayer gameoverSnd;
    public static SimpleExoPlayer bossMusic;
    public static MediaPlayer flightSnd;
    public static MediaPlayer winMusic;
    public static SimpleExoPlayer portalSound;
    public static SimpleExoPlayer timeMachineSnd;
    public static SimpleExoPlayer timeMachineSecondSnd;
    //    public static SimpleExoPlayer timeMachineNoneSnd;
    public static MediaPlayer readySnd;
    public static SimpleExoPlayer forgottenMusic;
    public static SimpleExoPlayer forgottenBossMusic;
    public static SimpleExoPlayer attentionSnd;

    private static int reloadSnd;
    private static int boomSnd;
    private static int laserSnd;
    private static int metalSnd;
    private static int shotgunSnd;
    private static int megaBoomSnd;
    private static int fallingBombSnd;
    private static int clickSnd;
    private static int deagleSnd;
    private static int healSnd;
    private static int bossShootSnd;
    private static int dynamiteSnd;
    private static int dynamiteBoomSnd;
    private static int thunderstormSnd;

    public static void init(MainActivity context) {
        mainActivity = context;
        path = "android.resource://" + context.getPackageName() + "/";

        reloadSnd = audioPool.addSoundsToPack(context, new float[][]{{R.raw.reload0, 1f}, {R.raw.reload1, 1f}});
        boomSnd = audioPool.addSound(context, R.raw.boom, 0.13f);
        laserSnd = audioPool.addSound(context, R.raw.laser, 0.17f);
        metalSnd = audioPool.addSound(context, R.raw.metal, 0.45f);
        shotgunSnd = audioPool.addSound(context, R.raw.shotgun, 0.25f);
        megaBoomSnd = audioPool.addSound(context, R.raw.megaboom, 1f);
        fallingBombSnd = audioPool.addSound(context, R.raw.falling_bomb, 0.2f);
        clickSnd = audioPool.addSound(context, R.raw.spacebar, 1f);
        deagleSnd = audioPool.addSound(context, R.raw.deagle, 1f);
        healSnd = audioPool.addSound(context, R.raw.heal, 0.8f);
        bossShootSnd = audioPool.addSound(context, R.raw.boss_shoot, 1f);
        dynamiteSnd = audioPool.addSound(context, R.raw.dynamite, 1f);
        dynamiteBoomSnd = audioPool.addSound(context, R.raw.boom, 0.58f);
        thunderstormSnd = audioPool.addSound(context, R.raw.thunderstorm, 1f);

        menuMusic = new SimpleExoPlayer.Builder(context).build();
        menuMusic.setMediaItem(MediaItem.fromUri(path + R.raw.menu));
        menuMusic.setRepeatMode(Player.REPEAT_MODE_ONE);
        menuMusic.prepare();

        readySnd = MediaPlayer.create(context, R.raw.ready);
        sounds.add(readySnd);
        volumes.add(1f);

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

        pauseMusic = MediaPlayer.create(context, R.raw.pause);
        pauseMusic.setLooping(true);
        sounds.add(pauseMusic);
        volumes.add(0.75f);

        flightSnd = MediaPlayer.create(context, R.raw.fly);
        sounds.add(flightSnd);
        volumes.add(1f);

        winMusic = MediaPlayer.create(context, R.raw.win);
        winMusic.setLooping(true);
        sounds.add(winMusic);
        volumes.add(0.3f);
    }

    public static void newVolumeForBackground(float newVolume) {
        mainActivity.runOnUiThread(() -> {
            volume = newVolume;
            for (int i = 0; i < sounds.size(); i++) {
                float volume = volumes.get(i) * newVolume;
                sounds.get(i).setVolume(volume, volume);
            }
            gameoverSnd.setVolume(newVolume);
            menuMusic.setVolume(newVolume);
        });
    }

    public static void newVolumeForEffects(float newVolume) {
        audioPool.newVolume(newVolume);
    }

    public static void releaseAP() {
        mainActivity.runOnUiThread(() -> {
            for (int i = 0; i < sounds.size(); i++) {
                sounds.get(i).release();
            }
            audioPool.release();
//            attentionSnd.release();
//            jingleMusic.release();
//            gameoverSnd.release();
//            bossMusic.release();
//            menuMusic.release();
//            forgottenMusic.release();
//            forgottenBossMusic.release();
//            portalSound.release();
//            timeMachineSnd.release();
//            timeMachineSecondSnd.release();
//            timeMachineNoneSnd.release();
        });
    }

    public static void playBoom() {
        audioPool.play(boomSnd);
    }

    public static void playShoot() {
        audioPool.play(laserSnd);
    }

    public static void playMetal() {
        audioPool.play(metalSnd);
    }

    public static void playShotgun() {
        audioPool.play(shotgunSnd);
    }

    public static void playMegaBoom() {
        audioPool.play(megaBoomSnd);
    }

    public static void playReload() {
        audioPool.playFromPack(reloadSnd);
    }

    public static void playFallingBomb() {
        audioPool.play(fallingBombSnd);
    }

    public static void playHealSnd() {
        audioPool.play(healSnd);
    }

    public static void playClick() {
        audioPool.play(clickSnd);
    }

    public static void playDeagle() {
        audioPool.play(deagleSnd);
    }

    public static void playBossShoot() {
        audioPool.play(bossShootSnd);
    }

    public static void playDynamite() {
        audioPool.play(dynamiteSnd);
    }

    public static void playDynamiteBoom() {
        audioPool.play(dynamiteBoomSnd);
    }

    public static void playThunderStorm() {
        audioPool.play(thunderstormSnd);
    }

    public static void soundOfClick(float volume) {
        audioPool.newVolumeForSnd(clickSnd, volume);
    }

    public static void loadFirstLevelSounds() {
        mainActivity.runOnUiThread(() -> {
            if (forgottenMusic != null) {
                forgottenMusic.release();
                forgottenBossMusic.release();
            }

            if (attentionSnd == null) {
                jingleMusic = new SimpleExoPlayer.Builder(mainActivity).build();
                jingleMusic.setMediaItem(MediaItem.fromUri(path + R.raw.jingle));
                jingleMusic.setVolume(0.5f * volume);
                jingleMusic.setRepeatMode(Player.REPEAT_MODE_ONE);
                jingleMusic.prepare();
                jingleMusic.play();

                attentionSnd = new SimpleExoPlayer.Builder(mainActivity).build();
                attentionSnd.setMediaItem(MediaItem.fromUri(path + R.raw.attention));
                attentionSnd.setVolume(0.6f * volume);
                attentionSnd.prepare();
                attentionSnd.addListener(new Player.Listener() {
                    @Override
                    public void onIsPlayingChanged(boolean isPlaying) {
                        if (!isPlaying) {
                            mainActivity.game.attention.fire();
                            attentionSnd.seekTo(0);
                            attentionSnd.pause();
                        }
                    }
                });

                bossMusic = new SimpleExoPlayer.Builder(mainActivity).build();
                bossMusic.setMediaItem(MediaItem.fromUri(path + R.raw.shadow_boss));
                bossMusic.setVolume(0.45f * volume);
                bossMusic.setRepeatMode(Player.REPEAT_MODE_ONE);
                bossMusic.prepare();
            } else {
                restartBackgroundMusic();
            }
        });
    }

    public static void loadSecondLevelSounds() {
        mainActivity.runOnUiThread(() -> {
            if (attentionSnd != null) {
                attentionSnd.release();
                jingleMusic.release();
                bossMusic.release();
            }

            if (forgottenMusic == null) {
                forgottenMusic = new SimpleExoPlayer.Builder(mainActivity).build();
                forgottenMusic.setMediaItem(MediaItem.fromUri(path + R.raw.forgotten_snd));
                forgottenMusic.setVolume(volume);
                forgottenMusic.setRepeatMode(Player.REPEAT_MODE_ONE);
                forgottenMusic.prepare();
                forgottenMusic.play();

                forgottenBossMusic = new SimpleExoPlayer.Builder(mainActivity).build();
                forgottenBossMusic.setMediaItem(MediaItem.fromUri(path + R.raw.forgotten_boss));
                forgottenBossMusic.setVolume(volume);
                forgottenBossMusic.setRepeatMode(Player.REPEAT_MODE_ONE);
                forgottenBossMusic.prepare();
            }
        });
    }

    public static void loadPortalSounds() {
        portalSound = new SimpleExoPlayer.Builder(mainActivity).build();
        portalSound.setMediaItem(MediaItem.fromUri(path + R.raw.portal));
        portalSound.setVolume(0.5f * volume);
        portalSound.prepare();
        portalSound.play();
        portalSound.addListener(new Player.Listener() {
            @Override
            public void onIsPlayingChanged(boolean isPlaying) {
                if (!isPlaying) {
                    portalSound.release();
                }
            }
        });

        timeMachineSecondSnd = new SimpleExoPlayer.Builder(mainActivity).build();
        timeMachineSecondSnd.setMediaItem(MediaItem.fromUri(path + R.raw.time_machine1));
        timeMachineSecondSnd.setVolume(volume);
        timeMachineSecondSnd.prepare();
        timeMachineSecondSnd.addListener(new Player.Listener() {
            @Override
            public void onIsPlayingChanged(boolean isPlaying) {
                if (!isPlaying) {
                    timeMachineSecondSnd.release();
                }
            }
        });

        timeMachineSnd = new SimpleExoPlayer.Builder(mainActivity).build();
        timeMachineSnd.setMediaItem(MediaItem.fromUri(path + R.raw.time_machine));
        timeMachineSnd.setVolume(volume);
        timeMachineSnd.prepare();
        timeMachineSnd.addListener(new Player.Listener() {
            @Override
            public void onIsPlayingChanged(boolean isPlaying) {
                if (!isPlaying) {
                    timeMachineSnd.release();
                    timeMachineSecondSnd.play();
                    Game.level++;
                    mainActivity.game.loadingScreen.newJob("newGame");
                    mainActivity.game.portal.kill();
                }
            }
        });
    }

    public static void resumeBackgroundMusic() {
        mainActivity.runOnUiThread(() -> {
            switch (Game.level) {
                case 1:
                    jingleMusic.play();
                    break;
                case 2:
                    forgottenMusic.play();
                    break;
            }
        });
    }

    public static void pauseBackgroundMusic() {
        mainActivity.runOnUiThread(() -> {
            if (jingleMusic != null) {
                if (jingleMusic.isPlaying()) {
                    jingleMusic.pause();
                }
            }
            if (forgottenMusic != null) {
                if (forgottenMusic.isPlaying()) {
                    forgottenMusic.pause();
                }
            }
        });
    }

    public static void restartBackgroundMusic() {
        pauseBackgroundMusic();
        mainActivity.runOnUiThread(() -> {
            switch (Game.level) {
                case 1:
                    jingleMusic.seekTo(0);
                    jingleMusic.play();
                    break;
                case 2:
                    forgottenMusic.seekTo(0);
                    forgottenMusic.play();
                    break;
            }
        });
    }

    public static void restartBossMusic() {
        pauseBossMusic();
        mainActivity.runOnUiThread(() -> {
            switch (Game.level) {
                case 1:
                    bossMusic.seekTo(0);
                    bossMusic.play();
                    break;
                case 2:
                    forgottenBossMusic.seekTo(0);
                    forgottenBossMusic.play();
                    break;
            }
        });
    }

    public static void pauseBossMusic() {
        mainActivity.runOnUiThread(() -> {
            if (bossMusic != null) {
                if (bossMusic.isPlaying()) {
                    bossMusic.pause();
                }
            }
            if (forgottenBossMusic != null) {
                if (forgottenBossMusic.isPlaying()) {
                    forgottenBossMusic.pause();
                }
            }
        });
    }

    public static void resumeBossMusic() {
        mainActivity.runOnUiThread(() -> {
            switch (Game.level) {
                case 1:
                    bossMusic.play();
                    break;
                case 2:
                    forgottenBossMusic.play();
                    break;
            }
        });
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
