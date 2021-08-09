package ru.warfare.darkannihilation.enemy;

import ru.warfare.darkannihilation.HardThread;
import ru.warfare.darkannihilation.base.Sprite;
import ru.warfare.darkannihilation.bullet.Bomb;
import ru.warfare.darkannihilation.ImageHub;
import ru.warfare.darkannihilation.systemd.Game;

import static ru.warfare.darkannihilation.constant.Constants.DEMOMAN_DAMAGE;
import static ru.warfare.darkannihilation.constant.Constants.DEMOMAN_HEALTH;
import static ru.warfare.darkannihilation.constant.Constants.DEMOMAN_SHOOT_TIME;
import static ru.warfare.darkannihilation.math.Randomize.randBoolean;
import static ru.warfare.darkannihilation.math.Randomize.randInt;

public class Demoman extends Sprite {
    private long lastShoot = System.currentTimeMillis();
    private boolean direction;
    private final int endY;

    public Demoman(Game game) {
        super(game, ImageHub.demomanImg);
        damage = DEMOMAN_DAMAGE;

        endY = Game.halfScreenHeight - height;

        lock = true;
        baseSettings();

        recreateRect(x + 30, y + 25, right() - 20, bottom() - 50);
    }

    private void baseSettings() {
        health = DEMOMAN_HEALTH;
        y = randInt(0, endY);
        speedX = randInt(5, 10);
        direction = randBoolean();
        if (direction) {
            x = -width;
        } else {
            x = Game.screenWidth;
            speedX = -speedX;
        }
    }

    private void shoot() {
        if (System.currentTimeMillis() - lastShoot > DEMOMAN_SHOOT_TIME) {
            HardThread.doInBackGround(() -> game.intersectOnlyPlayer.add(new Bomb(game, centerX(), centerY())));
            lastShoot = System.currentTimeMillis();
        }
    }

    @Override
    public Sprite getRect() {
        return newRect(x + 30, y + 25);
    }

    @Override
    public void hide() {
        lock = true;
        HardThread.doInBackGround(this::baseSettings);
    }

    @Override
    public void intersectionPlayer() {
        createSkullExplosion();
        hide();
    }

    @Override
    public void kill() {
        intersectionPlayer();
        Game.score += 35;
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
