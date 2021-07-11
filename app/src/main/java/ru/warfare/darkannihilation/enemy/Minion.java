package ru.warfare.darkannihilation.enemy;

import ru.warfare.darkannihilation.bullet.BulletEnemy;
import ru.warfare.darkannihilation.hub.AudioHub;
import ru.warfare.darkannihilation.math.Vector;
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
            HardThread.newJob(() -> {
                int X = centerX();
                int Y = centerY();
                int[] values = vector.vector(X, Y, game.player.centerX(), game.player.centerY(), 13);
                Game.allSprites.add(new BulletEnemy(X, Y, values[2], values[0], values[1]));
                AudioHub.playShotgun();
            });
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