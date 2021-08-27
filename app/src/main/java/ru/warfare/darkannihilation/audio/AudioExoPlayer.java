package ru.warfare.darkannihilation.audio;

import androidx.annotation.NonNull;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;

import ru.warfare.darkannihilation.systemd.service.Service;

class AudioExoPlayer {
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

    static void update(@NonNull SimpleExoPlayer exoPlayer, float vol) {
        exoPlayer.seekTo(0);
        exoPlayer.setVolume(vol * volume);
        exoPlayer.pause();
    }

    static void update(@NonNull SimpleExoPlayer exoPlayer) {
        exoPlayer.seekTo(0);
        exoPlayer.setVolume(volume);
        exoPlayer.pause();
    }

    static void release(SimpleExoPlayer simpleExoPlayer) {
        if (simpleExoPlayer != null) {
            simpleExoPlayer.release();
        }
    }

    @NonNull
    static MediaItem getItem(int id) {
        return MediaItem.fromUri(Service.path + id);
    }
}
