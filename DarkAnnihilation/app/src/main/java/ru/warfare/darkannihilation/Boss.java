package ru.warfare.darkannihilation;

import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class Boss {
    public int x;
    public int y;
    public int speedX = 0;
    public int speedY = 1;
    public int width;
    public int height;
    public int halfWidth;
    public int halfHeight;
    private final Game game;
    public float health = 200;
    public float maxHealth = health;
    private static final Paint paintFill = new Paint();
    private static final Paint paintOutLine = new Paint();

    private static final int shootTime = 1_500_000_000;
    private long lastShoot;
    private static long now;

    public Boss(Game g) {
        paintFill.setColor(Color.RED);
        paintOutLine.setColor(Color.WHITE);

        game = g;

        width = ImageHub.bossImage.getWidth();
        height = ImageHub.bossImage.getHeight();
        halfHeight = height / 2;
        halfWidth = width / 2;

        x = game.halfScreenWidth - halfWidth;
        y = -600;

        lastShoot = System.nanoTime();
    }

    public void shoot() {
        now = System.nanoTime();
        if (now - lastShoot > shootTime) {
            lastShoot = now;
            game.bulletBosses.add(new BulletBoss(game, x + width - 65, y + 20, 1));
            game.bulletBosses.add(new BulletBoss(game, x + width - 65, y + 20, 2));
            game.bulletBosses.add(new BulletBoss(game, x + width - 65, y + 20, 3));
            game.numberBulletsBoss += 3;
            AudioPlayer.playShoot();
        }
    }

    public void kill() {
        for (int i = 40; i < 45; i++) {
            if (game.explosions[i].lock) {
                game.explosions[i].start(x + halfWidth, y + halfHeight);
                break;
            }
        }
        AudioPlayer.playMegaBoom();
        game.score += 150;
        game.bosses.remove(this);
        game.numberBosses -= 1;
    }

    public void check_intersectionBullet(Bullet bullet) {
        if (x < bullet.x & bullet.x < x + width & y < bullet.y & bullet.y < y + height |
                bullet.x < x & x < bullet.x + bullet.width & bullet.y < y & y < bullet.y + bullet.height) {
            health -= bullet.damage;
            game.bullets.remove(bullet);
            game.numberBullets -= 1;
            for (int i = 20; i < 40; i++) {
                if (game.explosions[i].lock) {
                    game.explosions[i].start(bullet.x + bullet.halfWidth, bullet.y + bullet.halfHeight);
                    break;
                }
            }
        }
    }

    public void drawHealthBar() {
        game.canvas.drawRect(x + halfWidth - 70, y - 10, x + halfWidth + 70, y + 5, paintOutLine);
        game.canvas.drawRect(x + halfWidth - 68, y - 8, (float) (x + halfWidth - 72 + (health / maxHealth) * 140), y + 3, paintFill);
    }

    public void update() {
        x += speedX;
        y += speedY;

        if (y >= 50) {
            speedY = 0;
            speedX = -5;
            shoot();
        }
        if (x < -width) {
            x = game.screenWidth;
        }
    }

    public void render() {
        game.canvas.drawBitmap(ImageHub.bossImage, x, y, null);
        drawHealthBar();
    }
}
