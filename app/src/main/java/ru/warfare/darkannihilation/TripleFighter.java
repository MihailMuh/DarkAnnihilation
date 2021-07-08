package ru.warfare.darkannihilation;

import static ru.warfare.darkannihilation.Constants.TRIPLE_FIGHTER_DAMAGE;
import static ru.warfare.darkannihilation.Constants.TRIPLE_FIGHTER_HEALTH;
import static ru.warfare.darkannihilation.Constants.TRIPLE_FIGHTER_SHOOT_TIME;
import static ru.warfare.darkannihilation.MATH.randInt;

public class TripleFighter extends Sprite {
    private long lastShoot = System.currentTimeMillis();

    public TripleFighter() {
        super(ImageHub.tripleFighterImg.getWidth(), ImageHub.tripleFighterImg.getHeight());
        damage = TRIPLE_FIGHTER_DAMAGE;

        newStatus();

        recreateRect(x + 5, y + 5, right() - 5, bottom() - 5);
    }

    public void shoot() {
        long now = System.currentTimeMillis();
        if (now - lastShoot > TRIPLE_FIGHTER_SHOOT_TIME) {
            if (HardThread.job == 0) {
                lastShoot = now;
                HardThread.x = centerX();
                HardThread.y = centerY();
                HardThread.job = 1;
            }
        }
    }

    private void newStatus() {
        if (Game.bosses.size() != 0) {
            lock = true;
        }
        health = TRIPLE_FIGHTER_HEALTH;
        x = randInt(0, screenWidthWidth);
        y = -height;
        speedX = randInt(-3, 3);
        speedY = randInt(1, 10);
    }

    @Override
    public Sprite getRect() {
        return newRect(x + 5, y + 5);
    }

    @Override
    public void intersection() {
        createLargeTripleExplosion();
        Game.score += 5;
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
        if (x > 0 & x < screenWidthWidth & y > 0 & y < screenHeightHeight) {
            shoot();
        }

        x += speedX;
        y += speedY;

        if (x < -width | x > Game.screenWidth | y > Game.screenHeight) {
            newStatus();
        }
    }

    @Override
    public void render() {
        Game.canvas.drawBitmap(ImageHub.tripleFighterImg, x, y, null);
    }
}
