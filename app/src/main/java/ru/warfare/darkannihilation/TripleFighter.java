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

        lastShoot = System.currentTimeMillis();
    }

    public void shoot() {
        now = System.currentTimeMillis();
        if (now - lastShoot > shootTripleTime) {
            lastShoot = now;

            HardWorker.x = x;
            HardWorker.y = y;
            HardWorker.halfHeight = halfHeight;
            HardWorker.halfWidth = halfWidth;
            HardWorker.makeAngle = 1;
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
    public void check_intersectionBullet(BulletBase bullet) {
        if (x < bullet.x & bullet.x < x + width & y < bullet.y & bullet.y < y + height |
                bullet.x < x & x < bullet.x + bullet.width & bullet.y < y & y < bullet.y + bullet.height) {
            health -= bullet.damage;
            bullet.intersection();
            if (health <= 0) {
                intersection();
            }
        }
    }

    @Override
    public void update() {
        if (!lock) {
            if (y > 0) {
                shoot();
            }
            x += speedX;
            y += speedY;

            if (x < -width | x > screenWidth | y > screenHeight) {
                newStatus();
            }
        } else {
            if (game.numberBosses == 0 & game.gameStatus != 2 & game.gameStatus != 4 & game.gameStatus != 6 & game.gameStatus != 7) {
                lock = false;
            }
        }
    }

    @Override
    public void render() {
        game.canvas.drawBitmap(ImageHub.tripleFighterImg, x, y, null);
    }
}
