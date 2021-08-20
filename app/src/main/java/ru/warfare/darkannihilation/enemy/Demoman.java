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
import static ru.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_HEIGHT;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

public class Demoman extends Sprite {
    private long lastShoot = System.currentTimeMillis();
    private boolean goLeft = false;
    private final int endY;

    public Demoman(Game game) {
        super(game, ImageHub.demomanImg);
        damage = DEMOMAN_DAMAGE;

        endY = HALF_SCREEN_HEIGHT - height;
        speedX = -3;

        lock = true;

        recreateRect(x + 30, y + 25, right() - 20, bottom() - 50);
    }

    private void shoot() {
        if (System.currentTimeMillis() - lastShoot > DEMOMAN_SHOOT_TIME) {
            HardThread.doInBackGround(() ->
                    game.intersectOnlyPlayer.add(new Bomb(game, centerX(), centerY())));
            lastShoot = System.currentTimeMillis();
        }
    }

    @Override
    public Sprite getRect() {
        return newRect(x + 30, y + 25);
    }

    @Override
    public void start() {
        HardThread.doInBackGround(() -> {
            health = DEMOMAN_HEALTH;
            y = randInt(0, endY);
            speedX = randInt(5, 10);
            goLeft = randBoolean();

            if (goLeft) {
                image = ImageHub.mirrorImage(ImageHub.demomanImg, true);
                x = -width;
            } else {
                image = ImageHub.demomanImg;
                x = SCREEN_WIDTH;
                speedX = -speedX;
            }
            lock = false;
        });
    }

    @Override
    public void intersectionPlayer() {
        createSkullExplosion();
        lock = true;
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

        if (goLeft) {
            if (x > SCREEN_WIDTH) {
                lock = true;
            }
        } else {
            if (x < -width) {
                lock = true;
            }
        }
    }
}
