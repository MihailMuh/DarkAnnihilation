package com.warfare.darkannihilation.hub;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;

public class FontHub extends BaseHub {
    private static final String RUSSIAN = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдеёжзийклмнопрстуфхцчшщъыьэюя";

    public BitmapFont canisMinor, fiendish;

    public FontHub(AssetManagerSuper assetManager) {
        super(assetManager);
        FreeTypeFontGenerator.setMaxTextureSize(2048);

        assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(assetManager.resolver));
        assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(assetManager.resolver));

        FreeTypeFontLoaderParameter params = new FreeTypeFontLoaderParameter();
        params.fontFileName = "fonts/canis_minor.ttf";
        params.fontParameters.size = 150;
        params.fontParameters.minFilter = Texture.TextureFilter.MipMapLinearLinear;
        params.fontParameters.magFilter = Texture.TextureFilter.Linear;
        params.fontParameters.borderColor = Color.WHITE;
        params.fontParameters.hinting = FreeTypeFontGenerator.Hinting.Full;
        params.fontParameters.gamma = 0.5f;
        params.fontParameters.shadowOffsetY = 13;
        params.fontParameters.shadowOffsetX = 13;
        params.fontParameters.borderWidth = 5f;
        params.fontParameters.incremental = true;
        params.fontParameters.genMipMaps = true;
        params.fontParameters.characters += RUSSIAN;

        loadFiendish(new FreeTypeFontLoaderParameter());

        assetManager.load("canis_minor.ttf", BitmapFont.class, params);
    }

    private void loadFiendish(FreeTypeFontLoaderParameter params) {
        params.fontFileName = "fonts/fiendish.ttf";
        params.fontParameters.size = 190;
        params.fontParameters.minFilter = Texture.TextureFilter.MipMapLinearLinear;
        params.fontParameters.magFilter = Texture.TextureFilter.Linear;
        params.fontParameters.hinting = FreeTypeFontGenerator.Hinting.Full;
        params.fontParameters.color = Color.BLACK;
        params.fontParameters.spaceX = 20;
        params.fontParameters.incremental = true;
        params.fontParameters.genMipMaps = true;
        params.fontParameters.characters += RUSSIAN;

        assetManager.load("fiendish.ttf", BitmapFont.class, params);
    }

    @Override
    public void boot() {
        canisMinor = assetManager.get("canis_minor.ttf");
        fiendish = assetManager.get("fiendish.ttf");
    }

    public static float resizeFont(BitmapFont font, float maxWidth, String... texts) {
        GlyphLayout glyph = new GlyphLayout();

        if (texts.length > 1) {
            float maxTextWidth = -1;
            int indexOfWordWithMaxLen = 0;

            for (int i = 0; i < texts.length; i++) {
                String word = texts[i];

                glyph.setText(font, word);
                if (glyph.width > maxTextWidth) {
                    maxTextWidth = glyph.width;
                    indexOfWordWithMaxLen = i;
                }
            }

            glyph.setText(font, texts[indexOfWordWithMaxLen]);
        } else {
            glyph.setText(font, texts[0]);
        }

        return maxWidth / glyph.width;
    }
}
