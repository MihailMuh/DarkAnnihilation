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

        recreateRect(x + 5, y + 5, right() - 5, bottom() - 5);

        lastShoot = System.currentTimeMillis();
    }

    public void shoot() {
        long now = System.currentTimeMillis();
        if (now - lastShoot > shootTripleTime) {
            if (HardThread.job == 0) {
                lastShoot = now;
                HardThread.x = centerX();
                HardThread.y = centerY();
                HardThread.job = 1;
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
        AudioHub.playBoom();
        Game.score += 5;
        newStatus();
    }

    @Override
    public void intersectionPlayer() {
        AudioHub.playMetal();
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
        if (x > 0 & x < screenWidthWidth & y > 0 & y < screenHeightHeight) {
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
