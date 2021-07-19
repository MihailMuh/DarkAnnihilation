package ru.warfare.darkannihilation.character;

import ru.warfare.darkannihilation.base.BaseCharacter;
import ru.warfare.darkannihilation.base.Sprite;
import ru.warfare.darkannihilation.bullet.BulletDynamite;
import ru.warfare.darkannihilation.bullet.BulletThunder;
import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.math.Math;
import ru.warfare.darkannihilation.systemd.Game;

import static ru.warfare.darkannihilation.Constants.EMERALD_HEALTH;
import static ru.warfare.darkannihilation.Constants.EMERALD_SHOOT_TIME;
import static ru.warfare.darkannihilation.Constants.EMERALD_SHOTGUN_TIME;

public class Emerald extends BaseCharacter {
    public Emerald(Game g) {
        super(g, ImageHub.emeraldImg, EMERALD_HEALTH);
        recreateRect(x + 25, y + 25, right() - 25, bottom() - 25);

        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 5; i++) {
                hearts.add(new Heart(g, heartX, heartY, false));
                heartX += 90;
            }
            newLevel();
        }

        changeHearts();
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
                AudioHub.playDynamite();
                BulletDynamite bulletDynamite = new BulletDynamite(centerX(), y);
                Game.bullets.add(bulletDynamite);
                Game.allSprites.add(bulletDynamite);
                lastShoot = now;
            }
        }
    }

    @Override
    public void baseHeal() {
        if (Math.randInt(0, 1) == 0) {
            health += 30;
        } else {
            health += 35;
        }
        int armor = health - maxHealth;
        if (armor > 0) {
            addArmorHeart();
            if (armor > 10) {
                addArmorHeart();
            }
            if (armor > 20) {
                addArmorHeart();
            }
            if (armor > 30) {
                addArmorHeart();
            }
            maxHealth = health;
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
}
