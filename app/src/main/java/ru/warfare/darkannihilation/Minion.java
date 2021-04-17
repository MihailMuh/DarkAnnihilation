package ru.warfare.darkannihilation;

import android.graphics.Rect;

public class Minion extends Sprite {
    private static final int shootMinionTime = 900;
    private long lastShoot;
    private long now;

    public Minion(Game g, int FX) {
        super(g, ImageHub.minionImg.getWidth(), ImageHub.minionImg.getHeight());
        health = 2;
        damage = 5;

        x = randInt(FX, FX + ImageHub.factoryImg.getWidth());
        y = ImageHub.factoryImg.getHeight() - 100;
        speedX = randInt(-8, 8);
        speedY = randInt(2, 5);

        rect = new Rect(x + 15, y + 15, x + width - 15, y + height - 15);

        lastShoot = System.currentTimeMillis();
    }

    public void shoot() {
        now = System.currentTimeMillis();
        if (now - lastShoot > shootMinionTime) {
            lastShoot = now;
            HardWorker.x = x;
            HardWorker.y = y;
            HardWorker.halfHeight = halfHeight;
            HardWorker.halfWidth = halfWidth;
            HardWorker.makeAngle = 1;
        }
    }

    @Override
    public void intersection() {
        AudioPlayer.playBoom();
        game.allSprites.remove(this);
        for (int i = 0; i < numberMediumExplosionsTriple; i++) {
            if (game.allExplosions[i].lock) {
                game.allExplosions[i].start(x + halfWidth, y + halfHeight);
                break;
            }
        }
    }

    @Override
    public void intersectionPlayer() {
        AudioPlayer.playMetal();
        game.allSprites.remove(this);
        for (int i = numberMediumExplosionsTriple; i < numberSmallExplosionsTriple; i++) {
            if (game.allExplosions[i].lock) {
                game.allExplosions[i].start(x + halfWidth, y + halfHeight);
                break;
            }
        }
    }

    @Override
    public Rect getRect() {
        rect.offsetTo(x + 15, y + 15);
        return rect;
    }

    @Override
    public void check_intersectionBullet(BulletBase bullet) {
        if (Rect.intersects(getRect(), bullet.getRect())) {
            bullet.intersection();
            intersection();
        }
    }

    @Override
    public void update() {
        x += speedX;
        y += speedY;

        shoot();

        if (x < -width | x > game.screenWidth | y > game.screenHeight) {
            game.allSprites.remove(this);
        }
    }

    @Override
    public void render() {
        game.canvas.drawBitmap(ImageHub.minionImg, x, y, null);
    }
}