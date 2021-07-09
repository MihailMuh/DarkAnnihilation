package ru.warfare.darkannihilation.enemy;

import ru.warfare.darkannihilation.hub.AudioHub;
import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.HardThread;
import ru.warfare.darkannihilation.hub.ImageHub;
import ru.warfare.darkannihilation.base.Sprite;

import static ru.warfare.darkannihilation.Constants.XWING_DAMAGE;
import static ru.warfare.darkannihilation.Constants.XWING_HEALTH;
import static ru.warfare.darkannihilation.Constants.XWING_SHOOT_TIME;
import static ru.warfare.darkannihilation.math.Math.randInt;

public class XWing extends Sprite {
    private long lastShoot = System.currentTimeMillis();

    public XWing() {
        super(ImageHub.XWingImg);
        damage = XWING_DAMAGE;

        newStatus();

        recreateRect(x + 15, y + 15, x + width - 15, y + height - 15);
    }

    private void shoot() {
        long now = System.currentTimeMillis();
        if (now - lastShoot > XWING_SHOOT_TIME) {
            if (HardThread.job == 0) {
                HardThread.x = centerX();
                HardThread.y = centerY();
                HardThread.job = 8;
                lastShoot = now;
            }
        }
    }

    private void newStatus() {
        if (Game.bosses.size() == 0) {
            lock = true;
        }
        health = XWING_HEALTH;
        x = randInt(0, screenWidthWidth);
        y = -height;
        speedX = randInt(-3, 3);
        speedY = randInt(1, 8);

        if (buff) {
            up();
        }
    }

    private void up() {
        speedX *= 2;
        speedY *= 2;
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
        speedX /= 2;
        speedY /= 2;
    }

    @Override
    public Sprite getRect() {
        return newRect(x + 15, y + 15);
    }

    @Override
    public void intersection() {
        createLargeTripleExplosion();
        Game.score += 10;
        newStatus();
    }

    @Override
    public void intersectionPlayer() {
        AudioHub.playMetal();
        createSmallExplosion();
        newStatus();
    }

    @Override
    public void empireStart() {
        lock = false;
    }

    @Override
    public void update() {
        shoot();

        x += speedX;
        y += speedY;

        if (x < -width | x > Game.screenWidth | y > Game.screenHeight) {
            newStatus();
        }
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(image, x, y, Game.alphaEnemy);
    }
}