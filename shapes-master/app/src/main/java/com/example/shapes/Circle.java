package com.example.shapes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

public class Circle extends Figures {
    Point center;
    float radius;
    public Circle(String color, Point center, float radius) {
        super(color);
        this.color = color;
        this.center = center;
        this.radius = radius;
    }
    
    void draw(Canvas canvas) {
        super.draw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.parseColor(this.color));
        canvas.drawCircle(this.center.x, this.center.y, this.radius, paint);
    }
}
