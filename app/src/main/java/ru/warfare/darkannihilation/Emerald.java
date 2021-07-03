package ru.warfare.darkannihilation;

import java.util.ArrayList;

import static ru.warfare.darkannihilation.Constants.EMERALD_HEALTH;
import static ru.warfare.darkannihilation.Constants.EMERALD_SHOOT_TIME;
import static ru.warfare.darkannihilation.Constants.EMERALD_SHOTGUN_TIME;

public class Emerald extends BaseCharacter {
    public Emerald(Game g) {
        super(g, ImageHub.emeraldImg.getWidth(), ImageHub.emeraldImg.getHeight(), EMERALD_HEALTH);
        recreateRect(x + 25, y + 25, right() - 25, bottom() - 25);

        heartX = 45;
        heartY = 10;
        hearts = new ArrayList<>(0);
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 4; i++) {
                hearts.add(new Heart(g, heartX, heartY, false));
                heartX += 90;
            }
            newLevel();
        }

        lastShoot = System.currentTimeMillis();
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
    public void newLevel() {
        heartX = 45;
        heartY += 10 + ImageHub.imageHeartHalf.getHeight();
    }

    @Override
    public void killArmorHeart(Heart heart) {
        hearts.remove(heart);
        if (heartX != 45) {
            heartX -= 90;
        } else {
            heartX = 315;
            heartY -= 10 + ImageHub.imageHeartHalf.getHeight();
        }
        maxHealth = health;
    }

    @Override
    public void addArmorHeart() {
        hearts.add(new Heart(game, heartX, heartY, true));
        heartX += 90;
        if (heartX > 315) {
            newLevel();
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
