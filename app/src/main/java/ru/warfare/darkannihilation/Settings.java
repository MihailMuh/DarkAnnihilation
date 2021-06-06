package ru.warfare.darkannihilation;

import android.content.Context;
import android.media.AudioManager;
import android.widget.TextView;

import com.triggertrap.seekarc.SeekArc;

public class Settings implements SeekArc.OnSeekArcChangeListener {
    private final MainActivity mainActivity;
    private final AudioManager audioManager;

    private final TextView angle;
    private final TextView textView;
    private final SeekArc seekArc;

    private final int maxLoud;

    public Settings(MainActivity mainActivity) {
        this.mainActivity = mainActivity;

        audioManager = (AudioManager) mainActivity.getSystemService(Context.AUDIO_SERVICE);
        maxLoud = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currLoud = (audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) * 100) / maxLoud;

        angle = mainActivity.findViewById(R.id.angle);
        angle.setText(("Volume: " + currLoud));
        angle.setVisibility(TextView.GONE);

        textView = mainActivity.findViewById(R.id.textView);
        textView.setVisibility(TextView.GONE);

        seekArc = mainActivity.findViewById(R.id.seekArc);
        seekArc.setOnSeekArcChangeListener(this);
        seekArc.setProgress(currLoud);
        seekArc.setVisibility(SeekArc.GONE);
    }

    public void showSettings() {
        mainActivity.runOnUiThread(new Thread(() -> {
            angle.setVisibility(TextView.VISIBLE);
            textView.setVisibility(TextView.VISIBLE);
            seekArc.setVisibility(SeekArc.VISIBLE);
            Game.gameStatus = 10;
        }));
    }

    public void hideSettings() {
        mainActivity.runOnUiThread(new Thread(() -> {
            angle.setVisibility(TextView.GONE);
            textView.setVisibility(TextView.GONE);
            seekArc.setVisibility(SeekArc.GONE);
        }));
    }

    @Override
    public void onProgressChanged(SeekArc seekArc, int newVolume, boolean b) {
        angle.setText(("Volume: " + newVolume));
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (int) (maxLoud * ((float) newVolume / 100f)), 0);
    }

    @Override
    public void onStartTrackingTouch(SeekArc seekArc) {
    }

    @Override
    public void onStopTrackingTouch(SeekArc seekArc) {
    }
}
