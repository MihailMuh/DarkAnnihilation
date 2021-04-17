package ru.warfare.darkannihilation;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Boss extends Sprite {
    public float maxHealth;
    private static final Paint paintFill = new Paint();
    private static final Paint paintOutLine = new Paint();

    private static final int shootBossTime = 350;
    private long lastShoot;
    private long now;

    public Boss(Game g) {
        super(g, ImageHub.bossImage.getWidth(), ImageHub.bossImage.getHeight());

        paintFill.setColor(Color.RED);
        paintOutLine.setColor(Color.WHITE);

        health = 120;
        maxHealth = health;
        speedY = 1;
        isPassive = true;

        x = halfScreenWidth - halfWidth;
        y = -600;

        rect = new Rect(x + 20, y + 20, x + width - 20, y + height - 20);

        lastShoot = System.currentTimeMillis();
    }

    public void shoot() {
        now = System.currentTimeMillis();
        if (now - lastShoot > shootBossTime) {
            lastShoot = now;
            game.allSprites.add(new BulletBoss(game, x + width - 65, y + 20, 1));
            game.allSprites.add(new BulletBoss(game, x + width - 65, y + 20, 2));
            game.allSprites.add(new BulletBoss(game, x + width - 65, y + 20, 3));
            AudioPlayer.playShoot();
        }
    }

    public void killAfterFight() {
        for (int i = numberSmallExplosionsDefault; i < numberLargeExplosions; i++) {
            if (game.allExplosions[i].lock) {
                game.allExplosions[i].start(x + halfWidth, y + halfHeight);
                break;
            }
        }
        AudioPlayer.playMegaBoom();
        game.score += 150;
        game.bosses.remove(this);
        game.allSprites.remove(this);
        game.numberBosses -= 1;
        for (int i = 0; i < game.numberVaders; i++) {
            if (Game.random.nextFloat() <= 0.1) {
                game.allSprites.add(new TripleFighter(game));
            }
        }
        AudioPlayer.bossMusic.pause();
        if (game.portal.lock) {
            game.portal.start();
        }
        for (int i = 0; i < game.allSprites.size(); i++) {
            game.allSprites.get(i).empireStart();
        }
        Game.lastBoss += game.pauseTimer;
        game.pauseTimer = 0;
    }

    @Override
    public Rect getRect() {
        rect.offsetTo(x + 20, y + 20);
        return rect;
    }

    @Override
    public void check_intersectionBullet(BulletBase bullet) {
        if (Rect.intersects(getRect(), bullet.getRect())) {
            health -= bullet.damage;
            bullet.intersection();
            if (health <= 0) {
                killAfterFight();
            }
        }
    }

    @Override
    public void update() {
        game.pauseTimer += 20;
        x += speedX;
        y += speedY;

        if (y == -400) {
            AudioPlayer.bossMusic.seekTo(0);
            AudioPlayer.bossMusic.start();
            AudioPlayer.pirateMusic.pause();
            game.gameStatus = 5;
        }
        if (y >= 50) {
            speedY = 0;
            speedX = -8;
            shoot();
        }
        if (x < -width) {
            x = game.screenWidth;
        }
    }

    @Override
    public void render() {
        game.canvas.drawBitmap(ImageHub.bossImage, x, y, null);

        game.canvas.drawRect(x + halfWidth - 70, y - 10, x + halfWidth + 70, y + 5, paintOutLine);
        game.canvas.drawRect(x + halfWidth - 68, y - 8, x + halfWidth - 72 + (health / maxHealth) * 140, y + 3, paintFill);
    }
}
