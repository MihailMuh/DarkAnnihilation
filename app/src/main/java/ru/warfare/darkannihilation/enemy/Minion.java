package ru.warfare.darkannihilation.enemy;

import ru.warfare.darkannihilation.base.BaseBullet;
import ru.warfare.darkannihilation.base.BaseEnemy;
import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.arts.ImageHub;
import ru.warfare.darkannihilation.base.Sprite;
import ru.warfare.darkannihilation.thread.Process;

import static ru.warfare.darkannihilation.constant.Constants.MINION_DAMAGE;
import static ru.warfare.darkannihilation.constant.Constants.MINION_HEALTH;
import static ru.warfare.darkannihilation.constant.Constants.MINION_SHOOT_TIME;
import static ru.warfare.darkannihilation.math.Randomize.randInt;
import static ru.warfare.darkannihilation.systemd.Game.now;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

public class Minion extends BaseEnemy {
    private long lastShoot = now;
    private Process process;

    public Minion(Game game) {
        super(game, ImageHub.minionImg, MINION_DAMAGE);
        recreateRect(x + 15, y + 15, right() - 15, bottom() - 15);
    }

    public void start(int X, int Y, Process p) {
        x = X;
        y = Y;

        process = p;

        health = MINION_HEALTH;

        speedX = randInt(-8, 8);
        speedY = randInt(2, 5);

        lock = false;
    }

    @Override
    public void shoot() {
        if (now - lastShoot > MINION_SHOOT_TIME) {
            lastShoot = now;
            process.post(() -> {
                bulletEnemy(13);
                AudioHub.playShotgun();
            });
        }
    }

    @Override
    public void intersectionPlayer() {
        AudioHub.playMetal();
        lock = true;
        createSmallTripleExplosion();
    }

    @Override
    public Sprite getRect() {
        return newRect(x + 15, y + 15);
    }

    @Override
    public void check_intersectionBullet(BaseBullet bullet) {
        if (intersect(bullet)) {
            bullet.kill();
            kill();
        }
    }

    @Override
    public void kill() {
        lock = true;
        createLargeTripleExplosion();
    }

    @Override
    public void update() {
        x += speedX;
        y += speedY;

        shoot();

        if (x < -width || x > SCREEN_WIDTH || y > SCREEN_HEIGHT) {
            lock = true;
        }
    }
}