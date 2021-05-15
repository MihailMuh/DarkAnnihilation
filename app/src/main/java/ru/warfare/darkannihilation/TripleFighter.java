package ru.warfare.darkannihilation;

public class TripleFighter extends Sprite {
    private static final int shootTripleTime = 1_500;
    private long lastShoot;

    public TripleFighter() {
        super(ImageHub.tripleFighterImg.getWidth(), ImageHub.tripleFighterImg.getHeight());
        health = 6;
        damage = 10;

        x = randInt(0, Game.screenWidth);
        y = -150;
        speedX = randInt(-3, 3);
        speedY = randInt(1, 10);
        recreateRect(x + 5, y + 5, x + width - 5, y + height - 5);

        lastShoot = System.currentTimeMillis();
    }

    public void shoot() {
        long now = System.currentTimeMillis();
        if (now - lastShoot > shootTripleTime) {
            lastShoot = now;

            if (HardWorker.job == 0) {
                HardWorker.x = x + halfWidth;
                HardWorker.y = y + halfHeight;
                HardWorker.job = 1;
            }
        }
    }

    public void newStatus() {
        if (Game.bosses.size() != 0) {
            lock = true;
        }
        health = 6;
        x = randInt(0, Game.screenWidth);
        y = -150;
        speedX = randInt(-3, 3);
        speedY = randInt(1, 10);
    }

    @Override
    public Sprite getRect() {
        return goTO(x + 5, y + 5);
    }

    @Override
    public void intersection() {
        createLargeTripleExplosion();
        AudioPlayer.playBoom();
        Game.score += 5;
        newStatus();
    }

    @Override
    public void intersectionPlayer() {
        AudioPlayer.playMetal();
        createSmallExplosion();
        newStatus();
    }

    @Override
    public void check_intersectionBullet(Sprite bullet) {
        if (getRect().intersect(bullet.getRect())) {
            health -= bullet.damage;
            bullet.intersection();
            if (health <= 0) {
                intersection();
            }
        }
    }

    @Override
    public void empireStart() {
        lock = false;
    }

    @Override
    public void update() {
        if (x > 0 & x < Game.screenWidth - width & y > 0 & y < Game.screenHeight - height) {
            shoot();
        }

        x += speedX;
        y += speedY;

        if (x < -width | x > Game.screenWidth | y > Game.screenHeight) {
            newStatus();
        }
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(ImageHub.tripleFighterImg, x, y, null);
    }
}
