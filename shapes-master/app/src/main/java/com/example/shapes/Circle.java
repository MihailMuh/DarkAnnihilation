package com.example.shapes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Circle extends Figures {
    Point center;
    float radius;

    public Circle(String color, Point center, float radius) {
        super(color);
        this.color = color;
        this.center = center;
        this.radius = radius;
    }

    public Circle(JSONObject jsonObject) throws JSONException {
        this(
                jsonObject.getString("color"),
                new Point(jsonObject.getInt("x"), jsonObject.getInt("y")),
                (float)jsonObject.getDouble("radius")
        );
    }

    void draw(Canvas canvas) {
        super.draw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.parseColor(this.color));
        canvas.drawCircle(this.center.x, this.center.y, this.radius, paint);
    }
    @Override
    void save_inf(JSONArray jsonArray) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "circle");
        jsonObject.put("x", this.center.x);
        jsonObject.put("y", this.center.y);
        jsonObject.put("radius", this.radius);
        jsonObject.put("color", this.color);
        jsonArray.put(jsonObject);
    }
}
