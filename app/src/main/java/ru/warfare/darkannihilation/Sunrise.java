package ru.warfare.darkannihilation;

public class Sunrise extends Sprite {
    private long lastShoot;
    public boolean field;
    private boolean left = false;

    public Sunrise(Game g) {
        super(g, ImageHub.sunriseImg.getWidth(), ImageHub.sunriseImg.getHeight());
        damage = 20;
        hide();

        recreateRect(x + 15, y + 15, x + width - 15, y + height - 15);

        lastShoot = System.currentTimeMillis();
    }

    public void shoot() {
        long now = System.currentTimeMillis();
        if (now - lastShoot > 500) {
            lastShoot = now;

            if (HardWorker.job == 0) {
                HardWorker.x = x + halfWidth;
                HardWorker.y = y + halfHeight;
                HardWorker.job = 3;
            }
        }
    }

    public void hide() {
        field = false;
        lock = true;
        health = 45;
        x = randInt(width, screenWidth - width);
        y = -height;
        speedX = randInt(2, 4);
        speedY = randInt(2, 4);

        if (randInt(0, 1) == 1) {
            left = true;
        }
    }

    @Override
    public Sprite getRect() {
        return goTO(x + 15, y + 15);
    }

    @Override
    public void intersection() {
        AudioPlayer.playMegaBoom();
        for (int i = numberSmallExplosionsDefault; i < numberLargeExplosions; i++) {
            if (game.allExplosions[i].lock) {
                game.allExplosions[i].start(x + halfWidth, y + halfHeight);
                break;
            }
        }
        game.score += 100;
        hide();
    }

    @Override
    public void intersectionPlayer() {
        AudioPlayer.playMegaBoom();
        for (int i = numberSmallExplosionsDefault; i < numberLargeExplosions; i++) {
            if (game.allExplosions[i].lock) {
                game.allExplosions[i].start(x + halfWidth, y + halfHeight);
                break;
            }
        }
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
        if (y > 0) {
            shoot();
            if (!field) {
                field = true;
            }
        }
        if (x <= 0) {
            left = true;
        }
        if (x + width >= screenWidth) {
            left = false;
        }
        if ((y + height >= screenHeight) | (field & y <= 0)) {
            speedY = -speedY;
        }

        if (left) {
            x += speedX;
        } else {
            x -= speedX;
        }
        y += speedY;
    }

    @Override
    public void render() {
        game.canvas.drawBitmap(ImageHub.sunriseImg, x, y, Game.alphaPaint);
    }
}