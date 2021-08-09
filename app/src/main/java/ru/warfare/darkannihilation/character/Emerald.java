package ru.warfare.darkannihilation.character;

import ru.warfare.darkannihilation.base.BaseCharacter;
import ru.warfare.darkannihilation.base.Sprite;
import ru.warfare.darkannihilation.bullet.BulletDynamite;
import ru.warfare.darkannihilation.bullet.BulletThunder;
import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.math.Randomize;
import ru.warfare.darkannihilation.systemd.Game;

import static ru.warfare.darkannihilation.constant.Constants.EMERALD_HEALTH;
import static ru.warfare.darkannihilation.constant.Constants.EMERALD_SHOOT_TIME;
import static ru.warfare.darkannihilation.constant.Constants.EMERALD_SHOTGUN_TIME;
import static ru.warfare.darkannihilation.constant.NamesConst.SHOTGUN;

public class Emerald extends BaseCharacter {
    public Emerald(Game g) {
        super(g, ImageHub.emeraldImg, EMERALD_HEALTH);
        recreateRect(x + 30, y + 25, right() - 30, bottom() - 25);
    }

    public void shoot() {
        now = System.currentTimeMillis();
        if (gun == SHOTGUN) {
            if (now - lastShoot > EMERALD_SHOTGUN_TIME) {
                lastShoot = now;
                AudioHub.playThunderStorm();
                game.bullets.add(new BulletThunder(game, centerX(), y));
            }
        } else {
            if (now - lastShoot > EMERALD_SHOOT_TIME) {
                AudioHub.playDynamite();
                game.bullets.add(new BulletDynamite(game, centerX(), y));
                lastShoot = now;
            }
        }
    }

    @Override
    public void baseHeal() {
        if (Randomize.randBoolean()) {
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
