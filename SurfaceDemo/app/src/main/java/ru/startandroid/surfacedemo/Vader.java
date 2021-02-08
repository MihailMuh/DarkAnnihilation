package ru.startandroid.surfacedemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.util.Random;

public class Vader {
    private Bitmap vader_img;
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

//    Rectangle imgRect = new Rectangle(263, 146, img.getWidth(), img.getHeight());
//    Rectangle img7Rect = new Rectangle(x+ player.getmapX() + 500, y + player.getmapY() + 500, 40, 40);
//
//        ()
//            if(imgRect.intersects(img7Rect)) {
//    }

    public Vader(Context context) {
        vader_img = BitmapFactory.decodeResource(context.getResources(), R.drawable.vader3);
        vader_img = Bitmap.createScaledBitmap(vader_img, 75, 75, isFilter);
        x = get_random(0, 1920);
        y = -50;
        speedX = get_random(-5, 5);
        speedY = get_random(3, 10);
        width = vader_img.getWidth();
        height = vader_img.getHeight();
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
        y = -50;
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
        canvas.drawRect(x, y, x + width, y + height, paint);
        canvas.drawBitmap(vader_img, x, y, paint);
        if (x < -width | x > screenWidth + width | y > screenHeight + height * 2) {
            newStatus();
        }
    }
}
