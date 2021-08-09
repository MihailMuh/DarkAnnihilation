package ru.warfare.darkannihilation.systemd;

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
import ru.warfare.darkannihilation.Clerk;
import ru.warfare.darkannihilation.HardThread;
import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.R;
import ru.warfare.darkannihilation.SeekArcListener;
import ru.warfare.darkannihilation.Time;
import ru.warfare.darkannihilation.Vibrator;
import ru.warfare.darkannihilation.Windows;
import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.math.Math;

import static ru.warfare.darkannihilation.systemd.Game.string_choose_lang;
import static ru.warfare.darkannihilation.systemd.Game.string_disable;
import static ru.warfare.darkannihilation.systemd.Game.string_enable;
import static ru.warfare.darkannihilation.systemd.Game.string_loud_effects;
import static ru.warfare.darkannihilation.systemd.Game.string_loud_music;
import static ru.warfare.darkannihilation.systemd.Game.string_vibration;
import static ru.warfare.darkannihilation.systemd.Game.string_volume;

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
        MainActivity mainActivity = game.mainActivity;

        parseSettings();

        float density = Windows.resizeLayout();
        int angle = (int) (finalVolumeEffects * 100);
        int _200 = (int) (200 * density + 0.5f);
        ViewGroup.LayoutParams layoutParams;

        angleEffects = mainActivity.findViewById(R.id.angleEffects);
        angleEffects.setVisibility(TextView.VISIBLE);
        textViewEffects = mainActivity.findViewById(R.id.textViewEffects);
        textViewEffects.setVisibility(TextView.VISIBLE);
        seekArcEffects = mainActivity.findViewById(R.id.seekArcEffects);
        layoutParams = seekArcEffects.getLayoutParams();
        layoutParams.width = _200;
        layoutParams.height = _200;
        seekArcEffects.setLayoutParams(layoutParams);
        seekArcEffects.setProgress(angle);
        seekArcEffects.setVisibility(SeekArc.VISIBLE);
        seekArcEffects.setOnSeekArcChangeListener(new SeekArcListener() {
            @Override
            public void onProgressChanged(SeekArc seekArc, int newVolume, boolean b) {
                angleEffects.setText((string_volume + " " + newVolume));
                finalVolumeEffects = newVolume / 100f;
                AudioHub.soundOfClick(finalVolumeEffects);
            }
        });

        angleMusic = mainActivity.findViewById(R.id.angleMusic);
        angle = (int) (finalVolumeMusic * 100);
        angleMusic.setVisibility(TextView.VISIBLE);
        textViewMusic = mainActivity.findViewById(R.id.textViewMusic);
        textViewMusic.setVisibility(TextView.VISIBLE);
        seekArcMusic = mainActivity.findViewById(R.id.seekArcMusic);
        layoutParams = seekArcMusic.getLayoutParams();
        layoutParams.width = _200;
        layoutParams.height = _200;
        seekArcMusic.setLayoutParams(layoutParams);
        seekArcMusic.setProgress(angle);
        seekArcMusic.setVisibility(SeekArc.VISIBLE);
        seekArcMusic.setOnSeekArcChangeListener(new SeekArcListener() {
            @Override
            public void onProgressChanged(SeekArc seekArc, int newVolume, boolean b) {
                angleMusic.setText((string_volume + " " + newVolume));
                finalVolumeMusic = newVolume / 100f;
                AudioHub.menuMusic.setVolume(finalVolumeMusic);
            }
        });

        textViewVibration = mainActivity.findViewById(R.id.textVibration);
        textViewVibration.setVisibility(TextView.VISIBLE);
        stickySwitch = mainActivity.findViewById(R.id.stickySwitch);
        layoutParams = stickySwitch.getLayoutParams();
        layoutParams.width = (int) (140 * density + 0.5f);
        stickySwitch.setLayoutParams(layoutParams);
        stickySwitch.setVisibility(TextView.VISIBLE);
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
                    Time.sleep(300);
                    Game.vibrate = true;
                    Vibrator.vibrate(60);
                });
            } else {
                Game.vibrate = false;
            }
        });

        ArrayList<IconSpinnerItem> iconSpinnerItems = new ArrayList<>();
        iconSpinnerItems.add(new IconSpinnerItem("English", ImageHub.enImg));
        iconSpinnerItems.add(new IconSpinnerItem("Русский", ImageHub.ruImg));
        iconSpinnerItems.add(new IconSpinnerItem("Français", ImageHub.frImg));
        iconSpinnerItems.add(new IconSpinnerItem("Español", ImageHub.spImg));
        iconSpinnerItems.add(new IconSpinnerItem("Deutsch", ImageHub.geImg));

        spinner = mainActivity.findViewById(R.id.spinner);
        spinner.setVisibility(TextView.VISIBLE);
        layoutParams = spinner.getLayoutParams();
        layoutParams.width = (int) (180 * density + 0.5f);
        layoutParams.height = (int) (50 * density + 0.5f);
        spinner.setLayoutParams(layoutParams);
        IconSpinnerAdapter iconSpinnerAdapter = new IconSpinnerAdapter(spinner);
        spinner.setSpinnerAdapter(iconSpinnerAdapter);
        spinner.setItems(iconSpinnerItems);
        spinner.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(int i, @Nullable Object o, int i1, Object t1) {
                switch (i1) {
                    case 0:
                        game.language = "en";
                        ImageHub.buttonImagePressed = ImageHub.resizeBitmap(ImageHub.buttonImagePressed,
                                ImageHub._300, ImageHub._70);
                        ImageHub.buttonImageNotPressed = ImageHub.resizeBitmap(ImageHub.buttonImageNotPressed,
                                ImageHub._300, ImageHub._70);
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
                makeLanguage();
            }
        });
        spinner.setLifecycleOwner(mainActivity);
        textSpinner = mainActivity.findViewById(R.id.textSpinner);
        textSpinner.setVisibility(TextView.VISIBLE);

        stickySwitch.setRightIcon(ImageHub.onImg);
        stickySwitch.setLeftIcon(ImageHub.offImg);

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
        int angle = (int) (finalVolumeEffects * 100);
        angleEffects.setText((string_volume + " " + angle));
        textViewEffects.setText(string_loud_effects);

        angle = (int) (finalVolumeMusic * 100);
        angleMusic.setText((string_volume + " " + angle));
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
