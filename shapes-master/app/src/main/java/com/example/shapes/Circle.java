package com.example.shapes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

public class Circle {
    String colorCircle;
    Point center;
    float radius;
    public Circle(String colorCircle, Point center, float radius) {
        this.colorCircle = colorCircle;
        this.center = center;
        this.radius = radius;
    }
    void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.parseColor(this.colorCircle));
        canvas.drawCircle(this.center.x, this.center.y, this.radius, paint);
    }
}
