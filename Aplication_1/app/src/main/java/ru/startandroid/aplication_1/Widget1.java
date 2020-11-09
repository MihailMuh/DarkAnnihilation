package ru.startandroid.aplication_1;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;
import android.content.Context;

import java.util.Random;

public class Widget1 extends View {
    float density;
    int x;
    int y;
    int block;
    int cell_x;
    int cell_y;
    int ship_x;
    int ship_y;
    boolean destroy;
    Random random;
    public Widget1(Context context, float density) {
        super(context);
        this.random = new Random();

        this.density = density;
        this.x = 0;
        this.y = 0;
        this.block = 100;
        this.destroy = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        this.cell_x = getWidth() / this.block;
        this.cell_y = getHeight() / this.block;
        this.ship_x = this.random.nextInt(this.cell_x);
        this.ship_y = this.random.nextInt(this.cell_y);
        Log.i("boom", "x: " + this.ship_x + ", y: " + this.ship_y);

        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);
        for (int i = 0; i < getWidth(); i += this.block) {
            canvas.drawLine(i, 0, i, getHeight(), paint);
        }
        for (int j = 0; j < getHeight(); j += this.block) {
            canvas.drawLine(0, j, getWidth(), j, paint);
        }

        boom(canvas);

    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            this.x = (int) event.getX() / this.block;
            this.y = (int) event.getY() / this.block;
            Log.i("onTouch", "x: " + this.x + ", y: " + this.y);
            if (this.x == this.ship_x & this.y == this.ship_y) {
                this.destroy = true;
                invalidate();
            }
        }
        return false;
    }



    public void boom(Canvas canvas) {
        if (this.destroy) {
            Paint d = new Paint();
            d.setColor(Color.RED);
            d.setStrokeWidth(2);
            d.setStyle(Paint.Style.STROKE);
            canvas.drawLine(this.x * this.block, this.y * this.block, this.x * this.block + this.block, this.y * this.block + this.block, d);
            canvas.drawLine(this.x * this.block, this.y * this.block + this.block, this.x * this.block + this.block, this.y * this.block, d);

        }
    }
}
