package ru.warfare.darkannihilation.systemd;

import android.view.ViewGroup;
import android.widget.TextView;

import com.animsh.animatedcheckbox.AnimatedCheckBox;
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
import ru.warfare.darkannihilation.R;
import ru.warfare.darkannihilation.SeekArcListener;
import ru.warfare.darkannihilation.audio.AudioExoPlayer;
import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.math.Math;

import static ru.warfare.darkannihilation.systemd.Game.string_choose_lang;
import static ru.warfare.darkannihilation.systemd.Game.string_disable;
import static ru.warfare.darkannihilation.systemd.Game.string_enable;
import static ru.warfare.darkannihilation.systemd.Game.string_loud_effects;
import static ru.warfare.darkannihilation.systemd.Game.string_loud_music;
import static ru.warfare.darkannihilation.systemd.Game.string_smooth;
import static ru.warfare.darkannihilation.systemd.Game.string_vibration;
import static ru.warfare.darkannihilation.systemd.Game.string_volume;

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

    private final AnimatedCheckBox animatedCheckBox;
    private final TextView textAntiAlias;

    private float finalVolumeEffects;
    private float finalVolumeMusic;

    public Settings() {
        this.mainActivity = Service.getContext();
        parseSettings();
        float density = mainActivity.getResources().getDisplayMetrics().density;
        int angle = (int) (finalVolumeEffects * 100);

        angleEffects = mainActivity.findViewById(R.id.angleEffects);
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
                angleEffects.setText((string_volume + " " + newVolume));
                finalVolumeEffects = (float) newVolume / 100f;
                AudioHub.soundOfClick(finalVolumeEffects);
            }
        });

        angleMusic = mainActivity.findViewById(R.id.angleMusic);
        angle = (int) (finalVolumeMusic * 100);
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
                angleMusic.setText((string_volume + " " + newVolume));
                finalVolumeMusic = (float) newVolume / 100f;
                AudioHub.menuMusic.setVolume(finalVolumeMusic);
            }
        });

        textViewVibration = mainActivity.findViewById(R.id.textVibration);
        textViewVibration.setVisibility(TextView.GONE);
        stickySwitch = mainActivity.findViewById(R.id.stickySwitch);
        layoutParams = stickySwitch.getLayoutParams();
        layoutParams.width = (int) (140 * Service.getResizeCoefficientForLayout() * density + 0.5f);
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
            if (text.equals(string_enable)) {
                HardThread.newJob(() -> {
                    Service.sleep(300);
                    Game.vibrate = true;
                    Service.vibrateNOW(60);
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
        spinner.setVisibility(TextView.GONE);
        layoutParams = spinner.getLayoutParams();
        layoutParams.width = (int) (180 * Service.getResizeCoefficientForLayout() * density + 0.5f);
        layoutParams.height = (int) (50 * Service.getResizeCoefficientForLayout() * density + 0.5f);
        spinner.setLayoutParams(layoutParams);
        IconSpinnerAdapter iconSpinnerAdapter = new IconSpinnerAdapter(spinner);
        spinner.setSpinnerAdapter(iconSpinnerAdapter);
        spinner.setItems(iconSpinnerItems);
        spinner.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(int i, @Nullable Object o, int i1, Object t1) {
                switch (i1) {
                    case 0:
                        Game.language = "en";
                        ImageHub.buttonImagePressed = ImageHub.resizeImage(ImageHub.buttonImagePressed, ImageHub.eX300, ImageHub.eX70);
                        ImageHub.buttonImageNotPressed = ImageHub.resizeImage(ImageHub.buttonImageNotPressed, ImageHub.eX300, ImageHub.eX70);
                        break;
                    case 1:
                        Game.language = "ru";
                        break;
                    case 2:
                        Game.language = "fr";
                        break;
                    case 3:
                        Game.language = "sp";
                        break;
                    case 4:
                        Game.language = "ge";
                        break;
                }
                mainActivity.game.makeLanguage(true);
                makeLanguage();
            }
        });
        spinner.setLifecycleOwner(mainActivity);
        textSpinner = mainActivity.findViewById(R.id.textSpinner);
        textSpinner.setVisibility(TextView.GONE);

        animatedCheckBox = mainActivity.findViewById(R.id.animatedCheckBox);
        animatedCheckBox.setVisibility(TextView.GONE);
        layoutParams = animatedCheckBox.getLayoutParams();
        layoutParams.width = (int) (40 * Service.getResizeCoefficientForLayout() * density + 0.5f);
        layoutParams.height = (int) (40 * Service.getResizeCoefficientForLayout() * density + 0.5f);
        animatedCheckBox.setLayoutParams(layoutParams);
        animatedCheckBox.setChecked(Game.scorePaint.isAntiAlias(), false);
        animatedCheckBox.setOnCheckedChangeListener((checkBox, isChecked) ->
                mainActivity.game.setAntiAlias(isChecked));
        textAntiAlias = mainActivity.findViewById(R.id.textAntiAlias);
        textAntiAlias.setVisibility(TextView.GONE);

        makeLanguage();
    }

    public void showSettings() {
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
        switch (Game.language) {
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
        textSpinner.setVisibility(TextView.VISIBLE);

        animatedCheckBox.setVisibility(TextView.VISIBLE);
        animatedCheckBox.setChecked(Game.scorePaint.isAntiAlias(), false);
        textAntiAlias.setVisibility(TextView.VISIBLE);

        Game.gameStatus = 10;
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

        textAntiAlias.setText(string_smooth);
    }

    public void confirmSettings() {
        Service.runOnUiThread(() -> {
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

            animatedCheckBox.setVisibility(TextView.GONE);
            textAntiAlias.setVisibility(TextView.GONE);
            AudioExoPlayer.newVolumeForBackground(finalVolumeMusic);
            AudioHub.newVolumeForEffects(finalVolumeEffects);
            saveSettings();
        });
    }

    public void parseSettings() {
        String[] settings = Clerk.getSettings().split(" ");
        finalVolumeMusic = Float.parseFloat(settings[0]);
        finalVolumeEffects = Float.parseFloat(settings[1]);
        Game.vibrate = Integer.parseInt(settings[2]) == 1;
        Game.language = settings[3];
        mainActivity.game.makeLanguage(false);
        mainActivity.game.setAntiAlias(Integer.parseInt(settings[4]) == 1);
        AudioExoPlayer.newVolumeForBackground(finalVolumeMusic);
        AudioHub.newVolumeForEffects(finalVolumeEffects);
    }

    public void saveSettings() {
        Clerk.saveSettings(finalVolumeMusic + " " + finalVolumeEffects + " " +
                Math.boolToInt(Game.vibrate) + " " + Game.language + " " +
                Math.boolToInt(Game.scorePaint.isAntiAlias()));
    }
}
