package com.warfare.darkannihilation;

import static com.warfare.darkannihilation.systemd.Frontend.spriteBatch;
import static com.warfare.darkannihilation.systemd.service.Service.print;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class FontWrap {
    private final BitmapFont bitmapFont;
    private final GlyphLayout glyph = new GlyphLayout();
    private final float scale;

    public FontWrap(BitmapFont font, float scale) {
        this.scale = scale;
        bitmapFont = font;
    }

    private void setText(String text) {
        bitmapFont.getData().setScale(scale);
        glyph.setText(bitmapFont, text);
        bitmapFont.getData().setScale(1);
    }

    public float getTextWidth(String text) {
        setText(text);
        return glyph.width;
    }

    public float getTextHeight(String text) {
        setText(text);
        return glyph.height;
    }

    public void draw(float x, float y, String text) {
        bitmapFont.getData().setScale(scale);
        bitmapFont.draw(spriteBatch, text, x, y);
        bitmapFont.getData().setScale(1);
    }
}
