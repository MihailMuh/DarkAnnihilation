package ru.warfare.darkannihilation;

import static ru.warfare.darkannihilation.Constants.SATURN_SHOOT_TIME;
import static ru.warfare.darkannihilation.Constants.SATURN_SHOTGUN_TIME;

public class Saturn extends BaseCharacter {
    public Saturn(Game g) {
        super(g, ImageHub.saturnImg.getWidth(), ImageHub.saturnImg.getHeight());

        recreateRect(x + 25, y + 25, right() - 25, bottom() - 17);

        lastShoot = System.currentTimeMillis();
    }

    @Override
    public void shoot() {
        now = System.currentTimeMillis();
        if (gun.equals("shotgun")) {
            if (now - lastShoot > SATURN_SHOTGUN_TIME) {
                lastShoot = now;
                BuckshotSaturn buckshotSaturn = new BuckshotSaturn(game, centerX(), y);
                Game.bullets.add(buckshotSaturn);
                Game.allSprites.add(buckshotSaturn);
            }
        } else {
            if (now - lastShoot > SATURN_SHOOT_TIME) {
                if (HardThread.job == 0) {
                    lastShoot = now;
                    HardThread.job = 4;
                }
            }
        }
    }

    @Override
    public Sprite getRect() {
        return goTO(x + 25, y + 25);
    }

    @Override
    public void checkIntersections(Sprite sprite) {
        if (getRect().intersect(sprite.getRect())) {
            damage(sprite.damage);
            sprite.intersectionPlayer();
        }
    }

    @Override
    public void update() {
        if (!lock) {
            shoot();
        }

        x += speedX;
        y += speedY;

        speedX = (endX - x) / 20;
        speedY = (endY - y) / 20;
    }

    @Override
    public void render () {
        renderHearts();
        Game.canvas.drawBitmap(ImageHub.saturnImg, x, y, null);
    }
}
