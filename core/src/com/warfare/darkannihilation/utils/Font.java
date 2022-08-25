package com.warfare.darkannihilation.utils;

import static com.warfare.darkannihilation.hub.Resources.getBatch;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.BitmapFontData;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.warfare.darkannihilation.hub.FontHub;
import com.warfare.darkannihilation.systemd.service.Processor;

public class Font {
    private final BitmapFont bitmapFont;
    private final BitmapFontData bitmapFontData;
    private final GlyphLayout glyph = new GlyphLayout();
    private final float scale, oldScale;

    private final Color oldColor;
    private Color color = Color.WHITE;

    public Font(BitmapFont font, float scale) {
        checkUIThread();

        this.scale = scale;
        bitmapFont = font;
        bitmapFontData = bitmapFont.getData();

        oldScale = bitmapFontData.scaleX;
        oldColor = new Color(font.getColor());
    }

    public static Font scaledFontWrap(BitmapFont font, float limit, String... strings) {
        return new Font(font, FontHub.resizeFont(font, limit, strings));
    }

    private static void checkUIThread() {
        if (!Processor.isUIThread()) throw new GdxRuntimeException("No OpenGL context found!");
    }

    private synchronized void setText(String text) {
        checkUIThread();

        bitmapFontData.setScale(scale);
        glyph.setText(bitmapFont, text);
        bitmapFontData.setScale(oldScale);
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
        color = oldColor;
    }

    public void draw(float x, float y, String text) {
        bitmapFontData.setScale(scale);
        bitmapFont.setColor(color);

        bitmapFont.draw(getBatch(), text, x, y);

        bitmapFont.setColor(Color.WHITE);
        bitmapFontData.setScale(oldScale);
    }
}
