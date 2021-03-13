package ru.warfare.darkannihilation;

import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;

public class Factory extends Sprite {
    private static final int spawnTime = 1_000;
    private long lastSpawn;
    private long now;

    private static final Paint paintFill = new Paint();
    private static final Paint paintOutLine = new Paint();
    public float maxHealth;

    public Factory(Game g) {
        super(g, ImageHub.factoryImg.getWidth(), ImageHub.factoryImg.getHeight());

        paintFill.setColor(Color.RED);
        paintOutLine.setColor(Color.WHITE);

        health = 200;
        speedY = 3;
        lock = true;
        maxHealth = health;

        x = halfScreenWidth - halfWidth;
        y = -height;

        lastSpawn = System.currentTimeMillis();
    }

    public void hide() {
        lock = true;
        y = -height;
        speedY = 3;
        health = (int) maxHealth;
        game.minions = new ArrayList<>(0);
        game.numberMinions = 0;
    }

    public void shoot() {
        now = System.currentTimeMillis();
        if (now - lastSpawn > spawnTime) {
            lastSpawn = now;
            game.minions.add(new Minion(game, x));
            game.minions.add(new Minion(game, x));
            game.numberMinions += 2;
        }
    }

    @Override
    public void check_intersectionBullet(Bullet bullet) {
        if (x + 20 < bullet.x & bullet.x < x + width - 20 & y + 80 < bullet.y & bullet.y < y + height - 20 |
                bullet.x < x + 20 & x + 20 < bullet.x + bullet.width & bullet.y < y + 80 & y + 80 < bullet.y + bullet.height) {
            health -= bullet.damage;
            for (int i = numberDefaultExplosions; i < numberSmallExplosions; i++) {
                if (game.explosions[i].lock) {
                    game.explosions[i].start(bullet.x + bullet.halfWidth, bullet.y + bullet.halfHeight);
                    break;
                }
            }
            game.bullets.remove(bullet);
            game.numberBullets -= 1;
        }
    }

    public void check_intersectionBullet(Buckshot bullet) {
        if (x + 20 < bullet.x & bullet.x < x + width - 20 & y + 80 < bullet.y & bullet.y < y + height - 20 |
                bullet.x < x + 20 & x + 20 < bullet.x + bullet.width & bullet.y < y + 80 & y + 80 < bullet.y + bullet.height) {
            health -= bullet.damage;
            for (int i = numberDefaultExplosions; i < numberSmallExplosions; i++) {
                if (game.explosions[i].lock) {
                    game.explosions[i].start(bullet.x + bullet.halfWidth, bullet.y + bullet.halfHeight);
                    break;
                }
            }
            game.buckshots.remove(bullet);
            game.numberBuckshots -= 1;
        }
    }

    @Override
    public void update() {
        y += speedY;

        if (y >= 0) {
            speedY = 0;
            shoot();
        }

        if (health <= 0) {
            AudioPlayer.playMegaBoom();
            game.score += 75;
            for (int i = numberSmallExplosions; i < numberLargeExplosions; i++) {
                if (game.explosions[i].lock) {
                    game.explosions[i].start(x + halfWidth, y + halfHeight);
                    break;
                }
            }
            hide();
        }
    }

    @Override
    public void render() {
        game.canvas.drawBitmap(ImageHub.factoryImg, x, y, null);

        game.canvas.drawRect(x + halfWidth - 250, y + 60, x + halfWidth + 250, y + 75 , paintOutLine);
        game.canvas.drawRect(x + halfWidth - 248, y + 62, x + halfWidth - 252 + (health / maxHealth) * 500, y + 73, paintFill);
    }
}
