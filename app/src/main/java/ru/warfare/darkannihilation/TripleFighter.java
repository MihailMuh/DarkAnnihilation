package ru.warfare.darkannihilation;

public class TripleFighter extends Sprite {
    private static final int shootTripleTime = 1_500;
    private long lastShoot;
    private long now;

    public TripleFighter(Game g) {
        super(g, ImageHub.tripleFighterImg.getWidth(), ImageHub.tripleFighterImg.getHeight());
        lock = true;
        health = 6;
        damage = 10;

        x = randInt(0, screenWidth);
        y = -150;
        speedX = randInt(-3, 3);
        speedY = randInt(1, 10);
        recreateRect(x + 5, y + 5, x + width - 5, y + height - 5);

        lastShoot = System.currentTimeMillis();
    }

    public void shoot() {
        now = System.currentTimeMillis();
        if (now - lastShoot > shootTripleTime) {
            lastShoot = now;

            if (HardWorker.makeAngle == 0) {
                HardWorker.x = x + halfWidth;
                HardWorker.y = y + halfHeight;
                HardWorker.makeAngle = 1;
            }
        }
    }

    public void newStatus() {
        if (game.numberBosses != 0) {
            lock = true;
        }
        health = 6;
        x = randInt(0, screenWidth);
        y = -150;
        speedX = randInt(-3, 3);
        speedY = randInt(1, 10);
        lastShoot = System.currentTimeMillis();
    }

    @Override
    public Sprite getRect() {
        return goTO(x + 5, y + 5);
    }

    @Override
    public void intersection() {
        for (int i = 0; i < numberMediumExplosionsTriple; i++) {
            if (game.allExplosions[i].lock) {
                game.allExplosions[i].start(x + halfWidth, y + halfHeight);
                break;
            }
        }
        AudioPlayer.playBoom();
        game.score += 5;
        newStatus();
    }

    @Override
    public void intersectionPlayer() {
        AudioPlayer.playMetal();
        for (int i = numberMediumExplosionsDefault; i < numberSmallExplosionsDefault; i++) {
            if (game.allExplosions[i].lock) {
                game.allExplosions[i].start(x + halfWidth, y + halfHeight);
                break;
            }
        }
        newStatus();
    }

    @Override
    public void check_intersectionBullet(BaseBullet bullet) {
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
        if (x > 0 & x < screenWidth - width & y > 0 & y < screenHeight - height) {
            shoot();
        }

        x += speedX;
        y += speedY;

        if (x < -width | x > screenWidth | y > screenHeight) {
            newStatus();
        }
    }

    @Override
    public void render() {
        game.canvas.drawBitmap(ImageHub.tripleFighterImg, x, y, null);
    }
}
