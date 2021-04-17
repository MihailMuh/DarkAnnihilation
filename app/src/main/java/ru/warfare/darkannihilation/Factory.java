package ru.warfare.darkannihilation;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

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
        isPassive = true;

        x = halfScreenWidth - halfWidth;
        y = -height;

        rect = new Rect(x + 20, y + 80, x + width - 20, y + height - 20);

        lastSpawn = System.currentTimeMillis();
    }

    public void hide() {
        lock = true;
        y = -height;
        x = halfScreenWidth - halfWidth;
        health = (int) maxHealth;
    }

    public void spawn() {
        now = System.currentTimeMillis();
        if (now - lastSpawn > spawnTime) {
            lastSpawn = now;
            game.allSprites.add(new Minion(game, x));
            game.allSprites.add(new Minion(game, x));
        }
    }

    @Override
    public Rect getRect() {
        rect.offsetTo(x + 20, y + 80);
        return rect;
    }

    @Override
    public void intersection() {
        AudioPlayer.playMegaBoom();
        game.score += 75;
        for (int i = numberSmallExplosionsDefault; i < numberLargeExplosions; i++) {
            if (game.allExplosions[i].lock) {
                game.allExplosions[i].start(x + halfWidth, y + halfHeight);
                break;
            }
        }
        hide();
    }

    @Override
    public void check_intersectionBullet(BulletBase bullet) {
        if (Rect.intersects(getRect(), bullet.getRect())) {
            health -= bullet.damage;
            bullet.intersection();
            if (health <= 0) {
                intersection();
            }
        }
    }

    @Override
    public void update() {
        if (y < 0) {
            y += speedY;
        } else {
            spawn();
        }
    }

    @Override
    public void render() {
        game.canvas.drawBitmap(ImageHub.factoryImg, x, y, null);

        game.canvas.drawRect(x + halfWidth - 250, y + 60, x + halfWidth + 250, y + 75 , paintOutLine);
        game.canvas.drawRect(x + halfWidth - 248, y + 62, x + halfWidth - 252 + (health / maxHealth) * 500, y + 73, paintFill);
    }
}
