package ru.warfare.darkannihilation;

import android.view.ViewGroup;
import android.widget.TextView;

import com.triggertrap.seekarc.SeekArc;

import io.ghyeok.stickyswitch.widget.StickySwitch;

public class Settings {
    private final MainActivity mainActivity;

    private final TextView angleEffects;
    private final TextView textViewEffects;
    private final SeekArc seekArcEffects;

    private final TextView angleMusic;
    private final TextView textViewMusic;
    private final SeekArc seekArcMusic;

    private final TextView textViewVibration;
    private final StickySwitch stickySwitch;

    private float finalVolumeEffects = 1;
    private float finalVolumeMusic = 1;

    public Settings(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        float density = mainActivity.getResources().getDisplayMetrics().density;

        seekArcEffects = mainActivity.findViewById(R.id.seekArcEffects);
        ViewGroup.LayoutParams layoutParams = seekArcEffects.getLayoutParams();
        layoutParams.width = (int) (200 * Service.getResizeCoefficientForLayout() * density + 0.5f);
        layoutParams.height = (int) (200 * Service.getResizeCoefficientForLayout() * density + 0.5f);
        seekArcEffects.setLayoutParams(layoutParams);
        seekArcEffects.setProgress(100);
        seekArcEffects.setVisibility(SeekArc.GONE);
        seekArcEffects.setOnSeekArcChangeListener(new SeekArcListener() {
            @Override
            public void onProgressChanged(SeekArc seekArc, int newVolume, boolean b) {
                angleEffects.setText(("Volume: " + newVolume));
                finalVolumeEffects = (float) newVolume / 100f;
                AudioPool.newVolumeForSnd(7, finalVolumeEffects);
            }
        });

        seekArcMusic = mainActivity.findViewById(R.id.seekArcMusic);
        layoutParams = seekArcMusic.getLayoutParams();
        layoutParams.width = (int) (200 * Service.getResizeCoefficientForLayout() * density + 0.5f);
        layoutParams.height = (int) (200 * Service.getResizeCoefficientForLayout() * density + 0.5f);
        seekArcMusic.setLayoutParams(layoutParams);
        seekArcMusic.setProgress(100);
        seekArcMusic.setVisibility(SeekArc.GONE);
        seekArcMusic.setOnSeekArcChangeListener(new SeekArcListener() {
            @Override
            public void onProgressChanged(SeekArc seekArc, int newVolume, boolean b) {
                angleMusic.setText(("Volume: " + newVolume));
                finalVolumeMusic = (float) newVolume / 100f;
                AudioHub.menuMusic.setVolume(finalVolumeMusic, finalVolumeMusic);
            }
        });

        angleEffects = mainActivity.findViewById(R.id.angleEffects);
        angleEffects.setText(("Volume: " + 100));
        angleEffects.setVisibility(TextView.GONE);
        textViewEffects = mainActivity.findViewById(R.id.textViewEffects);
        textViewEffects.setVisibility(TextView.GONE);

        angleMusic = mainActivity.findViewById(R.id.angleMusic);
        angleMusic.setText(("Volume: " + 100));
        angleMusic.setVisibility(TextView.GONE);
        textViewMusic = mainActivity.findViewById(R.id.textViewMusic);
        textViewMusic.setVisibility(TextView.GONE);

        textViewVibration = mainActivity.findViewById(R.id.textVibration);
        textViewVibration.setVisibility(TextView.GONE);
        stickySwitch = mainActivity.findViewById(R.id.stickySwitch);
        layoutParams = stickySwitch.getLayoutParams();
        layoutParams.width = (int) (150 * Service.getResizeCoefficientForLayout() * density + 0.5f);
        layoutParams.height = (int) (90 * Service.getResizeCoefficientForLayout() * density + 0.5f);
        stickySwitch.setLayoutParams(layoutParams);
        stickySwitch.setVisibility(TextView.GONE);
        stickySwitch.setRightIcon(ImageHub.onImg);
        stickySwitch.setLeftIcon(ImageHub.offImg);
        stickySwitch.setDirection(StickySwitch.Direction.RIGHT);
        stickySwitch.setOnSelectedChangeListener((direction, text) -> {
            if (text.equals("Enable")) {
                Game.vibrate = true;
                Service.vibrate(50);
            } else {
                Game.vibrate = false;
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

            textViewVibration.setVisibility(TextView.VISIBLE);
            stickySwitch.setVisibility(SeekArc.VISIBLE);
            stickySwitch.setRightIcon(ImageHub.onImg);
            stickySwitch.setLeftIcon(ImageHub.offImg);

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

            textViewVibration.setVisibility(TextView.GONE);
            stickySwitch.setVisibility(SeekArc.GONE);
        }));
        AudioHub.changeVolumeForAllPlayers(finalVolumeMusic);
        AudioPool.newVolumeForPool(finalVolumeEffects);
    }
}
