package ru.warfare.darkannihilation;

import android.widget.TextView;

import com.triggertrap.seekarc.SeekArc;

public class Settings {
    private final MainActivity mainActivity;

    private final TextView angleEffects;
    private final TextView textViewEffects;
    private final SeekArc seekArcEffects;

    private final TextView angleMusic;
    private final TextView textViewMusic;
    private final SeekArc seekArcMusic;

    private float finalVolumeEffects = 1;
    private float finalVolumeMusic = 1;

    public Settings(MainActivity mainActivity) {
        this.mainActivity = mainActivity;

        angleEffects = mainActivity.findViewById(R.id.angleEffects);
        angleEffects.setText(("Volume: " + 50));
        angleEffects.setVisibility(TextView.GONE);

        textViewEffects = mainActivity.findViewById(R.id.textViewEffects);
        textViewEffects.setVisibility(TextView.GONE);

        seekArcEffects = mainActivity.findViewById(R.id.seekArcEffects);
        seekArcEffects.setProgress(50);
        seekArcEffects.setVisibility(SeekArc.GONE);
        seekArcEffects.setOnSeekArcChangeListener(new SeekArcListener() {
            @Override
            public void onProgressChanged(SeekArc seekArc, int newVolume, boolean b) {
                angleEffects.setText(("Volume: " + newVolume));
                finalVolumeEffects = (float) newVolume / 100f;
                AudioPool.newVolumeForSnd(7, finalVolumeEffects);
            }
        });

        angleMusic = mainActivity.findViewById(R.id.angleMusic);
        angleMusic.setText(("Volume: " + 50));
        angleMusic.setVisibility(TextView.GONE);

        textViewMusic = mainActivity.findViewById(R.id.textViewMusic);
        textViewMusic.setVisibility(TextView.GONE);

        seekArcMusic = mainActivity.findViewById(R.id.seekArcMusic);
        seekArcMusic.setProgress(50);
        seekArcMusic.setVisibility(SeekArc.GONE);
        seekArcMusic.setOnSeekArcChangeListener(new SeekArcListener() {
            @Override
            public void onProgressChanged(SeekArc seekArc, int newVolume, boolean b) {
                angleMusic.setText(("Volume: " + newVolume));
                finalVolumeMusic = (float) newVolume / 100f;
                AudioHub.menuMusic.setVolume(finalVolumeMusic, finalVolumeMusic);
            }
        });
    }

    public void showSettings() {
        mainActivity.runOnUiThread(new Thread(() -> {
            angleEffects.setVisibility(TextView.VISIBLE);
            textViewEffects.setVisibility(TextView.VISIBLE);
            seekArcEffects.setVisibility(SeekArc.VISIBLE);

            angleMusic.setVisibility(TextView.VISIBLE);
            textViewMusic.setVisibility(TextView.VISIBLE);
            seekArcMusic.setVisibility(SeekArc.VISIBLE);
            Game.gameStatus = 10;
        }));
    }

    public void confirmSettings() {
        mainActivity.runOnUiThread(new Thread(() -> {
            angleEffects.setVisibility(TextView.GONE);
            textViewEffects.setVisibility(TextView.GONE);
            seekArcEffects.setVisibility(SeekArc.GONE);

            angleMusic.setVisibility(TextView.GONE);
            textViewMusic.setVisibility(TextView.GONE);
            seekArcMusic.setVisibility(SeekArc.GONE);
        }));
        AudioHub.changeVolumeForAllPlayers(finalVolumeMusic);
        AudioPool.newVolumeForPool(finalVolumeEffects);
    }
}
