package ru.warfare.darkannihilation;

public class Sunrise extends Sprite {
    private long lastShoot;
    private boolean field;
    private boolean left = false;

    public Sunrise() {
        super(ImageHub.sunriseImg.getWidth(), ImageHub.sunriseImg.getHeight());
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
        health = 55;
        x = randInt(width, Game.screenWidth - width);
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
        createSkullExplosion();
        Game.score += 100;
        hide();
    }

    @Override
    public void intersectionPlayer() {
        AudioPlayer.playMegaBoom();
        createSkullExplosion();
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
        if (y > 0 & !field) {
            field = true;
        }
        if (y > -halfHeight) {
            shoot();
        }
        if (x <= 0) {
            left = true;
        }
        if (x + width >= Game.screenWidth) {
            left = false;
        }
        if ((y + height >= Game.screenHeight) | (field & y <= 0)) {
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
        Game.canvas.drawBitmap(ImageHub.sunriseImg, x, y, Game.alphaPaint);
    }
}