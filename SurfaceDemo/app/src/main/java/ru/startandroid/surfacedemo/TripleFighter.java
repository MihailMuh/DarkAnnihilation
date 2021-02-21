package ru.startandroid.surfacedemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;

public class TripleFighter{
    public Bitmap tripleFighterImg;
    public int x;
    public int y;
    public int speedX;
    public int speedY;
    public int width;
    public int height;
    private static final boolean isFilter = true;
    private static final Paint paint = new Paint();
    private final Game game;
    public boolean lock = true;
    public int health = 16;
    private static int screenWidth;
    private static int screenHeight;
    private double angle;
    private static final int shootTime = 1_000_000_000;
    private long lastShoot;
    private static long now;

    public TripleFighter(Game g) {
        game = g;
        screenWidth = game.screenWidth;
        screenHeight = game.screenHeight;
        paint.setColor(Color.WHITE);

        tripleFighterImg = BitmapFactory.decodeResource(game.context.getResources(), R.drawable.triple_fighter);
        tripleFighterImg = Bitmap.createScaledBitmap(tripleFighterImg, 105, 105, isFilter);
        width = tripleFighterImg.getWidth();
        height = tripleFighterImg.getHeight();

        x = get_random(0, 1920);
        y = -150;
        speedX = get_random(-3, 3);
        speedY = get_random(1, 10);
        lastShoot = System.nanoTime();
    }

    public void shoot() {
        now = System.nanoTime();
        if (now - lastShoot > shootTime) {
            int X = (game.player.x + game.player.width / 2) - (x + width / 2);
            int Y = (game.player.y + game.player.height / 2) - (y + height / 2);
            lastShoot = now;
            angle = Math.toDegrees(Math.atan2(Y, X) + (Math.PI / 2.0));

            BulletEnemy bullet1 = new BulletEnemy(game, x + width / 2, y + height / 2, angle, X / 50, Y / 50);
            game.bulletEnemies.add(bullet1);
            game.numberBulletsEnemy += 1;
        }
    }

    public static int get_random(int min, int max){
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

    public void newStatus() {
        x = get_random(0, 1920);
        y = -150;
        speedX = get_random(-3, 3);
        speedY = get_random(1, 10);
        lastShoot = System.nanoTime();
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
            game.player.health -= 10;
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

            shoot();

            if (x < -width | x > screenWidth | y > screenHeight) {
                newStatus();
            }
//            game.canvas.drawRect(x, y, x + width, y + height, paint);

            game.canvas.drawBitmap(tripleFighterImg, x, y, paint);

        }
    }

}
