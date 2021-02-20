package ru.startandroid.surfacedemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;

public class Vader {
    public Bitmap vaderImage;
    public int x;
    public int y;
    public int speedX;
    public int speedY;
    public int width;
    public int height;
    private static int screenWidth;
    private static int screenHeight;
    private static final boolean isFilter = true;
    private static final Paint paint = new Paint();
    private final Game game;
    public boolean lock = false;
    public int health = 2;

    public Vader(Game g) {
        game = g;
        screenWidth = game.screenWidth;
        screenHeight = game.screenHeight;
        paint.setColor(Color.WHITE);

        vaderImage = BitmapFactory.decodeResource(game.context.getResources(), R.drawable.vader3);
        vaderImage = Bitmap.createScaledBitmap(vaderImage, 75, 75, isFilter);
        width = vaderImage.getWidth();
        height = vaderImage.getHeight();

        x = get_random(0, 1920);
        y = -150;
        speedX = get_random(-5, 5);
        speedY = get_random(3, 10);
    }

    public static int get_random(int min, int max){
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

    public void newStatus() {
        x = get_random(0, 1920);
        y = -150;
        speedX = get_random(-5, 5);
        speedY = get_random(3, 10);
    }

    public void damage(int damage) {
        health -= damage;
        if (health <= 0) {
            newStatus();
        }
    }

    public void check_intersectionBullet(float playerX, float playerY, float playerWidth, float playerHeight, int damage) {
        if (x < playerX & playerX < x + width & y < playerY & playerY < y + height |
                playerX < x & x < playerX + playerWidth & playerY < y & y < playerY + playerHeight) {
            health -= damage;
            if (health <= 0) {
                game.audioPlayer.playBoom();
                newStatus();
            }
        }
    }

    public void check_intersectionPlayer(float playerX, float playerY, float playerWidth, float playerHeight) {
        if (x + 20 < playerX + 15 & playerX + 15 < x + width - 20 & y + 30 < playerY + 15 & playerY + 15 < y + height - 20 |
                playerX + 15 < x & x < playerX + playerWidth - 15 & playerY + 15 < y & y < playerY + playerHeight - 15) {
            game.audioPlayer.playMetal();
            newStatus();
            game.player.health -= 5;
        }
    }

    public void check_intersectionAI(float playerX, float playerY, float playerWidth, float playerHeight) {
        if (x + 20 < playerX + 15 & playerX + 15 < x + width - 20 & y + 30 < playerY + 15 & playerY + 15 < y + height - 20 |
                playerX + 15 < x & x < playerX + playerWidth - 15 & playerY + 15 < y & y < playerY + playerHeight - 15) {
            game.audioPlayer.playMetal();
            newStatus();
        }
    }

    public void update() {
        if (!lock) {
            x += speedX;
            y += speedY;

            if (x < -width | x > screenWidth + width | y > screenHeight + height) {
                newStatus();
            }

            game.canvas.drawBitmap(vaderImage, x, y, paint);
//            game.canvas.drawRect(x + 15, y + 15, x + width - 15, y + height - 15, paint);

        }
    }
}
