package com.example.shapes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import org.json.JSONException;

public class Figures {
    String color;
    Figures(String color) {
        this.color = color;
    }
    void draw(Canvas canvas) {
    }
    String save_inf() throws JSONException {return "There is a DATA";}
}
