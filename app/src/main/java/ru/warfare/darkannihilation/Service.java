package ru.warfare.darkannihilation;

public class Service {
    public static void resumeBackgroundMusic() {
        switch (Game.level)
        {
            case 1:
                AudioPlayer.pirateMusic.start();
                break;
            case 2:
                AudioPlayer.forgottenMusic.start();
                break;
        }
    }

    public static void pauseBackgroundMusic() {
        if (AudioPlayer.pirateMusic.isPlaying()) {
            AudioPlayer.pirateMusic.pause();
        }
        if (AudioPlayer.forgottenMusic.isPlaying()) {
            AudioPlayer.forgottenMusic.pause();
        }
    }

    public static void restartBackgroundMusic() {
        switch (Game.level)
        {
            case 1:
                if (AudioPlayer.pirateMusic.isPlaying()) {
                    AudioPlayer.pirateMusic.pause();
                }
                AudioPlayer.pirateMusic.seekTo(0);
                AudioPlayer.pirateMusic.start();
                break;
            case 2:
                if (AudioPlayer.forgottenMusic.isPlaying()) {
                    AudioPlayer.forgottenMusic.pause();
                }
                AudioPlayer.forgottenMusic.seekTo(0);
                AudioPlayer.forgottenMusic.start();
                break;
        }
    }

    public static void restartBossMusic() {
        switch (Game.level)
        {
            case 1:
                if (AudioPlayer.bossMusic.isPlaying()) {
                    AudioPlayer.bossMusic.pause();
                }
                AudioPlayer.bossMusic.seekTo(0);
                AudioPlayer.bossMusic.start();
                break;
            case 2:
                if (AudioPlayer.forgottenBossMusic.isPlaying()) {
                    AudioPlayer.forgottenBossMusic.pause();
                }
                AudioPlayer.forgottenBossMusic.seekTo(0);
                AudioPlayer.forgottenBossMusic.start();
                break;
        }
    }

    public static void pauseBossMusic() {
        if (AudioPlayer.bossMusic.isPlaying()) {
            AudioPlayer.bossMusic.pause();
        }
        if (AudioPlayer.forgottenBossMusic.isPlaying()) {
            AudioPlayer.forgottenBossMusic.pause();
        }
    }

    public static void resumeBossMusic() {
        switch (Game.level)
        {
            case 1:
                AudioPlayer.bossMusic.start();
                break;
            case 2:
                AudioPlayer.forgottenBossMusic.start();
                break;
        }
    }
}
