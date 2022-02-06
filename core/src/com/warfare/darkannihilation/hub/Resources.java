package com.warfare.darkannihilation.hub;

public class Resources {
    private static ImageHub imageHub;
    private static SoundHub soundHub;
    private static FontHub fontHub;

    public static void setProviders(ImageHub image, SoundHub sound, FontHub font) {
        imageHub = image;
        soundHub = sound;
        fontHub = font;
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
}
