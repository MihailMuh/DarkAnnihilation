package ru.warfare.darkannihilation;

public class TripleFighter extends Sprite {
    private static final int shootTripleTime = 1_500;
    private long lastShoot;
    private long now;
    public int X;
    public int Y;
    private double angle;

    public TripleFighter(Game g) {
        super(g, ImageHub.tripleFighterImg.getWidth(), ImageHub.tripleFighterImg.getHeight());
        lock = true;
        health = 6;

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
            HardWorker.typeWork = 1;
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
    public void check_intersectionBullet(Bullet bullet) {
        if (x < bullet.x & bullet.x < x + width & y < bullet.y & bullet.y < y + height |
                bullet.x < x & x < bullet.x + bullet.width & bullet.y < y & y < bullet.y + bullet.height) {
            health -= bullet.damage;
            for (int i = numberDefaultExplosions; i < numberSmallExplosions; i++) {
                if (game.explosions[i].lock) {
                    game.explosions[i].start(bullet.x + bullet.halfWidth, bullet.y + bullet.halfHeight);
                    break;
                }
            }
            game.bullets.remove(bullet);
            game.numberBullets -= 1;
            if (health <= 0) {
                for (int i = 0; i < numberDefaultExplosions; i++) {
                    if (game.explosions[i].lock) {
                        game.explosions[i].start(x + halfWidth, y + halfHeight);
                        break;
                    }
                }
                AudioPlayer.playBoom();
                game.score += 5;
                newStatus();
            }
        }
    }

    public void check_intersectionBullet(Buckshot bullet) {
        if (x < bullet.x & bullet.x < x + width & y < bullet.y & bullet.y < y + height |
                bullet.x < x & x < bullet.x + bullet.width & bullet.y < y & y < bullet.y + bullet.height) {
            health -= bullet.damage;
            for (int i = numberDefaultExplosions; i < numberSmallExplosions; i++) {
                if (game.explosions[i].lock) {
                    game.explosions[i].start(bullet.x + bullet.halfWidth, bullet.y + bullet.halfHeight);
                    break;
                }
            }
            game.buckshots.remove(bullet);
            game.numberBuckshots -= 1;
            if (health <= 0) {
                for (int i = 0; i < numberDefaultExplosions; i++) {
                    if (game.explosions[i].lock) {
                        game.explosions[i].start(x + halfWidth, y + halfHeight);
                        break;
                    }
                }
                AudioPlayer.playBoom();
                game.score += 5;
                newStatus();
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
