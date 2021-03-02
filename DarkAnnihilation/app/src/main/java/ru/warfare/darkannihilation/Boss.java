package ru.warfare.darkannihilation;

import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class Boss extends Sprite {
    public float maxHealth;
    private static final Paint paintFill = new Paint();
    private static final Paint paintOutLine = new Paint();

    private static final int shootTime = 500_000_000;
    private long lastShoot;
    private static long now;

    public Boss(Game g) {
        super(g, ImageHub.bossImage.getWidth(), ImageHub.bossImage.getHeight());

        paintFill.setColor(Color.RED);
        paintOutLine.setColor(Color.WHITE);

        health = 200;
        maxHealth = health;
        speedY = 1;

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

    @Override
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

    @Override
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

        if (health <= 0) {
            kill();
        }
    }

    @Override
    public void render() {
        game.canvas.drawBitmap(ImageHub.bossImage, x, y, null);

        game.canvas.drawRect(x + halfWidth - 70, y - 10, x + halfWidth + 70, y + 5, paintOutLine);
        game.canvas.drawRect(x + halfWidth - 68, y - 8, (float) (x + halfWidth - 72 + (health / maxHealth) * 140), y + 3, paintFill);
    }
}
