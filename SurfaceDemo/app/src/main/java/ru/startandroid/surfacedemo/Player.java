package ru.startandroid.surfacedemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.media.MediaPlayer;

public class Player {
    private static Bitmap playerImage;
    public int x;
    public int y;
    public int endX;
    public int endY;
    public int width;
    public int height;
    private static final boolean isFilter = true;
    public static final Paint color = new Paint();
    private static int screenWidth;
    private static int screenHeight;
    public int speedX;
    public int speedY;
    private static final int shootTime = 100_000_000;
    private long lastShoot;
    private static long now;
    private final Game game;
    public boolean lock = false;
    public int damage = 999;
    public int health = 50;

    public Player(Game g) {
        game = g;
        screenWidth = game.screenWidth;
        screenHeight = game.screenHeight;

        playerImage = BitmapFactory.decodeResource(game.context.getResources(), R.drawable.ship);
        playerImage = Bitmap.createScaledBitmap(playerImage, 100, 120, isFilter);
        width = playerImage.getWidth();
        height = playerImage.getHeight();

        x = screenWidth / 2;
        y = screenHeight / 2;
        endX = x;
        endY = y;

        lastShoot = System.nanoTime();
    }

    public void update() {
        if (!lock) {
            now = System.nanoTime();
            if (now - lastShoot > shootTime) {
                lastShoot = now;
                Bullet bullet = new Bullet(game, x + width / 2 - 3, y);
                game.bullets.add(bullet);
                game.numberBullets += 1;
            }
        }

        if (Math.sqrt((endX - x) * (endX - x) + (endY - y) * (endY - y)) > screenWidth / 2){
            speedX = 0;
            speedY = 0;
        } else {
            speedX = (endX - x) / 5;
            speedY = (endY - y) / 5;
        }

        if (health <= 0) {
            for (int i = 0; i < game.heartsNumbers; i++) {
                game.hearts.get(i).lock = true;
            }
        }

        x += speedX;
        y += speedY;
//        game.canvas.drawRect(x, y, x + width, y + height, color);
        game.canvas.drawBitmap(playerImage, x, y, color);
    }
}


class AI {
    public static Bitmap playerImage;
    public int x;
    public int y;
    public int width;
    public int height;
    private static final boolean isFilter = true;
    public static final Paint color = new Paint();
    private static int screenWidth;
    private static int screenHeight;
    public int speedX = get_random(3, 7);
    public int speedY = get_random(3, 7);
    private static final int shootTime = 250_000_000;
    private long lastShoot;
    private static long now;
    private final Game game;
    public int damage = 999;

    public AI(Game g) {
        game = g;
        screenWidth = game.screenWidth;
        screenHeight = game.screenHeight;

        playerImage = BitmapFactory.decodeResource(game.context.getResources(), R.drawable.ship);
        playerImage = Bitmap.createScaledBitmap(playerImage, 100, 120, isFilter);
        width = playerImage.getWidth();
        height = playerImage.getHeight();

        x = screenWidth / 2;
        y = screenHeight / 2;

        lastShoot = System.nanoTime();
    }

    public static int get_random(int min, int max){
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

    public void update() {
        now =  System.nanoTime();
        if (now - lastShoot > shootTime) {
            lastShoot = now;
            Bullet bullet = new Bullet(game, x + width / 2 - 3, y);
            game.bullets.add(bullet);
            game.numberBullets += 1;
        }

        if (x < 30 | x > screenWidth - height - 30) {
            speedX = -speedX;
        }
        if (y < 30 | y > screenHeight - width - 30) {
            speedY = -speedY;
        }

        x += speedX;
        y += speedY;
//        canvas.drawRect(x, y, x + width, y + height, color);
        game.canvas.drawBitmap(playerImage, x, y, color);
    }
}