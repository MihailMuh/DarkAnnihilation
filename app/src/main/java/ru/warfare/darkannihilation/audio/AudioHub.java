package ru.warfare.darkannihilation.audio;

import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;

import java.util.Arrays;

import ru.warfare.darkannihilation.HardThread;
import ru.warfare.darkannihilation.R;
import ru.warfare.darkannihilation.math.Math;
import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.systemd.MainActivity;
import ru.warfare.darkannihilation.systemd.Service;

public final class AudioHub extends AudioExoPlayer {
    private static MainActivity mainActivity;
    private static volatile boolean alreadySaved = false;

    private static final AudioPool audioPool = new AudioPool();
    private static final boolean[] playing = new boolean[15];

    private static SimpleExoPlayer pauseMusic;
    private static SimpleExoPlayer jingleMusic;
    static SimpleExoPlayer gameoverSnd;
    private static SimpleExoPlayer bossMusic;
    private static SimpleExoPlayer flightSnd;
    private static SimpleExoPlayer timeMachineSnd;
    private static SimpleExoPlayer timeMachineSecondSnd;
    private static SimpleExoPlayer timeMachineNoneSnd;
    private static SimpleExoPlayer readySnd;
    private static SimpleExoPlayer forgottenMusic;
    private static SimpleExoPlayer forgottenBossMusic;
    private static SimpleExoPlayer portalSound;
    private static SimpleExoPlayer attentionSnd;
    private static SimpleExoPlayer winMusic;
    public static SimpleExoPlayer menuMusic;

    private static int reloadSnd1;
    private static int reloadSnd2;
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

    public static void init(MainActivity app) {
        mainActivity = app;
        HardThread.doInBackGround(() -> {
            Arrays.fill(playing, false);
            reloadSnd1 = audioPool.addSound(mainActivity, R.raw.reload0, 1f);
            reloadSnd2 = audioPool.addSound(mainActivity, R.raw.reload1, 1f);
            boomSnd = audioPool.addSound(mainActivity, R.raw.boom, 0.13f);
            laserSnd = audioPool.addSound(mainActivity, R.raw.laser, 0.17f);
            metalSnd = audioPool.addSound(mainActivity, R.raw.metal, 0.45f);
            shotgunSnd = audioPool.addSound(mainActivity, R.raw.shotgun, 0.25f);
            megaBoomSnd = audioPool.addSound(mainActivity, R.raw.megaboom, 1f);
            fallingBombSnd = audioPool.addSound(mainActivity, R.raw.falling_bomb, 0.2f);
            clickSnd = audioPool.addSound(mainActivity, R.raw.spacebar, 1f);
            deagleSnd = audioPool.addSound(mainActivity, R.raw.deagle, 1f);
            healSnd = audioPool.addSound(mainActivity, R.raw.heal, 0.8f);
            bossShootSnd = audioPool.addSound(mainActivity, R.raw.boss_shoot, 1f);
            dynamiteSnd = audioPool.addSound(mainActivity, R.raw.dynamite, 1f);
            dynamiteBoomSnd = audioPool.addSound(mainActivity, R.raw.boom, 0.58f);
            thunderstormSnd = audioPool.addSound(mainActivity, R.raw.thunderstorm, 1f);
        });
    }

    public static void newVolumeForEffects(float newVolume) {
        audioPool.newVolume(newVolume);
    }

