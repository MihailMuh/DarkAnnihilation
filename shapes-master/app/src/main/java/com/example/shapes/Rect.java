package com.example.shapes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

public class Rect extends Figures {
    String color;
    Point corner;
    int widthRect;
    int heightRect;
    public Rect(String color, Point corner, int widthRect, int heightRect){
        super(color);
        this.color = color;
        this.corner = corner;
        this.widthRect = widthRect;
        this.heightRect = heightRect;
    }
    void draw(Canvas canvas) {
        super.draw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.parseColor(this.color));
        canvas.drawRect(this.corner.x, this.corner.y, this.corner.x + this.widthRect, this.corner.y + this.heightRect, paint);
    }
}
