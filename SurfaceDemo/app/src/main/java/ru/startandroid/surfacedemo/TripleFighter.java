package ru.startandroid.surfacedemo;

import android.graphics.Color;
import android.graphics.Paint;

public class TripleFighter{
    public int x;
    public int y;
    public int X;
    public int Y;
    public int speedX;
    public int speedY;
    public int width;
    public int height;
    private static final Paint paint = new Paint();
    private final Game game;
    public boolean lock = true;
    public int health = 6;
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

        width = ImageHub.tripleFighterImg.getWidth();
        height = ImageHub.tripleFighterImg.getHeight();

        x = get_random(0, 1920);
        y = -150;
        speedX = get_random(-3, 3);
        speedY = get_random(1, 10);
        lastShoot = System.nanoTime();
    }

    public void shoot() {
        now = System.nanoTime();
        if (now - lastShoot > shootTime) {
            lastShoot = now;

            X = ((game.player.x + game.player.width / 2) - (x + width / 2)) / 50;
            Y = ((game.player.y + game.player.height / 2) - (y + height / 2)) / 50;
            angle = Math.toDegrees(Math.atan2(Y, X) + (Math.PI / 2));

            BulletEnemy bullet = new BulletEnemy(game, x + width / 2, y + height / 2, angle, X, Y);
            game.bulletEnemies.add(bullet);
            game.numberBulletsEnemy += 1;
        }
    }

    public static int get_random(int min, int max){
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

    public void newStatus() {
        health = 7;
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
            for (int i = 20; i < 40; i++) {
                if (game.explosions[i].lock) {
                    game.explosions[i].start(bullet.x + bullet.width / 2, bullet.y + bullet.height / 2);
                    break;
                }
            }
            game.bullets.remove(bullet);
            game.numberBullets -= 1;
            if (health <= 0) {
                for (int i = 0; i < game.numberExplosions; i++) {
                    if (game.explosions[i].lock) {
                        game.explosions[i].start(x + width / 2, y + height / 2);
                        break;
                    }
                }
                AudioPlayer.playBoom();
                game.score += 5;
                newStatus();
            }
        }
    }

    public void check_intersectionPlayer() {
        if (x + 15 < game.player.x + 20 & game.player.x + 20 < x + width - 15 &
                y + 15 < game.player.y + 25 & game.player.y + 25 < y + height - 15 |
                game.player.x + 20 < x & x < game.player.x + game.player.width - 20 &
                        game.player.y + 25 < y & y < game.player.y + game.player.height - 20) {
            AudioPlayer.playMetal();
            for (int i = 20; i < 40; i++) {
                if (game.explosions[i].lock) {
                    game.explosions[i].start(x + width / 2, y + height / 2);
                    break;
                }
            }
            newStatus();
            game.player.health -= 10;
        }
    }

    public void update() {
        if (!lock) {
            check_intersectionPlayer();

            x += speedX;
            y += speedY;

            shoot();

            if (x < -width | x > screenWidth | y > screenHeight) {
                newStatus();
            }
//            game.canvas.drawRect(x, y, x + width, y + height, paint);

            game.canvas.drawBitmap(ImageHub.tripleFighterImg, x, y, paint);

        }
    }
}
