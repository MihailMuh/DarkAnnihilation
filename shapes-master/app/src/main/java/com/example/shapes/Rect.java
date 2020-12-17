package com.example.shapes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    @Override
    String save_inf(JSONArray jsonArray) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "rect");
        jsonObject.put("x", this.corner.x);
        jsonObject.put("y", this.corner.y);
        jsonObject.put("width", this.widthRect);
        jsonObject.put("height", this.heightRect);
        jsonObject.put("color", this.color);
        jsonArray.put(jsonObject);

        return jsonArray.toString();
    }
}
