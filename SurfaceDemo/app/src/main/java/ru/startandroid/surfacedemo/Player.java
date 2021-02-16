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
    private Game game;

    public Player(Game g) {
        game = g;
        context = game.context;
        screenWidth = game.screenWidth;
        screenHeight = game.screenHeight;

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

    public void update() {
        now =  System.nanoTime();
        if (now - lastShoot > shootTime) {
            lastShoot = now;
            Bullet bullet = new Bullet(game, (int) (x + width / 2) - 3, (int) y);
            game.bullets.add(bullet);
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
        game.canvas.drawBitmap(playerImage, x, y, color);
    }
}


class AI {
    private Context context;
    public static Bitmap playerImage;
    public float x;
    public float y;
    public float width;
    public float height;
    private static final boolean isFilter = true;
    public static final Paint color = new Paint();
    private static int screenWidth;
    private static int screenHeight;
    public float speedX = 3;
    public float speedY = 3;
    private static final int shootTime = 250_000_000;
    private long lastShoot;
    private long now;
    private Game game;

    public AI(Game g) {
        game = g;
        context = game.context;
        screenWidth = game.screenWidth;
        screenHeight = game.screenHeight;

        playerImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.ship);
        playerImage = Bitmap.createScaledBitmap(playerImage, 100, 120, isFilter);
        width = playerImage.getWidth();
        height = playerImage.getHeight();

        x = (float) screenWidth / 2;
        y = (float) screenHeight / 2;

        lastShoot = System.nanoTime();
    }

    public void reboot() {
        x = (float) screenWidth / 2;
        y = (float) screenHeight / 2;
    }

    public void update() {
        now =  System.nanoTime();
        if (now - lastShoot > shootTime) {
            lastShoot = now;
            Bullet bullet = new Bullet(game, (int) (x + width / 2) - 3, (int) y);
            game.bullets.add(bullet);
        }

        if (x < 30 | x > screenWidth - 30) {
            speedX = -speedX;
        }
        if (y < 30 | y > screenHeight- 30) {
            speedY = -speedY;
        }

        x += speedX;
        y += speedY;
//        canvas.drawRect(x, y, x + width, y + height, color);
        game.canvas.drawBitmap(playerImage, x, y, color);
    }
}