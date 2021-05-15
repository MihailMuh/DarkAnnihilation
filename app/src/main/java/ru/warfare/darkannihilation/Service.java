package ru.warfare.darkannihilation;

import android.util.Log;

import org.json.JSONObject;

public final class Service {
    public static final String IP = "http://78.29.33.173:49150/";
    public static final String TAG = "D'Ark";

//    public static void vibrate() {
//        ((Vibrator) game.context.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(150,10));
//    }

    public static void print(String text) {
        Log.e(TAG, text);
    }

    public static String generateJSONString(String nickname, int score) {
        try {
            JSONObject jsonScore = new JSONObject();
            jsonScore.put(nickname, score);
            return jsonScore.toString();
        } catch (Exception e) {
            Log.e(TAG, "" + e);
            return "";
        }
    }

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

    public static void pausePauseMusic() {
        if (AudioPlayer.pauseMusic.isPlaying()) {
            AudioPlayer.pauseMusic.pause();
        }
    }

    public static void restartPauseMusic() {
        pausePauseMusic();
        AudioPlayer.pauseMusic.seekTo(0);
        AudioPlayer.pauseMusic.start();
    }

    public static void pauseMenuMusic() {
        if (AudioPlayer.menuMusic.isPlaying()) {
            AudioPlayer.menuMusic.pause();
        }
    }

    public static void restartMenuMusic() {
        if (!AudioPlayer.menuMusic.isPlaying()) {
            AudioPlayer.menuMusic.seekTo(0);
            AudioPlayer.menuMusic.start();
        }
    }

    public static void pauseFlightMusic() {
        if (AudioPlayer.flightSnd.isPlaying()) {
            AudioPlayer.flightSnd.pause();
        }
    }

    public static void restartFlightMusic() {
        pauseFlightMusic();
        AudioPlayer.flightSnd.seekTo(0);
        AudioPlayer.flightSnd.start();
    }

    public static void pauseReadySound() {
        if (AudioPlayer.readySnd.isPlaying()) {
            AudioPlayer.readySnd.pause();
        }
    }

    public static void restartReadySound() {
        pauseReadySound();
        AudioPlayer.readySnd.seekTo(0);
        AudioPlayer.readySnd.start();
    }
}
