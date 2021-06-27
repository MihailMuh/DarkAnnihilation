package ru.warfare.darkannihilation;

import static ru.warfare.darkannihilation.Constants.SUNRISE_SHOOT_TIME;

public class Sunrise extends Sprite {
    private long lastShoot = System.currentTimeMillis();
    private boolean field;
    private boolean left = false;

    public Sunrise() {
        super(ImageHub.sunriseImg.getWidth(), ImageHub.sunriseImg.getHeight());
        damage = 20;
        hide();

        recreateRect(x + 15, y + 15, right() - 15, bottom() - 15);
    }

    public void shoot() {
        long now = System.currentTimeMillis();
        if (now - lastShoot > SUNRISE_SHOOT_TIME) {
            if (HardThread.job == 0) {
                lastShoot = now;
                HardThread.x = centerX();
                HardThread.y = centerY();
                HardThread.job = 2;
            }
        }
    }

    public void hide() {
        field = false;
        lock = true;
        health = 45;
        x = randInt(width, screenWidthWidth);
        y = -height;
        speedX = randInt(2, 4);
        speedY = randInt(2, 4);

        if (randInt(0, 1) == 1) {
            left = true;
        }

        if (buff) {
            up();
        }
    }

    private void up() {
        speedX *= 3;
        speedY *= 3;
    }

    @Override
    public void buff() {
        buff = true;
        if (!lock) {
            up();
        }
    }

    @Override
    public void stopBuff() {
        speedX /= 3;
        speedY /= 3;
    }

    @Override
    public Sprite getRect() {
        return goTO(x + 15, y + 15);
    }

    @Override
    public void intersection() {
        intersectionPlayer();
        Game.score += 100;
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
        if (y > 0 & !field) {
            field = true;
        }
        if (y > -halfHeight) {
            shoot();
        }
        if (x <= 0) {
            left = true;
        }
        if (x >= screenWidthWidth) {
            left = false;
        }
        if ((y >= screenHeightHeight) | (field & y <= 0)) {
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