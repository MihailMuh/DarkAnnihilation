package ru.warfare.darkannihilation.arts;

import static android.graphics.Color.WHITE;

import static ru.warfare.darkannihilation.systemd.service.Fonts.canisMinor;

import android.graphics.Paint;

public class CustomPaint extends Paint {
    public CustomPaint() {
        super();

        init();
        setColor(WHITE);
    }

    public CustomPaint(int textSize) {
        super();

        init();
        setColor(WHITE);
        setTextSize(textSize);
    }

    public CustomPaint(int textSize, int color) {
        super();

        init();
        setColor(color);
        setTextSize(textSize);
    }

    private void init() {
        setFilterBitmap(true);
        setDither(true);
        setAntiAlias(true);
        setFakeBoldText(true);
        setTypeface(canisMinor);
    }
}
