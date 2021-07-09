package ru.warfare.darkannihilation.hub;

import android.media.MediaPlayer;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;

import java.util.ArrayList;
import java.util.Arrays;

import ru.warfare.darkannihilation.AudioPool;
import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.systemd.MainActivity;
import ru.warfare.darkannihilation.R;
import ru.warfare.darkannihilation.systemd.Service;

public final class AudioHub {
    private static MainActivity mainActivity;
    private static String path;
    private static volatile float volume;

    public static final AudioPool audioPool = new AudioPool();

    public static ArrayList<MediaPlayer> sounds = new ArrayList<>(0);
    public static ArrayList<Float> volumes = new ArrayList<>(0);
    private static final boolean[] playing = new boolean[12];

    public static SimpleExoPlayer menuMusic;
    public static MediaPlayer pauseMusic;
    public static SimpleExoPlayer jingleMusic;
    public static SimpleExoPlayer gameoverSnd;
    public static SimpleExoPlayer bossMusic;
    public static MediaPlayer flightSnd;
    public static MediaPlayer winMusic;
    public static SimpleExoPlayer portalSound;
    private static SimpleExoPlayer timeMachineSnd;
    private static SimpleExoPlayer timeMachineSecondSnd;
    private static SimpleExoPlayer timeMachineNoneSnd;
    public static SimpleExoPlayer readySnd;
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
        playing[3] = true;

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

