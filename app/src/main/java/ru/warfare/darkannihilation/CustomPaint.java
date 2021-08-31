package ru.warfare.darkannihilation;

import static android.graphics.Color.WHITE;

import static ru.warfare.darkannihilation.systemd.service.Fonts.canisMinor;

import android.graphics.Paint;

public class CustomPaint extends Paint {
    public CustomPaint() {
        super();

        setColor(WHITE);
        setFilterBitmap(true);
        setDither(true);
        setAntiAlias(true);
        setFakeBoldText(true);
        setTypeface(canisMinor);
    }
}
