package ru.warfare.darkannihilation;

import static ru.warfare.darkannihilation.Constants.DEMOMAN_SHOOT_TIME;

public class Demoman extends Sprite {
    private long lastShoot = System.currentTimeMillis();
    private int direction = 0;

    public Demoman() {
        super(ImageHub.demomanImg.getWidth(), ImageHub.demomanImg.getHeight());

        damage = 40;

        hide();

        recreateRect(x + 30, y + 25, right() - 20, bottom() - 50);

    }

    public void hide() {
        lock = true;
        health = 30;
        y = randInt(0, Game.halfScreenHeight - height);
        direction = randInt(0, 1);
        if (direction == 0) {
            x = -width;
            speedX = randInt(5, 10);
        } else {
            x = Game.screenWidth;
            speedX = randInt(-10, -5);
        }
    }

    public void shoot() {
        long now = System.currentTimeMillis();
        if (now - lastShoot > DEMOMAN_SHOOT_TIME) {
            if (HardThread.job == 0) {
                lastShoot = now;
                HardThread.job = 3;
            }
        }
    }

    @Override
    public Sprite getRect() {
        return goTO(x + 30, y + 25);
    }

    @Override
    public void intersection() {
        intersectionPlayer();
        Game.score += 35;
    }

    @Override
    public void intersectionPlayer() {
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
        shoot();

        x += speedX;

        if (direction == 0) {
            if (x > Game.screenWidth) {
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
        Game.canvas.drawBitmap(ImageHub.demomanImg, x, y, null);
    }
}
