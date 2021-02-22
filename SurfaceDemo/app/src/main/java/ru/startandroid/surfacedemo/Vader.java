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
        health = 2;
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

    public void check_intersectionBullet(Bullet bullet) {
        if (x < bullet.x & bullet.x < x + width & y < bullet.y & bullet.y < y + height |
                bullet.x < x & x < bullet.x + bullet.width & bullet.y < y & y < bullet.y + bullet.height) {
            health -= bullet.damage;
            bullet.bulletImage.recycle();
            game.bullets.remove(bullet);
            game.numberBullets -= 1;
            if (health <= 0) {
                game.audioPlayer.playBoom();
                game.score += 1;
                newStatus();
            }
        }
    }

    public void check_intersectionPlayer() {
        if (x + 20 < game.player.x + 15 & game.player.x + 15 < x + width - 20 &
                y + 30 < game.player.y + 15 & game.player.y + 15 < y + height - 20 |
                game.player.x + 15 < x & x < game.player.x + game.player.width - 15 &
                        game.player.y + 15 < y & y < game.player.y + game.player.height - 15) {
            game.audioPlayer.playMetal();
            newStatus();
            game.player.health -= 5;
        }
    }

    public void check_intersectionAI() {
        if (x + 20 < game.ai.x + 15 & game.ai.x + 15 < x + width - 20 &
                y + 30 < game.ai.y + 15 & game.ai.y + 15 < y + height - 20 |
                game.ai.x + 15 < x & x < game.ai.x + game.ai.width - 15 &
                        game.ai.y + 15 < y & y < game.ai.y + game.ai.height - 15) {
            game.audioPlayer.playMetal();
            newStatus();
        }
    }

    public void update() {
        if (!lock) {
            x += speedX;
            y += speedY;

            if (x < -width | x > screenWidth | y > screenHeight) {
                newStatus();
            }

            game.canvas.drawBitmap(vaderImage, x, y, paint);
//            game.canvas.drawRect(x + 15, y + 15, x + width - 15, y + height - 15, paint);

        }
    }
}