    public static void releaseAP() {
        Service.runOnUiThread(() -> {
            release(attentionSnd);
            release(jingleMusic);
            release(gameoverSnd);
            release(bossMusic);
            release(menuMusic);
            release(forgottenBossMusic);
            release(forgottenMusic);
            release(portalSound);
            release(timeMachineNoneSnd);
            release(timeMachineSecondSnd);
            release(timeMachineSnd);
            release(pauseMusic);

            audioPool.release();
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
        if (Math.randInt(0, 1) == 0) {
            audioPool.play(reloadSnd1);
        } else {
            audioPool.play(reloadSnd2);
        }
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

    public static void playAttentionSnd() {
        HardThread.doInUI(() -> attentionSnd.play());
    }

    public static void loadFirstLevelSounds() {
        if (forgottenMusic != null) {
            forgottenMusic.release();
            forgottenMusic = null;
            forgottenBossMusic.release();
            forgottenBossMusic = null;
        }

        if (jingleMusic == null) {
            jingleMusic = new SimpleExoPlayer.Builder(mainActivity).build();
            jingleMusic.setMediaItem(getItem(R.raw.jingle));
            jingleMusic.setVolume(0.5f * volume);
            jingleMusic.setRepeatMode(Player.REPEAT_MODE_ONE);
            jingleMusic.prepare();
            jingleMusic.play();
            jingleMusic.addListener(new Player.Listener() {
                @Override
                public void onPlaybackStateChanged(int state) {
                    if (state == Player.STATE_READY) {
                        if (Game.gameStatus == 4 | !mainActivity.game.playing) {
                            jingleMusic.pause();
                            playing[1] = true;
                        }
                    }
                }
            });
            attentionSnd = new SimpleExoPlayer.Builder(mainActivity).build();
            attentionSnd.setMediaItem(getItem(R.raw.attention));
            attentionSnd.setVolume(0.6f * volume);
            attentionSnd.prepare();
            attentionSnd.addListener(new Player.Listener() {
                @Override
                public void onPlaybackStateChanged(int state) {
                    if (state == Player.STATE_ENDED) {
                        attentionSnd.pause();
                        mainActivity.game.attention.fire();
                        attentionSnd.seekTo(0);
                    }
                }
            });

            bossMusic = new SimpleExoPlayer.Builder(mainActivity).build();
            bossMusic.setMediaItem(getItem(R.raw.shadow_boss));
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
    }

    public static void loadSecondLevelSounds() {
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
            forgottenMusic.setMediaItem(getItem(R.raw.forgotten_snd));
            forgottenMusic.setVolume(volume);
            forgottenMusic.setRepeatMode(Player.REPEAT_MODE_ONE);
            forgottenMusic.prepare();
            forgottenMusic.play();

            forgottenBossMusic = new SimpleExoPlayer.Builder(mainActivity).build();
            forgottenBossMusic.setMediaItem(getItem(R.raw.forgotten_boss));
            forgottenBossMusic.setVolume(volume);
            forgottenBossMusic.setRepeatMode(Player.REPEAT_MODE_ONE);
            forgottenBossMusic.prepare();
            forgottenBossMusic.addListener(new Player.Listener() {
                @Override
                public void onPlaybackStateChanged(int state) {
                    if (state == Player.STATE_READY) {
                        if (Game.gameStatus == 7) {
                            forgottenBossMusic.pause();
                        }
                    }
                }
            });
        } else {
            update(forgottenMusic);
            forgottenBossMusic.seekTo(0);
            forgottenBossMusic.pause();
        }
    }

    public static void loadPortalSounds() {
        Service.runOnUiThread(() -> {
            portalSound = new SimpleExoPlayer.Builder(mainActivity).build();
            portalSound.setMediaItem(getItem(R.raw.portal));
            portalSound.setVolume(0.5f * volume);
            portalSound.prepare();
            portalSound.play();
            portalSound.addListener(new Player.Listener() {
                @Override
                public void onPlaybackStateChanged(int state) {
                    if (state == Player.STATE_ENDED) {
                        deletePortalSnd();
                    }
                }
            });

            timeMachineSecondSnd = new SimpleExoPlayer.Builder(mainActivity).build();
            timeMachineSecondSnd.setMediaItem(getItem(R.raw.time_machine1));
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
            timeMachineSnd.setMediaItem(getItem(R.raw.time_machine));
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
            timeMachineNoneSnd.setMediaItem(getItem(R.raw.time_machine_none));
            timeMachineNoneSnd.setVolume(0);
            timeMachineNoneSnd.prepare();
            timeMachineNoneSnd.addListener(new Player.Listener() {
                @Override
                public void onPlaybackStateChanged(int state) {
                    if (state == Player.STATE_ENDED) {
                        timeMachineSecondSnd.play();
                        timeMachineNoneSnd.release();
                        mainActivity.game.onLoading(() -> {
                            Game.level++;
                            mainActivity.game.portal.kill();
                            mainActivity.game.generateNewGame();
                        });
                    }
                }
            });
        });
    }

    public static void deletePortalSnd() {
        if (portalSound != null) {
            portalSound.release();
            portalSound = null;
        }
    }

    public static void playTimeMachine() {
        timeMachineNoneSnd.play();
        timeMachineSnd.play();
    }

    public static void loadMenuSnd() {
        if (menuMusic == null) {
            menuMusic = new SimpleExoPlayer.Builder(mainActivity).build();
            menuMusic.setMediaItem(getItem(R.raw.menu));
            menuMusic.setRepeatMode(Player.REPEAT_MODE_ONE);
            menuMusic.setVolume(volume);
            menuMusic.prepare();
            menuMusic.play();
        }
    }

    public static void deleteMenuSnd() {
        if (menuMusic != null) {
            menuMusic.release();
            menuMusic = null;
        }
    }

    public static void playReadySnd() {
        if (readySnd == null) {
            readySnd = new SimpleExoPlayer.Builder(mainActivity).build();
            readySnd.setMediaItem(getItem(R.raw.ready));
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
                            if (Game.gameStatus == 4 | !mainActivity.game.playing) {
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
    }

    public static void playPauseMusic() {
        HardThread.doInUI(() -> {
            if (pauseMusic == null) {
                pauseMusic = new SimpleExoPlayer.Builder(mainActivity).build();
                pauseMusic.setMediaItem(getItem(R.raw.pause));
                pauseMusic.setVolume(0.75f * volume);
                pauseMusic.setRepeatMode(Player.REPEAT_MODE_ONE);
                pauseMusic.prepare();
                pauseMusic.play();
            } else {
                update(pauseMusic);
            }
        });
    }

    public static void deletePauseMusic() {
        Service.runOnUiThread(() -> {
            if (pauseMusic != null) {
                pauseMusic.release();
                pauseMusic = null;
            }
        });
    }

    public static void playFlightSnd() {
        if (flightSnd == null) {
            flightSnd = new SimpleExoPlayer.Builder(mainActivity).build();
            flightSnd.setMediaItem(getItem(R.raw.fly));
            flightSnd.setVolume(volume);
            flightSnd.prepare();
            flightSnd.play();
            flightSnd.addListener(new Player.Listener() {
                @Override
                public void onPlaybackStateChanged(int state) {
                    if (state == Player.STATE_ENDED) {
                        flightSnd.release();
                        flightSnd = null;
                    }
                }
            });
        }
    }

    public static void playWinMusic() {
        winMusic = new SimpleExoPlayer.Builder(mainActivity).build();
        winMusic.setMediaItem(getItem(R.raw.win));
        winMusic.setVolume(0.3f * volume);
        winMusic.setRepeatMode(Player.REPEAT_MODE_ONE);
        winMusic.prepare();
        winMusic.play();
    }

    public static void deleteWinMusic() {
        if (winMusic != null) {
            winMusic.release();
            winMusic = null;
        }
    }

    public static void stopAndSavePlaying() {
        Service.runOnUiThread(() -> {
            if (!alreadySaved) {
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
                playing[13] = stopIfPlaying(flightSnd);
                playing[14] = stopIfPlaying(winMusic);
                alreadySaved = true;
            } else {
                playing[12] = stopIfPlaying(pauseMusic);
                alreadySaved = false;
            }
        });
    }

    public static void whoIsPlayed() {
        Service.runOnUiThread(() -> {
            if (alreadySaved) {
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
                setPlaying(flightSnd, playing[13]);
                setPlaying(winMusic, playing[14]);
                alreadySaved = false;
            } else {
                setPlaying(pauseMusic, playing[12]);
                alreadySaved = true;
            }
        });
    }

    public static void clearStatus() {
        alreadySaved = false;
        Arrays.fill(playing, false);
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
}
