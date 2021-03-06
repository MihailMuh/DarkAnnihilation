package ru.warfare.darkannihilation;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

public class Boss extends Sprite {
    public float maxHealth;
    private static final Paint paintFill = new Paint();
    private static final Paint paintOutLine = new Paint();

    private static final int shootTime = 500;
    private long lastShoot;
    private static long now;

    public Boss(Game g) {
        super(g, ImageHub.bossImage.getWidth(), ImageHub.bossImage.getHeight());

        paintFill.setColor(Color.RED);
        paintOutLine.setColor(Color.WHITE);

        health = 120;
        maxHealth = health;
        speedY = 1;

        x = game.halfScreenWidth - halfWidth;
        y = -600;

        lastShoot = System.currentTimeMillis();
    }

    public void shoot() {
        now = System.currentTimeMillis();
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
        for (int i = 0; i < game.numberVaders; i++) {
            if (Game.random.nextFloat() <= 0.1) {
                game.tripleFighters.add(new TripleFighter(game));
                game.numberTripleFighters += 1;
            }
        }
        AudioPlayer.bossMusic.pause();
        AudioPlayer.pirateMusic.start();
        Game.lastBoss = System.currentTimeMillis();
    }

    @Override
    public void check_intersectionBullet(Bullet bullet) {
        if (x + 20 < bullet.x & bullet.x < x + width - 20 & y + 20 < bullet.y & bullet.y < y + height - 20 |
                bullet.x < x + 20 & x + 20 < bullet.x + bullet.width & bullet.y < y + 20 & y + 20 < bullet.y + bullet.height) {
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

    public void check_intersectionBullet(Buckshot bullet) {
        if (x + 20 < bullet.x & bullet.x < x + width - 20 & y + 20 < bullet.y & bullet.y < y + height - 20 |
                bullet.x < x + 20 & x + 20 < bullet.x + bullet.width & bullet.y < y + 20 & y + 20 < bullet.y + bullet.height) {
            health -= bullet.damage;
            game.buckshots.remove(bullet);
            game.numberBuckshots -= 1;
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
        game.canvas.drawRect(x + halfWidth - 68, y - 8, x + halfWidth - 72 + (health / maxHealth) * 140, y + 3, paintFill);
    }
}
