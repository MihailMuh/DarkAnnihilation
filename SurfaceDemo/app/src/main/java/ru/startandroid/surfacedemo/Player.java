package ru.startandroid.surfacedemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class Player extends Sprite{
    private Bitmap playerImage;
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
    public float speedX;
    public float speedY;
    public final Bullet[] bullet = new Bullet[12];
    public int ammo = 0;
    private int start = 1;
    private static Timer timer = new Timer();
    private static TimerTask timerTask;


    public Player(Context context) {
        playerImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.ship);
        playerImage = Bitmap.createScaledBitmap(playerImage, 100, 120, isFilter);
        width = playerImage.getWidth();
        height = playerImage.getHeight();
        for (int i = 0; i < 12; i++) {
            bullet[i] = new Bullet(context, this);
        }
//        ammo = 0;
    }

    @Override
    public void setCoords(int width, int height) {
        screenWidth = width;
        screenHeight = height;
        x = (float) screenWidth / 2;
        y = (float) screenHeight / 2;
        endX = x;
        endY = y;
    }

    public void start() {
        timerTask = new TimerTask()
        {
            @Override
            public void run() {
                if (ammo != 12) {
                    bullet[ammo].start = 1;
                    ammo += 1;
                } else {
                    ammo = 0;
                }
            }
        };
        timer.schedule(timerTask, 50, 600);
    }

    @Override
    public void update(Canvas canvas) {
        speedX = (endX - x) / 5;
        speedY = (endY - y) / 5;
        x += speedX;
        y += speedY;
//        canvas.drawRect(x, y, x + width, y + height, color);
        canvas.drawBitmap(playerImage, x, y, color);
    }
}
