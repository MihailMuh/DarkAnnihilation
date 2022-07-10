package com.warfare.darkannihilation.hub;

public class Resources {
    private static final PoolHub poolHub = new PoolHub();

    private static ImageHub imageHub;
    private static SoundHub soundHub;
    private static FontHub fontHub;
    private static LocaleHub localeHub;

    public static void setProviders(ImageHub image, SoundHub sound, FontHub font, LocaleHub locales) {
        imageHub = image;
        soundHub = sound;
        fontHub = font;
        localeHub = locales;
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
}
