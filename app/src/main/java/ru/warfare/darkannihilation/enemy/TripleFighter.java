package ru.warfare.darkannihilation.enemy;

import ru.warfare.darkannihilation.HardThread;
import ru.warfare.darkannihilation.base.Sprite;
import ru.warfare.darkannihilation.bullet.BulletEnemy;
import ru.warfare.darkannihilation.hub.AudioHub;
import ru.warfare.darkannihilation.hub.ImageHub;
import ru.warfare.darkannihilation.math.Vector;
import ru.warfare.darkannihilation.systemd.Game;

import static ru.warfare.darkannihilation.Constants.TRIPLE_FIGHTER_DAMAGE;
import static ru.warfare.darkannihilation.Constants.TRIPLE_FIGHTER_HEALTH;
import static ru.warfare.darkannihilation.Constants.TRIPLE_FIGHTER_SHOOT_TIME;
import static ru.warfare.darkannihilation.math.Math.randInt;

public class TripleFighter extends Sprite {
    private long lastShoot = System.currentTimeMillis();
    private final Vector vector = new Vector();

    public TripleFighter(Game game) {
        super(game, ImageHub.tripleFighterImg);
        damage = TRIPLE_FIGHTER_DAMAGE;

        newStatus();

        recreateRect(x + 5, y + 5, right() - 5, bottom() - 5);
    }

    private void shoot() {
        if (System.currentTimeMillis() - lastShoot > TRIPLE_FIGHTER_SHOOT_TIME) {
            HardThread.newJob(() -> {
                int X = centerX();
                int Y = centerY();
                int[] values = vector.vector(X, Y, game.player.centerX(), game.player.centerY(), 13);
                Game.allSprites.add(new BulletEnemy(X, Y, values[2], values[0], values[1]));
                AudioHub.playShotgun();
            });
            lastShoot = System.currentTimeMillis();
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
}
