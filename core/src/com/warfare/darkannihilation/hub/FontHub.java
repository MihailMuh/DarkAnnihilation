package com.warfare.darkannihilation.hub;

import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;
import com.warfare.darkannihilation.utils.AssetManagerSuper;

public class FontHub extends BaseHub {
    public BitmapFont canisMinor, canisMinorHuge;

    public FontHub(AssetManagerSuper assetManager) {
        super(assetManager);

        FileHandleResolver resolver = new InternalFileHandleResolver();
        assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        assetManager.setLoader(BitmapFont.class, ".otf", new FreetypeFontLoader(resolver));

        FreeTypeFontLoaderParameter params = new FreeTypeFontLoaderParameter();
        params.fontFileName = "fonts/canis_minor.otf";
        params.fontParameters.size = 80;
        params.fontParameters.minFilter = Texture.TextureFilter.Linear;
        params.fontParameters.magFilter = Texture.TextureFilter.Linear;
        params.fontParameters.borderColor = Color.WHITE;
        params.fontParameters.hinting = FreeTypeFontGenerator.Hinting.Full;
        params.fontParameters.shadowOffsetY = 11;
        params.fontParameters.shadowOffsetX = 11;
        params.fontParameters.borderWidth = 3f;

        assetManager.load("canis_minor.otf", BitmapFont.class, params);

        params = new FreeTypeFontLoaderParameter();
        params.fontFileName = "fonts/canis_minor.otf";
        params.fontParameters.size = 140;
        params.fontParameters.borderColor = Color.WHITE;
        params.fontParameters.hinting = FreeTypeFontGenerator.Hinting.Full;
        params.fontParameters.minFilter = Texture.TextureFilter.Linear;
        params.fontParameters.magFilter = Texture.TextureFilter.Linear;
        params.fontParameters.gamma = 1f;
        params.fontParameters.shadowOffsetY = 5;
        params.fontParameters.shadowOffsetX = 5;
        params.fontParameters.borderWidth = 3f;

        assetManager.load("canis_minor_huge.otf", BitmapFont.class, params);
    }

    @Override
    public void boot() {
        canisMinor = assetManager.get("canis_minor.otf", BitmapFont.class);
        canisMinorHuge = assetManager.get("canis_minor_huge.otf", BitmapFont.class);
    }

    public static float resizeFont(BitmapFont font, float maxWidth, String... texts) {
        GlyphLayout glyph = new GlyphLayout();

        if (texts.length != 1) {
            float maxTextWidth = -1;
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
