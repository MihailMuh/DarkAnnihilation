package ru.warfare.darkannihilation.enemy;

import static ru.warfare.darkannihilation.constant.Constants.DEMOMAN_DAMAGE;
import static ru.warfare.darkannihilation.constant.Constants.DEMOMAN_HEALTH;
import static ru.warfare.darkannihilation.constant.Constants.DEMOMAN_SHOOT_TIME;
import static ru.warfare.darkannihilation.constant.Constants.NUMBER_BOMBS;
import static ru.warfare.darkannihilation.constant.Constants.NUMBER_BULLETS_ENEMY;
import static ru.warfare.darkannihilation.math.Randomize.randBoolean;
import static ru.warfare.darkannihilation.math.Randomize.randInt;
import static ru.warfare.darkannihilation.systemd.service.Windows.HALF_SCREEN_HEIGHT;
import static ru.warfare.darkannihilation.systemd.service.Windows.SCREEN_WIDTH;

import android.graphics.Bitmap;

import ru.warfare.darkannihilation.arts.ImageHub;
import ru.warfare.darkannihilation.audio.AudioHub;
import ru.warfare.darkannihilation.base.Sprite;
import ru.warfare.darkannihilation.systemd.Game;
import ru.warfare.darkannihilation.thread.GameTask;

public class Demoman extends Sprite {
    private final GameTask gameTask = new GameTask(this::shoot, DEMOMAN_SHOOT_TIME);

    private boolean goLeft = false;
    private final int endY;
    private final Bitmap mirrorImage;

    public Demoman(Game game) {
        super(game, ImageHub.demomanImg);
        damage = DEMOMAN_DAMAGE;

        endY = HALF_SCREEN_HEIGHT - height;
        speedX = -3;

        lock = true;

        mirrorImage = ImageHub.mirrorImage(ImageHub.demomanImg);

        recreateRect(x + 30, y + 25, right() - 20, bottom() - 50);
    }

    private void shoot() {
        for (int i = NUMBER_BULLETS_ENEMY; i < NUMBER_BOMBS; i++) {
            if (Game.bulletsEnemy[i].lock) {
                Game.bulletsEnemy[i].start(centerX(), centerY());
                AudioHub.playFallingBomb();
                break;
            }
        }
    }

    @Override
    public Sprite getRect() {
        return newRect(x + 30, y + 25);
    }

    @Override
    public void start() {
        health = DEMOMAN_HEALTH;
        y = randInt(0, endY);
        speedX = randInt(5, 10);
        goLeft = randBoolean();

        if (goLeft) {
            image = mirrorImage;
            x = -width;
        } else {
            image = ImageHub.demomanImg;
            x = SCREEN_WIDTH;
            speedX = -speedX;
        }

        lock = false;
        gameTask.start();
    }

    @Override
    public void intersectionPlayer() {
        lock = true;
        gameTask.stop();
        createSkullExplosion();
    }

    @Override
    public void kill() {
        if (!lock) {
            intersectionPlayer();
            Game.score += 35;
        }
    }

    @Override
    public void update() {
        x += speedX;

        if (goLeft) {
            if (x > SCREEN_WIDTH) {
                gameTask.stop();
                lock = true;
            }
        } else {
            if (x < -width) {
                gameTask.stop();
                lock = true;
            }
        }
    }
}
