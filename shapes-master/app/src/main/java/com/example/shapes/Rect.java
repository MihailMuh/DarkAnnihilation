package com.example.shapes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

public class Rect {
    String colorRect;
    Point corner;
    int widthRect;
    int heightRect;
    public Rect(String colorRect, Point corner, int widthRect, int heightRect){
        this.colorRect = colorRect;
        this.corner = corner;
        this.widthRect = widthRect;
        this.heightRect = heightRect;
    }
    void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.parseColor(this.colorRect));
        canvas.drawRect(this.corner.x, this.corner.y, this.corner.x + this.widthRect, this.corner.y + this.heightRect, paint);
    }
}
