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
    public BitmapFont canisMinor, fiendish;

    public FontHub(AssetManagerSuper assetManager) {
        super(assetManager);

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

        loadFiendish(new FreeTypeFontLoaderParameter());

        assetManager.load("canis_minor.ttf", BitmapFont.class, params);
    }

    private void loadFiendish(FreeTypeFontLoaderParameter params) {
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = params.fontParameters;

        params.fontFileName = "fonts/fiendish.ttf";
        parameter.size = 190;
        parameter.minFilter = Texture.TextureFilter.MipMapLinearLinear;
        parameter.magFilter = Texture.TextureFilter.Linear;
        parameter.hinting = FreeTypeFontGenerator.Hinting.Full;
        parameter.color = Color.BLACK;
        parameter.spaceX = 20;
        parameter.incremental = true;
        parameter.genMipMaps = true;

        assetManager.load("fiendish.ttf", BitmapFont.class, params);
    }

    @Override
    public void boot() {
        canisMinor = assetManager.get("canis_minor.ttf");
        fiendish = assetManager.get("fiendish.ttf");
    }

    public synchronized static float resizeFont(BitmapFont font, float maxWidth, String... texts) {
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
