package com.warfare.darkannihilation.hub;

import static com.warfare.darkannihilation.constants.Assets.LOCALES;

import com.badlogic.gdx.assets.loaders.I18NBundleLoader.I18NBundleParameter;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;

public class LocaleHub extends BaseHub {
    public String quit, start, topScore, settings;
    public String tapScreenWith2Fingers, youDead;
    public String shoot;
    public String currentScore;
    public String unexpectedError, pleaseReEnter;

    public LocaleHub(AssetManagerSuper assetManager) {
        super(assetManager);

        loadNewLocale("ru");
    }

    @Override
    public void boot() {
        getLocale();
    }

    public void loadNewLocale(String language) {
        assetManager.load(LOCALES, I18NBundle.class, new I18NBundleParameter(new Locale(language), "UTF-8"));
    }

    public void getLocale() {
        final I18NBundle locale = assetManager.get(LOCALES, I18NBundle.class);

        quit = locale.get("quit");
        start = locale.get("start");
        topScore = locale.get("topScore");
        settings = locale.get("settings");
        tapScreenWith2Fingers = locale.get("tapScreenWith2Fingers");
        youDead = locale.get("youDead");
        shoot = locale.get("shoot");
        currentScore = locale.get("currentScore");
        unexpectedError = locale.get("unexpectedError");
        pleaseReEnter = locale.get("pleaseReEnter");

        assetManager.unload(LOCALES);
    }
}
