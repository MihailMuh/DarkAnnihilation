package ru.startandroid.surfacedemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;

public class Player {
    private static Bitmap playerImage;
    public int x;
    public int y;
    public int endX;
    public int endY;
    public int width;
    public int height;
    private static final boolean isFilter = true;
    public static Paint paint = new Paint();
    private static int screenWidth;
    private static int screenHeight;
    public int speedX;
    public int speedY;
    private static final int shootTime = 100_000_000;
    private long lastShoot;
    private static long now;
    private final Game game;
    public boolean lock = false;
    public int health = 50;

    public Player(Game g) {
        game = g;
        screenWidth = game.screenWidth;
        screenHeight = game.screenHeight;
        paint.setColor(Color.WHITE);

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

    public void check_intersectionBullet(BulletEnemy bulletEnemy) {
        if (x + 20 < bulletEnemy.x + 5 & bulletEnemy.x + 5 < x + width - 20 &
                y + 30 < bulletEnemy.y + 5 & bulletEnemy.y + 5 < y + height - 20 |
                bulletEnemy.x + 5 < x & x < bulletEnemy.x + bulletEnemy.width - 5 &
                        bulletEnemy.y + 5 < y & y < bulletEnemy.y + bulletEnemy.height - 5) {
            health -= bulletEnemy.damage;
            bulletEnemy.bulletImage.recycle();
            game.bulletEnemies.remove(bulletEnemy);
            game.numberBulletsEnemy -= 1;
        }
    }

    public void update() {
        x += speedX;
        y += speedY;

        if (!lock) {
            now = System.nanoTime();
            if (now - lastShoot > shootTime) {
                lastShoot = now;
                Bullet bullet = new Bullet(game, x + width / 2 - 3, y);
                game.bullets.add(bullet);
                game.numberBullets += 1;
            }
        }

        speedX = (endX - x) / 5;
        speedY = (endY - y) / 5;


        switch (health) {
            case 50:
                game.hearts[0].update("full");
                game.hearts[1].update("full");
                game.hearts[2].update("full");
                game.hearts[3].update("full");
                game.hearts[4].update("full");
                break;
            case 45:
                game.hearts[0].update("half");
                game.hearts[1].update("full");
                game.hearts[2].update("full");
                game.hearts[3].update("full");
                game.hearts[4].update("full");
                break;
            case 40:
                game.hearts[0].update("non");
                game.hearts[1].update("full");
                game.hearts[2].update("full");
                game.hearts[3].update("full");
                game.hearts[4].update("full");
                break;
            case 35:
                game.hearts[0].update("non");
                game.hearts[1].update("half");
                game.hearts[2].update("full");
                game.hearts[3].update("full");
                game.hearts[4].update("full");
                break;
            case 30:
                game.hearts[0].update("non");
                game.hearts[1].update("non");
                game.hearts[2].update("full");
                game.hearts[3].update("full");
                game.hearts[4].update("full");
                break;
            case 25:
                game.hearts[0].update("non");
                game.hearts[1].update("non");
                game.hearts[2].update("half");
                game.hearts[3].update("full");
                game.hearts[4].update("full");
                break;
            case 20:
                game.hearts[0].update("non");
                game.hearts[1].update("non");
                game.hearts[2].update("non");
                game.hearts[3].update("full");
                game.hearts[4].update("full");
                break;
            case 15:
                game.hearts[0].update("non");
                game.hearts[1].update("non");
                game.hearts[2].update("non");
                game.hearts[3].update("half");
                game.hearts[4].update("full");
                break;
            case 10:
                game.hearts[0].update("non");
                game.hearts[1].update("non");
                game.hearts[2].update("non");
                game.hearts[3].update("non");
                game.hearts[4].update("full");
                break;
            case 5:
                game.hearts[0].update("non");
                game.hearts[1].update("non");
                game.hearts[2].update("non");
                game.hearts[3].update("non");
                game.hearts[4].update("half");
                break;
            case 0:
                game.hearts[0].update("non");
                game.hearts[1].update("non");
                game.hearts[2].update("non");
                game.hearts[3].update("non");
                game.hearts[4].update("non");
                break;
        }

        game.canvas.drawBitmap(playerImage, x, y, paint);
//        game.canvas.drawRect(x + 20, y + 30, x + width - 20, y + height - 20, paint);
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