package com.warfare.darkannihilation.utils;

import static com.warfare.darkannihilation.systemd.Frontend.spriteBatch;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.warfare.darkannihilation.hub.FontHub;

public class FontWrap {
    private final BitmapFont bitmapFont;
    private final BitmapFont.BitmapFontData bitmapFontData;
    private final GlyphLayout glyph = new GlyphLayout();

    private final float scale;

    private Color color = Color.WHITE;

    public FontWrap(BitmapFont font, float scale) {
        this.scale = scale;
        bitmapFont = font;
        bitmapFontData = bitmapFont.getData();
    }

    public static FontWrap scaledFontWrap(BitmapFont font, float limit, String... strings) {
        return new FontWrap(font, FontHub.resizeFont(font, limit, strings));
    }

    private synchronized void setText(String text) {
        bitmapFontData.setScale(scale);
        glyph.setText(bitmapFont, text);
        bitmapFontData.setScale(1);
    }

    public float getTextWidth(String text) {
        setText(text);
        return glyph.width;
    }

    public float getHalfTextWidth(String string) {
        return getTextWidth(string) / 2f;
    }

    public float getTextHeight(String text) {
        setText(text);
        return glyph.height;
    }

    public float getHalfTextHeight(String string) {
        return getTextHeight(string) / 2f;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void resetColor() {
        color = Color.WHITE;
    }

    public void draw(float x, float y, String text) {
        final BitmapFont bitmapFont = this.bitmapFont;
        final BitmapFont.BitmapFontData bitmapFontData = this.bitmapFontData;

        bitmapFontData.setScale(scale);
        bitmapFont.setColor(color);

        bitmapFont.draw(spriteBatch, text, x, y);

        bitmapFont.setColor(Color.WHITE);
        bitmapFontData.setScale(1);
    }
}
