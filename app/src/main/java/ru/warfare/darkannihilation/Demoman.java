package ru.warfare.darkannihilation;

public class Demoman extends Sprite {
    private static final int shootTime = 150;
    private long lastShoot;

    private int direction = 0;

    public Demoman(Game g) {
        super(g, ImageHub.demomanImg.getWidth(), ImageHub.demomanImg.getHeight());

        damage = 40;

        hide();

        recreateRect(x + 30, y + 25, x + width - 20, y + height - 50);

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
        long now = System.currentTimeMillis();
        if (now - lastShoot > shootTime) {
            lastShoot = now;
            if (HardWorker.job == 0) {
                HardWorker.job = 4;
            }
        }
    }

    @Override
    public Sprite getRect() {
        return goTO(x + 30, y + 25);
    }

    @Override
    public void intersection() {
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

    @Override
    public void intersectionPlayer() {
        AudioPlayer.playMetal();
        for (int i = numberSmallExplosionsDefault; i < numberLargeExplosions; i++) {
            if (game.allExplosions[i].lock) {
                game.allExplosions[i].start(x + halfWidth, y + halfHeight);
                break;
            }
        }
        AudioPlayer.playMegaBoom();
        hide();
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
