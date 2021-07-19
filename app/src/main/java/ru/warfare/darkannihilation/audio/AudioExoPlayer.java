package ru.warfare.darkannihilation.audio;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;

import ru.warfare.darkannihilation.systemd.Service;

public class AudioExoPlayer {
    public static volatile float volume = 1f;

    public static void newVolumeForBackground(float newVolume) {
        volume = newVolume;
    }

    static void setPlaying(SimpleExoPlayer exoPlayer, boolean isPlaying) {
        if (exoPlayer != null) {
            exoPlayer.setPlayWhenReady(isPlaying);
        }
    }

    static boolean stopIfPlaying(SimpleExoPlayer exoPlayer) {
        if (exoPlayer != null) {
            if (exoPlayer.isPlaying()) {
                exoPlayer.pause();
                return true;
            }
        }
        return false;
    }

    static void update(SimpleExoPlayer exoPlayer) {
        exoPlayer.seekTo(0);
        if (!exoPlayer.isPlaying()) {
            exoPlayer.play();
        }
    }

    static void release(SimpleExoPlayer simpleExoPlayer) {
        if (simpleExoPlayer != null) {
            simpleExoPlayer.release();
        }
    }

    static MediaItem getItem(int id) {
        return MediaItem.fromUri(Service.getResPath() + id);
    }
}
