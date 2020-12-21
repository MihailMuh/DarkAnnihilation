package com.example.shapes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Figures {
    String color;
    Figures(String color) {
        this.color = color;
    }
    void draw(Canvas canvas) {
    }
    String save_inf(JSONArray jsonArray) throws JSONException {return "There is a DATA";}
    void recovery_inf(JSONArray jsonArray) throws JSONException {}

}
