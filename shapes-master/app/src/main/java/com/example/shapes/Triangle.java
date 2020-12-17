package com.example.shapes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    @Override
    String save_inf(JSONArray jsonArray) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "triangle");
        jsonObject.put("xa", this.a.x);
        jsonObject.put("ya", this.a.y);
        jsonObject.put("xb", this.b.x);
        jsonObject.put("yb", this.b.y);
        jsonObject.put("xc", this.c.x);
        jsonObject.put("yc", this.c.y);
        jsonObject.put("color", this.color);
        jsonArray.put(jsonObject);
        return jsonArray.toString();
    }

}
