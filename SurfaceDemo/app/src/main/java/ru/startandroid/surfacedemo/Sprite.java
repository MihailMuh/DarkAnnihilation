package ru.startandroid.surfacedemo;


import android.graphics.Canvas;

public class Sprite {
    private int screenWidth;
    private int screenHeight;
    public void setCoords(int width, int height) {
        screenWidth = width;
        screenHeight = height;
    }
    public void update(Canvas canvas) {}
    public void update(Canvas canvas, Player player) {}

}
