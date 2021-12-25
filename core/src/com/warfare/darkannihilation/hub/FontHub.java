package com.warfare.darkannihilation.hub;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;

public final class FontHub {
    private static ImageHub imageHub;
    public static BitmapFont fontButtons;

    static void prepare(ImageHub imageHub) {
        FontHub.imageHub = imageHub;

        imageHub.assetManager.setLoader(FreeTypeFontGenerator.class,
                new FreeTypeFontGeneratorLoader(imageHub.assetManager.getFileHandleResolver()));
        imageHub.assetManager.setLoader(BitmapFont.class,
                new FreetypeFontLoader(imageHub.assetManager.getFileHandleResolver()));

        FreeTypeFontLoaderParameter params = new FreeTypeFontLoaderParameter();
        params.fontFileName = "fonts/canis_minor.ttf";
        params.fontParameters.size = 80;
        params.fontParameters.minFilter = Texture.TextureFilter.Linear;
        params.fontParameters.magFilter = Texture.TextureFilter.Linear;
        params.fontParameters.borderColor = Color.WHITE;
        params.fontParameters.shadowOffsetY = 10;
        params.fontParameters.shadowOffsetX = 10;
        params.fontParameters.borderWidth = 2.5f;

        imageHub.assetManager.load("canis_minor.ttf", BitmapFont.class, params);
    }

    static void finish() {
        fontButtons = imageHub.assetManager.get("canis_minor.ttf", BitmapFont.class);
    }

    public static void resizeFont(BitmapFont font, float width, String... texts) {
        final GlyphLayout glyph = new GlyphLayout();
        float maxWidth = -45366;
        String maxStr = "";

        for (String text : texts) {
            glyph.setText(font, text);
            if (glyph.width > maxWidth) {
                maxWidth = glyph.width;
                maxStr = text;
            }
        }

        glyph.setText(font, maxStr);

        float scale = 1f;
        while (glyph.width >= width) {
            font.getData().setScale(scale);
            scale -= 0.008f;
            glyph.setText(font, maxStr);
        }
    }
}
