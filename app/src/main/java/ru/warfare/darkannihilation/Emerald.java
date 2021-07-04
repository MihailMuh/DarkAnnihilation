package ru.warfare.darkannihilation;

import static ru.warfare.darkannihilation.Constants.EMERALD_HEALTH;
import static ru.warfare.darkannihilation.Constants.EMERALD_SHOOT_TIME;
import static ru.warfare.darkannihilation.Constants.EMERALD_SHOTGUN_TIME;

public class Emerald extends BaseCharacter {
    public Emerald(Game g) {
        super(g, ImageHub.emeraldImg.getWidth(), ImageHub.emeraldImg.getHeight(), EMERALD_HEALTH);
        recreateRect(x + 25, y + 25, right() - 25, bottom() - 25);

        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 5; i++) {
                hearts.add(new Heart(g, heartX, heartY, false));
                heartX += 90;
            }
            newLevel();
        }
    }

    public void shoot() {
        now = System.currentTimeMillis();
        if (gun.equals("shotgun")) {
            if (now - lastShoot > EMERALD_SHOTGUN_TIME) {
                lastShoot = now;
                AudioHub.playThunderStorm();
                BulletThunder bulletThunder = new BulletThunder(centerX(), y);
                Game.bullets.add(bulletThunder);
                Game.allSprites.add(bulletThunder);
            }
        } else {
            if (now - lastShoot > EMERALD_SHOOT_TIME) {
                if (HardThread.job == 0) {
                    HardThread.job = 9;
                    lastShoot = now;
                }
            }
        }
    }

    @Override
    public void heal() {
        health += 20;
        int armor = health - maxHealth;
        if (armor > 0) {
            addArmorHeart();
        }
        if (armor > 10) {
            addArmorHeart();
        }
    }

    @Override
    public Sprite getRect() {
        return newRect(x + 25, y + 25);
    }

    @Override
    public void update() {
        if (!lock) {
            shoot();
        }

        x += speedX;
        y += speedY;

        speedX = (endX - x) / 10;
        speedY = (endY - y) / 10;
    }

    @Override
    public void render () {
        Game.canvas.drawBitmap(ImageHub.emeraldImg, x, y, null);
        renderHearts();
    }
}
