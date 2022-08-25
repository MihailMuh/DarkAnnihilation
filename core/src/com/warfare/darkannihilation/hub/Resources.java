package com.warfare.darkannihilation.hub;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.warfare.darkannihilation.player.Player;

public class Resources {
    private static final PoolHub poolHub = new PoolHub();

    private static ImageHub imageHub;
    private static SoundHub soundHub;
    private static FontHub fontHub;
    private static LocaleHub localeHub;
    private static Player player;
    private static AssetManagerSuper assetManager;
    private static SpriteBatch batch;
    private static Viewport viewport;

    public static void setProviders(ImageHub image, SoundHub sound, FontHub font, LocaleHub locales, AssetManagerSuper assetManagerSuper) {
        imageHub = image;
        soundHub = sound;
        fontHub = font;
        localeHub = locales;
        assetManager = assetManagerSuper;
    }

    public static void setPlayer(Player player) {
        Resources.player = player;
    }

    public static void setBatch(SpriteBatch batch) {
        Resources.batch = batch;
    }

    public static ImageHub getImages() {
        return imageHub;
    }

    public static SoundHub getSounds() {
        return soundHub;
    }

    public static FontHub getFonts() {
        return fontHub;
    }

    public static PoolHub getPools() {
        return poolHub;
    }

    public static LocaleHub getLocales() {
        return localeHub;
    }

    public static Player getPlayer() {
        return player;
    }

    public static AssetManagerSuper getAssetManager() {
        return assetManager;
    }

    public static SpriteBatch getBatch() {
        return batch;
    }

    public static void setViewport(Viewport viewport) {
        Resources.viewport = viewport;
    }

    public static Viewport getViewport() {
        return viewport;
    }
}
