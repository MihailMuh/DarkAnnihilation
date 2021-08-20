package ru.warfare.darkannihilation.audio;

import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;

import ru.warfare.darkannihilation.R;
import ru.warfare.darkannihilation.systemd.service.Service;

import static ru.warfare.darkannihilation.audio.AudioHub.gameoverSnd;

public class GameOver extends AudioExoPlayer {
    public static void play() {
        Service.runOnUiThread(() -> {
            gameoverSnd = new SimpleExoPlayer.Builder(Service.activity).build();
            gameoverSnd.setMediaItem(getItem(R.raw.gameover_phrase));
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
        });
    }

    public static void pause() {
        if (gameoverSnd != null) {
            gameoverSnd.release();
            gameoverSnd = null;
        }
    }
}
