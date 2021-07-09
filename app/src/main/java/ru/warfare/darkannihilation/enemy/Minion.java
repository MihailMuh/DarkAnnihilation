package ru.warfare.darkannihilation.enemy;

import ru.warfare.darkannihilation.hub.AudioHub;
import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.HardThread;
import ru.warfare.darkannihilation.hub.ImageHub;
import ru.warfare.darkannihilation.base.Sprite;

import static ru.warfare.darkannihilation.Constants.MINION_DAMAGE;
import static ru.warfare.darkannihilation.Constants.MINION_HEALTH;
import static ru.warfare.darkannihilation.Constants.MINION_SHOOT_TIME;
import static ru.warfare.darkannihilation.math.Math.randInt;

public class Minion extends Sprite {
    private long lastShoot = System.currentTimeMillis();

    public Minion(int x, int y) {
        super(ImageHub.minionImg);
        health = MINION_HEALTH;
        damage = MINION_DAMAGE;

        this.x = x;
        this.y = y;
        speedX = randInt(-8, 8);
        speedY = randInt(2, 5);

        recreateRect(x + 15, y + 15, right() - 15, bottom() - 15);
    }

    private void shoot() {
        if (System.currentTimeMillis() - lastShoot > MINION_SHOOT_TIME) {
            if (HardThread.job == 0) {
                lastShoot = System.currentTimeMillis();
                HardThread.x = centerX();
                HardThread.y = centerY();
                HardThread.job = 1;
            }
        }
    }

    @Override
    public void intersection() {
        Game.allSprites.remove(this);
        createLargeTripleExplosion();
    }

    @Override
    public void intersectionPlayer() {
        AudioHub.playMetal();
        Game.allSprites.remove(this);
        createSmallTripleExplosion();
    }

    @Override
    public Sprite getRect() {
        return newRect(x + 15, y + 15);
    }

    @Override
    public void check_intersectionBullet(Sprite bullet) {
        if (intersect(bullet)) {
            bullet.intersection();
            intersection();
        }
    }

    @Override
    public void update() {
        x += speedX;
        y += speedY;

        shoot();

        if (x < -width | x > Game.screenWidth | y > Game.screenHeight) {
            Game.allSprites.remove(this);
        }
    }
}