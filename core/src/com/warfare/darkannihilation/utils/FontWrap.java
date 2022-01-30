package com.warfare.darkannihilation.utils;

import static com.warfare.darkannihilation.systemd.Frontend.spriteBatch;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class FontWrap {
    private final BitmapFont bitmapFont;
    private final BitmapFont.BitmapFontData bitmapFontData;
    private final GlyphLayout glyph = new GlyphLayout();
    private final float scale;

    public FontWrap(BitmapFont font, float scale) {
        this.scale = scale;
        bitmapFont = font;
        bitmapFontData = bitmapFont.getData();
    }

    private void setText(String text) {
        bitmapFontData.setScale(scale);
        glyph.setText(bitmapFont, text);
        bitmapFontData.setScale(1);
    }

    public float getTextWidth(String text) {
        setText(text);
        return glyph.width;
    }

    public float getTextHeight(String text) {
        setText(text);
        return glyph.height;
    }

    public void setColor(float r, float g, float b, float a) {
        bitmapFont.setColor(r, g, b, a);
    }

    public void setColor(Color color) {
        bitmapFont.setColor(color);
    }

    public void draw(float x, float y, String text) {
        bitmapFontData.setScale(scale);
        bitmapFont.draw(spriteBatch, text, x, y);
        bitmapFontData.setScale(1);
    }
}
