package ru.warfare.darkannihilation;

import android.view.ViewGroup;
import android.widget.TextView;

import com.skydoves.powerspinner.IconSpinnerAdapter;
import com.skydoves.powerspinner.IconSpinnerItem;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;
import com.skydoves.powerspinner.PowerSpinnerView;
import com.triggertrap.seekarc.SeekArc;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

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

    private final PowerSpinnerView spinner;
    private final TextView textSpinner;

    private float finalVolumeEffects;
    private float finalVolumeMusic;

    public Settings(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        parseSettings();
        float density = mainActivity.getResources().getDisplayMetrics().density;
        int angle = (int) (finalVolumeEffects * 100);

        angleEffects = mainActivity.findViewById(R.id.angleEffects);
        angleEffects.setText(("Volume: " + angle));
        angleEffects.setVisibility(TextView.GONE);
        textViewEffects = mainActivity.findViewById(R.id.textViewEffects);
        textViewEffects.setVisibility(TextView.GONE);
        seekArcEffects = mainActivity.findViewById(R.id.seekArcEffects);
        ViewGroup.LayoutParams layoutParams = seekArcEffects.getLayoutParams();
        layoutParams.width = (int) (200 * Service.getResizeCoefficientForLayout() * density + 0.5f);
        layoutParams.height = (int) (200 * Service.getResizeCoefficientForLayout() * density + 0.5f);
        seekArcEffects.setLayoutParams(layoutParams);
        seekArcEffects.setProgress(angle);
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
        angle = (int) (finalVolumeMusic * 100);
        angleMusic.setText(("Volume: " + angle));
        angleMusic.setVisibility(TextView.GONE);
        textViewMusic = mainActivity.findViewById(R.id.textViewMusic);
        textViewMusic.setVisibility(TextView.GONE);
        seekArcMusic = mainActivity.findViewById(R.id.seekArcMusic);
        layoutParams = seekArcMusic.getLayoutParams();
        layoutParams.width = (int) (200 * Service.getResizeCoefficientForLayout() * density + 0.5f);
        layoutParams.height = (int) (200 * Service.getResizeCoefficientForLayout() * density + 0.5f);
        seekArcMusic.setLayoutParams(layoutParams);
        seekArcMusic.setProgress(angle);
        seekArcMusic.setVisibility(SeekArc.GONE);
        seekArcMusic.setOnSeekArcChangeListener(new SeekArcListener() {
            @Override
            public void onProgressChanged(SeekArc seekArc, int newVolume, boolean b) {
                angleMusic.setText(("Volume: " + newVolume));
                finalVolumeMusic = (float) newVolume / 100f;
                AudioHub.menuMusic.setVolume(finalVolumeMusic, finalVolumeMusic);
            }
        });

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
        if (Game.vibrate) {
            stickySwitch.setDirection(StickySwitch.Direction.RIGHT);
        } else {
            stickySwitch.setDirection(StickySwitch.Direction.LEFT);
        }
        stickySwitch.setOnSelectedChangeListener((direction, text) -> {
            if (text.equals("Enable")) {
                new Thread(() -> {
                    try {
                        Thread.sleep(300);
                        Game.vibrate = true;
                        Service.vibrate(50);
                    } catch (Exception e) {
                        Service.print(e.toString());
                    }
                }).start();
            } else {
                Game.vibrate = false;
            }
        });

        textSpinner = mainActivity.findViewById(R.id.textSpinner);
        textSpinner.setVisibility(TextView.GONE);
        ArrayList<IconSpinnerItem> iconSpinnerItems = new ArrayList<>();
        iconSpinnerItems.add(new IconSpinnerItem("English", ImageHub.enImg));
        iconSpinnerItems.add(new IconSpinnerItem("Russian", ImageHub.ruImg));
        spinner = mainActivity.findViewById(R.id.spinner);
        spinner.setVisibility(TextView.GONE);
        IconSpinnerAdapter iconSpinnerAdapter = new IconSpinnerAdapter(spinner);
        spinner.setSpinnerAdapter(iconSpinnerAdapter);
        spinner.setItems(iconSpinnerItems);
        spinner.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(int i, @Nullable Object o, int i1, Object t1) {
                switch (i1)
                {
                    case 0:
                        Game.language = "en";
                        break;
                    case 1:
                        Game.language = "ru";
                        break;
                }
                mainActivity.game.makeLanguage();
            }
        });
        spinner.setLifecycleOwner(mainActivity);
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

            spinner.setVisibility(TextView.VISIBLE);
            switch (Game.language)
            {
                case "en":
                    spinner.selectItemByIndex(0);
                    break;
                case "ru":
                    spinner.selectItemByIndex(1);
                    break;
            }
            textSpinner.setVisibility(TextView.VISIBLE);

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

            spinner.setVisibility(TextView.GONE);
            spinner.dismiss();
            textSpinner.setVisibility(TextView.GONE);
        }));
        AudioHub.changeVolumeForAllPlayers(finalVolumeMusic);
        AudioPool.newVolumeForPool(finalVolumeEffects);
        saveSettings();
    }

    public void parseSettings() {
        String[] settings = Clerk.getSettings().split(" ");
        finalVolumeMusic = Float.parseFloat(settings[0]);
        finalVolumeEffects = Float.parseFloat(settings[1]);
        Game.vibrate = Integer.parseInt(settings[2]) == 1;
        Game.language = settings[3];
        mainActivity.game.makeLanguage();
        AudioHub.changeVolumeForAllPlayers(finalVolumeMusic);
        AudioPool.newVolumeForPool(finalVolumeEffects);
    }

    public void saveSettings() {
        if (Game.vibrate) {
            Clerk.saveSettings(finalVolumeMusic + " " + finalVolumeEffects + " 1 " + Game.language);
        } else {
            Clerk.saveSettings(finalVolumeMusic + " " + finalVolumeEffects + " 0 " + Game.language);
        }
    }
}
