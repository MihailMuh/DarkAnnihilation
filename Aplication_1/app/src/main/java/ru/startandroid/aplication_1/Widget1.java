package ru.startandroid.aplication_1;

import android.view.View;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;
import android.content.Context;

public class Widget1 extends View {
    float density;
    public Widget1(Context context, float density) {
        super(context);
        this.density = density;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float block = 100 * (int) density;
        int b = 0 + (int) (Math.random() * 15);
        float width = getWidth() / block;
        float height = getHeight() / block;

        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(density);
        paint.setStyle(Paint.Style.STROKE);
        for (int i = 0; i < getWidth(); i += block) {
            canvas.drawLine(i, 0, i, getHeight(), paint);
        }
        for (int j = 0; j < getHeight(); j += block) {
            canvas.drawLine(0, j, getWidth(), j, paint);
        }
        System.out.println(b);
        System.out.println(width);
        System.out.println(density);
        Paint d = new Paint();
        d.setColor(Color.RED);
        d.setStrokeWidth(density);
        d.setStyle(Paint.Style.STROKE);
        canvas.drawLine(b*getWidth()-block, block, b*getWidth(), block, d);

    }
}
