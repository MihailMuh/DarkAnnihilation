package ru.warfare.darkannihilation.enemy;

import ru.warfare.darkannihilation.base.BaseBullet;
import ru.warfare.darkannihilation.bullet.BulletEnemy;
import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.math.Vector;
import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.thread.HardThread;
import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.base.Sprite;

import static ru.warfare.darkannihilation.constant.Constants.MINION_DAMAGE;
import static ru.warfare.darkannihilation.constant.Constants.MINION_HEALTH;
import static ru.warfare.darkannihilation.constant.Constants.MINION_SHOOT_TIME;
import static ru.warfare.darkannihilation.math.Randomize.randInt;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_HEIGHT;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

public class Minion extends Sprite {
    private long lastShoot = System.currentTimeMillis();
    private final Vector vector = new Vector();

    public Minion(Game game, int x, int y) {
        super(game, ImageHub.minionImg);
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
            lastShoot = System.currentTimeMillis();
            HardThread.doInBackGround(() -> {
                int X = centerX();
                int Y = centerY();
                int[] values = vector.vector(X, Y, game.player.centerX(), game.player.centerY(), 13);
                game.intersectOnlyPlayer.add(new BulletEnemy(game, X, Y, values[2], values[0], values[1]));
                AudioHub.playShotgun();
            });
        }
    }

    @Override
    public void intersectionPlayer() {
        AudioHub.playMetal();
        hide();
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
    public void hide() {
        game.enemies.remove(this);
    }

    @Override
    public void kill() {
        hide();
        createLargeTripleExplosion();
    }

    @Override
    public void update() {
        x += speedX;
        y += speedY;

        shoot();

        if (x < -width | x > SCREEN_WIDTH | y > SCREEN_HEIGHT) {
            kill();
        }
    }
}