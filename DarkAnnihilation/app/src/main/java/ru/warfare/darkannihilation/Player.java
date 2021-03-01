package ru.warfare.darkannihilation;

import android.graphics.Color;
import android.graphics.Paint;

public class Player {
    public int x;
    public int y;
    public int endX;
    public int endY;
    public int width;
    public int height;
    public static Paint paint = new Paint();
    private static int screenWidth;
    private static int screenHeight;
    public int speedX = get_random(3, 7);
    public int speedY = get_random(3, 7);
    private static final int shootTime = 100_000_000;
    private long lastShoot;
    private static long now;
    private final Game game;
    public boolean lock = false;
    public int health = 50;
    public int ai = 1;
    public boolean dontmove = false;

    public Player(Game g) {
        game = g;
        screenWidth = game.screenWidth;
        screenHeight = game.screenHeight;
        paint.setColor(Color.WHITE);

        width = ImageHub.playerImage.getWidth();
        height = ImageHub.playerImage.getHeight();

        x = screenWidth / 2;
        y = screenHeight / 2;
        endX = x;
        endY = y;

        lastShoot = System.nanoTime();
    }

    public static int get_random(int min, int max){
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

    public void AI() {
        ai = 1;
        x = screenWidth / 2;
        y = screenHeight / 2;
        speedX = get_random(3, 7);
        speedY = get_random(3, 7);
    }

    public void PLAYER() {
        ai = 0;
        x = screenWidth / 2;
        y = screenHeight / 2;
        lock = true;
        health = 50;
    }

    public void check_intersectionBullet(BulletEnemy bulletEnemy) {
        if (x + 10 < bulletEnemy.x & bulletEnemy.x < x + width - 10 &
                y + 10 < bulletEnemy.y & bulletEnemy.y < y + height - 20 |
                bulletEnemy.x < x & x < bulletEnemy.x + bulletEnemy.width &
                        bulletEnemy.y < y & y < bulletEnemy.y + bulletEnemy.height) {
            health -= bulletEnemy.damage;
            for (int i = 20; i < 40; i++) {
                if (game.explosions[i].lock) {
                    game.explosions[i].start(bulletEnemy.x + bulletEnemy.halfWidth, bulletEnemy.y + bulletEnemy.halfHeight);
                    break;
                }
            }
            game.bulletEnemies.remove(bulletEnemy);
            game.numberBulletsEnemy -= 1;
        }
    }

    public void check_intersectionBullet(BulletBoss bulletEnemy) {
        if (x + 10 < bulletEnemy.x & bulletEnemy.x < x + width - 10 &
                y + 10 < bulletEnemy.y & bulletEnemy.y < y + height - 20 |
                bulletEnemy.x < x & x < bulletEnemy.x + bulletEnemy.width &
                        bulletEnemy.y < y & y < bulletEnemy.y + bulletEnemy.height) {
            health -= bulletEnemy.damage;
            for (int i = 20; i < 40; i++) {
                if (game.explosions[i].lock) {
                    game.explosions[i].start(bulletEnemy.x + bulletEnemy.halfWidth, bulletEnemy.y + bulletEnemy.halfHeight);
                    break;
                }
            }
            game.bulletBosses.remove(bulletEnemy);
            game.numberBulletsBoss -= 1;
        }
    }

    public void update() {
        x += speedX;
        y += speedY;

        if (!lock) {
            now = System.nanoTime();
            if (now - lastShoot > shootTime) {
                lastShoot = now;
                game.bullets.add(new Bullet(game, x + width / 2 - 6, y));
                game.bullets.add(new Bullet(game, x + width / 2, y));
                game.numberBullets += 2;
            }
        }

        if (ai == 0) {

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
        } else {
            if (x < 30 | x > screenWidth - height - 30) {
                speedX = -speedX;
            }
            if (y < 30 | y > screenHeight - width - 30) {
                speedY = -speedY;
            }
        }

        game.canvas.drawBitmap(ImageHub.playerImage, x, y, null);
//        game.canvas.drawRect(x + 20, y + 30, x + width - 20, y + height - 20, paint);
    }
}