        menuMusic = new SimpleExoPlayer.Builder(context).build();
        menuMusic.setMediaItem(MediaItem.fromUri(path + R.raw.menu));
        menuMusic.setRepeatMode(Player.REPEAT_MODE_ONE);
        menuMusic.prepare();
    }

    public static void newVolumeForBackground(float newVolume) {
        volume = newVolume;
        Service.runOnUiThread(() -> {
            for (int i = 0; i < sounds.size(); i++) {
                float volume = volumes.get(i) * newVolume;
                sounds.get(i).setVolume(volume, volume);
            }
            menuMusic.setVolume(newVolume);
        });
    }

    public static void newVolumeForEffects(float newVolume) {
        audioPool.newVolume(newVolume);
    }

    public static void releaseAP() {
        Service.runOnUiThread(() -> {
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
        Service.runOnUiThread(() -> {
            if (forgottenMusic != null) {
                forgottenMusic.release();
                forgottenMusic = null;
                forgottenBossMusic.release();
                forgottenBossMusic = null;
            }

            if (jingleMusic == null) {
                jingleMusic = new SimpleExoPlayer.Builder(mainActivity).build();
                jingleMusic.setMediaItem(MediaItem.fromUri(path + R.raw.jingle));
                jingleMusic.setVolume(0.5f * volume);
                jingleMusic.setRepeatMode(Player.REPEAT_MODE_ONE);
                jingleMusic.prepare();
                jingleMusic.play();
                jingleMusic.addListener(new Player.Listener() {
                    @Override
                    public void onPlaybackStateChanged(int state) {
                        if (state == Player.STATE_READY) {
                            if (Game.gameStatus == 4) {
                                jingleMusic.pause();
                                playing[1] = true;
                            }
                        }
                    }
                });
                attentionSnd = new SimpleExoPlayer.Builder(mainActivity).build();
                attentionSnd.setMediaItem(MediaItem.fromUri(path + R.raw.attention));
                attentionSnd.setVolume(0.6f * volume);
                attentionSnd.prepare();
                attentionSnd.addListener(new Player.Listener() {
                    @Override
                    public void onPlaybackStateChanged(int state) {
                        if (state == Player.STATE_ENDED) {
                            mainActivity.game.attention.fire();
                            attentionSnd.pause();
                            attentionSnd.seekTo(0);
                        }
                    }
                });

                bossMusic = new SimpleExoPlayer.Builder(mainActivity).build();
                bossMusic.setMediaItem(MediaItem.fromUri(path + R.raw.shadow_boss));
                bossMusic.setVolume(0.45f * volume);
                bossMusic.setRepeatMode(Player.REPEAT_MODE_ONE);
                bossMusic.prepare();
            } else {
                update(jingleMusic);
                attentionSnd.seekTo(0);
                attentionSnd.pause();
                bossMusic.seekTo(0);
                bossMusic.pause();
            }
        });
    }

    public static void loadSecondLevelSounds() {
        Service.runOnUiThread(() -> {
            if (attentionSnd != null) {
                attentionSnd.release();
                attentionSnd = null;
                jingleMusic.release();
                jingleMusic = null;
                bossMusic.release();
                bossMusic = null;
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
            } else {
                update(forgottenMusic);
                forgottenBossMusic.seekTo(0);
                forgottenBossMusic.pause();
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
            public void onPlaybackStateChanged(int state) {
                if (state == Player.STATE_ENDED) {
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
            public void onPlaybackStateChanged(int state) {
                if (state == Player.STATE_ENDED) {
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
            public void onPlaybackStateChanged(int state) {
                if (state == Player.STATE_ENDED) {
                    timeMachineSnd.release();
                }
            }
        });

        timeMachineNoneSnd = new SimpleExoPlayer.Builder(mainActivity).build();
        timeMachineNoneSnd.setMediaItem(MediaItem.fromUri(path + R.raw.time_machine_none));
        timeMachineNoneSnd.setVolume(0);
        timeMachineNoneSnd.prepare();
        timeMachineNoneSnd.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int state) {
                if (state == Player.STATE_ENDED) {
                    timeMachineSecondSnd.play();
                    timeMachineNoneSnd.release();
                    Game.level++;
                    mainActivity.game.loadingScreen.newJob("newGame");
                    mainActivity.game.portal.kill();
                }
            }
        });
    }

    public static void playTimeMachine() {
        Service.runOnUiThread(() -> {
            timeMachineNoneSnd.play();
            timeMachineSnd.play();
        });
    }

    public static void loadMenuSnd() {
        Service.runOnUiThread(() -> {
            if (menuMusic == null) {
                menuMusic = new SimpleExoPlayer.Builder(mainActivity).build();
                menuMusic.setMediaItem(MediaItem.fromUri(path + R.raw.menu));
                menuMusic.setRepeatMode(Player.REPEAT_MODE_ONE);
                menuMusic.setVolume(volume);
                menuMusic.prepare();
                menuMusic.play();
            }
        });
    }

    public static void deleteMenuSnd() {
        Service.runOnUiThread(() -> {
            if (menuMusic != null) {
                menuMusic.release();
                menuMusic = null;
            }
        });
    }

    public static void playReadySnd() {
        Service.runOnUiThread(() -> {
            if (readySnd == null) {
                readySnd = new SimpleExoPlayer.Builder(mainActivity).build();
                readySnd.setMediaItem(MediaItem.fromUri(path + R.raw.ready));
                readySnd.setVolume(volume);
                readySnd.prepare();
                readySnd.play();
                readySnd.addListener(new Player.Listener() {
                    @Override
                    public void onPlaybackStateChanged(int state) {
                        if (state == Player.STATE_ENDED) {
                            readySnd.release();
                            readySnd = null;
                        } else {
                            if (state == Player.STATE_READY) {
                                if (Game.gameStatus == 4) {
                                    readySnd.pause();
                                    playing[2] = true;
                                }
                            }
                        }
                    }
                });
            } else {
                update(readySnd);
            }
        });
    }

    public static void playGameOverSnd() {
        if (gameoverSnd == null) {
            gameoverSnd = new SimpleExoPlayer.Builder(mainActivity).build();
            gameoverSnd.setMediaItem(MediaItem.fromUri(path + R.raw.gameover_phrase));
            gameoverSnd.setVolume(volume);
            gameoverSnd.prepare();
            gameoverSnd.play();
            gameoverSnd.addListener(new Player.Listener() {
                @Override
                public void onPlaybackStateChanged(int state) {
                    if (state == Player.STATE_ENDED) {
                        gameoverSnd.release();
                        gameoverSnd = null;
                    }
                }
            });
        } else {
            update(gameoverSnd);
        }
    }

    public static void stopAndSavePlaying() {
        Service.runOnUiThread(() -> {
            Arrays.fill(playing, false);
            playing[0] = stopIfPlaying(attentionSnd);
            playing[1] = stopIfPlaying(jingleMusic);
            playing[2] = stopIfPlaying(readySnd);
            playing[3] = stopIfPlaying(menuMusic);
            playing[4] = stopIfPlaying(gameoverSnd);
            playing[5] = stopIfPlaying(bossMusic);
            playing[6] = stopIfPlaying(portalSound);
            playing[7] = stopIfPlaying(timeMachineSnd);
            playing[8] = stopIfPlaying(timeMachineSecondSnd);
            playing[9] = stopIfPlaying(timeMachineNoneSnd);
            playing[10] = stopIfPlaying(forgottenMusic);
            playing[11] = stopIfPlaying(forgottenBossMusic);
        });
    }

    public static void whoIsPlayed() {
        Service.runOnUiThread(() -> {
            setPlaying(attentionSnd, playing[0]);
            setPlaying(jingleMusic, playing[1]);
            setPlaying(readySnd, playing[2]);
            setPlaying(menuMusic, playing[3]);
            setPlaying(gameoverSnd, playing[4]);
            setPlaying(bossMusic, playing[5]);
            setPlaying(portalSound, playing[6]);
            setPlaying(timeMachineSnd, playing[7]);
            setPlaying(timeMachineSecondSnd, playing[8]);
            setPlaying(timeMachineNoneSnd, playing[9]);
            setPlaying(forgottenMusic, playing[10]);
            setPlaying(forgottenBossMusic, playing[11]);
        });
    }

    public static void resumeBackgroundMusic() {
        Service.runOnUiThread(() -> {
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
        Service.runOnUiThread(() -> {
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

    public static void restartBossMusic() {
        pauseBossMusic();
        Service.runOnUiThread(() -> {
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
        Service.runOnUiThread(() -> {
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

    public static void setPlaying(SimpleExoPlayer exoPlayer, boolean isPlaying) {
        if (exoPlayer != null) {
            exoPlayer.setPlayWhenReady(isPlaying);
        }
    }

    public static boolean stopIfPlaying(SimpleExoPlayer exoPlayer) {
        if (exoPlayer != null) {
            if (exoPlayer.isPlaying()) {
                exoPlayer.pause();
                return true;
            }
        }
        return false;
    }

    public static void update(SimpleExoPlayer exoPlayer) {
        exoPlayer.setVolume(volume);
        exoPlayer.seekTo(0);
        if (!exoPlayer.isPlaying()) {
            exoPlayer.play();
        }
    }
}
