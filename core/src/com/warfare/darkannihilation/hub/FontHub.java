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
    public BitmapFont canisMinor;

    public FontHub(AssetManagerSuper assetManager) {
        super(assetManager);

        FileHandleResolver resolver = new InternalFileHandleResolver();
        assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

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

        assetManager.load("canis_minor.ttf", BitmapFont.class, params);
    }

    @Override
    public void boot() {
        canisMinor = assetManager.get("canis_minor.ttf", BitmapFont.class);
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
