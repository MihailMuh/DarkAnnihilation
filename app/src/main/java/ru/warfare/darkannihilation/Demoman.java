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
    public void check_intersectionBullet(BulletBase bullet) {
        if (x + 30 < bullet.x & bullet.x < x + width - 15 & y < bullet.y & bullet.y < y + height - 50 |
                bullet.x < x + 30 & x + 30 < bullet.x + bullet.width & bullet.y < y & y < bullet.y + bullet.height) {
            health -= bullet.damage;
            bullet.intersection();
            if (health <= 0) {
                for (int i = numberSmallExplosionsDefault; i < numberLargeExplosions; i++) {
                    if (game.allExplosions[i].lock) {
                        game.allExplosions[i].start(x + halfWidth, y + halfHeight);
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
