package com.example.shapes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import org.json.JSONException;
import org.json.JSONObject;

public class Circle extends Figures {
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

    @Override
    String save_inf() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "circle");
        jsonObject.put("x", this.center.x);
        jsonObject.put("y", this.center.y);
        jsonObject.put("radius", this.radius);
        jsonObject.put("color", this.color);
        return jsonObject.toString();
    }


}
