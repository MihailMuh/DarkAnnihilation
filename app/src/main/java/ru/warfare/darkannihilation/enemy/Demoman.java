package ru.warfare.darkannihilation.enemy;

import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.HardThread;
import ru.warfare.darkannihilation.hub.ImageHub;
import ru.warfare.darkannihilation.base.Sprite;

import static ru.warfare.darkannihilation.Constants.DEMOMAN_DAMAGE;
import static ru.warfare.darkannihilation.Constants.DEMOMAN_HEALTH;
import static ru.warfare.darkannihilation.Constants.DEMOMAN_SHOOT_TIME;
import static ru.warfare.darkannihilation.math.Math.randInt;

public class Demoman extends Sprite {
    private long lastShoot = System.currentTimeMillis();
    private boolean direction;

    public Demoman() {
        super(ImageHub.demomanImg);
        damage = DEMOMAN_DAMAGE;

        hide();

        recreateRect(x + 30, y + 25, right() - 20, bottom() - 50);
    }

    public void hide() {
        lock = true;
        health = DEMOMAN_HEALTH;
        y = randInt(0, Game.halfScreenHeight - height);
        speedX = randInt(5, 10);
        direction = randInt(0, 1) == 0;
        if (direction) {
            x = -width;
        } else {
            x = Game.screenWidth;
            speedX = -speedX;
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
        return newRect(x + 30, y + 25);
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
    public void update() {
        shoot();

        x += speedX;

        if (direction) {
            if (x > Game.screenWidth) {
                hide();
            }
        } else {
            if (x < -width) {
                hide();
            }
        }
    }
}
