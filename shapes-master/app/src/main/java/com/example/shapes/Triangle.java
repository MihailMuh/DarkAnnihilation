package com.example.shapes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

public class Triangle extends Figures {
    String color;
    Point a;
    Point b;
    Point c;
    public Triangle(String color, Point a, Point b, Point c){
        super(color);
        this.color = color;
        this.a = a;
        this.b = b;
        this.c = c;
    }
    void draw(Canvas canvas) {
        super.draw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.parseColor(this.color));

        Path path = new Path();
        path.moveTo(this.a.x, this.a.y);
        path.lineTo(this.b.x, this.b.y);
        path.lineTo(this.c.x, this.c.y);
        path.lineTo(this.a.x, this.a.y);

        canvas.drawPath(path, paint);
    }


}
