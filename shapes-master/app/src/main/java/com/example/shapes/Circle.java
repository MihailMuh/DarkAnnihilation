package com.example.shapes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

class Box {

}
class Human {

}
interface Postman {
    void getPost(Box box);
    void dropBox(Box box);
    void sendTo(Human human);
}


public class Circle extends Figures implements Postman {
    private static final double PI = 3.14;
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
    double getArea() {
        return radius * radius * PI;
    }

    @Override
    public void getPost(Box box) {

    }

    @Override
    public void dropBox(Box box) {

    }

    @Override
    public void sendTo(Human human) {

    }
}
