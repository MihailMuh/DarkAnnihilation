package ru.warfare.darkannihilation.character;

import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.HardThread;
import ru.warfare.darkannihilation.hub.ImageHub;
import ru.warfare.darkannihilation.base.BaseCharacter;
import ru.warfare.darkannihilation.base.Sprite;
import ru.warfare.darkannihilation.bullet.BuckshotSaturn;

import static ru.warfare.darkannihilation.Constants.SATURN_HEALTH;
import static ru.warfare.darkannihilation.Constants.SATURN_SHOOT_TIME;
import static ru.warfare.darkannihilation.Constants.SATURN_SHOTGUN_TIME;

public class Saturn extends BaseCharacter {
    public Saturn(Game g) {
        super(g, ImageHub.saturnImg, SATURN_HEALTH);
        recreateRect(x + 25, y + 25, right() - 25, bottom() - 17);

        hearts.add(new Heart(g, heartX, heartY, false));
        heartX += 90;

        changeHearts();
    }

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
    public void baseHeal() {
        health += 10;
        if (health - maxHealth > 0) {
            addArmorHeart();
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

        speedX = (endX - x) / 20;
        speedY = (endY - y) / 20;
    }
}
