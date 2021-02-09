package ru.startandroid.surfacedemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class Player {
    private Bitmap player_image;
    public float x;
    public float y;
    public float endX;
    public float endY;
    public float width;
    public float height;
    private final boolean isFilter = true;
    public final Paint color = new Paint();
    private int screenWidth;
    private int screenHeight;
    float speedX;
    float speedY;

    public Player(Context context) {
        player_image = BitmapFactory.decodeResource(context.getResources(), R.drawable.ship);
        player_image = Bitmap.createScaledBitmap(player_image, 100, 120, isFilter);
        width = player_image.getWidth();
        height = player_image.getHeight();
    }

    public void setCoords(int width, int height) {
        screenWidth = width;
        screenHeight = height;
        x = (float) screenWidth / 2;
        y = (float) screenHeight / 2;
        endX = x;
        endY = y;
    }

    public void update(Canvas canvas) {
        speedX = (endX - x) / 5;
        speedY = (endY - y) / 5;
        x += speedX;
        y += speedY;
//        canvas.drawRect(x, y, x + width, y + height, color);
        canvas.drawBitmap(player_image, x, y, color);
    }
}
