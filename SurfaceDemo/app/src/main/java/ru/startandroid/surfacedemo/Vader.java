package ru.startandroid.surfacedemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Vader {
    private Bitmap vaderImage;
    public float x;
    public float y;
    public float speedX;
    public float speedY;
    public float width;
    public float height;
    private int screenWidth;
    private int screenHeight;
    private boolean isFilter = true;
    private final Paint paint = new Paint();

    public Vader(Context context) {
        vaderImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.vader3);
        vaderImage = Bitmap.createScaledBitmap(vaderImage, 75, 75, isFilter);
        x = get_random(0, 1920);
        y = -150;
        speedX = get_random(-5, 5);
        speedY = get_random(3, 10);
        width = vaderImage.getWidth();
        height = vaderImage.getHeight();
    }

    public void setCoords(int width, int height) {
        screenWidth = width;
        screenHeight = height;
    }

    public float get_random(int min, int max){
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

    public void newStatus() {
        x = get_random(0, 1920);
        y = -150;
        speedX = get_random(-5, 5);
        speedY = get_random(3, 10);
    }

    public void check_intersection(float playerX, float playerY, float playerWidth, float playerHeight) {
        if (x < playerX & playerX < x + width & y < playerY & playerY < y + height |
                playerX < x & x < playerX + playerWidth & playerY < y & y < playerY + playerHeight) {
            newStatus();
        }
    }

    public void update(Canvas canvas) {
        x += speedX;
        y += speedY;
//        canvas.drawRect(x, y, x + width, y + height, paint);
        canvas.drawBitmap(vaderImage, x, y, paint);
        if (x < -width | x > screenWidth + width | y > screenHeight + height) {
            newStatus();
        }
    }
}
