package ru.warfare.darkannihilation;

public class Demoman extends Sprite {
    private static final int shootTime = 150;
    private long lastShoot;
    private long now;

    public int direction = 0;

    public Demoman(Game g) {
        super(g, ImageHub.demomanImg.getWidth(), ImageHub.demomanImg.getHeight());

        hide();
        lastShoot = System.currentTimeMillis();
    }

    public void hide() {
        lock = true;
        health = 20;
        y = randInt(0, halfScreenHeight - health);
        direction = randInt(0, 1);
        if (direction == 0) {
            x = -width;
            speedX = randInt(5, 10);
        } else {
            x = screenWidth;
            speedX = randInt(-10, -5);
        }
    }


    public void shoot() {
        now = System.currentTimeMillis();
        if (now - lastShoot > shootTime) {
            lastShoot = now;
            HardWorker.makeBomb = 1;
        }
    }

    @Override
    public void check_intersectionBullet(Bullet bullet) {
        if (x + 30 < bullet.x & bullet.x < x + width - 15 & y < bullet.y & bullet.y < y + height - 50 |
                bullet.x < x + 30 & x + 30 < bullet.x + bullet.width & bullet.y < y & y < bullet.y + bullet.height) {
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
                for (int i = numberSmallExplosions; i < numberLargeExplosions; i++) {
                    if (game.explosions[i].lock) {
                        game.explosions[i].start(x + halfWidth, y + halfHeight);
                        break;
                    }
                }
                AudioPlayer.playMegaBoom();
                game.score += 35;
                hide();
            }
        }
    }

    public void check_intersectionBullet(Buckshot bullet) {
        if (x + 30 < bullet.x & bullet.x < x + width - 15 & y < bullet.y & bullet.y < y + height - 50 |
                bullet.x < x + 30 & x + 30 < bullet.x + bullet.width & bullet.y < y & y < bullet.y + bullet.height) {
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
                for (int i = numberSmallExplosions; i < numberLargeExplosions; i++) {
                    if (game.explosions[i].lock) {
                        game.explosions[i].start(x + halfWidth, y + halfHeight);
                        break;
                    }
                }
                AudioPlayer.playMegaBoom();
                game.score += 35;
                hide();
            }
        }
    }

    @Override
    public void update() {
        shoot();

        x += speedX;

        if (direction == 0) {
            if (x > game.screenWidth) {
                hide();
            }
        } else {
            if (x < -width) {
                hide();
            }
        }
    }

    @Override
    public void render() {
        game.canvas.drawBitmap(ImageHub.demomanImg, x, y, null);
    }
}
