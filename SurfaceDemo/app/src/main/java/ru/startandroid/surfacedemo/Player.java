package ru.startandroid.surfacedemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;


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
    private static final int shootTime = 100_000_000;
    private long lastShoot;
    private long now;

    public Player(Context c, int screenW, int screenH) {
        context = c;
        screenWidth = screenW;
        screenHeight = screenH;

        playerImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.ship);
        playerImage = Bitmap.createScaledBitmap(playerImage, 100, 120, isFilter);
        width = playerImage.getWidth();
        height = playerImage.getHeight();

        x = (float) screenWidth / 2;
        y = (float) screenHeight / 2;
        endX = x;
        endY = y;

        lastShoot = System.nanoTime();
    }

    public void reboot() {
        x = (float) screenWidth / 2;
        y = (float) screenHeight / 2;
        endX = x;
        endY = y;
    }

    public void update(Canvas canvas, LinkedList<Bullet> bullets) {
        now =  System.nanoTime();
        if (now - lastShoot > shootTime) {
            lastShoot = now;
            Bullet bullet = new Bullet(context, (int) (x + width / 2) - 3, (int) y);
            bullets.add(bullet);
        }


        if (Math.sqrt((endX - x) * (endX - x) + (endY - y) * (endY - y)) > screenWidth / 2){
            speedX = 0;
            speedY = 0;
        } else {
            speedX = (endX - x) / 5;
            speedY = (endY - y) / 5;
        }
        x += speedX;
        y += speedY;
//        canvas.drawRect(x, y, x + width, y + height, color);
        canvas.drawBitmap(playerImage, x, y, color);
    }
}
