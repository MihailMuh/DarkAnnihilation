package ru.warfare.darkannihilation.systemd;

import static ru.warfare.darkannihilation.systemd.Game.string_choose_lang;
import static ru.warfare.darkannihilation.systemd.Game.string_disable;
import static ru.warfare.darkannihilation.systemd.Game.string_enable;
import static ru.warfare.darkannihilation.systemd.Game.string_loud_effects;
import static ru.warfare.darkannihilation.systemd.Game.string_loud_music;
import static ru.warfare.darkannihilation.systemd.Game.string_vibration;
import static ru.warfare.darkannihilation.systemd.Game.string_volume;
import static ru.warfare.darkannihilation.systemd.service.Service.activity;
import static ru.warfare.darkannihilation.systemd.service.Service.runOnUiThread;

import android.widget.TextView;

import com.skydoves.powerspinner.IconSpinnerAdapter;
import com.skydoves.powerspinner.IconSpinnerItem;
import com.skydoves.powerspinner.PowerSpinnerView;
import com.triggertrap.seekarc.SeekArc;

import java.util.ArrayList;

import io.ghyeok.stickyswitch.widget.StickySwitch;
import ru.warfare.darkannihilation.R;
import ru.warfare.darkannihilation.arts.ImageHub;
import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.interfaces.SeekArcListener;
import ru.warfare.darkannihilation.interfaces.SpinnerListener;
import ru.warfare.darkannihilation.math.Math;
import ru.warfare.darkannihilation.systemd.service.Clerk;
import ru.warfare.darkannihilation.systemd.service.Time;
import ru.warfare.darkannihilation.systemd.service.Vibrator;
import ru.warfare.darkannihilation.thread.HardThread;

public class Settings {
    private final Game game;

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

    public Settings(Game game) {
        this.game = game;

        parseSettings();

        angleEffects = activity.findViewById(R.id.angleEffects);
        angleEffects.setVisibility(TextView.VISIBLE);
        textViewEffects = activity.findViewById(R.id.textViewEffects);
        textViewEffects.setVisibility(TextView.VISIBLE);
        seekArcEffects = activity.findViewById(R.id.seekArcEffects);
        seekArcEffects.setProgress((int) (finalVolumeEffects * 100));
        seekArcEffects.setVisibility(SeekArc.VISIBLE);
        seekArcEffects.setOnSeekArcChangeListener((SeekArcListener) newVolume -> {
            angleEffects.setText((string_volume + " " + newVolume));
            finalVolumeEffects = newVolume / 100f;
            AudioHub.soundOfClick(finalVolumeEffects);
        });

        angleMusic = activity.findViewById(R.id.angleMusic);
        angleMusic.setVisibility(TextView.VISIBLE);
        textViewMusic = activity.findViewById(R.id.textViewMusic);
        textViewMusic.setVisibility(TextView.VISIBLE);
        seekArcMusic = activity.findViewById(R.id.seekArcMusic);
        seekArcMusic.setProgress((int) (finalVolumeMusic * 100));
        seekArcMusic.setVisibility(SeekArc.VISIBLE);
        seekArcMusic.setOnSeekArcChangeListener((SeekArcListener) newVolume -> {
            angleMusic.setText((string_volume + " " + newVolume));
            finalVolumeMusic = newVolume / 100f;
            AudioHub.menuMusic.setVolume(finalVolumeMusic);
        });

        textViewVibration = activity.findViewById(R.id.textVibration);
        textViewVibration.setVisibility(TextView.VISIBLE);
        stickySwitch = activity.findViewById(R.id.stickySwitch);
        stickySwitch.setVisibility(TextView.VISIBLE);
        stickySwitch.setRightIcon(ImageHub.onImg);
        stickySwitch.setLeftIcon(ImageHub.offImg);
        stickySwitch.setRightIcon(ImageHub.onImg);
        stickySwitch.setLeftIcon(ImageHub.offImg);
        if (Game.vibrate) {
            stickySwitch.setDirection(StickySwitch.Direction.RIGHT);
        } else {
            stickySwitch.setDirection(StickySwitch.Direction.LEFT);
        }
        stickySwitch.setOnSelectedChangeListener((direction, text) -> {
            if (text.equals(string_enable)) {
                HardThread.doInBackGround(() -> {
                    Time.sleep(350);
                    Game.vibrate = true;
                    Vibrator.vibrate(60);
                });
            } else {
                Game.vibrate = false;
            }
        });

        ArrayList<IconSpinnerItem> iconSpinnerItems = new ArrayList<>(0);
        iconSpinnerItems.add(new IconSpinnerItem("English", ImageHub.enImg));
        iconSpinnerItems.add(new IconSpinnerItem("Русский", ImageHub.ruImg));
        iconSpinnerItems.add(new IconSpinnerItem("Français", ImageHub.frImg));
        iconSpinnerItems.add(new IconSpinnerItem("Español", ImageHub.spImg));
        iconSpinnerItems.add(new IconSpinnerItem("Deutsch", ImageHub.geImg));

        spinner = activity.findViewById(R.id.spinner);
        spinner.setVisibility(TextView.VISIBLE);
        spinner.setSpinnerAdapter(new IconSpinnerAdapter(spinner));
        spinner.setItems(iconSpinnerItems);
        spinner.setOnSpinnerItemSelectedListener((SpinnerListener) id -> HardThread.doInBackGround(() -> {
            switch (id) {
                case 0:
                    game.language = "en";
                    break;
                case 1:
                    game.language = "ru";
                    break;
                case 2:
                    game.language = "fr";
                    break;
                case 3:
                    game.language = "sp";
                    break;
                case 4:
                    game.language = "ge";
                    break;
            }
            game.confirmLanguage(true);

            runOnUiThread(this::makeLanguage);
        }));
        spinner.setLifecycleOwner(activity);
        textSpinner = activity.findViewById(R.id.textSpinner);
        textSpinner.setVisibility(TextView.VISIBLE);

        switch (game.language) {
            case "en":
                spinner.selectItemByIndex(0);
                break;
            case "ru":
                spinner.selectItemByIndex(1);
                break;
            case "fr":
                spinner.selectItemByIndex(2);
                break;
            case "sp":
                spinner.selectItemByIndex(3);
                break;
            case "ge":
                spinner.selectItemByIndex(4);
                break;
        }
    }

    private void makeLanguage() {
        angleEffects.setText((string_volume + " " + (int) (finalVolumeEffects * 100)));
        textViewEffects.setText(string_loud_effects);

        angleMusic.setText((string_volume + " " + (int) (finalVolumeMusic * 100)));
        textViewMusic.setText(string_loud_music);

        textViewVibration.setText(string_vibration);
        stickySwitch.setRightText(string_enable);
        stickySwitch.setLeftText(string_disable);

        textSpinner.setText(string_choose_lang);
    }

    public void confirmSettings() {
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

        saveSettings();

        AudioHub.newVolumeForBackground(finalVolumeMusic);
        AudioHub.newVolumeForEffects(finalVolumeEffects);
    }

    private void parseSettings() {
        String[] settings = Clerk.getSettings().split(" ");
        finalVolumeMusic = Float.parseFloat(settings[0]);
        finalVolumeEffects = Float.parseFloat(settings[1]);
    }

    public void saveSettings() {
        Clerk.saveSettings(finalVolumeMusic + " " + finalVolumeEffects + " " +
                Math.boolToInt(Game.vibrate) + " " + game.language);
    }
}
