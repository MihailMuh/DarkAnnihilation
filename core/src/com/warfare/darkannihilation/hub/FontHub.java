package com.warfare.darkannihilation.hub;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;
import com.warfare.darkannihilation.abstraction.BaseHub;

public class FontHub extends BaseHub {
    public BitmapFont canisMinor, canisMinorHuge;

    public FontHub(ResourcesManager resourcesManager) {
        super(resourcesManager);

        resourcesManager.setLoader(FreeTypeFontGenerator.class,
                new FreeTypeFontGeneratorLoader(resourcesManager.resolver));
        resourcesManager.setLoader(BitmapFont.class,
                new FreetypeFontLoader(resourcesManager.resolver));

        FreeTypeFontLoaderParameter params = new FreeTypeFontLoaderParameter();
        params.fontFileName = "fonts/canis_minor.ttf";
        params.fontParameters.size = 80;
        params.fontParameters.minFilter = Texture.TextureFilter.Linear;
        params.fontParameters.magFilter = Texture.TextureFilter.Linear;
        params.fontParameters.borderColor = Color.WHITE;
        params.fontParameters.shadowOffsetY = 10;
        params.fontParameters.shadowOffsetX = 10;
        params.fontParameters.borderWidth = 2.5f;

        resourcesManager.load("canis_minor.ttf", BitmapFont.class, params);

        params = new FreeTypeFontLoaderParameter();
        params.fontFileName = "fonts/canis_minor.ttf";
        params.fontParameters.size = 120;
        params.fontParameters.borderColor = Color.WHITE;
        params.fontParameters.hinting = FreeTypeFontGenerator.Hinting.Full;
        params.fontParameters.minFilter = Texture.TextureFilter.Linear;
        params.fontParameters.magFilter = Texture.TextureFilter.Linear;
        params.fontParameters.gamma = 1f;
        params.fontParameters.shadowOffsetY = 5;
        params.fontParameters.shadowOffsetX = 5;
        params.fontParameters.borderWidth = 2.5f;
        resourcesManager.load("canis_minor_huge.ttf", BitmapFont.class, params);
    }

    @Override
    public void boot() {
        canisMinor = resourcesManager.get("canis_minor.ttf", BitmapFont.class);
        canisMinorHuge = resourcesManager.get("canis_minor_huge.ttf", BitmapFont.class);
    }

    public static float resizeFont(BitmapFont font, float maxWidth, String... texts) {
        GlyphLayout glyph = new GlyphLayout();

        if (texts.length != 1) {
            float maxTextWidth = -45366;
            String maxStr = "";

            for (String text : texts) {
                glyph.setText(font, text);
                if (glyph.width > maxTextWidth) {
                    maxTextWidth = glyph.width;
                    maxStr = text;
                }
            }

            glyph.setText(font, maxStr);
        } else {
            glyph.setText(font, texts[0]);
        }

        return maxWidth / glyph.width;
    }
}
