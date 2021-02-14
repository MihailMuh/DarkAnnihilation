package ru.startandroid.surfacedemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class Player {
    private Context context;
    private static Bitmap playerImage;
    public float x;
    public float y;
    public float endX;
    public float endY;
    public float width;
    public float height;
    private static final boolean isFilter = true;
    public static final Paint color = new Paint();
    private static int screenWidth;
    private static int screenHeight;
    public float speedX;
    public float speedY;
    private static final int shootTime = 200_000_000;
    private long lastShoot;
    private long now;

    public Player(Context c) {
        context = c;
        playerImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.ship);
        playerImage = Bitmap.createScaledBitmap(playerImage, 100, 120, isFilter);
        width = playerImage.getWidth();
        height = playerImage.getHeight();
        lastShoot = System.nanoTime();
    }

    public void setCoords(int width, int height) {
        screenWidth = width;
        screenHeight = height;
        x = (float) screenWidth / 2;
        y = (float) screenHeight / 2;
        endX = x;
        endY = y;
    }

    public void update(Canvas canvas, BulletGroup bulletGroup) {
        now =  System.nanoTime();
        if (now - lastShoot > shootTime) {
            lastShoot = now;
            Bullet bullet = new Bullet(context, (int) (x + width / 2), (int) y);
            bulletGroup.append(bullet);
        }

        speedX = (endX - x) / 5;
        speedY = (endY - y) / 5;
        x += speedX;
        y += speedY;
//        canvas.drawRect(x, y, x + width, y + height, color);
        canvas.drawBitmap(playerImage, x, y, color);
    }
}
